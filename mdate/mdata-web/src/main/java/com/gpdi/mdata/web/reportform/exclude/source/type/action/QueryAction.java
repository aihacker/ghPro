package com.gpdi.mdata.web.reportform.exclude.source.type.action;


import com.gpdi.mdata.sys.entity.report.PurchaseContract;
import com.gpdi.mdata.sys.service.reportform.mainriskexclude.SingleSourceService;
import com.gpdi.mdata.web.reportform.data.QueryData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import pub.dao.query.PageSettings;
import pub.dao.query.QueryResult;
import pub.spring.web.mvc.ActionResult;
import pub.spring.web.mvc.model.Model;

import java.util.List;

/**
 * 签订合同及金额最多的合同类型
 */
@Controller
public class QueryAction {
    @Autowired
    private SingleSourceService singleSourceService;

   @RequestMapping
    public QueryResult execute(QueryData queryData, PageSettings settings, Model model){
       List<String> purList = singleSourceService.getAllPurchaseWay();
       model.put("typeList",purList);
       return singleSourceService.queryConstructMoneyMostByType(queryData,settings);
   }

    @RequestMapping
    public ActionResult getModal(QueryData queryData) {
        ActionResult result = ActionResult.ok();
        List<PurchaseContract> list = singleSourceService.getCount(queryData);
        result.setData(list);
        return result;
    }

}
