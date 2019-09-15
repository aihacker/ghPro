package wxgh.wx.web.party.branch.chat;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import pub.spring.web.mvc.ActionResult;
import wxgh.app.session.user.UserSession;
import wxgh.data.chat.ChatList;
import wxgh.data.pcadmin.pub.ChatGroupuser;
import wxgh.entity.chat.ChatGroup;
import wxgh.param.chat.group.ChatGroupParam;
import wxgh.param.union.group.user.ListParam;
import wxgh.sys.service.weixin.chat.ChatGroupService;

import java.util.List;

/**
 * Created by Administrator on 2017/8/21.
 */
@Controller
public class ApiController {

    @RequestMapping
    public ActionResult list(ChatGroupParam param) {
        param.setPageIs(true);
        param.setUserid(UserSession.getUserid());
        param.setType(ChatGroup.TYPE_PARTY);

        List<ChatList> groups = chatGroupService.listGroup(param);
        return ActionResult.okRefresh(groups, param);
    }

    @RequestMapping
    public ActionResult edit(ChatGroup group) {
        chatGroupService.editGroup(group);
        return ActionResult.ok();
    }

    @RequestMapping
    public ActionResult user_list(ListParam param) {
        param.setPageIs(true);
        List<ChatGroupuser> users = chatGroupService.listUser(param);
        return ActionResult.okRefresh(users, param);
    }

    @RequestMapping
    public ActionResult del(Integer id) {
        chatGroupService.delUser(id);
        return ActionResult.ok();
    }

    @Autowired
    private ChatGroupService chatGroupService;

}
