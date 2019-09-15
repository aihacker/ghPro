package com.weixin.bean.message;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * Created by XLKAI on 2017/7/11.
 */
public class Msg {

    @JsonIgnore
    private String msgtype;

    public Msg(String msgtype) {
        this.msgtype = msgtype;
    }

    public String getMsgtype() {
        return msgtype;
    }

    public void setMsgtype(String msgtype) {
        this.msgtype = msgtype;
    }
}
