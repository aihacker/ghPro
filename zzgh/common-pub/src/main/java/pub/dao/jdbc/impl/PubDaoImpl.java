package pub.dao.jdbc.impl;

import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.*;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import pub.dao.jdbc.PubDao;
import pub.dao.jdbc.sql.SQL;
import pub.dao.page.Page;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/6/13.
 */
public class PubDaoImpl implements PubDao {

    private NamedParameterJdbcTemplate jdbcTemplate;

    public void setJdbcTemplate(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }


    @Override
    public int execute(String sql, Object... args) {
        return jdbcTemplate.getJdbcOperations().update(sql, args);
    }

    @Override
    public int executeBean(String sql, Object param) {
        SqlParameterSource source = new BeanPropertySqlParameterSource(param);
        return jdbcTemplate.update(sql, source);
    }

    @Override
    public <P extends Page, T> List<T> queryPage(SQL.SqlBuilder sql, P page, Class<T> tClass) {
        if (page.getPageIs()) {
            Integer count = queryParamInt(sql.count().build().sql(), page);
            if (count == 0) {
                return null;
            }
            page.setTotalCount(count);

            sql.limit(":pagestart, :rowsPerPage");
        }
        return queryList(sql.select().build().sql(), page, tClass);
    }

    @Override
    public Integer insertAndGetKey(String sql, Object param) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        SqlParameterSource source = new BeanPropertySqlParameterSource(param);

        ParsedSql parsedSql = NamedParameterUtils.parseSqlStatement(sql);
        final String sqlToUse = NamedParameterUtils.substituteNamedParameters(parsedSql, source);
        final Object[] params = NamedParameterUtils.buildValueArray(parsedSql, source, null);

        jdbcTemplate.getJdbcOperations().update(new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
                PreparedStatement ps = con.prepareStatement(sqlToUse, PreparedStatement.RETURN_GENERATED_KEYS);
                if (params != null && params.length > 0) {
                    for (int i = 0; i < params.length; i++) {
                        ps.setObject(i + 1, params[i]);
                    }
                }
                return ps;
            }
        }, keyHolder);
        return keyHolder.getKey().intValue();
    }

    @Override
    public <T> int[] executeBatch(String sql, List<T> param) {
        SqlParameterSource[] sources = SqlParameterSourceUtils.createBatch(param.toArray());
        return jdbcTemplate.batchUpdate(sql, sources);
    }

    @Override
    public int[] batch(String sql, Object[] param) {
        if (param != null && param.length > 0) {
            List<Object[]> params = new ArrayList<>();
            for (Object ob : param) {
                params.add(new Object[]{ob});
            }
            return jdbcTemplate.getJdbcOperations().batchUpdate(sql, params);
        }
        return jdbcTemplate.getJdbcOperations().batchUpdate(sql);
    }


    @Override
    public <T> T query(Class<T> tClass, String sql, Object... args) {
        try {
            if (tClass.getName().startsWith("java")) {
                return jdbcTemplate.getJdbcOperations().queryForObject(sql, tClass, args);
            } else {
                RowMapper<T> rowMapper = new BeanPropertyRowMapper<T>(tClass);
                return jdbcTemplate.getJdbcOperations().queryForObject(sql, args, rowMapper);
            }
        } catch (IncorrectResultSizeDataAccessException e) {
            if (e.getActualSize() == 0) return null;
            else throw e;
        }
    }

    @Override
    public <T> T query(String sql, Object param, Class<T> tClass) {
        try {
            SqlParameterSource source = new BeanPropertySqlParameterSource(param);
            if (tClass.getName().startsWith("java")) {
                return jdbcTemplate.queryForObject(sql, source, tClass);
            } else {
                RowMapper<T> rowMapper = new BeanPropertyRowMapper<T>(tClass);
                return jdbcTemplate.queryForObject(sql, source, rowMapper);
            }
        } catch (IncorrectResultSizeDataAccessException e) {
            if (e.getActualSize() == 0) return null;
            else throw e;
        }
    }

    @Override
    public int queryInt(String sql, Object... args) {
        Integer val = query(Integer.class, sql, args);
        return val == null ? 0 : val;
    }

    @Override
    public int queryParamInt(String sql, Object param) {
        Integer val = query(sql, param, Integer.class);
        return val == null ? 0 : val;
    }

    @Override
    public <T> List<T> queryList(Class<T> tClass, String sql, Object... args) {
        if (tClass.getName().startsWith("java")) {
            return jdbcTemplate.getJdbcOperations().queryForList(sql, args, tClass);
        } else {
            RowMapper<T> rowMapper = new BeanPropertyRowMapper<T>(tClass);
            return jdbcTemplate.getJdbcOperations().query(sql, args, rowMapper);
        }
    }

    @Override
    public <T> List<T> queryList(String sql, Object param, Class<T> tClass) {
        SqlParameterSource source = new BeanPropertySqlParameterSource(param);
        if (tClass.getName().startsWith("java")) {
            return jdbcTemplate.queryForList(sql, source, tClass);
        } else {
            RowMapper<T> rowMapper = new BeanPropertyRowMapper<T>(tClass);
            return jdbcTemplate.query(sql, source, rowMapper);
        }
    }

    @Override
    public <T> List<T> queryList(String sql, Object param, RowMapper<T> rowMapper) {
        SqlParameterSource source = new BeanPropertySqlParameterSource(param);
        return jdbcTemplate.query(sql, source, rowMapper);
    }

    @Override
    public void queryList(String sql, Object param, RowCallbackHandler handler) {
        SqlParameterSource source = new BeanPropertySqlParameterSource(param);
        jdbcTemplate.query(sql, source, handler);
    }
}
