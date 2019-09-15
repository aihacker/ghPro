package wxgh.admin.web.pub.group;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import pub.spring.web.mvc.model.Model;
import wxgh.data.pcadmin.pub.ChatGroupList;
import wxgh.param.admin.pub.ChatGroupParam;
import wxgh.sys.service.admin.chat.ChatGroupService;

import java.util.List;

/**
 * Created by Administrator on 2017/8/18.
 */
@Controller
public class MainController {

    @RequestMapping
    public void list() {
    }

    @RequestMapping
    public void list_user(ChatGroupParam param, Model model) {
        List<ChatGroupList> groups = chatGroupService.listGroup(param);
        model.put("groups", groups);
    }

    @Autowired
    private ChatGroupService chatGroupService;
}
