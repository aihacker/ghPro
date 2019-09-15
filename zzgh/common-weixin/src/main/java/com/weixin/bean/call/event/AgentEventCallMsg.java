package com.weixin.bean.call.event;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * 成员进入应用的事件
 * Created by Administrator on 2017/7/13.
 */
public class AgentEventCallMsg extends EventCallMsg {

    @JsonProperty("EventKey")
    private String eventKey;

    public AgentEventCallMsg() {
        super("enter_agent");
    }

    public String getEventKey() {
        return eventKey;
    }

    public void setEventKey(String eventKey) {
        this.eventKey = eventKey;
    }
}
