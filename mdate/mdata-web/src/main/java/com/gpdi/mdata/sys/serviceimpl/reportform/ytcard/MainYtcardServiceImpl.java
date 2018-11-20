package com.gpdi.mdata.sys.serviceimpl.reportform.ytcard;

import com.gpdi.mdata.sys.entity.report.MonthTime;
import com.gpdi.mdata.sys.service.reportform.ytcard.MainYtcardService;
import com.gpdi.mdata.web.reportform.data.YtcardData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pub.dao.GeneralDao;
import pub.dao.jdbc.dialect.mysql.PagedQuery;
import pub.dao.query.PageSettings;
import pub.dao.query.Query;
import pub.dao.query.QueryResult;
import pub.functions.StrFuncs;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @description:粤通卡功能业务
 * @author: WangXiaoGang
 * @data: Created in 2018/10/9 16:40
 * @modifier:
 */
@Service
@Transactional(readOnly = false)//该注解表示该类里面的所有方法或者这个方法的事务由spring处理,来保证事务的原子性
public class MainYtcardServiceImpl implements MainYtcardService {

    @Autowired
    GeneralDao generalDao;

    //================查询佛山本部粤通卡号==========================
    @Override
    public QueryResult queryAscriptions(YtcardData ytcardData, PageSettings settings) {
        Query query = new PagedQuery(settings);
        query.select("*").from("t_card_location");
        generalDao.execute(query);
        QueryResult queryResult = query.getResult();
        return queryResult;
    }
    //================查看车牌号和卡号对应关系表==========================
    @Override
    public QueryResult queryPlatenumber(YtcardData ytcardData, PageSettings settings) {
        Query query = new PagedQuery(settings);
        query.select("*").from("t_card_relation");
        generalDao.execute(query);
        QueryResult queryResult = query.getResult();
        return queryResult;
    }

    //================查看车辆出入停车场信息==========================
    @Override
    public QueryResult queryOutInfo(YtcardData ytcardData, PageSettings settings, String yearss, String params) {
        int pageMonth = ytcardData.getPageMonth();//获取前台的月份
        String pageYear = yearss;//获取前台下拉框的年份
        String pageCarNumber = params;//获取前台输入框的车牌号
        Query query = new PagedQuery(settings);

        if (StrFuncs.notEmpty(pageYear)  && StrFuncs.notEmpty(pageCarNumber)) {
            if (pageMonth > 9) {//车牌号和年份都不为空的时间执行下来
                query.select("*").from("t_car_out_information x").where("((CASE WHEN (SELECT COUNT(1) FROM t_holiday_add h WHERE h.start_time <= date(x.playing_time) AND h.end_time >= date(x.playing_time)) >= 1 THEN '非工作日' ELSE CASE WHEN DAYNAME(x.playing_time) IN ('Saturday', 'Sunday') THEN '非工作日' ELSE '工作日' END END) = '非工作日' AND playing_time LIKE '"+pageYear+"-"+pageMonth+"-%' AND license_number = '"+pageCarNumber+"') OR ((CASE WHEN (SELECT 1 FROM t_holiday_add h WHERE h.start_time <= date(x.playing_time) AND h.end_time >= date(x.playing_time)) >= 1 THEN '非工作日' ELSE CASE WHEN DAYNAME(x.playing_time) IN ('Saturday', 'Sunday') THEN '非工作日' ELSE '工作日' END END) = '工作日' AND ((HOUR (playing_time) = 17 AND MINUTE (playing_time) >= 30) OR HOUR (playing_time) > 17) AND playing_time LIKE '"+pageYear+"-"+pageMonth+"-%' AND license_number = '"+pageCarNumber+"' OR MONTH (playing_time) < MONTH (come_back) AND playing_time LIKE '"+pageYear+"-"+pageMonth+"-%' AND license_number = '"+pageCarNumber+"' OR DAY (playing_time) < DAY (come_back) AND playing_time LIKE '"+pageYear+"-"+pageMonth+"-%' AND license_number = '"+pageCarNumber+"' OR come_back = '1900-01-01 00:00:00' AND playing_time LIKE '"+pageYear+"-"+pageMonth+"-%' AND license_number = '"+pageCarNumber+"')");
            }else {
                query.select("*").from("t_car_out_information x").where("((CASE WHEN (SELECT COUNT(1) FROM t_holiday_add h WHERE h.start_time <= date(x.playing_time) AND h.end_time >= date(x.playing_time)) >= 1 THEN '非工作日' ELSE CASE WHEN DAYNAME(x.playing_time) IN ('Saturday', 'Sunday') THEN '非工作日' ELSE '工作日' END END) = '非工作日' AND playing_time LIKE '"+pageYear+"-0"+pageMonth+"-%' AND license_number = '"+pageCarNumber+"') OR ((CASE WHEN (SELECT 1 FROM t_holiday_add h WHERE h.start_time <= date(x.playing_time) AND h.end_time >= date(x.playing_time)) >= 1 THEN '非工作日' ELSE CASE WHEN DAYNAME(x.playing_time) IN ('Saturday', 'Sunday') THEN '非工作日' ELSE '工作日' END END) = '工作日' AND ((HOUR (playing_time) = 17 AND MINUTE (playing_time) >= 30) OR HOUR (playing_time) > 17) AND playing_time LIKE '"+pageYear+"-0"+pageMonth+"-%' AND license_number = '"+pageCarNumber+"' OR MONTH (playing_time) < MONTH (come_back) AND playing_time LIKE '"+pageYear+"-0"+pageMonth+"-%' AND license_number = '"+pageCarNumber+"' OR DAY (playing_time) < DAY (come_back) AND playing_time LIKE '"+pageYear+"-0"+pageMonth+"-%' AND license_number = '"+pageCarNumber+"' OR come_back = '1900-01-01 00:00:00' AND playing_time LIKE '"+pageYear+"-0"+pageMonth+"-%' AND license_number = '"+pageCarNumber+"')");
            }
        }else{
            if (pageMonth > 9) {//车牌号和年份都为空的时间执行下来sql
                query.select("*").from("t_car_out_information x").where("((CASE WHEN (SELECT COUNT(1) FROM t_holiday_add h WHERE h.start_time <= date(x.playing_time) AND h.end_time >= date(x.playing_time)) >= 1 THEN '非工作日' ELSE CASE WHEN DAYNAME(x.playing_time) IN ('Saturday', 'Sunday') THEN '非工作日' ELSE '工作日' END END) = '非工作日' AND playing_time LIKE '2018-"+pageMonth+"-%') OR ((CASE WHEN (SELECT 1 FROM t_holiday_add h WHERE h.start_time <= date(x.playing_time) AND h.end_time >= date(x.playing_time)) >= 1 THEN '非工作日' ELSE CASE WHEN DAYNAME(x.playing_time) IN ('Saturday', 'Sunday') THEN '非工作日' ELSE '工作日' END END) = '工作日' AND ((HOUR (playing_time) = 17 AND MINUTE (playing_time) >= 30) OR HOUR (playing_time) > 17) AND playing_time LIKE '2018-"+pageMonth+"-%' OR MONTH (playing_time) < MONTH (come_back) AND playing_time LIKE '2018-"+pageMonth+"-%' OR DAY (playing_time) < DAY (come_back) AND playing_time LIKE '2018-"+pageMonth+"-%' OR come_back = '1900-01-01 00:00:00' AND playing_time LIKE '2018-"+pageMonth+"-%')");
            }else {
                query.select("*").from("t_car_out_information x").where("((CASE WHEN (SELECT COUNT(1) FROM t_holiday_add h WHERE h.start_time <= date(x.playing_time) AND h.end_time >= date(x.playing_time)) >= 1 THEN '非工作日' ELSE CASE WHEN DAYNAME(x.playing_time) IN ('Saturday', 'Sunday') THEN '非工作日' ELSE '工作日' END END) = '非工作日' AND playing_time LIKE '2018-0"+pageMonth+"-%') OR ((CASE WHEN (SELECT 1 FROM t_holiday_add h WHERE h.start_time <= date(x.playing_time) AND h.end_time >= date(x.playing_time)) >= 1 THEN '非工作日' ELSE CASE WHEN DAYNAME(x.playing_time) IN ('Saturday', 'Sunday') THEN '非工作日' ELSE '工作日' END END) = '工作日' AND ((HOUR (playing_time) = 17 AND MINUTE (playing_time) >= 30) OR HOUR (playing_time) > 17) AND playing_time LIKE '2018-0"+pageMonth+"-%' OR MONTH (playing_time) < MONTH (come_back) AND playing_time LIKE '2018-0"+pageMonth+"-%' OR DAY (playing_time) < DAY (come_back) AND playing_time LIKE '2018-0"+pageMonth+"-%' OR come_back = '1900-01-01 00:00:00' AND playing_time LIKE '2018-0"+pageMonth+"-%')");
            }
        }

        generalDao.execute(query);
        QueryResult queryResult = query.getResult();
        //12345678
        String sql ="";
        String date = null;
        List<YtcardData> temp = new ArrayList();
        List allList = new ArrayList();
        temp = (List) queryResult.getValue();
        for (int a = 0;a < temp.size(); a++) {
            List temp2 = Collections.singletonList(temp.get(a));
            String playingtime = (String) ((Map)temp2.get(0)).get("playing_time");
            sql = "select holidays_types as holidayTypes from t_holiday_add where start_time<= date('"+playingtime+"') AND end_time>=date('"+playingtime+"')";
            allList = generalDao.queryList(YtcardData.class,sql);
            if (allList.size()==0){
                //===============================判断是星期几(头)==================================================
                sql = "SELECT DAYNAME ("+"'"+playingtime+""+"') as workinDayType";
                allList = generalDao.queryList(YtcardData.class,sql);
                YtcardData pdfTotalBean1 = (YtcardData) allList.get(0);
                ((Map) temp2.get(0)).put("week",pdfTotalBean1.getWorkinDayType());
                //===============================判断是星期几(尾)===============================================

            }else {
                YtcardData pdfTotalBean = (YtcardData) allList.get(0);
                ((Map) temp2.get(0)).put("week",pdfTotalBean.getHolidayTypes());

            }


        }
        //12345678
        //增加两个时间相差多少小时功能
        String sqlr ="";
        String dater = null;
        List<YtcardData> tempr = new ArrayList();
        List allListr = new ArrayList();
        tempr = (List) queryResult.getValue();
        for (int a = 0;a < tempr.size(); a++) {
            List temp2r = Collections.singletonList(tempr.get(a));
            String playingtime = (String) ((Map)temp2r.get(0)).get("playing_time");
            String comebacktime = (String) ((Map)temp2r.get(0)).get("come_back");
            sqlr = "SELECT TIMESTAMPDIFF(HOUR,'"+playingtime+"','"+comebacktime+"') as workindaytype";
            allListr = generalDao.queryList(YtcardData.class,sqlr);
            YtcardData pdfTotalBean1r = (YtcardData) allListr.get(0);
            ((Map) temp2r.get(0)).put("hour",pdfTotalBean1r.getWorkinDayType());
        }
        //增加两个时间相差多少小时功能
        return queryResult;
    }
    ////月份里程信息查看
    @Override
    public QueryResult queryMileageInfo(YtcardData ytcardData, PageSettings settings, String yearss, String params) {
        int pageMonth = ytcardData.getPageMonth();//获取前台的月份
        String pageYear = yearss;//获取前台下拉框的年份
        String pageCarNumber = params;//获取前台输入框的车牌号
        Query query = new PagedQuery(settings);
        if (StrFuncs.notEmpty(pageYear)  && StrFuncs.notEmpty(pageCarNumber)) {
            if (pageMonth > 9) {//车牌号和年份都不为空的时间执行下来sql
//                query.select("*,(mileage /(endmileage-startmileage)) AS msc").from("t_mileage").where("month LIKE '"+pageYear+"-"+pageMonth+"-%' and carnumber ='"+pageCarNumber+"'");
//                query.select("a.id,a.license_plate_number,a.car_nature,a.mileage_driven_time,a.month_driving_mileage,a.oil_consumption,a.bridge_cash_charge,a.month_mileage_difference,(month_mileage_difference/oil_consumption) as msca").from("t_month_mileage_entry a,t_car_basic_type b").where("a.license_plate_number=b.car_number AND a.mileage_driven_time LIKE '"+pageYear+"-"+pageMonth+"-%' and a.license_plate_number='"+pageCarNumber+"'");
                query.select("a.id,a.license_plate_number,a.car_nature,a.mileage_driven_time,a.month_driving_mileage,a.oil_consumption,a.bridge_cash_charge,a.month_mileage_difference,(SELECT SUM(c.number) AS litre FROM t_iccard_machine_bill c,t_card_relation d WHERE c.date LIKE '2018-"+pageMonth+"-%' AND d.card_number = c.card_number AND d.license_plate_number = a.license_plate_number) as litre,b.production_time,b.car_type,b.out_oil,b.monthly_mileage").from("t_month_mileage_entry a,t_car_basic_type b").where("a.license_plate_number = b.car_number AND a.mileage_driven_time LIKE '2018-"+pageMonth+"-%' and a.license_plate_number='"+pageCarNumber+"'");/*,b.oil_consumption_rule as mscb*/

            }else {
//                query.select("*,(mileage /(endmileage-startmileage)) AS msc").from("t_mileage").where("month LIKE '"+pageYear+"-0"+pageMonth+"-%' and carnumber ='"+pageCarNumber+"'");
//                query.select("a.id,a.license_plate_number,a.car_nature,a.mileage_driven_time,a.month_driving_mileage,a.oil_consumption,a.bridge_cash_charge,a.month_mileage_difference,(month_mileage_difference/oil_consumption) as msca").from("t_month_mileage_entry a,t_car_basic_type b").where("a.license_plate_number=b.car_number AND a.mileage_driven_time LIKE '"+pageYear+"-0"+pageMonth+"-%' and a.license_plate_number='"+pageCarNumber+"'");
                query.select("a.id,a.license_plate_number,a.car_nature,a.mileage_driven_time,a.month_driving_mileage,a.oil_consumption,a.bridge_cash_charge,a.month_mileage_difference,(SELECT SUM(c.number) AS litre FROM t_iccard_machine_bill c,t_card_relation d WHERE c.date LIKE '2018-0"+pageMonth+"-%' AND d.card_number = c.card_number AND d.license_plate_number = a.license_plate_number) as litre,b.production_time,b.car_type,b.out_oil,b.monthly_mileage").from("t_month_mileage_entry a,t_car_basic_type b").where("a.license_plate_number = b.car_number AND a.mileage_driven_time LIKE '2018-0"+pageMonth+"-%' and a.license_plate_number='"+pageCarNumber+"'");/*,b.oil_consumption_rule as mscb*/

            }
        }else{
            if (pageMonth > 9) {//车牌号和年份都为空的时间执行下来sql
//                query.select("a.id,a.license_plate_number,a.car_nature,a.mileage_driven_time,a.month_driving_mileage,a.oil_consumption,a.bridge_cash_charge,a.month_mileage_difference,(month_mileage_difference/oil_consumption) as msca").from("t_month_mileage_entry a,t_car_basic_type b").where("a.license_plate_number=b.car_number AND a.mileage_driven_time LIKE '2018-"+pageMonth+"-%'");
                query.select("a.id,a.license_plate_number,a.car_nature,a.mileage_driven_time,a.month_driving_mileage,a.oil_consumption,a.bridge_cash_charge,a.month_mileage_difference,(SELECT SUM(c.number) AS litre FROM t_iccard_machine_bill c,t_card_relation d WHERE c.date LIKE '2018-"+pageMonth+"-%' AND d.card_number = c.card_number AND d.license_plate_number = a.license_plate_number) as litre,b.production_time,b.car_type,b.out_oil,b.monthly_mileage").from("t_month_mileage_entry a,t_car_basic_type b").where("a.license_plate_number = b.car_number AND a.mileage_driven_time LIKE '2018-"+pageMonth+"-%'");/*,b.oil_consumption_rule as mscb*/
            }else {
//                query.select("*,(mileage /(endmileage-startmileage)) AS msc").from("t_mileage").where("month LIKE '2018-0"+pageMonth+"-%'");
//                query.select("a.id,a.license_plate_number,a.car_nature,a.mileage_driven_time,a.month_driving_mileage,a.oil_consumption,a.bridge_cash_charge,a.month_mileage_difference,(month_mileage_difference/oil_consumption) as msca").from("t_month_mileage_entry a,t_car_basic_type b").where("a.license_plate_number=b.car_number AND a.mileage_driven_time LIKE '2018-0"+pageMonth+"-%'");/*,b.oil_consumption_rule as mscb*/
                query.select("a.id,a.license_plate_number,a.car_nature,a.mileage_driven_time,a.month_driving_mileage,a.oil_consumption,a.bridge_cash_charge,a.month_mileage_difference,(SELECT SUM(c.number) AS litre FROM t_iccard_machine_bill c,t_card_relation d WHERE c.date LIKE '2018-0"+pageMonth+"-%' AND d.card_number = c.card_number AND d.license_plate_number = a.license_plate_number) as litre,b.production_time,b.car_type,b.out_oil,b.monthly_mileage").from("t_month_mileage_entry a,t_car_basic_type b").where("a.license_plate_number = b.car_number AND a.mileage_driven_time LIKE '2018-0"+pageMonth+"-%'");/*,b.oil_consumption_rule as mscb*/

            }
        }
        generalDao.execute(query);
        QueryResult queryResult = query.getResult();
        return queryResult;
    }

    //月份修车信息查看
    @Override
    public QueryResult queryRepairingInfo(YtcardData ytcardData, PageSettings settings, String yearss, String params) {
        int pageMonth = ytcardData.getPageMonth();//获取前台的月份
        String pageYear = yearss;//获取前台下拉框的年份
        String pageCarNumber = params;//获取前台输入框的车牌号
        Query query = new PagedQuery(settings);
        if (StrFuncs.notEmpty(pageYear)  && StrFuncs.notEmpty(pageCarNumber)) {
            if (pageMonth > 9) {//车牌号和年份都不为空的时间执行下来sql
                query.select("*").from("t_car_repairing").where("repair_time LIKE '"+pageYear+"-"+pageMonth+"-%' and license_plate_number ='"+pageCarNumber+"'");
            }else {
                query.select("*").from("t_car_repairing").where("repair_time LIKE '"+pageYear+"-0"+pageMonth+"-%' and license_plate_number ='"+pageCarNumber+"'");
            }
        }else{
            if (pageMonth > 9) {//车牌号和年份都为空的时间执行下来sql
                query.select("*").from("t_car_repairing").where("repair_time LIKE '2018-"+pageMonth+"-%'");
            }else {
                query.select("*").from("t_car_repairing").where("repair_time LIKE '2018-0"+pageMonth+"-%'");
            }
        }
        generalDao.execute(query);
        QueryResult queryResult = query.getResult();
        return queryResult;
    }

    //根据月份查出每个月对应的油卡费记录
    @Override
    public QueryResult queryOilCardInfo(YtcardData ytcardData, PageSettings settings, String yearss, String params) {
        int pageMonth = ytcardData.getPageMonth();//获取前台的月份
        String pageYear = yearss;//获取前台下拉框的年份
        String pageCarNumber = params;//获取前台输入框的车牌号
        Query query = new PagedQuery(settings);
        if (StrFuncs.notEmpty(pageYear)  && StrFuncs.notEmpty(pageCarNumber)) {
            if (pageMonth > 9) {//车牌号和年份都不为空的时间执行下来sql
                query.select("a.card_id,a.card_number,a.date,a.business_type,a.variety,a.number,a.unit_price,a.sum,a.reward_points,a.preferential_price,a.balance,a.site,a.operator,a.remark,b.license_plate_number").from("t_iccard_machine_bill a,t_card_relation b").where("a.date LIKE '"+pageYear+"-"+pageMonth+"-%' AND a.card_number=b.card_number and b.license_plate_number='"+pageCarNumber+"'");
            }else {
                query.select("a.card_id,a.card_number,a.date,a.business_type,a.variety,a.number,a.unit_price,a.sum,a.reward_points,a.preferential_price,a.balance,a.site,a.operator,a.remark,b.license_plate_number").from("t_iccard_machine_bill a,t_card_relation b").where("a.date LIKE '"+pageYear+"-0"+pageMonth+"-%' AND a.card_number=b.card_number and b.license_plate_number='"+pageCarNumber+"'");
            }
        }else{
            if (pageMonth > 9) {//车牌号和年份都为空的时间执行下来sql
                query.select("a.card_id,a.card_number,a.date,a.business_type,a.variety,a.number,a.unit_price,a.sum,a.reward_points,a.preferential_price,a.balance,a.site,a.operator,a.remark,b.license_plate_number").from("t_iccard_machine_bill a,t_card_relation b").where("a.date LIKE '2018-"+pageMonth+"-%' AND a.card_number=b.card_number");
            }else {
                query.select("a.card_id,a.card_number,a.date,a.business_type,a.variety,a.number,a.unit_price,a.sum,a.reward_points,a.preferential_price,a.balance,a.site,a.operator,a.remark,b.license_plate_number").from("t_iccard_machine_bill a,t_card_relation b").where("a.date LIKE '2018-0"+pageMonth+"-%' AND a.card_number=b.card_number");
            }
        }
        generalDao.execute(query);
        QueryResult queryResult = query.getResult();
        //12345678
        String sql ="";
        String date = null;
        List<YtcardData> temp = new ArrayList();
        List allList = new ArrayList();
        temp = (List) queryResult.getValue();
        for (int a = 0;a < temp.size(); a++) {
            List temp2 = Collections.singletonList(temp.get(a));
            String playingtime = (String) ((Map)temp2.get(0)).get("date");
            StringBuilder playingtime1 = new StringBuilder(playingtime);
            playingtime1.insert(10, " ");
            System.out.println(playingtime1);
            sql = "select holidays_types as holidayTypesyk from t_holiday_add where start_time<= date('"+playingtime1+"') AND end_time>=date('"+playingtime1+"')";
            allList = generalDao.queryList(YtcardData.class,sql);
            if (allList.size()==0){
                //===============================判断是星期几(头)==================================================
                sql = "SELECT DAYNAME ("+"'"+playingtime1+""+"') as workinDayTypeyk";
                System.out.println(sql);
                allList = generalDao.queryList(YtcardData.class,sql);
                YtcardData pdfTotalBean1 = (YtcardData) allList.get(0);
                ((Map) temp2.get(0)).put("weekyk",pdfTotalBean1.getWorkinDayTypeyk());
                //===============================判断是星期几(尾)===============================================

            }else {
                YtcardData pdfTotalBean = (YtcardData) allList.get(0);
                ((Map) temp2.get(0)).put("weekyk",pdfTotalBean.getHolidayTypesyk());

            }


        }
        //12345678
        return queryResult;
    }
    //根据月份查出每个月对应的车桥费记录
    @Override
    public QueryResult queryAxleFeeInfo(YtcardData ytcardData, PageSettings settings, String yearss, String params) {
        int pageMonth = ytcardData.getPageMonth();//获取前台的月份
        String pageYear = yearss;//获取前台下拉框的年份
        String pageCarNumber = params;//获取前台输入框的车牌号
        Query query = new PagedQuery(settings);
        if (StrFuncs.notEmpty(pageYear)  && StrFuncs.notEmpty(pageCarNumber)) {
            if (pageMonth > 9) {//车牌号和年份都不为空的时间执行下来sql
                query.select("*").from("(SELECT detail_id,invoice_code,invoice_number,invoice_sum,through_city,serial_number,deal_time,entrance,exit_s,split_sum,deal_sum,etc_card_number,licence_plate_number FROM t_pdf_through_detail WHERE deal_sum not like '%同%') a").where("deal_time LIKE '"+pageYear+"-"+pageMonth+"-%' and licence_plate_number = '"+pageCarNumber+"'");
            }else {
                query.select("*").from("(SELECT detail_id,invoice_code,invoice_number,invoice_sum,through_city,serial_number,deal_time,entrance,exit_s,split_sum,deal_sum,etc_card_number,licence_plate_number FROM t_pdf_through_detail WHERE deal_sum not like '%同%') a").where("deal_time LIKE '"+pageYear+"-0"+pageMonth+"-%' and licence_plate_number = '"+pageCarNumber+"'");
            }
        }else{
            if (pageMonth > 9) {//车牌号和年份都为空的时间执行下来sql
                query.select("*").from("(SELECT detail_id,invoice_code,invoice_number,invoice_sum,through_city,serial_number,deal_time,entrance,exit_s,split_sum,deal_sum,etc_card_number,licence_plate_number FROM t_pdf_through_detail WHERE deal_sum not like '%同%') a").where("deal_time LIKE '2018-"+pageMonth+"-%'");
            }else {
                query.select("*").from("(SELECT detail_id,invoice_code,invoice_number,invoice_sum,through_city,serial_number,deal_time,entrance,exit_s,split_sum,deal_sum,etc_card_number,licence_plate_number FROM t_pdf_through_detail WHERE deal_sum not like '%同%') a").where("deal_time LIKE '2018-0"+pageMonth+"-%'");
            }
        }
        generalDao.execute(query);
        QueryResult queryResult = query.getResult();
        //12345678
        String sql ="";
        String date = null;
        List<YtcardData> temp = new ArrayList();
        List allList = new ArrayList();
        temp = (List) queryResult.getValue();
        for (int a = 0;a < temp.size(); a++) {
            List temp2 = Collections.singletonList(temp.get(a));
            String playingtime = (String) ((Map)temp2.get(0)).get("deal_time");
            sql = "select holidays_types as holidayTypescq from t_holiday_add where start_time<= date('"+playingtime+"') AND end_time>=date('"+playingtime+"')";
            allList = generalDao.queryList(YtcardData.class,sql);
            if (allList.size()==0){
                //===============================判断是星期几(头)==================================================
                sql = "SELECT DAYNAME ("+"'"+playingtime+""+"') as workinDayTypecq";
                allList = generalDao.queryList(YtcardData.class,sql);
                YtcardData pdfTotalBean1 = (YtcardData) allList.get(0);
                ((Map) temp2.get(0)).put("weekcq",pdfTotalBean1.getWorkinDayTypecq());
                //===============================判断是星期几(尾)===============================================

            }else {
                YtcardData pdfTotalBean = (YtcardData) allList.get(0);
                ((Map) temp2.get(0)).put("weekcq",pdfTotalBean.getHolidayTypescq());

            }


        }
        //12345678
        int tyui=0;
        return queryResult;
    }
//======================================================================================================
    //根据月份查出每个月对应的违规出入车场记录
    List<MonthTime> bigList =new ArrayList<>();
    @Override
    public List<MonthTime> queryMonthOutInfo(String params, String year) {
        bigList.clear();
        String carNumber = params;
        List<MonthTime> list =null;
        String sql2=null;
        String changeYear = null;
        if (year == null || year == "") {
            changeYear = "2018";
        }else {
            changeYear = year;
        }
        if (carNumber == null || carNumber ==""){//车牌号和年份为空
            for (int a =1; a<13; a++) {
                //首先判断是否为节假日区间里面，如果是在节假日区间里，那么那一天不管什么情况只要有数据都是可能违规，如果它不是再节假日区间里面，那么它不是工作日就是非工作日，如果是工作日，那天数据是17:30出去或者出去时间大于7个小时那么算可能违规，还有出现数据1900-01-01 00:00:00也可能违规，如果是非工作日则和节假日一样，那么那一天不管什么情况只要有数据都是可能违规。
                if (a>9) {
                     sql2 = "SELECT COUNT(id) AS time FROM t_car_out_information x WHERE ((CASE WHEN (SELECT COUNT(1) FROM t_holiday_add h WHERE h.start_time <= date(x.playing_time) AND h.end_time >= date(x.playing_time)) >= 1 THEN '非工作日' ELSE CASE WHEN DAYNAME(x.playing_time) IN ('Saturday', 'Sunday') THEN '非工作日' ELSE '工作日' END END) = '非工作日' AND playing_time LIKE '"+changeYear+"-"+a+"-%') OR ((CASE WHEN (SELECT 1 FROM t_holiday_add h WHERE h.start_time <= date(x.playing_time) AND h.end_time >= date(x.playing_time)) >= 1 THEN '非工作日' ELSE CASE WHEN DAYNAME(x.playing_time) IN ('Saturday', 'Sunday') THEN '非工作日' ELSE '工作日' END END) = '工作日' AND ((HOUR (playing_time) = 17 AND MINUTE (playing_time) >= 30) OR HOUR (playing_time) > 17) AND playing_time LIKE '"+changeYear+"-"+a+"-%' OR MONTH (playing_time) < MONTH (come_back) AND playing_time LIKE '"+changeYear+"-"+a+"-%' OR DAY (playing_time) < DAY (come_back) AND playing_time LIKE '"+changeYear+"-"+a+"-%' OR come_back = '1900-01-01 00:00:00' AND playing_time LIKE '"+changeYear+"-"+a+"-%')";

                }else{
                    sql2 = "SELECT COUNT(id) AS time FROM t_car_out_information x WHERE ((CASE WHEN (SELECT COUNT(1) FROM t_holiday_add h WHERE h.start_time <= date(x.playing_time) AND h.end_time >= date(x.playing_time)) >= 1 THEN '非工作日' ELSE CASE WHEN DAYNAME(x.playing_time) IN ('Saturday', 'Sunday') THEN '非工作日' ELSE '工作日' END END) = '非工作日' AND playing_time LIKE '"+changeYear+"-0"+a+"-%') OR ((CASE WHEN (SELECT 1 FROM t_holiday_add h WHERE h.start_time <= date(x.playing_time) AND h.end_time >= date(x.playing_time)) >= 1 THEN '非工作日' ELSE CASE WHEN DAYNAME(x.playing_time) IN ('Saturday', 'Sunday') THEN '非工作日' ELSE '工作日' END END) = '工作日' AND ((HOUR (playing_time) = 17 AND MINUTE (playing_time) >= 30) OR HOUR (playing_time) > 17) AND playing_time LIKE '"+changeYear+"-0"+a+"-%' OR MONTH (playing_time) < MONTH (come_back) AND playing_time LIKE '"+changeYear+"-0"+a+"-%' OR DAY (playing_time) < DAY (come_back) AND playing_time LIKE '"+changeYear+"-0"+a+"-%' OR come_back = '1900-01-01 00:00:00' AND playing_time LIKE '"+changeYear+"-0"+a+"-%')";
                }
                System.out.println(sql2);
                list = generalDao.queryList(MonthTime.class,sql2);
                for (int index = 0; index<list.size();index++) {
                    list.get(index).setMonth(a);
                }
                bigList.add(list.get(0));
            }
        }else{
            for (int a =1; a<13; a++) {
                if (a>9) {
                     sql2 = "SELECT COUNT(id) AS time FROM t_car_out_information x WHERE ((CASE WHEN (SELECT COUNT(1) FROM t_holiday_add h WHERE h.start_time <= date(x.playing_time) AND h.end_time >= date(x.playing_time)) >= 1 THEN '非工作日' ELSE CASE WHEN DAYNAME(x.playing_time) IN ('Saturday', 'Sunday') THEN '非工作日' ELSE '工作日' END END) = '非工作日' AND playing_time LIKE '"+changeYear+"-"+a+"-%' AND license_number = '"+carNumber+"') OR ((CASE WHEN (SELECT 1 FROM t_holiday_add h WHERE h.start_time <= date(x.playing_time) AND h.end_time >= date(x.playing_time)) >= 1 THEN '非工作日' ELSE CASE WHEN DAYNAME(x.playing_time) IN ('Saturday', 'Sunday') THEN '非工作日' ELSE '工作日' END END) = '工作日' AND ((HOUR (playing_time) = 17 AND MINUTE (playing_time) >= 30) OR HOUR (playing_time) > 17) AND playing_time LIKE '"+changeYear+"-"+a+"-%' AND license_number = '"+carNumber+"' OR MONTH (playing_time) < MONTH (come_back) AND playing_time LIKE '"+changeYear+"-"+a+"-%' AND license_number = '"+carNumber+"' OR DAY (playing_time) < DAY (come_back) AND playing_time LIKE '"+changeYear+"-"+a+"-%' AND license_number = '"+carNumber+"' OR come_back = '1900-01-01 00:00:00' AND playing_time LIKE '"+changeYear+"-"+a+"-%' AND license_number = '"+carNumber+"')";

                }else {
                     sql2 = "SELECT COUNT(id) AS time FROM t_car_out_information x WHERE ((CASE WHEN (SELECT COUNT(1) FROM t_holiday_add h WHERE h.start_time <= date(x.playing_time) AND h.end_time >= date(x.playing_time)) >= 1 THEN '非工作日' ELSE CASE WHEN DAYNAME(x.playing_time) IN ('Saturday', 'Sunday') THEN '非工作日' ELSE '工作日' END END) = '非工作日' AND playing_time LIKE '"+changeYear+"-0"+a+"-%' AND license_number = '"+carNumber+"') OR ((CASE WHEN (SELECT 1 FROM t_holiday_add h WHERE h.start_time <= date(x.playing_time) AND h.end_time >= date(x.playing_time)) >= 1 THEN '非工作日' ELSE CASE WHEN DAYNAME(x.playing_time) IN ('Saturday', 'Sunday') THEN '非工作日' ELSE '工作日' END END) = '工作日' AND ((HOUR (playing_time) = 17 AND MINUTE (playing_time) >= 30) OR HOUR (playing_time) > 17) AND playing_time LIKE '"+changeYear+"-0"+a+"-%' AND license_number = '"+carNumber+"' OR MONTH (playing_time) < MONTH (come_back) AND playing_time LIKE '"+changeYear+"-0"+a+"-%' AND license_number = '"+carNumber+"' OR DAY (playing_time) < DAY (come_back) AND playing_time LIKE '"+changeYear+"-0"+a+"-%' AND license_number = '"+carNumber+"' OR come_back = '1900-01-01 00:00:00' AND playing_time LIKE '"+changeYear+"-0"+a+"-%' AND license_number = '"+carNumber+"')";

                }
                list = generalDao.queryList(MonthTime.class,sql2);
                for (int index = 0; index<list.size();index++) {
                    list.get(index).setMonth(a);
                }
                bigList.add(list.get(0));
            }
        }

        int  yuio=1;
        return bigList;
    }



    //根据月份查出每个月对应的里程记录
    @Override
    public List<MonthTime> queryMonthMileage(String params, String year) {
        List<MonthTime> list =null;
        String carNumber = params;
        String changeYear = null;
        String sql2 = null;
        if (year == null || year == "") {
            changeYear = "2018";
        }else {
            changeYear = year;
        }
        if (carNumber == null || carNumber ==""){
            for (int a =1; a<13; a++) {
                if (a>9) {
//                    sql2 = "select (SUM(endmileage)-SUM(startmileage)) AS mileage FROM t_mileage WHERE month LIKE '"+changeYear+"-"+a+"-%'";
                    sql2 = "select SUM(month_mileage_difference) AS mileage FROM t_month_mileage_entry WHERE mileage_driven_time LIKE '"+changeYear+"-"+a+"-%'";
                }else {
//                    sql2 = "select (SUM(endmileage)-SUM(startmileage)) AS mileage FROM t_mileage WHERE month LIKE '"+changeYear+"-0"+a+"-%'";
                    sql2 = "select SUM(month_mileage_difference) AS mileage FROM t_month_mileage_entry WHERE mileage_driven_time LIKE '"+changeYear+"-0"+a+"-%'";
                }
                list = generalDao.queryList(MonthTime.class,sql2);
                System.out.println(list);
                bigList.get(a-1).setMileage(list.get(0).getMileage());
            }
        }else{
            for (int a =1; a<13; a++) {
                if (a>9) {
//                     sql2 = "select (SUM(endmileage)-SUM(startmileage)) AS mileage FROM t_mileage WHERE month LIKE '"+changeYear+"-"+a+"-%' and carnumber='"+carNumber+"'";
                    sql2 = "select SUM(month_mileage_difference) AS mileage FROM t_month_mileage_entry WHERE mileage_driven_time LIKE '"+changeYear+"-"+a+"-%' and license_plate_number='"+carNumber+"'";
                }else {
//                    sql2 = "select (SUM(endmileage)-SUM(startmileage)) AS mileage FROM t_mileage WHERE month LIKE '"+changeYear+"-0"+a+"-%' and carnumber='"+carNumber+"'";
                    sql2 = "select SUM(month_mileage_difference) AS mileage FROM t_month_mileage_entry WHERE mileage_driven_time LIKE '"+changeYear+"-0"+a+"-%' and license_plate_number='"+carNumber+"'";
                }
                list = generalDao.queryList(MonthTime.class,sql2);//
                System.out.println(list);
                bigList.get(a-1).setMileage(list.get(0).getMileage());
            }
        }


        return bigList;
    }

    //根据月份查出每个月对应的油卡记录
    @Override
    public List<MonthTime> queryMonthOilCard(String params, String year) {
        List<MonthTime> list =null;
        String carNumber = params;
        String changeYear = null;
        String sql2=null;
        if (year == null || year == "") {
            changeYear = "2018";
        }else {
            changeYear = year;
        }
        if (carNumber == null || carNumber ==""){//车牌号和年份都为空
            for (int a =1; a<13; a++) {
                if (a>9) {
//                    sql2 = "SELECT CONCAT('加了',CONVERT(SUM(a.number),CHAR),'升油',',','花了',CONVERT(TRUNCATE (SUM(a.number * a.unit_price), 2),CHAR),'元') AS oil FROM t_iccard_machine_bill a,t_card_relation b WHERE a.date LIKE '"+changeYear+"-"+a+"-%'";
                    sql2 = "SELECT SUM(a.number) as litre,TRUNCATE (SUM(a.number * a.unit_price),2) AS oil FROM t_iccard_machine_bill a,t_card_relation b WHERE a.date LIKE '"+changeYear+"-"+a+"-%'";
                }else{
//                    sql2 = "SELECT CONCAT('加了',CONVERT(SUM(a.number),CHAR),'升油',',','花了',CONVERT(TRUNCATE (SUM(a.number * a.unit_price), 2),CHAR),'元') AS oil FROM t_iccard_machine_bill a,t_card_relation b WHERE a.date LIKE '"+changeYear+"-0"+a+"-%'";
                    sql2 = "SELECT SUM(a.number) as litre,TRUNCATE (SUM(a.number * a.unit_price),2) AS oil FROM t_iccard_machine_bill a,t_card_relation b WHERE a.date LIKE '"+changeYear+"-0"+a+"-%'";

                }
                list = generalDao.queryList(MonthTime.class,sql2);//
                System.out.println(list);
                bigList.get(a-1).setOil(list.get(0).getOil());
                bigList.get(a-1).setLitre(list.get(0).getLitre());
//                bigList.get(a-1).setViolations(list.get(0).getViolations());//还有这里
            }
        }else{//车牌号和年份不为空
            for (int a =1; a<13; a++) {
                if (a>9) {
                     sql2 = "SELECT SUM(a.number) as litre,TRUNCATE (SUM(a.number * a.unit_price),2) AS oil FROM t_iccard_machine_bill a,t_card_relation b WHERE a.date LIKE '"+changeYear+"-"+a+"-%' AND b.card_number = a.card_number AND b.license_plate_number = '"+params+"'";
                }else{
                     sql2 = "SELECT SUM(a.number) as litre,TRUNCATE (SUM(a.number * a.unit_price),2) AS oil FROM t_iccard_machine_bill a,t_card_relation b WHERE a.date LIKE '"+changeYear+"-0"+a+"-%' AND b.card_number = a.card_number AND b.license_plate_number = '"+params+"'";

                }
                list = generalDao.queryList(MonthTime.class,sql2);//
                System.out.println(list);
                bigList.get(a-1).setOil(list.get(0).getOil());
                bigList.get(a-1).setLitre(list.get(0).getLitre());
//                bigList.get(a-1).setViolations(list.get(0).getViolations());//这里
            }
        }

        return bigList;
    }

    //根据月份查出每个月对应的车桥费记录
    @Override
    public List<MonthTime> queryMonthAxleFee(String params, String year) {
        List<MonthTime> list =null;
        String carNumber = params;
        String changeYear = null;
        String sql2 =null;
        if (year == null || year == "") {
            changeYear = "2018";
        }else {
            changeYear = year;
        }
        if (carNumber == null || carNumber ==""){
            for (int a =1; a<13; a++) {
                if (a>9) {
                     sql2 = "SELECT SUM(deal_sum) as axleFee FROM (SELECT detail_id,invoice_code,invoice_number,invoice_sum,through_city,serial_number,deal_time,entrance,exit_s,split_sum,deal_sum,etc_card_number,licence_plate_number FROM t_pdf_through_detail WHERE deal_sum not like '%同%') a WHERE deal_time LIKE '"+changeYear+"-"+a+"-%'";
                }else{
                    sql2 = "SELECT SUM(deal_sum) as axleFee FROM (SELECT detail_id,invoice_code,invoice_number,invoice_sum,through_city,serial_number,deal_time,entrance,exit_s,split_sum,deal_sum,etc_card_number,licence_plate_number FROM t_pdf_through_detail WHERE deal_sum not like '%同%') a WHERE deal_time LIKE '"+changeYear+"-0"+a+"-%'";

                }
                list = generalDao.queryList(MonthTime.class,sql2);
                System.out.println(list);
                bigList.get(a-1).setAxleFee(list.get(0).getAxleFee());
            }
        }else{
            for (int a =1; a<13; a++) {
                if (a>9) {
                     sql2 = "SELECT SUM(deal_sum) as axleFee FROM (SELECT detail_id,invoice_code,invoice_number,invoice_sum,through_city,serial_number,deal_time,entrance,exit_s,split_sum,deal_sum,etc_card_number,licence_plate_number FROM t_pdf_through_detail WHERE deal_sum not like '%同%') a WHERE deal_time LIKE '"+changeYear+"-"+a+"-%' and licence_plate_number = '"+params+"'";
                }else{
                     sql2 = "SELECT SUM(deal_sum) as axleFee FROM (SELECT detail_id,invoice_code,invoice_number,invoice_sum,through_city,serial_number,deal_time,entrance,exit_s,split_sum,deal_sum,etc_card_number,licence_plate_number FROM t_pdf_through_detail WHERE deal_sum not like '%同%') a WHERE deal_time LIKE '"+changeYear+"-0"+a+"-%' and licence_plate_number = '"+params+"'";

                }
                list = generalDao.queryList(MonthTime.class,sql2);
                System.out.println(list);
                bigList.get(a-1).setAxleFee(list.get(0).getAxleFee());
            }
        }

        return bigList;
    }

    ////根据月份查出每个月对应的修车记录
    @Override
    public List<MonthTime> queryMonthRepairing(String params, String year) {
        List<MonthTime> list =null;
        String carNumber = params;
        String changeYear = null;
        String sql2 = null;
        if (year == null || year == "") {
            changeYear = "2018";
        }else {
            changeYear = year;
        }
        if (carNumber == null || carNumber ==""){//条件都为空的
            for (int a =1; a<13; a++) {
                if (a>9) {
                    sql2 = "select SUM(car_repair_fee) AS carRepairing FROM t_car_repairing WHERE repair_time LIKE '"+changeYear+"-"+a+"-%'";
                }else {
                    sql2 = "select SUM(car_repair_fee) AS carRepairing FROM t_car_repairing WHERE repair_time LIKE '"+changeYear+"-0"+a+"-%'";
                }
                list = generalDao.queryList(MonthTime.class,sql2);
                System.out.println(list);
                bigList.get(a-1).setCarRepairing(list.get(0).getCarRepairing());
            }
        }else{
            for (int a =1; a<13; a++) {
                if (a>9) {
                    sql2 = "select count(id) as repairingId,SUM(car_repair_fee) AS totalRepair,SUM(car_repair_fee) AS carRepairing FROM t_car_repairing WHERE repair_time LIKE '"+changeYear+"-"+a+"-%' and license_plate_number='"+carNumber+"'";
                }else {
                    sql2 = "select count(id) as repairingId,SUM(car_repair_fee) AS totalRepair,SUM(car_repair_fee) AS carRepairing FROM t_car_repairing WHERE repair_time LIKE '"+changeYear+"-0"+a+"-%' and license_plate_number='"+carNumber+"'";
                }
                list = generalDao.queryList(MonthTime.class,sql2);
                System.out.println(list);
                bigList.get(a-1).setCarRepairing(list.get(0).getCarRepairing());
                bigList.get(a-1).setTotalRepair(list.get(0).getTotalRepair());
                bigList.get(a-1).setRepairingId(list.get(0).getRepairingId());
            }
        }


        return bigList;
    }

    //根据月份查出每个月里程疑似违规的记录
    @Override
    public List<MonthTime> queryMonthMileageDeregulation(String params, String yearss) {
        List<MonthTime> list =null;
        String carNumber = params;
        String changeYear = null;
        String sql2 = null;
        if (yearss == null || yearss == "") {
            changeYear = "2018";
        }else {
            changeYear = yearss;
        }
        if (carNumber == null || carNumber ==""){//条件都为空的
            /*for (int a =1; a<13; a++) {
                if (a>9) {
                    sql2 = "";
                }else {
                    sql2 = "";
                }
                list = generalDao.queryList(MonthTime.class,sql2);
                System.out.println(list);
                bigList.get(a-1).setCarRepairing(list.get(0).getCarRepairing());
            }*/
            return bigList;
        }else{
            for (int a =1; a<13; a++) {
                if (a>9) {
//                    sql2 = "select count(id) as violations FROM t_mileage a WHERE month LIKE '"+changeYear+"-"+a+"-%' AND (mileage /(endmileage-startmileage))>0.15 AND carnumber='"+carNumber+"'";
//                    sql2 = "SELECT count(a.id) as violations FROM t_month_mileage_entry a,t_car_basic_type b WHERE (a.month_mileage_difference/a.oil_consumption)>b.oil_consumption_rule AND b.car_number =a.license_plate_number AND a.license_plate_number='"+carNumber+"' AND mileage_driven_time LIKE '"+changeYear+"-"+a+"-%'";
                    sql2 = "SELECT count(a.id) AS violations from  t_month_mileage_entry a,t_car_basic_type b where a.license_plate_number = b.car_number AND a.mileage_driven_time LIKE '"+changeYear+"-"+a+"-%' AND a.license_plate_number = '"+carNumber+"' AND (SELECT SUM(c.number) AS litre FROM t_iccard_machine_bill c,t_card_relation d WHERE c.date LIKE '"+changeYear+"-"+a+"-%' AND d.card_number = c.card_number AND d.license_plate_number = '"+carNumber+"')/a.month_mileage_difference*100>a.oil_consumption";
                }else {
//                    sql2 = "select count(id) as violations FROM t_mileage a WHERE month LIKE '"+changeYear+"-0"+a+"-%' AND (mileage /(endmileage-startmileage))>0.15 AND carnumber='"+carNumber+"'";
//                    sql2 = "SELECT count(a.id) as violations FROM t_month_mileage_entry a,t_car_basic_type b WHERE (a.month_mileage_difference/a.oil_consumption)>b.oil_consumption_rule AND b.car_number =a.license_plate_number AND a.license_plate_number='"+carNumber+"' AND mileage_driven_time LIKE '"+changeYear+"-0"+a+"-%'";
                    sql2 = "SELECT count(a.id) AS violations from  t_month_mileage_entry a,t_car_basic_type b where a.license_plate_number = b.car_number AND a.mileage_driven_time LIKE '"+changeYear+"-0"+a+"-%' AND a.license_plate_number = '"+carNumber+"' AND (SELECT SUM(c.number) AS litre FROM t_iccard_machine_bill c,t_card_relation d WHERE c.date LIKE '"+changeYear+"-0"+a+"-%' AND d.card_number = c.card_number AND d.license_plate_number = '"+carNumber+"')/a.month_mileage_difference*100>a.oil_consumption";
                }
                list = generalDao.queryList(MonthTime.class,sql2);
                System.out.println(list);
                if (list.size()>0) {//list.get(0).getViolations()
                    bigList.get(a-1).setViolations("存在"+list.get(0).getViolations()+"条疑似违规");
                }
            }
        }
        return bigList;
    }

    //===================================保存录入界面数据==================================================
    //保存里程录入界面的数据
    @Override
    public boolean saveMileageData(String params) {
        String[] strArray=null;
        strArray = params.split(",");
        Double mileageDou = Double.valueOf(strArray[5]);
        String sql = "insert into t_mileage (month,carnumber,startmileage,endmileage,carnature,mileage) values ('"+strArray[0]+"','"+strArray[1]+"','"+strArray[2]+"','"+strArray[3]+"','"+strArray[4]+"','"+mileageDou+"')";
        int res = 0;//插入成功,返回1
        res = generalDao.execute(sql);
        if (res==1){
            System.out.println("插入成功");
            return true;
        }
        return false;
    }

    //保存修车费录入界面的数据
    @Override
    public boolean saveCarRepairingData(String params) {
        String[] strArray=null;
        strArray = params.split(",");
        String sql = "insert into t_car_repairing (repair_time,license_plate_number,car_repair_fee) values ('"+strArray[0]+"','"+strArray[1]+"','"+strArray[2]+"')";
        int res = 0;//插入成功,返回1
        res = generalDao.execute(sql);
        if (res==1){
            System.out.println("插入成功");
            return true;
        }
        return false;
    }

    //根据车牌号查出车的性质
    @Override
    public String getCarNature(String params) {
//        String sql = "SELECT carnature FROM t_mileage where carnumber='"+params+"' ";
        String sql = "SELECT car_nature FROM t_month_mileage_entry where license_plate_number='"+params+"'";
        List<MonthTime> allList = generalDao.queryList(MonthTime.class,sql);
       if (allList !=null && allList.size()>0){
           String carNatureStr =allList.get(0).getCarNature();
           return carNatureStr;
       }
       return null;
    }

    @Override
    public Double getCarRepairFee(String params) {
        String sql = "SELECT monthly_mileage as carRepairFee from t_car_basic_type WHERE car_number='"+params+"'";
        List<MonthTime> allList = generalDao.queryList(MonthTime.class,sql);
        if (allList !=null && allList.size()>0){
            Double carRepairFee =allList.get(0).getCarRepairFee();
            return carRepairFee;
        }
        return null;
    }


    //保存车辆规则界面的数据
    @Override
    public boolean savaCarRulesData(String params) {
        String[] strArray=null;
        strArray = params.split(",");
       /* Double oils = Double.parseDouble(strArray[5])*100;*/
        String sql = "insert into t_car_basic_type (car_number,production_time,car_type,out_oil,monthly_mileage,oil_consumption_rule,repairs,repairs_times) values ('"+strArray[0]+"','"+strArray[1]+"','"+strArray[2]+"','"+strArray[3]+"','"+strArray[4]+"','"+strArray[5]+"','"+strArray[6]+"','"+strArray[7]+"')";
        int res = 0;//插入成功,返回1
        res = generalDao.execute(sql);
        if (res==1){
            System.out.println("插入成功");
            return true;
        }
        return false;
    }

    //车辆里程录入界面-历史记录的查询
    @Override
    public QueryResult queryHistoryMileageEntry(YtcardData ytcardData, PageSettings settings) {
        Query query = new PagedQuery(settings);
        query.select("*").from("t_month_mileage_entry ORDER BY license_plate_number ");
        generalDao.execute(query);
        QueryResult queryResult = query.getResult();
        List temp = new ArrayList();
        temp = (List) queryResult.getValue();
        for (int a = 0;a < temp.size(); a++) {
            List temp2 = Collections.singletonList(temp.get(a));
            Object timeStr=((Map)temp2.get(0)).get("mileage_driven_time");
            ((Map)temp2.get(0)).put("mileage_driven_time",timeStr.toString().substring(0,7));
        }
        return queryResult;
    }

    //查询所有的车牌号
    @Override
    public List<MonthTime> queryAllCarNumber(YtcardData ytcardData, PageSettings settings) {
        String sql = "select distinct license_plate_number as totalRepair from t_card_relation";
        List<MonthTime> list =new ArrayList<>();
        list = generalDao.queryList(MonthTime.class,sql);
        return list;
    }

    //查询车牌号对应的汽车的车辆性质
    @Override
    public String queryCarNature(String params) {
        String sql = "SELECT car_nature as CarNature FROM t_car_check_registration where license_plate_number='"+params+"'";
        List<MonthTime> allList = generalDao.queryList(MonthTime.class,sql);
        if (allList !=null && allList.size()>0){
            String carNatureStr =allList.get(0).getCarNature();
            return carNatureStr;
        }
        return null;
    }

    //查询车牌号对应的汽车的月规定里程
    @Override
    public String queryMonthOnMileage(String params) {
        String sql = "SELECT monthly_mileage as mileage FROM t_car_basic_type where car_number = '"+params+"'";
        List<MonthTime> allList = generalDao.queryList(MonthTime.class,sql);
        if (allList !=null && allList.size()>0){
            String MonthOnMileageStr =allList.get(0).getMileage();
            return MonthOnMileageStr;
        }
        return null;
    }

    @Override
    public String queryOil(String params) {
        String sql = "SELECT oil_consumption_rule as oil FROM t_car_basic_type where car_number='"+params+"'";
        List<MonthTime> allList = generalDao.queryList(MonthTime.class,sql);
        if (allList !=null && allList.size()>0){
            String carOil =allList.get(0).getOil();
            return carOil;
        }
        return null;
    }
    //查询车牌号对应的汽车的车辆性质和油耗
    /*@Override
    public List<MonthTime> queryCarNatureAndOil(String params) {
        String sql = "SELECT a.car_nature as CarNature,b.oil_consumption_rule as oil FROM t_car_check_registration a,t_car_basic_type b where a.license_plate_number='"+params+"' AND a.license_plate_number=b.car_number";
        List<MonthTime> list =new ArrayList<>();
        list = generalDao.queryList(MonthTime.class,sql);
        return list;
    }*/

    //保存里程录入界面数据
    @Override
    public boolean savaMileageEnterData(String arraystr) {
        String[] strArray=null;
        strArray = arraystr.split(",");
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-dd");
        Date date = new Date();
        String currentYearDay = sdf.format(date);//获取当前年月日
        String[] arrayYearDay = currentYearDay.split("-");//获取的年日拆份成两个字符串
        String timeStr = arrayYearDay[0]+"-"+strArray[2]+"-"+arrayYearDay[1];//拼接成2018-?-06
        String sqlStr3 = "select mileage_driven_time as time from t_month_mileage_entry where year(mileage_driven_time)=year('"+timeStr+"') AND month(mileage_driven_time)=month('"+timeStr+"') AND license_plate_number='"+strArray[0]+"'";
        List<MonthTime> allList3 = generalDao.queryList(MonthTime.class,sqlStr3);
        if (allList3.size()>0){
            return false;
        }
        String where = "";
        int monthVal = Integer.parseInt(strArray[2]);
        double lastMonthMileageStr = 0;
        double themonthStr = 0;
        if (monthVal == 01) {
            where = "month(mileage_driven_time)="+monthVal+"+11 AND year(mileage_driven_time)="+arrayYearDay[0]+"-1 and license_plate_number = '"+strArray[0]+"'";
        }else{
            where = "month(mileage_driven_time)="+monthVal+"-1 AND year(mileage_driven_time)="+arrayYearDay[0]+" and license_plate_number = '"+strArray[0]+"'";
        }
        String sqlStr = "select month_driving_mileage as mileage from t_month_mileage_entry where "+where+"";
        List<MonthTime> allList = generalDao.queryList(MonthTime.class,sqlStr);
        if (allList.size()>0) {
            lastMonthMileageStr = Double.parseDouble(allList.get(0).getMileage());//获取选择月份的上个月的里程
        }
        themonthStr = Double.parseDouble(strArray[3])-lastMonthMileageStr;//这个月减上个月的里程
        String sql2Str = "insert into t_month_mileage_entry (license_plate_number,car_nature,mileage_driven_time,month_driving_mileage,oil_consumption,bridge_cash_charge,month_mileage_difference) values ('"+strArray[0]+"','"+strArray[1]+"','"+timeStr+"','"+strArray[3]+"','"+strArray[4]+"','"+strArray[5]+"','"+themonthStr+"')";
        int res = 0;//插入成功,返回1
        res = generalDao.execute(sql2Str);
        if (res==1){
            System.out.println("插入成功");
            return true;
        }
        return false;

    }
}
