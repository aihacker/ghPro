package wxgh.wx.web.chat;

import com.weixin.WeixinException;
import com.weixin.utils.ApiList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import pub.spring.web.mvc.ActionResult;
import pub.spring.web.mvc.model.Model;
import pub.web.ServletUtils;
import wxgh.app.session.chat.SessionConsts;
import wxgh.app.utils.WeixinUtils;
import wxgh.data.chat.ChatInfo;
import wxgh.data.chat.ChatModelInfo;
import wxgh.data.chat.msg.MsgList;
import wxgh.param.chat.ChatObject;
import wxgh.param.chat.chatmsg.ListParam;
import wxgh.sys.service.weixin.chat.ChatMsgService;
import wxgh.sys.service.weixin.chat.ChatService;

import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * Created by Administrator on 2017/7/28.
 */
@Controller
public class MainController {

    @RequestMapping
    public void chat(Integer id, Integer type, Model model) throws WeixinException {
        ChatInfo chatInfo = chatService.getGroup(id, type);
        model.put("chat", chatInfo);
        List<String> apiList = ApiList.getImageApi();
        apiList.add(ApiList.STARTRECORD);
        apiList.add(ApiList.STOPRECORD);
        apiList.add(ApiList.ONVOICERECORDEND);
        apiList.add(ApiList.PLAYVOICE);
        apiList.add(ApiList.PAUSEVOICE);
        apiList.add(ApiList.STOPVOICE);
        apiList.add(ApiList.ONVOICEPLAYEND);
        apiList.add(ApiList.UPLOADVOICE);
        apiList.add(ApiList.GETLOCATION);
        apiList.add(ApiList.OPENLOCATION);
        WeixinUtils.sign(model, apiList);
    }

    /**
     * 获取消息列表
     *
     * @param param
     * @return
     */
    @RequestMapping
    public ActionResult list_msg(ListParam param) {
        param.setPageIs(true);
        param.setRowsPerPage(15);

        List<MsgList> msgLists = chatMsgService.listMsg(param);

        return ActionResult.okRefresh(msgLists, param);
    }

    /**
     * websocket登录
     *
     * @param chatObject
     * @return
     */
    @RequestMapping
    public ActionResult login(ChatObject chatObject) {
        HttpSession session = ServletUtils.getSession();
        ChatObject sessionUser = (ChatObject) session.getAttribute(SessionConsts.SESSION_CHAT);
        if (sessionUser != null) {
            session.setAttribute(SessionConsts.SESSION_CHAT, null);
        }
        session.setAttribute(SessionConsts.SESSION_CHAT, chatObject);
        return ActionResult.ok(null, ServletUtils.getHostAddr());
    }

    @Autowired
    private ChatService chatService;

    @Autowired
    private ChatMsgService chatMsgService;

}
