package com.weixin.bean.call.msg;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by Administrator on 2017/7/13.
 */
public class VideoCallMsg extends FileCallMsg {

    @JsonProperty("ThumbMediaId")
    private String thumbMediaId;

    public VideoCallMsg() {
        super("video");
    }

    public VideoCallMsg(String msgType) {
        super(msgType);
    }

    public String getThumbMediaId() {
        return thumbMediaId;
    }

    public void setThumbMediaId(String thumbMediaId) {
        this.thumbMediaId = thumbMediaId;
    }
}
