package pub.web.filter;

import pub.web.ServletUtils;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by Administrator on 2017/7/11.
 */
public class RequestDataFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        try {
            HttpServletRequest request = (HttpServletRequest) servletRequest;

            ServletUtils.setRequest(request);
            ServletUtils.setResponse((HttpServletResponse) servletResponse);

            filterChain.doFilter(servletRequest, servletResponse);
        } finally {
            ServletUtils.setRequest(null);
            ServletUtils.setResponse(null);
        }
    }

    @Override
    public void destroy() {

    }
}
