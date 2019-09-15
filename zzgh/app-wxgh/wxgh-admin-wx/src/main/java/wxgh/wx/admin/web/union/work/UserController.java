package wxgh.wx.admin.web.union.work;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import pub.spring.web.mvc.ActionResult;
import pub.type.RefData;
import wxgh.entity.union.innovation.InnovateApply;
import wxgh.entity.union.innovation.WorkUser;
import wxgh.param.union.innovation.work.WorkUserQuery;
import wxgh.sys.service.weixin.union.innovation.WorkUserService;

import java.util.List;

/**
 * Created by Administrator on 2017/6/8.
 */
@Controller
public class UserController {

    @RequestMapping
    public void index() {
    }

    @RequestMapping
    public ActionResult list(WorkUserQuery query) {
        query.setStatus(1);
        query.setWorkType(InnovateApply.TYPE_SHOP);
        Integer count = workUserService.countUser(query);

        query.setPageIs(true);
        query.setRowsPerPage(10);
        query.setTotalCount(count);

        List<WorkUser> users = workUserService.getUsers(query);
        RefData refData = new RefData();
        refData.put("users", users);
        refData.put("total", query.getPages());
        refData.put("current", query.getCurrentPage());

        return ActionResult.ok(null, refData);
    }

    @Autowired
    private WorkUserService workUserService;

}
