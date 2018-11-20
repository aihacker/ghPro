package com.gpdi.mdata.sys.serviceimpl.reportform.mainriskexclute;

import com.gpdi.mdata.sys.dao.report.RepPurchaseDao;
import com.gpdi.mdata.sys.entity.report.PurchaseContract;
import com.gpdi.mdata.sys.service.reportform.mainriskexclude.SingleSourceService;
import com.gpdi.mdata.web.reportform.data.QueryData;
import com.gpdi.mdata.web.utils.PercentUtil;
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

import java.text.SimpleDateFormat;
import java.util.List;

/**
 * 单一采购源查询
 */
@Service
@Transactional(readOnly = false) //@Transactional:事务管理控制,readOnly=false表明所注解的方法或类是增加,删除,修改数据
public class SingleSourceServiceImpl implements SingleSourceService {

    @Autowired
    GeneralDao generalDao;

    @Autowired
    RepPurchaseDao repPurchaseDao;
    @Override           //查询所有的采购方式
    public List<String> getAllPurchaseWay() {
        return repPurchaseDao.getAllPurchaseWay();
    }

    @Override
    public QueryResult queryProportion(QueryData queryData, QuerySettings settings) {
        Query query = new PagedQuery<>(settings);
        query.select("b.p3 as aa,b.p2 as bb,a.p1 as cc,b.p2/a.p1 as dd ,b.q2 as ee,a.q1 as ff,b.q2/a.q1 as gg").from("(SELECT COUNT(purchase_way) p1,SUM(contract_amount) q1 FROM t_purchase_contract) a ,(SELECT COUNT(purchase_way) p2 , purchase_way p3,SUM(contract_amount) q2  FROM t_purchase_contract WHERE purchase_way in ('公开招标','公开比选','邀请询价','单一来源采购（公示）','单一来源采购（非公示）')  GROUP BY p3) b").orderBy("FIELD(aa,'公开招标','公开比选','邀请询价','单一来源采购（公示）','单一来源采购（非公示）')");
        generalDao.execute(query);
        QueryResult queryResult = query.getResult();
        return queryResult;
    }



    @Override       //查询合同最多和金额最高的供应商
    public QueryResult queryConstructMoneyMostBySupplier(QueryData queryData, QuerySettings settings) {
        Query query = new PagedQuery<>(settings);//创建查询对象
        String startTime = queryData.getStartTime();
        String endTime = queryData.getEndTime();
       /* String way = queryData.getName();
        if(way == null || way.equals("01")){
            way="单一来源采购（公示）";
        }*/
       // query.select("supplier_name,count(supplier_name) as cc,SUM(contract_amount) as am,purchase_way").from("t_purchase_contract as tp").where("tp.purchase_way='" + way + "'").groupBy("supplier_name").orderBy("cc DESC,am DESC ");
        String where ="";
        if (StrFuncs.notEmpty(startTime)  && StrFuncs.notEmpty(endTime)){
            where += " and purchase_time between '"+startTime+"' and '"+endTime+"'";
            query.put("startTime",startTime);
            query.put("endTime",endTime);
        }
       /* query2.select("supplier_name,count(supplier_name) cc,SUM(contract_amount) dd")
                .from("t_purchase_contract")
                .where(where)
                .groupBy("supplier_name").orderBy("cc DESC ,dd DESC LIMIT " + num);*/


//刪了時間where
        query.select("supplier_name,count(supplier_name) as cc,SUM(contract_amount) as am,purchase_way").from("t_purchase_contract as tp").where("tp.purchase_way like '单一来源采购%'"+where+"").groupBy("supplier_name,purchase_way").orderBy("cc DESC,am DESC ");
        generalDao.execute(query);
        QueryResult queryResult = query.getResult();
        return queryResult;
    }


    @Override   //查询签订合同最多金额最高的经办部门
    public QueryResult queryConstructMoneyMostByDepart(QueryData queryData, QuerySettings settings) {
        Query query = new PagedQuery<>(settings);
        String startTime = queryData.getStartTime();
        String endTime = queryData.getEndTime();
       /* String way = queryData.getName();
        if(way == null || way.equals("01")){
            way="单一来源采购（公示）";
        }*/
        // query.select("supplier_name,count(supplier_name) as cc,SUM(contract_amount) as am,purchase_way").from("t_purchase_contract as tp").where("tp.purchase_way='" + way + "'").groupBy("supplier_name").orderBy("cc DESC,am DESC ");
        String where ="";
        if (StrFuncs.notEmpty(startTime)  && StrFuncs.notEmpty(endTime)){
            where += " and purchase_time between '"+startTime+"' and '"+endTime+"'";
            query.put("startTime",startTime);
            query.put("endTime",endTime);
        }
        query.select("undertake_dept,count(undertake_dept) as cc,SUM(contract_amount) as am,purchase_way").from("t_purchase_contract as tp").where("tp.purchase_way like '单一来源采购%' "+where+"").groupBy("undertake_dept,purchase_way").orderBy("cc DESC,am DESC ");
        generalDao.execute(query);
        QueryResult queryResult =query.getResult();
        return queryResult;
    }

    @Override   //查询签订合同最多金额最高的合同类型
    public QueryResult queryConstructMoneyMostByType(QueryData queryData, QuerySettings settings) {
        Query query = new PagedQuery<>(settings);
        String way = queryData.getName();
        String startTime = queryData.getStartTime();
        String endTime = queryData.getEndTime();
       /* String way = queryData.getName();
        if(way == null || way.equals("01")){
            way="单一来源采购（公示）";
        }*/
        // query.select("supplier_name,count(supplier_name) as cc,SUM(contract_amount) as am,purchase_way").from("t_purchase_contract as tp").where("tp.purchase_way='" + way + "'").groupBy("supplier_name").orderBy("cc DESC,am DESC ");
        String where ="";
        if (StrFuncs.notEmpty(startTime)  && StrFuncs.notEmpty(endTime)){
            where += " and purchase_time between '"+startTime+"' and '"+endTime+"'";
            query.put("startTime",startTime);
            query.put("endTime",endTime);
        }
        query.select("contract_type,count(contract_type) as cc,SUM(contract_amount) as am,purchase_way").from("t_purchase_contract as tp").where("tp.purchase_way like '单一来源采购%'"+where+"").groupBy("contract_type,purchase_way").orderBy("cc DESC,am DESC ");
        generalDao.execute(query);
        QueryResult queryResult =query.getResult();
        return queryResult;
    }

    @Override   //查询签订合同金额最高的前10个项目
    public QueryResult queryConstructMoneyMax(QueryData queryData, QuerySettings settings) {
        Query query = new PagedQuery<>(settings);
        String way = queryData.getName();
        if(way==null || way.equals("01") || way.equals("02")){
            query.select("contract_name,contract_amount,undertake_dept,supplier_name,purchase_way").from("t_purchase_contract as tp").orderBy("contract_amount DESC LIMIT 10");
        }else {
            query.select("contract_name,contract_amount,undertake_dept,supplier_name,purchase_way").from("t_purchase_contract as tp").where("tp.purchase_way='" + way + "'").orderBy("contract_amount DESC LIMIT 10 ");
        }
        generalDao.execute(query);
        QueryResult queryResult =query.getResult();
        return queryResult;
    }

    @Override
    public List<PurchaseContract> getCount(QueryData queryData) {
        String purchaseWay = queryData.getPurchaseWay();
        String supplier = queryData.getSupperName();
        String dept = queryData.getDept();
        String type = queryData.getType();
        String sql="";
        if(supplier !=null){
            sql = "SELECT supplier_name,contract_code,contract_name,contract_type,contract_amount,undertake_dept,purchase_way,purchase_time \n" +
                    "FROM t_purchase_contract WHERE supplier_name='"+supplier+"' and purchase_way='"+purchaseWay+"'";
        }
        if(dept !=null){
            sql = "SELECT supplier_name,contract_code,contract_name,contract_type,contract_amount,undertake_dept,purchase_way,purchase_time \n" +
                    "FROM t_purchase_contract WHERE undertake_dept='"+dept+"' and purchase_way='"+purchaseWay+"'";
        }
        if(type !=null){
            sql = "SELECT supplier_name,contract_code,contract_name,contract_type,contract_amount,undertake_dept,purchase_way,purchase_time \n" +
                    "FROM t_purchase_contract WHERE contract_type='"+type+"' and purchase_way='"+purchaseWay+"'";
        }
        List<PurchaseContract> list = generalDao.queryList(PurchaseContract.class,sql);
        JSONArray jsonArray = new JSONArray();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        for(PurchaseContract pur : list){
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("supplier_name",pur.getSupplierName());
            jsonObject.put("contract_code",pur.getContractCode());
            jsonObject.put("contract_name",pur.getContractName());
            jsonObject.put("contract_type", PercentUtil.getChinese(pur.getContractType()));
            jsonObject.put("contract_amount",pur.getContractAmount());
            jsonObject.put("undertake_dept",pur.getUndertakeDept());
            jsonObject.put("purchase_way",pur.getPurchaseWay());
            jsonObject.put("purchase_time",sdf.format(pur.getPurchaseTime()));
            jsonArray.add(jsonObject);
        }
        return jsonArray;
    }

}
