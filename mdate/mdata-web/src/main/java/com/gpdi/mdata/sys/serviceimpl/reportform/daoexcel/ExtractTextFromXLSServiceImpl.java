package com.gpdi.mdata.sys.serviceimpl.reportform.daoexcel;

import com.gpdi.mdata.sys.dao.report.*;
import com.gpdi.mdata.sys.entity.report.CleanInventory;
import com.gpdi.mdata.sys.entity.report.MachineBill;
import com.gpdi.mdata.sys.entity.report.MachineTitleBill;
import com.gpdi.mdata.sys.entity.report.PurchaseContract;
import com.gpdi.mdata.sys.service.reportform.daoexcel.ExtractTextFromXLSService;
import com.gpdi.mdata.sys.service.reportform.daoexcel.PurchaseParentService;
import com.gpdi.mdata.web.reportform.data.QueryData;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pub.dao.GeneralDao;
import pub.dao.GeneralDao2;
import pub.dao.jdbc.dialect.mysql.PagedQuery;
import pub.dao.query.PageSettings;
import pub.dao.query.Query;
import pub.dao.query.QueryResult;

import java.text.SimpleDateFormat;
import java.util.List;

/**
 * @description:
 * @author: WangXiaoGang
 * @data: Created in 2018/6/22 11:17
 * @modifier:
 */
@Service
@Transactional(readOnly = false)  //该注解表示该类里面的所有方法或者这个方法的事务由spring处理,来保证事务的原子性
public class ExtractTextFromXLSServiceImpl implements ExtractTextFromXLSService {
    protected static final Logger log = Logger.getLogger(ExtractTextFromXLSServiceImpl.class);
    @Autowired
    private GeneralDao generalDao;
    @Autowired
    private GeneralDao2 generalDao2;

    @Autowired
    private PurchaseDao purchaseDao;

    @Autowired
    private CleanDao cleanDao;

@Autowired
PurchaseParentService purchaseParentService;
    @Autowired
    private PurchaseParentDao orderParentDao;

    @Autowired
    private PurchaseBillDao purchaseBillDao;
    @Autowired
    private PurchaseTitleBillDao purchaseTitleBillDao;


    //保存导入后的采购合同表
    @Override
    public boolean savePurchaseContract(List<PurchaseContract> list) {
        for (PurchaseContract purchaseContract : list) {
            purchaseDao.save(purchaseContract);
        }
        return true;
    }
    //保存导入后的及时清洁数据
    @Override
    public boolean saveCleanlneventory(List<CleanInventory> list) {
        boolean result = purchaseParentService.saveCleanlneventory(list);
        return result;
    }

    @Override
    public String getFileName(String name) {
        return orderParentDao.getFileName(name);
    }

    @Override
    public QueryResult queryPurchaseNote(QueryData queryData, PageSettings settings) {
        Query query = new PagedQuery<>(settings);
        query.select("create_date,user,file_name,remark").from("t_purchase_parent").where("type=0").orderBy("create_date ASC");
        generalDao.execute(query);
        QueryResult queryResult = query.getResult();
        return queryResult;
    }

    @Override
    public QueryResult queryAccountNote(QueryData queryData, PageSettings settings) {
        Query query = new PagedQuery<>(settings);
        query.select("create_date,user,file_name,remark").from("t_purchase_parent").where("type=1").orderBy("create_date ASC");
        generalDao.execute(query);
        QueryResult queryResult = query.getResult();
        return queryResult;
    }

    @Override
    public QueryResult queryKinsfolkNote(QueryData queryData, PageSettings settings) {
        Query query = new PagedQuery<>(settings);
        query.select("create_date,user,file_name,remark").from("t_purchase_parent").where("type=2").orderBy("create_date DESC");
        generalDao.execute(query);
        QueryResult queryResult = query.getResult();
        return queryResult;
    }

    @Override
    public QueryResult queryBidCompanyNote(QueryData queryData, PageSettings settings) {
        Query query = new PagedQuery<>(settings);
        query.select("create_date,user,file_name,remark").from("t_purchase_parent").where("type=3").orderBy("create_date DESC");
        generalDao.execute(query);
        QueryResult queryResult = query.getResult();
        return queryResult;
    }

    @Override
    public void save(String startTime, String endTime) {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String curreTime = df.format((System.currentTimeMillis()));
        System.out.println("系统当前时间:"+curreTime);
        String sql = "insert into i_daochushuju (begin,end,reqi,jieshu,date,branch,account,service_tem) values ('"+startTime+"','"+endTime+"','"+startTime+"','"+endTime+"','"+curreTime+"','合同取数','admin','导出数据')";
        int ss = generalDao2.execute(sql);//插入成功,返回1
        System.out.println("返回的数据是什么:"+ss);
    }

    @Override
    public boolean insertData(String resultCode) {
        System.out.println("获取的采购结果单号"+resultCode);
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String curreTime = df.format((System.currentTimeMillis()));
        System.out.println("系统当前时间:"+curreTime);
        String sql = "insert into i_caigou (cgjgbm,date,branch,account,service_tem) values ('"+resultCode+"','"+curreTime+"','广东省','admin','采购取数')";
        int res = 0;//插入成功,返回1
        try {
            System.out.println("generalDao2是多少:"+generalDao2);
            res = generalDao2.execute(sql);
            if (res==1){
                log.info("插入模板二数据成功");
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
            log.error("插入模板二数据失败");
        }
        return false;
    }
//=========================查看IC加油站台账数据==========================
    @Override
    public QueryResult queryICCard(QueryData queryData, PageSettings settings) {
       Query query = new PagedQuery(settings);
       query.select("a.*,b.customer_name,b.customer_code,b.website_name,b.bill_type,b.startstop_time,b.apply_type,b.bill_operator,b.print_date ").from("t_iccard_machine_bill AS a LEFT JOIN t_iccard_title_bill AS b ON a.title_id = b.title_id");
       generalDao.execute(query);
       QueryResult queryResult = query.getResult();
        return queryResult;
    }


 //======================保存导入后ic加油站数据==================
    @Override
    public boolean savePurchaseBill(List<MachineBill> list) {
        for (MachineBill purchaseContract:list){
            purchaseBillDao.save(purchaseContract);
        }

        return true;
    }
    //保存导入后ic加油站标题数据
    @Override
    public boolean savePurchaseTitleBill(List<MachineTitleBill> list) {
        for (MachineTitleBill purchaseContract:list){
            purchaseTitleBillDao.save(purchaseContract);
        }
        return true;
    }


}
