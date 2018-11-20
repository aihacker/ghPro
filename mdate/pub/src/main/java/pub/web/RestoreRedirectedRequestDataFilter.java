package pub.web;

import java.io.IOException;
import java.util.Map;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * describe: Created by IntelliJ IDEA.
 * @author zzl
 * @version 2010-2-12
 */
@SuppressWarnings("unchecked")
public class RestoreRedirectedRequestDataFilter implements Filter {

	public static final String REQUEST_DATA_MAP_FOR_REDIRECT = "REQUEST_DATA_MAP_FOR_REDIRECT";
	private static final String LAST_REDIRECTED_URI = "LAST_REDIRECTED_URI";

	private static Map<String, Object> getDataMap() {
		HttpServletRequest request = ServletUtils.getRequest();
		HttpSession session = request.getSession();
		Map<String, Object> map = (Map<String, Object>)
			session.getAttribute(REQUEST_DATA_MAP_FOR_REDIRECT);
		return map;
	}

	public static void setRequestData(String name, Object value) {
		Map<String, Object> map = getDataMap();
		map.put(name, value);
	}

	public static Object getRequestData(String name) {
		return getDataMap().get(name);
	}

	public void init(FilterConfig config) throws ServletException {
		// do nothing
	}

	@SuppressWarnings("unchecked")
	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse,
						 FilterChain chain) throws IOException, ServletException {

		HttpServletRequest request = (HttpServletRequest) servletRequest;
		HttpSession session = request.getSession();
		Map<String, Object> requestDataMap = (Map<String, Object>)
			session.getAttribute(REQUEST_DATA_MAP_FOR_REDIRECT);
		if (requestDataMap.size() > 0) {
			for (Map.Entry<String, Object> entry : requestDataMap.entrySet()) {
				request.setAttribute(entry.getKey(), entry.getValue());
			}
			String requestUri = request.getRequestURI();
			String lastRedirectedUri = (String) session.getAttribute(LAST_REDIRECTED_URI);
			if (lastRedirectedUri == null) {
				session.setAttribute(LAST_REDIRECTED_URI, requestUri);
			}
			else {
				if (!lastRedirectedUri.equals(requestUri)) {
					session.removeAttribute(LAST_REDIRECTED_URI);
					requestDataMap.clear();
				}
			}
		}
		chain.doFilter(servletRequest, servletResponse);
	}

	public void destroy() {
		// do nothing
	}
}
