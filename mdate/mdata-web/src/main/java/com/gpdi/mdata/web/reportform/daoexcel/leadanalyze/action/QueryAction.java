package com.gpdi.mdata.web.reportform.daoexcel.leadanalyze.action;

import com.gpdi.mdata.sys.service.reportform.daoexcel.ExtractTextFromXLSService;
import com.gpdi.mdata.web.reportform.data.QueryData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import pub.dao.query.PageSettings;
import pub.dao.query.QueryResult;

/**
 * @description:查看IC卡清单数据
 * @author: WangXiaoGang
 * @data: Created in 2018/10/9 14:21
 * @modifier:
 */
@Controller
public class QueryAction {

    @Autowired
    ExtractTextFromXLSService textFromXLSService;
    @RequestMapping
    public QueryResult execute(QueryData queryData, PageSettings settings){
        return textFromXLSService.queryICCard(queryData,settings);
    }
}
