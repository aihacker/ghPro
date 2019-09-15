package wxgh.admin.web.di.plan;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import pub.spring.web.mvc.model.Model;
import wxgh.data.pub.NameValue;
import wxgh.sys.service.admin.di.PlanService;

import java.util.List;

/**
 * Created by Administrator on 2017/8/21.
 */
@Controller
public class MainController {

    @RequestMapping
    public void list(Model model) {
        List<NameValue> groups = planService.listGroup();
        model.put("groups", groups);
    }

    @Autowired
    private PlanService planService;

}
