package pub.spring.web.mvc.controller;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.ui.ExtendedModelMap;
import org.springframework.util.ClassUtils;
import org.springframework.validation.support.BindingAwareModelMap;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.servlet.HandlerAdapter;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.support.WebContentGenerator;
import pub.spring.web.mvc.controller.support.MethodInvokerContext;
import pub.spring.web.mvc.controller.support.ServletHandlerMethodInvoker;
import pub.spring.web.mvc.controller.support.ServletHandlerMethodResolver;
import pub.spring.web.mvc.model.Model;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * describe: Created by IntelliJ IDEA.
 * @author zzl
 * @version 13-5-5
 */
public class SimpleHandlerAdapter extends WebContentGenerator
        implements HandlerAdapter, Ordered {

    private int cacheSecondsForSessionAttributeHandlers = 0;

    private int order = Ordered.LOWEST_PRECEDENCE;

    private final Map<Class<?>, ServletHandlerMethodResolver> methodResolverCache =
            new ConcurrentHashMap<Class<?>, ServletHandlerMethodResolver>();

    private final Map<Class<?>, Boolean> sessionAnnotatedClassesCache = new ConcurrentHashMap<Class<?>, Boolean>();

    private MethodInvokerContext invokerContext;

    public SimpleHandlerAdapter() {
        // no restriction of HTTP methods by default
        super(false);

    }

    public MethodInvokerContext getInvokerContext() {
        return invokerContext;
    }

    public void setInvokerContext(MethodInvokerContext invokerContext) {
        this.invokerContext = invokerContext;
    }

    public void setCacheSecondsForSessionAttributeHandlers(int cacheSecondsForSessionAttributeHandlers) {
        this.cacheSecondsForSessionAttributeHandlers = cacheSecondsForSessionAttributeHandlers;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    public int getOrder() {
        return this.order;
    }

    public boolean supports(Object handler) {
        return true;
    }

    public ModelAndView handle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {

        Class<?> clazz = ClassUtils.getUserClass(handler);
        Boolean annotatedWithSessionAttributes = this.sessionAnnotatedClassesCache.get(clazz);
        if (annotatedWithSessionAttributes == null) {
            annotatedWithSessionAttributes = (AnnotationUtils.findAnnotation(clazz, SessionAttributes.class) != null);
            this.sessionAnnotatedClassesCache.put(clazz, annotatedWithSessionAttributes);
        }

        if (annotatedWithSessionAttributes) {
            // Always prevent caching in case of session attribute management.
            checkAndPrepare(request, response, this.cacheSecondsForSessionAttributeHandlers, true);
            // Prepare cached set of session attributes names.
        }
        else {
            // Uses configured default cacheSeconds setting.
            checkAndPrepare(request, response, true);
        }

        return invokeHandlerMethod(request, response, handler);
    }

    protected ModelAndView invokeHandlerMethod(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {

        ServletHandlerMethodResolver methodResolver = getMethodResolver(handler);
        Method handlerMethod = methodResolver.resolveHandlerMethod(request);
        ServletHandlerMethodInvoker methodInvoker = new ServletHandlerMethodInvoker(
                methodResolver, invokerContext);
        ServletWebRequest webRequest = new ServletWebRequest(request, response);
        Model implicitModel = new Model();

        methodResolver.prepareModel(request, implicitModel);

        Object result = methodInvoker.invokeHandlerMethod(handlerMethod, handler, webRequest, implicitModel);
        ModelAndView mav =
                methodInvoker.getModelAndView(handlerMethod, handler.getClass(), result, implicitModel, webRequest);

        //
        Map<String, Object> mavModel = mav != null ? mav.getModel() : null;
        methodInvoker.updateModelAttributes(handler, mavModel, implicitModel, webRequest);

        methodResolver.updateModelAttributes(handler, mavModel, implicitModel, request);

        return mav;
    }

    public long getLastModified(HttpServletRequest request, Object handler) {
        return -1;
    }

    private ServletHandlerMethodResolver getMethodResolver(Object handler) {
        Class handlerClass = ClassUtils.getUserClass(handler);
        ServletHandlerMethodResolver resolver = this.methodResolverCache.get(handlerClass);
        if (resolver == null) {
            synchronized (this.methodResolverCache) {
                resolver = this.methodResolverCache.get(handlerClass);
                if (resolver == null) {
                    resolver = new ServletHandlerMethodResolver(handlerClass, invokerContext);
                    this.methodResolverCache.put(handlerClass, resolver);
                }
            }
        }
        return resolver;
    }

}
