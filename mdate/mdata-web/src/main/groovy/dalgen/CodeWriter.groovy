package dalgen

import dalgen.model.EntityInfo
import dalgen.model.TableInfo
import org.apache.velocity.VelocityContext
import org.apache.velocity.app.Velocity
import org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader

import java.text.SimpleDateFormat

class CodeWriter {

    ConfigObject config
    List<TableInfo> tableInfos

    CodeWriter(config, tableInfos) {
        this.config = config
        this.tableInfos = tableInfos
    }

    def write() {
        def p = new Properties()
        p.setProperty('resource.loader', 'class')
        p.setProperty("class.resource.loader.class", ClasspathResourceLoader.class.name)
        Velocity.init(p)

        def tableEntityMapper = new TableEntityMapper()
        tableInfos.each {
            println "processing ${it.name}.."
            _write it, tableEntityMapper.map(it)
        }
    }
    
    private _write(TableInfo tableInfo, EntityInfo entityInfo) {
        def context = newContext(tableInfo, entityInfo)

        _write(context, 'entity', "${entityInfo.name}.java")
        _write(context, 'dao', "${entityInfo.name}Dao.java")
        _write(context, 'daoimpl', "${entityInfo.name}DaoImpl.java")
        _write(context, 'sql', "${entityInfo.name}.xml")

    }

    private _write(VelocityContext context, templateType, String fileName) {
        def sw = new StringWriter()
        Velocity.mergeTemplate("/dalgen/vm/${templateType}.vm", context, sw)

        def dir = new File('output', "${config.package.replace('.', '/')}/${templateType}")
        dir.mkdirs()
        def file = new File(dir, fileName)
        file.write(sw.toString())
    }

    private VelocityContext newContext(tableInfo, entityInfo) {
        def context = new VelocityContext()
        context.put('table', tableInfo)
        context.put('entity', entityInfo)
        context.put('config', config)
        def today = new SimpleDateFormat('MMM d, yyyy', Locale.ENGLISH).format(new Date())
        context.put('today', today)
        context.put('newline', '\r\n')
        context.put('tab', '    ')
        context.put('dollar', '$')
        return context
    }

}