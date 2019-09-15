package wxgh.admin.session;

import pub.error.ValidationError;
import pub.web.ServletUtils;
import wxgh.admin.session.bean.SeAdmin;

/**
 * Created by Administrator on 2017/7/13.
 */
public class AdminSession {

    private static final String SESSION_TAG = "root";

    public static SeAdmin getAdmin() {
        return (SeAdmin) ServletUtils.getSession().getAttribute(SESSION_TAG);
    }

    public static void setAdmin(SeAdmin admin) {
        ServletUtils.getSession().setAttribute(SESSION_TAG, admin);
    }

    public static String getAdminId() {
        SeAdmin admin = getAdmin();
        if (admin == null) {
            throw new ValidationError("未知管理员");
        }
        return admin.getAdminId();
    }

    public static String getCateId() {
        SeAdmin admin = getAdmin();
        if (admin == null) {
            throw new ValidationError("未知管理员");
        }
        return admin.getCateId();
    }

    /**
     * 获取支部ID
     *
     * @return
     */
//    public static Integer getPartyId() {
//        SeAdmin admin = getAdmin();
//        if (admin.getCateId().equals("0")) { //超级管理员
//            return 1;
//        }
//        String partyId = admin.getExtraMap().get("PARTYID");
//        return partyId == null ? -1 : Integer.valueOf(partyId);
//    }

    public static void remove() {
        ServletUtils.getSession().removeAttribute(SESSION_TAG);
    }
}
