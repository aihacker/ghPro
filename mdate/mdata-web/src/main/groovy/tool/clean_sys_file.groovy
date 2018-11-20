package tool

import groovy.sql.Sql

/**
 * Created by zzl on 2016/1/8.
 */
def sql = """
SELECT c.table_name, c.column_name
 FROM INFORMATION_SCHEMA.COLUMNS c WHERE TABLE_SCHEMA = 'liuhe'
and column_name like '%file_id'
"""

def jdbcUrl = 'jdbc:mysql://172.16.108.101:3306/liuhe?userSgt=liuhe&password=a'
Class.forName('com.mysql.jdbc.Driver')

def db = Sql.newInstance(jdbcUrl)
def existingFileIdsSql = ''
db.eachRow(sql) {
    if (existingFileIdsSql) {
        existingFileIdsSql += '\n union \n'
    }
    existingFileIdsSql += 'select ' + it.column_name +
            ' from ' + it.table_name
}
def existingFileIds = []
db.eachRow(existingFileIdsSql) {
    existingFileIds << it[0]
}

sql = """
select id, rel_table, rel_id, type, seq from t_sys_file order by id desc
"""
def fileIdsToClean = []
Set<String> relFileKeySet = new HashSet<>()
db.eachRow(sql) {
    def id = it.id
    if(existingFileIds.contains(id)) {
        return
    }
    def relTable = it.rel_table
    def relId = it.rel_id
    if(relId == 0) {
//        if(relTable[0] != '_') {
        if(relTable == 'temp') {
            fileIdsToClean << id
        }
        else {
            // _IMAGE?
        }
    }
    else {
        def tSql = "select 1 from " + relTable + ' where id = ' + relId
        println '>> ' + tSql
        if(db.firstRow(tSql) == null) {
            fileIdsToClean << id
        }
        else {
            //?
//            def relFileKey = relTable + '.' + relId + '.' + it.type + '.' + it.seq;
//            if(!relFileKeySet.add(relFileKey)) {
//                fileIdsToClean << id
//            }
//            else {
//                println "?"
//            }
        }
    }
}

fileIdsToClean.each {
    db.execute("delete from t_sys_file where id = $it")
}
