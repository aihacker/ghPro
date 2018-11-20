package pub.spring.web.mvc.bind;

import org.springframework.ui.ModelMap;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import pub.functions.VarFuncs;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

/**
 * Created by zzl on 2015/9/18.
 */
public class XDataHandlerInterceptor extends HandlerInterceptorAdapter {

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response,
                           Object handler, ModelAndView modelAndView) throws Exception {
        HandlerMethod handlerMethod = (HandlerMethod) handler;
        Class<?> controllerClazz = handlerMethod.getBeanType();
        XData xData = controllerClazz.getAnnotation(XData.class);
        if(xData == null) {
            return;
        }
        String httpMethod = request.getMethod();
        if(httpMethod.equals("GET")) {
            storeXData(request, modelAndView, xData);
        }
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String httpMethod = request.getMethod();
        if(!httpMethod.equals("POST")) {
            return true;
        }
//        Object result = request.getAttribute(attributeName, RequestAttributes.SCOPE_REQUEST);
//        if (result != null) {
//            return result;
//        }
//        String storeAttributeName = getAttributeNameInSession(request, attributeName);
//        String xData = request.getParameter(storeAttributeName);
//        if (xData == null) {
//            return null;
//        }
//        Object attributeValue = VarFuncs.deserializeXData(xData);
//        request.setAttribute(attributeName, attributeValue, RequestAttributes.SCOPE_REQUEST);
//        return attributeValue;

        return true;
    }

    private void storeXData(HttpServletRequest request, ModelAndView modelAndView, XData xData) {
        List<String> xDataNames = Arrays.asList(xData.value());
        List<Class> xDataTypes = Arrays.asList(xData.types());

        Map<String, Object> xDataMap = new HashMap<>();
        ModelMap model = modelAndView.getModelMap();
        String[] attrNames = model.keySet().toArray(new String[model.size()]);
        for(String attrName: attrNames) {
            Object attrValue = model.get(attrName);
            if(xDataNames.contains(attrName) || xDataTypes.contains(attrValue.getClass())) {
                xDataMap.put(attrName, attrValue);
            }
        }
        if(!xDataMap.isEmpty()) {
            request.setAttribute("xDataMap", xDataMap);;
        }
    }
}
