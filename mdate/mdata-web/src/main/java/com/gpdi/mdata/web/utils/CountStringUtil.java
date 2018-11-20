package com.gpdi.mdata.web.utils;


/**
 * 查找字符串中有几个某字符
 */
public class CountStringUtil {

    private static int counter;

    public static void main(String[] args){
        System.out.println(haveChar("hello","l"));
    }

    public static Integer haveChar(String str1, String str2){
        counter = 0;
        return haveChar2(str1, str2);
    }

    public static Integer haveChar2(String str1, String str2){
        if (str1.indexOf(str2) == -1) {
            return 0;
        } else if (str1.indexOf(str2) != -1) {
            counter++;
            haveChar2(str1.substring(str1.indexOf(str2) +
                    str2.length()), str2);
            return counter;
        }
        return 0;
    }

}
