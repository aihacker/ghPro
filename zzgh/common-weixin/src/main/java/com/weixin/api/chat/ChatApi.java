package com.weixin.api.chat;

import com.libs.common.json.JsonUtils;
import com.weixin.Weixin;
import com.weixin.WeixinException;
import com.weixin.bean.ErrResult;
import com.weixin.bean.chat.*;
import com.weixin.bean.chat.message.ChatMsg;
import com.weixin.bean.result.chat.ChatResult;
import com.weixin.bean.result.invalid.InvalidUser;
import com.weixin.utils.WeixinHttp;

import java.util.List;

/**
 * Created by XLKAI on 2017/7/11.
 */
public class ChatApi {

    /**
     * 创建会话
     *
     * @param chat
     * @throws WeixinException
     */
    public static void create(Chat chat) throws WeixinException {
        String url = Weixin.getTokenURL("chat/create");
        WeixinHttp.post(url, chat, ErrResult.class);
    }

    /**
     * 获取会话
     *
     * @param chatid
     * @return
     * @throws WeixinException
     */
    public static Chat get(String chatid) throws WeixinException {
        String url = Weixin.getTokenURL("chat/get?chatid=%s", chatid);
        ChatResult result = WeixinHttp.get(url, ChatResult.class);
        return result.getChatInfo();
    }

    /**
     * 更新会话
     *
     * @param chat
     * @throws WeixinException
     */
    public static void update(UpdateChat chat) throws WeixinException {
        String url = Weixin.getTokenURL("chat/update");
        WeixinHttp.post(url, chat, ErrResult.class);
    }

    /**
     * 退出会话
     *
     * @param chat
     * @throws WeixinException
     */
    public static void quit(QuitChat chat) throws WeixinException {
        String url = Weixin.getTokenURL("chat/quit");
        WeixinHttp.post(url, chat, ErrResult.class);
    }

    /**
     * 清除会话未读状态
     *
     * @param userid 会话所有者
     * @param type   会话类型，group|single，群聊|单聊
     * @param id     会话值，为userid|chatid，分别表示：成员id|会话id
     * @throws WeixinException
     */
    public static void clearnotify(String userid, Chat.Type type, String id) throws WeixinException {
        String url = Weixin.getTokenURL("chat/clearnotify");
        WeixinHttp.post(url, new ClearChat(userid, type.getType(), id), ErrResult.class);
    }

    /**
     * 设置成员新消息免打扰
     *
     * @param muteChats
     * @return
     * @throws WeixinException
     */
    public static InvalidUser setmute(List<MuteChat> muteChats) throws WeixinException {
        if (muteChats.size() > 10000) {
            throw new WeixinException("setting mute user limit 10000");
        }
        String url = Weixin.getTokenURL("chat/setmute");
        String json = String.format("{\"user_mute_list\":%s}", JsonUtils.stringfy(muteChats));
        return WeixinHttp.post(url, json, InvalidUser.class);
    }

    /**
     * 发送消息
     * 消息支持：文本(text)、图片(image)、文件(file)、语音(voice)、链接(link)
     *
     * @param chatMsg
     * @throws WeixinException
     */
    public static void send(ChatMsg chatMsg) throws WeixinException {
        String url = Weixin.getTokenURL("chat/send");
        WeixinHttp.post(url, chatMsg, ErrResult.class);
    }
}
