package com.weixin.bean.result.message;

import com.weixin.bean.ErrResult;

/**
 * Created by XLKAI on 2017/7/11.
 */
public class SendResult extends ErrResult {

    private String invaliduser;
    private String invalidparty;
    private String invalidtag;

    public String getInvaliduser() {
        return invaliduser;
    }

    public void setInvaliduser(String invaliduser) {
        this.invaliduser = invaliduser;
    }

    public String getInvalidparty() {
        return invalidparty;
    }

    public void setInvalidparty(String invalidparty) {
        this.invalidparty = invalidparty;
    }

    public String getInvalidtag() {
        return invalidtag;
    }

    public void setInvalidtag(String invalidtag) {
        this.invalidtag = invalidtag;
    }
}
