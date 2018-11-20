package com.gpdi.mdata.web.reportform.exclude.source.moneymax.action;


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
 * 合同金额最高的前10个项目
 */
@Controller
public class QueryAction {
    @Autowired
    private SingleSourceService singleSourceService;

   @RequestMapping
    public QueryResult execute(QueryData queryData, PageSettings settings, Model model){
       List<String> purList = singleSourceService.getAllPurchaseWay();
       model.put("typeList",purList);
       return singleSourceService.queryConstructMoneyMax(queryData,settings);
   }

}
