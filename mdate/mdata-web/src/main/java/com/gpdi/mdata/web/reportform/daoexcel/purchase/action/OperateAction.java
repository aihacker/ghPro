package com.gpdi.mdata.web.reportform.daoexcel.purchase.action;

import com.gpdi.mdata.sys.entity.report.PurchaseContract;
import com.gpdi.mdata.sys.entity.report.PurchaseParent;
import com.gpdi.mdata.sys.service.reportform.daoexcel.ExtractTextFromXLSService;
import com.gpdi.mdata.sys.service.reportform.daoexcel.PurchaseParentService;
import com.gpdi.mdata.web.reportform.daoexcel.entities.PurchaseContract.ExcelColumn;
import com.gpdi.mdata.web.reportform.daoexcel.entities.PurchaseContract.ExcelColumnToPurchaseContract;
import com.gpdi.mdata.web.reportform.task.accountimport.ExcelPurchaseHandleTask;
import com.gpdi.mdata.web.reportform.task.match.ExecutorManage;
import com.gpdi.mdata.web.reportform.thread.OutThread;
import com.gpdi.mdata.web.reportform.thread.ResultCodeThread;
import com.gpdi.mdata.web.utils.UploadUtils;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.fileupload.disk.DiskFileItem;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import pub.dao.GeneralDao;
import pub.spring.web.mvc.ActionResult;
import pub.types.ValidationError;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Date;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.FutureTask;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @description:导入采购合同台账数据
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
    private GeneralDao generalDao;
    @Autowired
    private PurchaseParentService purchaseParentService;

    @Autowired
    private ExtractTextFromXLSService extractService;
    @RequestMapping
   public ActionResult daoExcel(MultipartFile file) {
       String fileName = file.getOriginalFilename();//获取文件名称
       // String fileName = file.getSession().getServletContext().getRealPath("upload");//获取文件名称
        /*String excelName = extractService.getFileName(fileName);
        if (excelName != null) {
            log.error("++++++++++++++++文件名称已经存在++++++++++++++" + excelName);
            return ActionResult.fail("文档已存在,请勿重复导入!");//根据文件名称判断是否有导入
        }*/
        InputStream in = null; //输入字节流
        log.info(this.getClass().getName()+"daoExcel()->开始导入数据");
        List<PurchaseContract> results = new LinkedList<>();//创建结果集对象
        try {
            //1.打开excel表
            in = file.getInputStream();
            List<List<String>> rows = UploadUtils.parse(in,fileName);//调用excel读取方法返回行
            //2. 读取Excel内容
            ExcelColumnToPurchaseContract parser = null;
            boolean header = true;
            for (List<String> columns : rows) {//获取每一行一行的数据
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
                    parser = new ExcelColumnToPurchaseContract(list);
                    header = false;
                    continue;
                }
                PurchaseContract purchase = parser.parse(columns);



                //中间可做检索
                //if(StringUtils.isNotBlank(purchase.getPurchaseTimeStr())) { //判断定稿时间是否为空
                if(purchase.getPurchaseTimeStr() !=null && purchase.getPurchaseTimeStr().length()>0) { //判断定稿时间是否为空
                    try {
                        purchase.setPurchaseTime(Date.valueOf(purchase.getPurchaseTimeStr()));//将字符串转化为date日期类型
                    } catch (Exception e) {
                        log.error("第"+rows.indexOf(columns)+"行定稿时间"+purchase.getPurchaseTimeStr()+"未能识别,请输入时间格式为2018-5-20格式的日期");
                        e.printStackTrace();
                        return ActionResult.fail("第"+rows.indexOf(columns)+"行定稿时间"+purchase.getPurchaseTimeStr()+"未能识别,请输入时间格式为2018-5-20格式的日期");
                    }
                }/* else {
                    return ActionResult.fail("第"+rows.indexOf(columns)+"行定稿时间不能为空");
                }*/


                if(StringUtils.isNotBlank(purchase.getArchiveDateStr())) { //判断归档日期是否为空
                    try {
                        purchase.setArchiveDate(Date.valueOf(purchase.getArchiveDateStr()));//将字符串转化为date日期类型
                    } catch (Exception e) {
                        log.error("第"+rows.indexOf(columns)+"行归档日期"+purchase.getArchiveDateStr()+"未能识别,请输入时间格式为2018-5-20格式的日期");
                        e.printStackTrace();
                        return ActionResult.fail("第"+rows.indexOf(columns)+"行归档日期"+purchase.getArchiveDateStr()+"未能识别,请输入时间格式为2018-5-20格式的日期");
                    }
                }/*else{
                    return ActionResult.fail("第"+rows.indexOf(columns)+"行归档日期不能为空");
                }*/


                if(StringUtils.isNotBlank(purchase.getAmount())) {
                    try {
                        //replaceAll:替换字符串,这里的意思把金额中,替换成空格
                        purchase.setContractAmount(Double.parseDouble(purchase.getAmount().replaceAll(",", "")));
                    } catch (NumberFormatException e) {
                        e.printStackTrace();
                        log.error("第" + rows.indexOf(columns) + "行合同金额" + purchase.getAmount() + "未能识别");
                        return ActionResult.fail("第" + rows.indexOf(columns) + "行合同金额:" + purchase.getAmount() + "未能识别");
                    }
                }/*else {
                    return ActionResult.fail("第"+rows.indexOf(columns)+"行合同金额不能为空:"+ purchase.getAmount());
                }*/
                results.add(purchase);
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
                int type = 0;//设置上传的文件类型:,0:采购合同报表;1.合同台账报表;2:领导亲属经商表
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
            FutureTask task = new FutureTask<>(new ExcelPurchaseHandleTask(results,purchaseParent.getId()));
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
        OutThread out = new OutThread(results);
        out.start();
       /*  ResultCodeThread into = new ResultCodeThread(results);
        into.start();*/
        try {
            intables(results);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            return ActionResult.ok("导入成功");
        }
        //return ActionResult.ok("导入成功");

    }

    public void intables(List<PurchaseContract> list){
        for (PurchaseContract purchase : list) {
            String resultCode = purchase.getPurchaseResultCode();//获取采购结果单号
            if(resultCode ==null || resultCode ==""){
                continue;
            }
            boolean result=true;
            String sqlw ="SELECT pur_result_code FROM t_candidater WHERE pur_result_code ='"+resultCode+"'";
            String ok = generalDao.queryValue(String.class,sqlw);
            if (ok !=null){
                continue;
            }
            try {

                result = extractService.insertData(resultCode);
               // Thread.sleep(300000);//5000是5秒钟,30W就是5分钟
            } catch (Exception e) {
                e.printStackTrace();
                log.error("插入机器人中间表失败!");
            }
            if (result){
                continue;
            }else {
                break;
            }

        }
    }
}
