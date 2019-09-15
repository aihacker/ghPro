package wxgh.wx.web.chat;

import com.libs.common.ffmpeg.AudioUtils;
import com.libs.common.http.HttpClient;
import com.libs.common.http.HttpException;
import com.libs.common.json.JsonUtils;
import com.libs.common.type.StringUtils;
import com.libs.common.type.TypeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.socket.TextMessage;
import pub.spring.web.mvc.ActionResult;
import pub.utils.PathUtils;
import pub.web.ServletUtils;
import wxgh.app.callback.SuccessCallBack;
import wxgh.app.session.bean.SeUser;
import wxgh.app.session.chat.SessionConsts;
import wxgh.app.session.user.UserSession;
import wxgh.app.sys.api.FileApi;
import wxgh.data.chat.msg.MsgList;
import wxgh.entity.chat.ChatMsg;
import wxgh.entity.pub.SysFile;
import wxgh.param.chat.ChatObject;
import wxgh.param.chat.location.Location;
import wxgh.param.chat.location.LocationResponse;
import wxgh.sys.service.weixin.chat.ChatMsgService;
import wxgh.wx.sys.websocket.ChatHandler;

import java.io.File;
import java.io.IOException;
import java.util.Date;

/**
 * Created by Administrator on 2017/7/18.
 */
@Controller
public class SendController {

    private static final String ak = "QDpMvwlnCdH7WSVqtUamlcCXqEsoQ8uD";

    @RequestMapping
    public ActionResult get_location(Location location) {
        String url = "http://api.map.baidu.com/geocoder/v2/";
        url += ("?ak=" + ak);
        url += ("&location=" + location.toString());
        url += ("&coordtype=gcj02");
        url += ("&output=json");
        url += ("&pois=1");

        LocationResponse response = null;
        try {
            response = JsonUtils.parseBean(HttpClient.get(url), LocationResponse.class);
        } catch (HttpException e) {
            e.printStackTrace();
        }

        return ActionResult.ok(null, response);
    }

    @RequestMapping
    public ActionResult message(ChatMsg chatMsg) {
        try {
            TextMessage message = saveMessage(chatMsg);

            chatHandler.sendTextMessage(message);
        } catch (IOException e) {
            e.printStackTrace();
            return ActionResult.error("消息发送失败");
        }
        return ActionResult.ok();
    }

    @RequestMapping
    public ActionResult image(final ChatMsg chatMsg, String mediaid) {
        if (!TypeUtils.empty(mediaid)) {
            fileApi.wxDownload(mediaid, new SuccessCallBack() {
                @Override
                public void onSuccess(SysFile file, File toFile) {
                    String content = "{\"imgSrc\":\"" + file.getFilePath() + "\"}";
                    chatMsg.setContent(content);
                    try {
                        TextMessage message = saveMessage(chatMsg);
                        chatHandler.sendTextMessage(message);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });
        }
        return ActionResult.ok();
    }

    @RequestMapping
    public ActionResult voice(final ChatMsg chatMsg, final String mediaid, Long second) {
        if (!TypeUtils.empty(mediaid)) {
            fileApi.wxDownload(mediaid, new SuccessCallBack() {
                @Override
                public void onSuccess(SysFile file, File toFile) {

                    File mp3Path = PathUtils.getMp3(toFile, mediaid);
                    AudioUtils.toMp3(toFile.getAbsolutePath(), mp3Path.getAbsolutePath());
                    String cnt = null;
                    try {
                        cnt = "{\"voiceSrc\":\"" + PathUtils.getPath(mp3Path) + "\", \"second\":" + AudioUtils.getAmrDuration(toFile) + "}";
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    chatMsg.setContent(cnt);

                    try {
                        TextMessage message = saveMessage(chatMsg);
                        chatHandler.sendTextMessage(message);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });
        }
        return ActionResult.ok(null, chatMsg.getContent());
    }

    private TextMessage saveMessage(ChatMsg chatMsg) {


        chatMsg.setSendTime(new Date());

        SeUser user = UserSession.getUser();

        ChatObject mUser = (ChatObject) ServletUtils.getSession().getAttribute(SessionConsts.SESSION_CHAT);
        chatMsg.setChatId(StringUtils.uuid());
        chatMsg.setUserid(mUser.getUserid());
        chatMsg.setGroupId(mUser.getId());

        chatMsgService.save(chatMsg);

        MsgList msg = new MsgList();
        msg.setUserid(mUser.getUserid());
        msg.setChatId(chatMsg.getChatId());
        msg.setSendTime(chatMsg.getSendTime());
        msg.setAvatar(user.getAvatar());
        msg.setContent(chatMsg.getContent());
        msg.setMsgType(chatMsg.getMsgType());
        msg.setUsername(user.getName());
        msg.setId(chatMsg.getId());

        TextMessage message = new TextMessage(JsonUtils.stringfy(msg));
        return message;
    }


    @Autowired
    private ChatHandler chatHandler;

    @Autowired
    private FileApi fileApi;

    @Autowired
    private ChatMsgService chatMsgService;

}
