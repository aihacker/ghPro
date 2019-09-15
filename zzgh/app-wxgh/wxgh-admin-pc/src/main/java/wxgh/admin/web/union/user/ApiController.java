package wxgh.admin.web.union.user;

import com.weixin.WeixinException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import pub.spring.web.mvc.ActionResult;
import wxgh.data.pub.user.DeptUserList;
import wxgh.data.wxadmin.user.DeptList;
import wxgh.entity.pub.Dept;
import wxgh.entity.pub.User;
import wxgh.param.pcadmin.user.UserListParam;
import wxgh.sys.service.wxadmin.union.DeptUserService;

import java.util.List;

/**
 * Created by Administrator on 2017/9/25.
 */
@Controller
public class ApiController {

    @RequestMapping
    public ActionResult list_user(UserListParam param) {
        param.setPageIs(true);
        List<DeptUserList> userLists = deptUserService.listUsers(param);
        return ActionResult.okAdmin(userLists, param);
    }

    @RequestMapping
    public ActionResult update_user(User user) {
        deptUserService.updateUser(user);
        return ActionResult.ok();
    }

    @RequestMapping
    public ActionResult del_user(String id) {
        deptUserService.delUser(id);
        return ActionResult.ok();
    }

    @RequestMapping
    public ActionResult list_dept(Integer deptid) {
        if (deptid == null) {
            deptid = 1;
        }
        List<DeptList> deptLists = deptUserService.listDept(deptid);
        return ActionResult.okWithData(deptLists);
    }

    @RequestMapping
    public ActionResult add_dept(Dept dept) {
        try {
            deptUserService.updateDept(dept);
        } catch (WeixinException e) {
            return ActionResult.error("新增失败！");
        }
        return ActionResult.ok();
    }

    @RequestMapping
    public ActionResult del_dept(Integer id) {
        deptUserService.delDept(id);
        return ActionResult.ok();
    }

    @Autowired
    private DeptUserService deptUserService;

}
