package com.gpdi.mdata.web.reportform.daoexcel.updata.action;


import com.gpdi.mdata.sys.service.reportform.mainriskexclude.SingleService;
import com.gpdi.mdata.web.reportform.data.QueryData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import pub.dao.query.PageSettings;
import pub.dao.query.QueryResult;
import pub.spring.web.mvc.ActionResult;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 根据一段时间更新数据
 */
@Controller
public class QueryAction {
    @Autowired
    private SingleService singleService;


   @RequestMapping
    public QueryResult execute(QueryData queryData, PageSettings settings){
       return null;
//       return singleService.queryReverseDate(queryData,settings);
   }

    @RequestMapping
    public ActionResult updata(HttpServletRequest request, HttpServletResponse response){
        String startTime = request.getParameter("startTime");
        String endTime = request.getParameter("endTime");
        System.out.println("startTime:"+startTime+"+++endTime:"+endTime);
        return ActionResult.ok();
    }



}
