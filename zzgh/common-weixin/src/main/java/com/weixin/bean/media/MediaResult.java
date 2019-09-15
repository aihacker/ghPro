package com.weixin.bean.media;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.weixin.bean.ErrResult;

/**
 * Created by XLKAI on 2017/7/10.
 */
public class MediaResult extends ErrResult {

    private String type;
    @JsonProperty("media_id")
    private String mediaId;
    @JsonProperty("created_at")
    private String createdAt;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getMediaId() {
        return mediaId;
    }

    public void setMediaId(String mediaId) {
        this.mediaId = mediaId;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }
}
