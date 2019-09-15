package com.weixin.bean.result.user;

import com.weixin.bean.ErrResult;
import com.weixin.bean.user.SimpleUser;

import java.util.List;

/**
 * Created by XLKAI on 2017/7/9.
 */
public class SimpleUserResult extends ErrResult {

    private List<SimpleUser> userlist;

    public List<SimpleUser> getUserlist() {
        return userlist;
    }

    public void setUserlist(List<SimpleUser> userlist) {
        this.userlist = userlist;
    }
}
