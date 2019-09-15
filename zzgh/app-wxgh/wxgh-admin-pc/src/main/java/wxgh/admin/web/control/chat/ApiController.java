package wxgh.admin.web.control.chat;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import pub.spring.web.mvc.ActionResult;
import wxgh.data.chat.ChatModelInfo;
import wxgh.entity.chat.ChatGroupModel;
import wxgh.sys.service.admin.chat.ChatModelService;

import java.util.List;

/**
 * Created by Administrator on 2017/8/11.
 */
@Controller
public class ApiController {

    @RequestMapping
    public ActionResult list_model(Integer type) {
        List<ChatModelInfo> models = chatModelService.listModel(type);
        return ActionResult.okWithData(models);
    }

    @RequestMapping
    public ActionResult add_model(ChatGroupModel model) {
        chatModelService.addModel(model);
        return ActionResult.ok();
    }

    @RequestMapping
    public ActionResult del_model(String id) {
        chatModelService.delModel(id);
        return ActionResult.ok();
    }

    @RequestMapping
    public ActionResult update_model(ChatGroupModel model) {
        chatModelService.updateModel(model);
        return ActionResult.ok();
    }

    @Autowired
    private ChatModelService chatModelService;

}
