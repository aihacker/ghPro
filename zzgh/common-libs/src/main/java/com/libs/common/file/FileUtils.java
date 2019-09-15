package com.libs.common.file;

import com.libs.common.type.TypeUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by XDLK on 2016/7/28.
 * <p>
 * Date： 2016/7/28
 */
public class FileUtils {

    public static final int cache = 10 * 1024;

    public static boolean isExist(File file) {
        if (file == null) {
            return false;
        }
        return file.exists();
    }

    public static String suffix(String name) {
        if (TypeUtils.empty(name) || name.lastIndexOf(".") == -1) return "";
        return name.substring(name.lastIndexOf("."));
    }

    public static boolean delete(File file) {
        if (file == null || !file.exists()) {
            return true;
        }
        return file.delete();
    }

    public static File getTemp() {
        String floader = System.getProperty("java.io.tmpdir");
        return new File(floader);
    }

    /**
     * 将流写入到文件中
     *
     * @param in
     * @param file 待保存文件的路径
     * @throws IOException
     */
    public static void toFile(InputStream in, File file) throws Exception {
//        if (file.exists()) {
//            throw new Exception("文件已经存在");
//        }

        if (!file.getParentFile().exists()) {
            file.getParentFile().mkdirs();
        }
        file.createNewFile();

        FileOutputStream out = new FileOutputStream(file);

        byte[] bytes = new byte[cache];
        int len = 0;
        while ((len = in.read(bytes)) != -1) {
            out.write(bytes, 0, len);
        }
        in.close();
        out.flush();
        out.close();
    }
}
