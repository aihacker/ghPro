package wxgh.wx.web.union.innovation.team;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import pub.spring.web.mvc.ActionResult;
import pub.spring.web.mvc.model.Model;
import wxgh.app.consts.Status;
import wxgh.data.union.innovation.group.ChatUserData;
import wxgh.param.union.innovation.team.TeamListParam;
import wxgh.sys.service.weixin.union.innovation.team.ChatGroupService;

import java.util.List;

/**
 * ----------------------------------------------------------
 * @Description
 * ----------------------------------------------------------
 * @Author Ape
 * ----------------------------------------------------------
 * @Email <16511660@qq.com>
 * ----------------------------------------------------------
 * @Date 2017-08-14  15:30
 * ----------------------------------------------------------
 */
@Controller
public class UserController {

    @Autowired
    private ChatGroupService chatGroupService;

    @RequestMapping
    public void index(Model model, Integer id){
        model.put("groupId", id);
    }

    @RequestMapping
    public ActionResult list(TeamListParam param) {
        param.setPageIs(true);
        param.setRowsPerPage(10);
        param.setStatus(Status.NORMAL.getStatus());

        List<ChatUserData> users = chatGroupService.listUser(param);

        return ActionResult.okRefresh(users, param);
    }

}
