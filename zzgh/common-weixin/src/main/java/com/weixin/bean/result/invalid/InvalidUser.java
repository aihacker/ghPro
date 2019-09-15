package com.weixin.bean.result.invalid;

import com.weixin.bean.ErrResult;

import java.util.List;

/**
 * Created by XLKAI on 2017/7/11.
 */
public class InvalidUser extends ErrResult {

    private List<String> invaliduser;

    public List<String> getInvaliduser() {
        return invaliduser;
    }

    public void setInvaliduser(List<String> invaliduser) {
        this.invaliduser = invaliduser;
    }
}
