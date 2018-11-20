package com.gpdi.mdata.web.reportform.info.companyinfo.action;


import com.gpdi.mdata.sys.entity.report.PurchaseContract;
import com.gpdi.mdata.sys.service.reportform.form.RepPurchaseService;
import com.gpdi.mdata.sys.service.reportform.info.CompanyInfoService;
import com.gpdi.mdata.web.reportform.data.QueryData;
import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.Row;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import pub.dao.query.PageSettings;
import pub.dao.query.QueryResult;
import pub.spring.web.mvc.ActionResult;
import pub.spring.web.mvc.model.Model;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * 获取公开采购投标公司信息
 */
@Controller
public class QueryAction {
    @Autowired
    private CompanyInfoService companyInfoService;

   @RequestMapping
    public QueryResult execute(QueryData queryData, PageSettings settings){
       QueryResult queryResult = companyInfoService.query(queryData,settings);
       /*List<String> list3 = new ArrayList<>();
       Map<String,String> map = new HashMap<String,String>();
      List<Map<String,String>> rows =  queryResult.getRows();
        for (int i=0;i<rows.size();i++){
            Map<String,String> map2 = rows.get(i);

            for (String key : map2.keySet()) {
                System.out.println("key= "+ key + " and value= " + map2.get(key));
            }
                System.out.println("-----------");

        }*/
       return queryResult;
   }


}
