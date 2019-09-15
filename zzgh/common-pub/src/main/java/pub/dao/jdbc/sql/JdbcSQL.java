package pub.dao.jdbc.sql;

import pub.dao.jdbc.analyze.TableAnalyze;
import pub.dao.jdbc.mapper.ColumnMapper;
import pub.dao.jdbc.mapper.TableMapper;

/**
 * Created by Administrator on 2017/7/12.
 */
public class JdbcSQL {

    public static String save(Class entityClass) {
        TableMapper mapper = TableAnalyze.mapperMap.get(entityClass.getName());
        StringBuilder field = new StringBuilder();
        StringBuilder value = new StringBuilder();
        int i = 0;
        for (ColumnMapper col : mapper.getColumnMappers()) {
            if (i > 0) {
                field.append(",");
                value.append(",");
            }
            field.append(col.getColumnName());
            value.append(":" + col.getFieldName());
            i++;
        }
        SQL sql = new SQL.SqlBuilder()
                .field(field.toString())
                .value(value.toString())
                .insert(mapper.getTableName()).build();
        return sql.sql();
    }

    public static String delete(Class entityClass) {
        TableMapper mapper = TableAnalyze.mapperMap.get(entityClass.getName());
        SQL sql = new SQL.SqlBuilder()
                .where(mapper.getId().getColumnName() + " = ?")
                .delete(mapper.getTableName()).build();
        return sql.sql();
    }

    public static String get(Class entityClass) {
        TableMapper mapper = TableAnalyze.mapperMap.get(entityClass.getName());
        SQL sql = new SQL.SqlBuilder()
                .field("*")
                .where(mapper.getId().getColumnName() + " = ?")
                .select(mapper.getTableName())
                .build();
        return sql.sql();
    }
}
