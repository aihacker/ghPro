package pub.spring.web.mvc.controller;

import org.springframework.web.servlet.mvc.condition.ParamsRequestCondition;
import org.springframework.web.servlet.mvc.condition.PatternsRequestCondition;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;
import pub.functions.StrFuncs;

import java.lang.reflect.Method;

/**
 * Created by zzl on 2015/9/16.
 */
public class HandlerMapping extends RequestMappingHandlerMapping {

    private String basePackageName;

    public String getBasePackageName() {
        return basePackageName;
    }

    public void setBasePackageName(String basePackageName) {
        this.basePackageName = basePackageName;
    }

    @Override
    public void afterPropertiesSet() {
        super.afterPropertiesSet();
    }

    @Override
    protected RequestMappingInfo getMappingForMethod(Method method, Class<?> handlerType) {
        RequestMappingInfo info = super.getMappingForMethod(method, handlerType);
//        String name = info.getName();
//        if (name != null) {
//            return info;
//        }

        if(info == null) {
            return info;
        }
        if(!info.getPatternsCondition().getPatterns().isEmpty()) {
            return info;
        }

        String className = method.getDeclaringClass().getName();
        if(basePackageName == null) {
//            basePackageName =
            int nPos = className.indexOf(".web.");
            basePackageName = className.substring(0, nPos + 4);
        }
        assert className.startsWith(basePackageName); //
        String uri;
        uri = className.substring(basePackageName.length());
        int lastDotPos = uri.lastIndexOf('.');
        int lastDotPos2 = uri.lastIndexOf('.', lastDotPos - 1);
        if (!"action".equals(uri.substring(lastDotPos2 + 1, lastDotPos))) {
            throw new RuntimeException("bad controller package");
        }
        String path = uri.substring(0, lastDotPos2);
        //
        String actionName = uri.substring(lastDotPos + 1);
        if (!actionName.endsWith("Action")) {
            throw new RuntimeException("bad controller name");
        }
        actionName = actionName.substring(0, actionName.length() - 6);
        uri = path.replace('.', '/') + '/' + StrFuncs.unCamelize(actionName);
        if(uri.startsWith("/app/")) {
            uri = uri.substring(5);
        }

        //
        ParamsRequestCondition paramsRequestCondition = info.getParamsCondition();
        if(!method.getName().equals("execute")) {
            paramsRequestCondition = new ParamsRequestCondition("action=" + method.getName())
                    .combine(paramsRequestCondition);
        }
        RequestMappingInfo info1 = new RequestMappingInfo(
                uri + '#' + method.getName(),
                new PatternsRequestCondition(uri),
                info.getMethodsCondition(),
                paramsRequestCondition,
                info.getHeadersCondition(),
                info.getConsumesCondition(),
                info.getProducesCondition(),
                info.getCustomCondition()
        );

        return info1;
    }

}
