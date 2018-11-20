package pub.dao.jdbc;

import org.springframework.jdbc.core.CallableStatementCreator;
import org.springframework.jdbc.core.CallableStatementCreatorFactory;
import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterUtils;
import org.springframework.jdbc.core.namedparam.ParsedSql;
import pub.functions.VarFuncs;
import pub.types.Executable;

import java.sql.Types;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * describe: Created by IntelliJ IDEA.
 * @author zzl
 * @version 12-12-3
 */
@SuppressWarnings("unchecked")
public class DbCommand implements Executable, JdbcTemplateAware  {

    private NamedParameterJdbcTemplate jdbcTemplate;
    private String sql;
    private Map<String, Object> inParamMap;
    private Map<String, Object> outParamMap;
    private Map<String, Object> resultMap;

    private Integer affectedRows;

    public DbCommand() {
        inParamMap = new HashMap<String, Object>();
        outParamMap = new HashMap<String, Object>();
    }

    @Override
    public void setJdbcTemplate(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public String getSql() {
        return sql;
    }

    public DbCommand setSql(String sql) {
        this.sql = sql;
        return this;
    }

    public DbCommand put(String paramName, Object value) {
        inParamMap.put(paramName, value);
        return this;
    }

    public DbCommand output(String paramName, Class clazz) {
        outParamMap.put(paramName, clazz);
        return this;
    }

    public DbCommand output(String paramName, int jdbcType) {
        outParamMap.put(paramName, jdbcType);
        return this;
    }

    @Override
    public void execute() {
//        this.affectedRows = 0;
        boolean callable = sql.toLowerCase().contains("call");
        if(!callable) {
            int rows = jdbcTemplate.update(sql, inParamMap);
            this.affectedRows = rows;
            return;
        }

        ParsedSql parsedSql = NamedParameterUtils.parseSqlStatement(sql);
        MapSqlParameterSource paramSource = new MapSqlParameterSource(inParamMap);
        paramSource.addValues(outParamMap);

		List<SqlParameter> declaredParameters = NamedParameterUtils.buildSqlParameterList(parsedSql, paramSource);
        setDeclaredOutputParams(declaredParameters);

        String sqlToUse = NamedParameterUtils.substituteNamedParameters(parsedSql, paramSource);
        CallableStatementCreatorFactory cscf = new CallableStatementCreatorFactory(sqlToUse,
                declaredParameters);
        CallableStatementCreator csc = cscf.newCallableStatementCreator(inParamMap);

        resultMap = jdbcTemplate.getJdbcOperations().call(csc, declaredParameters);
        coerceOutputTypes();

        this.affectedRows = 0;
    }

    private void coerceOutputTypes() {
        for(String key: resultMap.keySet()) {
            Object outTypeObj = outParamMap.get(key);
            if(!(outTypeObj instanceof Class)) {
                continue;
            }
            Class outType = (Class) outTypeObj;
            resultMap.put(key, VarFuncs.to(resultMap.get(key), outType));
        }
    }

    private void setDeclaredOutputParams(List<SqlParameter> declaredParameters) {
        for(int n = 0; n < declaredParameters.size(); n++) {
            SqlParameter sqlParameter = declaredParameters.get(n);
            String paramName = sqlParameter.getName();
            if(outParamMap.containsKey(paramName)) {
                Object outParamTypeInfo = outParamMap.get(paramName);
                int jdbcType = Types.OTHER;
                if(outParamTypeInfo instanceof Class) {
                    jdbcType = JdbcTypeDetector.detect((Class) outParamTypeInfo);
                }
                else if(outParamTypeInfo instanceof Integer) {
                    jdbcType = (Integer)outParamTypeInfo;
                }
                SqlParameter outParam = new SqlOutParameter(paramName, jdbcType);
                declaredParameters.set(n, outParam);
            }
        }
    }

    public Object get(String paramName) {
        return resultMap.get(paramName);
    }

    public Map<String, Object> getResultMap() {
        return resultMap;
    }

    public Integer getAffectedRows() {
        return affectedRows;
    }
}
