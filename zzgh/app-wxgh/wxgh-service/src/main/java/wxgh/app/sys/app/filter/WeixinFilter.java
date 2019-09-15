package wxgh.app.sys.app.filter;

import com.libs.common.cache.AgentCache;
import com.libs.common.type.TypeUtils;
import com.weixin.api.OAuthApi;
import pub.web.ServletUtils;
import wxgh.app.session.bean.SeUser;
import wxgh.app.session.user.ParamSession;
import wxgh.app.session.user.UserSession;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by Administrator on 2017/7/13.
 */
public class WeixinFilter implements Filter {

    public static final String AGENTID = "agentid";

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        SeUser user = UserSession.getUser();
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        String agentId = request.getParameter(AGENTID);
        if (!TypeUtils.empty(agentId)) {
            AgentCache.set(Integer.valueOf(agentId));
        }

        if (user == null) {
            //todo 非微信浏览器，自动登录
            String agent = request.getHeader("user-agent");
            if (TypeUtils.empty(agent) || agent.toLowerCase().indexOf("micromessenger") < 0) {
                user = new SeUser();
                user.setAvatar("http://shp.qpic.cn/bizmp/pjoYDdnZ6yQmmJ6h3XEicwG0Pjib78p5sN08CnOcuX1aPJ7PSHh4cb5A/");
                user.setDeptid(2);
                user.setGender(1);
                user.setMobile("18402028708");
                user.setName("徐朗凯");
                user.setUserid("XuLangKai");
                UserSession.setUser(user);
                filterChain.doFilter(servletRequest, servletResponse);
                return;
            }

            String backUrl = ServletUtils.getHostAddr() + "/app/weixin/login.html";
            String requestUrl = request.getServletPath();

            String requestQuery = request.getQueryString();
            if (!TypeUtils.empty(requestQuery)) {
                requestQuery = requestQuery.replaceAll("[?|&]?" + AGENTID + "=\\d+[?|&]?", "");
                ParamSession.put(requestQuery);
            }
            backUrl += "?redirect=" + requestUrl;

            response.sendRedirect(OAuthApi.getOAuthUrl(backUrl));
        } else {
            filterChain.doFilter(servletRequest, servletResponse);
        }
    }

    @Override
    public void destroy() {

    }
}
