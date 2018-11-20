package pub.dao.mybatis;

import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import pub.dao.DataSourceAware;
import pub.dao.GeneralDao;
import pub.dao.jdbc.JdbcTemplateAware;
import pub.types.Executable;

import javax.sql.DataSource;
import java.util.Date;
import java.util.List;

/**
 * describe: Created by IntelliJ IDEA.
 * @author zzl
 * @version 12-2-15
 */
public class GeneralDaoImpl extends MyBatisDao<Object> implements GeneralDao {

    private DataSource dataSource;
    private NamedParameterJdbcTemplate jdbcTemplate;

    public GeneralDaoImpl() {
        super(Object.class);
    }

    @Override
    public void execute(Executable executable) {
        if(executable instanceof SqlSessionAware) {
            ((SqlSessionAware)executable).setSqlSession(getSqlSession());
        }
        if(dataSource != null && executable instanceof DataSourceAware) {
            ((DataSourceAware) executable).setDataSource(dataSource);
        }
        else if(jdbcTemplate != null && executable instanceof JdbcTemplateAware) {
            ((JdbcTemplateAware)executable).setJdbcTemplate(jdbcTemplate);
        }
        executable.execute();
    }

    @Override
    public Date getSysDate() {
        return null;
    }

    @Override
    public void flushOperations() {
        //
    }

    @Override
    public void evict(List entities) {
        //
    }

    @Override
    public void evict(Object entity) {
        //
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T> T queryValue(Class<T> clazz, String sql, Object... args) {
        try {
            if(clazz.getName().startsWith("java")) {
                return jdbcTemplate.getJdbcOperations().queryForObject(sql, args, clazz);
            }
            else {
                RowMapper rowMapper = new BeanPropertyRowMapper(clazz);
                return (T) jdbcTemplate.getJdbcOperations().queryForObject(sql, args, rowMapper);
            }
        }
        catch (IncorrectResultSizeDataAccessException e) {
            if(e.getActualSize() == 0) {
                return null;
            }
            else {
                throw e;
            }
        }
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T> List<T> queryList(Class<T> clazz, String sql, Object... args) {
        if(clazz.getName().startsWith("java")) {
            return jdbcTemplate.getJdbcOperations().queryForList(sql, args, clazz);
        }
        else {
            RowMapper rowMapper = new BeanPropertyRowMapper(clazz);
            return jdbcTemplate.getJdbcOperations().query(sql, args, rowMapper);
        }
    }

    @Override
    public int execute(String sql, Object... args) {
        return jdbcTemplate.getJdbcOperations().update(sql, args);
    }


    public void setDataSource(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public void setJdbcTemplate(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
}
