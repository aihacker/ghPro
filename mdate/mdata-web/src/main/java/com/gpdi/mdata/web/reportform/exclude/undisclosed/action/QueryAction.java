package com.gpdi.mdata.web.reportform.exclude.undisclosed.action;


import com.gpdi.mdata.sys.service.reportform.mainriskexclude.ReverseService;
import com.gpdi.mdata.sys.service.reportform.mainriskexclude.UndisclosedService;
import com.gpdi.mdata.web.reportform.data.QueryData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import pub.dao.query.PageSettings;
import pub.dao.query.QueryResult;

/**
 * 应招未招，应公开未公开
 * 根据采购管理办法规定的项目类型、项目投资额等条件，检索是否有应招未招、应公开未公开的情况
 */
@Controller
public class QueryAction {
    @Autowired
    private UndisclosedService undisclosedService;


   @RequestMapping
    public QueryResult execute(QueryData queryData, PageSettings settings){
       return undisclosedService.queryUnOpen(queryData,settings);
   }

}
