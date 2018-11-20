package com.gpdi.mdata.web.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @description:求时间差
 * @author: WangXiaoGang
 * @data: Created in 2018/9/29 19:02
 * @modifier:
 */
public class DifferUtil {
    public static String getDiffer(List<String> param){
//        System.out.println("---------"+param);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
        List<Long> ca = new ArrayList<>();
        if (param !=null && param.size()>1){
            try {

            for (int i=0;i<param.size();i++){
                Date date1 = sdf.parse(param.get(i));
                for (int k=i+1;k<param.size();k++){
                        Date date2 = sdf.parse(param.get(k));
                    long ti =Math.abs((date1.getTime()-date2.getTime())/(24*3600*1000));//将时间转换为天数相减得出相隔天数
                    ca.add(ti);
                    }
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }

        Collections.sort(ca);
        String var = String.valueOf(ca.get(0));
        return var;
    }
}
