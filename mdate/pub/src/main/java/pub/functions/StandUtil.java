package pub.functions;

import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;

/**
 * 常用工具类
 * @author DuanGongwei
 * @version 1.0 Sep 9, 2008
 */
public class StandUtil {

	/**
	 * Convert object to integer
	 * @param obj
	 * @return
	 */
	public static int toInt(Object obj) {
		try {
			return Integer.parseInt(obj.toString());
		}
		catch (Exception e) {
			return 0;
		}
	}

	/**
	 * Convert object to short
	 * @param obj
	 * @return
	 */
	public static short toShort(Object obj) {
		try {
			return Short.parseShort(obj.toString());
		}
		catch (Exception e) {
			return 0;
		}
	}

	/**
	 * Convert object to long
	 * @param obj
	 * @return
	 */
	public static long toLong(Object obj) {
		try {
			return Long.parseLong(obj.toString());
		}
		catch (Exception e) {
			return 0;
		}
	}

	/**
	 * Convert object to double
	 * @param obj
	 * @return
	 */
	public static double toDouble(Object obj) {
		try {
			return Double.parseDouble(obj.toString());
		}
		catch (Exception e) {
			return 0;
		}
	}

	/**
	 * Convert object to float
	 * @param obj
	 * @return
	 */
	public static float toFloat(Object obj) {
		try {
			return Float.parseFloat(obj.toString());
		}
		catch (Exception e) {
			return 0;
		}
	}

	/**
	 * Convert object to string
	 * @param obj
	 * @return
	 */
	public static String toString(Object obj) {
		try {
			return obj.toString();
		}
		catch (Exception e) {
			return "";
		}
	}

	/**
	 * Convert object to date
	 * @param obj
	 * @return
	 */
	public static Date toDate(Object obj, String pattern) {
		try {
			SimpleDateFormat sf = new SimpleDateFormat(pattern);
			return sf.parse(obj.toString());
		}
		catch (Exception e) {
			return new Date();
		}
	}

	/**
	 * Convert object to date
	 * @param obj
	 * @return
	 */
	public static Date toDate(Object obj) {
		try {
			return DateFormat.getDateInstance().parse(obj.toString());
		}
		catch (Exception e) {
			return new Date();
		}
	}

	/**
	 * Convert object to boolean
	 * @param obj
	 * @return
	 */
	public static boolean toBoolean(Object obj) {
		try {
			return Boolean.parseBoolean(obj.toString());
		}
		catch (Exception e) {
			return false;
		}
	}

	/**
	 * Truncate a string by a character
	 * @param obj
	 * @return
	 */
	public static String truncate(Object obj, char ch) {
		try {
			String str = obj.toString();
			int i = str.indexOf(ch);
			if (i == -1) {
				return str;
			}
			else {
				return str.substring(0, i);
			}
		}
		catch (Exception e) {
			return "";
		}
	}

	/**
	 * Fetch the max value of a array
	 * @param var
	 * @return
	 */
	public static int max(int[] var) {
		if (var == null)
			return 0;
		int max = var[0];
		for (int i = 1; i < var.length; i++) {
			if (var[i] > max)
				max = var[i];
		}
		return max;
	}

	/**
	 * Fetch the min value of a array
	 * @param var
	 * @return
	 */
	public static int min(int[] var) {
		if (var == null)
			return 0;
		int min = var[0];
		for (int i = 1; i < var.length; i++) {
			if (var[i] < min)
				min = var[i];
		}
		return min;
	}

	/**
	 * Make a map by a obj
	 * @param obj
	 * @return
	 */
	public static Map makeMap(Object obj) {
		try {
			return BeanUtils.describe(obj);
		}
		catch (Exception e) {
			print("Make Map Exception !");
		}
		return null;
	}

	public static int[] subArray(int[] arr1, int[] arr2) {
		if (arr1 == null || arr2 == null) {
			return null;
		}
		int[] tmp = new int[arr1.length];
		for (int i = 0; i < tmp.length; i++) {
			tmp[i] = arr1[i];
		}
		int leng = Math.min(arr1.length, arr2.length);
		for (int i = 0; i < leng; i++) {
			tmp[i] -= arr2[i];
		}
		return tmp;
	}

	public static int[] addArray(int[] arr1, int[] arr2) {
		if (arr1 == null || arr2 == null) {
			return null;
		}
		int[] tmp = new int[arr1.length];
		for (int i = 0; i < tmp.length; i++) {
			tmp[i] = arr1[i];
		}
		int leng = Math.min(arr1.length, arr2.length);
		for (int i = 0; i < leng; i++) {
			tmp[i] += arr2[i];
		}
		return tmp;
	}

	/**
	 * Correct the page value
	 * @param count
	 * @param page
	 * @param size
	 * @return
	 */
	public static int correctPage(int count, int page, int size) {
		int total = (int) Math.ceil((double) count / size) - 1;
		int current = page;
		if (page < 0) {
			current = 0;
		}
		else if (page > total) {
			current = total;
		}
		return current;
	}

	/**
	 * Fetch the suffix
	 * @param name
	 * @return
	 */
	public static String suffix(String name) {
		int index = name.lastIndexOf('.');
		return name.substring(index);
	}

	/**
	 * Fetch the prefix
	 * @param name
	 * @return
	 */
	public static String prefix(String name) {
		int index = name.lastIndexOf('.');
		return name.substring(0, index).replace('\\', File.separatorChar);
	}

	/**
	 * Fetch the directory
	 * @param path
	 * @return
	 */
	public static String folder(String path) {
		int index = path.lastIndexOf('\\');
		return path.substring(0, index - 1).replace('\\', File.separatorChar);
	}

	/**
	 * Fetch the file name
	 * @param path
	 * @return
	 */
	public static String file(String path) {
		int index = path.lastIndexOf('\\');
		return path.substring(index + 1, path.length());
	}

	/**
	 * Check if the obj is empty
	 * @param obj
	 * @return
	 */
	public static boolean isEmpty(Object obj) {
		if (obj == null)
			return true;
		if (obj instanceof String) {
			return "".equals(obj) ? true : false;
		}
		else if (obj instanceof Map) {
			return ((Map) obj).isEmpty();
		}
		else if (obj instanceof Collection) {
			return ((Collection) obj).isEmpty();
		}
		else {
			return false;
		}
	}

	/**
	 * Check if the obj is not empty
	 * @param obj
	 * @return
	 */
	public static boolean notEmpty(Object obj) {
		return !isEmpty(obj);
	}

	public static String bothLike(Object str) {
		return "%" + str + "%";
	}

	public static String leftLike(Object str) {
		return str + "%";
	}

	public static String rightLike(Object str) {
		return str + "%";
	}

	public static void print(Object msg) {
		System.out.println("\n- - - - - - - - Message - - - - - - - -");
		System.out.println(msg);
		System.out.println("- - - - - - - - - - - - - - - - - - - -\n");
	}
	public static <T extends Object> T castType(Object src, Class<T> c) {
		return c.cast(src);
	}

	public static void debug(Exception e) {
		System.out.println("\n- - - - - - - - Exception - - - - - - - -");
		System.out.println("Class : " + e.getClass());
		StackTraceElement[] msg = e.getStackTrace();
		for (int i = 0; i < msg.length; i++) {
			System.out.println(msg[i]);
		}
		System.out.println("- - - - - - - - - - - - - - - - - - - - -\n");
	}
	public static Double obj2Double(Object obj) {
		if (obj == null)
			return null;
		return Double.parseDouble(obj.toString());

	}

	/**
	 * Get the absolute path of complier classes
	 * @return
	 */
	public static String getClassPath() {
		return ClassLoader.getSystemResource(".")
			.getPath().substring(1);
	}

}
