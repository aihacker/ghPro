package com.gpdi.mdata.web.reportform.daoexcel.cleaninventory.action;

import com.gpdi.mdata.sys.service.reportform.daoexcel.ExtractTextFromXLSService;
import com.gpdi.mdata.web.reportform.data.QueryData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import pub.dao.query.PageSettings;
import pub.dao.query.QueryResult;

/**
 * @author: WangXiaoGang
 * @data: Created in 2018/9/4 19:05
 * @description:导入及时清洁数据
 */

@Controller
public class QueryAction {

    @Autowired
    private ExtractTextFromXLSService extractTextFromXLSService;

    @RequestMapping
    public QueryResult execute(QueryData queryData, PageSettings settings){
        return extractTextFromXLSService.queryBidCompanyNote(queryData,settings);
    }


}