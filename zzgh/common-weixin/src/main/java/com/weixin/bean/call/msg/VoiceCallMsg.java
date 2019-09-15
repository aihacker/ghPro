package com.weixin.bean.call.msg;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by Administrator on 2017/7/13.
 */
public class VoiceCallMsg extends FileCallMsg {

    @JsonProperty("Format")
    private String format;

    public VoiceCallMsg() {
        super("voice");
    }

    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }
}
