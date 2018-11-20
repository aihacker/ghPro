package com.gpdi.mdata.web.app.filter;

import com.gpdi.mdata.web.app.consts.SessionConsts;
import com.gpdi.mdata.web.app.data.SessionData;
import pub.functions.PropsFuncs;
import pub.functions.ReqFuncs;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * describe: Created by IntelliJ IDEA.
 *
 * @author zzl
 * @version 2015/7/16
 */
public class SecurityFilter implements Filter {

    public void init(FilterConfig filterConfig) {
        // do nothing
    }

    public void doFilter(ServletRequest servletRequest,
                         ServletResponse servletResponse, FilterChain filterChain)
            throws IOException, ServletException {

        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        response.addHeader("P3P: CP", "\"CAO PSA OUR\"");
        // logined

        String home = request.getContextPath();
        String uri = request.getRequestURI();

        // admin
        HttpSession session = request.getSession();
        SessionData sessionData = (SessionData) session.getAttribute(SessionConsts.SESSION_DATA);
        if (sessionData != null) {
            filterChain.doFilter(servletRequest, servletResponse);
            return;
        }

        String reqUrl = ReqFuncs.getUrl(request);
        if (reqUrl.contains("doGPToDb")) {// || true) {
            filterChain.doFilter(servletRequest, servletResponse);
            return;
        }

        // don't care about login page
        String loginUrl = home + "/login.html";
        if (uri.equals(loginUrl)) {// || true) {
            filterChain.doFilter(servletRequest, servletResponse);
            return;
        }

        //ycy don't care about doGPToDb request
        String reqUrl2 = ReqFuncs.getUrl(request);
//        if (reqUrl2.contains("/daoexcel/purchase/query.html")) {// || true) {
        if (reqUrl2.contains("daoexcel")) {// || true) {
            filterChain.doFilter(servletRequest, servletResponse);
            return;
        }
      /*  String reqUrl3 = ReqFuncs.getUrl(request);
//        if (reqUrl3.contains("/daoexcel/purchase/operate.json?action=daoExcel")) {// || true) {
        if (reqUrl3.contains("daoexcel")) {// || true) {
            filterChain.doFilter(servletRequest, servletResponse);
            return;
        }*/
       /* String reqUrl4 = ReqFuncs.getUrl(request);
        if (reqUrl4.contains("/form/contractmostbytype/query.html")) {// || true) {
            filterChain.doFilter(servletRequest, servletResponse);
            return;
        }*/
       /* if (reqUrl3.contains("/daoexcel/purchase/show.json?action=save&&code")) {// || true) {
            filterChain.doFilter(servletRequest, servletResponse);
            return;
        }


        if (reqUrl3.contains("/daoexcel/cleaninventory/show.json?action=save&&code")) {// || true) {
            filterChain.doFilter(servletRequest, servletResponse);
            return;
        }*/

        //htd don't care about doDbToGP request
        if (reqUrl.contains("doDbToGP")) {// || true) {
            filterChain.doFilter(servletRequest, servletResponse);
            return;
        }

        //htd don't care about doFtpToGP request
        if (reqUrl.contains("doFtpToGP")) {// || true) {
            filterChain.doFilter(servletRequest, servletResponse);
            return;
        }

        //htd don't care about doGPToFtp request
        if (reqUrl.contains("doGPToFtp")) {// || true) {
            filterChain.doFilter(servletRequest, servletResponse);
            return;
        }

        response.sendRedirect(loginUrl);
    }

    public void destroy() {
        // do nothing
    }
}
