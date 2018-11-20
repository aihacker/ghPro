package com.gpdi.mdata.web.reportform.cleanInventory.dismant.action;


import com.gpdi.mdata.sys.entity.report.DismantTemp;
import com.gpdi.mdata.sys.entity.report.PurchaseContract;
import com.gpdi.mdata.sys.service.reportform.cleanInventory.CleanDismantlingService;
import com.gpdi.mdata.sys.service.reportform.form.QuerySupByDeptService;
import com.gpdi.mdata.sys.service.reportform.form.RepPurchaseService;
import com.gpdi.mdata.sys.service.reportform.mainriskexclude.DismantlingService;
import com.gpdi.mdata.web.reportform.data.QueryData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import pub.dao.query.PageSettings;
import pub.spring.web.mvc.model.Model;

import java.util.List;

/**
 * 即时清结拆单
 */
@Controller
public class QueryAction {
   @Autowired
   private CleanDismantlingService cleanDismantlingService;

   @RequestMapping
   public void  execute(QueryData queryData, PageSettings settings, Model model){
      List<DismantTemp> list = cleanDismantlingService.queryDismant(queryData,settings);
      model.put("queryResult",list);

      //获取物料名称
      List<String> contracts = cleanDismantlingService.goodsType();
      model.addAttribute("list", contracts);
      //获取经办部门
      List<String> deptList = cleanDismantlingService.queryDept();//获取所有部门
      model.put("deptList",deptList);
   }

}
