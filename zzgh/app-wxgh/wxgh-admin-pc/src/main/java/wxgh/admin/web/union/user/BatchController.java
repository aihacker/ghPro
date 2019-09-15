package wxgh.admin.web.union.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import pub.spring.web.mvc.ActionResult;
import wxgh.app.sys.excel.user.UserReadApi;
import wxgh.entity.pub.User;
import wxgh.sys.service.admin.union.UserBatchService;

import javax.servlet.http.HttpServletResponse;
import java.util.Collections;
import java.util.List;

/**
 * Created by Administrator on 2017/9/26.
 */
@Controller
public class BatchController {

    /**
     * 模版下载
     */
    @RequestMapping
    public void template(HttpServletResponse response) {
        userReadApi.setName("export_user_template");
        userReadApi.toExcel(Collections.emptyList());
        userReadApi.response(response);
    }

    @RequestMapping
    public void export(Integer deptid, HttpServletResponse response) {
        if (deptid == null) deptid = 1;
        List<User> users = userBatchService.batchUsers(deptid);
        String deptname = userBatchService.getDeptName(deptid);
        userReadApi.setName(deptname + "人员");
        userReadApi.toExcel(users);
        userReadApi.response(response);
    }

    @RequestMapping
    public ActionResult import_user(Integer deptid) {

        return ActionResult.ok();
    }

    @Autowired
    private UserReadApi userReadApi;

    @Autowired
    private UserBatchService userBatchService;
}
