package dalgen

import groovy.sql.Sql
import java.sql.ResultSet
import dalgen.model.TableInfo
import dalgen.model.ColumnInfo

/**
 * describe: Created by IntelliJ IDEA.
 * @author zzl
 * @version 12-2-12
 */
class SchemaReader {

    ConfigObject config
    Sql db

    SchemaReader(Map<String, Object> config) {
        this.config = new ConfigObject()
        this.config.putAll(config)
    }

    SchemaReader(ConfigObject config) {
        this.config = config
    }

    def read() {
        println "reading schema info.."

        def jdbcUrl = config.jdbcUrl
        Class.forName(config.driverClass)
        db = Sql.newInstance(jdbcUrl)

        def sql = "select table_name as name from information_schema.tables where table_schema = DATABASE()"
        def tableInfos = []
        db.eachRow(sql) {
            def match = false
            for(def table in config.tables) {
                if(it.name.toUpperCase() ==~ table) {
                    match = true
                    break
                }
            }
            if(match) {
                tableInfos << getTableInfo(it.name)
            }
        }
        db.close()
        return tableInfos
    }

    def read(String tableName) {
        if(!db) {
             def jdbcUrl = config.jdbcUrl
            Class.forName(config.driverClass)
            db = Sql.newInstance(jdbcUrl)
        }
        return getTableInfo(tableName)
    }

    private TableInfo getTableInfo(tableName) {
        def tableInfo = new TableInfo(name: tableName, comments: '', columns: [])

        def metaData = db.connection.getMetaData()
        ResultSet rsColumns = metaData.getColumns(null, null,
                tableName, null)
        while(rsColumns.next()) {
            tableInfo.columns << getColumnInfo(tableName, rsColumns)
        }
        rsColumns.close()

        ResultSet rsPk = metaData.getPrimaryKeys(null, null, tableName)
        while(rsPk.next()) {
            tableInfo.idColumnName = rsPk.getString("COLUMN_NAME")
        }
        rsPk.close()
        if(!tableInfo.idColumnName) {
            tableInfo.idColumnName = tableInfo.columns[0].name
        }

        tableInfo.prefixToSkip = this.config.table_prefix_to_skip
        return tableInfo
    }

    private def getColumnInfo(tableName, ResultSet rsColumns) {
        def columnInfo = new ColumnInfo()
        columnInfo.type = rsColumns.getString("TYPE_NAME")
        columnInfo.name = rsColumns.getString("COLUMN_NAME")

        columnInfo.size = rsColumns.getInt("COLUMN_SIZE")
        columnInfo.scale = rsColumns.getInt("DECIMAL_DIGITS")

//        def sql = """select comments from user_col_comments
//                  where table_name = $tableName and column_name = $columnInfo.name""";

        def sql = """
SELECT column_comment as comment
  FROM information_schema.columns
 WHERE table_schema = DATABASE()
   AND table_name= $tableName and column_name = $columnInfo.name
"""

        columnInfo.comments = db.firstRow(sql).comment

//        sql = """select data_default from cols
//              where table_name = $tableName and column_name = $columnInfo.name""";
//        columnInfo.defaultValue = db.firstRow(sql)[0]?.trim();
        return columnInfo
    }
}
