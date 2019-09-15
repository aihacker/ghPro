package com.weixin.bean.message;

/**
 * Created by XLKAI on 2017/7/11.
 */
public class Text extends Msg {

    private String content;

    public Text(String content) {
        super("text");
        this.content = content;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
