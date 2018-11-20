package pub.beans.converter;

import java.text.SimpleDateFormat;

import org.apache.commons.beanutils.ConvertUtils;
import org.apache.commons.beanutils.Converter;

import pub.functions.DateFuncs;

/**
 * describe: Created by IntelliJ IDEA.
 * @author zzl
 * @version 2008-3-30
 */
public class DateConverter implements Converter {
	private static final SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
	private static final SimpleDateFormat format2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	private static final SimpleDateFormat format3 = new SimpleDateFormat("yyyy-MM-dd HH:mm");
	private static final SimpleDateFormat format4 = new SimpleDateFormat("yyyy-MM");

	public Object convert(Class type, Object value) {
		if (value == null) return null;
		if (!(value instanceof String)) return ConvertUtils.convert(value);
		if (((String) value).trim().length() == 0)
			return null;
		return DateFuncs.convert(value.toString());
	}

}
