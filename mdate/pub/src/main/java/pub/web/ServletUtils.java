package pub.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * describe: Created by IntelliJ IDEA.
 * @author zzl
 * @version 2008-8-25
 */
public class ServletUtils {
	private static ThreadLocal<HttpServletRequest> servletRequest = new ThreadLocal<HttpServletRequest>();
	private static ThreadLocal<HttpServletResponse> servletResponse = new ThreadLocal<HttpServletResponse>();

	public static HttpServletRequest getRequest() {
		return servletRequest.get();
	}

	public static HttpServletResponse getResponse() {
		return servletResponse.get();
	}

	public static void setRequest(HttpServletRequest request) {
		servletRequest.set(request);
	}

	public static void setResponse(HttpServletResponse response) {
		servletResponse.set(response);
	}

	public static HttpSession getSession() {
		return getRequest().getSession();
	}

	public static String getRequestUri() {
		return getRequest().getRequestURI();
	}

	public static String getRequestUrl() {
		return getRequest().getRequestURL().toString();
	}

	public static String getShortRequestUrl() {
		HttpServletRequest request = getRequest();
		String url = request.getRequestURI();
		String qs = request.getQueryString();
		if (qs != null && qs.length() > 0) {
			url = url + '?' + qs;
		}
		return url;
	}
}
