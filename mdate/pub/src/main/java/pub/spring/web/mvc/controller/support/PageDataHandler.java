package pub.spring.web.mvc.controller.support;

import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.ui.ExtendedModelMap;
import org.springframework.web.context.request.ServletWebRequest;
import pub.spring.web.mvc.bind.PageData;
import pub.web.PageDataStore;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

/**
 * describe: Created by IntelliJ IDEA.
 * @author zzl
 * @version 13-5-5
 */
public class PageDataHandler {

    private final Set<String> pageDataNames = new HashSet<String>();

    public void init(Class<?> handlerType) {
        PageData pageData = AnnotationUtils.findAnnotation(handlerType, PageData.class);
        if(pageData == null) {
            return;
        }
        this.pageDataNames.addAll(Arrays.asList(pageData.value()));
    }

    public void prepareModel(HttpServletRequest request, ExtendedModelMap implicitModel) {
        for(String name: pageDataNames) {
            Object obj = PageDataStore.load(request, name);
            if(obj != null) {
                implicitModel.put(name, obj);
            }
        }
    }


    public void updateModelAttributes(Object handler, Map<String, Object> mavModel,
            ExtendedModelMap implicitModel, HttpServletRequest request) {

        for(String name: pageDataNames) {
            PageDataStore.save(request, name, implicitModel.get(name));
        }

    }
}
