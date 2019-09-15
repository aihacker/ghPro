package wxgh.wx.web.pub.apply;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import pub.spring.web.mvc.model.Model;
import wxgh.entity.four.FourPropagate;
import wxgh.param.four.PropagateParam;
import wxgh.sys.service.weixin.four.FourPropagateService;

/**
 * Created by Administrator on 2016/10/9.
 */
@Controller
public class FourController {

    @Autowired
    private FourPropagateService fourPropagateService;

    @RequestMapping
    public void index(Model model, Integer id) {
        PropagateParam query = new PropagateParam();
        query.setId(id);
        FourPropagate fourPropagate = fourPropagateService.getPropagate(query);

        model.put("apply", fourPropagate);
    }

}
