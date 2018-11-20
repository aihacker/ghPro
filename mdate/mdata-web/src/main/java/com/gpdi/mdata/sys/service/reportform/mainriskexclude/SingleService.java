package com.gpdi.mdata.sys.service.reportform.mainriskexclude;

import com.gpdi.mdata.web.reportform.data.QueryData;
import pub.dao.query.QueryResult;
import pub.dao.query.QuerySettings;

/**
 * 单一合同多维度分析
 */
public interface SingleService {
    //倒签可能性排查
    QueryResult  queryReverseDate(QueryData queryData, QuerySettings settings);
}
