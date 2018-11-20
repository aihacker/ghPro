package com.gpdi.mdata.sys.serviceimpl.reportform.cleanInventory;


import com.gpdi.mdata.sys.dao.report.CleanResultDao;
import com.gpdi.mdata.sys.dao.report.ResultCodeDao;
import com.gpdi.mdata.sys.entity.report.CleanResult;
import com.gpdi.mdata.sys.entity.report.ResultCode;
import com.gpdi.mdata.sys.service.reportform.cleanInventory.CleanCallBackService;
import com.gpdi.mdata.sys.service.reportform.daoexcel.resultCode.CallBackService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pub.dao.GeneralDao;
import pub.dao.GeneralDao2;

import java.util.Date;
import java.util.List;

/**
 *即时清结获取候选供应商
 */
@Service
@Transactional(readOnly = false)  //该注解表示该类里面的所有方法或者这个方法的事务由spring处理,来保证事务的原子性
public class CleanCallBackServiceImpl implements CleanCallBackService {
    private static final Logger logger = LoggerFactory.getLogger(CleanCallBackServiceImpl.class);

    @Autowired
    private GeneralDao2 generalDao2;
    @Autowired
    private GeneralDao generalDao;
    @Autowired
    private CleanResultDao cleanResultDao;

    @Override
    public List<CleanResult> getCompanys(int code) {

        // String sql = "SELECT * FROM i_caigou WHERE cgbh='"+code+"'";
        String sql = "SELECT * FROM i_jsqj_one WHERE id="+code;
        List<CleanResult> allList = null;
        try {
            allList = generalDao2.queryList(CleanResult.class,sql);
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("模板二从中间表查询数据失败或为查询到数据");
        }
        return allList;
    }

    @Override
    @Transactional
    public boolean saveRsult(List<CleanResult> list) {
        if (list !=null && list.size()>0) {

            for (CleanResult resultCode : list) {
                String contractCode = resultCode.getJsqj_num();
                String dtime = resultCode.getDtime();
                String providers = resultCode.getProviders();
                String pro_sum = resultCode.getPro_sum();
                String opinion = resultCode.getOpinion();
                String branch = resultCode.getBranch();
                String account = resultCode.getAccount();
                Date date = resultCode.getDate();
                String service_tem = resultCode.getService_tem();
                //将意向供应商拆分出来
                if (providers !=null && !providers.equals("")){
                    String[] str = providers.split("\\｜");
                    String[] mon = pro_sum.split("\\｜");
                      for (int i=0;i<str.length;i++){
                          String sql2 = "insert into t_clean_candidater(contract_code,company_name,amount) VALUES ('"+contractCode+"','"+str[i]+"','"+mon[i]+"')";
                          try {
                              generalDao.execute(sql2);//插入成功,返回1
                          } catch (Exception e) {
                              e.printStackTrace();
                              logger.error("保存候选供应商数据失败");
                          }
                      }
                      if (opinion !=null && opinion.length()>0 ){
                          String sql3 = "insert into t_clean_option(contract_code,options) VALUES ('"+contractCode+"','"+opinion+"')";
                          try {
                          generalDao.execute(sql3);
                          } catch (Exception e) {
                              e.printStackTrace();
                              logger.error("保存审批人信息错误");
                          }
                      }

                }

                String sql = "insert into t_clean_result(jsqj_num,dtime,providers,pro_sum,opinion,branch,account,date,service_tem) VALUES ('"+contractCode+"','"+dtime+"','"+providers+"','"+pro_sum+"','"+opinion+"','"+branch+"','"+account+"','"+date+"','"+service_tem+"')";
                try {
                    int res = generalDao.execute(sql);//插入成功,返回1
                    if (res==1){ logger.info("保存中间表数据成功");}
                } catch (Exception e) {
                    e.printStackTrace();
                    logger.error("保存中间表数据失败");
                }
            }
            return true;
        }else {
            return false;
        }

    }


}
