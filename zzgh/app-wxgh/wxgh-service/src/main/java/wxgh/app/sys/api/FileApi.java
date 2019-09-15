package wxgh.app.sys.api;

import com.libs.common.bean.FileType;
import com.libs.common.crypt.MD5;
import com.libs.common.ffmpeg.VideoUtils;
import com.libs.common.type.StringUtils;
import com.libs.common.type.TypeUtils;
import com.weixin.api.MediaApi;
import com.weixin.bean.ErrResult;
import com.weixin.bean.FileBean;
import com.weixin.utils.DownloadCallback;
import net.coobird.thumbnailator.Thumbnails;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import pub.error.ValidationError;
import pub.utils.PathUtils;
import wxgh.app.callback.SuccessCallBack;
import wxgh.app.sys.variate.GlobalValue;
import wxgh.app.utils.ImageDownload;
import wxgh.entity.pub.SysFile;
import wxgh.param.pub.file.FileParam;
import wxgh.sys.service.weixin.pub.SysFileService;

import java.io.File;
import java.io.IOException;

/**
 * Created by Administrator on 2017/7/18.
 */
@Component
public class FileApi {

    private static final Log log = LogFactory.getLog(FileApi.class);

    public String replaceImage(String html) {
        if (html == null || html.length() <= 0) {
            return "";
        }
        Document document = Jsoup.parse(html);
        Elements elements = document.select("img[src]");
        String host = GlobalValue.host;

        try {
            for (Element e : elements) {
                String src = e.attr("src");
                if (src.startsWith("http") && !src.startsWith(host)) {
                    String fileId = StringUtils.uuid();
                    File toFile = PathUtils.getUpload(fileId);

                    FileBean fileBean = ImageDownload.download(src, toFile);
                    SysFile file = addFile(fileBean, null);

                    e.attr("src", GlobalValue.getUrl() + file.getFilePath());
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return document.body().html();
    }

    public void wxDownload(String mediaId, final SuccessCallBack callBack) {
        try {
            final String fileId = StringUtils.uuid();
            File toFile = PathUtils.getUpload(fileId);
            MediaApi.download(mediaId, toFile, new DownloadCallback() {
                @Override
                public void onFaile(Exception e) {
                    log.error("download weixin mediaId faile", e);
                }

                @Override
                public void onError(ErrResult errResult) {
                    if (errResult != null)
                        log.error("download weixin mediaId error, errcode=" + errResult.getErrcode() + ", errmsg='" + errResult.getErrmsg() + "'");
                }

                @Override
                public void onSuccess(FileBean fileBean) {
                    fileBean.setFileId(fileId);
                    SysFile sysFile = addFile(fileBean, null);
                    callBack.onSuccess(sysFile, fileBean.getSaveFile());
                }
            });
        } catch (IOException e) {
            log.error("download weixin mediaId failed", e);
        }

    }

    /**
     * 添加文件到数据库
     *
     * @param file
     * @param param
     * @return
     */
    public SysFile addFile(FileBean file, FileParam param) {
        String md5 = MD5.getFileMD5(file.getSaveFile());

        SysFile sysFile = sysFileService.getFile(md5);
        if (sysFile == null || !sysFile.getSize().equals(file.getSize())) {
            String thumb = createThumb(param, file.getSaveFile()); //创建略缩图

            sysFile = new SysFile();
            sysFile.setFileId(file.getFileId());
            sysFile.setFilename(file.getFilename());
            sysFile.setFilePath(PathUtils.getPath(file.getSaveFile()));
            sysFile.setMimeType(file.getContentType());
            sysFile.setThumbPath(thumb);
            sysFile.setSize(file.getSize());
            sysFile.setMd5(md5);
            sysFile.setType(param == null ? "" : param.getType());

            sysFileService.addFile(sysFile);
        }

        return sysFile;
    }

    /**
     * 添加文件到数据库
     *
     * @param f
     * @param param
     * @return
     */
    public SysFile addFile(MultipartFile f, FileParam param) {
        try {
            String fileId = StringUtils.uuid();
            File toFile = PathUtils.getUpload(fileId, f);
            FileBean fileBean = new FileBean(f, fileId);
            fileBean.setSaveFile(toFile);

            return addFile(fileBean, param);
        } catch (IOException e) {
            throw new ValidationError("上传失败");
        }
    }

    /**
     * 创建略缩图
     *
     * @param param
     * @param toFile
     * @return
     */
    public String createThumb(FileParam param, File toFile) {
        File thumbFile = null;
        try {
            if (param != null && param.getThumb() != null && param.getThumb()) {
                //图片略缩图
                if (FileType.IMAGE.getType().equals(param.getType())) {
                    Thumbnails.Builder<File> builder = Thumbnails.of(toFile);
                    if (!TypeUtils.empty(param.getSize())) {
                        String[] size = param.getSize().split("x");
                        if (size.length == 1) {
                            builder.width(Integer.valueOf(size[0]));
                        } else if (size.length == 2) {
                            builder.size(Integer.valueOf(size[0]), Integer.valueOf(size[1]));
                        }
                    }
                    thumbFile = PathUtils.getThumb(toFile, ".jpg");
                    builder.toFile(thumbFile);

                    //视频文件略缩图
                } else if (FileType.VIDEO.getType().equals(param.getType())) {
                    thumbFile = PathUtils.getThumb(toFile, ".jpg");
                    VideoUtils.getVideoImage(toFile.getAbsolutePath(),
                            thumbFile.getAbsolutePath());
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return PathUtils.getPath(thumbFile);
    }

    @Autowired
    private SysFileService sysFileService;

}
