package com.gpdi.mdata.web.reportform.exclude.reverse.action;


import com.gpdi.mdata.sys.service.reportform.mainriskexclude.BidderExcludeService;
import com.gpdi.mdata.sys.service.reportform.mainriskexclude.ReverseService;
import com.gpdi.mdata.sys.service.reportform.mainriskexclude.SingleSourceService;
import com.gpdi.mdata.web.reportform.data.QueryData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import pub.dao.query.PageSettings;
import pub.dao.query.QueryResult;
import pub.spring.web.mvc.model.Model;

import java.util.List;

/**
 * 排查是否存在倒签合同的情况：
 * 比对合同履约起始时间与合同签订时间的先后关系
 */
@Controller
public class QueryAction {
    @Autowired
    private ReverseService reverseService;


   @RequestMapping
    public QueryResult execute(QueryData queryData, PageSettings settings){
       return reverseService.queryReverseDate(queryData,settings);
   }

}
