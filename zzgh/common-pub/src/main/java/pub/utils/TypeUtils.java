package pub.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * @Author xlkai
 * @Version 2017/2/21
 */
public class TypeUtils {

    public static final String[] chars = {"A", "B", "C", "D", "E", "F", "G", "H",
            "I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V",
            "W", "X", "Y", "Z"};

    /**
     * 判断list是否为空
     *
     * @param list
     * @return
     */
    public static boolean empty(List list) {
        if (list == null || list.size() <= 0) {
            return true;
        }
        return false;
    }

    public static String getNameByIndex(int i) {
        return chars[i];
    }

    public static String listToString(List strings, String separated) {
        if (empty(strings)) {
            return "";
        }
        String str = "";
        int size = strings.size();
        for (int i = 0; i < size; i++) {
            if(i != size - 1)
                str += strings.get(i).toString() + separated;
            else
                str += strings.get(i).toString();
        }
        return str;
    }

    public static String listToStr(List strings, String separated) {
        if (empty(strings)) {
            return "";
        }
        String str = "";
        for (Object s : strings) {
            str += s.toString() + separated;
        }
        str = str.substring(0, str.length() - 1);
        return str;
    }

    public static String listToStr(List strings) {
        return listToStr(strings, ",");
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

    public static boolean contain(int[] values, int val) {
        for (int v : values) {
            if (v == val) return true;
        }
        return false;
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

    /**
     * 判断是否为偶数
     *
     * @return
     */
    public static boolean isEvenNumber(Integer numb) {
        if (numb == null) return false;
        return numb % 2 == 0;
    }

    /**
     * 判断是否为2的乘方
     *
     * @param numb
     * @return
     */
    public static boolean isTwo(Integer numb) {
        String str = Integer.toBinaryString(numb);
        return str.matches("10*");
    }

    public static double log(double val, double base) {
        return Math.log(val) / Math.log(base);
    }

    public static String getUUID() {
        String uuid = UUID.randomUUID().toString();
        return uuid.replace("-", "");
    }

}
