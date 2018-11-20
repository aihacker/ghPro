package pub.dao.mybatis.support;

import org.springframework.beans.BeanUtils;

import javax.persistence.*;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * describe: Created by IntelliJ IDEA.
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
        this.tableName = tableAnn.name();

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
        mapping.column = columnAnn.name();
        columnMappings.add(mapping);

        Id idAnn = method.getAnnotation(Id.class);
        if (idAnn != null) {
            idMapping = mapping;
            idJavaType = resolveJavaType(mapping.propertyType);
            GeneratedValue generatedValueAnn = method.getAnnotation(GeneratedValue.class);
            this.idGenerated = generatedValueAnn != null;
            if(this.idGenerated) {
                this.idIsIdentity = generatedValueAnn.strategy() == GenerationType.IDENTITY;
            }
        }
    }

    private String resolveJavaType(Class<?> clazz) {
        String type = null;
        if (clazz == Long.class) {
            type = "long";
        }
        else if (clazz == Integer.class) {
            type = "int";
        }
        else if (clazz == String.class) {
            type = "string";
        }
        else {
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
