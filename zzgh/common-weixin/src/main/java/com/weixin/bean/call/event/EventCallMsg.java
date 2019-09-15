package com.weixin.bean.call.event;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.weixin.bean.call.Call;

/**
 * Created by Administrator on 2017/7/13.
 */
public class EventCallMsg extends Call {

    @JsonProperty("Event")
    private String event;

    public EventCallMsg(String event) {
        super("event");
        this.event = event;
    }

    public String getEvent() {
        return event;
    }

    public void setEvent(String event) {
        this.event = event;
    }
}
