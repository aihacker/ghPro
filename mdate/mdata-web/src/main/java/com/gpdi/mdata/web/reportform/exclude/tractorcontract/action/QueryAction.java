package com.gpdi.mdata.web.reportform.exclude.tractorcontract.action;


import com.gpdi.mdata.sys.entity.report.LeadDepartment;
import com.gpdi.mdata.sys.service.reportform.mainriskexclude.BidderExcludeService;
import com.gpdi.mdata.sys.service.reportform.mainriskexclude.SingleSourceService;
import com.gpdi.mdata.sys.service.reportform.mainriskexclude.TractorcontractService;
import com.gpdi.mdata.web.reportform.data.QueryData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import pub.dao.query.PageSettings;
import pub.dao.query.QueryResult;
import pub.spring.web.mvc.model.Model;

import java.util.List;

/**
 * 拖拉机合同
 *
 */
@Controller
public class QueryAction {
    @Autowired
    private TractorcontractService tractorcontractService;


   @RequestMapping
    public QueryResult execute(QueryData queryData, PageSettings settings, Model model){
       List<LeadDepartment> leadDepartments = tractorcontractService.queryleadDepartment();
       model.put("list",leadDepartments);
       return null;
   }


}
