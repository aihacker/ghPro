package com.libs.common.excel;


/**
 * @Version 1.0
 * --------------------------------------------------------- *
 * @Description >_
 * --------------------------------------------------------- *
 * @Author Ape
 * --------------------------------------------------------- *
 * @Email <16511660@qq.com>
 * --------------------------------------------------------- *
 * @Date 2017-10-12  14:36
 * --------------------------------------------------------- *
 */
public class ExcelCharFilter {

    private static final String[] chars = new String[]{
            ":", "?", "*", "/", "\\", "[", "]"
    };
    private static final String[] replace = new String[]{
            "：", "？", "＊", "／", "＼", "［", "］"
    };

    public static String replace(String s){
        int length = chars.length;
        for (int i = 0; i < length; i++) {
            if(s.contains(chars[i]))
                s = s.replace(chars[i], replace[i]);
        }
        return s;
    }

}
