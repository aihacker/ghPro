package com.gpdi.mdata.sys.serviceimpl.reportform.form;


import com.gpdi.mdata.sys.dao.report.RepPurchaseDao;
import com.gpdi.mdata.sys.dao.system.SysModuleDao;
import com.gpdi.mdata.sys.dao.system.SysRoleDao;
import com.gpdi.mdata.sys.dao.system.SysUserDao;
import com.gpdi.mdata.sys.dao.system.SysVarDao;
import com.gpdi.mdata.sys.entity.report.PurchaseContract;
import com.gpdi.mdata.sys.entity.system.SysModule;
import com.gpdi.mdata.sys.entity.system.SysRole;
import com.gpdi.mdata.sys.entity.system.SysUser;
import com.gpdi.mdata.sys.service.reportform.form.RepPurchaseService;
import com.gpdi.mdata.sys.service.system.user.SysUserService;
import com.gpdi.mdata.sys.utils.JsonUtil;
import com.gpdi.mdata.web.reportform.data.QueryData;
import com.gpdi.mdata.web.system.data.UserData;
import com.gpdi.mdata.web.utils.PercentUtil;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.hamcrest.core.IsNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pub.dao.GeneralDao;
import pub.dao.jdbc.dialect.mysql.PagedQuery;
import pub.dao.mybatis.NamedQuery;
import pub.dao.query.Query;
import pub.dao.query.QueryResult;
import pub.dao.query.QuerySettings;
import pub.functions.StrFuncs;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.apache.commons.lang.StringUtils.trim;

/**
 *
 */
@Service
@Transactional(readOnly = false) //@Transactional:事务管理控制,readOnly=false表明所注解的方法或类是增加,删除,修改数据
public class RepPurchaseServiceImpl implements RepPurchaseService {
    @Autowired
    private GeneralDao generalDao;

    @Autowired
    private RepPurchaseDao repPurchaseDao;

    @Override
    public QueryResult query(QueryData queryData, QuerySettings settings) {
        Query query = new PagedQuery<>(settings);
        String contractNum =trim(queryData.getContractNum());//获取项目编号
        String contractName = trim(queryData.getContractName());//获取项目名称
        String supplier = trim(queryData.getSupperName());//获取供应商名称
        String where = "";
        if (StrFuncs.notEmpty(contractNum)){
            where += " and i.contract_code like :contractNum";
            query.put("contractNum",StrFuncs.anyLike(contractNum));
        }
        if(StrFuncs.notEmpty(contractName)){
            where += " and i.contract_name like:contractName";
            query.put("contractName",StrFuncs.anyLike(contractName));
        }
        if(StrFuncs.notEmpty(supplier)){
            where += " and i.supplier_name like:supplier";
            query.put("supplier",StrFuncs.anyLike(supplier));
        }
       /* if (StrFuncs.notEmpty(contractNum)|| StrFuncs.notEmpty(contractName) || StrFuncs.notEmpty(supplier)) {//判断不为空其长度大于0
            //where += " and i.contract_code like :name or i.contract_name like:name or i.supplier_name like :name or i.undertake_dept like :name";

            query.put("name", StrFuncs.anyLike(name));
        }*/
        query.select("i.*")
                .from("t_purchase_contract i")
                .where(where);
        generalDao.execute(query);
        QueryResult queryResult = query.getResult();
        return queryResult;
    }






    @Override
    public QueryResult getAmount2(String name, QuerySettings settings) {
        Query query = new PagedQuery<>(settings);//创建分页对象
        query.select("supplier_name,contract_code,contract_name,contract_type,contract_amount,undertake_dept,purchase_way,purchase_time").from("t_purchase_contract").where("supplier_name='"+name+"'");
        generalDao.execute(query);
        QueryResult queryResult = query.getResult();
        return queryResult;
    }


    @Override
    public List<PurchaseContract> getAmount(String name ) {
        String sql = "SELECT supplier_name,contract_code,contract_name,contract_type,contract_amount,undertake_dept,purchase_way,purchase_time \n" +
                "FROM t_purchase_contract WHERE supplier_name='"+name+"'";
        List<PurchaseContract> amountList = generalDao.queryList(PurchaseContract.class,sql);
       JSONArray jsonArray = new JSONArray();
       SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        for(PurchaseContract pur : amountList){
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("supplier_name",pur.getSupplierName());
            jsonObject.put("contract_code",pur.getContractCode());
            jsonObject.put("contract_name",pur.getContractName());
            jsonObject.put("contract_type",PercentUtil.getChinese(pur.getContractType()));
            jsonObject.put("contract_amount",pur.getContractAmount());
            jsonObject.put("undertake_dept",pur.getUndertakeDept());
            jsonObject.put("purchase_way",pur.getPurchaseWay());
            jsonObject.put("purchase_time",sdf.format(pur.getPurchaseTime()));
            jsonArray.add(jsonObject);
        }
        return jsonArray;
    }


    @Override
    public List<PurchaseContract> getContractType() {
        return repPurchaseDao.getContractType();
    }

    @Override
    public List<PurchaseContract> getResultCode() {
        String sql = "SELECT purchase_result_code FROM t_purchase_contract ";
        List<PurchaseContract> allList = generalDao.queryList(PurchaseContract.class,sql);
        return allList;
    }



    @Override
    public QueryResult queryContractLeastByType(QueryData queryData, QuerySettings settings) {

        Query query = new PagedQuery(settings);
        String type = queryData.getName();
        String code = queryData.getCode();if(code == null){code = "10";}
        String startTime = queryData.getStartTime();
        String endTime = queryData.getEndTime();
//        if(type==null || type.equals("01")){ type="工程设计/工程勘察";}
        String where ="a.purchase_result_code = b.pur_result_code";
        if (type !=null){
            where += " and a.contract_type_name='"+type+"'";
            query.put("type",type);
        }
        if (StrFuncs.notEmpty(startTime)  && StrFuncs.notEmpty(endTime)){
            where += " and a.purchase_time between '"+startTime+"' and '"+endTime+"'";
            query.put("startTime",startTime);
            query.put("endTime",endTime);
        }
            query.select("a.contract_code,a.contract_name,a.contract_type_name,COUNT(b.company_name) cc,GROUP_CONCAT(b.company_name) gc ")
                    .from("t_purchase_contract a,\n" +
                            "t_candidater b")
                    .where(where)
                    .groupBy("b.pur_result_code HAVING cc<3")
                    .orderBy("cc DESC LIMIT " + code);
        generalDao.execute(query);
        QueryResult queryResult = query.getResult();
        return queryResult;
    }

    @Override
    public List<PurchaseContract> getCountByType(QueryData queryData) {
       String supplier = queryData.getSupperName();
       String type = queryData.getType();
        String sql = "SELECT supplier_name,contract_code,contract_name,contract_type,contract_amount,undertake_dept,purchase_way,purchase_time \n" +
                "FROM t_purchase_contract WHERE supplier_name='"+supplier+"' and contract_type='"+type+"'";
        List<PurchaseContract> list = generalDao.queryList(PurchaseContract.class,sql);
        JSONArray jsonArray = new JSONArray();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        for(PurchaseContract pur : list){
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("supplier_name",pur.getSupplierName());
            jsonObject.put("contract_code",pur.getContractCode());
            jsonObject.put("contract_name",pur.getContractName());
            jsonObject.put("contract_type",PercentUtil.getChinese(pur.getContractType()));
            jsonObject.put("contract_amount",pur.getContractAmount());
            jsonObject.put("undertake_dept",pur.getUndertakeDept());
            jsonObject.put("purchase_way",pur.getPurchaseWay());
            jsonObject.put("purchase_time",sdf.format(pur.getPurchaseTime()));
            jsonArray.add(jsonObject);
        }
        return jsonArray;
    }

    @Override
    public QueryResult queryForm(QueryData queryData, QuerySettings settings){
        Query query = new PagedQuery<>(settings);//创建分页对象
        String startTime = queryData.getStartTime();
        String endTime = queryData.getEndTime();
        String where ="";
        if(queryData.getName() != null && queryData.getName() != ""){
            where += " and contract_type_name='" + queryData.getName() + "'";
            query.put("type", queryData.getName());
        }
        if (queryData.getDept() !=null && queryData.getDept() != ""){
            where += " and undertake_dept='" + queryData.getDept()+"'";
            query.put("dept", queryData.getDept());
        }
        if (StrFuncs.notEmpty(startTime)  && StrFuncs.notEmpty(endTime)){
            where += " and purchase_time between '"+startTime+"' and '"+endTime+"'";
            query.put("startTime",startTime);
            query.put("endTime",endTime);
        }
        query.select("supplier_name,count(supplier_name)  cc,SUM(contract_amount) dd,contract_type_name")
                .from("t_purchase_contract")
                .where(where)
                .groupBy("supplier_name");
        if (queryData.getSearchType() == 2) {
            //签订金额最多的供应商
            query.orderBy("dd DESC LIMIT " + (queryData.getCode()!=null?queryData.getCode():"10"));
        } else if (queryData.getSearchType() == 3) {
            //签订数量及金额均最多的供应商
            query.orderBy("cc DESC,dd DESC LIMIT " + (queryData.getCode()!=null?queryData.getCode():"10"));
        } else {
            //签订数量最多的供应商
            query.orderBy("cc DESC LIMIT " + (queryData.getCode()!=null?queryData.getCode():"10"));
        }
        generalDao.execute(query);
        QueryResult queryResult = query.getResult();
        return queryResult;
    }

}
