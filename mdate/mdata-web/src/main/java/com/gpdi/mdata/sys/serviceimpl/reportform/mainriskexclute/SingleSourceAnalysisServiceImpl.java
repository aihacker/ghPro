package com.gpdi.mdata.sys.serviceimpl.reportform.mainriskexclute;

import com.gpdi.mdata.sys.entity.report.PurchaseContract;
import com.gpdi.mdata.sys.service.reportform.mainriskexclude.SingleSourceAnalysisService;
import com.gpdi.mdata.web.reportform.data.QueryData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pub.dao.GeneralDao;
import pub.dao.jdbc.dialect.mysql.PagedQuery;
import pub.dao.query.Query;
import pub.dao.query.QueryResult;
import pub.dao.query.QuerySettings;
import pub.functions.StrFuncs;

import java.util.List;

/**
 * @description:在单一来源中,根据各维度做个角度的分析
 * @author: WangXiaoGang
 * @data: Created in 2018/9/18 15:03
 * @modifier:
 */
@Service
@Transactional(readOnly = false)
public class SingleSourceAnalysisServiceImpl implements SingleSourceAnalysisService{
    @Autowired
    GeneralDao generalDao;

    @Override
    public List<PurchaseContract> getContractBySupplier(QueryData queryData) {
        //获取供应商名称和采购方式
        String supperName = queryData.getSupperName();//获取供应商名称
        String contractType = queryData.getType();//获取合同类型
        String contractDept = queryData.getDept();//获取经办部门
        String purchaseWay = queryData.getPurchaseWay();//获取采购方式
//        String sql = "select contract_type_name,COUNT(contract_type) AS ContractCode from t_purchase_contract where supplier_name = '"+supperName+"' and purchase_way = '"+purchaseWay+"' group by contract_type_name order by ContractCode desc";
        String sql = "select contract_type_name,COUNT(contract_type) AS ContractCode,COUNT(contract_type_name)/(SELECT sum(sss) FROM (SELECT contract_type_name,COUNT(contract_type_name) AS sss FROM t_purchase_contract WHERE supplier_name = '"+supperName+"' AND purchase_way = '"+purchaseWay+"' GROUP BY contract_type_name ORDER BY sss DESC) a) AS Percent,supplier_name,purchase_way  from t_purchase_contract where supplier_name = '"+supperName+"' and purchase_way = '"+purchaseWay+"' group by contract_type_name order by ContractCode desc";
        List<PurchaseContract> list = generalDao.queryList(PurchaseContract.class,sql);
        return list;
    }

    @Override
    public QueryResult getDepartBySupplier(QueryData queryData, QuerySettings settings) {
        Query query = new PagedQuery<>(settings);
        String supperName = queryData.getSupperName();//获取供应商名称
        String contractType = queryData.getType();//获取合同类型
        String contractDept = queryData.getDept();//获取经办部门
        String purchaseWay = queryData.getPurchaseWay();//获取采购方式
        Double number =null;//获取合同数量
//        Double number = queryData.getPercent();//获取合同数量
        String where = "";

        if(StrFuncs.notEmpty(supperName)){
            where += " and supplier_name = '"+supperName+"'";
            query.put("supperName",StrFuncs.anyLike(supperName));
        }
        if(StrFuncs.notEmpty(contractType)){
            where += " and contract_type = '"+contractType+"'";
            query.put("contractType",StrFuncs.anyLike(contractType));
        }
        if(StrFuncs.notEmpty(contractDept)){
            where += " and undertake_dept = '"+contractDept+"'";
            query.put("contractDept",StrFuncs.anyLike(contractDept));
        }
        if(StrFuncs.notEmpty(purchaseWay)){
            where += " and purchase_way = '"+purchaseWay+"'";
            query.put("contractDept",StrFuncs.anyLike(purchaseWay));
        }
        /*query.select("undertake_dept,COUNT(undertake_dept) AS sss,supplier_name,purchase_way")
                .from("t_purchase_contract")
                .where(where).groupBy("undertake_dept").orderBy("sss desc");*/
        query.select("supplier_name,COUNT(supplier_name) AS sss,COUNT(supplier_name)/(SELECT sum(sss) FROM (SELECT supplier_name,COUNT(supplier_name) AS sss FROM t_purchase_contract WHERE supplier_name = '"+supperName+"' AND purchase_way = '"+purchaseWay+"' GROUP BY supplier_name ORDER BY sss DESC) a) AS uuu,undertake_dept,purchase_way")
                .from("t_purchase_contract")
                .where(where).groupBy("undertake_dept").orderBy("sss desc");
        //========================================================
        generalDao.execute(query);
        QueryResult queryResult = query.getResult();
        return null;
    }
}
