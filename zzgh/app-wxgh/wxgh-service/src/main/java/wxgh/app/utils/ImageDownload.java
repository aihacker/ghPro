package wxgh.app.utils;

import com.weixin.bean.FileBean;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by Administrator on 2017/8/2.
 */
public class ImageDownload {

    public static FileBean download(String url, File toFile) {
        try {
            String filename = "";
            if (url.lastIndexOf(".") > 0) {
                filename = url.substring(url.lastIndexOf("/"));
            }

            CloseableHttpClient httpClient = HttpClients.createDefault();
            HttpGet get = new HttpGet(url);
            CloseableHttpResponse response = httpClient.execute(get);

            if (response.getStatusLine().getStatusCode() >= 400) {
                throw new IOException("Got bad response, error code = " + response.getStatusLine().getStatusCode()
                        + " downloadUrl: " + url);
            }
            HttpEntity entity = response.getEntity();
            if (entity != null) {
                FileBean fileBean = new FileBean(filename, entity.getContentType().getValue(), entity.getContentLength());
                InputStream in = entity.getContent();

                FileOutputStream out = new FileOutputStream(toFile, false);
                byte[] buffer = new byte[1024];
                int len = 0;
                while ((len = in.read(buffer)) != -1) {
                    out.write(buffer, 0, len);
                }
                out.flush();
                out.close();
                in.close();

                fileBean.setSaveFile(toFile);

                return fileBean;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

}
