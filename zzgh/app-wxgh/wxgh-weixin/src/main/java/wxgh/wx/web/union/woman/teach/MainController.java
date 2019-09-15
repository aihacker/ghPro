package wxgh.wx.web.union.woman.teach;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import pub.spring.web.mvc.model.Model;
import wxgh.data.union.woman.teach.TeachShow;
import wxgh.sys.service.weixin.union.woman.TeachService;

/**
 * Created by Administrator on 2017/9/13.
 */
@Controller
public class MainController {

    @RequestMapping
    public void list() {

    }

    @RequestMapping
    public void show(Model model, Integer id) {
        TeachShow teachShow = teachService.show(id);
        model.put("t", teachShow);
    }

    @RequestMapping
    public void join() {
    }

    @Autowired
    private TeachService teachService;

}
