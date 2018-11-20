package com.gpdi.mdata.sys.service.reportform.ytcard;

import com.gpdi.mdata.sys.entity.report.MonthTime;
import com.gpdi.mdata.web.reportform.data.YtcardData;
import pub.dao.query.PageSettings;
import pub.dao.query.QueryResult;

import java.util.List;

/**
 * @description:粤通卡主要相关功能业务
 * @author: WangXiaoGang
 * @data: Created in 2018/10/9 16:26
 * @modifier:
 */
public interface MainYtcardService {

    /**
     * 查询佛山本部粤通卡号
     * @param ytcardData
     * @param settings
     * @return
     */
    QueryResult queryAscriptions(YtcardData ytcardData, PageSettings settings);

    /**
     * 查看车牌号和卡号对应关系表
     * @param ytcardData
     * @param settings
     * @return
     */
    QueryResult queryPlatenumber(YtcardData ytcardData, PageSettings settings);

    /**
     * 查看车辆出入停车场信息
     * @param ytcardData
     * @param settings
     * @return
     */
    //月份违规出入车场记录查看
    QueryResult queryOutInfo(YtcardData ytcardData, PageSettings settings, String yearss, String params);

    //根据月份查出每个月对应的油卡费记录
    QueryResult queryOilCardInfo(YtcardData ytcardData, PageSettings settings, String yearss, String params);

    //根据月份查出每个月对应的车桥费记录
    QueryResult queryAxleFeeInfo(YtcardData ytcardData, PageSettings settings, String yearss, String params);

    //月份里程信息查看
    QueryResult queryMileageInfo(YtcardData ytcardData, PageSettings settings, String yearss, String params);

    //月份修车信息查看
    QueryResult queryRepairingInfo(YtcardData ytcardData, PageSettings settings, String yearss, String params);

    //====================================================================================
    //根据月份查出每个月对应的违规出入车场记录
    List<MonthTime> queryMonthOutInfo(String params, String yearss);
    //根据月份查出每个月对应的里程记录
    List<MonthTime> queryMonthMileage(String params, String yearss);
    //根据月份查出每个月对应的油卡记录
    List<MonthTime> queryMonthOilCard(String params, String yearss);
    //根据月份查出每个月对应的车桥费记录
    List<MonthTime> queryMonthAxleFee(String params, String yearss);
    //根据月份查出每个月对应的修车记录
    List<MonthTime> queryMonthRepairing(String params, String yearss);
    //根据月份查出每个月里程疑似违规的记录
    List<MonthTime> queryMonthMileageDeregulation(String params, String yearss);
//=======================================================================================
    //保存里程录入界面的数据
    boolean  saveMileageData(String params);
    //保存修车费录入界面的数据
    boolean  saveCarRepairingData(String params);
    //根据车牌号查出车的性质
    String getCarNature(String params);
    //根据车牌号查出修车费
    Double getCarRepairFee(String params);

//============================================================================================
    //保存车辆规则界面的数据
    boolean savaCarRulesData(String params);

//=============================================================================================
    //车辆里程录入界面-历史记录的查询
    QueryResult queryHistoryMileageEntry(YtcardData ytcardData, PageSettings settings);
    //查询所有的车牌号
    List<MonthTime> queryAllCarNumber(YtcardData ytcardData, PageSettings settings);
    //查询车牌号对应的汽车的车辆性质
    String queryCarNature(String params);
    //查询车牌号对应的汽车的月规定里程
    String queryMonthOnMileage(String params);
    //查询车牌号对应的汽车的油耗
    String queryOil(String params);
    //查询车牌号对应的汽车的车辆性质和油耗
    /*List<MonthTime> queryCarNatureAndOil(String params);*/
    //保存里程录入界面数据
    boolean savaMileageEnterData(String arraystr);
}
