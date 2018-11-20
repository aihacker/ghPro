package com.gpdi.mdata.sys.service.reportform.cleanInventory;

import com.gpdi.mdata.sys.entity.report.CleanInventoryRel;
import com.gpdi.mdata.sys.entity.report.CleanResult;
import com.gpdi.mdata.sys.entity.report.CleanTemps;
import com.gpdi.mdata.web.reportform.data.QueryData;
import pub.dao.query.PageSettings;
import pub.dao.query.QueryResult;
import pub.dao.query.QuerySettings;

import java.util.HashMap;
import java.util.List;

/**
 *即时清结候选供应商排序
 */
public interface CleanSortService {

    /**
     * 查询及保存回调数据
     * @return
     */
    String getCompanyName(String code);

    HashMap<String, Integer> doSortComb(QueryData queryData, QuerySettings settings);

    List<CleanTemps> getAllByDept(String dept);
    //QueryResult getAllByDept(String dept);

    QueryResult queryContract(QueryData queryData, PageSettings settings);
    //List doSortComb(QueryData queryData, QuerySettings settings);

    QueryResult queryContractByDept(QueryData queryData, PageSettings settings);

}
