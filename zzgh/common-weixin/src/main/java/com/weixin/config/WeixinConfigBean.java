package com.weixin.config;

import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

/**
 * Created by XLKAI on 2017/7/8.
 */
public class WeixinConfigBean {

    private String appid; //公众号、企业号ID
    private String secret; //管理者的凭证秘钥

    private String host;
    private String tokenPath; //token文件保存路径
    private boolean debug;

    private NamedParameterJdbcTemplate jdbcTemplate;

    public void setJdbcTemplate(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        Config.jdbcTemplate = jdbcTemplate;
    }

    public void setHost(String host) {
        this.host = host;
        Config.HOST = host;
    }

    public void setAppid(String appid) {
        this.appid = appid;
        Config.APPID = appid;
    }

    public void setSecret(String secret) {
        this.secret = secret;
        Config.SECRET = secret;
    }

    public void setTokenPath(String tokenPath) {
        this.tokenPath = tokenPath;
        Config.TOKEN_PATH = tokenPath;
    }

    public void setDebug(boolean debug) {
        this.debug = debug;
        Config.DEBUG = debug;
    }
}
