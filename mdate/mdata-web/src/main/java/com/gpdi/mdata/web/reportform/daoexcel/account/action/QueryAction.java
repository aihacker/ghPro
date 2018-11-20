package com.gpdi.mdata.web.reportform.daoexcel.account.action;

import com.gpdi.mdata.sys.service.reportform.daoexcel.ExtractTextFromXLSService;
import com.gpdi.mdata.web.reportform.data.QueryData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import pub.dao.query.PageSettings;
import pub.dao.query.QueryResult;

/**
 * @author: WangXiaoGang
 * @data: Created in 2018/8/8 10:09
 * @description:导入合同台账表
 */

@Controller
public class QueryAction {

    @Autowired
    private ExtractTextFromXLSService extractTextFromXLSService;

    @RequestMapping
    public QueryResult execute(QueryData queryData, PageSettings settings){
        return extractTextFromXLSService.queryAccountNote(queryData,settings);
    }




}