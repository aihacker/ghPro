package com.gpdi.mdata.web.reportform.exclude.tractorcontractinfo.action;

import com.gpdi.mdata.sys.service.reportform.form.AllContractNumberService;
import com.gpdi.mdata.sys.service.reportform.mainriskexclude.TractorcontractService;
import com.gpdi.mdata.web.reportform.data.QueryData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import pub.dao.query.PageSettings;
import pub.dao.query.QueryResult;

/**
 * @description:查询合同数量明细
 * @author: WangXiaoGang
 * @data: Created in 2018/8/13 15:56
 * @modifier:
 */
@Controller
public class QueryAction {

    @Autowired
    private TractorcontractService tractorcontractService;

    @RequestMapping
    public QueryResult execute(QueryData queryData, PageSettings settings){
        return tractorcontractService.queryDetail(queryData,settings);
    }

}
