package com.gpdi.mdata.web.app.utils;


import com.gpdi.mdata.sys.entity.system.SysUser;
import com.gpdi.mdata.web.app.data.SessionData;
import com.gpdi.mdata.web.app.consts.SessionConsts;
import pub.web.ServletUtils;

import javax.servlet.http.HttpSession;
import java.util.List;

/**
 *
 */
public class SessionUtils {

    public static void loginAdmin() {
        HttpSession session = ServletUtils.getSession();
        SessionData sessionData = new SessionData();
        sessionData.setUserId(0);
        sessionData.setUserName("系统管理员");
        //sessionData.setUserType(SysConsts.USER_ADMIN);
        session.setAttribute(SessionConsts.SESSION_DATA, sessionData);
    }

//    public static void loginSteward(Steward steward) {
//        HttpSession session = ServletUtils.getSession();
//        SessionData sessionData = new SessionData();
//        sessionData.setUserId(steward.getId());
//        sessionData.setUserName(steward.getName());
//        sessionData.setUserType(SysConsts.USER_STEWARD);
//
//        session.setAttribute(SessionConsts.SESSION_DATA, sessionData);
//    }


    public static void loginUserInfo(SysUser user,List<String> codes) {
        HttpSession session = ServletUtils.getSession();
        SessionData sessionData = new SessionData();
        sessionData.setUserId(user.getUserId());
        sessionData.setUserName(user.getUserName());
        sessionData.setModuleCode(codes);
        session.setAttribute(SessionConsts.SESSION_DATA, sessionData);
    }

    //普通用户
    public static void loginUser(SysUser user) {
        HttpSession session = ServletUtils.getSession();
        SessionData sessionData = new SessionData();
        sessionData.setUserId(user.getUserId());
        sessionData.setUserName(user.getUserName());
        session.setAttribute(SessionConsts.SESSION_DATA, sessionData);
    }

    public static SessionData getSessionData() {
        HttpSession session = ServletUtils.getSession();
        return (SessionData) session.getAttribute(SessionConsts.SESSION_DATA);
    }
}
