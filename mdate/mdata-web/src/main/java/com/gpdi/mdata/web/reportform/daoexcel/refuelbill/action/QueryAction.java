package com.gpdi.mdata.web.reportform.daoexcel.refuelbill.action;

import com.gpdi.mdata.sys.entity.report.MachineBill;
import com.gpdi.mdata.sys.entity.report.MachineTitleBill;
import com.gpdi.mdata.sys.service.reportform.daoexcel.ExtractTextFromXLSService;
//import com.gpdi.mdata.web.reportform.daoexcel.entity.PurchaseContract.ExcelColumnBill;
//mport com.gpdi.mdata.web.reportform.daoexcel.entity.PurchaseContract.ExcelColumnToPetrolStationBill;
import com.gpdi.mdata.web.reportform.daoexcel.entities.StationBill.ExcelColumnBill;
import com.gpdi.mdata.web.reportform.daoexcel.entities.StationBill.ExcelColumnToPetrolStationBill;
import com.gpdi.mdata.web.utils.ExcelUtil;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;
import pub.spring.web.mvc.ActionResult;
import pub.types.ValidationError;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @description:导入加油站账单
 * @author: zzy
 * @data: Created in 2018/6/22 9:26
 * @modifier:
 */

@Controller
public class QueryAction {
    protected static final Logger log = Logger.getLogger(QueryAction.class);
    //重入锁:就是一个线程在获取了锁之后，再次去获取了同一个锁，这时候仅仅是把状态值进行累加。不需要排队
    private ReentrantLock lock = new ReentrantLock();
    @Autowired
    private ExtractTextFromXLSService extractTextFromXLSService;

   /*@Autowired
    private PurchaseBillParentService purchaseParentService;*/

    public static int a = 0;
    long id =System.currentTimeMillis();

    @RequestMapping
    public void execute() {
    }
     @RequestMapping
         public ActionResult daoExcel(MultipartFile file) {
         InputStream in = null; //输入字节流
         List<MachineTitleBill> temp = new LinkedList<>();
         List<List<String>> t_list = new LinkedList<>();
         List list2 = new ArrayList();
         log.info(this.getClass().getName()+"daoExcel()->开始导入数据");
         try {
             //1.打开excel表
             in = file.getInputStream();
             List<List<String>> rows = ExcelUtil.parse(in);//调用excel读取方法返回行

             System.out.println(id);
            a++;
            //======================================================================

                MachineTitleBill mtb=new MachineTitleBill();

                    mtb.setCustomerName(rows.get(1).get(1).toString());
                     mtb.setCustomerCode(rows.get(1).get(3).toString());
                     mtb.setWebsiteName(rows.get(2).get(1).toString());
                     mtb.setBillType(rows.get(1).get(3).toString());
                     mtb.setStartstopTime(rows.get(3).get(1).toString());
                     mtb.setApplyType(rows.get(3).get(3).toString());
                     mtb.setBillOperator(rows.get(4).get(1).toString());
                     mtb.setPrintDate((rows.get(4).get(4)).toString());
                     mtb.setTitleId((double) id);
                 temp.add(mtb);

             extractTextFromXLSService.savePurchaseTitleBill(temp);
            //======================================================
            for (int k = 5; k <=rows.size()-2; k++) {
                    list2 = rows.get(k);
                    if (k == 5) {
                        list2.add("标题");
                    }else {
                        list2.add(String.valueOf(id));
                    }
                    if (list2.get(4).equals("小计") || list2.get(0).equals("由于可能存在的在途交易，此对账单只做参考")) {
                        continue;
                    }
                    t_list.add(list2);
            }

             System.out.println(t_list);

             //2. 读取Excel内容
             ExcelColumnToPetrolStationBill parser = null;
             boolean header = true;

             List<MachineBill> results = new LinkedList<>();//创建结果集对象
                 for (List<String> columns : t_list) {
                 if (header) {
                     List<ExcelColumnBill> list = new ArrayList<>();
                     for (ExcelColumnBill column : ExcelColumnBill.values()) {
                        //System.out.println(column+"--------"+column.getName());
                         /*if (!columns.contains(column.getName())) { //contains(),该方法是判断字符串中是否有子字符串。如果有则返回true,如果没有则返回false
                             log.info("请在第一张表中指定列名称--" + column.getName());
                             return ActionResult.fail("请在第一张表中指定列名称--" + column.getName());
                         }*/
                         column.setColumn(columns.indexOf(column.getName()));
                         //column.setTitle_id(a);//加入title_id
                         list.add(column);

                     }

                     parser = new ExcelColumnToPetrolStationBill(list);
                     header = false;
                     continue;
                 }
                /* PurchaseContract purchase = parser.parse(columns);*/
                     MachineBill purchase = parser.parse(columns);
                 //中间可做检索
                 //if(StringUtils.isNotBlank(purchase.getPurchaseTimeStr())) { //判断定稿时间是否为空
                     //===============================================
                 /*if(purchase.getPurchaseTimeStr() !=null && purchase.getPurchaseTimeStr().length()>0) { //判断定稿时间是否为空
                     try {
                         purchase.setPurchaseTime(Date.valueOf(purchase.getPurchaseTimeStr()));//将字符串转化为date日期类型
                     } catch (Exception e) {

                         log.error("第"+rows.indexOf(columns)+"行定稿时间"+purchase.getPurchaseTimeStr()+"未能识别,请输入时间格式为2018-5-20格式的日期");
                         e.printStackTrace();
                        // return ActionResult.fail("第"+rows.indexOf(columns)+"行定稿时间"+purchase.getPurchaseTimeStr()+"未能识别,请输入时间格式为2018-5-20格式的日期");
                     }
                 }*/
                 //=========================================
                /* else {
                     return ActionResult.fail("第"+rows.indexOf(columns)+"行定稿时间不能为空");
                 }*//*


                 if(StringUtils.isNotBlank(purchase.getArchiveDateStr())) { //判断归档日期是否为空
                     try {
                         purchase.setArchiveDate(Date.valueOf(purchase.getArchiveDateStr()));//将字符串转化为date日期类型
                     } catch (Exception e) {
                         log.error("第"+rows.indexOf(columns)+"行归档日期"+purchase.getArchiveDateStr()+"未能识别,请输入时间格式为2018-5-20格式的日期");
                        e.printStackTrace();
                         //return ActionResult.fail("第"+rows.indexOf(columns)+"行归档日期"+purchase.getArchiveDateStr()+"未能识别,请输入时间格式为2018-5-20格式的日期");
                     }
                 }*//*else{
                     return ActionResult.fail("第"+rows.indexOf(columns)+"行归档日期不能为空");
                 }*//*


                 if(StringUtils.isNotBlank(purchase.getAmount())) {
                     try {
                         //replaceAll:替换字符串,这里的意思把金额中,替换成空格
                         purchase.setContractAmount(Double.parseDouble(purchase.getAmount().replaceAll(",", "")));
                     } catch (NumberFormatException e) {
                         e.printStackTrace();
                         log.error("第" + rows.indexOf(columns) + "行合同金额" + purchase.getAmount() + "未能识别");
                        // return ActionResult.fail("第" + rows.indexOf(columns) + "行合同金额" + purchase.getAmount() + "未能识别");
                     }
                 }*//*else {
                     return ActionResult.fail("第"+rows.indexOf(columns)+"行合同金额不能为空");
                 }*/
                 results.add(purchase);

             }
             //3.保存导入日志
             /*PurchaseParent purchaseParent;
             try {
                 String md5 = DigestUtils.md5Hex(file.getInputStream());//生成唯一标识
                 System.out.println("MD5 : "+md5);
                 lock.lock();
              *//*   PurchaseParent existOrder = purchaseParentService.getByMd5(md5);
                 System.out.println("空空空"+existOrder.getMd5());
                 //导入失败的可以重新导入，否则不可导入
                 if(existOrder != null && !existOrder.getState().equals(PurchaseParent.STATE_FAILED)){
                     if(existOrder.getState().equals(PurchaseParent.STATE_PROCESS)){
                         return ActionResult.fail("文件已导入并处理中,请等待处理完成.");
                     }else if(existOrder.getState().equals(PurchaseParent.STATE_SUCCESS)){
                         return ActionResult.fail("文件已导入并处理成功,请勿重复导入.");
                     }else if(existOrder.getState().equals(PurchaseParent.STATE_READY)){
                         return ActionResult.fail("文件正在导入中,请等待导入完成.");
                     }else{
                         return ActionResult.fail("文件已导入系统,请勿重复导入.");
                     }
                 }*//*
                 purchaseParent = purchaseParentService.create();
                 if (purchaseParent ==null || purchaseParent.getId() ==null){
                     log.error(this.getClass().getName()+"生成导入日志失败");
                     return ActionResult.fail("服务器异常,请联系管理员");
                 }
                 purchaseParent.setMd5(md5);//设置唯一标识
                 purchaseParent.setFile_name(file.getOriginalFilename());//设置文件名
                *//* purchaseParent.setUser(userName);//设置导入的用户*//*
                 purchaseParentService.save(purchaseParent);
             } catch (Exception e) {
                 e.printStackTrace();
                 log.info(this.getClass().getName()+"doImportAccount()->导入数据出错:"+e.getMessage());
                 return ActionResult.fail("导入失败,请联系管理员1.");
             }finally {
                 lock.unlock();//释放重锁
             }*/
             //4. 处理Excel行数据
             /*FutureTask task = new FutureTask<>(new ExcelPurchaseBillHandleTask(results,purchaseParent.getId()));
             ExecutorManage.submit(task);*/
             extractTextFromXLSService.savePurchaseBill(results);//保存到数据库中

             //5.异常处理
         }catch (ValidationError e) {
             e.printStackTrace();
             log.info(this.getClass().getName() + "daoExcel-->->导入数据出错1:" + e.getMessage());
             return ActionResult.fail(e.getMessage());
         } catch (IOException e) {
             e.printStackTrace();
             log.info(this.getClass().getName() + "daoExcel-->->导入数据出错2:" + e.getMessage());
             return ActionResult.fail("导入失败,请重新导入");
         }catch (Exception e){
             e.printStackTrace();
             log.info(this.getClass().getName() + "daoExcel-->->导入数据出错3:" + e.getMessage());
             return ActionResult.fail("导入失败,请联系管理员2");
         }finally {
             if (in !=null){
                 try {
                     in.close();
                 } catch (IOException e) {
                     e.printStackTrace();
                 }
             }
         }

         return ActionResult.ok();
   }


}