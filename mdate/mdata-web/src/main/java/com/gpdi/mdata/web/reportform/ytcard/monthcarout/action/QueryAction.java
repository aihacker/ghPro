package com.gpdi.mdata.web.reportform.ytcard.monthcarout.action;

import com.gpdi.mdata.sys.entity.report.MonthTime;
import com.gpdi.mdata.sys.service.reportform.ytcard.MainYtcardService;
import com.gpdi.mdata.web.reportform.data.YtcardData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import pub.dao.query.PageSettings;
import pub.dao.query.QueryResult;
import pub.spring.web.mvc.model.Model;

import java.util.List;

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
    public QueryResult execute(YtcardData ytcardData, PageSettings settings, Model model, String params, String yearss){
        System.out.println(params);
        System.out.println(yearss);
        List<MonthTime> temp = ytcardService.queryMonthOutInfo(params,yearss);//出入车场
        model.put("temp",temp);
        List<MonthTime> mileageTemp = ytcardService.queryMonthMileage(params,yearss);//里程
        model.put("mileageTemp",mileageTemp);
        //model
        System.out.println(params);
        if (params !=null && !params.equals("")){
            System.out.println(params);
            String carProperties= ytcardService.getCarNature(params);//车辆性质
            model.put("carProperties", carProperties);
//          String carProperties= ytcardService.getTotalRepairFee(params);//车辆性质
            Double carRepairFee= ytcardService.getCarRepairFee(params);//修车费用
            model.put("carRepairFee", carRepairFee);
        }
        //model
        List<MonthTime> oilCcarTemp = ytcardService.queryMonthOilCard(params,yearss);//油卡
        model.put("oilCcarTemp",oilCcarTemp);
        List<MonthTime> axleFeeTemp = ytcardService.queryMonthAxleFee(params,yearss);//车桥费
        model.put("axleFeeTemp",axleFeeTemp);
        List<MonthTime> repairingTemp = ytcardService.queryMonthRepairing(params,yearss);//修车
        model.put("repairingTemp",repairingTemp);
        List<MonthTime> deregulationTemp = ytcardService.queryMonthMileageDeregulation(params,yearss);//里程违规
        model.put("deregulationTemp",deregulationTemp);
       QueryResult queryResult = ytcardService.queryMileageInfo( ytcardData, settings, yearss, params);
        return queryResult;
    }
}
