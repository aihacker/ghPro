package pub.functions;

import org.apache.commons.beanutils.BeanUtilsBean;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang.builder.EqualsBuilder;

import java.beans.PropertyDescriptor;
import java.io.*;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * describe: Created by IntelliJ IDEA.
 * @author zzl
 * @version 2008-8-21
 */
@SuppressWarnings("unchecked")
public class BeanFuncs {

    @SuppressWarnings({"UnnecessaryLocalVariable", "unchecked"})
    public static <T> T clone(T obj) {
        try {
            T result = (T) BeanUtilsBean.getInstance().cloneBean(obj);
            return result;
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static <T> T deepClone(T t) {
        assert t instanceof Serializable : t.getClass().getName() + " not serializable!";
        try {
            ByteArrayOutputStream obuf = new ByteArrayOutputStream();
            ObjectOutputStream os = new ObjectOutputStream(obuf);
            os.writeObject(t);

            ByteArrayInputStream ibuf = new ByteArrayInputStream(obuf.toByteArray());
            ObjectInputStream is = new ObjectInputStream(ibuf);
            T t1 = (T) is.readObject();
            return t1;
        }
        catch (Exception e) {
            assert false : "this should not happen";
            return null;
        }
    }

    public static void copyProperties(Object dest, Object orig, boolean ignoreException) {
        try {
            //BeanUtils.copyProperties(dest, orig);
            PropertyUtils.copyProperties(dest, orig);
        }
        catch (Exception e) {
            if (!ignoreException) {
                throw new RuntimeException(e);
            }
        }
    }

    @SuppressWarnings("unchecked")
    public static Object dynamize(Object object) throws Exception {
        if (object == null) {
            return null;    //#
        }
        Class type = object.getClass();
        if (type.isEnum()) {
            return object.toString();
        }
        if (type.isPrimitive()) {
            return object;    //#
        }
        if (TypeFuncs.isList(type)) {
            List result = new ArrayList();
            for (Object obj : (List) object) {
                result.add(dynamize(obj));
            }
            return result;    //#
        }
        if (TypeFuncs.isJavaType(type)) {
            return object;    //#
        }
        //
        Map result = new HashMap();
        PropertyDescriptor[] propDescs = PropertyUtils.getPropertyDescriptors(object);
        for (PropertyDescriptor propDesc : propDescs) {
            String propName = propDesc.getName();
            if (propName.equals("class") || propName.equals("declaringClass")) {
                continue;
            }
            Object propValue = propDesc.getReadMethod().invoke(object);
            result.put(propName, dynamize(propValue));
        }
        return result;    //#
    }

    public static boolean equals(Object o1, Object o2) {
        return EqualsBuilder.reflectionEquals(o1, o2);
    }

    public static <T> T newInstance(Class<T> clazz) {
        try {
            return clazz.newInstance();
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static void setEmptyFieldsAsNull(Object bean) {
        try {
            PropertyDescriptor[] propDescs = PropertyUtils.getPropertyDescriptors(bean);
            for (PropertyDescriptor propDesc : propDescs) {
                Object value = propDesc.getReadMethod().invoke(bean);
                if ("".equals(value)) {
                    propDesc.getWriteMethod().invoke(bean, new Object[]{null});
                }
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void removeDiffProperties(Object dest, Object differ, Class baseClass) {
        assert dest != null && differ != null;
        try {
            PropertyDescriptor[] propDescs = PropertyUtils.getPropertyDescriptors(baseClass);
            for (PropertyDescriptor propDesc : propDescs) {
                if (propDesc.getName().equals("class")) {
                    continue;
                }
                Object destValue = propDesc.getReadMethod().invoke(dest);
                if (destValue == null) { // shortcut
                    continue;
                }
                Object differValue = propDesc.getReadMethod().invoke(differ);
                if (!destValue.equals(differValue)) {
                    propDesc.getWriteMethod().invoke(dest, new Object[]{null});
                }
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void mergeNotNullProperties(Object dest, Object merger, Class baseClass) {
        try {
            PropertyDescriptor[] propDescs = PropertyUtils.getPropertyDescriptors(baseClass);
            for (PropertyDescriptor propDesc : propDescs) {
                if (propDesc.getName().equals("class")) {
                    continue;
                }
                Object mergerValue = propDesc.getReadMethod().invoke(merger);
                if (mergerValue != null) {
                    propDesc.getWriteMethod().invoke(dest, mergerValue);
                }
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void mergeDiffProperties(Object dest, Object merger, Object differ, Class baseClass) {
        try {
            PropertyDescriptor[] propDescs = PropertyUtils.getPropertyDescriptors(baseClass);
            for (PropertyDescriptor propDesc : propDescs) {
                if (propDesc.getName().equals("class")) {
                    continue;
                }
                Object mergerValue = propDesc.getReadMethod().invoke(merger);
                Object differValue = propDesc.getReadMethod().invoke(differ);
                if (mergerValue == null && differValue != null ||
                        mergerValue != null && !mergerValue.equals(differValue)) {
                    propDesc.getWriteMethod().invoke(dest, mergerValue);
                }
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Object readField(Object bean, String fieldName) {
        try {
            Field fld = bean.getClass().getDeclaredField(fieldName);
            fld.setAccessible(true);
            return fld.get(bean);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
}
