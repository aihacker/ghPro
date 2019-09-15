package wxgh.admin.web.manage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import pub.spring.web.mvc.model.Model;
import wxgh.entity.pub.Dept;
import wxgh.sys.service.admin.tribe.TribeActService;

import java.util.List;

@Controller
public class MainController {

    @Autowired
    private TribeActService tribeActService;

    @RequestMapping
    public void judge(){

    }

    @RequestMapping
    public void index(){

    }

    @RequestMapping
    public void add(Model model){
        List<Dept> list = tribeActService.getDept();
        model.addAttribute("list",list);
    }

}
