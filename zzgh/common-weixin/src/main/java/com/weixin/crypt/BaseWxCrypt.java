package com.weixin.crypt;

import com.qq.weixin.mp.aes.AesException;
import com.qq.weixin.mp.aes.WXBizMsgCrypt;
import com.weixin.bean.signature.Signature;
import com.weixin.config.Config;

/**
 * @Author xlkai
 * @Version 2016/12/6
 */
public class BaseWxCrypt extends WXBizMsgCrypt {

    public BaseWxCrypt(String token, String aeskey) throws AesException {
        super(token, aeskey, Config.APPID);
    }

    /**
     * 验证url
     *
     * @param signature
     * @return
     * @throws AesException
     */
    public String verifyURL(Signature signature) throws AesException {
        return super.VerifyURL(signature.getMsg_signature(), signature.getTimestamp(), signature.getNonce(), signature.getEchostr());
    }

    /**
     * 加密
     *
     * @param replyMsg
     * @param signature
     * @return
     * @throws AesException
     */
    public String encryptMsg(String replyMsg, Signature signature) throws AesException {
        return super.EncryptMsg(replyMsg, signature.getTimestamp(), signature.getNonce());
    }

    /**
     * 解密
     *
     * @param signature
     * @param postData
     * @return
     * @throws AesException
     */
    public String decryptMsg(Signature signature, String postData) throws AesException {
        return super.DecryptMsg(signature.getMsg_signature(), signature.getTimestamp(), signature.getNonce(), postData);
    }
}
