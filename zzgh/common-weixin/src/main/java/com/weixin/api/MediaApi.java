package com.weixin.api;

import com.weixin.Weixin;
import com.weixin.WeixinException;
import com.weixin.bean.ErrResult;
import com.weixin.bean.media.MediaResult;
import com.weixin.bean.media.MediaType;
import com.weixin.utils.DownloadCallback;
import com.weixin.utils.WeixinHttp;

import java.io.File;

/**
 * Created by XLKAI on 2017/7/10.
 */
public class MediaApi {

    /**
     * 上传临时文件
     *
     * @param type 文件类型
     * @param file 待上传的文件
     * @return
     * @throws WeixinException
     */
    public static MediaResult upload(MediaType type, File file) throws WeixinException {
        String url = Weixin.getTokenURL("media/upload?type=%s", type.getType());
        return WeixinHttp.upload(url, file, MediaResult.class);
    }

    /**
     * 获取临时素材文件
     *
     * @param mediaId
     * @param toFile
     * @throws WeixinException
     */
    public static void download(String mediaId, File toFile) throws WeixinException {
        String url = Weixin.getTokenURL("media/get?media_id=%s", mediaId);
        WeixinHttp.download(url, toFile, ErrResult.class);
    }

    public static void download(String mediaId, File toFile, DownloadCallback callback) {
        try {
            String url = Weixin.getTokenURL("media/get?media_id=%s", mediaId);
            WeixinHttp.download(url, toFile, callback);
        } catch (WeixinException e) {
            callback.onError(e.getErrResult());
        }
    }

}
