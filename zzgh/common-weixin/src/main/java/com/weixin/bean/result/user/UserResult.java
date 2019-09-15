package com.weixin.bean.result.user;

import com.weixin.bean.user.User;

/**
 * Created by XLKAI on 2017/7/9.
 */
public class UserResult extends User {

    private Integer errcode;
    private String errmsg;

    public Integer getErrcode() {
        return errcode;
    }

    public void setErrcode(Integer errcode) {
        this.errcode = errcode;
    }

    public String getErrmsg() {
        return errmsg;
    }

    public void setErrmsg(String errmsg) {
        this.errmsg = errmsg;
    }

}
