package pub.dao.sql.dialect;

/**
 * describe: Created by IntelliJ IDEA.
 * @author zzl
 * @version 13-5-9
 */
public class MysqlDialect implements Dialect {


    @Override
    public boolean supportsTop() {
        return false;
    }

    @Override
    public boolean supportsLimit() {
        return true;
    }

    @Override
    public boolean supportsOffset() {
        return true;
    }
}
