package com.gpdi.mdata.web.utils;

import java.math.BigDecimal;

/**
 * Created by 椋庝付杞昏交 on 2017/12/1.
 */
public class BigDecimalUtil {
    //鍟嗕笟鐢˙igDecimal鏉ヨ繘琛岃绠楋紝姝ｅ父鐨刯ava娴偣鏁拌繍绠椾細瀛樺湪璇樊
    private BigDecimalUtil(){

    }


    public static double add(double v1,double v2){
        BigDecimal b1 = new BigDecimal(Double.toString(v1));
        BigDecimal b2 = new BigDecimal(Double.toString(v2));
        return b1.add(b2).doubleValue();
    }

    public static double sub(double v1,double v2){
        BigDecimal b1 = new BigDecimal(Double.toString(v1));
        BigDecimal b2 = new BigDecimal(Double.toString(v2));
        return b1.subtract(b2).doubleValue();
    }


    public static double mul(double v1,double v2){
        BigDecimal b1 = new BigDecimal(Double.toString(v1));
        BigDecimal b2 = new BigDecimal(Double.toString(v2));
        return b1.multiply(b2).doubleValue();
    }

    public static double div(double v1,double v2){
        BigDecimal b1 = new BigDecimal(Double.toString(v1));
        BigDecimal b2 = new BigDecimal(Double.toString(v2));
        return b1.divide(b2,2,BigDecimal.ROUND_HALF_UP).doubleValue();//鍥涜垗浜斿叆,淇濈暀2浣嶅皬鏁�
        //闄や笉灏界殑鎯呭喌
    }
}
