package com.gpdi.mdata.sys.utils;

import pub.functions.StrFuncs;
import pub.support.Base64;
import sun.java2d.pipe.SolidTextRenderer;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

/**
 * Created by lenovo on 2017/3/14.
 */
public class Base64Util {
    private final static char[] mChars = "0123456789ABCDEF".toCharArray();
    private final static String mHexStr = "0123456789ABCDEF";
    private static final String CODE_TYPE = "utf-8";//文件名base64编码 类型

    private static BASE64Encoder encoder = new BASE64Encoder();
    private static BASE64Decoder decoder = new BASE64Decoder();

    /**
     * 字符串转换成十六进制字符串
     * @param str String 待转换的ASCII字符串
     * @return String 每个Byte之间空格分隔，如: [61 6C 6B]
     */
    private static String str2HexStr(String str){
        StringBuilder sb = new StringBuilder();
        byte[] bs = str.getBytes();

        for (int i = 0; i < bs.length; i++){
            sb.append(mChars[(bs[i] & 0xFF) >> 4]);
            sb.append(mChars[bs[i] & 0x0F]);
            //sb.append(' ');
        }
        return sb.toString().trim();
    }

    /**
     * 十六进制字符串转换成 ASCII字符串
     * @param hexStr String Byte字符串
     * @return String 对应的字符串
     */
    private static String hexStr2Str(String hexStr){
        //hexStr = hexStr.toString().trim().replace(" ", "").toUpperCase();
        char[] hexs = hexStr.toCharArray();
        byte[] bytes = new byte[hexStr.length() / 2];
        int iTmp = 0x00;

        for (int i = 0; i < bytes.length; i++){
            iTmp = mHexStr.indexOf(hexs[2 * i]) << 4;
            iTmp |= mHexStr.indexOf(hexs[2 * i + 1]);
            bytes[i] = (byte) (iTmp & 0xFF);
        }
        return new String(bytes);
    }

    public static String encode(String pwd){
        if(StrFuncs.isEmpty(pwd)){
            return null;
        }
        try {
            String passwd = encoder.encode(pwd.getBytes(CODE_TYPE));
            passwd = str2HexStr(passwd);
            return passwd;
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }

    }

    public static String decode(String pwd){
        if(StrFuncs.isEmpty(pwd)){
            return null;
        }
        try {
            String passwd = hexStr2Str(pwd);
            byte[] bytes = decoder.decodeBuffer(passwd);
            passwd = new String(bytes,CODE_TYPE);
            return passwd;
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }
    }


    public static void main(String [] args){
        String str = "59577873646D6C6C64794641497A4D794D513D3D";
        String s = Base64Util.decode(str);
        System.out.println(s);
    }
}

