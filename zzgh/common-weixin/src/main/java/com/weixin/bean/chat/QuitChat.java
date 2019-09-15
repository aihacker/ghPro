package com.weixin.bean.chat;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by XLKAI on 2017/7/11.
 */
public class QuitChat {

    private String chatid;
    @JsonProperty("op_user")
    private String opUser;

    public String getChatid() {
        return chatid;
    }

    public void setChatid(String chatid) {
        this.chatid = chatid;
    }

    public String getOpUser() {
        return opUser;
    }

    public void setOpUser(String opUser) {
        this.opUser = opUser;
    }
}
