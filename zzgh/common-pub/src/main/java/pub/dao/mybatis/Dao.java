package pub.dao.mybatis;

import java.io.Serializable;
import java.util.List;

/**
 * describe: Created by IntelliJ IDEA.
 *
 * @author zzl
 * @version Jun 23, 2009
 */
public interface Dao<T> {

    T get(Serializable id);

    void update(T t, String key);

    List<T> getAll();

    List<T> getAll(String orderBy);

    Serializable save(T t);

    void delete(T t);

    void delete(Serializable id);

    void delete(Serializable[] ids);

}
