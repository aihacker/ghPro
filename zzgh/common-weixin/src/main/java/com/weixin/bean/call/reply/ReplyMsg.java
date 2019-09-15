package com.weixin.bean.call.reply;

import com.libs.common.type.StringUtils;
import com.libs.common.xml.XmlUtils;
import com.qq.weixin.mp.aes.AesException;
import com.weixin.bean.call.msg.TextCallMsg;
import com.weixin.bean.message.Text;
import com.weixin.bean.signature.Signature;
import com.weixin.config.Config;
import com.weixin.crypt.BaseWxCrypt;

/**
 * Created by Administrator on 2017/7/13.
 */
public class ReplyMsg {

    /**
     * 回复文字消息
     *
     * @param touser
     * @param text
     * @param crypt
     * @return
     */
    public static String text(String touser, Text text, BaseWxCrypt crypt) {
        TextCallMsg call = new TextCallMsg();
        call.setCreateTime(System.currentTimeMillis() / 1000);
        call.setFromUser(Config.APPID);
        call.setToUser(touser);
        call.setMsgType(text.getMsgtype());
        call.setContent(text.getContent());

        Signature signature = new Signature();
        signature.setTimestamp(Long.toString(System.currentTimeMillis() / 1000));
        signature.setNonce(StringUtils.rand_int(9));

        try {
            return crypt.encryptMsg(XmlUtils.strinfy(call), signature);
        } catch (AesException e) {
            e.printStackTrace();
        }
        return "";
    }

}
