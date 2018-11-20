package com.gpdi.mdata.web.reportform.info.rulersinfo.action;


import com.gpdi.mdata.sys.service.reportform.daoexcel.resultCode.CallBackService;
import com.gpdi.mdata.sys.service.reportform.info.CompanyInfoService;
import com.gpdi.mdata.web.reportform.data.QueryData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import pub.dao.query.PageSettings;
import pub.dao.query.QueryResult;


/**
 * 应招标规则文件说明
 */
@Controller
public class QueryAction {
    @Autowired
    private CompanyInfoService companyInfoService;

   @RequestMapping
    public QueryResult execute(QueryData queryData, PageSettings settings){
       QueryResult queryResult = companyInfoService.queryrulers(queryData,settings);
       return queryResult;
   }


}
