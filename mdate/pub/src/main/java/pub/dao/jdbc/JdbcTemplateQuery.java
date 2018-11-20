package pub.dao.jdbc;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.ColumnMapRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.SingleColumnRowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import pub.dao.query.AbstractQuery;
import pub.dao.query.QueryResultType;
import pub.dao.query.QuerySettings;

/**
 * describe: Created by IntelliJ IDEA.
 * @author zzl
 * @version 12-2-15
 */
@SuppressWarnings("unchecked")
public abstract class JdbcTemplateQuery<T> extends AbstractQuery<T> implements JdbcTemplateAware {

    protected NamedParameterJdbcTemplate jdbcTemplate;

    @Override
    public void setJdbcTemplate(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    protected <T> RowMapper<T> createRowMapper(QuerySettings settings, Class<T> beanClass) {
        RowMapper<T> rowMapper = null;
        if (beanClass != null) {
            if (beanClass.getPackage().getName().startsWith("java.")) {
                rowMapper = new SingleColumnRowMapper<T>(beanClass);
            } else {
                rowMapper = new BeanPropertyRowMapper(beanClass);
            }
        } else if (settings.getResultType() == QueryResultType.MAP) {
            rowMapper = (RowMapper<T>) new ColumnMapRowMapper();
        } else if (settings.getResultType() == QueryResultType.ARRAY) {
            rowMapper = (RowMapper<T>) new ArrayRowMapper();
        }
        return rowMapper;
    }


}
