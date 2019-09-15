package com.weixin.bean.call.msg;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.weixin.bean.call.Call;

/**
 * Created by Administrator on 2017/7/13.
 */
public class CallMsg extends Call {

    @JsonProperty("MsgId")
    private Long msgid;

    public CallMsg(String msgType) {
        super(msgType);
    }

    public Long getMsgid() {
        return msgid;
    }

    public void setMsgid(Long msgid) {
        this.msgid = msgid;
    }
}
