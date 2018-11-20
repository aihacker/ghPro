package com.gpdi.mdata.sys.serviceimpl.reportform.mainriskexclute;

import com.gpdi.mdata.sys.dao.report.DismantDao;
import com.gpdi.mdata.sys.entity.report.CompareScore;
import com.gpdi.mdata.sys.entity.report.ContractType;
import com.gpdi.mdata.sys.entity.report.DismantTemp;
import com.gpdi.mdata.sys.entity.report.PurchaseContract;
import com.gpdi.mdata.sys.service.reportform.mainriskexclude.DismantlingService;
import com.gpdi.mdata.web.reportform.data.QueryData;
import com.gpdi.mdata.web.utils.CompareStrUtil;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pub.dao.GeneralDao;
import pub.dao.jdbc.dialect.mysql.PagedQuery;
import pub.dao.query.Query;
import pub.dao.query.QueryResult;
import pub.dao.query.QuerySettings;
import pub.functions.StrFuncs;

import java.lang.reflect.Array;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import static com.gpdi.mdata.sys.utils.JsonUtil.toArray;
import static jdk.nashorn.internal.objects.NativeString.trim;

/**
 * @author: WangXiaoGang
 * @data: Created in 2018/8/23 11:32
 * @description:拆单可能性排查
 */
/**
 * @Transactional(readOnly = false) 的作用
 * jdbc事务控制,readOnly=ture:表明所注解的方法或类只能读写,不能做更改
 * ready=false:表明所注解的方法是增加删除或修改
 */
@Service
@Transactional(readOnly = false)
public class DismantlingServiceImpl implements DismantlingService {

    @Autowired
    GeneralDao generalDao;
    @Autowired
    DismantDao dismantDao;

    @Override
    public QueryResult queryDismantMaybe(QueryData queryData, QuerySettings settings) {
        Query query = new PagedQuery<>(settings);
        String name = queryData.getName();
        String where = "";
        if (StrFuncs.notEmpty(name)) {//判断不为空其长度大于0
            where += "contract_name like :name";
            query.put("name",StrFuncs.anyLike(name));
        }
        query.select("contract_name,supplier_name,contract_type ,purchase_way,purchase_time").from("t_purchase_contract").where(where).orderBy("contract_name,supplier_name");
        generalDao.execute(query);
        QueryResult queryResult = query.getResult();
        return queryResult;
    }

    @Transactional
    public List<DismantTemp> queryDismant(QueryData queryData, QuerySettings settings) {
        //直接将日期转换为数字进行计算
        //"\tGROUP_CONCAT(DATEDIFF(purchase_time,now())+365 SEPARATOR ';') AS contractTime\n" +

        //获取开始时间和结束时间
        String startTime = queryData.getStartTime();
        String endTime = queryData.getEndTime();
        String contractType = queryData.getType();//获取合同类型
        String department = queryData.getDept();//获取部门
        String supperName = queryData.getSupperName();//获取供应商名称
        String str1 = queryData.getStr1();//获取年月字样
        String str2 = queryData.getStr2();//获取第几期字样
        String str3 = queryData.getStr3();//获取第几批字样
        String str4 = queryData.getStr4();//获取业务代理字样
        String purchaseWays=queryData.getPurchaseWay();

        //直接将日期转换为数字进行计算
        String where =" undertake_dept IS NOT NULL AND undertake_dept != '' AND contract_name NOT LIKE  '%变更合同%'  " +
                "AND contract_name NOT LIKE  '%变更合同%'\n" +
                "AND contract_name NOT LIKE  '%原合同为%'\n" +
                "AND contract_name NOT LIKE  '%(新)%' " ;

        //有时间的情况
        if (StrFuncs.notEmpty(startTime)  && StrFuncs.notEmpty(endTime)) {
            where += " AND purchase_time BETWEEN '"+startTime+"' AND '"+endTime+"' ";

        }else{
            where += " AND purchase_time BETWEEN '2017-01-01' AND '2017-12-30' ";
        }
        if (str1 !=null && str1.equals("1")){
            where += " AND contract_name NOT LIKE '%年%月%' ";
        }
        if (str2 !=null && str2.equals("2")){
            where += " AND contract_name NOT LIKE '%期%' ";
        }
        if (str3 !=null && str3.equals("3")){
            where += " AND contract_name NOT LIKE '%第%批%' ";
        }
        if (str4 !=null && str4.equals("4")){
            where += " AND contract_type_name != '业务代理(含农村统包)' ";
        }
        if (contractType !=null && !contractType.equals("") && !contractType.equals("01")){
            where += "AND contract_type_name = '"+contractType+"'";
        }
        if (supperName !=null && !supperName.equals("")){
            where += "AND supplier_name = '"+supperName+"'";
        }
        if (department !=null && !department.equals("") && !department.equals("01")){
            where += "AND undertake_dept = '"+department+"'";
        }
        if(purchaseWays !=null && !purchaseWays.equals("") && !purchaseWays.equals("01")){
            where +="AND purchase_way = '"+purchaseWays+"'";
        }


        String  sql = "SELECT\n" +
                   "\tundertake_dept,\n" +
                   "\tsupplier_name,\n" +
                   "\tcontract_type_name,\n" +
                   "\tCOUNT(undertake_dept) AS count,\n" +
                   "\tGROUP_CONCAT(contract_name SEPARATOR ';') AS contractName,\n" +
                   "\tGROUP_CONCAT(purchase_time SEPARATOR ';') AS contractTime,\n" +
                   "\tGROUP_CONCAT(contract_amount SEPARATOR ';') AS contractAmount,\n" +
                   "\tGROUP_CONCAT(purchase_way SEPARATOR ';') AS purchaseWay\n" +
                   "FROM\n" +
                   "\tt_purchase_contract\n" +
                   "WHERE "+where+" \n" +
                   "GROUP BY\n" +
                   "\tundertake_dept,\n" +
                   "\tsupplier_name,\n" +
                   "\tcontract_type_name\n" +
                   "HAVING \n" +
                   "\tCOUNT(undertake_dept)>1";


    List<DismantTemp> list = generalDao.queryList(DismantTemp.class, sql);
    List<DismantTemp> list2 = new ArrayList<>();
    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
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
                        if (ti<180) {
                            total += res;
                            n++;
                        la.add(con[j]);
                        }
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                    }

                }
                if(n !=0){
                    la.add(con[i]);
                    avg = total/n;
                    BigDecimal bg = new BigDecimal(avg).setScale(2, RoundingMode.UP);//保留2位小数
                    avg=bg.doubleValue();//获取每组权重
                    dia.setScore(avg);
                    Collections.sort(la);
                    dia.setColect(la);
                    dia.setContractTypeName(dismantTemp.getContractTypeName());
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
            List<String>  purchaseWay  =new ArrayList<>();
            List<String>  contractCode =new ArrayList<>();
            List<String>  purPlanCode =new ArrayList<>();
           if (lis !=null && lis.size()>0){
               for (int k=0;k<lis.size();k++){
                   String str = lis.get(k);
                   if (str !=null){
                       String sql2 = "SELECT DISTINCT contract_code,purchase_time,contract_amount,purchase_way,pur_plan_code FROM t_purchase_contract pu LEFT JOIN t_candidater ca ON pu.purchase_result_code = ca.pur_result_code WHERE pu.contract_name = '"+str+"' GROUP BY pu.contract_name";
                       List<CompareScore> am = generalDao.queryList(CompareScore.class,sql2);
                       if (am !=null && am.size()>0){
                           for (CompareScore pur:am){
                                purchaseTime.add(pur.getPurchaseTime());
                                purchaseAmount.add(pur.getContractAmount());
                                purchaseWay.add(pur.getPurchaseWay());
                                contractCode.add(pur.getContractCode());
                               String cod = pur.getPurPlanCode();
                               if (cod ==null){ cod=""; }
                                purPlanCode.add(cod);
                           }
                       }
                   }
               }
           }
           dTemp.setPurTime(purchaseTime);
           dTemp.setPurWay(purchaseWay);
           dTemp.setPurMoney(purchaseAmount);
           dTemp.setPurCode(contractCode);
           dTemp.setPurPlanCode(purPlanCode);

        }


        List<DismantTemp> dt = new ArrayList<>();
        DismantTemp[] cp;
        if (list2 !=null && list2.size()>0) {
            cp = list2.toArray(new DismantTemp[list2.size()]);
            java.util.Arrays.sort(cp);
            dt = Arrays.asList(cp);
        }

    return dt;
}

    @Override
    public String typeformTree() {
        String sql = "SELECT * FROM t_contract_type";
        List<ContractType> contractTypes = generalDao.queryList(ContractType.class,sql);
        if (contractTypes !=null && contractTypes.size()>0){
            JSONArray jsonArray = new JSONArray();
            for (ContractType con:contractTypes){
                JSONObject object = new JSONObject();
                object.put("id",con.getId());
                object.put("pId",con.getId());
                object.put("name",con.getTypeName());
                object.put("code",con.getTypeCode());
                object.put("checked", false);
                object.put("open", true);
                jsonArray.add(object);
            }
            return toArray(jsonArray);
        }
        return null;
    }
}


