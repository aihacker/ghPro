package com.weixin.bean.result.chat;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.weixin.bean.ErrResult;
import com.weixin.bean.chat.Chat;

/**
 * Created by XLKAI on 2017/7/11.
 */
public class ChatResult extends ErrResult {

    @JsonProperty("chat_info")
    private Chat chatInfo;

    public Chat getChatInfo() {
        return chatInfo;
    }

    public void setChatInfo(Chat chatInfo) {
        this.chatInfo = chatInfo;
    }
}
