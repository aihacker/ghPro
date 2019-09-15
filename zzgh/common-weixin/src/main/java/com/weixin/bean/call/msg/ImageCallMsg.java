package com.weixin.bean.call.msg;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by Administrator on 2017/7/13.
 */
public class ImageCallMsg extends FileCallMsg {

    @JsonProperty("PicUrl")
    private String picUrl;

    public ImageCallMsg() {
        super("image");
    }

    public String getPicUrl() {
        return picUrl;
    }

    public void setPicUrl(String picUrl) {
        this.picUrl = picUrl;
    }

}
