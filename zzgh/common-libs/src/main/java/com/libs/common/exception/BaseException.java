package com.libs.common.exception;

/**
 * Created by XLKAI on 2017/7/8.
 */
public class BaseException extends Exception {

    private int errcode;

    public BaseException() {
    }

    public BaseException(Throwable throwable) {
        super(throwable);
    }

    public BaseException(String message) {
        super(message);
    }

    public BaseException(String message, Throwable throwable) {
        super(message, throwable);
    }

    public BaseException(int errcode) {
        this.errcode = errcode;
    }

    public BaseException(int errcode, String message) {
        super(message);
        this.errcode = errcode;
    }

    public BaseException(int errcode, String message, Throwable cause) {
        super(message, cause);
        this.errcode = errcode;
    }

    public int getErrcode() {
        return errcode;
    }
}
