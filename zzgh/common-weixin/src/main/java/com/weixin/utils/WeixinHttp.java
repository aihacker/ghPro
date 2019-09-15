package com.weixin.utils;

import com.libs.common.file.FileUtils;
import com.libs.common.http.HttpClient;
import com.libs.common.http.HttpException;
import com.libs.common.json.JsonUtils;
import com.libs.common.type.TypeUtils;
import com.weixin.WeixinException;
import com.weixin.bean.ErrResult;
import com.weixin.bean.FileBean;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by XLKAI on 2017/7/8.
 */
public class WeixinHttp {

    public static <T extends ErrResult> T get(String url, Class<T> tClass) throws WeixinException {
        try {
            T t = HttpClient.get(url, tClass);
            parseResult(t);
            return t;
        } catch (HttpException e) {
            throw new WeixinException(e);
        }
    }

    public static <T extends ErrResult> T post(String url, Object param, Class<T> tClass) throws WeixinException {
        try {
            T t = HttpClient.post(url, param, tClass);
            parseResult(t);
            return t;
        } catch (HttpException e) {
            throw new WeixinException(e);
        }
    }

    public static <T extends ErrResult> T upload(String url, File file, Class<T> tClass) throws WeixinException {
        try {
            T t = HttpClient.upload(url, file, tClass);
            parseResult(t);
            return t;
        } catch (HttpException e) {
            throw new WeixinException(e);
        }
    }

    public static void download(String url, File file, DownloadCallback callback) {
        try {
            CloseableHttpClient closeableHttpClient = HttpClients.createDefault();
            HttpGet httpGet = new HttpGet(url);
            CloseableHttpResponse response = closeableHttpClient.execute(httpGet);
            HttpEntity entity = response.getEntity();
            String contentType = entity.getContentType().getValue();
            if (contentType == "application/json") {
                String result = EntityUtils.toString(entity, "UTF-8");
                ErrResult errResult = JsonUtils.parseBean(result, ErrResult.class);
                parseResult(errResult);
            } else {
                InputStream in = entity.getContent();
                FileUtils.toFile(in, file);
                Header[] headers = response.getHeaders("Content-disposition");
                String filename = headers[0].getValue();
                System.out.println(filename);
                if (!TypeUtils.empty(filename)) {
                    String tip = "filename=";
                    int i = filename.indexOf(tip);
                    if (i > 0) {
                        i = i + tip.length();
                        if (i < filename.length()) {
                            filename = filename.substring(i);
                            filename = filename.replace("\"", "");
                        }
                    }
                }
                FileBean fileBean = new FileBean(filename, contentType, entity.getContentLength());
                fileBean.setSaveFile(file);
                callback.onSuccess(fileBean);
            }
        } catch (ClientProtocolException e) {
            callback.onFaile(e);
        } catch (IOException e) {
            callback.onFaile(e);
        } catch (WeixinException e) {
            callback.onError(e.getErrResult());
        } catch (Exception e) {
            callback.onFaile(e);
        }
    }

    public static <T extends ErrResult> T download(String url, File file, Class<T> tClass) throws WeixinException {
        try {
            T t = HttpClient.download(url, file, tClass);
            parseResult(t);
            return t;
        } catch (HttpException e) {
            throw new WeixinException(e);
        }
    }

    public static String download(String url, File file) {
        return HttpClient.download(url, file);
    }

    public static <T extends ErrResult> void parseResult(T t) throws WeixinException {
        if (t.getErrcode() != 0 && !"ok".equals(t.getErrmsg())) {
            throw new WeixinException(t);
        }
    }

}
