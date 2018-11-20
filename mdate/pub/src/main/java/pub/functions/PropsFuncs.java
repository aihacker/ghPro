package pub.functions;

import java.io.*;
import java.net.URL;
import java.util.Properties;

/**
 * describe: Created by IntelliJ IDEA.
 * @author zhuangzhonglong
 * @time 17:55:30
 * @since 2008-11-4
 */
public class PropsFuncs {

    public static String getProperty(String[] propertiesFileCpPaths, String key) {
        for (String path : propertiesFileCpPaths) {
            String value = getProperty(path, key);
            if (value != null) {
                return value;
            }
        }
        return null;
    }

    private static Properties getProperties(String propertiesFilePath) {
        InputStream is = null;
        try {
            File file = new File(propertiesFilePath);
            if (file.exists()) {
                is = new FileInputStream(file);
            }
            else {
                propertiesFilePath = makeFullPath(propertiesFilePath);

                URL resource = PropsFuncs.class.getClassLoader().getResource(propertiesFilePath);
                if (resource == null) {
//                System.out.println("NOT FOUND: " + propertiesFilePath);
                    return null;
                }
                else {
//                System.out.println("FOUND: " + resource);
                }
                is = resource.openStream();
            }
            try {
                Reader reader = new InputStreamReader(is, "utf-8");
                Properties properties = new Properties();
                properties.load(reader);
                reader.close();
                return properties;
            }
            finally {
                is.close();
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String getProperty(File file, String key) {
        return getProperty(file.getPath(), key);
    }

    public static String getProperty(String propertiesFilePath, String key) {
        Properties properties = getProperties(propertiesFilePath);
        if(properties != null) {
            return properties.getProperty(key);
        }
        else {
            return null;
        }
    }

    private static String makeFullPath(String propertiesFileCpPath) {
        if (propertiesFileCpPath.endsWith(".properties")) {
            return propertiesFileCpPath;
        }
        String localPropertiesFileCpPath = propertiesFileCpPath + ".local.properties";
        if (PropsFuncs.class.getClassLoader().getResource(localPropertiesFileCpPath) != null) {
            propertiesFileCpPath = localPropertiesFileCpPath;
        }
        else {
            propertiesFileCpPath = propertiesFileCpPath + ".properties";
        }

        return propertiesFileCpPath;
    }

    public static boolean getBool(String propertiesFileCpPath, String key, boolean defaultValue) {
        String prop = getProperty(propertiesFileCpPath, key);
        if (prop == null || prop.length() == 0) {
            return defaultValue;
        }
        return "1".equals(prop) ||
                "true".equalsIgnoreCase(prop) ||
                "y".equalsIgnoreCase(prop) ||
                "yes".equalsIgnoreCase(prop);
    }

    public static void setProperty(File propertiesFile, String key, String value) {
        setProperty(propertiesFile.getPath(), key, value);
    }

    public static void setProperty(String propertiesFilePath, String key, String value) {
        Properties properties = getProperties(propertiesFilePath);
        if(properties == null) {
            properties = new Properties();
        }
        properties.setProperty(key, value);
        File file = new File(propertiesFilePath);
        try {
            FileOutputStream fos = new FileOutputStream(file);
            OutputStreamWriter writer = new OutputStreamWriter(fos, "utf-8");
            properties.store(writer, "");
            writer.close();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}
