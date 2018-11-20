package com.gpdi.mdata.sys.serviceimpl.reportform.form;

import com.gpdi.mdata.sys.entity.report.PurchaseContract;
import com.gpdi.mdata.sys.service.reportform.form.AllContractDealService;
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
 * @description:根据不同的条件查询合同数量
 * @author: WangXiaoGang
 * @data: Created in 2018/8/13 16:13
 * @modifier:
 */
@Service//加上这一行以后，将自动扫描路径下面的包，如果一个类带了@Service注解，将自动注册到Spring容器，不需要再在applicationContext.xml文件定义bean了
@Transactional(readOnly = false)//事务管理控制
public class AllContractDealmpl implements AllContractDealService {
    @Autowired
    private GeneralDao generalDao;

    @Override
    public QueryResult queryAllContractDeal(QueryData queryData, QuerySettings settings) {
        Query query = new PagedQuery<>(settings);
       /* String supperName = queryData.getSupperName();//获取供应商名称
        String contractType = queryData.getType();//获取合同类型*/
        String contractDept = queryData.getDept();//获取经办部门
        String purchaseWay = queryData.getPurchaseWay();//获取采购方式
        String startTime = queryData.getHid();//获取开始时间
        String endTime = queryData.getEnd();//获取结束时间
        String where = "";

        if(StrFuncs.notEmpty(contractDept)){
            where += " and undertake_dept = '"+contractDept+"'";
            query.put("contractDept", StrFuncs.anyLike(contractDept));
        }
        if(StrFuncs.notEmpty(purchaseWay)){
            where += " and purchase_way = '"+purchaseWay+"'";
            query.put("contractDept", StrFuncs.anyLike(purchaseWay));
        }
        if (StrFuncs.notEmpty(startTime)  && StrFuncs.notEmpty(endTime)){
            where += " and purchase_time between '"+startTime+"' and '"+endTime+"'";
            query.put("startTime",startTime);
            query.put("endTime",endTime);
        }
        /*query.select("supplier_name,COUNT(supplier_name) AS sss,undertake_dept,purchase_way")
                .from("t_purchase_contract")
                .where(where).groupBy("supplier_name").orderBy("sss desc");
        generalDao.execute(query);*/
        query.select("supplier_name,COUNT(supplier_name) AS sss,COUNT(supplier_name)/(SELECT sum(sss) FROM (SELECT supplier_name,COUNT(supplier_name) AS sss FROM t_purchase_contract WHERE undertake_dept = '"+contractDept+"' AND purchase_way = '"+purchaseWay+"' GROUP BY supplier_name ORDER BY sss DESC) a) AS uuu,undertake_dept,purchase_way")
                .from("t_purchase_contract")
                .where(where).groupBy("supplier_name").orderBy("sss desc");
        generalDao.execute(query);
        QueryResult queryResult = query.getResult();
        return queryResult;
    }

    @Override
    public List<PurchaseContract> getDepartWayContractType(QueryData queryData) {
        //获取供应商名称和采购方式
        String supperName = queryData.getSupperName();//获取供应商名称
        String contractType = queryData.getType();//获取合同类型
        String contractDept = queryData.getDept();//获取经办部门
        String purchaseWay = queryData.getPurchaseWay();//获取采购方式
//        String sql = "select contract_type_name,COUNT(contract_type) AS ContractCode from t_purchase_contract where supplier_name = '"+supperName+"' and purchase_way = '"+purchaseWay+"' group by contract_type_name order by ContractCode desc";
        String sql = "select contract_type_name,COUNT(contract_type) AS ContractCode,COUNT(contract_type_name)/(SELECT sum(sss) FROM (SELECT contract_type_name,COUNT(contract_type_name) AS sss FROM t_purchase_contract WHERE undertake_dept = '"+contractDept+"' AND purchase_way = '"+purchaseWay+"' GROUP BY contract_type_name ORDER BY sss DESC) a) AS Percent,undertake_dept,purchase_way  from t_purchase_contract where undertake_dept = '"+contractDept+"' and purchase_way = '"+purchaseWay+"' group by contract_type_name order by ContractCode desc";
        List<PurchaseContract> list = generalDao.queryList(PurchaseContract.class,sql);
        return list;
    }



    /*@Override
    public QueryResult queryAllContractDeal2(QueryData queryData, QuerySettings settings) {
        Query query = new PagedQuery<>(settings);
        String contractDept = queryData.getDept();//获取经办部门
        String purchaseWay = queryData.getPurchaseWay();//获取采购方式
        String where = "";

        if(StrFuncs.notEmpty(contractDept)){
            where += " and undertake_dept = '"+contractDept+"'";
            query.put("contractDept", StrFuncs.anyLike(contractDept));
        }
        if(StrFuncs.notEmpty(purchaseWay)){
            where += " and purchase_way = '"+purchaseWay+"'";
            query.put("contractDept", StrFuncs.anyLike(purchaseWay));
        }

        query.select("contract_type,COUNT(contract_type) AS bbb")
                .from("t_purchase_contract")
                .where(where).groupBy("contract_type").orderBy("bbb desc");
        generalDao.execute(query);
        QueryResult queryResult2 = query.getResult();


        return queryResult2;
    }*/

    @Override
    public QueryResult queryPartContract(QueryData queryData, QuerySettings settings) {
        Query query = new PagedQuery<>(settings);
        String contractDept = queryData.getDept();//获取经办部门
        String purchaseWay = queryData.getPurchaseWay();//获取采购方式
        String supperName = queryData.getSupperName();
        String contractType = queryData.getType();//获取合同类型_name
        String contractTypeName = queryData.getName();//获取合同类型
        String where = "";

        if(StrFuncs.notEmpty(purchaseWay)){
            where += " and purchase_way = '"+purchaseWay+"'";
            query.put("contractDept", StrFuncs.anyLike(purchaseWay));
        }
        if(StrFuncs.notEmpty(contractDept)){
            where += " and undertake_dept = '"+contractDept+"'";
            query.put("contractDept", StrFuncs.anyLike(contractDept));
        }
        if(StrFuncs.notEmpty(supperName)){
            where += " and supplier_name = '"+supperName+"'";
            query.put("contractDept", StrFuncs.anyLike(supperName));
        }
        /*if(StrFuncs.notEmpty(contractType)){
            where += " and contract_type = '"+contractType+"'";
            query.put("contractType",StrFuncs.anyLike(contractType));
        }*/
        if(StrFuncs.notEmpty(contractType)){
            where += " and contract_type_name = '"+contractType+"'";
            query.put("contractType", StrFuncs.anyLike(contractType));
        }

        if(StrFuncs.notEmpty(contractTypeName)){
            where += " and contract_type = '"+contractTypeName+"'";
            query.put("contractType", StrFuncs.anyLike(contractTypeName));
        }


        query.select("supplier_name,contract_code,contract_name,contract_type,contract_amount,undertake_dept,purchase_way,purchase_time,undertake_man")
                .from("t_purchase_contract")
                .where(where).orderBy("contract_name desc");
        generalDao.execute(query);
        QueryResult queryResult = query.getResult();
        return queryResult;


    }

    @Override
    public QueryResult queryDepartmentAndType(QueryData queryData, QuerySettings settings) {
        Query query = new PagedQuery<>(settings);
        String supperName = queryData.getSupperName();//获取供应商名称
        String contractType = queryData.getType();//获取合同类型
        String contractDept = queryData.getDept();//获取经办部门
        String purchaseWay = queryData.getPurchaseWay();//获取采购方式
        Double number = queryData.getPercent();//获取合同数量
        String where = "";

        if(StrFuncs.notEmpty(supperName)){
            where += " and supplier_name = '"+supperName+"'";
            query.put("supperName", StrFuncs.anyLike(supperName));
        }
        if(StrFuncs.notEmpty(contractType)){
            where += " and contract_type = '"+contractType+"'";
            query.put("contractType", StrFuncs.anyLike(contractType));
        }
        if(StrFuncs.notEmpty(contractDept)){
            where += " and undertake_dept = '"+contractDept+"'";
            query.put("contractDept", StrFuncs.anyLike(contractDept));
        }
        if(StrFuncs.notEmpty(purchaseWay)){
            where += " and purchase_way = '"+purchaseWay+"'";
            query.put("contractDept", StrFuncs.anyLike(purchaseWay));
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
        return queryResult;
    }
    //=====================================================
    @Override
    public List<PurchaseContract> getScreenContractType(QueryData queryData) {
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
    //===============================================



    @Override
    public QueryResult queryDepartmentAndSupplier(QueryData queryData, QuerySettings settings) {
        Query query = new PagedQuery<>(settings);
        String supperName = queryData.getSupperName();//获取供应商名称
        String contractType = queryData.getType();//获取合同类型
        String contractDept = queryData.getDept();//获取经办部门
        String purchaseWay = queryData.getPurchaseWay();//获取采购方式
        String where = "";

        if(StrFuncs.notEmpty(supperName)){
            where += " and supplier_name = '"+supperName+"'";
            query.put("supperName", StrFuncs.anyLike(supperName));
        }

        if(StrFuncs.notEmpty(contractType)){
            where += " and contract_type = '"+contractType+"'";
            query.put("contractType", StrFuncs.anyLike(contractType));
        }
        if(StrFuncs.notEmpty(contractDept)){
            where += " and undertake_dept = '"+contractDept+"'";
            query.put("contractDept", StrFuncs.anyLike(contractDept));
        }
        if(StrFuncs.notEmpty(purchaseWay)){
            where += " and purchase_way = '"+purchaseWay+"'";
            query.put("contractDept", StrFuncs.anyLike(purchaseWay));
        }
        query.select("undertake_dept,COUNT(undertake_dept) AS sss,COUNT(undertake_dept)/(SELECT sum(sss) FROM (SELECT undertake_dept,COUNT(undertake_dept) AS sss FROM t_purchase_contract WHERE contract_type = '"+contractType+"' AND purchase_way = '"+purchaseWay+"' GROUP BY undertake_dept ORDER BY sss DESC) a) AS uuu,contract_type,purchase_way")
                .from("t_purchase_contract")
                .where(where).groupBy("undertake_dept,contract_type_name,purchase_way").orderBy("sss desc");
        generalDao.execute(query);
        QueryResult queryResult = query.getResult();
        return queryResult;
    }
    /*@Override
    public QueryResult queryDepartmentAndSupplier(QueryData queryData) {
        Query query = new PagedQuery<>();
        String supperName = queryData.getSupperName();//获取供应商名称
        String contractType = queryData.getType();//获取合同类型
        String contractDept = queryData.getDept();//获取经办部门
        String purchaseWay = queryData.getPurchaseWay();//获取采购方式
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
        query.select("undertake_dept,COUNT(undertake_dept) AS sss,COUNT(undertake_dept)/(SELECT sum(sss) FROM (SELECT undertake_dept,COUNT(undertake_dept) AS sss FROM t_purchase_contract WHERE contract_type = '"+contractType+"' AND purchase_way = '"+purchaseWay+"' GROUP BY undertake_dept ORDER BY sss DESC) a) AS uuu,contract_type_name,purchase_way")
                .from("t_purchase_contract")
                .where(where).groupBy("undertake_dept,contract_type_name,purchase_way").orderBy("sss desc");
        generalDao.execute(query);
        QueryResult queryResult = query.getResult();
        return queryResult;
    }*/
    @Override
    public List<PurchaseContract> getDepartNameContractType(QueryData queryData) {
        //获取供应商名称和采购方式
        String supperName = queryData.getSupperName();//获取供应商名称
        String contractType = queryData.getType();//获取合同类型
        String contractDept = queryData.getDept();//获取经办部门
        String purchaseWay = queryData.getPurchaseWay();//获取采购方式
//        String sql = "select contract_type_name,COUNT(contract_type) AS ContractCode from t_purchase_contract where supplier_name = '"+supperName+"' and purchase_way = '"+purchaseWay+"' group by contract_type_name order by ContractCode desc";
        String sql = "select supplier_name,COUNT(supplier_name) AS ContractCode,COUNT(supplier_name)/(SELECT sum(sss) FROM (SELECT supplier_name,COUNT(supplier_name) AS sss FROM t_purchase_contract WHERE contract_type = '"+contractType+"' AND purchase_way = '"+purchaseWay+"' GROUP BY supplier_name ORDER BY sss DESC) a) AS Percent,contract_type as ContractTypeName,purchase_way from t_purchase_contract where contract_type = '"+contractType+"' and purchase_way = '"+purchaseWay+"' group by supplier_name,contract_type,purchase_way order by ContractCode desc";
        List<PurchaseContract> list = generalDao.queryList(PurchaseContract.class,sql);
        return list;
    }
}
