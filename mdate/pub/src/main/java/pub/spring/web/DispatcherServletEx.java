package pub.spring.web;

import org.springframework.web.servlet.DispatcherServlet;
import pub.spring.bean.BeanUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * describe: Created by IntelliJ IDEA.
 * @author zzl
 * @version 12-4-11
 */
public class DispatcherServletEx extends DispatcherServlet {

    @Override
    protected void doDispatch(HttpServletRequest request, HttpServletResponse response) throws Exception {
        BeanUtils.setThreadApplicationContext(getWebApplicationContext());
        try {
            super.doDispatch(request, response);
        }
        finally {
            BeanUtils.setThreadApplicationContext(null);
        }
    }
}
