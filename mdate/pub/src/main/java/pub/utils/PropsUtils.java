package pub.utils;

import pub.functions.IoFuncs;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;

/**
 * describe: Created by IntelliJ IDEA.
 * @author zzl
 * @version 12-7-9
 */
public class PropsUtils {

    private static Map<Class, PropsUtils> instanceMap = new ConcurrentHashMap<Class, PropsUtils>();
    private String filePath;
    private Properties props;
    private long lastModified;
    private long lastCheckTime;

    private static final long CHECK_MODIFIED_INTERVAL = 1000 * 5;

    public PropsUtils(Class clazz) {
        this.props = loadProperties(clazz);
    }

    private Properties loadProperties(Class clazz) {
        Properties props = new Properties();
        URL resource = clazz.getResource(clazz.getSimpleName() + ".xml");

        //
        filePath = resource.getPath();
        if (filePath.indexOf('!') != -1) {
            filePath = null;
        }
        else {
            lastModified = new File(filePath).lastModified();
            lastCheckTime = System.currentTimeMillis();
        }

        //
        InputStream is = null;
        try {
            is = resource.openStream();
            props.loadFromXML(is);
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
        finally {
            IoFuncs.tryClose(is);
        }
        return props;
    }

    public static PropsUtils _for(Object obj) {
        Class tClazz = obj.getClass();
        PropsUtils instance = instanceMap.get(tClazz);
        if (instance == null || instance.checkChange()) {
            instance = new PropsUtils(tClazz);
            instanceMap.put(tClazz, instance);
        }
        return instance;
    }

    private boolean checkChange() {
        if (lastModified == 0 || System.currentTimeMillis() - lastCheckTime < CHECK_MODIFIED_INTERVAL) {
            return false;
        }
        lastCheckTime = System.currentTimeMillis();
        long tLastModified = new File(filePath).lastModified();
        if (tLastModified != lastModified) {
            lastModified = tLastModified;
            return true;
        }
        return false;
    }

    public String get(String key) {
        return props.getProperty(key);
    }
}
