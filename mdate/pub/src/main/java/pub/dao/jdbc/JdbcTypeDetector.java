package pub.dao.jdbc;

import java.math.BigDecimal;
import java.sql.Types;

/**
 * describe: Created by IntelliJ IDEA.
 * @author zzl
 * @version 12-12-3
 */
public class JdbcTypeDetector {

    public static int detect(Class clazz) {
        if(clazz == Integer.class) {
            return Types.INTEGER;
        }
        else if(clazz == Long.class || clazz == BigDecimal.class) {
            return Types.BIGINT;
        }
        else if(clazz == String.class) {
            return Types.VARCHAR;
        }
        throw new RuntimeException("unknown type");
    }
}
