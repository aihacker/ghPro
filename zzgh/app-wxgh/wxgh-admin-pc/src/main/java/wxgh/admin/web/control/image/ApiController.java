package wxgh.admin.web.control.image;

import com.libs.common.crypt.MD5;
import com.libs.common.file.FileUtils;
import com.libs.common.type.StringUtils;
import com.libs.common.type.TypeUtils;
import com.weixin.bean.FileBean;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import pub.spring.web.mvc.ActionResult;
import pub.utils.PathUtils;
import pub.utils.StrUtils;
import wxgh.app.sys.api.FileApi;
import wxgh.entity.pub.SysFile;
import wxgh.param.pub.file.FileParam;
import wxgh.sys.service.weixin.pub.SysFileService;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/8/25.
 */
@Controller
public class ApiController {

    private static final String ADMIN_PATH = "http://fsdx.fsecity.com:8088/weixin-app-admin/";

    private static final String APP_PATH = "http://fsdx.fsecity.com:8088/wxgh/";

    public static final Integer timeOut = 20 * 1000;

    @RequestMapping
    public ActionResult get(String url) {
        if (TypeUtils.empty(url)) {
            return ActionResult.error("链接地址不能为空");
        }
        try {
            String[] urls = url.split(",");

            List<String> fileIds = new ArrayList<>();
            for (String murl : urls) {
                murl = getImagePath(murl);
                String fileId = StringUtils.uuid();
                File toFile = PathUtils.getUpload(fileId, null);

                FileBean fileBean = downlod(murl, toFile);

                String md5 = MD5.getFileMD5(fileBean.getSaveFile());
                SysFile sysFile = sysFileService.getFile(md5);
                if (sysFile == null) {
                    FileParam param = new FileParam();
                    String thumb = fileApi.createThumb(param, fileBean.getSaveFile()); //创建略缩图

                    sysFile = new SysFile();
                    sysFile.setFileId(fileId);
                    sysFile.setFilename(fileBean.getFilename());
                    sysFile.setFilePath(PathUtils.getPath(fileBean.getSaveFile()));
                    sysFile.setMimeType(fileBean.getContentType());
                    sysFile.setThumbPath(thumb);
                    sysFile.setSize(fileBean.getSize());
                    sysFile.setMd5(MD5.getFileMD5(fileBean.getSaveFile()));
                    sysFile.setType(param == null ? "" : param.getType());

                    sysFileService.addFile(sysFile);
                } else {
                    FileUtils.delete(toFile);
                }
                fileIds.add(sysFile.getFileId());
            }

            return ActionResult.okWithData(fileIds);
        } catch (Exception e) {
            return ActionResult.error(e);
        }
    }

    private FileBean downlod(String url, File toFile) {
        try {
            CloseableHttpClient closeableHttpClient = HttpClients.createDefault();
            HttpGet httpGet = new HttpGet(url);
            RequestConfig config = RequestConfig.custom()
                    .setConnectTimeout(timeOut)
                    .setConnectionRequestTimeout(10000)
                    .setSocketTimeout(timeOut)
                    .build();
            httpGet.setConfig(config);

            CloseableHttpResponse response = closeableHttpClient.execute(httpGet);
            HttpEntity entity = response.getEntity();
            Header contentHeader = entity.getContentType();
            String contentType = "";
            if (contentHeader != null) {
                contentType = contentHeader.getValue();
            }
            InputStream in = entity.getContent();
            FileBean fileBean = new FileBean(toFile.getName(), contentType, entity.getContentLength());
            fileBean.setSaveFile(toFile);
            FileUtils.toFile(in, toFile);
            return fileBean;
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private String getImagePath(String path) {
        if (StrUtils.empty(path)) {
            return "";
        }
        if (path.startsWith("http://")) {
            return path;
        }
        if (path.startsWith("${admin}")) {
            return path.replace("${admin}", ADMIN_PATH);
        }
        if (path.startsWith("${home}")) {
            return path.replace("${home}", APP_PATH);
        }
        if (path.startsWith("/weixin-app-server")) {
            return path.replace("/weixin-app-server", APP_PATH);
        }
        if (path.startsWith("/weixin-app-admin")) {
            return path.replace("/weixin-app-admin", ADMIN_PATH);
        }
        if (path.startsWith("/wx-dev")) {
            return path.replace("/wx-dev", APP_PATH);
        }
        if (path.startsWith("/wxgh")) {
            return path.replace("/wxgh", APP_PATH);
        }
        if (path.indexOf("/") < 0) {
            return APP_PATH + "/uploads/image/material/" + path;
        }
        return APP_PATH + path;
    }

    @Autowired
    private FileApi fileApi;

    @Autowired
    private SysFileService sysFileService;
}
