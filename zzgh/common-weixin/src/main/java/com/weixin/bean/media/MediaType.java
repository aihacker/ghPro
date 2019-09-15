package com.weixin.bean.media;

/**
 * 媒体文件类型
 * Created by XLKAI on 2017/7/10.
 */
public enum MediaType {

    IMAGE("image"),
    VOICE("voice"),
    VIDEO("video"),
    FILE("file");

    private String type;

    MediaType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }
}
