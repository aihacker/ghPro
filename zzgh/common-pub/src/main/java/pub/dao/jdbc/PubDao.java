package pub.dao.jdbc;

import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.jdbc.core.RowMapper;
import pub.dao.jdbc.sql.SQL;
import pub.dao.page.Page;

import java.util.List;

/**
 * Created by Administrator on 2017/6/13.
 */
public interface PubDao {

    int execute(String sql, Object... args);
    
    int executeBean(String sql, Object param);

    <P extends Page, T> List<T> queryPage(SQL.SqlBuilder sql, P page, Class<T> tClass);

    Integer insertAndGetKey(String sql, Object param);

    <T> int[] executeBatch(String sql, List<T> param);

    int[] batch(String sql, Object[] param);

    <T> T query(Class<T> tClass, String sql, Object... args);

    <T> T query(String sql, Object param, Class<T> tClass);

    int queryInt(String sql, Object... args);

    int queryParamInt(String sql, Object param);

    <T> List<T> queryList(Class<T> tClass, String sql, Object... args);

    <T> List<T> queryList(String sql, Object param, Class<T> tClass);

    <T> List<T> queryList(String sql, Object param, RowMapper<T> rowMapper);

    void queryList(String sql, Object param, RowCallbackHandler handler);
}
