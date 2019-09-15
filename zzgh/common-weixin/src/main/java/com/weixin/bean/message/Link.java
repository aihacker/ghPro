package com.weixin.bean.message;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * 链接消息
 * Created by XLKAI on 2017/7/11.
 */
public class Link extends Msg {

    private String title;
    private String description;
    private String url;
    @JsonProperty("thumb_media_id")
    private String mediaId;

    public Link() {
        super("link");
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getMediaId() {
        return mediaId;
    }

    public void setMediaId(String mediaId) {
        this.mediaId = mediaId;
    }
}
