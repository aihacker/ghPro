package com.gpdi.mdata.sys.service.reportform.cleanInventory;

import com.gpdi.mdata.web.reportform.data.QueryData;
import pub.dao.query.QueryResult;
import pub.dao.query.QuerySettings;

/**
 * 围标可能性排查:选取询价、比选、招标等采购方式的项目，
 * 比对参与项目竞逐的各个供应商的法定代表人、控股股东是否为同一人
 */
public interface CleanBidderExcludeService {
    //查询围标可能性排查
    QueryResult cleanQueryBidders(QueryData queryData, QuerySettings settings);

}
