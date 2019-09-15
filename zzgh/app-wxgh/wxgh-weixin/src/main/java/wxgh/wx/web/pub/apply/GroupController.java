package wxgh.wx.web.pub.apply;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import pub.spring.web.mvc.model.Model;
import wxgh.entity.union.group.Group;
import wxgh.sys.service.weixin.union.innovation.Group2Service;

@Controller
public class GroupController {

    @Autowired
    private Group2Service group2Service;

    @RequestMapping
    public void index(Model model, Integer id) {
        Group group = group2Service.getGroupDetail(id);
//        if (group.getAddTime() != null) {
//            group.setAddTime(DateUtils.formatDate(group.getAddTime(), "yyyy-MM-dd HH:mm"));
//        }
//        group.setAvatar(PathUtils.getImagePath(group.getAvatar()));
        model.put("group", group);
    }

}
