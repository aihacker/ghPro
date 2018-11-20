package pub.spring.bean;

import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.support.WebApplicationContextUtils;
import org.springframework.web.servlet.FrameworkServlet;
import pub.web.ServletUtils;

import javax.servlet.ServletContext;

/**
 * describe: Created by IntelliJ IDEA.
 * @author zzl
 * @version 2010-2-15
 */
public class BeanUtils {

    public static ApplicationContext _ac = null;
    private static ThreadLocal<ApplicationContext> _tlAc = new ThreadLocal<ApplicationContext>();

    public static void setThreadApplicationContext(ApplicationContext wac) {
        _tlAc.set(wac);
    }

    private static ApplicationContext getThreadApplicationContext() {
        return _tlAc.get();
    }

    public static <T> T getBean(Class<T> clazz) {
        ApplicationContext ac = getApplicationContext();
        try {
            return ac.getBean(clazz);
        }
        catch (NoSuchBeanDefinitionException e) {
            return null;
        }
    }

    public static ApplicationContext getApplicationContext() {
        ApplicationContext ac = _ac;
        if (ac == null) {
            ac = getThreadApplicationContext();
        }
        if (ac == null) {
            ac = ContextLoader.getCurrentWebApplicationContext();
        }
        if(ac == null) {
            ServletContext sc = ServletUtils.getRequest().getServletContext();
            ac = WebApplicationContextUtils.getWebApplicationContext(sc);

            if(ac == null) {
                String attrName = FrameworkServlet.SERVLET_CONTEXT_PREFIX + "dispatcher";
                ac = WebApplicationContextUtils.getWebApplicationContext(sc, attrName);
            }
        }
        return ac;
    }

    public static Object getBean(String name) {
        ApplicationContext ac = getApplicationContext();
        try {
            return ac.getBean(name);
        }
        catch (NoSuchBeanDefinitionException e) {
            return null;
        }
    }
}
