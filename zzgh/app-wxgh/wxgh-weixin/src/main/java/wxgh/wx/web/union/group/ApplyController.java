package wxgh.wx.web.union.group;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import pub.spring.web.mvc.ActionResult;
import pub.spring.web.mvc.model.Model;
import wxgh.app.session.user.UserSession;
import wxgh.data.union.group.user.ApplyCount;
import wxgh.data.union.group.user.UserData;
import wxgh.param.union.group.user.ListParam;
import wxgh.sys.service.weixin.union.group.GroupUserService;

import java.util.List;

/**
 * Created by Administrator on 2017/7/21.
 */
@Controller
public class ApplyController {

    @RequestMapping
    public void index(Model model, Integer id) {
        ApplyCount count = groupUserService.getCount(id);
        model.put("c", count);
    }

    @RequestMapping
    public ActionResult list(ListParam param) {
        param.setPageIs(true);

        List<UserData> users = groupUserService.listUser(param);

        return ActionResult.okRefresh(users, param);
    }

    @RequestMapping
    public ActionResult apply(Integer id, Integer status, Integer groupId) {
        groupUserService.verify_permisstion(UserSession.getUserid(), groupId);
        groupUserService.updateStatus(id, status);
        return ActionResult.ok();
    }

    @Autowired
    private GroupUserService groupUserService;

}
