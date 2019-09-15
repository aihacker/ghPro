package com.weixin.bean.result.user;

import com.weixin.bean.ErrResult;
import com.weixin.bean.user.User;

import java.util.List;

/**
 * Created by XLKAI on 2017/7/9.
 */
public class UserListResult extends ErrResult {

    private List<User> userlist;

    public List<User> getUserlist() {
        return userlist;
    }

    public void setUserlist(List<User> userlist) {
        this.userlist = userlist;
    }
}
