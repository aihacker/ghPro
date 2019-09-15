package com.weixin.bean.tag;

import com.libs.common.json.JsonNoneNull;

import java.util.List;

/**
 * Created by XLKAI on 2017/7/9.
 */
public class TagUser extends JsonNoneNull {

    private Integer tagid;
    private List<String> userlist;
    private List<Integer> partylist;

    public Integer getTagid() {
        return tagid;
    }

    public void setTagid(Integer tagid) {
        this.tagid = tagid;
    }

    public List<String> getUserlist() {
        return userlist;
    }

    public void setUserlist(List<String> userlist) {
        this.userlist = userlist;
    }

    public List<Integer> getPartylist() {
        return partylist;
    }

    public void setPartylist(List<Integer> partylist) {
        this.partylist = partylist;
    }
}
