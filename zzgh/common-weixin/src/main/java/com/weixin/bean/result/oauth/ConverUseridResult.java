package com.weixin.bean.result.oauth;

import com.weixin.bean.ErrResult;

/**
 * Created by XLKAI on 2017/7/10.
 */
public class ConverUseridResult extends ErrResult {

    private String userid;

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }
}
