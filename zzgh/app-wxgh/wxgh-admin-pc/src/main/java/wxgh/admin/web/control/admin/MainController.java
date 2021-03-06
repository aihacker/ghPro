package wxgh.admin.web.control.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import pub.spring.web.mvc.model.Model;
import wxgh.app.consts.Status;
import wxgh.entity.admin.NavCate;
import wxgh.param.pcadmin.NavCateParam;
import wxgh.sys.service.admin.control.NavService;

import java.util.List;

/**
 * Created by Administrator on 2017/8/22.
 */
@Controller
public class MainController {

    @RequestMapping
    public void list(Model model) {
        NavCateParam param = new NavCateParam();
        param.setStatus(Status.NORMAL.getStatus());
        List<NavCate> cates = navService.getNavCates(param);
        model.put("cates", cates);
    }

    @Autowired
    private NavService navService;

}
