package wxgh.wx.admin.web.union.group;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import pub.spring.web.mvc.model.Model;
import wxgh.data.wxadmin.group.GroupInfo;
import wxgh.sys.service.wxadmin.union.GroupService;

/**
 * Created by Administrator on 2017/8/7.
 */
@Controller
public class MainController {

    @RequestMapping
    public void index() {

    }

    @RequestMapping
    public void show(Integer id, Model model) {
        GroupInfo group = groupService.getGroup(id);
        model.put("g", group);
    }

    @Autowired
    private GroupService groupService;

}
