package wxgh.wx.admin.web.union.group;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import pub.spring.web.mvc.ActionResult;
import wxgh.data.union.group.GroupList;
import wxgh.param.union.group.ListParam;
import wxgh.sys.service.wxadmin.union.GroupService;

import java.util.List;

/**
 * Created by Administrator on 2017/8/7.
 */
@Controller
public class ApiController {

    @RequestMapping
    public ActionResult list_group(ListParam param) {
        List<GroupList> groups = groupService.listGroup(param);
        return ActionResult.okRefresh(groups, param);
    }

    @RequestMapping
    public ActionResult delete(Integer id) {
        groupService.deleteGroup(id);
        return ActionResult.ok();
    }

    @RequestMapping
    public ActionResult apply(Integer id, Integer status) {
        groupService.apply(id, status);
        return ActionResult.ok();
    }

    @Autowired
    private GroupService groupService;
}
