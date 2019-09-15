package wxgh.admin.web.union.woman.mom;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import pub.spring.web.mvc.model.Model;
import wxgh.data.union.woman.mom.MomImage;
import wxgh.entity.union.woman.WomanMom;
import wxgh.sys.service.weixin.union.woman.MomService;

import java.util.List;

/**
 * Created by Administrator on 2017/9/13.
 */
@Controller
public class MainController {

    @Autowired
    private MomService momService;

    @RequestMapping
    public void list() {
    }

    @RequestMapping
    public void edit(Model model, Integer id){
        WomanMom woman = momService.getWomanMonById(id);
        String cover = woman.getCover();
        if(cover!=null){
            String[] images = cover.split(",");
            List<MomImage> imagePaths = momService.getImagePath(images);
            model.put("image",imagePaths);
        }
        model.put("mom",woman);
    }

    @RequestMapping
    public void yuyue(Model model, Integer id){
        String weeks = momService.getWeek(id);
        String[] split = weeks.split(",");
        model.put("week",Integer.parseInt(split[0]));
        model.put("id",id);
    }

}
