package dalgen

def configFile = args? args[0]: null

def configurator = new Configurator(configFile)
def config = configurator.config

config.jdbcUrl = 'jdbc:mysql://localhost:3306/mdata?user=root&password=root'

//config.jdbcUrl = 'jdbc:mysql://172.16.108.101:3306/mtax?user=infoset&password=a';

//config.jdbcUrl = 'jdbc:mysql://127.0.0.1:3306/liuhe?userSgt=r1&password=a';

//config.jdbcUrl = 'jdbc:mysql://120.25.152.16:3306/hexi?userSgt=hexi&password=Bigdata_1';


config.driverClass = 'com.mysql.jdbc.Driver'
config.useValueGenerator = true

println ''
def schemaReader = new SchemaReader(config)
def tableInfos = schemaReader.read()

def codeWriter = new CodeWriter(config, tableInfos)
codeWriter.write()

println "codes generated in folder ${new File('output').absolutePath}"
println "\ndone."
