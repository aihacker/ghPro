package pub.spring.bean;

import org.springframework.util.StringUtils;

import java.beans.PropertyEditorSupport;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by IntelliJ IDEA.
 * User: zzl
 * Date: 2010-2-11
 * Time: 22:43:26
 */
public class DateEditor extends PropertyEditorSupport {

	private static final String[] patterns;

	static {
		patterns = new String[]{
			"yyyy-MM-dd HH:mm:ss",
			"yyyy-MM-dd HH:mm",
			"yyyy-MM-dd",
			"yyyy-MM"};
	}

	@Override
	public void setAsText(String text) throws IllegalArgumentException {
		if (!StringUtils.hasText(text)) {
			setValue(null);
		}
		else {
			Date date = null;
			for (String pattern : patterns) {
				try {
					SimpleDateFormat format = new SimpleDateFormat(pattern);
					date = format.parse(text);
					break;
				}
				catch (Exception e) {
					// do nothing
				}
			}
			if (date == null) {
				throw new IllegalArgumentException("Could not parse date: " + text);
			}
			setValue(date);
		}
	}

	@Override
	public String getAsText() {
		Date value = (Date) getValue();
		if (value == null) {
			return "";
		}
		SimpleDateFormat format = new SimpleDateFormat(patterns[1]);
		return format.format(value);
	}

}
