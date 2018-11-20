package com.gpdi.mdata.web.reportform.info.tenderingrules.action;


import com.gpdi.mdata.sys.entity.report.FileRulers;
import com.gpdi.mdata.sys.entity.report.Legal;
import com.gpdi.mdata.sys.service.reportform.info.CompanyInfoService;
import com.gpdi.mdata.web.reportform.data.QueryData;
import com.gpdi.mdata.web.reportform.data.YtcardData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import pub.dao.query.PageSettings;
import pub.dao.query.QueryResult;
import pub.spring.web.mvc.model.Model;

import java.util.List;


/**
 * 采购方式适用列表
 */
@Controller
public class QueryAction {
    @Autowired
    private CompanyInfoService companyInfoService;

   @RequestMapping
    public QueryResult execute(YtcardData ytcardData, PageSettings settings, Model model){
       QueryResult queryResult = companyInfoService.queryTenderWay(ytcardData,settings);
       List<Legal> list = companyInfoService.queryTenderlegal(ytcardData);
       List<FileRulers> fileRulers = companyInfoService.queryFileRules(ytcardData);
       model.put("list",list);
       model.put("fileRulers",fileRulers);
       return queryResult;
   }


}
