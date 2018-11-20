package pub.dao.mybatis.support;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.ibatis.builder.xml.XMLMapperEntityResolver;
import org.apache.ibatis.parsing.XNode;
import org.apache.ibatis.parsing.XPathParser;
import org.apache.ibatis.type.TypeAliasRegistry;
import org.springframework.core.io.Resource;
import pub.functions.StrFuncs;
import pub.types.AbortException;

import javax.persistence.Entity;
import java.io.InputStream;
import java.util.*;

/**
 * describe: Created by IntelliJ IDEA.
 * @author zzl
 * @version 12-2-12
 */
public class EntityAliasRegistrar {

    protected final Log logger = LogFactory.getLog(getClass());
    private List<String> entityClasses = new ArrayList<String>();

    public EntityAliasRegistrar() {
        
    }

    public void process(Resource mapperLocation) throws Exception {
        logger.debug("processing " + mapperLocation.getURL() + "..");

        InputStream is = mapperLocation.getInputStream();
        XPathParser parser = new XPathParser(is, false, new Properties(),
                new XMLMapperEntityResolver());
        try {
            XNode node = parser.evalNode("/mapper");
            String namespace = node.getStringAttribute("namespace");
            if (namespace.contains(".entity.")) {
                entityClasses.add(namespace);
            }
        } finally {
            is.close();
        }
    }

    public void register(Resource[] mapperLocations, TypeAliasRegistry typeAliasRegistry) {
//        if(true) {
//            return;
//        }
        for (Resource mapperLocation : mapperLocations) {
            try {
                process(mapperLocation);
            } catch (AbortException e) {
                // do nothing
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        Map<String, Class<?>> typeAliases = typeAliasRegistry.getTypeAliases();
        for (String entityClass : entityClasses) {

            int lastDotPos = entityClass.lastIndexOf('.');
            String alias = entityClass.substring(lastDotPos + 1);

            String declaredEntityName = getDeclaredEntityName(entityClass);
            if(declaredEntityName != null && declaredEntityName.length() != 0) {
                alias = declaredEntityName;
            }

            if(typeAliases.containsKey(alias.toLowerCase())) {
                logger.warn("ignoring duplicate alias for " + entityClass + "..");
            }
            else {
                typeAliasRegistry.registerAlias(alias, entityClass);
            }

            // register module qualifed alias
            if(!entityClass.substring(0, lastDotPos).endsWith(".sys.entity")) {

                String sFind = ".sys.";
                int sysEndPos = entityClass.indexOf(sFind) + sFind.length();

                alias = entityClass.substring(sysEndPos).replace(".entity.", ".");
                typeAliasRegistry.registerAlias(alias, entityClass);
            }
        }
    }

    private String getDeclaredEntityName(String entityClass) {
        Class clazz = null;
        try {
            clazz = Class.forName(entityClass);
        }
        catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        Entity entityAnn = (Entity) clazz.getAnnotation(Entity.class);
        return entityAnn.name();
    }
}
