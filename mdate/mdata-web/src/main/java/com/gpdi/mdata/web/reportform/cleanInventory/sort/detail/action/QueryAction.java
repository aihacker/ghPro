package com.gpdi.mdata.web.reportform.cleanInventory.sort.detail.action;


import com.gpdi.mdata.sys.entity.report.CleanInventoryRel;
import com.gpdi.mdata.sys.service.reportform.cleanInventory.CleanSortService;
import com.gpdi.mdata.web.reportform.data.QueryData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import pub.dao.query.PageSettings;
import pub.dao.query.QueryResult;
import pub.spring.web.mvc.model.Model;

import java.util.List;


/**
 * @author: WangXiaoGang
 * @data: Created in 2018/9/19 18:52
 * @description:根据公司名称和部门名称查询项目明细
 */
@Controller
public class QueryAction {

    @Autowired
    CleanSortService cleanSortService;

    @RequestMapping
    public QueryResult execute(QueryData queryData, PageSettings settings) {

        return cleanSortService.queryContractByDept(queryData, settings);

    }


}