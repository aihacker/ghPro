package com.weixin.api;

import com.libs.common.cache.AgentCache;
import com.libs.common.json.JsonUtils;
import com.weixin.Agent;
import com.weixin.Weixin;
import com.weixin.WeixinException;
import com.weixin.bean.token.Token;
import com.weixin.bean.token.TokenVal;
import com.weixin.config.Config;
import com.weixin.utils.DbUtils;
import com.weixin.utils.WeixinHttp;
import org.apache.log4j.Logger;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * Created by XLKAI on 2017/7/8.
 */
public class TokenApi {

    private static final Logger log = Logger.getLogger(TokenApi.class);
    private static ConcurrentMap<Integer, Token> tokenMap = new ConcurrentHashMap<>();

    public static Token getToken(Integer agentId) throws WeixinException {
        if (agentId == null) {
            agentId = AgentCache.get();
        }
        Token token2 = null;
        //从数据库中获取agentid对应的token数据
        TokenVal token1 = DbUtils.getVal(agentId);
        if(token1 != null){
            token2 = JsonUtils.parseBean(token1.getVal(),Token.class);

            if (token2 == null || isTimeout(token2)) {

                token2 = getFileToken(agentId);
                if (token2 == null || isTimeout(token2)) {
                    //写入token数据
                    token2 = getWxToken(agentId,false);//agentId
                }

            }
        }else {
            token2 = getWxToken(agentId,true);//agentId
        }

        /*Token token = tokenMap.getOrDefault(agentId, null);
        if (token == null || isTimeout(token)) {
            token = getFileToken(agentId);
            if (token == null || isTimeout(token)) {
                token = getWxToken(agentId);//agentId
            }
            tokenMap.put(agentId, token);
        }*/
        return token2;
    }

    public static Token getToken() throws WeixinException {
        return getToken(null);
    }

    /**
     * 判断token是否过期
     *
     * @param token
     * @return
     */
    private static boolean isTimeout(Token token) {
        if ((System.currentTimeMillis() - token.getAddTime()) / 1000 > token.getExpires() - 80) {
            return true;
        }
        return false;
    }

    /**
     * 请求服务器获取token
     *
     * @return
     * @throws WeixinException
     */
/*    public static Token getWxToken(Integer agentId) throws WeixinException {
        Token token = WeixinHttp.get(Weixin.getURL("gettoken?corpid=%s&corpsecret=%s", Config.APPID,  Config.SECRET),
                Token.class);
        if (log.isDebugEnabled()) {
            log.debug("get new weixin token from weixin's server：" + JsonUtils.stringfy(token));
        }
        writeToken(token, agentId);
        return token;
    }*/

    public static Token getWxToken(Integer agentId,Boolean flag) throws WeixinException {

        Token token = WeixinHttp.get(Weixin.getURL("gettoken?corpid=%s&corpsecret=%s", Config.APPID,Agent.from(agentId).getSecret()),
                Token.class);
        if (log.isDebugEnabled()) {
            log.debug("get new weixin token from weixin's server：" + JsonUtils.stringfy(token));
        }
        writeToken(token,agentId,flag);
        return token;
    }

    private static void writeToken(Token token, Integer agentId, Boolean flag) {
        token.setAddTime(System.currentTimeMillis()/1000);

        if (flag){

            DbUtils.addVal(agentId,TokenVal.VAL_TOKEN + "_" + agentId, JsonUtils.stringfy(token));
        }
        else {
            DbUtils.updateVal(agentId,TokenVal.VAL_TOKEN + "_" + agentId, JsonUtils.stringfy(token));
        }

    }

    /**
     * 获取文件token
     *
     * @return
     */
    public static Token getFileToken(Integer agentId) {
        TokenVal tokenVal = DbUtils.getVal(Weixin.getName(TokenVal.VAL_TOKEN, agentId));
        if (tokenVal != null) {
            Token token = JsonUtils.parseBean(tokenVal.getVal(), Token.class);
            return token;
        }
        return null;
    }

    /**
     * 将token写入到文件
     *
     * @param token
     */
    public static void writeToken(Token token, Integer agentId) {
        token.setAddTime(System.currentTimeMillis());

        DbUtils.addVal(Weixin.getName(TokenVal.VAL_TOKEN, agentId), JsonUtils.stringfy(token));
    }

    public static void writeToken(Token token) {
        token.setAddTime(System.currentTimeMillis());

        DbUtils.addVal(TokenVal.VAL_TOKEN, JsonUtils.stringfy(token));
//        File file = Config.getWeixinFile("weixin_token");
//        try {
//            FileUtils.writeStringToFile(file, XmlUtils.strinfy(token), "UTF-8");
//        } catch (IOException e) {
//            log.error("error occurred when write weixin token to file：" + file.getAbsolutePath(), e);
//        }
    }

    public static void clearToken(){

        /*System.out.println("判断是否超时");
        for(Integer key : tokenMap.keySet()) {
            Token token = tokenMap.getOrDefault(key, null);
            if (token == null || isTimeout(token)) {
                System.out.println("移除旧token,获取新token");
                try {
                    Token koken = getWxToken(key);
                    tokenMap.put(key,koken);
                } catch (WeixinException e) {
                    e.printStackTrace();
                }
            }
        }*/

    }



}
