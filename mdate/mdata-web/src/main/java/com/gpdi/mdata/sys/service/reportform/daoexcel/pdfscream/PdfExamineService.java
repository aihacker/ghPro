package com.gpdi.mdata.sys.service.reportform.daoexcel.pdfscream;


import com.gpdi.mdata.web.reportform.data.QueryData;
import pub.dao.query.QueryResult;
import pub.dao.query.QuerySettings;

/**
 * @description:
 * @author: zzy
 * @data: Created in 2018/7/17 20:55
 * @modifier:
 */
public interface PdfExamineService {
        QueryResult queryPdf(QueryData queryData, QuerySettings settings);

        boolean insertHolidayTime(String params);

}
