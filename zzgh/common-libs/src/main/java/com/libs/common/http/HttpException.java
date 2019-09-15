package com.libs.common.http;

import com.libs.common.exception.BaseException;

/**
 * Created by XLKAI on 2017/7/8.
 */
public class HttpException extends BaseException {

    private Code code;

    public HttpException(Code code) {
        super(code.getCode(), code.getMsg());
    }

    public HttpException(Code code, Throwable throwable) {
        super(code.getCode(), code.getMsg(), throwable);
        this.code = code;
    }

    public Code getCode() {
        return code;
    }

    public enum Code {

        ERR_SERVER(-20001, "server internal error"),
        EMPTY_PARAM(-20002, "empty param"),
        EMPTY_RESPONSE(-20003, "empty response"),
        FILE_NOT_EXIST(-20004, "file dones not exit");

        private int code;
        private String msg;

        Code(int code, String msg) {
            this.code = code;
            this.msg = msg;
        }

        public int getCode() {
            return code;
        }

        public String getMsg() {
            return msg;
        }
    }
}
