package pub.functions;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;

/**
 * describe: Created by IntelliJ IDEA.
 * @author zzl
 * @version 2010-11-1
 */
public class UrlFuncs {

	public static String encodeURIComponent(String s) {
		return encodeURIComponent(s, "utf-8");
	}

	public static String encodeURIComponent(String s, String encoding) {
		try {
			String result = URLEncoder.encode(s, encoding)
				.replaceAll("\\+", "%20")
				.replaceAll("\\%21", "!")
				.replaceAll("\\%27", "'")
				.replaceAll("\\%28", "(")
				.replaceAll("\\%29", ")")
				.replaceAll("\\%7E", "~");
			return result;
		}
		catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public static String decodeURIComponent(String s, String encoding) {
		try {
			String result = URLDecoder.decode(s, encoding);
			return result;
		}
		catch (UnsupportedEncodingException e) {
			throw new RuntimeException(e);
		}
	}
}
