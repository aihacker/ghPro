package pub.functions;

//import net.sf.json.JSONSerializer;
//import net.sf.json.JsonConfig;
//import net.sf.json.processors.JsDateJsonBeanProcessor;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

/**
 * describe: Created by IntelliJ IDEA.
 * @author zzl
 * @version 2007-1-31
 */
public class RspFuncs {

//	private static JsonConfig jsonConfig;
//	static {
//		jsonConfig=new JsonConfig();
//		JsDateJsonBeanProcessor beanProcessor = new JsDateJsonBeanProcessor();
//		jsonConfig.registerJsonBeanProcessor(java.util.Date.class, beanProcessor);
//	}
//
//	public static void writeJson(HttpServletResponse response, Object obj) throws IOException {
//		String json = JSONSerializer.toJSON(obj, jsonConfig).toString();
//		response.setCharacterEncoding("utf-8");
//		response.setContentType("text/html; charset=utf-8");
//		setNoCache(response);
//		response.getWriter().println(json);
//	}

    public static void deleteCookie(HttpServletResponse response, String name, String path) {
        Cookie cookie = new Cookie(name, null);
        cookie.setPath(path);
        cookie.setMaxAge(0);
        response.addCookie(cookie);
    }

    public static void setNoCache(HttpServletResponse response) {
        //response.setHeader("Cache-Control", "no-cache");
        response.setHeader("Cache-Control", "no-store");

    }

    public static void setCookie(HttpServletResponse response, String path,
                                 String name, String value,
                                 boolean persist) {
        Cookie cookie = new Cookie(name, value);
        cookie.setPath(path);
        if (persist) {
            cookie.setMaxAge(3600 * 24 * 365); // 1 year
        }
        else {
            cookie.setMaxAge(-1);
        }

        response.addCookie(cookie);
    }
}
