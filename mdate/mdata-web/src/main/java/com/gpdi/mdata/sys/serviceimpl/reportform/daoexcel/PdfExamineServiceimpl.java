package com.gpdi.mdata.sys.serviceimpl.reportform.daoexcel;

import com.gpdi.mdata.sys.entity.report.PdfTotalBean;
import com.gpdi.mdata.sys.service.reportform.daoexcel.pdfscream.PdfExamineService;
import com.gpdi.mdata.web.reportform.data.QueryData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pub.dao.GeneralDao;
import pub.dao.jdbc.dialect.mysql.PagedQuery;
import pub.dao.query.Query;
import pub.dao.query.QueryResult;
import pub.dao.query.QuerySettings;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * @description:
 * @author: WangXiaoGang
 * @data: Created in 2018/8/10 11:19
 * @modifier:
 */
@Service
@Transactional(readOnly = false)
public class PdfExamineServiceimpl implements PdfExamineService {
    @Autowired
    private GeneralDao generalDao;
    @Override
    public QueryResult queryPdf(QueryData queryData, QuerySettings settings) {
        Query query = new PagedQuery<>(settings);//创建分页对象
        query.select("detail_id,invoice_code,invoice_number,invoice_sum,through_city,serial_number,deal_time,entrance,exit_s,split_sum,deal_sum,etc_card_number,licence_plate_number").from("t_pdf_through_detail").where("deal_sum not like '%同%'");
        generalDao.execute(query);
        QueryResult queryResult = query.getResult();
        String sql ="";
        String date = null;
        List<PdfTotalBean> temp = new ArrayList();
        List allList = new ArrayList();
        temp = (List) queryResult.getValue();
        for (int a = 0;a < temp.size(); a++) {
            List temp2 = Collections.singletonList(temp.get(a));
            String dealtime = (String) ((Map)temp2.get(0)).get("deal_time");
//            sql = "select holiday_types as holidayTypes from t_holiday where date = substring("+"'"+dealtime+"'"+",1,10) ";
            sql = "select holidays_types as holidayTypes from t_holiday_add where start_time<= '"+dealtime+"' AND end_time>='"+dealtime+"'";
            allList = generalDao.queryList(PdfTotalBean.class,sql);
            if (allList.size()==0){
                //===============================判断是星期几(头)==================================================
                sql = "SELECT DAYNAME ("+"'"+dealtime+""+"') as licencePlateNumber";
                allList = generalDao.queryList(PdfTotalBean.class,sql);
                PdfTotalBean pdfTotalBean1 = (PdfTotalBean) allList.get(0);
                System.out.println("++++++++++++++++="+ pdfTotalBean1.getLicencePlateNumber()+"+++++++++++");
                ((Map) temp2.get(0)).put("week",pdfTotalBean1.getLicencePlateNumber());
                //===============================判断是星期几(尾)===============================================

            }else {
                PdfTotalBean pdfTotalBean = (PdfTotalBean) allList.get(0);
                ((Map) temp2.get(0)).put("week",pdfTotalBean.getHolidayTypes());

            }


        }




        return queryResult;
    }
    /*@Override
    public QueryResult insertHolidayTime(QueryData queryData, QuerySettings settings) {
        String startTime = queryData.getStartTime();
        String endTime = queryData.getEndTime();
        String holidayTime =queryData.getHolidayTime();
        if(holidayTime.equals(null)){
            return null;
        }
        String sql = "insert into t_holiday_add (start_time ,end_time,holidays_types) values ('"+startTime+"','"+endTime+"','"+holidayTime+"')";
        int res = 0;//插入成功,返回1
        res = generalDao.execute(sql);
        if (res==1){
            System.out.println("插入成功");
//            return true;
        }
        return null;
    }*/

    @Override
    public boolean insertHolidayTime(String params) {
        String[] strArray=null;
        strArray = params.split(",");
        String sql = "insert into t_holiday_add (start_time ,end_time,holidays_types) values ('"+strArray[0]+"','"+strArray[1]+"','"+strArray[2]+"')";
        int res = 0;//插入成功,返回1
        res = generalDao.execute(sql);
        if (res==1){
            System.out.println("插入成功");
            return true;
        }
        return false;
    }

}
