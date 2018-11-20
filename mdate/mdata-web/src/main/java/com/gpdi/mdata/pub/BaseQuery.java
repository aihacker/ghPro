package com.gpdi.mdata.pub;

import com.sun.org.apache.xpath.internal.operations.Bool;
import org.apache.commons.dbcp.BasicDataSource;

import javax.sql.DataSource;
import java.io.Serializable;
import java.sql.*;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

/**
 * Created by lenovo on 2016/9/5.
 * 在executeQuery中存在严重的sql 注入漏洞。 应该改成PreparedStatement 实现
 *
 * */
public class BaseQuery implements Serializable{
    private static final long serialVersionUID = 1L;

    protected String sql;
    protected List<Map<Object,Object>> result; //实际为LinkedHashMap

    public void setSql(String sql) {
        this.sql = sql;
    }

    public String getSql() {
        return sql;
    }

    public List<Map<Object,Object>> getResult() {
        return result;
    }

    //执行非查询的sql语句, update, insert, create, 等
    public void execute(DataSource dataSource) throws SQLException {
        Connection conn = null;
        PreparedStatement st = null;
        try {
            conn = dataSource.getConnection();
            st = conn.prepareStatement(sql);
            st.execute();
        }catch (SQLException e){
            throw e;
        }finally {
            try { st.close();
            }catch (Exception e){ }
            try { conn.close();
            }catch (Exception e){ }
        }
    }

    //执行非查询的sql语句, update, insert 等 返回更新条数
    public int executeUpdate(DataSource dataSource) throws SQLException {
        Connection conn = null;
        PreparedStatement st = null;
        try {
            conn = dataSource.getConnection();
            st = conn.prepareStatement(sql);
            int count = st.executeUpdate();
            return count;
        }catch (SQLException e){
            throw e;
        }finally {
            try { st.close();
            }catch (Exception e){ }
            try { conn.close();
            }catch (Exception e){ }
        }
    }

    public void execute(DataSource dataSource, Object... args) throws Exception{
        Object[] objs = args;
        Connection conn = null;
        PreparedStatement st = null;
        try {
            conn = dataSource.getConnection();
            st = conn.prepareStatement(sql);
            if(args != null) {
                for (int i = 0; i < objs.length; i++) {
                    int index = i+1;
                    Object o = objs[i];
                    if (o instanceof String) {
                        st.setString(index, (String) o);
                    } else if (o instanceof Integer) {
                        st.setInt(index, (int) o);
                    } else if (o instanceof Boolean) {
                        st.setBoolean(index, (Boolean) o);
                    } else if (o instanceof Byte) {
                        st.setByte(index, (Byte) o);
                    } else if (o instanceof Timestamp){
                        st.setTimestamp(index, (Timestamp) o);
                    } else if (o instanceof Time){
                        st.setTime(index, (Time)o);
                    }else if (o instanceof Date) {
                        st.setDate(index, (Date) o);
                    } else if (o instanceof Double) {
                        st.setDouble(index, (Double) o);
                    } else if (o instanceof Float) {
                        st.setFloat(index, (Float) o);
                    } else if (o instanceof Long) {
                        st.setLong(index, (Long) o);
                    } else if (o instanceof Short) {
                        st.setShort(index, (Short) o);
                    } else {
                        st.setObject(index, o);
                    }
                }
            }
            st.execute();
        }catch (Exception e){
            throw e;
        }finally {
            try { st.close();
            }catch (Exception e){ }
            try { conn.close();
            }catch (Exception e){ }
        }
    }


    public void executeQuery(DataSource dataSource) throws Exception{
        Connection conn = null;
        try {
            conn = dataSource.getConnection();
        }catch (Exception e){
            result = null;
            e.printStackTrace();
            throw e;
        }
        Statement stmt = null;
        try {
            stmt = conn.createStatement() ;
            ResultSet resultSet = stmt.executeQuery(sql);
            initResult(resultSet);
        } catch (Exception e) {
            result = null;
            e.printStackTrace();
            throw  e;
        }finally {
            try { stmt.close();
             }catch (Exception e){}
            try { conn.close();
            }catch (Exception e){}
        }
    }

    protected void initResult(ResultSet resultSet){
        result = new ArrayList();
        ArrayList<String> columnLabel = new ArrayList<>();
        try {
            ResultSetMetaData resultSetMetaData = resultSet.getMetaData();
            int num = resultSetMetaData.getColumnCount();
            for(int n=1; n<=num; n++){
                columnLabel.add(resultSetMetaData.getColumnLabel(n).toLowerCase()); //所有的查询列都是小写
            }
            while (resultSet.next()) {
                LinkedHashMap<Object, Object> map = new LinkedHashMap<>();
                for (int i = 1; i <= num; i++) {
                    String key = columnLabel.get(i-1);
                    Object val = resultSet.getObject(key);
                    map.put(key,val);
                }
                result.add(map);
            }
        }catch (SQLException e){
        }
    }



}
