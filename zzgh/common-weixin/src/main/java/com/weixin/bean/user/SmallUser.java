package com.weixin.bean.user;

import com.libs.common.json.JsonNoneNull;

/**
 * Created by XLKAI on 2017/7/9.
 */
public class SmallUser extends JsonNoneNull {

    private String name;
    private String userid;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }
}
