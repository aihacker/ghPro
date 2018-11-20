package com.gpdi.mdata.sys.service.reportform.mainriskexclude;

import com.gpdi.mdata.sys.entity.report.CompareScore;
import com.gpdi.mdata.sys.entity.report.DismantTemp;
import com.gpdi.mdata.web.reportform.data.QueryData;
import pub.dao.query.QueryResult;
import pub.dao.query.QuerySettings;

import java.util.List;

/**
 * 拆单可能性排查
 */
public interface DismantlingService {

    QueryResult  queryDismantMaybe(QueryData queryData, QuerySettings settings);

    //拆单可能性排查
    List<DismantTemp> queryDismant(QueryData queryData, QuerySettings settings);

    //合同类型组织结构
    String typeformTree();

    //获取所有合同类型

}
