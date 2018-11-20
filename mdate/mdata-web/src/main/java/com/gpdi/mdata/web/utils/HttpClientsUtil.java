package com.gpdi.mdata.web.utils;

import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;

/**
 * @description:发起http请求,并获取返回结果
 * @author: WangXiaoGang
 * @data: Created in 2018/8/6 16:01
 * @modifier:
 */
public class HttpClientsUtil {
    public static String get(String param,String URL) {
        CloseableHttpClient httpclient = HttpClients.createDefault();
        String obj=null;
        try {
            HttpUriRequest httpget = new HttpGet(URL+"?"+param);
            // 执行get请求.
            CloseableHttpResponse response = httpclient.execute(httpget);
            try {
                // 获取响应实体
                HttpEntity entity = response.getEntity();
                // 打印响应状态
                if (entity != null) {
                    obj= EntityUtils.toString(entity);
                }
            } finally {
                response.close();
            }
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            // 关闭连接,释放资源
            try {
                httpclient.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return obj;
    }

    public static void main(String[] args) {
        String url = "http://127.0.0.1:99/pachong";
        String param = 	"comName=广东省电信规划设计院有限公司";
        String result = get(param,url);
        System.out.println("+++++++++++++:"+result);
    }
}
