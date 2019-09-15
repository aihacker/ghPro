package pub.dao.mybatis;

import com.libs.common.type.StringUtils;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.SqlSessionUtils;
import org.mybatis.spring.support.SqlSessionDaoSupport;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ResolvableType;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * describe: Created by IntelliJ IDEA.
 *
 * @author zzl
 * @version 12-2-9
 */
@SuppressWarnings("unchecked")
public class MyBatisDao<T> extends SqlSessionDaoSupport implements Dao<T> {

    public static Map<String, ValueGenerator> generators = new HashMap<>();

    public static ValueGenerator defaultValueGenerator;

    private final Class<T> entityClass;
    private PropertyDescriptor idProperty;

    public MyBatisDao() {
//        this.entityClass = entityClass;
        this.entityClass = (Class<T>) ResolvableType.forType(getClass().getGenericSuperclass()).getGeneric(0).resolve();
    }

    @Override
    public T get(Serializable id) {
        return selectOne("get", id);
    }

    @Override
    public void update(T t, String key) {
        Map<String, Object> map = beanToMap(t, key);
        execute("update2", map);

    }

    private Map<String, Object> beanToMap(Object obj, String param) {
        Map<String, Object> map = new HashMap<>();
        try {
            BeanInfo beanInfo = Introspector.getBeanInfo(obj.getClass());

            PropertyDescriptor[] ps = beanInfo.getPropertyDescriptors();
            for (PropertyDescriptor p : ps) {
                String key = p.getName();
                if (!key.equals("class")) {
                    // 得到property对应的getter方法
                    Method getter = p.getReadMethod();
                    Object value = getter.invoke(obj);
                    if (key.equals(param)) {
                        Column column = getter.getAnnotation(Column.class);
                        String colName = column.name();
                        if (colName == null || colName.length() <= 0) {
                            colName = StringUtils.camelToUnderline(key);
                        }
                        map.put("whereName", colName);
                        map.put("whereValue", value);
                    } else {
                        map.put(key, value);
                    }
                }
            }
        } catch (IntrospectionException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        return map;
    }

    @Override
    public List<T> getAll() {
        return selectList("getAll");
    }

    @Override
    public List<T> getAll(String orderBy) {
        return selectList("getAll", orderBy);
    }

    @Override
    public Serializable save(T t) {
        Serializable idValue = getId(t);
        if (idValue == null) {
            Method idGetter = idProperty.getReadMethod();
            GeneratedValue generatedValueAnn = idGetter.getAnnotation(GeneratedValue.class);
            ValueGenerator valueGenerator = getValueGenerator(generatedValueAnn);
            if (valueGenerator instanceof PreGenerator) {
                idValue = generateId(valueGenerator);
                setId(t, idValue);
            }
            getSqlSession().insert(fullQualifySqlId("insert"), t);
            if (idValue == null && valueGenerator instanceof PostGenerator) {
                idValue = postGenerateId(valueGenerator);
                setId(t, idValue);
            }
        } else {
            getSqlSession().update(fullQualifySqlId("update"), t);
        }
        return idValue;
    }

    //*
    private Serializable generateId(ValueGenerator generator) {
        SqlSessionFactory sqlSessionFactory = ((SqlSessionTemplate) getSqlSession()).getSqlSessionFactory();
        SqlSession tempSession = SqlSessionUtils.getSqlSession(sqlSessionFactory);
        try {
            Connection connection = tempSession.getConnection();
            Serializable id = (Serializable) generator.generate(connection, idProperty);
            return id;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        } finally {
            SqlSessionUtils.closeSqlSession(tempSession, sqlSessionFactory);
        }
    }

    private Serializable postGenerateId(ValueGenerator generator) {
        try {
            Connection connection = getSqlSession().getConnection();
            Serializable id = (Serializable) generator.generate(connection, idProperty);
            return id;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private ValueGenerator getValueGenerator(GeneratedValue generatedValueAnn) {
        if (generatedValueAnn == null) {
            return null;
        }
        String generatorExpr = generatedValueAnn.generator();
        ValueGenerator generator;
        if (generatorExpr != null && generatorExpr.length() > 0) {
            try {
                Object generatorObj = generators.get(generatorExpr);
                if (generatorObj == null) {
                    generatorObj = Class.forName(generatorExpr).newInstance();
                }
                generator = (ValueGenerator) generatorObj;
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        } else {
            generator = defaultValueGenerator;
        }
        return generator;
    }
    //*/

    private Serializable getId(T t) {
        if (idProperty == null) {
            idProperty = detectIdProperty();
        }
        try {
            return (Serializable) idProperty.getReadMethod().invoke(t);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private Serializable setId(T t, Serializable idValue) {
        if (idProperty == null) {
            idProperty = detectIdProperty();
        }
        try {
            return (Serializable) idProperty.getWriteMethod().invoke(t, idValue);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private PropertyDescriptor detectIdProperty() {
        PropertyDescriptor[] propDescs = BeanUtils.getPropertyDescriptors(entityClass);
        for (PropertyDescriptor propDesc : propDescs) {
            Method method = propDesc.getReadMethod();
            Id idAnn = method.getAnnotation(Id.class);
            if (idAnn != null) {
                return propDesc;
            }
        }
        throw new RuntimeException("id property not found on " + entityClass.getName());
    }

    @Override
    public void delete(T t) {
        Serializable id = getId(t);
        if (id != null) {
            delete(id);
        }
    }

    @Override
    public void delete(Serializable id) {
        delete(new Serializable[]{id});
    }

    @Override
    public void delete(Serializable[] ids) {
        if (ids.length == 0) {
            return;
        }
        getSqlSession().delete(fullQualifySqlId("delete"), Arrays.asList(ids));
    }

    protected int execute(String sqlId, Object... params) {
        Object param = toParam(params);
        sqlId = fullQualifySqlId(sqlId);
        int result = getSqlSession().update(sqlId, param);
        return result;
    }

    protected <T1> T1 selectOne(String sqlId, Object... params) {
        Object param = toParam(params);
        sqlId = fullQualifySqlId(sqlId);
        Object result = getSqlSession().selectOne(sqlId, param);
        return (T1) result;
    }

    private Object toParam(Object[] params) {
        Object param;
        if (params.length == 0) {
            param = null;
        } else if (params.length == 1) {
            param = params[0];
        } else {
            Map<String, Object> map = new HashMap<String, Object>();
            for (int n = 0; n < params.length; n++) {
                map.put("p" + n, params[n]);
            }
            param = map;
        }
        return param;
    }

    protected Integer selectInt(String sqlId, Object... params) {
        Integer count = selectOne(sqlId, params);
        return count == null ? 0 : count;
    }

    protected <T1> List<T1> selectList(String sqlId, Object... params) {
        Object param = toParam(params);
        sqlId = fullQualifySqlId(sqlId);
        List result = getSqlSession().selectList(sqlId, param);
        return result;
    }

    protected String fullQualifySqlId(String sqlId) {
        if (sqlId.indexOf('.') != -1) {
            return sqlId;
        }
        return entityClass.getName() + '.' + sqlId;
    }

    @Autowired
    public void setSqlSessionTemplate(SqlSessionTemplate sqlSessionTemplate) {
        super.setSqlSessionTemplate(sqlSessionTemplate);
    }

}
