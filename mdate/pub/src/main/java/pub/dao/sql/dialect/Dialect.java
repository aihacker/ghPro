package pub.dao.sql.dialect;

/**
 * describe: Created by IntelliJ IDEA.
 * @author zzl
 * @version 13-5-9
 */
public interface Dialect {

    boolean supportsTop();
    boolean supportsLimit();
    boolean supportsOffset();

}
