package wxgh.data.union;

/**
 * Created by Administrator on 2017/5/4.
 */
public class QueryParam {

    private String sql;
    private Object[] params;

    public QueryParam() {
    }

    public QueryParam(String sql, Object[] params) {
        this.sql = sql;
        this.params = params;
    }

    public String getSql() {
        return sql;
    }

    public void setSql(String sql) {
        this.sql = sql;
    }

    public Object[] getParams() {
        return params;
    }

    public void setParams(Object[] params) {
        this.params = params;
    }
}
