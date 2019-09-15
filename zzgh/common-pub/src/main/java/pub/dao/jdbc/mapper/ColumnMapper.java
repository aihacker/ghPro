package pub.dao.jdbc.mapper;

/**
 * Created by Administrator on 2017/7/12.
 */
public class ColumnMapper {

    private String columnName; //数据库表字段名
    private String fieldName; //对象字段名称
    private Class<?> fieldClass;

    public String getColumnName() {
        return columnName;
    }

    public void setColumnName(String columnName) {
        this.columnName = columnName;
    }

    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public Class<?> getFieldClass() {
        return fieldClass;
    }

    public void setFieldClass(Class<?> fieldClass) {
        this.fieldClass = fieldClass;
    }
}
