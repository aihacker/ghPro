package com.gpdi.mdata.web.app.listener;


import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 * describe: Created by IntelliJ IDEA.
 * @author zzl
 * @version 2010-2-9
 */

public class AppListener implements ServletContextListener {

    public void contextInitialized(ServletContextEvent event) {
        ServletContext sc = event.getServletContext();
        sc.setAttribute("home", sc.getContextPath());
        initMdEnvironment();
    }

    private void initMdEnvironment() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

    }

    public void contextDestroyed(ServletContextEvent event) {

    }



}
