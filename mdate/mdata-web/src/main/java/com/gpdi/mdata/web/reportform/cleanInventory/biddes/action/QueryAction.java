package com.gpdi.mdata.web.reportform.cleanInventory.biddes.action;


import com.gpdi.mdata.sys.service.reportform.cleanInventory.CleanBidderExcludeService;
import com.gpdi.mdata.sys.service.reportform.mainriskexclude.BidderExcludeService;
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
 * 围标可能性排查:选取询价、比选、招标等采购方式的项目，
 * 比对参与项目竞逐的各个供应商的法定代表人、控股股东是否为同一人
 */
@Controller
public class QueryAction {

    @Autowired
    private CleanBidderExcludeService bidderExcludeService;

   @RequestMapping
    public QueryResult execute(QueryData queryData, PageSettings settings, Model model){
       return bidderExcludeService.cleanQueryBidders(queryData,settings);
   }


}
