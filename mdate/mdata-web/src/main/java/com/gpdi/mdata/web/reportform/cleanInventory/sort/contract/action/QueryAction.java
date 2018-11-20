package com.gpdi.mdata.web.reportform.cleanInventory.sort.contract.action;


import com.gpdi.mdata.sys.entity.report.CleanInventoryRel;
import com.gpdi.mdata.sys.service.reportform.cleanInventory.CleanSortService;
import com.gpdi.mdata.sys.service.reportform.form.RepPurchaseService;
import com.gpdi.mdata.web.reportform.data.QueryData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import pub.dao.query.PageSettings;
import pub.dao.query.QueryResult;
import pub.spring.web.mvc.model.Model;

import java.util.HashMap;
import java.util.List;


/**
 * @author: WangXiaoGang
 * @data: Created in 2018/9/7 15:23
 * @description:即时清结数据更新
 */
@Controller
public class QueryAction {

    @Autowired
    CleanSortService cleanSortService;

    @RequestMapping
    public QueryResult execute(QueryData queryData, PageSettings settings, Model model) {
        if(queryData.getName() != null && queryData.getName() != ""
                && queryData.getSupperName() != null && queryData.getSupperName() != "") {
            QueryResult queryResult =  cleanSortService.queryContract(queryData, settings);
            model.put("cleanInventoryRels", queryResult);
            return queryResult;
        } else {
            return null;
        }
    }


}