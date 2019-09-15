package com.libs.common.scheduler;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by Administrator on 2017/7/26.
 */
public class CronUtils {

    /**
     * 根据活动开始时间和提前时间推送出活动开始时间
     *
     * @param startTime 活动开始时间 yyyy-MM-dd HH:mm格式
     * @param remind    提前提醒时间
     * @return int[]{小时，分钟，相隔天数}
     */
    public static int[] getTime(Date startTime, Float remind) {
        Calendar time = Calendar.getInstance();
        time.setTime(startTime);
        int minute = (int) (remind * 60);

        Calendar time1 = Calendar.getInstance();
        time1.setTime(time.getTime());
        time1.add(Calendar.MINUTE, -minute);

        //计算两个时间相隔天数
        int dayDif = time.get(Calendar.DAY_OF_MONTH) - time1.get(Calendar.DAY_OF_MONTH);

        Integer hour = time1.get(Calendar.HOUR_OF_DAY);
        Integer minu = time1.get(Calendar.MINUTE);

        return new int[]{hour, minu, dayDif};
    }

    public static String getCronOne(Date startTime, Float remind) {
        int minute = (int) (remind * 60);
        Calendar cal = Calendar.getInstance();
        cal.setTime(startTime);
        cal.add(Calendar.MINUTE, -minute);

        StringBuilder cron = new StringBuilder();
        cron.append("0"); //秒
        cron.append(" " + cal.get(Calendar.MINUTE)); //分
        cron.append(" " + cal.get(Calendar.HOUR_OF_DAY)); //小时
        cron.append(" " + cal.get(Calendar.DAY_OF_MONTH)); //日
        cron.append(" " + (cal.get(Calendar.MONTH) + 1)); //月
        cron.append(" ?"); //星期
        int year = cal.get(Calendar.YEAR);
        cron.append(" " + year + "-" + year);
        return cron.toString();
    }

    public static String getCron(Date time) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(time);
        StringBuilder cron = new StringBuilder();
        cron.append("0"); //秒
        cron.append(" " + cal.get(Calendar.MINUTE)); //分
        cron.append(" " + cal.get(Calendar.HOUR_OF_DAY)); //小时
        cron.append(" " + cal.get(Calendar.DAY_OF_MONTH)); //日
        cron.append(" " + (cal.get(Calendar.MONTH) + 1)); //月
        cron.append(" ?"); //星期
        int year = cal.get(Calendar.YEAR);
        cron.append(" " + year + "-" + year);
        return cron.toString();
    }

    public static String getCronWeek(Date startTime, int[] weeks, Float remind) {
        int[] times = getTime(startTime, remind);
        String weekCron;
        if (weeks.length == 7) {
            weekCron = "*";
        } else {
            int dayDif = times[2];
            weekCron = "";
            if (dayDif == 0) {
                for (int i = 0, len = weeks.length; i < len; i++) {
                    if (i > 0) {
                        weekCron += ",";
                    }
                    weekCron += (weeks[i] + 1) > 7 ? (weeks[i] + 1) - 7 : (weeks[i] + 1);
                }
            } else {
                for (int i = 0, len = weeks.length; i < len; i++) {
                    int week = weeks[i];
                    int newWeek = week > dayDif ? (week - dayDif) : (7 - dayDif + week);
                    if (i > 0) {
                        weekCron += ",";
                    }
                    weekCron += (newWeek + 1) > 7 ? (newWeek + 1) - 7 : (newWeek + 1);
                }
            }
        }

        StringBuilder cron = new StringBuilder();
        cron.append("0"); //秒
        cron.append(" " + times[1]); //分
        cron.append(" " + times[0]); //小时
        cron.append(" ?"); //日
        cron.append(" *"); //月
        cron.append(" " + weekCron); //星期
        return cron.toString();
    }

//    public static void main(String[] args) {
//        System.out.println(getCronWeek(new Date(), new int[]{1, 7}, 0F));
//        System.out.println(getCronWeek(new Date(), new int[]{2, 4}, 0F));
//        System.out.println(getCronWeek(new Date(), new int[]{6, 7}, 0F));
//    }

}
