package com.weixin.bean.result.tag;

import com.weixin.bean.ErrResult;

import java.util.List;

/**
 * Created by XLKAI on 2017/7/9.
 */
public class AddTagUserResult extends ErrResult {

    private String invalidlist;
    private List<Integer> invalidparty;

    public String getInvalidlist() {
        return invalidlist;
    }

    public void setInvalidlist(String invalidlist) {
        this.invalidlist = invalidlist;
    }

    public List<Integer> getInvalidparty() {
        return invalidparty;
    }

    public void setInvalidparty(List<Integer> invalidparty) {
        this.invalidparty = invalidparty;
    }
}
