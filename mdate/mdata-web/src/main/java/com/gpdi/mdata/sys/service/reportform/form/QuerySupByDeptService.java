package com.gpdi.mdata.sys.service.reportform.form;

import com.gpdi.mdata.sys.entity.report.PurchaseContract;
import com.gpdi.mdata.web.reportform.data.QueryData;
import pub.dao.query.Query;
import pub.dao.query.QueryResult;
import pub.dao.query.QuerySettings;

import java.util.List;

/**
 * 根据部门查询供应商
 */
public interface QuerySupByDeptService {

    List<PurchaseContract> getAllDept();//获取所有的部门
    List<PurchaseContract> getCountByDept(QueryData queryData);//根据供应商,部门查询合同数量
    List<String> getAllPurchaseWay();//获取所有采购方式

}
