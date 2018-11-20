package com.gpdi.mdata.web.reportform.daoexcel.cleaninventory.action;



import com.gpdi.mdata.sys.entity.report.CleanInventory;
import com.gpdi.mdata.sys.entity.report.CleanInventoryRel;
import com.gpdi.mdata.sys.entity.report.PurchaseContract;
import com.gpdi.mdata.sys.service.reportform.cleanInventory.InventoryExamineService;
import com.gpdi.mdata.sys.service.reportform.daoexcel.ExtractTextFromXLSService;
import com.gpdi.mdata.web.reportform.daoexcel.entities.clean.ExcelColumn;
import com.gpdi.mdata.web.reportform.daoexcel.entities.clean.ExcelColumnToCleanInventory;
import com.gpdi.mdata.web.reportform.task.accountimport.ExcelCleanHandleTask;
import com.gpdi.mdata.web.reportform.task.match.ExecutorManage;
import com.gpdi.mdata.web.reportform.thread.CleanOutThread;
import com.gpdi.mdata.web.utils.UploadUtil;
import com.gpdi.mdata.web.utils.UploadUtil2;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;
import pub.spring.web.mvc.ActionResult;
import pub.types.ValidationError;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Date;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.FutureTask;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @description:导入即时清结数据
 * @author: WangXiaoGang
 * @data: Created in 2018/7/13 19:42
 * @modifier:
 */
@Controller
public class OperateAction {
    protected static final Logger log = Logger.getLogger(OperateAction.class);

    @Autowired
    InventoryExamineService inventorys;

    @RequestMapping
    public ActionResult daoExcel(MultipartFile file) {
        String fileName = file.getOriginalFilename();//获取文件名称
        InputStream in = null; //输入字节流
        log.info(this.getClass().getName() + "daoExcel()->开始导入数据");
        List<CleanInventory> results = new LinkedList<>();//创建结果集对象   ////改
        List<CleanInventoryRel>  rela = new ArrayList<>() ;
        try {
            //1.打开excel表
            in = file.getInputStream();
            List<List<String>> rows = UploadUtil2.parse(in, fileName);//调用excel读取方法返回行
            //2. 读取Excel内容
            ExcelColumnToCleanInventory parser = null;
            boolean header = true;
            for (List<String> columns : rows) {//获取每一行一行的数据
                if (header) {
                    List<ExcelColumn> list = new ArrayList<>();
                    for (ExcelColumn column : ExcelColumn.values()) {
                        String ob = column.getName().trim();
                        if (!columns.contains(ob)) { //contains(),该方法是判断字符串中是否有子字符串。如果有则返回true,如果没有则返回false
                            log.info("请在第一张表中指定列名称--" + column.getName());
                            return ActionResult.fail("请在第一张表中指定列名称--" + column.getName());
                        }
                        column.setColumn(columns.indexOf(column.getName()));
                        list.add(column);
                    }
                    parser = new ExcelColumnToCleanInventory(list);
                    header = false;
                    continue;
                }
                CleanInventory purchase = parser.parse(columns);//改

                results.add(purchase);
            }

            //4. 处理Excel行数据
            FutureTask task = new FutureTask<>(new ExcelCleanHandleTask(results));
            ExecutorManage.submit(task);

//=============================4.2去重,将重复的脏数据清理并存入另一张表中,源导入的数据保留=========================
            // rela = inventorys.cleanAll();
            boolean cleans = inventorys.saveClean(results);
            if (cleans){
                log.info("清理数据并保存成功");
            }else {
                log.error("保存数据失败");
            }


//======================异常处理======================================
            //5.异常处理
        } catch (ValidationError e) {//验证,校验,规则合法性错误,描述错误异常
            e.printStackTrace();
            log.info(this.getClass().getName() + "daoExcel()-->->规则合法性错误异常:" + e.getMessage());
            return ActionResult.fail(e.getMessage());
        } catch (IOException e) {//当用FileInputStrem的read方法读取一文件时,需要捕获IOException
            e.printStackTrace();
            log.info(this.getClass().getName() + "daoExcel()-->->文件流读取异常:" + e.getMessage());
            return ActionResult.fail("导入失败,请重新导入");
        } catch (IllegalArgumentException e) {//非法参数异常,此异常表明向方法传递了一个不合法或不正确的参数。你看看传值的方法是否参数不正确
            e.printStackTrace();
            log.info(this.getClass().getName() + "daoExcel()-->->传入的参数类型不正确:" + e.getMessage());
            return ActionResult.fail("导入失败,请检查各参数类型是否正确");
        } catch (Exception e) {
            e.printStackTrace();
            log.info(this.getClass().getName() + "daoExcel()-->->导入数据出错:" + e.getMessage());
            return ActionResult.fail("导入失败,请联系管理员");
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }
        return ActionResult.ok("导入数据成功");
    }




}