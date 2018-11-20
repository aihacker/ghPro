package com.gpdi.mdata.web.utils;

import org.apache.poi.ss.usermodel.Row;
import org.springframework.util.LinkedCaseInsensitiveMap;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * @description:
 * @author: WangXiaoGang
 * @data: Created in 2018/11/18 17:53
 * @modifier:
 */
public class Datas {

    public static void setData(LinkedCaseInsensitiveMap map, Object o) {
        Set<String> set = map.keySet();
        for (String key : set) {
            if (key.equals("Id")) {
                key = key.toLowerCase();
            }
            String keyIndex = key.substring(0, 1).toUpperCase();
            String keyBack = key.substring(1, key.length());
            try {
                if (getFields(o).contains(key) && map.get(key) != null) {
                    o.getClass().getMethod("set" + keyIndex + keyBack, String.class).invoke(o, map.get(key).toString());
                    if(key.equals("relName")){
                        o.getClass().getMethod("setMess", String.class).invoke(o," ");
                    }
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            }
        }
    }

    public static void getData(LinkedCaseInsensitiveMap map, Object o, Integer num, Row row, Object excel) {
        Set<String> set = map.keySet();
        for (String key : set) {
            if (key.equals("Id")) {
                key = key.toLowerCase();
            }
            String keyIndex = key.substring(0, 1).toUpperCase();
            String keyBack = key.substring(1, key.length());
            if (getFields(o).contains(key)) {
                try {
                    if (num < getFields(o).size()) {
                        if(key.equals("relName")){
                            ((ExcelSupportApi) excel).createCell(row, num, o.getClass().getMethod("getMess", null).invoke(o, null));
                        }else {
                            ((ExcelSupportApi) excel).createCell(row, num, o.getClass().getMethod("get" + keyIndex + keyBack, null).invoke(o, null));
                        }
                        num++;
                    }
                    if (num == getFields(o).size()) {
                        num = 0;
                    }
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (InvocationTargetException e) {
                    e.printStackTrace();
                } catch (NoSuchMethodException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static LinkedCaseInsensitiveMap getMap(List list, Object temp, Object data) {
        LinkedCaseInsensitiveMap map = new LinkedCaseInsensitiveMap();
        List<String> dataList = getFields(data);
        for (int i = 0; i < list.size(); i++) {
            Object o = list.get(i);
            for (String field : getFields(temp)) {
                if (field.equals("Id")) {
                    field = field.toLowerCase();
                }
                String keyIndex = field.substring(0, 1).toUpperCase();
                String keyBack = field.substring(1, field.length());
                if (dataList.contains(field)) {
                    try {
                        map.put(field, o.getClass().getMethod("get" + keyIndex + keyBack, null).invoke(o, null));
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    } catch (InvocationTargetException e) {
                        e.printStackTrace();
                    } catch (NoSuchMethodException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return map;
    }

    public static List<String> getFields(Object o) {
        Field[] fields = o.getClass().getDeclaredFields();
        List<String> fieldList = new ArrayList<String>();
        for (int i = 0; i < fields.length; i++) {
            String field = fields[i].getName().toString();
            fieldList.add(field);
        }
        return fieldList;
    }

    public static boolean isMessField(Object o) {
        Field[] fields = o.getClass().getDeclaredFields();
        List<String> fieldList = new ArrayList<String>();
        for (int i = 0; i < fields.length; i++) {
            String field = fields[i].getName().toString();
            if ("mess".equals(field)) {
                return true;
            }
        }
        return false;
    }

}
