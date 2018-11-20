package com.gpdi.mdata.web.app.filter;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import pub.web.ServletUtils;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;

/**
 * describe: Created by IntelliJ IDEA.
 * @author zzl
 * @version 2010-2-12
 */
public class RequestDataFilter implements Filter {

    private static final Log log = LogFactory.getLog(RequestDataFilter.class);

    public void init(FilterConfig config) {
        // do nothing
    }

    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse,
                         FilterChain chain) throws IOException, ServletException {

        HttpServletRequest request = (HttpServletRequest) servletRequest;

        ServletUtils.setRequest(request);
        ServletUtils.setResponse((HttpServletResponse) servletResponse);

        String qn = request.getParameter("_QN");
        try {
            //
//            String locale = LocaleDetector.instance.detect(request);

            //
            //System.out.println(requestInfo);
            if (log.isDebugEnabled()) {
                String requestInfo = "Serving " + request.getRequestURL().toString() + " @ " + new Date();
                log.trace(requestInfo);
            }
            if(qn != null ) {
            }
            chain.doFilter(servletRequest, servletResponse);
        }
        finally {
            ServletUtils.setRequest(null);
            ServletUtils.setResponse(null);
            if(qn != null) {
            }
        }

    }

    public void destroy() {
        // do nothing
    }
}
