package com.weixin.bean.oauth;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.weixin.bean.ErrResult;

/**
 * Created by XLKAI on 2017/7/10.
 */
@JsonInclude(value = JsonInclude.Include.NON_NULL)
public class UserInfo extends ErrResult {

    @JsonProperty("OpenId")
    private String openId;
    @JsonProperty("UserId")
    private String userid;
    @JsonProperty("DeviceId")
    private String deviceid;
    @JsonProperty("user_ticket")
    private String userTicket;
    @JsonProperty("expires_in")
    private Long expires;

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getDeviceid() {
        return deviceid;
    }

    public void setDeviceid(String deviceid) {
        this.deviceid = deviceid;
    }

    public String getUserTicket() {
        return userTicket;
    }

    public void setUserTicket(String userTicket) {
        this.userTicket = userTicket;
    }

    public Long getExpires() {
        return expires;
    }

    public void setExpires(Long expires) {
        this.expires = expires;
    }

    @Override
    public String toString() {
        return "UserInfo{" +
                "openId='" + openId + '\'' +
                ", userid='" + userid + '\'' +
                ", deviceid='" + deviceid + '\'' +
                ", userTicket='" + userTicket + '\'' +
                ", expires=" + expires +
                '}';
    }
}
