package com.libs.common.ffmpeg;

import java.io.*;
import java.util.List;

/**
 * Created by Administrator on 2017/4/28.
 */
public class VideoUtils {

    public static final String CMD = "C:\\ffmpeg\\bin\\ffmpeg.exe";

    public static String getFfmpegPath() {
        try {
            Process process = Runtime.getRuntime().exec("where " + CMD);
            InputStream is = process.getInputStream();
            InputStream erroIn = process.getErrorStream();
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            BufferedReader errorBr = new BufferedReader(new InputStreamReader(erroIn));
            String path = br.readLine();
            String error = errorBr.readLine();

            System.out.println(error);
            br.close();
            errorBr.close();
            return path;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static boolean getVideoImage(String path, String outImagePath) {
        File file = new File(path);
        if (!file.exists()) {//判断文件是否存在
            System.out.println("[" + path + "]视频文件不存在!");
            return false;
        }
        //设置参数
        List<String> commands = new java.util.ArrayList<String>();
        commands.add(CMD);//这里设置ffmpeg.exe存放的路径
        commands.add("-i");
        commands.add(path);//这里是设置要截取缩略图的视频的路径
        commands.add("-y");
        commands.add("-f");
        commands.add("image2");
//        commands.add("-ss");
//        commands.add("10");//这里设置的是要截取视频开始播放多少秒后的图，可以自己设置时间
        commands.add("-t");
        commands.add("0.001");
        commands.add("-s");
        commands.add("320x240");//这里设置输出图片的大小
        commands.add(outImagePath);//这里设置输出的截图的保存路径
        try {
            //截取缩略图并保存
            ProcessBuilder builder = new ProcessBuilder();
            builder.command(commands);
            builder.start();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
