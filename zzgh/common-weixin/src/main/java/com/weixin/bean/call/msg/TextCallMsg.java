package com.weixin.bean.call.msg;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by Administrator on 2017/7/13.
 */
public class TextCallMsg extends CallMsg {

    @JsonProperty("Content")
    private String content;

    public TextCallMsg() {
        super("text");
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
