package com.gpdi.mdata.web.reportform.ytcard.mileageinformation.action;

import com.gpdi.mdata.sys.service.reportform.ytcard.MainYtcardService;
import com.gpdi.mdata.web.reportform.data.YtcardData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import pub.dao.query.PageSettings;
import pub.dao.query.QueryResult;
import pub.spring.web.mvc.model.Model;

/**
 * @description:车辆出入公司停车场信息
 * @author: WangXiaoGang
 * @data: Created in 2018/10/9 16:19
 * @modifier:
 */
@Controller
public class QueryAction {
    @Autowired
    MainYtcardService ytcardService;
    @RequestMapping
    public QueryResult execute(YtcardData ytcardData, PageSettings settings, Model model, String params, String yearss, String litre){
        /*if (params !=null && !params.equals("")){
            model.put("litre",litre);
        }*/

        return ytcardService.queryMileageInfo(ytcardData,settings,yearss,params);
    }
}
