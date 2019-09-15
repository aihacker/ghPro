package com.weixin.bean.result.oauth;

import com.weixin.bean.ErrResult;

/**
 * Created by XLKAI on 2017/7/10.
 */
public class ConverOpenidResult extends ErrResult {

    private String openid;
    private String appid;

    public String getOpenid() {
        return openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid;
    }

    public String getAppid() {
        return appid;
    }

    public void setAppid(String appid) {
        this.appid = appid;
    }
}
