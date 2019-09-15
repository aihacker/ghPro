package wxgh.admin.web.tribe;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import pub.spring.web.mvc.model.Model;
import wxgh.app.sys.api.FileApi;
import wxgh.entity.pub.Dept;
import wxgh.entity.tribe.TribeAct;
import wxgh.param.entertain.place.QueryScoreParam;
import wxgh.sys.service.admin.tribe.PointService;
import wxgh.sys.service.admin.tribe.TribeActService;

import java.util.List;

/**
 * Created by cby on 2017/8/17.
 */
@Controller
public class MainController {

    @Autowired
    private PointService pointService;

    @Autowired
    private TribeActService tribeActService;

    @Autowired
    private FileApi fileApi;

    @RequestMapping
    public void index(Model model){

    }
    @RequestMapping
    public void integral(Model model,QueryScoreParam queryScore){
        List<TribeAct> allTribeAct = pointService.getAllTribeAct();
        model.put("acts",allTribeAct);
    }
    @RequestMapping
    public void preview(Model model){

    }
    @RequestMapping
    public void detail(Integer id,Model model){
        TribeAct act = tribeActService.getAct(id);
        List<Dept> list = tribeActService.getDept();
        model.put("act",act);
        model.addAttribute("list",list);
    }

    @RequestMapping
    public void add(Model model){
        List<Dept> list = tribeActService.getDept();
        model.addAttribute("list",list);
    }

    @RequestMapping
    public void new_send(Model model){

    }

    @RequestMapping
    public void new_list(Model model){

    }
}
