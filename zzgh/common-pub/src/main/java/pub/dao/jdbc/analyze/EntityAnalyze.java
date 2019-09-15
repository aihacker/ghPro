package pub.dao.jdbc.analyze;

import com.libs.common.type.StringUtils;
import org.springframework.beans.BeanUtils;
import pub.dao.jdbc.mapper.ColumnMapper;
import pub.dao.jdbc.mapper.TableMapper;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/7/12.
 */
public class EntityAnalyze {

    public static TableMapper analyze(Class entityClass, String tablePrefix) {
        Table table = (Table) entityClass.getAnnotation(Table.class);
        TableMapper mapper = null;
        if (table != null) {
            mapper = new TableMapper();
            mapper.setMapperClass(entityClass);

            String tableName = table.name();
            if (tableName == null || tableName.length() == 0) {
                tableName = StringUtils.camelToUnderline(entityClass.getSimpleName());
            }
            if (tablePrefix != null && !tableName.startsWith(tablePrefix)) {
                tableName = tablePrefix + tableName;
            }
            mapper.setTableName(tableName);

            List<ColumnMapper> columnMappers = new ArrayList<>();
            mapper.setColumnMappers(columnMappers);
            PropertyDescriptor[] propDescs = BeanUtils.getPropertyDescriptors(entityClass);
            for (PropertyDescriptor propDesc : propDescs) {
                processProperty(propDesc, mapper);
            }
        }
        return mapper;
    }

    private static void processProperty(PropertyDescriptor propDesc, TableMapper mapper) {
        Method method = propDesc.getReadMethod();
        Column columnAnn = method.getAnnotation(Column.class);
        if (columnAnn == null) {
            return;
        }

        String columnName = columnAnn.name();
        if (columnName == null || columnName.length() <= 0) {
            columnName = StringUtils.camelToUnderline(propDesc.getName());
        }
        ColumnMapper columnMapper = new ColumnMapper();
        columnMapper.setColumnName(columnName);
        columnMapper.setFieldClass(propDesc.getPropertyType());
        columnMapper.setFieldName(propDesc.getName());
        mapper.getColumnMappers().add(columnMapper);

        Id idAnn = method.getAnnotation(Id.class);
        if (idAnn != null) {
            mapper.setId(columnMapper);
        }
    }
}
