package dalgen

import dalgen.model.TableInfo
import dalgen.model.EntityInfo
import dalgen.model.FieldInfo

/**
 * describe: Created by IntelliJ IDEA.
 * @author zzl
 * @version 12-2-12
 */
class TableEntityMapper {

    TableEntityMapper() {
    }

    private static String toUpperCamelCase(String s){
        s = toLowerCamelCase(s)
        return s.length() > 1? s[0].toUpperCase() + s[1..-1]: s
    }

    private static String toLowerCamelCase(String s) {
        s.toLowerCase().replaceAll(/_(\w)?/) { wholeMatch, firstLetter ->
            firstLetter?.toUpperCase() ?: ''
        }
    }

    private static String mapDbTypeToJavaType(String typeName, int size, int scale) {
        typeName = typeName.toUpperCase()
        if(typeName.contains('IMAGE'))
        {
            return 'byte[]'
        }
        if(typeName.contains('CHAR'))
        {
            return 'String'
        }
        if(typeName.contains('INT'))
        {
            return 'Integer'
        }
        if(typeName.contains('DATE') || typeName.contains('TIME'))
        {
            return 'Date'
        }
        if(typeName.contains('NUM'))
        {
            return scale > 0 ? 'Double' : size > 10? 'Long': "Integer"
        }
        if(typeName == 'FLOAT') {
            return 'Double'
        }
        if(typeName == 'BLOB' || typeName == 'VARBINARY')
        {
            return 'byte[]'
        }
        if(typeName == 'CLOB' || typeName == 'TEXT') {
            return 'String'
        }
        if(typeName == 'BIT') {
            //return 'Boolean';
            return 'Integer'
        }
        if(typeName == 'DOUBLE') {
            return "Double"
        }
        assert false: "unknown typeName: " + typeName
        null
    }

    private static String mapDbTypeToJdbcType(String typeName, int size, int scale) {
        String jdbcType = typeName
        if(typeName == 'VARCHAR2') {
            jdbcType = 'VARCHAR'
        }
        else if(typeName == "NUMBER") {
            jdbcType = scale > 0 ? 'DOUBLE' : size > 10? 'BIGINT': "INTEGER"
        }
        return jdbcType
    }

    EntityInfo map(TableInfo tableInfo) {
        def entityInfo = new EntityInfo()

        def entityNameSource = tableInfo.name

        def prefixToSkip = tableInfo.prefixToSkip
        if(prefixToSkip && entityNameSource.toUpperCase().startsWith(prefixToSkip.toUpperCase())) {
            entityNameSource = entityNameSource.substring(prefixToSkip.length())
        }

        entityInfo.name = toUpperCamelCase(entityNameSource)

        def fieldInfos = []
        tableInfo.columns.each {
            def fieldInfo = new FieldInfo()
            fieldInfo.name = toLowerCamelCase(it.name)
            fieldInfo.type = mapDbTypeToJavaType(it.type, it.size, it.scale)
            fieldInfo.jdbcType = mapDbTypeToJdbcType(it.type, it.size, it.scale)
            fieldInfo.getter = 'get' + toUpperCamelCase(it.name)
            fieldInfo.setter = 'set' + toUpperCamelCase(it.name)
            fieldInfos << fieldInfo
            if (it.name == tableInfo.idColumnName) {
                entityInfo.idField = fieldInfo
            }
        }
        entityInfo.fields = fieldInfos
        return entityInfo
    }

}
