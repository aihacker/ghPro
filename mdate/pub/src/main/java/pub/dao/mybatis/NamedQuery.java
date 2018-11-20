package pub.dao.mybatis;

import org.apache.ibatis.session.SqlSession;
import pub.dao.query.Query;
import pub.dao.query.QueryResult;
import pub.dao.query.QuerySettings;
import pub.dao.sql.model.Columns;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * describe: Created by IntelliJ IDEA.
 * @author zzl
 * @version 12-3-7
 */
@SuppressWarnings("unchecked")
public class NamedQuery<T> implements Query<T>, SqlSessionAware {
    
    private String name;
    private Map<String, Object> _params;
    private Object[] params;
    
    private transient SqlSession sqlSession;

    public NamedQuery() {
        _params = new HashMap<String, Object>();
    }

    public NamedQuery(String name) {
        this();
        this.name = name;
    }

    public NamedQuery(String name, Object... params) {
        this(name);
        this.params = params;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public Query select(String select) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Query outerSelect(String outerSelect) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Query innerSelect(String innerSelect) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Query from(String from) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Query where(Object where) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Query distinct(boolean distinct) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Query orderBy(String orderBy) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Query groupBy(String groupBy) {
        throw new UnsupportedOperationException();
    }

    public NamedQuery put(String paramName, Object paramValue) {
        assert params == null || params.length == 0;
        _params.put(paramName, paramValue);
        return this;
    }

    @Override
    public Query setSql(String sql) {
        throw new UnsupportedOperationException();
    }

    private List resultList = null;
    
    @Override
    public void execute() {
        Object param;
        if (params == null || params.length == 0) {
            param = _params;
        } else if (params.length == 1) {
            param = params[0];
        } else {
            param = Arrays.asList(params);
        }
        resultList = sqlSession.selectList(name, param);
    }

    @Override
    public void setSqlSession(SqlSession sqlSession) {
        this.sqlSession = sqlSession;
    }
    
    public QueryResult<T> getResult() {
        QueryResult queryResult = new QueryResult();
        queryResult.setRows(resultList);
        return queryResult;
    }

    @Override
    public QuerySettings getSettings() {
        throw new UnsupportedOperationException();
    }

    @Override
    public void applySettings(QuerySettings settings) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Query select(Columns columns) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void setQuietLog() {
        //
    }

    public T getSingleResult() {
        return resultList.size() == 1? (T) resultList.get(0): null;
    }
}
