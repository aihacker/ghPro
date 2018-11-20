package com.gpdi.mdata.web.reportform.daoexcel.bidcompanyinfo.action;

import com.gpdi.mdata.sys.entity.report.BidCompanyInfo;
import com.gpdi.mdata.sys.entity.report.PurchaseParent;
import com.gpdi.mdata.sys.service.reportform.daoexcel.ExtractTextFromXLSService;
import com.gpdi.mdata.sys.service.reportform.daoexcel.PurchaseParentService;
import com.gpdi.mdata.web.reportform.daoexcel.entities.bidcominfo.ExcelColumn;
import com.gpdi.mdata.web.reportform.daoexcel.entities.bidcominfo.ExcelColumnToBidCompanyInfo;
import com.gpdi.mdata.web.reportform.task.accountimport.ExcelBidCompanyHandleTask;
import com.gpdi.mdata.web.reportform.task.accountimport.ExcelPurchaseHandleTask;
import com.gpdi.mdata.web.reportform.task.match.ExecutorManage;
import com.gpdi.mdata.web.utils.UploadUtil;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;
import pub.spring.web.mvc.ActionResult;
import pub.types.ValidationError;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Date;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.FutureTask;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @description:公开采购投标公司信息
 * @author: WangXiaoGang
 * @data: Created in 2018/7/13 19:42
 * @modifier:
 */
@Controller
public class OperateAction {
    protected static final Logger log = Logger.getLogger(OperateAction.class);
    //重入锁:就是一个线程在获取了锁之后，再次去获取了同一个锁，这时候仅仅是把状态值进行累加。不需要排队
    private ReentrantLock lock = new ReentrantLock();

    @Autowired
    private PurchaseParentService purchaseParentService;

    @Autowired
    private ExtractTextFromXLSService extractService;
    @RequestMapping
   public ActionResult daoExcel(MultipartFile file) {
       String fileName = file.getOriginalFilename();//获取文件名称
        // String fileName = file.getSession().getServletContext().getRealPath("upload");//获取文件名称
        String excelName = extractService.getFileName(fileName);
        if (excelName != null) {
            log.error("++++++++++++++++文件名称已经存在++++++++++++++" + excelName);
            return ActionResult.fail("文档已存在,请勿重复导入!");//根据文件名称判断是否有导入
        }
        InputStream in = null; //输入字节流
        log.info(this.getClass()+"daoExcel()->开始导入数据");
        List<BidCompanyInfo> results = new LinkedList<>();//创建结果集对象
        try {
            //1.打开excel表
            in = file.getInputStream();
            List<List<String>> rows = UploadUtil.parse(in,fileName);//调用excel读取方法返回行
            //2. 读取Excel内容
            ExcelColumnToBidCompanyInfo parser = null;
            boolean header = true;
            for (List<String> columns : rows) {//获取每一行的数据
                if (header) {
                    List<ExcelColumn> list = new ArrayList<>();
                    for (ExcelColumn column : ExcelColumn.values()) {
                        if (!columns.contains(column.getName())) { //contains(),该方法是判断字符串中是否有子字符串。如果有则返回true,如果没有则返回false
                            log.info("请在第一张表中指定列名称--" + column.getName());
                            return ActionResult.fail("请在第一张表中指定列名称--" + column.getName());
                        }
                        column.setColumn(columns.indexOf(column.getName()));
                        list.add(column);
                    }
                    parser = new ExcelColumnToBidCompanyInfo(list);
                    header = false;
                    continue;
                }
                BidCompanyInfo bidInfo = parser.parse(columns);

                if(bidInfo.getOpeningTimeStr() !=null && bidInfo.getOpeningTimeStr().length()>0) { //判断开标时间是否为空
                    try {
                        bidInfo.setOpeningTime(Date.valueOf(bidInfo.getOpeningTimeStr()));//将字符串转化为date日期类型
                    } catch (Exception e) {
                        log.error("第"+rows.indexOf(columns)+"行开标时间"+bidInfo.getOpeningTimeStr()+"未能识别,请输入时间格式为2018-5-20格式的日期");
                        e.printStackTrace();
                        return ActionResult.fail("第"+rows.indexOf(columns)+"行开标时间"+bidInfo.getOpeningTimeStr()+"未能识别,请输入时间格式为2018-5-20格式的日期");
                    }
                }
                results.add(bidInfo);
            }
            //3.保存导入日志
            PurchaseParent purchaseParent;
            try {
                String md5 = DigestUtils.md5Hex(file.getInputStream());//根据数据生成唯一标识
                System.out.println("MD5 : "+md5+"++++++++++++++++++:"+file.getInputStream());
                lock.lock();
                PurchaseParent purchase = purchaseParentService.getByMd5(md5);
                //导入失败的可以重新导入，否则不可导入
                if(purchase != null){
                    return ActionResult.fail("文件已存在,请勿重复导入.");
                }
                int type = 3;//设置上传的文件类型:,0:采购合同报表;1.合同台账报表;2:领导亲属经商表;3:公开采购投标公司信息
                purchaseParent = purchaseParentService.create(file,type);
                if (purchaseParent ==null || purchaseParent.getId() ==null){
                    log.error(this.getClass().getName()+"生成导入日志失败");
                    return ActionResult.fail("服务器异常,请联系管理员");
                }
            } catch (Exception e) {
                e.printStackTrace();
                log.info(this.getClass().getName()+"doImportAccount()->导入数据出错:"+e.getMessage());
                return ActionResult.fail("导入失败,请联系管理员.");
            }finally {
                lock.unlock();//释放重锁
            }
            //4. 处理Excel行数据
            FutureTask task = new FutureTask<>(new ExcelBidCompanyHandleTask(results,purchaseParent.getId()));
            ExecutorManage.submit(task);
            //extractTextFromXLSService.savePurchaseContract(results);//保存到数据库中

            //5.异常处理
        }catch (ValidationError e) {//验证,校验,规则合法性错误,描述错误异常
            e.printStackTrace();
            log.info(this.getClass().getName() + "daoExcel()-->->规则合法性错误异常:" + e.getMessage());
            return ActionResult.fail(e.getMessage());
        } catch (IOException e) {//当用FileInputStrem的read方法读取一文件时,需要捕获IOException
            e.printStackTrace();
            log.info(this.getClass().getName() + "daoExcel()-->->文件流读取异常:" + e.getMessage());
            return ActionResult.fail("导入失败,请重新导入");
        }catch (IllegalArgumentException e){//非法参数异常,此异常表明向方法传递了一个不合法或不正确的参数。你看看传值的方法是否参数不正确
            e.printStackTrace();
            log.info(this.getClass().getName() + "daoExcel()-->->传入的参数类型不正确:" + e.getMessage());
            return ActionResult.fail("导入失败,请检查各参数类型是否正确");
        } catch (Exception e){
            e.printStackTrace();
            log.info(this.getClass().getName() + "daoExcel()-->->导入数据出错:" + e.getMessage());
            return ActionResult.fail("导入失败,请联系管理员");
        }finally {
            if (in !=null){
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return ActionResult.ok("导入成功");

    }
}
