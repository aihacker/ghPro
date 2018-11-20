package com.gpdi.mdata.web.utils;

import net.sf.json.JSONException;
import net.sf.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.charset.Charset;

/**
 * @description:<p>类描述：接口读取工具。</p>
 * @author: WangXiaoGang
 * @data: Created in 2018/7/31 1:39
 * @modifier:返回json类型的
 */
public class UrlJsonUtil {
    public static JSONObject readJsonFromUrl(String url) throws IOException, JSONException {
        InputStream is = new URL(url).openStream();
        try {
            BufferedReader rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
            StringBuilder sb = new StringBuilder();
            int cp;
            while ((cp = rd.read()) != -1) {
                sb.append((char) cp);
            }
            String jsonText = sb.toString();
            JSONObject json = JSONObject.fromObject(jsonText);
            return json;
        } finally {
            is.close();
        }
    }

    public static void main(String[] args) throws Exception {

        String urlStr = "127.0.0.1:99/pachong?comName=广东省电信规划设计院有限公司";
        JSONObject json = UrlJsonUtil.readJsonFromUrl(urlStr);
        System.out.println(json.toString());



    }
}
