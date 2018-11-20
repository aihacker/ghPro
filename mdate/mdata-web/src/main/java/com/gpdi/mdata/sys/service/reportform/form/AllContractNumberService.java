package com.gpdi.mdata.sys.service.reportform.form;

import com.gpdi.mdata.web.reportform.data.QueryData;
import pub.dao.query.QueryResult;
import pub.dao.query.QuerySettings;

/**
 * @description:根据不同条件查询合同数量
 * @author: WangXiaoGang
 * @data: Created in 2018/8/13 16:10
 * @modifier:
 */
public interface AllContractNumberService {

    /**
     * 查询操作
     * @param queryData
     * @param settings
     * @return
     */
    QueryResult queryAllContractNumber(QueryData queryData, QuerySettings settings);//根据不同条件查询合同数量最多的合同


}
