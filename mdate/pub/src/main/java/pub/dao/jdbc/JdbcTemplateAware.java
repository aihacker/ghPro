package pub.dao.jdbc;

import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

/**
 * describe: Created by IntelliJ IDEA.
 * @author zzl
 * @version 12-2-15
 */
public interface JdbcTemplateAware {

    void setJdbcTemplate(NamedParameterJdbcTemplate jdbcTemplate);

}
