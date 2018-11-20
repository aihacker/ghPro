package com.gpdi.mdata.web.reportform.form.contractlessthreebytype.action;


import com.gpdi.mdata.sys.entity.report.PurchaseContract;
import com.gpdi.mdata.sys.service.reportform.form.RepPurchaseService;
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
 * 按合同类型分类,签订合同数量不超过3个的供应商
 */
@Controller
public class QueryAction {
    @Autowired
    private RepPurchaseService repPurchaseService;

    /*@RequestMapping
    public void execute(Model model) {
        List<PurchaseContract> contracts = repPurchaseService.getContractType();
        model.addAttribute("list", contracts);
    }*/
    @RequestMapping
    public QueryResult execute(QueryData queryData, PageSettings settings, Model model) {
        List<PurchaseContract> contracts = repPurchaseService.getContractType();
        model.addAttribute("list", contracts);
        return repPurchaseService.queryContractLeastByType(queryData,settings);
    }
    @RequestMapping
    public ActionResult conType(QueryData queryData) {
        ActionResult result = ActionResult.ok();
        List<PurchaseContract> list = repPurchaseService.getCountByType(queryData);
        result.setData(list);
        return result;
    }
}