package com.gpdi.mdata.sys.service.reportform.cleanInventory;

import com.gpdi.mdata.sys.entity.report.DismantTemp;
import com.gpdi.mdata.web.reportform.data.QueryData;
import pub.dao.query.QueryResult;
import pub.dao.query.QuerySettings;

import java.util.List;

/**
 * 即时清结拆单可能性排查
 */
public interface CleanDismantlingService {
    //拆单可能性排查
    List<DismantTemp> queryDismant(QueryData queryData, QuerySettings settings);

    //获取物料类型
    List<String> goodsType();

    //获取经办部门
    List<String> queryDept();
}
