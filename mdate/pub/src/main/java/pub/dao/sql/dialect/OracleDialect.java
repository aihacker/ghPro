package pub.dao.sql.dialect;

/**
 * describe: Created by IntelliJ IDEA.
 * @author zzl
 * @version 13-5-9
 */
public class OracleDialect implements Dialect {


    @Override
    public boolean supportsTop() {
        return false;
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
