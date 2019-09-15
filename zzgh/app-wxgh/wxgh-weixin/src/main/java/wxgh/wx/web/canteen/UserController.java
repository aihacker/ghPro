package wxgh.wx.web.canteen;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import pub.spring.web.mvc.ActionResult;
import pub.spring.web.mvc.model.Model;
import wxgh.app.consts.Status;
import wxgh.app.session.user.UserSession;
import wxgh.data.union.group.user.UserData;
import wxgh.param.union.group.user.ListParam;
import wxgh.sys.service.weixin.canteen.CanteenUserService;
import wxgh.sys.service.weixin.union.group.GroupUserService;

import java.util.List;

/**
 * Created by Administrator on 2017/7/20.
 */
@Controller
public class UserController {

    @RequestMapping
    public void index(Integer id, Model model) {
        Integer userType = canteenUserService.userType(UserSession.getUserid(), id);
        model.put("userType", userType == null ? 5 : userType);
    }

    @RequestMapping
    public void show(Model model, Integer id) {
        model.put("groupId", id);
    }

    @RequestMapping
    public ActionResult list(ListParam param) {
        param.setPageIs(true);
        param.setRowsPerPage(10);
        param.setStatus(Status.NORMAL.getStatus());

        List<UserData> users = canteenUserService.listUser(param);

        return ActionResult.okRefresh(users, param);
    }

    @RequestMapping
    public ActionResult delete(Integer id) {
        canteenUserService.verify_permisstion(UserSession.getUserid(), id);

        canteenUserService.deleteUser(id);
        return ActionResult.ok();
    }

    @Autowired
    private CanteenUserService canteenUserService;

}
