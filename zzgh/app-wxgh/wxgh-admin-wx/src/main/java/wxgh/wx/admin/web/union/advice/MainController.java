package wxgh.wx.admin.web.union.advice;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import pub.spring.web.mvc.ActionResult;
import pub.type.RefData;
import wxgh.app.session.bean.SeUser;
import wxgh.app.session.user.UserSession;
import wxgh.data.union.innovation.AdviceList;
import wxgh.param.union.innovation.AdviceParam;
import wxgh.sys.service.weixin.union.innovation.InnovateAdviceService;

import java.util.List;

/**
 * Created by Administrator on 2017/6/7.
 */
@Controller
public class MainController {

    @RequestMapping
    public void index() {

    }

    @RequestMapping
    public ActionResult list(AdviceParam param) {
        SeUser user = UserSession.getUser();
//        param.setNodeptid(user.getDeptid());

        Integer count = innovateAdviceService.countAdminAdvice(param);

        param.setRowsPerPage(15);
        param.setPageIs(true);
        param.setTotalCount(count);

        List<AdviceList> adviceLists = innovateAdviceService.getAdminAdvice(param);

        RefData refData = new RefData();
        refData.put("advices", adviceLists);
        refData.put("total", param.getPages());
        refData.put("current", param.getCurrentPage());

        return ActionResult.ok(null, refData);
    }

    @Autowired
    private InnovateAdviceService innovateAdviceService;

}
