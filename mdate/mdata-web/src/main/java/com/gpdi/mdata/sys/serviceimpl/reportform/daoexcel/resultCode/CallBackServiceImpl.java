package com.gpdi.mdata.sys.serviceimpl.reportform.daoexcel.resultCode;


import com.gpdi.mdata.sys.dao.report.ResultCodeDao;
import com.gpdi.mdata.sys.entity.report.PurchaseContract;
import com.gpdi.mdata.sys.entity.report.ResultCode;
import com.gpdi.mdata.sys.entity.report.TestDemos;
import com.gpdi.mdata.sys.service.reportform.daoexcel.resultCode.CallBackService;
import com.gpdi.mdata.web.reportform.daoexcel.purchase.action.ShowAction;
import com.gpdi.mdata.web.utils.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pub.dao.GeneralDao;
import pub.dao.GeneralDao2;
import pub.dao.jdbc.dialect.mysql.PagedQuery;
import pub.dao.query.PageSettings;
import pub.dao.query.Query;
import pub.dao.query.QueryResult;
import pub.types.Data;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 根据采购结果编号获取数据
 */
@Service
@Transactional(readOnly = false)  //该注解表示该类里面的所有方法或者这个方法的事务由spring处理,来保证事务的原子性
public class CallBackServiceImpl implements CallBackService {
    private static final Logger logger = LoggerFactory.getLogger(CallBackServiceImpl.class);

    @Autowired
    private GeneralDao2 generalDao2;
    @Autowired
    private GeneralDao generalDao;
    @Autowired
    private ResultCodeDao resultCodeDao;

    @Override
    public List<ResultCode> getCompanys(String code) {

        // String sql = "SELECT * FROM i_caigou WHERE cgbh='"+code+"'";
        String sql = "SELECT * FROM i_caigou WHERE id='" + code + "'";
        List<ResultCode> allList = null;
        try {
            allList = generalDao2.queryList(ResultCode.class, sql);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("模板二从中间表查询数据失败或为查询到数据");
        }
        return allList;
    }

    /**
     * 保存候选供应商
     * @param list
     * @return
     */
    @Override
    @Transactional
    public boolean saveRsult(List<ResultCode> list) {
        if (list != null && list.size() > 0) {
            for (ResultCode resultCode : list) {
                String cgbh = resultCode.getCgbh();
                String gcmc = resultCode.getGcmc();
                String xmlx = resultCode.getXmlx();
                String gzrq = resultCode.getGzrq();
                String cgsqlx = resultCode.getCgsqlx();
                String yxgysmc = resultCode.getYxgysmc();
                String gysbm = resultCode.getGysbm();
                String cgjgbm = resultCode.getCgjgbm();
                String gcbm = resultCode.getGcbm();
                String cggsze = resultCode.getCggsze();
                String shxx = resultCode.getShxx();
                Date date = resultCode.getDate();
                String branch = resultCode.getBranch();
                String account = resultCode.getAccount();
                String service_tem = resultCode.getService_tem();
                //将意向供应商拆分出来
                if (yxgysmc != null && !yxgysmc.equals("")) {
                    String[] str = yxgysmc.split("\\｜");
                    for (int i = 0; i < str.length; i++) {
                        String[] aa = str[i].split("\\,");
                        String companyName = aa[0];
                        String adres = aa[1];
                        //===========保存意向供应商============
                        String sql2 = "insert into t_candidater(pur_plan_code,pur_result_code,company_name,adress) VALUES ('" + cgbh + "','" + cgjgbm + "','" + companyName + "','" + adres + "')";
                        if (companyName !=null && !companyName.equals("") && companyName.length()>4){
                            //插入外网机器人公司名称
                            String sql3 = "insert into t_supplier_in(company_name) VALUES ('" + companyName + "') ";
                            generalDao.execute(sql3);//插入成功,返回1
                        }

                        try {
                            generalDao.execute(sql2);//插入成功,返回1
                        } catch (Exception e) {
                            e.printStackTrace();
                            logger.error("保存候选供应商数据失败");
                        }
                    }
                }
                //============取出审批人==============
                if (shxx != null && !shxx.equals("")) {
                    String[] resullers = shxx.split("\\|");
                    List<String>  rell = new ArrayList<>();
                    if (resullers != null && resullers.length>0 && !resullers[0].equals("｜")) {
                        for (int b=0;b<resullers.length;b++){
                            String appr= resullers[b];
                            if (appr !=null && !appr.equals("")){
                                String[] resull = appr.split("#");
                                if (resull !=null && resull.length>0 ){
                                    for (int p=0;p<resull.length;p++){
                                        String pop = resull[p];
                                        if (pop !=null && !pop.equals("")){
                                            rell.add(pop);
                                        }
                                    }
                                }
                            }
                        }
                        //对list去重
                        if (rell !=null && rell.size()>0){
                            HashSet ha = new HashSet(rell);
                            rell.clear();
                            rell.addAll(ha);
                            for (int k = 0; k < rell.size(); k++) {
                                String approvers = rell.get(k);
                                if (approvers !=null && !approvers.equals("") && approvers.length()<5){
                                    String sqll3 = "insert into t_candidater_option(canditater_result_code,approver) values ('" + cgjgbm + "','" + approvers + "')";
                                    generalDao.execute(sqll3);
                                }
                            }

                        }

                    }

                }

                //==============将机器人取到的数据保存到项目上去=============
                String sql = "insert into t_result_code(cgbh,gcmc,xmlx,gzrq,cgsqlx,yxgysmc,gysbm,cgjgbm,gcbm,cggsze,shxx,date,branch,account,service_tem) VALUES ('" + cgbh + "','" + gcmc + "','" + xmlx + "','" + gzrq + "','" + cgsqlx + "','" + yxgysmc + "','" + gysbm + "','" + cgjgbm + "','" + gcbm + "','" + cggsze + "','"+ shxx +"','" + date + "','" + branch + "','" + account + "','" + service_tem + "')";
                try {
                    int res = generalDao.execute(sql);//插入成功,返回1
                    if (res == 1) {
                        logger.info("保存中间表数据成功");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    logger.error("保存中间表数据失败");
                }
            }
            return true;
        } else {
            return false;
        }

    }

    /**
     * 保存领导审批人
     * @return
     */
    @Override
    @Transactional
    public  boolean saver(){
        String sql = "SELECT DISTINCT cgjgbm,shxx FROM i_caigou WHERE shxx != ''";
        List<TestDemos> demo5s = generalDao.queryList(TestDemos.class,sql);
        int res = 0;
        if (demo5s !=null && demo5s.size()>0){
            for (TestDemos de:demo5s){
                String cgjgbm = de.getCgjgbm();
                String shxx = de.getShxx();

                if (shxx != null && !shxx.equals("")) {
                  String[] resullers = shxx.split("\\|");
                    List<String>  rell = new ArrayList<>();
                   /* if (resullers !=null && !resullers.equals("")){
                        for (int o=0;o<resullers.length;o++){
                            String ing = resullers[o];
                            if (ing !=null && !ing.equals("")){
                                List<String>  result = StringUtils.getStr(ing);
                                if (result !=null && result.size()>0){
                                    for (int f =0;f<result.size();f++){
                                        String approvers = result.get(f);
                                        if (approvers !=null && !approvers.equals("") && approvers.length()<5){
                                            String sqll3 = "insert into t_candidater_option(canditater_result_code,approver) values ('" + cgjgbm + "','" + approvers + "')";
                                            generalDao.execute(sqll3);
                                        }
                                    }
                                }
                            }
                        }

                    }*/
                    if (resullers != null && resullers.length>0 && !resullers[0].equals("｜")) {
                        for (int b=0;b<resullers.length;b++){
                            String appr= resullers[b];
                            if (appr !=null && !appr.equals("")){
                                String[] resull = appr.split("#");
                                if (resull !=null && resull.length>0 ){
                                    for (int p=0;p<resull.length;p++){
                                        String pop = resull[p];
                                        if (pop !=null && !pop.equals("")){
                                            rell.add(pop);
                                        }
                                    }
                                }
                            }
                        }
                        //对list去重
                        if (rell !=null && rell.size()>0){
                            HashSet ha = new HashSet(rell);
                            rell.clear();
                            rell.addAll(ha);
                            for (int k = 0; k < rell.size(); k++) {
                                String approvers = rell.get(k);
                                if (approvers !=null && !approvers.equals("") && approvers.length()<5){
                                    String sqll3 = "insert into t_candidater_option(canditater_result_code,approver) values ('" + cgjgbm + "','" + approvers + "')";
                                    generalDao.execute(sqll3);
                                }
                            }

                        }

                    }

                }
            }
        }
        return true;
    }



}
