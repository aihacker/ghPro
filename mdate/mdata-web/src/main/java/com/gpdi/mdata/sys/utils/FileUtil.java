package com.gpdi.mdata.sys.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

/**
 * Created by lihz on 2017/6/1.
 */
public class FileUtil {

    /**
     *
     * @param filename 文件名
     * @param n  读取文件n行
     * @return
     */
    public static String readFile(String filename, int n){
        if(filename == null || n <= 0){
            return null;
        }
        BufferedReader reader = null;
        String line;
        StringBuilder builder = new StringBuilder();
        int count = 0;
        try {
            reader = new BufferedReader(new FileReader(filename));
            while ((line=reader.readLine()) != null && count < n){
                count++;
                builder.append(line).append("\n");
            }
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }finally {
            try{
                reader.close();
            }catch (Exception e){
            }
        }

        return builder.toString();
    }

    /**
     * 读取文件所有数据
     * @param filename 文件名
     * @return
     */
    public static String readFile(String filename){
        if(filename == null){
            return null;
        }
        BufferedReader reader = null;
        String line;
        StringBuilder builder = new StringBuilder();
        try {
            reader = new BufferedReader(new FileReader(filename));
            while ((line=reader.readLine()) != null){
                builder.append(line).append("\n");
            }
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }finally {
            try{
                reader.close();
            }catch (Exception e){
            }
        }

        return builder.toString();
    }

    /**
     * 删除文件，可以是文件或文件夹
     *
     * @param fileName
     *            要删除的文件名
     * @return 删除成功返回true，否则返回false
     */
    public static boolean delete(String fileName) {
        File file = new File(fileName);
        if (!file.exists()) {
            //System.out.println("删除文件失败:" + fileName + "不存在！");
            return false;
        } else {
            if (file.isFile())
                return deleteFile(fileName);
            else
                return deleteDirectory(fileName);
        }
    }

    /**
     * 删除单个文件
     *
     * @param fileName
     *            要删除的文件的文件名
     * @return 单个文件删除成功返回true，否则返回false
     */
    public static boolean deleteFile(String fileName) {
        File file = new File(fileName);
        // 如果文件路径所对应的文件存在，并且是一个文件，则直接删除
        if (file.exists() && file.isFile()) {
            //System.out.println("删除单个文件" + fileName + "成功！");
//System.out.println("删除单个文件" + fileName + "失败！");
            return file.delete();
        } else {
            //System.out.println("删除单个文件失败：" + fileName + "不存在！");
            return false;
        }
    }

    /**
     * 删除目录及目录下的文件
     *
     * @param dir
     *            要删除的目录的文件路径
     * @return 目录删除成功返回true，否则返回false
     */
    public static boolean deleteDirectory(String dir) {
        // 如果dir不以文件分隔符结尾，自动添加文件分隔符
        if (!dir.endsWith(File.separator))
            dir = dir + File.separator;
        File dirFile = new File(dir);
        // 如果dir对应的文件不存在，或者不是一个目录，则退出
        if ((!dirFile.exists()) || (!dirFile.isDirectory())) {
            //System.out.println("删除目录失败：" + dir + "不存在！");
            return false;
        }
        boolean flag = true;
        // 删除文件夹中的所有文件包括子目录
        File[] files = dirFile.listFiles();
        for (int i = 0; i < files.length; i++) {
            // 删除子文件
            if (files[i].isFile()) {
                flag = deleteFile(files[i].getAbsolutePath());
                if (!flag)
                    break;
            }
            // 删除子目录
            else if (files[i].isDirectory()) {
                flag = deleteDirectory(files[i]
                        .getAbsolutePath());
                if (!flag)
                    break;
            }
        }
        if (!flag) {
            //System.out.println("删除目录失败！");
            return false;
        }
        // 删除当前目录
        //System.out.println("删除目录" + dir + "成功！");
        return dirFile.delete();
    }

    public static void main(String [] args){
        String filename = "f:/test";
        delete(filename);
    }

}
