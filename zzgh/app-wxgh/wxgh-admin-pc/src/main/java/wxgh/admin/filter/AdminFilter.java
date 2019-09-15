package wxgh.admin.filter;

import com.libs.common.type.TypeUtils;
import wxgh.admin.session.AdminSession;
import wxgh.admin.session.bean.SeAdmin;
import wxgh.app.sys.variate.GlobalValue;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by Administrator on 2017/9/11.
 */
public class AdminFilter implements Filter {

    public static final String LOGIN = "/admin/login.html";

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        SeAdmin admin = AdminSession.getAdmin();
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        String url = request.getServletPath();
        if (admin != null || url.contains("login.html")) {
            filterChain.doFilter(servletRequest, servletResponse);
        } else {
            String param = request.getQueryString();
            if (!TypeUtils.empty(param)) {
                url += "?" + param;
            }
            response.sendRedirect("/" + GlobalValue.appName + LOGIN + "?url=" + url);
        }
    }

    @Override
    public void destroy() {

    }
}
