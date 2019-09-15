package pub.web;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Created by Administrator on 2017/7/11.
 */
public class ServletUtils {

    private static ThreadLocal<HttpServletRequest> servletRequest = new ThreadLocal<>();
    private static ThreadLocal<HttpServletResponse> servletResponse = new ThreadLocal<>();

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

    public static String getHost() {
        HttpServletRequest request = getRequest();
        return request.getScheme() + "://" + request.getServerName() + (request.getServerPort() == 80 ? "" : (":" + request.getServerPort()));
    }

    public static String getHostAddr() {
        HttpServletRequest request = getRequest();
        String appPath = request.getContextPath();
        return request.getScheme() + "://" + request.getServerName() + (request.getServerPort() == 80 ? "" : (":" + request.getServerPort())) + appPath;
    }

    public static String getSelf() {
        HttpServletRequest request = ServletUtils.getRequest();
        String url = request.getRequestURL().toString();
        String qs = request.getQueryString();
        if (qs != null && qs.length() > 0) {
            url = request.getRequestURL() + "?" + request.getQueryString();
        }
        return url;
    }
}
