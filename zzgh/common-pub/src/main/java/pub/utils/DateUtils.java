package pub.utils;

import pub.functions.DateFuncs;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by XDLK on 2016/9/12.
 * <p>
 * Date： 2016/9/12
 */
public class DateUtils {

    public static final SimpleDateFormat formatYMD = new SimpleDateFormat("yyyy-MM-dd");
    public static final SimpleDateFormat YMD = new SimpleDateFormat("yyyyMMdd");
    public static final SimpleDateFormat formatYMDHMS = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    /**
     * 获取两个时间戳间相隔的秒数
     *
     * @param cur
     * @param old
     * @return
     */
    public static long getMillis(long cur, long old) {
        return (cur - old) / 1000;
    }

    public static Date formt(Long time) {
        return new Date(time);
    }

    public static String getPlanTime(String time, int planYear, String format) {
        Date date = getDate(time, formatYMD);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);

        calendar.add(Calendar.YEAR, planYear);

        return formatDate(calendar.getTime(), format);
    }

    public static int getDateId(int week) {
        Calendar calendar = Calendar.getInstance();
        int nowDay = calendar.get(Calendar.DAY_OF_WEEK) - 1;

        int oneDayLong = 24 * 60 * 60 * 1000;

        long tim = calendar.getTimeInMillis() - (nowDay - week) * oneDayLong;
        calendar.setTimeInMillis(tim);
        return DateFuncs.toIntDate(calendar.getTime());
    }

    /**
     * 判断日期是否为周一
     *
     * @param date
     * @param week 表示周几，1周一，2周二
     * @return
     */
    public static boolean isWeekDay(Date date, int week) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        if (week == 7) {
            week = 1;
        } else {
            week += 1;
        }
        return calendar.get(Calendar.DAY_OF_WEEK) == week;
    }

    public static Date formatStr(String date, String pattern) {
        SimpleDateFormat format = new SimpleDateFormat(pattern);
        try {
            return format.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Date getLastTimeOfDate(int dateId) {
        Date time;
        time = DateFuncs.fromIntDate(dateId);
        time = DateFuncs.addDay(time, 1);
        time = DateFuncs.addSecond(time, -1);
        return time;
    }

    public static String strToStr(String time, String pattern, SimpleDateFormat toFormat) {
        Date dateTime = null;
        SimpleDateFormat format = new SimpleDateFormat(pattern);
        try {
            dateTime = format.parse(time);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        if (dateTime != null) {
            return toFormat.format(dateTime);
        }
        return "";
    }

    public static String formatDate(Object obj, String pattern) {
        Date dateTime = null;
        SimpleDateFormat format = new SimpleDateFormat(pattern);
        try {
            if (obj instanceof String) {
                dateTime = format.parse((String) obj);
            } else if (obj instanceof Date) {
                dateTime = (Date) obj;
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        if (dateTime != null) {
            return format.format(dateTime);
        }
        return "";
    }

    /**
     * 根据日期，获取该日期的星期
     *
     * @param obj
     * @return
     */
    public static int getWeek(Object obj) {
        Date dateTime = null;
        try {
            if (obj instanceof String) {
                dateTime = formatYMD.parse((String) obj);
            } else if (obj instanceof Date) {
                dateTime = (Date) obj;
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }

        if (dateTime != null) {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(dateTime);
            if (calendar.get(Calendar.DAY_OF_WEEK) == 1) {
                return 7;
            } else {
                return calendar.get(Calendar.DAY_OF_WEEK) - 1;
            }
        }

        return 0;
    }

    /**
     * format 今天、昨天、前天、MM-dd HH:mm、yyyy-MM-dd HH:mm
     *
     * @param time
     * @return
     */
    public static String formatDate(Object time) {

        if (time == null) {
            return "未知时间";
        }
        Date thTime = getDate(time, formatYMDHMS);

        Calendar now = Calendar.getInstance();
        now.setTime(new Date());

        Calendar start = Calendar.getInstance();
        start.setTime(thTime);

        if (isYear(start, now)) { // 如果同一年

            if (isMonth(start, now)) { // 如果同一个月

                // 前天
                if (start.after(getTowYesterday()) && start.before(getYesterday())) {
                    return "前天 " + new SimpleDateFormat("HH:mm").format(thTime);
                } else if (start.after(getYesterday()) && start.before(getToDay())) {
                    return "昨天 " + new SimpleDateFormat("HH:mm").format(thTime);
                } else if (start.after(getToDay()) && start.before(getToMonDay())) {
                    return "今天 " + new SimpleDateFormat("HH:mm").format(thTime);
                } else {
                    return new SimpleDateFormat("MM-dd HH:mm").format(thTime);
                    //System.out.println(new SimpleDateFormat("MM-dd HH:mm").format(time));
                }
            } else {
                return new SimpleDateFormat("MM-dd HH:mm").format(thTime);
            }

        } else {
            return new SimpleDateFormat("yyyy-MM-dd HH:mm").format(thTime);
        }
    }

    public static String formatDate(Date startTime, Date endTime) {

        Calendar now = Calendar.getInstance();
        now.setTime(new Date());

        Calendar start = Calendar.getInstance();
        start.setTime(startTime);

        if (endTime != null) {

            // 交换两个时间的顺序
            Date tmpDate = startTime;
            if (!startTime.before(endTime)) {
                startTime = endTime;
                endTime = tmpDate;
            }

            String startStr = formatDate(startTime);
            String endStr = formatDate(endTime);

            String startPre = startStr.split(" ")[0];
            String endPre = endStr.split(" ")[0];
            if (startPre.equals(endPre)) {
                return startStr + (endStr.replace(endPre, "").replace(" ", "-"));
            } else {
                return startStr + " - " + endStr;
            }
        }

        return formatDate(startTime);
    }

    /**
     * 判断是否为同一年
     *
     * @param calendar
     * @return
     */
    private static boolean isYear(Calendar calendar, Calendar now) {

        if (now.get(Calendar.YEAR) == calendar.get(Calendar.YEAR)) {
            return true;
        }
        return false;
    }

    /**
     * 判断是否为同一月
     *
     * @param calendar
     * @return
     */
    private static boolean isMonth(Calendar calendar, Calendar now) {

        if (now.get(Calendar.MONTH) == calendar.get(Calendar.MONTH)) {
            return true;
        }
        return false;
    }

    public static boolean isDay(Calendar calendar, Calendar now) {

        return true;
    }

    /**
     * 获取前两天时间
     *
     * @return
     */
    private static Calendar getTowYesterday() {
        Calendar yesterday = Calendar.getInstance();
        yesterday.set(Calendar.HOUR_OF_DAY, 23);
        yesterday.set(Calendar.MINUTE, 59);
        yesterday.set(Calendar.SECOND, 59);
        yesterday.set(Calendar.MILLISECOND, 1000);
        yesterday.add(Calendar.DATE, -3);
        return yesterday;
    }

    private static Calendar getThreeYesterday() {
        Calendar yesterday = Calendar.getInstance();
        yesterday.set(Calendar.HOUR_OF_DAY, 23);
        yesterday.set(Calendar.MINUTE, 59);
        yesterday.set(Calendar.SECOND, 59);
        yesterday.set(Calendar.MILLISECOND, 1000);
        yesterday.add(Calendar.DATE, -4);
        return yesterday;
    }

    /**
     * 获取昨天开始日期
     *
     * @return
     */
    private static Calendar getYesterday() {
        Calendar yesterday = Calendar.getInstance();
        yesterday.set(Calendar.HOUR_OF_DAY, 23);
        yesterday.set(Calendar.MINUTE, 59);
        yesterday.set(Calendar.SECOND, 59);
        yesterday.set(Calendar.MILLISECOND, 1000);
        yesterday.add(Calendar.DATE, -2);
        return yesterday;
    }

    /**
     * 获取今天开始时间
     *
     * @return
     */
    private static Calendar getToDay() {
        Calendar today = Calendar.getInstance();
        today.set(Calendar.HOUR_OF_DAY, 23);
        today.set(Calendar.MINUTE, 59);
        today.set(Calendar.SECOND, 59);
        today.set(Calendar.MILLISECOND, 1000);
        today.add(Calendar.DATE, -1);
        return today;
    }

    private static Calendar getToMonDay() {
        Calendar today = Calendar.getInstance();
        today.set(Calendar.HOUR_OF_DAY, 23);
        today.set(Calendar.MINUTE, 59);
        today.set(Calendar.SECOND, 59);
        today.set(Calendar.MILLISECOND, 1000);
        today.add(Calendar.DAY_OF_YEAR, 0);
        return today;
    }

    public static String toDayStr(Integer toDayCount) {
        return toDayStr(toDayCount, null, null);
    }

    public static String toDayStr(Integer toDayCount, String pattern) {
        return toDayStr(toDayCount, null, pattern);
    }

    public static String toDayStr(Integer toDayCount, Date dateTime) {
        return toDayStr(toDayCount, dateTime, null);
    }

    public static String toDayStr(Integer toDayCount, Date dateTime, String pattern) {
        Calendar cal = Calendar.getInstance();
        if (dateTime != null) {
            cal.setTime(dateTime);
        }

        cal.add(Calendar.DATE, toDayCount);
        return formatDate(cal.getTime(), pattern == null ? "yyyy-MM-dd" : pattern);
    }

    /**
     * 根据日期，获取本周开始、结束日期
     *
     * @param time
     * @return
     */
    public static Map<String, String> getCurWeek(Object time) {

        Map<String, String> map = new HashMap<>();

        Calendar cal = Calendar.getInstance();
        cal.setTime(getDate(time, formatYMD));
        // 判断要计算的日期是否是周日，如果是则减一天计算周六的，否则会出问题，计算到下一周去了
        int dayWeek = cal.get(Calendar.DAY_OF_WEEK);// 获得当前日期是一个星期的第几天
        if (1 == dayWeek) {
            cal.add(Calendar.DAY_OF_MONTH, -1);
        }
        cal.setFirstDayOfWeek(Calendar.MONDAY);
        int day = cal.get(Calendar.DAY_OF_WEEK);
        cal.add(Calendar.DATE, cal.getFirstDayOfWeek() - day);
        String imptimeBegin = formatYMD.format(cal.getTime());
        cal.add(Calendar.DATE, 6);
        String imptimeEnd = formatYMD.format(cal.getTime());

        map.put("start", imptimeBegin);
        map.put("end", imptimeEnd);

        return map;
    }

    /**
     * 获取上周开始，结束时间
     *
     * @param time
     * @return
     */
    public static Map<String, String> getPreviouWeek(Object time) {

        Map<String, String> map = new HashMap<>();

        Calendar cal = Calendar.getInstance();
        cal.setTime(getDate(time, formatYMD));
        cal.set(Calendar.DAY_OF_WEEK, 1);
        map.put("end", formatYMD.format(cal.getTime()));

        cal.add(Calendar.WEEK_OF_MONTH, -1);
        cal.set(Calendar.DAY_OF_WEEK, 2);
        map.put("start", formatYMD.format(cal.getTime()));

        return map;
    }

    /**
     * 获取下一周开始，结束时间
     *
     * @param time
     * @return
     */
    public static Map<String, String> getNextWeek(Object time) {

        Map<String, String> map = new HashMap<>();

        Calendar cal = Calendar.getInstance();
        cal.setTime(getDate("2016-09-20", formatYMD));

        cal.add(Calendar.WEEK_OF_MONTH, 1);
        cal.set(Calendar.DAY_OF_WEEK, 2);
        map.put("start", formatYMD.format(cal.getTime()));

        cal.add(Calendar.DATE, 6);
        map.put("end", formatYMD.format(cal.getTime()));

        return map;
    }

    private static Date getDate(Object time, SimpleDateFormat format) {
        Date dateTime = null;
        try {
            if (time instanceof String) {
                dateTime = format.parse((String) time);
            } else if (time instanceof Date) {
                dateTime = (Date) time;
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return dateTime;
    }

    public static Integer getLastMonthDayInt(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
        return DateFuncs.toIntDate(calendar.getTime());
    }

    public static Integer getFirstMonthDayInt(Date date) {
        return DateFuncs.toIntDate(getFirstMonthDay(date));
    }

    public static Date getFirstMonthDay(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMinimum(Calendar.DAY_OF_MONTH));
        return calendar.getTime();
    }

    public static Date getFirstWeekDay(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        return calendar.getTime();
    }

    public static Date getFirstWeekDay(Date date, int week) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        if (week != 0) {
            calendar.add(Calendar.DAY_OF_WEEK, week * 7);
        }
        calendar.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        return calendar.getTime();
    }

    public static Date getLastWeekDay(Date date, int week) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        if (week != 0) {
            calendar.add(Calendar.DAY_OF_WEEK, week * 7);
        }
        calendar.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.add(Calendar.WEEK_OF_YEAR, 1);
        return calendar.getTime();
    }

    public static Integer getFirstWeekDayInt(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        return DateFuncs.toIntDate(calendar.getTime());
    }

    public static Integer getLastWeekDayInt(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.add(Calendar.WEEK_OF_YEAR, 1);
        return DateFuncs.toIntDate(calendar.getTime());
    }

    public static String getWeekName(int week) {
        switch (week) {
            case 1:
                return "周一";
            case 2:
                return "周二";
            case 3:
                return "周三";
            case 4:
                return "周四";
            case 5:
                return "周五";
            case 6:
                return "周六";
            case 7:
                return "周日";
            default:
                return "未知";
        }
    }

    /**
     * 获取两个时间相隔的分钟数
     *
     * @param startDate
     * @param endDate
     * @return
     */
    public static Integer getMinute(Date startDate, Date endDate) {

        Calendar calendar1 = Calendar.getInstance();
        calendar1.setTime(startDate);

        Calendar calendar2 = Calendar.getInstance();
        calendar2.setTime(endDate);

        long time1 = calendar1.getTimeInMillis();
        long time2 = calendar2.getTimeInMillis();

        return (int) ((time2 - time1) / 60000);
    }
}
