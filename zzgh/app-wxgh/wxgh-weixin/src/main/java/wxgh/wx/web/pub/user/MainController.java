package wxgh.wx.web.pub.user;

import com.weixin.WeixinException;
import com.weixin.utils.ApiList;
import com.weixin.utils.MenuList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import pub.dao.jdbc.PubDao;
import pub.spring.web.mvc.model.Model;
import wxgh.app.session.user.UserSession;
import wxgh.app.utils.WeixinUtils;
import wxgh.data.pub.user.UserInfo;
import wxgh.entity.pub.Dept;
import wxgh.entity.pub.User;
import wxgh.sys.service.weixin.pub.UserService;

/**
 * Created by Administrator on 2017/7/13.
 */
@Controller
public class MainController {

    //个人中心
    @RequestMapping
    public void person(Model model) {
    }

    @RequestMapping
    public void notif(Model model) {
        model.put("status", userService.getSportPush(UserSession.getUserid()));
    }

    //我的资料
    @RequestMapping
    public void myinfo(Model model) {
        String id = UserSession.getUserid();
        String sql = "select * from t_user where userid = '" + id + "'";
        User user = pubDao.query(User.class, sql);
        model.put("userList", user);
        Integer deptId = user.getDeptid();
        String sql2 = "select * from t_dept where id =" + deptId;
        Dept dept = pubDao.query(Dept.class, sql2);
        model.put("deptName", dept.getName());
    }

    @RequestMapping
    public void show(String userid, Model model) throws WeixinException {
        UserInfo userInfo = userService.getUserInfo(userid);
        model.put("u", userInfo);
        WeixinUtils.sign(model, ApiList.getApiList(), MenuList.getShareMenuList());
    }

    @Autowired
    private UserService userService;

    @Autowired
    private PubDao pubDao;

}
