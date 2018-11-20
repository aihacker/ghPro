package com.gpdi.mdata.web.utils;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by XLKAI on 2017/5/19.
 */
public class TypeUtils {

    public static final String[] chars = {"A", "B", "C", "D", "E", "F", "G", "H",
            "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V",
            "W", "X", "Y", "Z"};

    /**
     * 判断List是否为空
     *
     * @param list
     * @return
     */
    public static boolean empty(List list) {
        return list == null || list.size() == 0;
    }

    /**
     * 判断字符串是否为空
     *
     * @param str
     * @return
     */
    public static boolean empty(String str) {
        return (str == null || str.length() <= 0);
    }

    public static boolean empty(Object[] arrays) {
        return arrays == null || arrays.length <= 0;
    }

    public static boolean contain(String[] targets, String str) {
        if (TypeUtils.empty(targets)) {
            return false;
        }
        for (String tar : targets) {
            if (str.equals(tar)) return true;
        }
        return false;
    }

    /**
     * Array转List
     *
     * @param ts
     * @param <T>
     * @return
     */
    public static <T> List<T> arrayToList(T[] ts) {
        if (empty(ts)) {
            return new ArrayList<>();
        }
        List<T> list = new ArrayList<>();
        for (T t : ts) {
            list.add(t);
        }
        return list;
    }

    public static String listToStr(List list, String sep) {
        if (empty(list)) return "";
        StringBuilder builder = new StringBuilder();
        for (Object obj : list) {
            builder.append(obj.toString());
            builder.append(sep);
        }
        return builder.substring(0, builder.length() - sep.length());
    }

    public static Object[] listToObject(List list) {
        if (empty(list)) {
            return new Object[]{};
        }
        Object[] lis = new Object[list.size()];
        for (int i = 0; i < list.size(); i++) {
            lis[i] = list.get(i);
        }
        return lis;
    }

    public static String listToStr(List list) {
        return listToStr(list, ",");
    }

    public static <T> List<T> strToList(String strs) {
        return strToList(strs, null);
    }

    public static <T> List<T> strToList(String strs, String separated) {
        if (strs == null || strs.isEmpty()) {
            return new ArrayList<T>();
        }
        if (separated == null) {
            separated = "\\s*,\\s*";
        }
        String[] lis = strs.split(separated);
        List<T> t = new ArrayList<T>();
        for (String s : lis) {
            t.add((T) s);
        }
        return t;
    }

    public static <T> List<T> removeRepetition(List<T> list) {
        List<T> tmpList = new ArrayList<T>();
        Iterator<T> it = list.iterator();
        while (it.hasNext()) {
            T t = it.next();
            if (tmpList.contains(t)) {
                it.remove();
            } else {
                tmpList.add(t);
            }
        }
        return list;
    }

    public static String getNameByIndex(int i) {
        return chars[i];
    }

    public static String[] listToArray(List<String> strings) {
        if (empty(strings)) {
            return new String[]{};
        }
        String[] lis = new String[strings.size()];
        for (int i = 0; i < strings.size(); i++) {
            lis[i] = strings.get(i);
        }
        return lis;
    }
}
