package com.weixin.bean.result.tag;

import com.weixin.bean.ErrResult;
import com.weixin.bean.user.SmallUser;

import java.util.List;

/**
 * Created by XLKAI on 2017/7/9.
 */
public class TagUserResult extends ErrResult {

    private List<SmallUser> userlist;
    private int[] partylist;

    public List<SmallUser> getUserlist() {
        return userlist;
    }

    public void setUserlist(List<SmallUser> userlist) {
        this.userlist = userlist;
    }

    public int[] getPartylist() {
        return partylist;
    }

    public void setPartylist(int[] partylist) {
        this.partylist = partylist;
    }
}
