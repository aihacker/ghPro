package com.gpdi.mdata.web.reportform.ytcard.carulestypein.action;

import com.gpdi.mdata.sys.service.reportform.ytcard.MainYtcardService;
import com.gpdi.mdata.web.reportform.data.YtcardData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import pub.dao.query.PageSettings;
import pub.dao.query.QueryResult;

/**
 * @description:保存车辆规则的数据
 * @author: zzy
 * @data:
 * @modifier:
 */
@Controller
public class QueryAction {
    @Autowired
    MainYtcardService ytcardService;
    @RequestMapping
    public QueryResult execute(YtcardData ytcardData, PageSettings settings, Model model, String params, String yearss){
        System.out.println(params);
        if (params!=null && !params.equals("")){
            ytcardService.savaCarRulesData(params);
        }

        QueryResult queryResult = ytcardService.queryMileageInfo( ytcardData, settings, yearss, params);
        return queryResult;
    }
}
