package pub.dao.mybatis.support;

import com.libs.common.type.StringUtils;
import org.springframework.beans.BeanUtils;
import pub.dao.jdbc.sql.SQL;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * describe: Created by IntelliJ IDEA.
 *
 * @author zzl
 * @version 12-5-4
 */
@SuppressWarnings("unchecked")
public class EntityMappingAnalyzer {

    private Class entityClass;
    private String entityName;
    private String tableName;

    private boolean idGenerated;
    private boolean idIsIdentity;

    private String idJavaType;
    private ColumnMapping idMapping; //property->column
    private List<ColumnMapping> columnMappings;

    public EntityMappingAnalyzer(Class entityClass) {
        this.entityClass = entityClass;
        columnMappings = new ArrayList<ColumnMapping>();
        entityName = entityClass.getSimpleName();
    }

    public boolean analyze() {
        Table tableAnn = (Table) entityClass.getAnnotation(Table.class);
        if (tableAnn == null) {
            return false;
        }
        String tableName = tableAnn.name();
        if (tableName == null || tableName.length() == 0) {
            tableName = StringUtils.camelToUnderline(entityName);
        }
        if (!tableName.startsWith(SQL.PREFIX)) {
            tableName = SQL.PREFIX + tableName;
        }
        this.tableName = tableName;

        PropertyDescriptor[] propDescs = BeanUtils.getPropertyDescriptors(entityClass);
        for (PropertyDescriptor propDesc : propDescs) {
            processProperty(propDesc);
        }
        return true;
    }

    private void processProperty(PropertyDescriptor propDesc) {
        Method method = propDesc.getReadMethod();
        Column columnAnn = method.getAnnotation(Column.class);
        if (columnAnn == null) {
            return;
        }

        ColumnMapping mapping = new ColumnMapping();
        mapping.property = propDesc.getName();
        mapping.propertyType = propDesc.getPropertyType();

        String columnName = columnAnn.name();
        if (columnName == null || columnName.length() == 0) {
            columnName = StringUtils.camelToUnderline(propDesc.getName());
        }
        mapping.column = columnName;
        columnMappings.add(mapping);

        Id idAnn = method.getAnnotation(Id.class);
        if (idAnn != null) {
            idMapping = mapping;
            idJavaType = resolveJavaType(mapping.propertyType);
            GeneratedValue generatedValueAnn = method.getAnnotation(GeneratedValue.class);
            this.idGenerated = true;
            if (this.idGenerated) {
                this.idIsIdentity = true;
            }
        }
    }

    private String resolveJavaType(Class<?> clazz) {
        String type = null;
        if (clazz == Long.class) {
            type = "long";
        } else if (clazz == Integer.class) {
            type = "int";
        } else if (clazz == String.class) {
            type = "string";
        } else {
            type = clazz.getName();
        }
        return type;
    }

    public Class getEntityClass() {
        return entityClass;
    }

    public String getEntityName() {
        return entityName;
    }

    public String getTableName() {
        return tableName;
    }

    public boolean isIdGenerated() {
        return idGenerated;
    }

    public boolean isIdIsIdentity() {
        return idIsIdentity;
    }

    public String getIdJavaType() {
        return idJavaType;
    }

    public ColumnMapping getIdMapping() {
        return idMapping;
    }

    public List<ColumnMapping> getColumnMappings() {
        return columnMappings;
    }
}
