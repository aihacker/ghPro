package com.gpdi.mdata.sys.service.reportform.mainriskexclude;

import com.gpdi.mdata.sys.entity.report.LeadDepartment;
import com.gpdi.mdata.web.reportform.data.QueryData;
import pub.dao.query.PageSettings;
import pub.dao.query.QueryResult;
import pub.dao.query.QuerySettings;

import java.util.List;

/**
 * @description:拖拉机合同
 * @author: WangXiaoGang
 * @data: Created in 2018/10/17 11:13
 * @modifier:
 */
public interface TractorcontractService {

    /**
     * 拖拉机合同
     * @return
     */
    List<LeadDepartment> queryleadDepartment();

    /**
     * 查询签订明细
     */

    QueryResult queryDetail(QueryData queryData, PageSettings settings);

}
