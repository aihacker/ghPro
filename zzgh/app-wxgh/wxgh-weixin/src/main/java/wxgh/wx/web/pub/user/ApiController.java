package wxgh.wx.web.pub.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import pub.spring.web.mvc.ActionResult;
import wxgh.app.session.user.UserSession;
import wxgh.sys.service.weixin.pub.UserService;

/**
 * Created with IntelliJ IDEA.
 * author：xlkai
 * date：2018/1/5
 * time：14:40
 * version：V1.0
 * Description：
 */
@Controller
public class ApiController {

    @RequestMapping
    public ActionResult edit_sport(Integer status) {
        userService.updateSportPush(UserSession.getUserid(), status);
        return ActionResult.ok();
    }

    @Autowired
    private UserService userService;

}
