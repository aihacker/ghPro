package com.weixin.bean.batch;

/**
 * Created by XLKAI on 2017/7/9.
 */
public class SyncCallback {

    private String url;
    private String token;
    private String encodingaeskey;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getEncodingaeskey() {
        return encodingaeskey;
    }

    public void setEncodingaeskey(String encodingaeskey) {
        this.encodingaeskey = encodingaeskey;
    }
}
