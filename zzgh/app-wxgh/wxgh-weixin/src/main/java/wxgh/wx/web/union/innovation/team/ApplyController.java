package wxgh.wx.web.union.innovation.team;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import pub.spring.web.mvc.ActionResult;
import pub.spring.web.mvc.model.Model;
import wxgh.app.session.user.UserSession;
import wxgh.data.union.group.user.ApplyCount;
import wxgh.data.union.innovation.group.ChatUserData;
import wxgh.param.union.innovation.team.TeamListParam;
import wxgh.sys.service.weixin.union.innovation.team.ChatGroupService;

import java.util.List;

/**
 *----------------------------------------------------------
 * @Description 
 *----------------------------------------------------------
 * @Author  Ape
 *----------------------------------------------------------
 * @Email <16511660@qq.com>
 *----------------------------------------------------------
 * @Date 2017-08-04 14:49
 *----------------------------------------------------------
 */
@Controller
public class ApplyController {

    @Autowired
    private ChatGroupService chatGroupService;

    @RequestMapping
    public void index(Model model, Integer id) {
        ApplyCount count = chatGroupService.getCount(id);
        model.put("c", count);
    }

    @RequestMapping
    public ActionResult list(TeamListParam param) {
        param.setPageIs(true);
        param.setRowsPerPage(10);

        List<ChatUserData> users = chatGroupService.listUser(param);

        return ActionResult.okRefresh(users, param);
    }

    @RequestMapping
    public ActionResult apply(Integer id, Integer status) {
        chatGroupService.verify_permisstion(UserSession.getUserid(), id);
        chatGroupService.updateStatus(id, status);
        return ActionResult.ok();
    }

}

