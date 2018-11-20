package com.gpdi.mdata.web.reportform.daoexcel.purchase.action;
import com.gpdi.mdata.sys.service.reportform.daoexcel.ExtractTextFromXLSService;
import com.gpdi.mdata.web.reportform.data.QueryData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import org.springframework.web.bind.annotation.RequestMapping;
import pub.dao.query.PageSettings;
import pub.dao.query.QueryResult;

/**
 * @description:导入采购合同台账表
 * @author: WangXiaoGang
 * @data: Created in 2018/6/22 9:26
 * @modifier:
 */

@Controller
public class QueryAction {

    @Autowired
    private ExtractTextFromXLSService extractTextFromXLSService;

    @RequestMapping
    public QueryResult execute(QueryData queryData, PageSettings settings){
        return extractTextFromXLSService.queryPurchaseNote(queryData,settings);
    }




}