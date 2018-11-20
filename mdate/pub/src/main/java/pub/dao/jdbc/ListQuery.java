package pub.dao.jdbc;

import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import pub.dao.query.QueryResult;
import pub.dao.query.QueryResultType;
import pub.dao.query.QuerySettings;

import java.util.List;

/**
 * describe: Created by IntelliJ IDEA.
 * @author zzl
 * @version 12-2-15
 */
@SuppressWarnings("unchecked")
public class ListQuery<T> extends JdbcTemplateQuery<T> {

    private QuerySettings settings;
    private QueryResult result;
    private boolean quietLog;

    public ListQuery() {
        settings = new QuerySettings();
        result = new QueryResult();
        quietLog = false;
    }

    public static <T1> ListQuery<T1> of(Class<T1> clazz) {
        ListQuery<T1> query = new ListQuery<T1>();
        query.setResultBeanClass(clazz);
        return query;
    }

    public ListQuery(QueryResultType resultType) {
        this();
        settings.setResultType(resultType);
    }

    public void execute() {
        long startTick = System.currentTimeMillis();

        String sql = getSql(true);
        sql = constructFullSql(sql);

        MapSqlParameterSource paramsSource = new MapSqlParameterSource(params);

        RowMapper<T> rowMapper = createRowMapper(settings, resultBeanClass);
        List<T> list = jdbcTemplate.query(sql, paramsSource, rowMapper);

        if(!quietLog) {
            System.out.println("List: " + (System.currentTimeMillis() - startTick));
        }

        result.setRows(list);
    }

    private String constructFullSql(String sql) {
        if(outerSelect == null) {
            return sql;
        }
        return "select " + outerSelect + " from (" + sql + ") x";
    }

    public QueryResult<T> getResult() {
        return result;
    }

    public QuerySettings getSettings() {
        return settings;
    }

    public void applySettings(QuerySettings settings) {
        this.settings.setResultType(settings.getResultType());
    }

    @Override
    public void setQuietLog() {
        quietLog = true;
    }

}
