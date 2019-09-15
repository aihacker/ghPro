package com.libs.common.bean;

/**
 * 文件类型
 * Created by Administrator on 2017/7/14.
 */
public enum FileType {

    IMAGE("image"),
    VIDEO("video"),
    VOICE("voice");

    private String type;

    FileType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }
}
