package com.gpdi.mdata.web.utils;

import java.io.*;

/**
 * @description:
 * @author: WangXiaoGang
 * @data: Created in 2018/9/12 2:08
 * @modifier:读取text文件
 */
public class ReadTextUtil {
    public static String readtext(String pathName){
        String line ="";
        try {
            File filename = new File(pathName);//要读取以上路径的文件
            InputStreamReader reader = new InputStreamReader(new FileInputStream(filename));//建立一个输入流对象reader
            BufferedReader bf = new BufferedReader(reader);//建立一个对象,他把文件内容转换成计算机能读懂的语言

            line = bf.readLine();//一次读入一行数据
            while (line !=null){
                line = bf.readLine();
            }
//====================写入text文件=====================
            File writename = new File("D:/2.txt");//绝对路径
            BufferedWriter out = new BufferedWriter(new FileWriter(writename));
            out.write("我会写入文件啦\r\n");//// \r\n即为换行
            out.write(line);//// \r\n即为换行
            out.flush();//把缓存区内容压入文件
            out.close();//最后记得关闭文件
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {

        }
        return line;
    }
}
