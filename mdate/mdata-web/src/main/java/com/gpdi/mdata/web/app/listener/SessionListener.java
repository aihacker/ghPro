package com.gpdi.mdata.web.app.listener;

import pub.web.PageDataStore;
import pub.web.RestoreRedirectedRequestDataFilter;

import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;
import java.util.HashMap;

/**
 * Created by IntelliJ IDEA.
 * User: zzl
 * Date: 2010-2-12
 * Time: 21:08:07
 */
public class SessionListener implements HttpSessionListener {

    public void sessionCreated(HttpSessionEvent event) {
        final HttpSession session = event.getSession();
        session.setAttribute(RestoreRedirectedRequestDataFilter.REQUEST_DATA_MAP_FOR_REDIRECT,
                new HashMap<String, Object>());

        PageDataStore.initialize(session);

        //
//        session.setAttribute(SessionConsts.SESSION_DATA, new SessionData());
    }

    public void sessionDestroyed(HttpSessionEvent event) {

    }
}
