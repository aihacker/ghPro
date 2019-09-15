package com.weixin.bean.chat.message;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.weixin.bean.chat.Chat;
import com.weixin.bean.message.Msg;
import com.weixin.json.ChatMsgSerialize;

/**
 * Created by XLKAI on 2017/7/11.
 */
@JsonSerialize(using = ChatMsgSerialize.class)
public class ChatMsg<T extends Msg> {

    private Chat.Type receiverType;
    private String receiverId;
    private String sender;
    private T msg;

    public Chat.Type getReceiverType() {
        return receiverType;
    }

    public void setReceiverType(Chat.Type receiverType) {
        this.receiverType = receiverType;
    }

    public String getReceiverId() {
        return receiverId;
    }

    public void setReceiverId(String receiverId) {
        this.receiverId = receiverId;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public T getMsg() {
        return msg;
    }

    public void setMsg(T msg) {
        this.msg = msg;
    }
}
