package com.libs.common.ffmpeg;

import it.sauronsoftware.jave.*;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;

/**
 * @Author xlkai
 * @Version 2016/11/15
 */
public class AudioUtils {

    /**
     * 将amr转成mp3格式
     *
     * @param sourcePath
     * @param targetPath
     */
    public static void toMp3(String sourcePath, String targetPath) {
        File source = new File(sourcePath);
        File target = new File(targetPath);

        AudioAttributes audio = new AudioAttributes();
        Encoder encoder = new Encoder();

        audio.setCodec("libmp3lame");
        EncodingAttributes encodingAttributes = new EncodingAttributes();
        encodingAttributes.setFormat("mp3");
        encodingAttributes.setAudioAttributes(audio);

        try {
            encoder.encode(source, target, encodingAttributes);
        } catch (InputFormatException e) {
            e.printStackTrace();
        } catch (EncoderException e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取amr文件时长
     *
     * @param file
     * @return
     * @throws IOException
     */
    public static long getAmrDuration(File file) throws IOException {
        long duration = -1;
        int[] packedSize = {12, 13, 15, 17, 19, 20, 26, 31, 5, 0, 0, 0, 0, 0, 0, 0};
        RandomAccessFile randomAccessFile = null;
        try {
            randomAccessFile = new RandomAccessFile(file, "rw");
            long length = file.length();//文件的长度
            int pos = 6;//设置初始位置
            int frameCount = 0;//初始帧数
            int packedPos = -1;
            byte[] datas = new byte[1];//初始数据值
            while (pos <= length) {
                randomAccessFile.seek(pos);
                if (randomAccessFile.read(datas, 0, 1) != 1) {
                    duration = length > 0 ? ((length - 6) / 650) : 0;
                    break;
                }
                packedPos = (datas[0] >> 3) & 0x0F;
                pos += packedSize[packedPos] + 1;
                frameCount++;
            }
            duration += frameCount * 20;//帧数*20
        } finally {
            if (randomAccessFile != null) {
                randomAccessFile.close();
            }
        }
        return duration;
    }
}
