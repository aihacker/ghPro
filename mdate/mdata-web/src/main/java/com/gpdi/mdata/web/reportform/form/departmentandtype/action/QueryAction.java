package com.gpdi.mdata.web.reportform.form.departmentandtype.action;

import com.gpdi.mdata.sys.entity.report.PurchaseContract;
import com.gpdi.mdata.sys.service.reportform.form.AllContractDealService;
import com.gpdi.mdata.web.reportform.data.QueryData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import pub.dao.query.PageSettings;
import pub.dao.query.QueryResult;
import pub.spring.web.mvc.ActionResult;
import pub.spring.web.mvc.model.Model;

import java.util.List;

/**
 * @description:查询合同数量明细
 * @author: WangXiaoGang
 * @data: Created in 2018/8/13 15:56
 * @modifier:
 */
@Controller
public class QueryAction {

    @Autowired
    private AllContractDealService allContractDealService;

    @RequestMapping
    public QueryResult execute(QueryData queryData, PageSettings settings, Model model){
        List<PurchaseContract> purList = allContractDealService.getScreenContractType(queryData);
        model.addAttribute("vale",purList);
        return allContractDealService.queryDepartmentAndType(queryData,settings);
    }

    @RequestMapping
    @ResponseBody
    public ActionResult test(){
        System.out.println();
        return ActionResult.ok();
    }


}
