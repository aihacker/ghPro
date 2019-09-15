package com.libs.common.type;

import java.util.*;

/**
 * Created by XLKAI on 2017/5/19.
 */
public class StringUtils {

    public static final char UNDERLINE = '_';

    /**
     * 驼峰格式字符串转换为下划线格式字符串
     *
     * @param param
     * @return
     */
    public static String camelToUnderline(String param) {
        if (param == null || "".equals(param.trim())) {
            return "";
        }
        int len = param.length();
        StringBuilder sb = new StringBuilder(len);
        for (int i = 0; i < len; i++) {
            char c = param.charAt(i);
            if (Character.isUpperCase(c)) {
                if (i != 0) sb.append(UNDERLINE);
                sb.append(Character.toLowerCase(c));
            } else {
                sb.append(c);
            }
        }
        return sb.toString();
    }

    /**
     * 下划线格式字符串转换为驼峰格式字符串
     *
     * @param param
     * @return
     */
    public static String underlineToCamel(String param) {
        if (param == null || "".equals(param.trim())) {
            return "";
        }
        int len = param.length();
        StringBuilder sb = new StringBuilder(len);
        for (int i = 0; i < len; i++) {
            char c = param.charAt(i);
            if (c == UNDERLINE) {
                if (++i < len) {
                    sb.append(Character.toUpperCase(param.charAt(i)));
                }
            } else {
                sb.append(c);
            }
        }
        return sb.toString();
    }

    public static String rand_int(int len) {
        String str = "0123456789";
        str += (str + str + str + str);

        List<String> list = Arrays.asList(str.split(""));
        Collections.shuffle(list);

        Random random = new Random();
        int size = list.size();
        String newStr = "";
        for (int i = 0; i < len; i++) {
            newStr += list.get(random.nextInt(size));
        }
        return newStr;
    }

    public static String randLetter(int len) {
        String str = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
        str += (str + str + str + str);

        List<String> list = Arrays.asList(str.split(""));
        Collections.shuffle(list);

        Random random = new Random();
        int size = list.size();
        String newStr = "";
        for (int i = 0; i < len; i++) {
            newStr += list.get(random.nextInt(size));
        }
        return newStr;
    }

    public static String uuid() {
        return UUID.randomUUID().toString().replace("-", "");
    }
}
