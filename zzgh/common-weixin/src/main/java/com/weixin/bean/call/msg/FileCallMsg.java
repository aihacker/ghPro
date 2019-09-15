package com.weixin.bean.call.msg;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by Administrator on 2017/7/13.
 */
public class FileCallMsg extends CallMsg {

    @JsonProperty("MediaId")
    private String mediaId;

    public FileCallMsg(String msgType) {
        super(msgType);
    }

    public String getMediaId() {
        return mediaId;
    }

    public void setMediaId(String mediaId) {
        this.mediaId = mediaId;
    }
}
