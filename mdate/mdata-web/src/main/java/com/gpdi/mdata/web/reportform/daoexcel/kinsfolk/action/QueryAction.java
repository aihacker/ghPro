package com.gpdi.mdata.web.reportform.daoexcel.kinsfolk.action;

import com.gpdi.mdata.sys.service.reportform.daoexcel.ExtractTextFromXLSService;
import com.gpdi.mdata.web.reportform.data.QueryData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import pub.dao.query.PageSettings;
import pub.dao.query.QueryResult;

/**
 * @description:导入领导亲属经商数据
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
        return extractTextFromXLSService.queryKinsfolkNote(queryData,settings);
    }




}