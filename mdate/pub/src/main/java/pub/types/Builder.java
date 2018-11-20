package pub.types;

import org.apache.ibatis.session.SqlSessionFactoryBuilder;

/**
 * describe: Created by IntelliJ IDEA.
 * @author zzl
 * @version 12-5-26
 */
public interface Builder<T> {

    T build();

}
