package com.gpdi.mdata.sys.serviceimpl.reportform.form;

import com.gpdi.mdata.sys.service.reportform.form.AllContractNumberService;
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

import static org.apache.commons.lang.StringUtils.trim;

/**
 * @description:根据不同的条件查询合同数量
 * @author: WangXiaoGang
 * @data: Created in 2018/8/13 16:13
 * @modifier:
 */
@Service//加上这一行以后，将自动扫描路径下面的包，如果一个类带了@Service注解，将自动注册到Spring容器，不需要再在applicationContext.xml文件定义bean了
@Transactional(readOnly = false)//事务管理控制
public class AllContractNumberImpl implements AllContractNumberService {
    @Autowired
    private GeneralDao generalDao;

    @Override
    public QueryResult queryAllContractNumber(QueryData queryData, QuerySettings settings) {
        Query query = new PagedQuery<>(settings);
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


        query.select("supplier_name,contract_code,contract_name,contract_type,contract_amount,undertake_dept,purchase_way,purchase_time")
                .from("t_purchase_contract")
                .where(where).orderBy("contract_name desc");
        generalDao.execute(query);
        QueryResult queryResult = query.getResult();
        return queryResult;
    }

}
