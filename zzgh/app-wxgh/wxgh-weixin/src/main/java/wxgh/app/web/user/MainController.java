package wxgh.app.web.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import pub.spring.web.mvc.ActionResult;
import pub.type.RefData;
import wxgh.entity.pub.user.UserDept;
import wxgh.sys.service.weixin.pub.ListUserService;

import java.util.List;

/**
 * Created by Administrator on 2017/8/18.
 */
@Controller
public class MainController {

    @RequestMapping
    public ActionResult list(Integer id) {
        if (id == null) {
            id = 0;
        }

        RefData refData = new RefData();
//wxgh/app/user/list.json
        List<UserDept> users = userService.getUser(id);
        refData.put("users", users);

        List<UserDept> depts = userService.getDept(id);
        refData.put("depts", depts);

        return ActionResult.okWithData(refData);
    }

    @RequestMapping
    public ActionResult search(String key) {
        List<UserDept> users = userService.searchUser(key);

        RefData refData = new RefData();
        refData.put("users", users);

        List<UserDept> depts = userService.searchDept(key);
        refData.put("depts", depts);

        return ActionResult.okWithData(refData);
    }

    @Autowired
    private ListUserService userService;
}
