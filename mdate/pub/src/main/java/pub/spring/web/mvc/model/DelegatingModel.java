package pub.spring.web.mvc.model;

import org.springframework.ui.ExtendedModelMap;
import org.springframework.ui.ModelMap;
import org.springframework.util.ClassUtils;
import org.springframework.validation.support.BindingAwareModelMap;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.function.BiFunction;
import java.util.function.Function;

/**
 * Created by zzl on 2015/9/17.
 */
public class DelegatingModel extends Model {

    private static final long serialVersionUID = 1L;

    private ModelMap map;

    public DelegatingModel(ModelMap map) {
        this.map = map;
    }

    public <T> T get(Class<T> clazz) {
        String attributeName = ClassUtils.getShortNameAsProperty(clazz);
        return (T) map.get(attributeName);
    }

    public <T> List<T> getList(Class<T> clazz) {
        String attributeName = ClassUtils.getShortNameAsProperty(clazz) + "List";
        return (List<T>) map.get(attributeName);
    }

    public Model put(Object attributeValue) {
        map.addAttribute(attributeValue);
        return this;
    }

    public void markAsFullResult() {
        map.put("_full_result", true);
    }

    //

    @Override
    public Object put(String key, Object value) {
        return map.put(key, value);
    }

    @Override
    public void putAll(Map<? extends String, ?> map) {
        this.map.putAll(map);
    }

    //
    @Override
    public DelegatingModel addAttribute(String attributeName, Object attributeValue) {
        map.addAttribute(attributeName, attributeValue);
        return this;
    }

    @Override
    public DelegatingModel addAttribute(Object attributeValue) {
        map.addAttribute(attributeValue);
        return this;
    }

    @Override
    public DelegatingModel addAllAttributes(Collection<?> attributeValues) {
        map.addAllAttributes(attributeValues);
        return this;
    }

    @Override
    public DelegatingModel addAllAttributes(Map<String, ?> attributes) {
        map.addAllAttributes(attributes);
        return this;
    }

    @Override
    public DelegatingModel mergeAttributes(Map<String, ?> attributes) {
        map.mergeAttributes(attributes);
        return this;
    }

    @Override
    public Map<String, Object> asMap() {
        return map;
    }

    //
    public boolean containsAttribute(String attributeName) {
        return map.containsAttribute(attributeName);
    }
    
    //

    @Override
    public int size() {
        return map.size();
    }

    @Override
    public boolean isEmpty() {
        return map.isEmpty();
    }

    @Override
    public boolean containsKey(Object key) {
        return map.containsKey(key);
    }

    @Override
    public Object remove(Object key) {
        return map.remove(key);
    }

    @Override
    public Object putIfAbsent(String key, Object value) {
        return map.putIfAbsent(key, value);
    }

    @Override
    public boolean remove(Object key, Object value) {
        return map.remove(key, value);
    }

    @Override
    public boolean replace(String key, Object oldValue, Object newValue) {
        return map.replace(key, oldValue, newValue);
    }

    @Override
    public Object replace(String key, Object value) {
        return map.replace(key, value);
    }

    @Override
    public Object computeIfAbsent(String key, Function<? super String, ?> mappingFunction) {
        return map.computeIfAbsent(key, mappingFunction);
    }

    @Override
    public Object computeIfPresent(String key, BiFunction<? super String, ? super Object, ?> remappingFunction) {
        return map.computeIfPresent(key, remappingFunction);
    }

    @Override
    public Object compute(String key, BiFunction<? super String, ? super Object, ?> remappingFunction) {
        return map.compute(key, remappingFunction);
    }

    @Override
    public Object merge(String key, Object value, BiFunction<? super Object, ? super Object, ?> remappingFunction) {
        return map.merge(key, value, remappingFunction);
    }

    @Override
    public Object clone() {
        return map.clone();
    }
}
