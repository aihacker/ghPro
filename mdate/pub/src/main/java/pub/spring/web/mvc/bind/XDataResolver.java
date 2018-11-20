package pub.spring.web.mvc.bind;

import org.springframework.core.MethodParameter;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;
import pub.functions.VarFuncs;
import pub.web.ServletUtils;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * Created by zzl on 2015/9/18.
 */
public class XDataResolver implements HandlerMethodArgumentResolver {

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        HttpServletRequest request = ServletUtils.getRequest();
        if(!request.getMethod().equals("POST")) {
            return false;
        }
        String name = parameter.getParameterName();
        if(request.getParameter(name) == null) {
            return false;
        }
        Class type = parameter.getParameterType();
        XData xData = parameter.getMethod().getDeclaringClass().getAnnotation(XData.class);
        if(xData == null) {
            return false;
        }
        List<String> xDataNames = Arrays.asList(xData.value());
        List<Class> xDataTypes = Arrays.asList(xData.types());
        if(xDataNames.contains(name) || xDataTypes.contains(type)) {
            return true;
        }
        return false;
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
                                  NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        String name = parameter.getParameterName();
        Class type = parameter.getParameterType();

        String xData = webRequest.getParameter(name);
        if(xData.length() == 0) {
            System.out.println("wtf?");
            return null;
        }
        Object value = VarFuncs.deserializeXData(xData);

        WebDataBinder binder = binderFactory.createBinder(webRequest, value, name);
        if (binder.getTarget() != null) {
            ServletRequest servletRequest = webRequest.getNativeRequest(ServletRequest.class);
            ServletRequestDataBinder servletBinder = (ServletRequestDataBinder) binder;
            servletBinder.bind(servletRequest);
        }

        // Add resolved attribute and BindingResult at the end of the model
        Map<String, Object> bindingResultModel = binder.getBindingResult().getModel();
        mavContainer.removeAttributes(bindingResultModel);
        mavContainer.addAllAttributes(bindingResultModel);

        return value;
    }
}
