package pub.spring.web.mvc.controller.support;

import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.ui.ExtendedModelMap;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.support.HandlerMethodResolver;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.servlet.HandlerMapping;
import org.springframework.web.servlet.mvc.multiaction.NoSuchRequestHandlingMethodException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.*;

/**
 * describe: Created by IntelliJ IDEA.
 * @author zzl
 * @version 13-5-5
 */
public class ServletHandlerMethodResolver extends HandlerMethodResolver {

    private MethodInvokerContext context;
    private Set<Method> knownMethods = new HashSet<>();

    private PageDataHandler pageDataHandler = new PageDataHandler();

    public ServletHandlerMethodResolver(Class<?> handlerType, MethodInvokerContext context) {
        this.context = context;
        init(handlerType);

        pageDataHandler.init(handlerType);
    }

    @Override
    protected boolean isHandlerMethod(Method method) {
        if (knownMethods.contains(method)) {
            return true;
        }
        RequestMapping mapping = AnnotationUtils.findAnnotation(method, RequestMapping.class);
        if (mapping != null) {
            knownMethods.add(method);
            return true;
        }
        return false;
    }

    public Method resolveHandlerMethod(HttpServletRequest request) throws ServletException {

        String methodName = (String) request.getAttribute("_action");
        if(methodName == null) {
             methodName = request.getParameter("action");
        }
        if (methodName == null) {
            methodName = "execute";
        }
        for (Method method : knownMethods) {
            if (method.getName().equals(methodName)) {
                return method;
            }
        }
        throw new NoSuchRequestHandlingMethodException(request);
    }

    //
    public void prepareModel(HttpServletRequest request, ExtendedModelMap implicitModel) {

        pageDataHandler.prepareModel(request, implicitModel);
    }

    public void updateModelAttributes(Object handler, Map<String, Object> mavModel,
            ExtendedModelMap implicitModel, HttpServletRequest request) {

        pageDataHandler.updateModelAttributes(handler, mavModel, implicitModel, request);
    }
}