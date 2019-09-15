package com.weixin.bean.call;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import com.libs.common.json.JsonNoneNull;

/**
 * Created by Administrator on 2017/7/13.
 */
@JacksonXmlRootElement(localName = "xml")
public class Call extends JsonNoneNull {

    @JsonProperty("ToUserName")
    private String toUser;
    @JsonProperty("FromUserName")
    private String fromUser;
    @JsonProperty("CreateTime")
    private Long createTime;
    @JsonProperty("MsgType")
    private String msgType;
    @JsonProperty("AgentID")
    private Integer agentId;

    public Call() {
    }

    public Call(String msgType) {
        this.msgType = msgType;
    }

    public String getToUser() {
        return toUser;
    }

    public void setToUser(String toUser) {
        this.toUser = toUser;
    }

    public String getFromUser() {
        return fromUser;
    }

    public void setFromUser(String fromUser) {
        this.fromUser = fromUser;
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

    public Integer getAgentId() {
        return agentId;
    }

    public void setAgentId(Integer agentId) {
        this.agentId = agentId;
    }
}
