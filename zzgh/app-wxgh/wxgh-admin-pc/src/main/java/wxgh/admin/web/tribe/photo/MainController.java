package wxgh.admin.web.tribe.photo;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import pub.spring.web.mvc.model.Model;

/**
 * Created by cby on 2017/8/29.
 */
@Controller
public class MainController {
    @RequestMapping
    public void index(Model model){

    }

    @RequestMapping
    public void result(Model model){

    }

    @RequestMapping
    public void show(Model model,Integer id){
        model.put("id",id);
    }
}
