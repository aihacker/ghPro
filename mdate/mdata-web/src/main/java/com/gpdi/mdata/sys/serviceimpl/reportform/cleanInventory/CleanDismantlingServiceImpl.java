package com.gpdi.mdata.sys.serviceimpl.reportform.cleanInventory;

import com.gpdi.mdata.sys.dao.report.DismantDao;
import com.gpdi.mdata.sys.entity.report.CompareScore;
import com.gpdi.mdata.sys.entity.report.DismantTemp;
import com.gpdi.mdata.sys.service.reportform.cleanInventory.CleanDismantlingService;
import com.gpdi.mdata.sys.service.reportform.mainriskexclude.DismantlingService;
import com.gpdi.mdata.web.reportform.data.QueryData;
import com.gpdi.mdata.web.utils.CompareStrUtil;
import com.huaban.analysis.jieba.JiebaSegmenter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pub.dao.GeneralDao;
import pub.dao.jdbc.dialect.mysql.PagedQuery;
import pub.dao.query.Query;
import pub.dao.query.QueryResult;
import pub.dao.query.QuerySettings;
import pub.functions.StrFuncs;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author: WangXiaoGang
 * @data: Created in 2018/9/9 5:57
 * @description:即时清结拆单
 */

/**
 * @Transactional(readOnly = false) 的作用
 * jdbc事务控制,readOnly=ture:表明所注解的方法或类只能读写,不能做更改
 * ready=false:表明所注解的方法是增加删除或修改
 */
@Service
@Transactional(readOnly = false)
public class CleanDismantlingServiceImpl implements CleanDismantlingService {

    @Autowired
    GeneralDao generalDao;
    @Autowired
    DismantDao dismantDao;



    @Transactional
    public List<DismantTemp> queryDismant(QueryData queryData, QuerySettings settings) {
        //获取开始时间和结束时间
        String startTime = queryData.getStartTime();
        String endTime = queryData.getEndTime();
        String contractType = queryData.getType();//获取物料名称
        String supperName = queryData.getSupperName();//获取供应商名称
        String department = queryData.getDept();//获取经办部门
        String str1 = queryData.getStr1();//获取年月字样
        String str2 = queryData.getStr2();//获取第几期字样
        String str3 = queryData.getStr3();//获取第几批字样

        //直接将日期转换为数字进行计算
        String where =" undertake_dept IS NOT NULL AND undertake_dept != '' ";
        //有时间的情况
        if (StrFuncs.notEmpty(startTime)  && StrFuncs.notEmpty(endTime)) {
            where += " AND purchase_time BETWEEN '"+startTime+"' AND '"+endTime+"' ";

        }else{
            where += " AND purchase_time BETWEEN '2017/1/1' AND '2017/12/30' ";
        }
        if (str1 !=null && str1.equals("1")){
            where += " AND contract_name NOT LIKE '%年%月%' ";
        }
        if (str2 !=null && str2.equals("2")){
            where += " AND contract_name NOT LIKE '%第%期%' ";
        }
        if (str3 !=null && str3.equals("3")){
            where += " AND contract_name NOT LIKE '%第%批%' ";
        }

        if (contractType !=null && !contractType.equals("") && !contractType.equals("01")){
            where += "AND material_name = '"+contractType+"'";
        }
        if (supperName !=null && !supperName.equals("")){
            where += "AND supplier_name = '"+supperName+"'";
        }

        if (department !=null && !department.equals("") && !department.equals("01")){
            where += "AND undertake_dept = '"+department+"'";
        }

        String sql = "SELECT \n" +
                "     undertake_dept,    \n" +
                "     supplier_name,    \n" +
                "     COUNT(undertake_dept) AS count,    \n" +
                "     GROUP_CONCAT(contract_name SEPARATOR ';') AS contractName,    \n" +
                "     GROUP_CONCAT(purchase_time SEPARATOR ';') AS contractTime,    \n" +
                "     GROUP_CONCAT(contract_amount SEPARATOR ';') AS contractAmount,      \n" +
                "     GROUP_CONCAT(undertake_man SEPARATOR ';') AS undertakeMan      \n" +
                "FROM    \n" +
                "     t_clean_list_immediately_rel    \n" +
                "WHERE  "+where+"  \n" +
                "GROUP BY    \n" +
                "     undertake_dept,    \n" +
                "     supplier_name   \n" +
                "HAVING     \n" +
                "     COUNT(undertake_dept)>1\n" +
                "ORDER BY\n" +
                "\t\tpurchase_time";
    List<DismantTemp> list = generalDao.queryList(DismantTemp.class, sql);
    List<DismantTemp> list2 = new ArrayList<>();
    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
    double num = 0.8;
    String code = queryData.getCode();
    if(code !=null && code.length()>0){ num= Double.parseDouble(code); }
    if (list != null && list.size() > 0) {
        for (DismantTemp dismantTemp : list) {
            String contract = dismantTemp.getContractName();
            String contractTime = dismantTemp.getContractTime();
            String[] con = contract.split("\\;");
            String[] tim = contractTime.split("\\;");

            for (int i = 0; i < con.length; i++) {
                DismantTemp dia = new DismantTemp();
                List<String> la = new ArrayList<>();
                double total = 0;
                double avg = 0;
                int n=0;
                for (int j = i + 1; j < con.length; j++) {
                    double res =CompareStrUtil.SimilarDegree(con[i], con[j]);
                    if(res>=num && res<1) {
                        try {
                            Date date1 = dateFormat.parse(tim[i]);
                            Date date2 = dateFormat.parse(tim[j]);
                            long ti =Math.abs((date1.getTime()-date2.getTime())/(24*3600*1000));//将时间转换为天数相减得出相隔天数
                        if (ti<360) {
                            total += res;
                            n++;
                           /* String fc = con[j];
                            JiebaSegmenter segmenter = new JiebaSegmenter();
                            fc = String.valueOf(segmenter.sentenceProcess(fc));
                            la.add(fc);*/
                        la.add(con[j]);
                        }
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                    }

                }
                if(n !=0){
                    /*String fc2 = con[i];
                    JiebaSegmenter segmenter = new JiebaSegmenter();
                    fc2 = String.valueOf(segmenter.sentenceProcess(fc2));
                    la.add(fc2);*/
                    la.add(con[i]);
                    avg = total/n;
                    BigDecimal bg = new BigDecimal(avg).setScale(2, RoundingMode.UP);//保留2位小数
                    avg=bg.doubleValue();//获取每组权重
                    dia.setScore(avg);
                    Collections.sort(la);
                    dia.setColect(la);
                    dia.setSupplierName(dismantTemp.getSupplierName());
                    dia.setUndertakeDept(dismantTemp.getUndertakeDept());
                    list2.add(dia);
                }

            }

        }
    }
    /*=================帅选排除子集合,去重===============*/
        for (int i=0;i<list2.size();i++){
            for (int j=i+1;j<list2.size();j++){
               List<String> stre1 = list2.get(i).getColect();
               List<String> stre2 = list2.get(j).getColect();
                boolean flag = list2.get(i).getColect().containsAll(list2.get(j).getColect());
                boolean flag2 = list2.get(j).getColect().containsAll(list2.get(i).getColect());
                if (flag){
                    list2.remove(j);
                    j--;
                }else if (flag2){
                    list2.remove(i);
                    i--;
                    continue;
                }
            }
        }
        for (DismantTemp dTemp:list2){
            List<String> lis = dTemp.getColect();
            List<String> purchaseTime = new ArrayList<>();
            List<String> purchaseAmount =new ArrayList<>();
            List<String>  contractCode =new ArrayList<>();
            List<String>  undertakeMan =new ArrayList<>();
            List<String>  material =new ArrayList<>();
           if (lis !=null && lis.size()>0){
               for (int k=0;k<lis.size();k++){
                   String str = lis.get(k);
                   if (str !=null){
                       String sql2 = "SELECT DISTINCT contract_code ,purchase_time ,contract_amount,undertake_man,material_name FROM t_clean_list_immediately_rel  WHERE contract_name = '"+str+"' GROUP BY contract_name";
                       List<CompareScore> am = generalDao.queryList(CompareScore.class,sql2);
                       if (am !=null && am.size()>0){
                           for (CompareScore pur:am){
                                purchaseTime.add(pur.getPurchaseTime());
                                purchaseAmount.add(pur.getContractAmount());
                                contractCode.add(pur.getContractCode());
                                undertakeMan.add(pur.getUndertakeMan());
                                material.add(pur.getMaterialName());
                           }
                       }
                   }
               }
           }
           dTemp.setPurTime(purchaseTime);
           dTemp.setPurMoney(purchaseAmount);
           dTemp.setPurCode(contractCode);
           dTemp.setUndMan(undertakeMan);
           dTemp.setMaterial(material);

        }


        List<DismantTemp> dt = new ArrayList<>();
        DismantTemp[] cp;
        if (list2 !=null && list2.size()>0) {
            cp = list2.toArray(new DismantTemp[list2.size()]);
            Arrays.sort(cp);
            dt = Arrays.asList(cp);
        }

    return dt;
}
//获取所有的物料名称
    @Override
    public List<String> goodsType() {
        String sql ="SELECT DISTINCT material_name FROM t_clean_list_immediately_rel WHERE material_name IS NOT NULL AND material_name !=''";
        List<String> goods = generalDao.queryList(String.class,sql);
        return goods;
    }

    @Override
    public List<String> queryDept() {
        String sql ="SELECT DISTINCT undertake_dept FROM t_clean_list_immediately_rel WHERE undertake_dept IS NOT NULL AND undertake_dept !=''";
        List<String> undertake = generalDao.queryList(String.class,sql);
        return undertake;
    }
}


