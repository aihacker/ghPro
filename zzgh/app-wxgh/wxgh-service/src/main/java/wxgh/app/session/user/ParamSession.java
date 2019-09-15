package wxgh.app.session.user;

import pub.web.ServletUtils;

/**
 * Created by Administrator on 2017/6/22.
 */
public class ParamSession {

    private static final String SESSION_ID = "session_wxgh_param";

    public static void put(String param) {
        ServletUtils.getSession().setAttribute(SESSION_ID, param);
    }

    public static String get() {
        return (String) ServletUtils.getSession().getAttribute(SESSION_ID);
    }

    public static void setEmpty() {
        ServletUtils.getSession().removeAttribute(SESSION_ID);
    }
}
