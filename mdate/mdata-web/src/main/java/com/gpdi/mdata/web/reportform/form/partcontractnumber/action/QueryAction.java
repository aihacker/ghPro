package com.gpdi.mdata.web.reportform.form.partcontractnumber.action;

import com.gpdi.mdata.sys.service.reportform.form.AllContractDealService;
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
    private AllContractDealService allContractDealService;

    @RequestMapping
    public QueryResult execute(QueryData queryData, PageSettings settings){
        return allContractDealService.queryPartContract(queryData,settings);
    }

}
