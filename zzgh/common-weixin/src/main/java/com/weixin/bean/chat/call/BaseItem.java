package com.weixin.bean.chat.call;

/**
 * Created by Administrator on 2017/4/12.
 */
public class BaseItem {

    private String fromName;
    private Long createTime;
    private String msgType;

    public String getFromName() {
        return fromName;
    }

    public void setFromName(String fromName) {
        this.fromName = fromName;
    }

    public Long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Long createTime) {
        this.createTime = createTime;
    }

    public String getMsgType() {
        return msgType;
    }

    public void setMsgType(String msgType) {
        this.msgType = msgType;
    }
}
