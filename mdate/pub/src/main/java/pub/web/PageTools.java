package pub.web;

import pub.functions.DateFuncs;
import pub.functions.VarFuncs;
import pub.types.IdText;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by zzl on 2015/11/4.
 */
public class PageTools {

    public static Object get(Map map, Object key) {
        return map.get(key);
    }

    public static String formatTime(Date date) {
        if(date == null) {
            return "";
        }
        return DateFuncs.toStringToMin(date);
    }

    public static String formatLocalDate(Object date) {
        if(date == null) {
            return "";
        }
        if(date instanceof Date) {
            return DateFuncs.toDateString((Date) date);
        }
        int nDate = VarFuncs.toInteger(date);
        int y = nDate / 10000;
        nDate -= y * 10000;
        int m = nDate / 100;
        int d = nDate - m * 100;
        return y + "年" + m + "月" + d + "日";
    }

    public static int toIntDate(Date date) {
        return DateFuncs.toIntDate(date);
    }

    public static String formatDate(Object date) {
        if(date == null) {
            return "";
        }
        if(date instanceof Date) {
            return DateFuncs.toDateString((Date) date);
        }
        int nDate = VarFuncs.toInteger(date);
        int y = nDate / 10000;
        nDate -= y * 10000;
        int m = nDate / 100;
        int d = nDate - m * 100;

        return y + "-" + (m < 10? "0": "") + m + "-" + (d < 10? "0": "") + d;
    }

    public static String formatMd(Object date) {
        if(date == null) {
            return "";
        }
        if(date instanceof Date) {
            return DateFuncs.toString((Date) date, "M日d日");
        }
        int nDate = VarFuncs.toInteger(date);
        nDate = nDate % 10000;
        int m = nDate / 100;
        int d = nDate - m * 100;
        return m + "月" + d + "日";
    }

    public static String formatHm(Object minutes) {
        if(minutes == null) {
            return "";
        }
        int nMinutes = VarFuncs.toInteger(minutes);
        int h = nMinutes / 100;
        int m = nMinutes % 100;
        return (h < 10? "0": "") + h + ":" + (m < 10? "0": "") + m;
    }

    public static String format(Object obj, String format) {
        if(obj == null) {
            return "";
        }
        if(obj instanceof Number) {
            NumberFormat fmt = new DecimalFormat(format);
            return fmt.format(obj);
        }
        System.out.println("??");
        return obj.toString();
    }

    public static Object translate(List<IdText> idTexts, Object value) {
        for(IdText idText: idTexts) {
            if(VarFuncs.equals(idText.getId(), value)) {
                return idText.getText();
            }
        }
        return "";
    }

}
