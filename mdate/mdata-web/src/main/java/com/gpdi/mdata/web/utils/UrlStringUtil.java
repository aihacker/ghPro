package com.gpdi.mdata.web.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * @description:
 * @author: WangXiaoGang
 * @data: Created in 2018/7/31 2:03
 * @modifier:调用接口获取数据
 */
public class UrlStringUtil {
        public static void main(String[] args) {

            //方法一
            String urlStr = "http://127.0.0.1:99/pachong?comName=广东省电信规划设计院有限公司";
            System.out.println((new UrlStringUtil()).getURLContent(urlStr));

            String ip = "113.57.244.100";
            String url = "http://api.map.baidu.com/location/ip?ak=32f38c9491f2da9eb61106aaab1e9739&ip="+ip+"&coor=bd09ll";
            System.out.println((new UrlStringUtil()).getURLContent(url));



        }

        public static String getURLContent() throws Exception {
            String strURL = "http://127.0.0.1:99/pachong?comName=广东省电信规划设计院有限公司";
            URL url = new URL(strURL);
            HttpURLConnection httpConn = (HttpURLConnection)url.openConnection();
            httpConn.setRequestMethod("GET");
            httpConn.connect();

            BufferedReader reader = new BufferedReader(new InputStreamReader(httpConn.getInputStream()));
            String line;
            StringBuffer buffer = new StringBuffer();
            while ((line = reader.readLine()) != null) {
                buffer.append(line);
            }
            reader.close();
            httpConn.disconnect();

            System.out.println(buffer.toString());
            return buffer.toString();
        }

        /**
         * 程序中访问http数据接口
         */
        public static String getURLContent(String urlStr) {
            /** 网络的url地址 */
            URL url = null;
            /** http连接 */
            HttpURLConnection httpConn = null;
            /**//** 输入流 */
            BufferedReader in = null;
            StringBuffer sb = new StringBuffer();
            try {

                url = new URL(urlStr);
                in = new BufferedReader(new InputStreamReader(url.openStream(), "utf-8"));
                String str = null;
                while ((str = in.readLine()) != null) {
                    sb.append(str);
                }
            } catch (Exception ex) {

            } finally {
                try {
                    if (in != null) {
                        in.close();
                    }
                } catch (IOException ex) {
                }
            }
            String result = sb.toString();
            System.out.println(result);
            return result;
        }

    }


