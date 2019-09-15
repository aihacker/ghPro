package wxgh.admin.web.pub.group;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import pub.spring.web.mvc.ActionResult;
import wxgh.data.pcadmin.pub.ChatGroupList;
import wxgh.data.pcadmin.pub.ChatGroupuser;
import wxgh.entity.chat.ChatGroup;
import wxgh.entity.chat.ChatUser;
import wxgh.param.admin.pub.ChatGroupParam;
import wxgh.param.admin.pub.ChatGroupuserParam;
import wxgh.sys.service.admin.chat.ChatGroupService;

import java.util.List;

/**
 * Created by Administrator on 2017/8/18.
 */
@Controller
public class ApiController {

    @RequestMapping
    public ActionResult list(ChatGroupParam param) {
        param.setPageIs(true);
        List<ChatGroupList> groups = chatGroupService.listGroup(param);
        return ActionResult.okAdmin(groups, param);
    }

    @RequestMapping
    public ActionResult del_group(String id) {
        chatGroupService.delete(id);
        return ActionResult.ok();
    }

    @RequestMapping
    public ActionResult add_group(ChatGroup group) {
        chatGroupService.addOrUpdateGroup(group);
        return ActionResult.ok();
    }

    @RequestMapping
    public ActionResult list_user(ChatGroupuserParam param) {
        param.setPageIs(true);
        param.setStatus(1);
        List<ChatGroupuser> users = chatGroupService.listUser(param);
        return ActionResult.okAdmin(users, param);
    }

    @RequestMapping
    public ActionResult add_user(ChatUser user) {
        chatGroupService.addOrUpdateUser(user);
        return ActionResult.ok();
    }

    @RequestMapping
    public ActionResult del_user(String id) {
        chatGroupService.deleteUser(id);
        return ActionResult.ok();
    }

    @Autowired
    private ChatGroupService chatGroupService;
}
