package wxgh.app.web.login;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import wxgh.app.session.bean.SeUser;
import wxgh.app.session.user.UserSession;
import wxgh.entity.pub.User;
import wxgh.sys.service.weixin.pub.UserService;

/**
 * Created by Administrator on 2017/7/28.
 */
@Controller
public class MainController {

    @RequestMapping
    public String pc_login(String userid) {
        User user = userService.getUser(userid);
        UserSession.setUser(new SeUser(user));
        return "redirect:/pub/index.html";
    }
    
    @Autowired
    private UserService userService;

}
