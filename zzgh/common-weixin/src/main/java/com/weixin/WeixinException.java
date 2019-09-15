package com.weixin;

import com.libs.common.exception.BaseException;
import com.weixin.bean.ErrResult;

/**
 * Created by XLKAI on 2017/7/8.
 */
public class WeixinException extends BaseException {

    private ErrResult errResult;

    public WeixinException(String message) {
        super(message);
    }

    public WeixinException(String message, Throwable throwable) {
        super(message, throwable);
    }

    public WeixinException(Throwable throwable) {
        super(throwable);
    }

    public WeixinException(ErrResult error) {
        super("weixin exception: code(" + error.getErrcode() + "), msg(" + error.getErrmsg() + ")");
        this.errResult = error;
    }

    public ErrResult getErrResult() {
        return errResult;
    }
}
