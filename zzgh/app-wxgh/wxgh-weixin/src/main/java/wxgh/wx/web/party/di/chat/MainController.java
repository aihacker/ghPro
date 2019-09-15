package wxgh.wx.web.party.di.chat;

import com.weixin.WeixinException;
import com.weixin.utils.ApiList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import pub.spring.web.mvc.model.Model;
import wxgh.app.session.user.UserSession;
import wxgh.app.utils.WeixinUtils;
import wxgh.data.chat.ChatGroupInfo;
import wxgh.sys.service.weixin.chat.ChatGroupService;

/**
 * Created by Administrator on 2017/8/21.
 */
@Controller
public class MainController {

    @RequestMapping
    public void list() {

    }

    @RequestMapping
    public void show(Integer id, Model model) throws WeixinException {
        ChatGroupInfo group = chatGroupService.groupInfo(id);
        model.put("g", group);
        WeixinUtils.sign(model, ApiList.getImageApi());
    }

    @RequestMapping
    public void userlist(Integer id, Model model) {
        Integer type = chatGroupService.hasPermission(id, UserSession.getUserid());
        model.put("permission", type);
    }

    @Autowired
    private ChatGroupService chatGroupService;

}
