package pub.spring.web.mvc.model;

import org.springframework.util.ClassUtils;
import org.springframework.validation.support.BindingAwareModelMap;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: zzl
 * Date: Jun 24, 2009
 * Time: 1:12:46 AM
 */
@SuppressWarnings("unchecked")
public class Model extends BindingAwareModelMap {

    private static final long serialVersionUID = 1L;

    public <T> T get(Class<T> clazz) {
        String attributeName = ClassUtils.getShortNameAsProperty(clazz);
        return (T) super.get(attributeName);
    }

    @Override
    public Object get(Object key) {
        return super.get(key);
    }

    public <T> List<T> getList(Class<T> clazz) {
        String attributeName = ClassUtils.getShortNameAsProperty(clazz) + "List";
        return (List<T>) super.get(attributeName);
    }

    public Model put(Object attributeValue) {
        addAttribute(attributeValue);
        return this;
    }

    public void markAsFullResult() {
        this.put("_full_result", true);
    }

}
