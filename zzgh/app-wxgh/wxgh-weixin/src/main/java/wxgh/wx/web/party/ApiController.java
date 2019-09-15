package wxgh.wx.web.party;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import pub.spring.web.mvc.ActionResult;
import wxgh.app.session.user.UserSession;
import wxgh.entity.chat.ChatUser;
import wxgh.sys.service.weixin.chat.ChatUserService;

/**
 * Created by Administrator on 2017/8/3.
 */
@Controller
public class ApiController {

    @RequestMapping
    public ActionResult tribe_join() {
        ChatUser chatUser = new ChatUser();
        chatUser.setUserid(UserSession.getUserid());
        chatUser.setGroupId("242c502064e14f87910277674b7b1022");

        chatUserService.addUser(chatUser);
        return ActionResult.ok();
    }

    @Autowired
    private ChatUserService chatUserService;

}
