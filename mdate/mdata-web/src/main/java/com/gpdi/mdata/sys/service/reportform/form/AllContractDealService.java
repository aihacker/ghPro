package com.gpdi.mdata.sys.service.reportform.form;

import com.gpdi.mdata.sys.entity.report.PurchaseContract;
import com.gpdi.mdata.web.reportform.data.QueryData;
import pub.dao.query.QueryResult;
import pub.dao.query.QuerySettings;

import java.util.List;

/**
 * @description:根据不同条件查询合同数量
 * @author: WangXiaoGang
 * @data: Created in 2018/8/13 16:10
 * @modifier:
 */
public interface AllContractDealService {

    /**
     * 查询操作
     * @param queryData
     * @param settings
     * @return
     */
    QueryResult queryAllContractDeal(QueryData queryData, QuerySettings settings);//供应商和合同类型

    //根据经办部门和采购方式筛选出合同类型
    List<PurchaseContract> getDepartWayContractType(QueryData queryData);//获取所有的合同类型

    QueryResult queryPartContract(QueryData queryData, QuerySettings settings);//合同信息

    QueryResult queryDepartmentAndType(QueryData queryData, QuerySettings settings);//经办部门和合同类型

    //根据供应商名称和采购方式筛选出合同类型
    List<PurchaseContract> getScreenContractType(QueryData queryData);//获取所有的合同类型

//    QueryResult queryDepartmentAndSupplier(QueryData queryData);//经办部门和供应商
    QueryResult queryDepartmentAndSupplier(QueryData queryData, QuerySettings settings);//经办部门和供应商


    List<PurchaseContract> getDepartNameContractType(QueryData queryData);//获取所有的合同类型


}
