package com.libs.common.http;


import com.libs.common.json.JsonUtils;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

/**
 * Created by zzl on 2016/5/11.
 */
public class HttpUtils {

    public static String get(String url) {
        try {
            return _get(url);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static String _get(String url) throws Exception {
        CloseableHttpClient httpclient = HttpClients.createDefault();
        HttpGet httpGet = new HttpGet(url);
        CloseableHttpResponse httpResponse = httpclient.execute(httpGet);

        String result = null;
        try {
//            System.out.println(httpResponse.getStatusLine());
            HttpEntity httpEntity = httpResponse.getEntity();
            result = EntityUtils.toString(httpEntity);
        } finally {
            httpResponse.close();
        }
        return result;
    }

    public static String post(String url, Object params) {
        String result = null;
        try {
            result = _post(url, params);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return result;
    }

    private static String _post(String url, Object params) throws Exception {
        HttpPost httpPost = new HttpPost(url);

        String paramsJson = null;
        if (params instanceof String) {
            paramsJson = (String) params;
        } else {
            paramsJson = JsonUtils.stringfy(params);
        }

        StringEntity entity = new StringEntity(paramsJson, "UTF-8");
        httpPost.setEntity(entity);

        CloseableHttpClient httpclient = HttpClients.createDefault();
        CloseableHttpResponse httpResponse = httpclient.execute(httpPost);

        String result = null;
        try {
            HttpEntity httpEntity = httpResponse.getEntity();
            if (httpEntity != null) {
                result = EntityUtils.toString(httpEntity, "UTF-8");
            }
        } finally {
            httpResponse.close();
        }
        return result;
    }
}
