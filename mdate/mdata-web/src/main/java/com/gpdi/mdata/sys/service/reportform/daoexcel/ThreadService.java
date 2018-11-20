package com.gpdi.mdata.sys.service.reportform.daoexcel;

import com.gpdi.mdata.sys.entity.report.PurchaseContract;
import com.gpdi.mdata.sys.service.reportform.form.RepPurchaseService;
import com.gpdi.mdata.web.reportform.daoexcel.updata.action.OperateAction;
import com.gpdi.mdata.web.reportform.thread.OutThread;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pub.dao.GeneralDao2;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * @description:
 * @author: WangXiaoGang
 * @data: Created in 2018/7/30 6:39
 * @modifier:
 */
@Service
@Transactional(readOnly = true)
public class ThreadService {

    protected static final Logger log = Logger.getLogger(OperateAction.class);
    @Autowired
    private GeneralDao2 generalDao2;
    @Autowired
    private RepPurchaseService repPurchaseService;
    @Transactional
    public  boolean insertData(String resultCode) {
        try {
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String curreTime = df.format((System.currentTimeMillis()));
            System.out.println("系统当前时间:"+curreTime);
            String sql = "insert into i_caigou (cgjgbm,date,branch,account,service_tem) values ('"+resultCode+"','"+curreTime+"','广东省','admin','采购取数')";
            int ss = generalDao2.execute(sql);//插入成功,返回1
            System.out.println("返回的数据是什么:"+ss);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            log.error("插入机器人中间表失败!");
        }
        return false;
    }

    public  void test() {
        List<PurchaseContract> list = new ArrayList<>();
        list =repPurchaseService.getResultCode();
       for (PurchaseContract pur:list){
           String resultCode = pur.getPurchaseResultCode();

           try {
               boolean sta = insertData(resultCode);
               System.out.println("取数状态:"+sta);
           } catch (Exception e) {
               e.printStackTrace();
           }
       }

    }

    public static void main(String[] args) {
        String ss = "【2015】GD43031号";
        ThreadService threadService = new ThreadService();
        try {
            threadService.insertData(ss);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
