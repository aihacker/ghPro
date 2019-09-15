package pub.dao.jdbc.mapper;

import java.util.List;

/**
 * Created by Administrator on 2017/7/12.
 */
public class TableMapper {

    private String tableName; //数据库表名
    private Class<?> mapperClass; //实体类
    private ColumnMapper id; //主键ID
    private List<ColumnMapper> columnMappers;

    public String getTableName() {
        return tableName;
    }

    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public Class<?> getMapperClass() {
        return mapperClass;
    }

    public void setMapperClass(Class<?> mapperClass) {
        this.mapperClass = mapperClass;
    }

    public ColumnMapper getId() {
        return id;
    }

    public void setId(ColumnMapper id) {
        this.id = id;
    }

    public List<ColumnMapper> getColumnMappers() {
        return columnMappers;
    }

    public void setColumnMappers(List<ColumnMapper> columnMappers) {
        this.columnMappers = columnMappers;
    }
}
