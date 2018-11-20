package com.gpdi.mdata.web.reportform.exclude.dismantling.action;


import com.gpdi.mdata.sys.entity.report.CompareScore;
import com.gpdi.mdata.sys.entity.report.DismantTemp;
import com.gpdi.mdata.sys.entity.report.PurchaseContract;
import com.gpdi.mdata.sys.service.reportform.form.QuerySupByDeptService;
import com.gpdi.mdata.sys.service.reportform.form.RepPurchaseService;
import com.gpdi.mdata.sys.service.reportform.mainriskexclude.BidderExcludeService;
import com.gpdi.mdata.sys.service.reportform.mainriskexclude.DismantlingService;
import com.gpdi.mdata.sys.service.reportform.mainriskexclude.SingleSourceService;
import com.gpdi.mdata.web.reportform.data.QueryData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.context.request.ServletWebRequest;
import pub.dao.query.PageSettings;
import pub.dao.query.QueryResult;
import pub.spring.web.mvc.model.Model;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Collection;
import java.util.List;
import java.util.Locale;

/**
 * 化整为零拆单规避招标的可能性排查：
 * 通过项目名称的相似度分析；通过短时间内（如3个月内）同类型合同、相同入选供应商等信息分析；
 通过单一来源采购，分析项目前后关联性。
 */
@Controller
public class QueryAction {
   @Autowired
   private DismantlingService dismantlingService;
   @Autowired
   private RepPurchaseService repPurchaseService;
   @Autowired
   private QuerySupByDeptService querySupByDeptService;

   @RequestMapping
   public void execute(QueryData queryData, PageSettings settings, Model model){
      List<DismantTemp> list = dismantlingService.queryDismant(queryData,settings);
      model.put("queryResult",list);
      //获取合同类型
      List<PurchaseContract> contracts = repPurchaseService.getContractType();
      model.addAttribute("list", contracts);
      //获取经办部门
      List<PurchaseContract> deptList = querySupByDeptService.getAllDept();//获取所有部门
      model.put("deptList",deptList);

      List<String> purchaseWay=querySupByDeptService.getAllPurchaseWay();
      model.put("purchaseWay",purchaseWay);

      if(queryData.getIsExport()!=null && queryData.getIsExport()==1){
         if(list.size()>0){
            DismantlingWriteApi dismantling=new DismantlingWriteApi();
            dismantling.toExcel(list);
            HttpServletResponse response= ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getResponse();
            dismantling.response(response);
         }
      }
      // String zTree = dismantlingService.typeformTree();//获取合同类型结构
      // model.put("tree",zTree);
   }

}
