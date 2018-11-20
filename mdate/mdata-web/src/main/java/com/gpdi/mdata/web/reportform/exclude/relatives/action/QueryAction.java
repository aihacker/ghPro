package com.gpdi.mdata.web.reportform.exclude.relatives.action;


import com.gpdi.mdata.sys.service.reportform.mainriskexclude.RelativesService;
import com.gpdi.mdata.web.reportform.data.QueryData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import pub.dao.query.PageSettings;
import pub.dao.query.QueryResult;
import pub.spring.web.mvc.model.Model;

import java.util.List;

/**
 * 领导及其亲属围绕企业经商排查：
 * 比对领导及其亲属围绕企业经商信息库的信息与成交合同供应商的法人、股东信息。
 */
@Controller
public class QueryAction {

    @Autowired
    private RelativesService relativesService;

   @RequestMapping
    public QueryResult execute(QueryData queryData, PageSettings settings, Model model){
      /* List<String> purList = relativesService.getAllRelatives();
       model.put("purList",purList);*/
       return relativesService.queryRelatives(queryData,settings);
   }

}
