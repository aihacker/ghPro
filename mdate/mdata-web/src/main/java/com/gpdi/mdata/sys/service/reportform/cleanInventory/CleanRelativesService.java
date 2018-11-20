package com.gpdi.mdata.sys.service.reportform.cleanInventory;

import com.gpdi.mdata.web.reportform.data.QueryData;
import pub.dao.query.QueryResult;
import pub.dao.query.QuerySettings;

import java.util.List;

/**
 * 即时清结多维度风险排查
 */
public interface CleanRelativesService {
    //查询领导亲属经商排查
    QueryResult queryRelatives(QueryData queryData, QuerySettings settings);
    //查询亲属所在公司签订的合同明细
    QueryResult queryRelativesRel(QueryData queryData, QuerySettings settings);

    //查询所有的领导亲属名称
    List<String> getAllRelatives();

}
