package com.weixin.bean.token;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.weixin.bean.ErrResult;

/**
 * Created by XLKAI on 2017/7/8.
 */
public class Token extends ErrResult {

    @JsonProperty("access_token")
    private String token;
    @JsonProperty("expires_in")
    private Integer expires;

    private Long addTime;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
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
        return "Token{" +
                "token='" + token + '\'' +
                ", expires=" + expires +
                ", addTime=" + addTime +
                '}';
    }
}
