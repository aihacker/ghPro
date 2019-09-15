package com.libs.common.http;

import com.libs.common.file.FileUtils;
import com.libs.common.json.JsonUtils;
import com.libs.common.type.TypeUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHeaders;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;

/**
 * Created by XDLK on 2016/7/27.
 * <p>
 * Date: 2016/7/27
 */
public class HttpClient {

    private static final String boundary = "----xlkaiUploadFile";

    /**
     * get请求
     *
     * @param url
     * @return 字符串类型结果
     * @throws HttpException
     */
    public static String get(String url) throws HttpException {
        return __get(url);
    }

    /**
     * get请求
     *
     * @param url
     * @param tClass
     * @param <T>
     * @return T
     * @throws HttpException
     */
    public static <T> T get(String url, Class<T> tClass) throws HttpException {
        String response = get(url);

        T obT = JsonUtils.parseBean(response, tClass);

        if (obT == null) {
            throw new HttpException(HttpException.Code.EMPTY_RESPONSE);
        }
        return obT;
    }


    /**
     * 文件上传
     *
     * @param url
     * @param file
     * @return
     * @throws HttpException
     */
    public static String upload(String url, File file) throws HttpException {

        if (!FileUtils.isExist(file)) {
            throw new HttpException(HttpException.Code.FILE_NOT_EXIST);
        }
        return __upload(url, file);
    }

    /**
     * 文件上传
     *
     * @param url
     * @param file
     * @param tClass
     * @param <T>
     * @return
     * @throws HttpException
     */
    public static <T> T upload(String url, File file, Class<T> tClass) throws HttpException {

        String response = upload(url, file);

        T obT = JsonUtils.parseBean(response, tClass);

        if (obT == null) {
            throw new HttpException(HttpException.Code.EMPTY_RESPONSE);
        }

        return obT;
    }

    /**
     * Post请求
     *
     * @param url
     * @param object
     * @return
     * @throws HttpException
     */
    public static String post(String url, Object object) throws HttpException {
        //请求参数为空
        if (object == null) {
            throw new HttpException(HttpException.Code.EMPTY_PARAM);
        }

        String paramJson;
        if (object instanceof String) {
            paramJson = (String) object;
        } else {
            paramJson = JsonUtils.stringfy(object);
        }

        String result = __post(url, paramJson);

        if (TypeUtils.empty(result)) {
            throw new HttpException(HttpException.Code.EMPTY_RESPONSE);
        }
        return result;
    }

    /**
     * post请求
     *
     * @param url
     * @param object
     * @param tClass
     * @param <T>
     * @return
     * @throws HttpException
     */
    public static <T> T post(String url, Object object, Class<T> tClass) throws HttpException {
        String response = post(url, object);

        T obT = JsonUtils.parseBean(response, tClass);

        if (obT == null) {
            throw new HttpException(HttpException.Code.EMPTY_RESPONSE);
        }
        return obT;
    }

    /**
     * 下载文件
     *
     * @param url
     * @param file
     * @return
     */
    public static String download(String url, File file) {
        try {
            CloseableHttpClient closeableHttpClient = HttpClients.createDefault();
            HttpGet httpGet = new HttpGet(url);
            CloseableHttpResponse response = closeableHttpClient.execute(httpGet);
            HttpEntity entity = response.getEntity();
            String contentType = entity.getContentType().getValue();
            if (contentType == "application/json") {
                return EntityUtils.toString(entity, "UTF-8");
            } else {
                InputStream in = entity.getContent();
                FileUtils.toFile(in, file);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static <T> T download(String url, File file, Class<T> tClass) throws HttpException {
        String result = download(url, file);
        if (TypeUtils.empty(result)) {
            throw new HttpException(HttpException.Code.EMPTY_RESPONSE);
        }
        T t = JsonUtils.parseBean(result, tClass);
        return t;
    }

    private static String __get(String url) throws HttpException {
        CloseableHttpClient httpClient = __getClient();

        HttpGet httpGet = new HttpGet(url);

        return sendRequest(httpClient, httpGet);
    }

    private static String __upload(String url, File file) throws HttpException {
        CloseableHttpClient httpClient = __getClient();

        HttpPost httpPost = __getMultipartPost(url);

        MultipartEntityBuilder entityBuilder = MultipartEntityBuilder.create();
        entityBuilder.setBoundary(boundary)
                .setCharset(Charset.forName("UTF-8"))
                .setMode(HttpMultipartMode.BROWSER_COMPATIBLE);
        entityBuilder.addBinaryBody("media", file, ContentType.APPLICATION_OCTET_STREAM, file.getName());

        HttpEntity fileEntity = entityBuilder.build();

        httpPost.setEntity(fileEntity);

        return sendRequest(httpClient, httpPost);
    }

    private static String __post(String url, String param) throws HttpException {

        CloseableHttpClient httpClient = __getClient();
        HttpPost httpPost = new HttpPost(url);

        StringEntity stringEntity = new StringEntity(param, "UTF-8");
        httpPost.setEntity(stringEntity);

        return sendRequest(httpClient, httpPost);
    }

    private static String sendRequest(CloseableHttpClient client, HttpUriRequest request) throws HttpException {
        CloseableHttpResponse response = null;
        try {
            response = client.execute(request);
        } catch (IOException e) {
            throw new HttpException(HttpException.Code.ERR_SERVER, e);
        }
        String result = null;
        try {
            if (response.getStatusLine().getStatusCode() == 200) {
                HttpEntity entity = response.getEntity();

                if (entity != null) {
                    result = EntityUtils.toString(entity, "UTF-8");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (response != null) response.close();
                if (client != null) client.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    private static CloseableHttpClient __getClient() {
        return HttpClients.createDefault();
    }

    private static HttpPost __getMultipartPost(String url) {
        HttpPost httpPost = new HttpPost(url);
        httpPost.setHeader(HttpHeaders.CONNECTION, "keep-alive");
        httpPost.setHeader(HttpHeaders.ACCEPT, "*/*");
        httpPost.setHeader(HttpHeaders.CONTENT_TYPE, "multipart/form-data; boundary=" + boundary);
        return httpPost;
    }
}
