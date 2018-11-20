package com.gpdi.mdata.web.utils;

import jdk.nashorn.internal.runtime.regexp.joni.Regex;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @description:
 * @author: WangXiaoGang
 * @data: Created in 2018/8/10 17:10
 * @modifier:截取字符串
 */
public class StringUtils {

    //[\u4e00-\u9fa5] 表示中文字符
    //\d 表示数字
    //string _s= Regex.Replace(_s, @"[\u4e00-\u9fa5]", ""); //去除汉字
    //string _s= Regex.Replace(_s, @"[^\u4e00-\u9fa5]", ""); //只留汉字
    //string ph = Regex.Replace(ph, @"\D", ""); //排除除数字外的所有字符
    //txt1.Text = Regex.Replace(txt1.Text, "[0-9]", "", RegexOptions.IgnoreCase);//去掉0-9的数字
    //txt1.Text = Regex.Replace(txt1.Text, "[a-z]", "", RegexOptions.IgnoreCase);//去掉a-z的字母


    public static void main(String[] args) {
        String res ="C-10业务代理(含农村统包)";
        //通过StringUtils提供的方法
        String[] bbr = res.split("\\d+");
        String co = bbr[0];
        String na = bbr[1];
        System.out.println("ere:"+na);
        String st = "同意。梁卫华 (佛山分公司顺德分公司 2017-1-3 16:33)｜同意。胡国强 (佛山分公司\\顺德分公司 2017-1-4 16:32)同意。胡国强 (佛山分公司顺德分公司 2017-1-6 09:44)";
       // String rr =  org.apache.commons.lang3.StringUtils.substringBeforeLast(res, "\\d");
        //System.out.println("dsfsdfs:"+rr);
        //System.out.println(   org.apache.commons.lang3.StringUtils.substringBefore(res,regex));

        String ress ="B-10 工程施工（包括自建管道等）";
        String[] bb = ress.split("[\\u4e00-\\u9fa5]");
        String ss = bb[0];
        System.out.println("hhahahah:"+ss);
    }
public void test11(){
    String res ="B-10 工程施工（包括自建管道等）";
    //通过StringUtils提供的方法
    String rr =  org.apache.commons.lang3.StringUtils.substringBeforeLast(res, "\\d");
    System.out.println("dsfsdfs:"+rr);
    System.out.println(   org.apache.commons.lang3.StringUtils.substringBefore(res,"\u4e00-\u9fa5"));

   /* StringUtils.substringBefore(res, “工”);
/结果是：dsk/
            这里是以第一个”e”，为标准。
    StringUtils.substringBeforeLast(“dskeabcee”, “e”)
    结果为：dskeabce
    这里以最后一个“e”为准*/
}
    //用slit方法截取
public void test12(){
    String str = "公诚管理咨询有限公司,http://cgfz-hq.mss.ctc.com/MSS-PURCHASE/provider/view.do?id=10248009｜深圳市都信建设监理有限公司,http://cgfz-hq.mss.ctc.com/MSS-PURCHASE/provider/view.do?id=10248966｜广州市汇源通信建设监理有限公司,http://cgfz-hq.mss.ctc.com/MSS-PURCHASE/provider/view.do?id=10255625";
        String[] bb = str.split("\\｜");
        for (int i = 0; i < bb.length; i++) {
            System.out.println(bb[i]);
            String[] cc = bb[i].split("\\,");
            String companyName = cc[0];
            String adreess = cc[1];
            System.out.println("companyName:"+companyName);
            System.out.println("adreess:"+adreess);

        }
    }

    public void test22(){
    //用正则表达式截取数字
    String res ="B-10 工程施工（包括自建管道等）";
    String[] bb = res.split("[\\u4e00-\\u9fa5]");
    String ss = bb[0];
        System.out.println("hhahahah:"+ss);
    }

    //只提取字符串的数字
    public static Integer getNumber(String param) {
        if (param !=null && !param.equals("")){
            Pattern pattern = Pattern.compile("[^0-9]");
            Matcher matcher = pattern.matcher(param);
            Integer number = Integer.valueOf(matcher.replaceAll(""));
            return number;
        }
        // 2
        //String phoneString = "哈哈,13888889999";
        //Pattern.compile("[^0-9]").matcher(phoneString).replaceAll("");
    return null;
    }

    public void test() {
        // 提取张三 去除数字
        String r_name3 = "张三 13599998888 000000";
        Pattern pattern = Pattern.compile("[\\d]");
        Matcher matcher = pattern.matcher(r_name3);
        System.out.println(matcher.replaceAll("").trim());
    }

   //=============截取2个字符串之间的数据
    public static List<String> getStr(String str){
        List<String> result = new ArrayList<>();
        String st = "同意。梁卫华 (佛山分公司顺德分公司 2017-1-3 16:33)｜同意。胡国强 (佛山分公司\\顺德分公司 2017-1-4 16:32)同意。胡国强 (佛山分公司顺德分公司 2017-1-6 09:44)";
        //截取。和(之间的人名
        String rgx = "。([\\u4e00-\\u9fa5]*) \\(";
      //  String rgx = "\\| ([\\w]*) \\|";
        Pattern pattern = Pattern.compile(rgx);
        Matcher ma = pattern.matcher(str);
        while (ma.find()){
            result.add(ma.group(1));
        }
        if (result !=null && result.size()>0){
            HashSet h = new HashSet(result);
            result.clear();
            result.addAll(h);
        }
        System.out.println(result);
        return result;

    }

    /**
     * 提取金额,规则为只提取数字和点号,必须有点号
     * 格式可以为0.0或者，11
     * @param cost
     * @return
     */
    public   Double extract_cost_dot(String cost) {
        Pattern compile = Pattern.compile("(\\d+\\.\\d+)|(\\d+)");
        Matcher matcher = compile.matcher(cost);
        matcher.find();
        return Double.valueOf(matcher.group());
    }

}
