package dalgen
/**
 * describe: Created by IntelliJ IDEA.
 * @author zzl
 * @version 12-2-10
 */

import groovy.util.logging.Log

@Log
class Configurator {

    BufferedReader reader = System.in.newReader()
    ConfigObject config

    Configurator(String configFileName) {

        def configFile = configFileName? new File(configFileName): getTempConfigFile()
        config = new ConfigSlurper().parse(configFile.toURL())

        if(!configFileName) {
            interactiveConfig()
            configFile.withWriter {
                config.writeTo(it)
            }
        }
    }

    private interactiveConfig() {
//        readOption('tnsName');
//        readOption('userName');
//        readOption('password');

        print '\nPlease specify tables to generate: \n>> '
        def tables = []
        def table
        while (table = reader.readLine().trim()) {
            tables << table.toUpperCase()
            print '>> '
        }
        config.tables = tables

        readOption('author', System.getProperty("userSgt.name"))
        readOption('package', 'some_package')
        readOption('table_prefix_to_skip', '')
    }

    private getTempConfigFile() {
        def tmpDir = System.properties['java.io.tmpdir']
        def fileName = "${tmpDir}dalgen.config.groovy"
        Configurator.log.info "using config file $fileName"
        def file = new File(fileName)
        if(!file.exists()) {
            file.createNewFile()
        }
        return file
    }

    private readOption(String key, String defaultValue = '') {
        def value = config[key]?: defaultValue
        print "$key${value ? "($value)": ''}? "
        def readValue = reader.readLine().trim()
        if(readValue) {
            value = readValue
        }
        config[key] = value
    }

}