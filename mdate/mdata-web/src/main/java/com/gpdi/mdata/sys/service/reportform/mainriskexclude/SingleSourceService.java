package com.gpdi.mdata.sys.service.reportform.mainriskexclude;

import com.gpdi.mdata.sys.entity.report.PurchaseContract;
import com.gpdi.mdata.web.reportform.data.QueryData;
import pub.dao.query.QueryResult;
import pub.dao.query.QuerySettings;

import java.util.List;

/**
 * 单一来源采购方式
 */
public interface SingleSourceService {
    //查询所有采购方式
    List<String> getAllPurchaseWay();
    //查询各采购方式所占比例
    QueryResult queryProportion(QueryData queryData, QuerySettings settings);



    //根据采购方式查询签订合同最多和金额最高的供应商
    QueryResult queryConstructMoneyMostBySupplier(QueryData queryData, QuerySettings settings);
    //根据采购方式查询签订合同最多和金额最高的经办部门
    QueryResult queryConstructMoneyMostByDepart(QueryData queryData, QuerySettings settings);
    //根据采购方式查询签订合同最多和金额最高的合同类型
    QueryResult queryConstructMoneyMostByType(QueryData queryData, QuerySettings settings);
    //根据采购方式查询签订合同金额最高的前10个项目
    QueryResult queryConstructMoneyMax(QueryData queryData, QuerySettings settings);
    List<PurchaseContract> getCount(QueryData queryData);//根据供应商,采购方式查询合同数量
 }
