package pub.functions;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * describe: Created by IntelliJ IDEA.
 * @author zzl
 * @since 2008-12-2 23:50:22
 */
public class ValidateFuncs {

	public static boolean validateMobile(String s) {
		return s.matches("^1[358]\\d{9}$");
	}

	public static boolean validateTelephone(String s) {
		return s.matches("^((\\(\\d{2,3}\\))|(\\d{3}\\-))?(\\(0\\d{2,3}\\)|0\\d{2,3}-)?[1-9]\\d{6,7}(\\-\\d{1,4})?$");
	}

	public static boolean validateTelno(String s) {
		return s.matches("^(\\d{6,13}|\\d{3}-\\d{6,10})$");
	}

	public static boolean validateAlphaNum(String s) {
		return s.matches("^[A-Za-z0-9]+$");
	}

	public static boolean isNumeric(String str) {
		Pattern pattern = Pattern.compile("[0-9]*(\\.?)[0-9]*");
		Matcher isNum = pattern.matcher(str);
		if (!isNum.matches()) {
			return false;
		}
		return true;
	}

	public static boolean isInteger(String str) {
		Pattern pattern = Pattern.compile("[0-9]*");
		Matcher isNum = pattern.matcher(str);
		if (!isNum.matches()) {
			return false;
		}
		return true;
	}

	public static boolean validateEmail(String s) {
		return s.matches("\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*");
	}
	public static boolean validatePostcode(String s) {
		return s.matches("[1-9]\\d{5}(?!\\d)");
	}
}
