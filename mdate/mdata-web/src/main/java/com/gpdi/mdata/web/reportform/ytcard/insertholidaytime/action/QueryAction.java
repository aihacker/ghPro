package com.gpdi.mdata.web.reportform.ytcard.insertholidaytime.action;


import com.gpdi.mdata.sys.service.reportform.daoexcel.pdfscream.PdfExamineService;
import com.gpdi.mdata.web.reportform.data.QueryData;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import pub.dao.query.PageSettings;
import pub.dao.query.QueryResult;


/**
 * @author: zzy
 * @data: Created in 2018/10/16 11:45
 * @description:节假日录入功能
 */
@Controller
public class QueryAction {
    protected static final Logger log = Logger.getLogger(com.gpdi.mdata.web.reportform.daoexcel.examinepdf.action.QueryAction.class);
    @Autowired
    private PdfExamineService pdfExamineService;

   @RequestMapping
    public QueryResult execute(QueryData queryData, PageSettings settings, String params){
       System.out.println("==============="+params);

       if (params != null) {
           boolean result = pdfExamineService.insertHolidayTime(params);

       }

        return null;

      // return pdfExamineService.queryPdf(queryData,settings);
   }


}
