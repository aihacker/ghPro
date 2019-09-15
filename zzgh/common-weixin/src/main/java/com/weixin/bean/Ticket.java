package com.weixin.bean;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by Administrator on 2017/7/13.
 */
public class Ticket extends ErrResult {

    private String ticket;
    @JsonProperty("expires_in")
    private Integer expires;
    private Long addTime;

    public String getTicket() {
        return ticket;
    }

    public void setTicket(String ticket) {
        this.ticket = ticket;
    }

    public Integer getExpires() {
        return expires;
    }

    public void setExpires(Integer expires) {
        this.expires = expires;
    }

    public Long getAddTime() {
        return addTime;
    }

    public void setAddTime(Long addTime) {
        this.addTime = addTime;
    }

    @Override
    public String toString() {
        return "Ticket{" +
                "ticket='" + ticket + '\'' +
                ", expires=" + expires +
                ", addTime=" + addTime +
                '}';
    }
}
