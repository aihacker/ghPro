package com.gpdi.mdata.web.reportform.daoexcel.examinepdf.action;


import com.gpdi.mdata.sys.service.reportform.daoexcel.pdfscream.PdfExamineService;
import com.gpdi.mdata.web.reportform.data.QueryData;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import pub.dao.query.PageSettings;
import pub.dao.query.QueryResult;


/**
 * @author: zhangzheyuan
 * @data: Created in 2018/10/16 11:34
 * @description:PDF查看
 */
@Controller
public class QueryAction {
    protected static final Logger log = Logger.getLogger(QueryAction.class);
    @Autowired
    private PdfExamineService pdfExamineService;

    @RequestMapping
    public QueryResult execute(QueryData queryData, PageSettings settings){
        return pdfExamineService.queryPdf(queryData,settings);
    }


}
