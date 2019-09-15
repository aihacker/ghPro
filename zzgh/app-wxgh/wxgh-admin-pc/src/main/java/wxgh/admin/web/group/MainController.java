package wxgh.admin.web.group;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import pub.dao.jdbc.PubDao;
import pub.spring.web.mvc.model.Model;
import wxgh.entity.group.MemberList;

import javax.xml.ws.RequestWrapper;

/**
 * @author hhl
 * @create 2017-08-22
 **/
@Controller
public class MainController {
    @RequestMapping
    public void list(){

    }

    @RequestMapping
    public void act(){}

    @RequestMapping
    public void show(){}

    @RequestMapping
    public void usershow(Model model,Integer id){
        String sql = "select gu.*,u.name as name,u.avatar as avatar from t_group_user gu join t_user u on gu.userid=u.userid where gu.id = ?";
        MemberList memberList = pubDao.query(MemberList.class,sql,id);
        model.put("user",memberList);
    }

    @Autowired
    private PubDao pubDao;
}
