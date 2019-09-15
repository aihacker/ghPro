package com.weixin.utils;

import com.libs.common.json.JsonUtils;
import com.weixin.bean.token.TokenVal;
import com.weixin.config.Config;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;

/**
 * Created by Administrator on 2017/8/1.
 */
public class DbUtils {

    public static TokenVal getVal(String name) {
        try {
            String sql = "select * from t_sys_val where name = ?";
            RowMapper<TokenVal> rowMapper = new BeanPropertyRowMapper<TokenVal>(TokenVal.class);
            TokenVal tokenVal = Config.jdbcTemplate.getJdbcOperations().queryForObject(sql, new Object[]{
                    name
            }, rowMapper);
            System.out.println(tokenVal.toString());
            return tokenVal;
        } catch (IncorrectResultSizeDataAccessException e) {
            if (e.getActualSize() == 0) return null;
        }
        return null;
    }

    public static TokenVal getVal(Integer agentid) {
        try {

            String sql = "select * from t_sys_val where agentid = ?";
            RowMapper<TokenVal> rowMapper = new BeanPropertyRowMapper<TokenVal>(TokenVal.class);
            TokenVal tokenVal = Config.jdbcTemplate.getJdbcOperations().queryForObject(sql, new Object[]{
                    agentid
            }, rowMapper);
            System.out.println(tokenVal.toString());
            return tokenVal;
        } catch (IncorrectResultSizeDataAccessException e) {
            if (e.getActualSize() == 0) return null;
        }
        return null;
    }

    public static void addVal(String name, String val) {
        String sql = "insert into t_sys_val(name, val) values(:name, :val)" +
                " ON DUPLICATE KEY UPDATE val = :val";
        TokenVal sysVal = new TokenVal();
        sysVal.setName(name);
        sysVal.setVal(val);

        SqlParameterSource source = new BeanPropertySqlParameterSource(sysVal);
        Config.jdbcTemplate.update(sql, source);
    }

    public static void addVal(Integer agentId, String valToken, String stringfy) {
        String sql = "insert into t_sys_val(agentid, name, val) values(:agentid, :name, :val)";
        TokenVal sysVal = new TokenVal();
        sysVal.setAgentid(agentId);
        sysVal.setName(valToken);
        sysVal.setVal(stringfy);

        SqlParameterSource source = new BeanPropertySqlParameterSource(sysVal);
        Config.jdbcTemplate.update(sql, source);

    }

    public static void updateVal(Integer agentId, String valToken, String stringfy) {

        TokenVal sysVal = new TokenVal();
        sysVal.setAgentid(agentId);
        sysVal.setName(valToken);
        sysVal.setVal(stringfy);

        String sql = "update t_sys_val set val = '" + stringfy + "' WHERE agentid = " + agentId;

        SqlParameterSource source = new BeanPropertySqlParameterSource(sysVal);
        Config.jdbcTemplate.update(sql,source);
        /*Config.jdbcTemplate.*/
    }
}
