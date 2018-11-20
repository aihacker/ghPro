package com.gpdi.mdata.web.reportform.daoexcel.kinsfolk.action;


import com.gpdi.mdata.sys.entity.report.KinsfolkBusiness;
import com.gpdi.mdata.sys.entity.report.PurchaseParent;
import com.gpdi.mdata.sys.service.reportform.daoexcel.ExtractTextFromXLSService;
import com.gpdi.mdata.sys.service.reportform.daoexcel.PurchaseParentService;
import com.gpdi.mdata.web.reportform.daoexcel.entities.kinsfolk.ExcelColumn;
import com.gpdi.mdata.web.reportform.daoexcel.entities.kinsfolk.ExcelColumnTokinsfolk;
import com.gpdi.mdata.web.reportform.task.accountimport.ExcelKinsfolkHandleTask;
import com.gpdi.mdata.web.reportform.task.match.ExecutorManage;
import com.gpdi.mdata.web.utils.UploadUtils;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;
import pub.spring.web.mvc.ActionResult;
import pub.spring.web.mvc.model.Model;
import pub.types.ValidationError;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.FutureTask;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @description:导入领导亲属经商数据
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
    private ExtractTextFromXLSService extractService;
    @Autowired
    private PurchaseParentService purchaseParentService;

    @RequestMapping
    public ActionResult upload(MultipartFile file, Model model) {
        String fileName = file.getOriginalFilename();//获取文件名称
        String excelName = extractService.getFileName(fileName);
        if (excelName != null) {
            log.error("++++++++++++++++文件名称已经存在++++++++++++++" + excelName);
            return ActionResult.fail("文档已存在,请勿重复导入!");//根据文件名称判断是否有导入
        }
        byte[] fileBytes = null;
        try {
            fileBytes = file.getBytes();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        InputStream is = new ByteArrayInputStream(fileBytes);//创建并传入输入文件流
        List<List<String>> rows = new ArrayList<List<String>>();
        try {
            rows = UploadUtils.parse(is, fileName);//调用excel识别工具返回结果
            //读取excel内容
            ExcelColumnTokinsfolk parses = null;
            boolean header = true;
            List<KinsfolkBusiness> results = new LinkedList<>();//创建结果集对象
            for (List<String> columns : rows) {
                if (header) {
                    List<ExcelColumn> list = new ArrayList<>();//创建标题映射对象
                    for (ExcelColumn column : ExcelColumn.values()) {
                        if (!columns.contains(column.getName())) {
                            log.error("请在第一张表中指定列名称:" + column.getName());
                            return ActionResult.fail("请在第一张表中指定列名称:" + column.getName());
                        }
                        column.setColumn(columns.indexOf(column.getName()));
                        list.add(column);
                    }
                    parses = new ExcelColumnTokinsfolk(list);
                    header = false;
                    continue;
                }
                KinsfolkBusiness kinsfolk = parses.parse(columns);
                //中间可做检索
                results.add(kinsfolk);
            }
            //保存导入日志
            PurchaseParent purchaseParent;
            try {
                String md5 = DigestUtils.md5Hex(file.getInputStream());//根据输入文件流生成唯一标识
                System.out.println("MD5 : " + md5 + "++++++++++++++++++:" + file.getInputStream());
                /**
                 * 重入锁（ReentrantLock)
                 * 这里加锁的目的是为了满足用户在上传文件过程中产生取消,卡顿,网页关闭,等其他异常引起的一些操作时的续传作用,
                 * 还有防止多个用户同时上传生成数据的过程中导致数据混乱,好处是必须一个线程一个线程走完才能触发下一个工作任务
                 */
                lock.lock();//加锁
                PurchaseParent purchase = purchaseParentService.getByMd5(md5);
                if (purchase != null) {
                    return ActionResult.fail("文件已导入,请勿重复导入");//这里是从导入的文件内容去判断是否已经导入过,不是根据文件名称
                }
                int type = 2;//设置上传的文件类型:,0:采购合同报表;1.合同台账报表;2:领导亲属经商表
                purchaseParent = purchaseParentService.create(file, type);
                if (purchaseParent == null || purchaseParent.getId() == null) {
                    log.error(this.getClass().getName() + "生成导入日志失败");
                    return ActionResult.fail("服务器异常,请联系管理员");
                }
            } catch (Exception e) {
                e.printStackTrace();
                log.info(this.getClass().getName()+"upload()->导入数据出错:"+e.getMessage());
                return ActionResult.fail("导入失败,请联系管理员.");
            }finally {
                lock.unlock();//释放重锁
            }

            //4. 处理Excel行数据
            /**
             * FutureTask:线程池的实现核心之一是FutureTask。在提交任务时，用户实现的Callable实例task会被包装为FutureTask实例ftask；
             * 提交后任务异步执行，无需用户关心；当用户需要时，再调用FutureTask#get()获取结果——或异常。
             */
            FutureTask task = new FutureTask<>(new ExcelKinsfolkHandleTask(results,purchaseParent.getId()));
            ExecutorManage.submit(task);
        //异常处理
        }catch (ValidationError e) {//验证,校验,规则合法性错误,描述错误异常
            e.printStackTrace();
            log.info(this.getClass().getName() + "upload()-->->规则合法性错误异常:" + e.getMessage());
            return ActionResult.fail(e.getMessage());
        } catch (IOException e) {//当用FileInputStrem的read方法读取一文件时,需要捕获IOException
            e.printStackTrace();
            log.info(this.getClass().getName() + "uplaod()-->->文件流读取异常:" + e.getMessage());
            return ActionResult.fail("导入失败,请重新导入");
        }catch (IllegalArgumentException e){//非法参数异常,此异常表明向方法传递了一个不合法或不正确的参数。你看看传值的方法是否参数不正确
            e.printStackTrace();
            log.info(this.getClass().getName() + "uplaod()-->->传入的参数类型不正确:" + e.getMessage());
            return ActionResult.fail("导入失败,请检查各参数类型是否正确");
        } catch (Exception e){
            e.printStackTrace();
            log.info(this.getClass().getName() + "upload()-->->导入数据出错:" + e.getMessage());
            return ActionResult.fail("导入失败,请联系管理员");
        }finally {
            if (is !=null){
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return ActionResult.ok("导入成功");

    }
}
