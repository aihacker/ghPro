package wxgh.wx.web.pub.apply;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import pub.spring.web.mvc.model.Model;
import wxgh.entity.common.ActApply;
import wxgh.param.common.act.ActApplyQuery;
import wxgh.sys.service.weixin.common.act.ActApplyService;

/**
 * Created by Administrator on 2016/10/9.
 */
@Controller
public class ActController {

    @Autowired
    private ActApplyService actApplyService;

    @RequestMapping
    public void index(Model model, Integer id) {
        ActApplyQuery query = new ActApplyQuery();
        query.setId(id);
        ActApply actApply = actApplyService.getApply(query);

        model.put("apply", actApply);
    }

}
