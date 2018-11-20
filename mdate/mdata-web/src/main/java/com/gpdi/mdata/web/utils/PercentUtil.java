package com.gpdi.mdata.web.utils;

import java.text.DecimalFormat;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 将小数转换为百分数
 */
public class PercentUtil {

     public static String percent(Double op) {
         String str;
         if(op !=null) {
             DecimalFormat result = new DecimalFormat("#######.00");//保留两位小数
             Double db = Double.valueOf(op * 100);
              str = String.valueOf(result.format(db) + "%");
         }else {
            str = null;
         }
         return str;
     }


/**
 * @description:获取字符串数字后面的字符串
 * @author: WangXiaoGang
 * @data: 2018/6/11 0011 8:50
 * @modifier:
 */
    /*1、至少匹配一个汉字的写法。
2、这两个unicode值正好是Unicode表中的汉字的头和尾。
3、"[]"代表里边的值出现一个就可以，后边的“+”代表至少出现1次，合起来即至少匹配一个汉字。
*/
    public static String getChinese(String paramValue) {
        String regex = "([\u4e00-\u9fa5]+)";
        String str = "";
        Matcher matcher = Pattern.compile(regex).matcher(paramValue);
        while (matcher.find()) {
            str+= matcher.group(0);
        }
        return str;
    }
}
