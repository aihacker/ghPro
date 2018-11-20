package com.gpdi.mdata.web.reportform.ytcard.mileageentryinterface.action;

import com.gpdi.mdata.sys.entity.report.MonthTime;
import com.gpdi.mdata.sys.service.reportform.ytcard.MainYtcardService;
import com.gpdi.mdata.web.reportform.data.YtcardData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import pub.dao.query.PageSettings;
import pub.dao.query.QueryResult;

import java.util.List;

/**
 * @description:车辆里程录入界面的数据
 * @author: zzy
 * @data:
 * @modifier:
 */
@Controller
public class QueryAction {
    @Autowired
    MainYtcardService ytcardService;
    @RequestMapping
    public QueryResult execute(YtcardData ytcardData, PageSettings settings, Model model, String params, String yearss, String arraystr){
        System.out.println(params);
        System.out.println(arraystr);
        if (params!=null && !params.equals("")){
            String carNatureStr= ytcardService.queryCarNature(params);//查询出车牌号对应的车辆性质
              model.addAttribute("carNatureStr",carNatureStr);
            String MonthOnMileageStr= ytcardService.queryMonthOnMileage(params);//查询出车牌号对应的月规定里程
            model.addAttribute("MonthOnMileageStr",MonthOnMileageStr);
            /*List<MonthTime> carNatureAndOil = ytcardService.queryCarNatureAndOil(params);//查询出车牌号对应的车辆性质
            model.addAttribute("carNatureAndOil",carNatureAndOil);*/
            String carOil= ytcardService.queryOil(params);//查询出车牌号对应的车辆性质
            model.addAttribute("carOil",carOil);
        }
        if (arraystr!=null && !arraystr.equals("")) {
            ytcardService.savaMileageEnterData(arraystr);//保存里程录入界面数据
        }
        List<MonthTime> allCarNumber = ytcardService.queryAllCarNumber(ytcardData, settings);//所有车牌号
        model.addAttribute("allCarNumber",allCarNumber);

        QueryResult queryResult = ytcardService.queryHistoryMileageEntry(ytcardData, settings);//历史记录
        return queryResult;
    }
}
