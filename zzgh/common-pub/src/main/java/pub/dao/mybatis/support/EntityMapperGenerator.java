package pub.dao.mybatis.support;

import org.apache.commons.io.IOUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.ibatis.builder.xml.XMLMapperBuilder;
import org.apache.ibatis.executor.ErrorContext;
import org.apache.ibatis.session.Configuration;
import org.springframework.core.NestedIOException;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.core.type.classreading.CachingMetadataReaderFactory;
import org.springframework.core.type.classreading.MetadataReader;
import org.springframework.core.type.classreading.MetadataReaderFactory;

import java.io.*;
import java.util.Date;

/**
 * describe: Created by IntelliJ IDEA.
 *
 * @author zzl
 * @version 12-5-4
 */
@SuppressWarnings("unchecked")
public class EntityMapperGenerator {

    protected final Log log = LogFactory.getLog(getClass());

    private String[] basePackages;
    private Configuration configuration;

    private ResourcePatternResolver resourcePatternResolver;
    private MetadataReaderFactory metadataReaderFactory;

    private File tempDir;

    public EntityMapperGenerator() {
        resourcePatternResolver = new PathMatchingResourcePatternResolver();
        metadataReaderFactory = new CachingMetadataReaderFactory(resourcePatternResolver);
        tempDir = getTempDir();
    }

    public String[] getBasePackages() {
        return basePackages;
    }

    public void setBasePackages(String[] basePackages) {
        this.basePackages = basePackages;
    }

    public void setConfiguration(Configuration configuration) {
        this.configuration = configuration;
    }

    public void generate() {
        try {
            for (String basePackage : basePackages) {
                String classPattern = "classpath*:" + basePackage.replace('.', '/') + "/*.class";
                generate(classPattern);
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    private void generate(String resourcePattern) throws Exception {
        Resource[] resources = resourcePatternResolver.getResources(resourcePattern);
        for (Resource resource : resources) {
            MetadataReader metadataReader = metadataReaderFactory.getMetadataReader(resource);
            String className = metadataReader.getClassMetadata().getClassName();
            Class clazz = Class.forName(className);

            File mappingFile = generate(clazz);
            if (mappingFile == null) {
                continue;
            }

            //
            InputStream is = null;
            String path = mappingFile.getAbsolutePath();
            try {
                is = new FileInputStream(mappingFile);
                XMLMapperBuilder xmlMapperBuilder = new XMLMapperBuilder(is,
                        configuration, path, configuration.getSqlFragments());
                xmlMapperBuilder.parse();
            } catch (Exception e) {
                throw new NestedIOException("Failed to parse mapping resource: '" + path + "'", e);
            } finally {
                IOUtils.closeQuietly(is);
                ErrorContext.instance().reset();
            }
        }
    }

    private File generate(Class entityClass) throws Exception {
        String fieldsSql = entityClass.getName() + ".fields";
        if (configuration.getSqlFragments().containsKey(fieldsSql)) {
            //already mapped
            return null;
        }
        EntityMappingAnalyzer analyzer = new EntityMappingAnalyzer(entityClass);
        if (!analyzer.analyze()) {
            return null;
        }

//        configuration.getTypeAliasRegistry().registerAlias(
//                analyzer.getEntityName(), analyzer.getEntityClass());

        File mappingFile = new File(tempDir, entityClass.getName() + ".xml");

        //
        log.debug("generating mapping file " + mappingFile.getAbsolutePath() + "..");

        OutputStream os = new BufferedOutputStream(new FileOutputStream(mappingFile));
        PrintWriter out = new PrintWriter(new OutputStreamWriter(os, "utf-8"));
        try {
            write(out, analyzer);
        } finally {
            out.close();
        }
        return mappingFile;
    }

    private void write(PrintWriter out, EntityMappingAnalyzer analyzer) {
        out.println("<?xml version=\"1.0\" encoding=\"UTF-8\" ?>");
        out.println("<!-- generated at " + new Date() + " -->");
        out.println("<!DOCTYPE mapper\r\n" +
                "    PUBLIC \"-//mybatis.org//DTD Mapper 3.0//EN\"\r\n" +
                "    \"http://mybatis.org/dtd/mybatis-3-mapper.dtd\">");
        out.println("<mapper namespace=\"" + analyzer.getEntityClass().getName() + "\">");

        writeFields(out, analyzer);
        writeResultMap(out, analyzer);
        writeSelect(out, analyzer);
        writeInsert(out, analyzer);
        writeInsertWithId(out, analyzer);
        writeUpdate(out, analyzer);
        writeDelete(out, analyzer);
        writeUpdate2(out, analyzer);

        out.println("</mapper>");
    }

    private void writeUpdate2(PrintWriter out, EntityMappingAnalyzer analyzer) {
        out.println("<update id=\"update2\" parameterType=\"" +
                analyzer.getEntityClass().getName() + "\">");

        out.println("update " + analyzer.getTableName());
        out.println("<set>");

        for (int n = 0; n < analyzer.getColumnMappings().size(); n++) {
            ColumnMapping columnMapping = analyzer.getColumnMappings().get(n);
            if (columnMapping == analyzer.getIdMapping()) {
                continue;
            }
            out.println("<if test=\"" + columnMapping.property + " != null\">");
            out.println(columnMapping.column + " = #{" + columnMapping.property + "},");
            out.println("</if>");
        }

        out.println("</set>");
        out.println(" where ${whereName} = #{whereValue}");
        out.println("</update>");
    }

    private void writeDelete(PrintWriter out, EntityMappingAnalyzer analyzer) {
        out.println("<delete id=\"delete\" parameterType=\"list\">");
        out.println("delete from " + analyzer.getTableName() +
                " where " + analyzer.getIdMapping().column + " in");
        out.println("<foreach item=\"item\" index=\"index\" collection=\"list\"");
        out.println("\topen=\"(\" separator=\", \" close=\")\">#{item}</foreach>");
        out.println("</delete>");
    }

    private void writeUpdate(PrintWriter out, EntityMappingAnalyzer analyzer) {
        out.println("<update id=\"update\" parameterType=\"" +
                analyzer.getEntityClass().getName() + "\">");

        out.println("update " + analyzer.getTableName() + " set");

        boolean firstCol = true;
        for (int n = 0; n < analyzer.getColumnMappings().size(); n++) {
            ColumnMapping columnMapping = analyzer.getColumnMappings().get(n);
            if (columnMapping == analyzer.getIdMapping()) {
                continue;
            }
            if (!firstCol) {
                out.print(", ");
            }
            out.print(columnMapping.column + " = #{" + columnMapping.property + "}");
            firstCol = false;
        }
        ColumnMapping idMapping = analyzer.getIdMapping();
        out.println(" where " + idMapping.column + " = #{" + idMapping.property + "}");
        out.println("</update>");
    }

    private void writeInsert(PrintWriter out, EntityMappingAnalyzer analyzer) {
        out.print("<insert id=\"insert\" parameterType=\"" +
                analyzer.getEntityClass().getName() + "\"");

        if (analyzer.isIdGenerated() && analyzer.isIdIsIdentity()) {
            out.print(" useGeneratedKeys=\"true\" keyProperty=\"" + analyzer.getIdMapping().property + "\"");
        }
        out.println(">");
        if (!analyzer.isIdGenerated()) {
            out.println("<selectKey keyProperty=\"" + analyzer.getIdMapping().property + "\" " +
                    "resultType=\"" + analyzer.getIdJavaType() + "\" " + "order=\"BEFORE\">");
            out.println("select seq_" + analyzer.getTableName() + ".nextval from dual");
            out.println("</selectKey>");
        }
//        out.println("insert into " + analyzer.getTableName() + " (<include refid=\"fields\"/>) values(");
        out.print("insert into " + analyzer.getTableName());

        boolean firstCol = true;
        String fields = "";
        String values = "";
        for (int n = 0; n < analyzer.getColumnMappings().size(); n++) {
            ColumnMapping columnMapping = analyzer.getColumnMappings().get(n);
            if (columnMapping == analyzer.getIdMapping() && analyzer.isIdIsIdentity()) {
                // skip id fields
                continue;
            }
            if (!firstCol) {
                fields += ", ";
                values += ", ";
            }
            fields += columnMapping.column;
            values += "#{" + columnMapping.property + "}";
            firstCol = false;
        }
        out.println("(" + fields + ")");
        out.println(" values (" + values + ")");

        out.println("\r\n</insert>");
    }

    private void writeInsertWithId(PrintWriter out, EntityMappingAnalyzer analyzer) {
        out.print("<insert id=\"insertWithId\" parameterType=\"" +
                analyzer.getEntityClass().getName() + "\">");
        out.print("insert into " + analyzer.getTableName());

        boolean firstCol = true;
        String fields = "";
        String values = "";
        for (int n = 0; n < analyzer.getColumnMappings().size(); n++) {
            ColumnMapping columnMapping = analyzer.getColumnMappings().get(n);
            if (!firstCol) {
                fields += ", ";
                values += ", ";
            }
            fields += columnMapping.column;
            values += "#{" + columnMapping.property + "}";
            firstCol = false;
        }
        out.println("(" + fields + ")");
        out.println(" values (" + values + ")");

        out.println("\r\n</insert>");
    }

    private void writeSelect(PrintWriter out, EntityMappingAnalyzer analyzer) {
        out.println("<select id=\"get\" parameterType=\"" + analyzer.getIdJavaType() + "\" " +
                "resultMap=\"" + analyzer.getEntityName() + "ResultMap\">");
        out.println("select * from " + analyzer.getTableName() +
                " where " + analyzer.getIdMapping().column + " = #{value}");
        out.println("</select>");

        out.println("<select id=\"getAll\" parameterType=\"" + analyzer.getIdJavaType() + "\" " +
                "resultMap=\"" + analyzer.getEntityName() + "ResultMap\">");
        out.print("select ");

        out.println("* from " + analyzer.getTableName());
        out.println("<if test=\"value != null\">order by ${value}</if>");

        out.println(" limit 65535");

        out.println("</select>");
    }

    private void writeResultMap(PrintWriter out, EntityMappingAnalyzer analyzer) {
        String entityName = analyzer.getEntityName();
        out.println("<resultMap id=\"" + entityName + "ResultMap\" type=\"" +
                analyzer.getEntityClass().getName() + "\">");

        ColumnMapping idMapping = analyzer.getIdMapping();
        out.println("<id property=\"" + idMapping.property + "\" column=\"" + idMapping.column + "\"/>");
        for (ColumnMapping columnMapping : analyzer.getColumnMappings()) {
            if (columnMapping == idMapping) {
                continue;
            }
            String column = columnMapping.column;
            if (column.indexOf('_') == -1) {
                continue;
            }
            out.println("<result property=\"" + columnMapping.property + "\" column=\"" + column + "\"/>");
        }
        out.println("</resultMap>");
    }

    private void writeFields(PrintWriter out, EntityMappingAnalyzer analyzer) {
        out.println("<sql id=\"fields\">");
        for (int n = 0; n < analyzer.getColumnMappings().size(); n++) {
            ColumnMapping columnMapping = analyzer.getColumnMappings().get(n);
            if (n > 0) {
                out.print(", ");
            }
            out.print(columnMapping.column);
        }
        out.println("\r\n</sql>");
    }

    private File getTempDir() {
        File baseDir = new File(System.getProperty("java.io.tmpdir"));
        File tempDir = new File(baseDir, "wxgh/mybatis");
        if (!tempDir.exists() && !tempDir.mkdirs()) {
            throw new RuntimeException("Failed to create temp dir " + tempDir.getAbsolutePath());
        }
        return tempDir;
    }

}
