package dalgen

def tables = [

]

def console = pub.tool.Console.instance

println 'tables?'
def table = ''
while(true) {
    table = console.readLine('>> ')
    if(table.length() == 0) {
        break
    }
    tables << table.toUpperCase()
}

def config = [
//    jdbcUrl: '',
    jdbcUrl: 'jdbc:',
    driverClass: 'com.microsoft.sqlserver.jdbc.SQLServerDriver',
    author: 'zzl',
    package: 'sys',
    tables: tables,
    useValueGenerator: true
]
def schemaReader = new SchemaReader(config)
def tableInfos = schemaReader.read()

def codeWriter = new CodeWriter(config, tableInfos)
codeWriter.write()

println "codes generated in folder ${new File('output').absolutePath}"
println "\ndone."
