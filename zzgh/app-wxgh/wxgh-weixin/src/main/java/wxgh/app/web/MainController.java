package wxgh.app.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import wxgh.app.sys.task.impl.SportAsyncImpl;
import wxgh.app.utils.FindParentId;
import wxgh.data.pub.user.UseridDeptMobile;
import wxgh.entity.pub.Dept;
import wxgh.entity.pub.User;
import wxgh.sys.service.weixin.pub.DeptService;
import wxgh.sys.service.weixin.pub.UserService;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Controller
public class MainController {


    @Autowired
    private UserService userService;

    @RequestMapping
    public void result() {
        UseridDeptMobile useridDeptMobile = new UseridDeptMobile();
        FindParentId findParentId = new FindParentId();
        useridDeptMobile.setDeptid(1);
        useridDeptMobile.setUserid("wxgh13392268428");
        useridDeptMobile.setMobile("13392268428");
        if (useridDeptMobile != null) {
            User user = userService.getUser(useridDeptMobile.getUserid());
            String deptId = user.getDepartment();
            if ("1".equals(user.getDepartment()) && user.getDeptid() == 1) {
                deptId = "1";
            }

            String[] tmp = findParentId.getSpiltIds(deptId);
            deptId = findParentId.find(tmp).toString();
            System.out.println(deptId);
        }
    }
}
