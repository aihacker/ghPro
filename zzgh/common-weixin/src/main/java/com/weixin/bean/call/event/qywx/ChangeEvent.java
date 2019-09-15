package com.weixin.bean.call.event.qywx;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.weixin.bean.call.event.EventCallMsg;

/**
 * Created by Administrator on 2017/8/1.
 */
public class ChangeEvent extends EventCallMsg {

    @JsonProperty("ChangeType")
    private String changeType;

    public ChangeEvent() {
        super("change_contact");
    }

    public ChangeEvent(String changeType) {
        this();
        this.changeType = changeType;
    }

    public String getChangeType() {
        return changeType;
    }

    public void setChangeType(String changeType) {
        this.changeType = changeType;
    }
}
