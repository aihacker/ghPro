package com.weixin.bean.message;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by XLKAI on 2017/7/11.
 */
public class MpArticle {

    private String title;
    @JsonProperty("thumb_media_id")
    private String mediaId;
    private String author;
    @JsonProperty("content_source_url")
    private String url;
    private String digest;
    private String content;
    @JsonProperty("show_cover_pic")
    private Integer showCover;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMediaId() {
        return mediaId;
    }

    public void setMediaId(String mediaId) {
        this.mediaId = mediaId;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getDigest() {
        return digest;
    }

    public void setDigest(String digest) {
        this.digest = digest;
    }

    public Integer getShowCover() {
        return showCover;
    }

    public void setShowCover(Integer showCover) {
        this.showCover = showCover;
    }
}
