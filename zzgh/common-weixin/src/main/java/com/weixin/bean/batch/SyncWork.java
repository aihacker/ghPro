package com.weixin.bean.batch;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by XLKAI on 2017/7/9.
 */
public class SyncWork {

    @JsonProperty("media_id")
    private String mediaId;
    private SyncCallback callback;

    public String getMediaId() {
        return mediaId;
    }

    public void setMediaId(String mediaId) {
        this.mediaId = mediaId;
    }

    public SyncCallback getCallback() {
        return callback;
    }

    public void setCallback(SyncCallback callback) {
        this.callback = callback;
    }
}
