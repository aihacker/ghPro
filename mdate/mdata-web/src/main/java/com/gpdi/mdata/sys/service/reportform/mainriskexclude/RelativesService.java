package com.gpdi.mdata.sys.service.reportform.mainriskexclude;

import com.gpdi.mdata.web.reportform.data.QueryData;
import pub.dao.query.QueryResult;
import pub.dao.query.QuerySettings;

import java.util.List;

/**
 * 领导亲属经商排查
 */
public interface RelativesService {
    //查询围标可能性排查
    QueryResult queryRelatives(QueryData queryData, QuerySettings settings);
    //查询亲属所在公司签订的合同明细
    QueryResult queryRelativesRel(QueryData queryData, QuerySettings settings);

    //查询所有的领导亲属名称
    List<String> getAllRelatives();

}
