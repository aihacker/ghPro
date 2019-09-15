package wxgh.admin.web.sport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import pub.spring.web.mvc.model.Model;
import wxgh.entity.pub.Dept;
import wxgh.sys.service.weixin.pub.DeptService;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * author：xlkai
 * date：2018/1/8
 * time：8:49
 * version：V1.0
 * Description：
 */
@Controller
public class MainController {

    @RequestMapping
    public void act(Model model) {
        List<Dept> depts = deptService.getCompanyList();
        model.put("depts", depts);
    }

    @Autowired
    private DeptService deptService;

}
