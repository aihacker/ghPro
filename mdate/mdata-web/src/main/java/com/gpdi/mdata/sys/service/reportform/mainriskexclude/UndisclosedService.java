package com.gpdi.mdata.sys.service.reportform.mainriskexclude;

import com.gpdi.mdata.web.reportform.data.QueryData;
import pub.dao.query.QueryResult;
import pub.dao.query.QuerySettings;

/**
 * 应招未招，应公开未公开
 * 根据采购管理办法规定的项目类型、项目投资额等条件，检索是否有应招未招、应公开未公开的情况
 */
public interface UndisclosedService {
    QueryResult queryUnOpen(QueryData queryData, QuerySettings settings);
}
