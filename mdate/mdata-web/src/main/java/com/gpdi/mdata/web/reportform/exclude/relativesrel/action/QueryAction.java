package com.gpdi.mdata.web.reportform.exclude.relativesrel.action;


import com.gpdi.mdata.sys.service.reportform.mainriskexclude.RelativesService;
import com.gpdi.mdata.web.reportform.data.QueryData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import pub.dao.query.PageSettings;
import pub.dao.query.QueryResult;
import pub.spring.web.mvc.model.Model;

/**
 * 查看领导亲属所在公司签订合同明细
 */
@Controller
public class QueryAction {

    @Autowired
    private RelativesService relativesService;

   @RequestMapping
    public QueryResult execute(QueryData queryData, PageSettings settings, Model model){

       return relativesService.queryRelativesRel(queryData,settings);
   }

}
