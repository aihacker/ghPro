package com.gpdi.mdata.sys.service.reportform.form;

import com.gpdi.mdata.sys.entity.report.PurchaseContract;
import com.gpdi.mdata.sys.entity.system.SysRole;
import com.gpdi.mdata.sys.entity.system.SysUser;
import com.gpdi.mdata.web.reportform.data.QueryData;
import pub.dao.query.Query;
import pub.dao.query.QueryResult;
import pub.dao.query.QuerySettings;

import java.text.ParseException;
import java.util.List;
import java.util.Map;

/**
 *采购报表分析
 */
public interface RepPurchaseService {

    /**
     * 查询操作
     * @param queryData
     * @param settings
     * @return
     */
    QueryResult query(QueryData queryData, QuerySettings settings);//查询所有的合同信息

    List<PurchaseContract> getAmount(String name );//根据名称查询数量
    QueryResult getAmount2(String name,QuerySettings settings);//根据名称查询数量

    List<PurchaseContract> getContractType();//获取所有的合同类型

    List<PurchaseContract> getResultCode();//获取所有的采购结果编号

    QueryResult queryContractLeastByType(QueryData queryData,QuerySettings settings);//根据合同类型查询签订合同数量不超过3个的供应商

    List<PurchaseContract> getCountByType(QueryData queryData);//根据供应商,项目类型查询合同数量

    QueryResult queryForm(QueryData queryData, QuerySettings settings);//根据条件查询供应商
}
