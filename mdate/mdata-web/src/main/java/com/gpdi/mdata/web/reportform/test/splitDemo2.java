package com.gpdi.mdata.web.reportform.test;

import org.apache.commons.lang3.StringUtils;

/**
 * @description:
 * @author: WangXiaoGang
 * @data: Created in 2018/8/10 16:07
 * @modifier:拆分字符串
 */
public class splitDemo2 {

    public static void main(String[] args) {
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
        //用正则表达式截取数字
        String res ="B-10 工程施工（包括自建管道等）";
        String[] bbr = res.split("[\\u4e00-\\u9fa5]");
        String ss = bbr[0];
        System.out.println("hhahahah:"+ss);
        String rr =  StringUtils.substringBeforeLast(res, "\\d");
        System.out.println("dsfsdfs:"+rr);
        System.out.println(   StringUtils.substringBefore(res,"\\d"));



    }
}
