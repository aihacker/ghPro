package com.weixin.bean.message;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by XLKAI on 2017/7/11.
 */
public class File extends Msg {

    @JsonProperty("media_id")
    private String mediaId;

    public File() {
        super("file");
    }

    public File(String msgtype) {
        super(msgtype);
    }

    public String getMediaId() {
        return mediaId;
    }

    public void setMediaId(String mediaId) {
        this.mediaId = mediaId;
    }
}
