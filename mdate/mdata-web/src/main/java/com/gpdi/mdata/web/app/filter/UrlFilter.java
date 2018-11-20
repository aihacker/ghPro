package com.gpdi.mdata.web.app.filter;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * Created by zzl on 2015/7/4.
 */
public class UrlFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse,
                         FilterChain chain) throws IOException, ServletException {

        HttpServletRequest request = (HttpServletRequest) servletRequest;

        String uri = request.getRequestURI();
        if (uri.charAt(uri.length() - 1) == '/') {
            uri = uri.substring(request.getContextPath().length()) + "index.html";
            request.getRequestDispatcher(uri).forward(request, servletResponse);
            return;
        }
        chain.doFilter(request, servletResponse);

    }

    @Override
    public void destroy() {

    }
}
