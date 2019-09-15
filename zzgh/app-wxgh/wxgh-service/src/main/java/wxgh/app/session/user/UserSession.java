package wxgh.app.session.user;

import pub.error.ValidationError;
import pub.web.ServletUtils;
import wxgh.app.session.bean.SeUser;

/**
 * Created by Administrator on 2017/7/13.
 */
public class UserSession {

    private static final String SESSION_TAG = "wxgh_user";

    public static SeUser getUser() {
        return (SeUser) ServletUtils.getSession().getAttribute(SESSION_TAG);
    }

    public static void setUser(SeUser user) {
        ServletUtils.getSession().setAttribute(SESSION_TAG, user);
    }

    public static String getUserid() {
        SeUser user = getUser();
        if (user == null) {
            throw new ValidationError("未知用户");
        }
        return user.getUserid();
    }
}
