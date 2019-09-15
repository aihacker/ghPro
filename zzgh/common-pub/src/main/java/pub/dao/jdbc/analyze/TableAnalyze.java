package pub.dao.jdbc.analyze;

import com.libs.common.file.FileUtils;
import com.libs.common.json.JsonUtils;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.core.type.classreading.CachingMetadataReaderFactory;
import org.springframework.core.type.classreading.MetadataReader;
import org.springframework.core.type.classreading.MetadataReaderFactory;
import pub.dao.jdbc.mapper.TableMapper;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2017/7/12.
 */
public class TableAnalyze {

    private ResourcePatternResolver resourcePatternResolver;
    private MetadataReaderFactory metadataReaderFactory;

    public static Map<String, TableMapper> mapperMap;
    private String path;
    private String tablePrefix;

    static {
        mapperMap = new HashMap<>();
    }

    public TableAnalyze(String path, String tablePrefix) {
        resourcePatternResolver = new PathMatchingResourcePatternResolver();
        metadataReaderFactory = new CachingMetadataReaderFactory(resourcePatternResolver);
        this.path = path;
        this.tablePrefix = tablePrefix;
    }

    public void analyze(String[] entityPackages) {
        try {
            for (String entity : entityPackages) {
                String classPattern = "classpath*:" + entity.replace('.', '/') + "/*.class";
                analyze(classPattern);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        if (mapperMap.size() > 0) {
            File temp = path != null ? new File(path) : FileUtils.getTemp();
            if (temp.isDirectory()) {
                if (!temp.exists()) temp.mkdirs();
                temp = new File(temp, "wxgh/entity_mapper.json");
            }
            try {
                org.apache.commons.io.FileUtils.writeStringToFile(temp, JsonUtils.stringfy(mapperMap), "UTF-8");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void analyze(String classPattern) throws IOException, ClassNotFoundException {
        Resource[] resources = resourcePatternResolver.getResources(classPattern);
        for (Resource resource : resources) {
            MetadataReader metadataReader = metadataReaderFactory.getMetadataReader(resource);
            String className = metadataReader.getClassMetadata().getClassName();
            Class clazz = Class.forName(className);

            TableMapper tableMapper = EntityAnalyze.analyze(clazz, tablePrefix);
            mapperMap.put(className, tableMapper);
        }
    }
}
