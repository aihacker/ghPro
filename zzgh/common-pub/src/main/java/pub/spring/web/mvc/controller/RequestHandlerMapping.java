package pub.spring.web.mvc.controller;

import com.libs.common.type.StringUtils;
import com.libs.common.type.TypeUtils;
import org.springframework.web.servlet.mvc.condition.PatternsRequestCondition;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by XLKAI on 2017/5/24.
 */
public class RequestHandlerMapping extends RequestMappingHandlerMapping {

    private String defaultController;
    private String defaultControllerName;
    private String defaultMethod;

    public void setDefaultController(String defaultController) {
        this.defaultController = defaultController;
    }

    public void setDefaultControllerName(String defaultControllerName) {
        this.defaultControllerName = defaultControllerName;
    }

    public void setDefaultMethod(String defaultMethod) {
        this.defaultMethod = defaultMethod;
    }

    @Override
    protected RequestMappingInfo getMappingForMethod(Method method, Class<?> handlerType) {
        RequestMappingInfo info = super.getMappingForMethod(method, handlerType);
        if (info == null || !info.getPatternsCondition().getPatterns().isEmpty()) {
            return info;
        }

        String className = method.getDeclaringClass().getName();

        //根据class全名，获取url
        if (className.indexOf(".web.") < 0) {
            throw new RuntimeException("bad action name package：" + className);
        }
        int firstIndex = className.indexOf(".") + 1;
        String url = className.substring(firstIndex, className.lastIndexOf("."));

        if (TypeUtils.empty(defaultController)) {
            defaultController = "main";
        } else {
            defaultController = StringUtils.camelToUnderline(defaultController);
        }
        if (TypeUtils.empty(defaultControllerName)) {
            defaultControllerName = "Controller";
        }
        if (TypeUtils.empty(defaultMethod)) {
            defaultMethod = "execute";
        }

        //获取控制器名称
        String controllerName = method.getDeclaringClass().getSimpleName();
        controllerName = StringUtils.camelToUnderline(controllerName.replace(defaultControllerName, ""));

        //获取方法名称
        String methodName = StringUtils.camelToUnderline(method.getName());

        //判断是否为默认模块
        url = url.replace(".web.", "/")
                .replace(".web", "/")
                .replace(".", "/");

        StringBuilder urlBuilder = new StringBuilder(url);
        if (!defaultController.equals(controllerName)) {
            urlBuilder.append("/" + controllerName);
        }

        List<String> urls = new ArrayList<>();
        //第一个地址
        if (defaultMethod.equals(methodName)) {
            urls.add(urlBuilder.toString());
        }

        //第二个地址
        urlBuilder.append("/" + methodName);
        urls.add(urlBuilder.toString());

        String[] patterns = new String[urls.size()];
        RequestMappingInfo mapInfo = new RequestMappingInfo(url.toString(),
                new PatternsRequestCondition(urls.toArray(patterns), null, null, true, true),
                info.getMethodsCondition(),
                info.getParamsCondition(),
                info.getHeadersCondition(),
                info.getConsumesCondition(),
                info.getProducesCondition(),
                info.getCustomCondition());
        return mapInfo;
    }
}
