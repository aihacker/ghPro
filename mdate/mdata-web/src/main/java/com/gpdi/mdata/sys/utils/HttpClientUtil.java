package com.gpdi.mdata.sys.utils;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.net.URI;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Map;

/**
 * Created by lihz on 2017/5/5.
 */
public class HttpClientUtil {
    public static String get(String url, Map<String,String> params) {
        CloseableHttpClient httpclient = HttpClients.createDefault();
        if(url == null || url.length() ==0){
            return null;
        }
        try {
            if(params != null){
                StringBuilder builder = new StringBuilder();
                for (Map.Entry<String,String> entry : params.entrySet()) {
                    String key = entry.getKey();
                    String val = entry.getValue();
                    if(key!=null && val!=null){
                        builder.append(key)
                                .append("=")
                                .append(URLEncoder.encode(val,"utf-8"))
                                .append("&");
                    }
                }
                if(builder.length() > 0){
                    url += "?" + builder.substring(0,builder.length()-1);
                    System.out.println("httpClient url : " + url);
                }
            }
        }catch (Exception e){
            System.out.println(e.getMessage());
        }

        //System.out.println("url : " + url);
        CloseableHttpResponse response = null;
        try {
            HttpGet httpget = new HttpGet(url);
            response = httpclient.execute(httpget);
            HttpEntity httpEntity = response.getEntity();
            String result = EntityUtils.toString(httpEntity);
            return result;
        }catch (Exception e){
            e.printStackTrace();
            System.out.println(e.getMessage());
            return null;
        }finally {
            try {
                response.close();
                httpclient.close();
            }catch (Exception e){
            }
        }

    }
}
