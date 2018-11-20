package com.gpdi.mdata.web.utils;

import java.io.*;

/**
 * Created by  on 2016/8/31.
 */
public final  class FileUtils {

    private  FileUtils() {}

    public static  File createDir(String filePath) {
        File file = new File(filePath);
        if(file.exists()) {
            return  file;
        }
        if (!filePath.endsWith(File.separator)) {
            filePath = filePath + File.separator;
        }
        file.mkdirs();
        return file;
    }

    public static File createFile(String filePath) {
        File file = new File(filePath);
        if(file.exists()) {

        }
        if (filePath.endsWith(File.separator)) {

        }
        if(!file.getParentFile().exists()) {
            file.getParentFile().mkdirs();
        }
        try {
            file.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return file;
    }

    public static void delAllFile(String fileDir) {
        File file = new File(fileDir);
        delAllFile(file);
    }

    public static void delAllFile(File file) {
        if (!file.exists()) {
            return;
        }

        if (file.isFile()) {
            file.delete();
        } else if (file.isDirectory()) {
            File[] files = file.listFiles();
            for (File f : files) {
                delAllFile(f);
            }
            file.delete();
        }
    }

    public static void copyFile(File source,File dest) {
        try (BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream(dest));
             BufferedInputStream  in = new BufferedInputStream(new FileInputStream(source))) {
            byte[] buff = new byte[1024 * 5];
            int len;
            while ((len = in.read(buff)) != -1) {
                out.write(buff, 0, len);
            }
            // 刷新此缓冲的输出流
            out.flush();
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}
