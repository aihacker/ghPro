package wxgh.wx.web.pub.apply;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import pub.spring.web.mvc.model.Model;
import wxgh.entity.union.innovation.WorkInnovate;
import wxgh.param.union.innovation.work.WorkInnovateQuery;
import wxgh.sys.service.weixin.union.innovation.WorkInnovateService;

/**
 * Created by Administrator on 2016/10/9.
 */
@Controller
public class InnovationController {

    @Autowired
    private WorkInnovateService workInnovateService;

    @RequestMapping
    public void index(Model model, Integer id) {
        WorkInnovateQuery workInnovateQuery = new WorkInnovateQuery();
        workInnovateQuery.setId(id);
        WorkInnovate workInnovate = workInnovateService.getInnovates(workInnovateQuery).get(0);
        model.put("apply", workInnovate);
    }

}
