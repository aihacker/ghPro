package pub.dao.sql.dialect;

/**
 * describe: Created by IntelliJ IDEA.
 * @author zzl
 * @version 13-5-9
 */
public class SqlsvrDialect implements Dialect {


    @Override
    public boolean supportsTop() {
        return true;
    }

    @Override
    public boolean supportsLimit() {
        return false;
    }

    @Override
    public boolean supportsOffset() {
        return false;
    }
}
