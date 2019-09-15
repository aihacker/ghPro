package wxgh.admin.web.control.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import pub.spring.web.mvc.ActionResult;
import wxgh.entity.admin.NewAdmin;
import wxgh.param.admin.control.AdminParam;
import wxgh.sys.service.admin.control.AdminService;

import java.util.List;

/**
 * Created by Administrator on 2017/8/22.
 */
@Controller
public class ApiController {

    @RequestMapping
    public ActionResult list_admin(AdminParam param) {
        param.setPageIs(true);

        List<NewAdmin> admins = adminService.listAdmin(param);

        return ActionResult.okAdmin(admins, param);
    }

    @RequestMapping
    public ActionResult del(String id) {
        adminService.delAdmin(id);
        return ActionResult.ok();
    }

    @RequestMapping
    public ActionResult add(NewAdmin admin) {
        adminService.addAdmin(admin);
        return ActionResult.ok();
    }

    @RequestMapping
    public ActionResult edit(NewAdmin admin) {
        adminService.editAdmin(admin);
        return ActionResult.ok();
    }

    @Autowired
    private AdminService adminService;

}
