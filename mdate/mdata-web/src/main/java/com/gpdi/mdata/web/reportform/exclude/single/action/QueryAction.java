package com.gpdi.mdata.web.reportform.exclude.single.action;


import com.gpdi.mdata.sys.service.reportform.mainriskexclude.ReverseService;
import com.gpdi.mdata.sys.service.reportform.mainriskexclude.SingleService;
import com.gpdi.mdata.web.reportform.data.QueryData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import pub.dao.query.PageSettings;
import pub.dao.query.QueryResult;

/**
 * 单个合同多维度分析
 */
@Controller
public class QueryAction {
    @Autowired
    private SingleService singleService;


   @RequestMapping
    public QueryResult execute(QueryData queryData, PageSettings settings){
       return singleService.queryReverseDate(queryData,settings);
   }

}
