package pub.spring.web.mvc.controller;

import org.springframework.context.ApplicationContext;
import org.springframework.web.servlet.handler.AbstractHandlerMapping;
import pub.functions.StrFuncs;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * describe: Created by IntelliJ IDEA.
 * @author zzl
 * @version 13-5-5
 */
public class SimpleHandlerMapping extends AbstractHandlerMapping {

    private String basePackageName;
    private final Map<String, Object> uriToHandlerMap = new ConcurrentHashMap<String, Object>();

    public String getBasePackageName() {
        return basePackageName;
    }

    public void setBasePackageName(String basePackageName) {
        this.basePackageName = basePackageName;
    }

    @Override
    protected Object getHandlerInternal(HttpServletRequest request) throws Exception {
        String uri = request.getServletPath();
        String pathInfo = request.getPathInfo();
        if (pathInfo != null) {
            uri += pathInfo;
        }
        int lastDotPos = uri.lastIndexOf('.');
        uri = uri.substring(0, lastDotPos); //remove extension

        Object controller = uriToHandlerMap.get(uri);
        if (controller != null) {
            return controller;
        }

        ApplicationContext ac = getApplicationContext();
        if (ac.containsBean(uri)) { // uri as bean name
            controller = ac.getBean(uri);
            uriToHandlerMap.put(uri, controller);
            return controller;
        }

        StringBuilder sb = new StringBuilder(basePackageName);
        sb.append(".web");

        uri = uri.replace('/', '.');
        lastDotPos = uri.lastIndexOf('.');

        String path = uri.substring(0, lastDotPos);
        if(path.isEmpty()) {
            path = ".app";
        }
        sb.append(path).append(".action.");
        sb.append(StrFuncs.camelize(uri.substring(lastDotPos + 1), true));
        sb.append("Action");
        String className = sb.toString();

        controller = ac.getBean(className);
        uriToHandlerMap.put(uri, controller);
        return controller;
    }

}
