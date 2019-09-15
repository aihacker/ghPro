package wxgh.app.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 日期工具类 默认使用 "yyyy-MM-dd HH:mm:ss" 格式化日期
 * 
 */

public class DateUtils {

	/**
	 * 英文简写（默认）如：2010-12-01
	 */
	public static String FORMAT_SHORT = "yyyy-MM-dd";

	/**
	 * 英文全称 如：2010-12-01 23:15:06
	 */
	public static String FORMAT_LONG = "yyyy-MM-dd HH:mm:ss";

	/**
	 * 精确到毫秒的完整时间 如：yyyy-MM-dd HH:mm:ss.S
	 */
	public static String FORMAT_FULL = "yyyy-MM-dd HH:mm:ss.S";

	/**
	 * 中文简写 如：2010年12月01日
	 */
	public static String FORMAT_SHORT_CN = "yyyy年MM月dd日";

	/**
	 * 中文全称 如：2010年12月01日 23时15分06秒
	 */
	public static String FORMAT_LONG_CN = "yyyy年MM月dd日  HH时mm分ss秒";

	/**
	 * 精确到毫秒的完整中文时间
	 */
	public static String FORMAT_FULL_CN = "yyyy年MM月dd日  HH时mm分ss秒SSS毫秒";

	/**
	 * 获得默认的 date pattern
	 */
	public static String getDatePattern() {
		return FORMAT_SHORT;
	}

	/**
	 * 根据预设格式返回当前日期
	 * 
	 * @return
	 */
	public static String getNow() {
		return format(new Date());
	}

	/**
	 * 根据用户格式返回当前日期
	 * 
	 * @param format
	 * @return
	 */
	public static String getNow(String format) {
		return format(new Date(), format);
	}

	/**
	 * 使用预设格式格式化日期
	 * 
	 * @param date
	 * @return
	 */
	public static String format(Date date) {
		return format(date, getDatePattern());
	}

	/**
	 * 使用用户格式格式化日期
	 * 
	 * @param date
	 *            日期
	 * @param pattern
	 *            日期格式
	 * @return
	 */
	public static String format(Date date, String pattern) {
		String returnValue = "";
		if (date != null) {
			SimpleDateFormat df = new SimpleDateFormat(pattern);
			returnValue = df.format(date);
		}
		return (returnValue);
	}

	/**
	 * 使用预设格式提取字符串日期
	 * 
	 * @param strDate
	 *            日期字符串
	 * @return
	 */
	public static Date parse(String strDate) {
		return parse(strDate, getDatePattern());
	}

	/**
	 * 使用用户格式提取字符串日期
	 * 
	 * @param strDate
	 *            日期字符串
	 * @param pattern
	 *            日期格式
	 * @return
	 */
	public static Date parse(String strDate, String pattern) {
		SimpleDateFormat df = new SimpleDateFormat(pattern);
		try {
			return df.parse(strDate);
		} catch (ParseException e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * 在日期上增加数个整月
	 * 
	 * @param date
	 *            日期
	 * @param n
	 *            要增加的月数
	 * @return
	 */
	public static Date addMonth(Date date, int n) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.MONTH, n);
		return cal.getTime();
	}

	/**
	 * 在日期上增加天数
	 * 
	 * @param date
	 *            日期
	 * @param n
	 *            要增加的天数
	 * @return
	 */
	public static Date addDay(Date date, int n) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.DATE, n);
		return cal.getTime();
	}

	/**
	 * @url
	 * @desciption 在时间上增加多少秒
	 * @status 已完成
	 * @update zcr
	 * @updatetime 20171208
	 * @reason
	 */
	public static Date addSeconds(Date date, int second) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.SECOND, second);
		return cal.getTime();
	}

	/**
	 * 获取时间戳
	 */
	public static String getTimeString() {
		SimpleDateFormat df = new SimpleDateFormat(FORMAT_FULL);
		Calendar calendar = Calendar.getInstance();
		return df.format(calendar.getTime());
	}

	/**
	 * 获取日期年份
	 * 
	 * @param date
	 *            日期
	 * @return
	 */
	public static String getYear(Date date) {
		return format(date).substring(0, 4);
	}

	/**
	 * 按默认格式的字符串距离今天的天数
	 * 
	 * @param date
	 *            日期字符串
	 * @return
	 */
	public static int countDays(String date) {
		SimpleDateFormat fomat = new SimpleDateFormat(getDatePattern());
		try {
			Date date1 = fomat.parse(date);
			return countDays(date1);

		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return 0;
	}

	/**
	 * 按用户格式字符串距离今天的天数
	 * 
	 * @param date
	 *            日期字符串
	 * @param format
	 *            日期格式
	 * @return
	 */
	public static int countDays(String date, String format) {
		long t = Calendar.getInstance().getTime().getTime();
		Calendar c = Calendar.getInstance();
		c.setTime(parse(date, format));
		long t1 = c.getTime().getTime();
		return (int) (t / 1000 - t1 / 1000) / 3600 / 24;
	}

	/**
	 * @author zhaoqide
	 * @datetime 2017年11月13日下午4:31:12
	 * @desciption 比较与当前时间的天数差值，返回正数，表示过去时间，返回负数，表述未来时间
	 * @update zhaoqide
	 * @status 已完成
	 * @reason 更改原因 需求变更/修复bug
	 * @param date
	 * @param format
	 * @return
	 */
	public static int countDays(Date date) {

		Calendar c = Calendar.getInstance();
		SimpleDateFormat fomat = new SimpleDateFormat(FORMAT_SHORT);
		String now = fomat.format(c.getTime());

		long t = 0;
		try {
			t = fomat.parse(now).getTime();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		c.setTime(date);
		long t1 = 0;
		try {
			t1 = fomat.parse(fomat.format(date)).getTime();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return (int) (t / 1000 - t1 / 1000) / 3600 / 24;
	}

	/**
	 * @author zhaoqide
	 * @url 请求地址
	 * @datetime 2017年11月13日下午5:38:45
	 * @desciption 比较小时
	 * @status 已完成
	 * @reason 更改原因 需求变更/修复bug
	 * @param date
	 * @return
	 */
	public static int countHours(Date date) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH");
		Calendar c = Calendar.getInstance();
		long t = 0;
		try {
			t = format.parse(format.format(c.getTime())).getTime();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		c.setTime(date);
		long t1 = 0;
		try {
			t1 = format.parse(format.format(c.getTime())).getTime();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return (int) (t / 1000 - t1 / 1000) / 3600;
	}

	/**
	 * @author zhaoqide
	 * @datetime 2017年11月13日下午5:39:27
	 * @desciption
	 * @update zhaoqide
	 * @status 已完成
	 * @reason 更改原因 需求变更/修复bug
	 * @param saleTime
	 * @return
	 */
	@SuppressWarnings("deprecation")
	public static int compareHours(String saleTime) {
		Date date = new Date();
		if ("1".equals(saleTime)) {
			date.setHours(6);
			if (countHours(date) >= 0) {
				return 1;
			}
			return -1;
		} else if ("2".equals(saleTime)) {
			date.setHours(11);
			if (countHours(date) >= 0) {
				return 1;
			}
			return -1;
		} else {
			date.setHours(17);
			if (countHours(date) >= 0) {
				return 1;
			}
			return -1;
		}
	}

	/**
	 * @url
	 * @desciption 根据日期取得星期几
	 * @status 已完成
	 * @update
	 * @updatetime
	 * @reason
	 */
	public static String getWeek(Date date) {
		SimpleDateFormat sdf = new SimpleDateFormat("EEEE");
		String week = sdf.format(date);
		return week;
	}

}





	

	
	
	