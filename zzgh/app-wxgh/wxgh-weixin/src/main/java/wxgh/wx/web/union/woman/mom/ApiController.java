package wxgh.wx.web.union.woman.mom;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import pub.spring.web.mvc.ActionResult;
import wxgh.data.union.woman.mom.MomList;
import wxgh.data.union.woman.mom.YuyueList;
import wxgh.param.union.woman.MomParam;
import wxgh.param.union.woman.YuyueParam;
import wxgh.sys.service.weixin.union.woman.MomService;

import java.util.List;

/**
 * Created by Administrator on 2017/9/14.
 */
@Controller
public class ApiController {

    @RequestMapping
    public ActionResult list(MomParam param) {
        param.setPageIs(true);
        List<MomList> moms = momService.listMom(param);
        return ActionResult.okRefresh(moms, param);
    }

    @RequestMapping
    public ActionResult yuyue(YuyueParam param) {
        momService.yuyue(param);
        return ActionResult.ok();
    }

    @RequestMapping
    public ActionResult yuyue_list(Integer id) {
        List<YuyueList> yuyues = momService.yuyueList(id);
        return ActionResult.okWithData(yuyues);
    }

    @Autowired
    private MomService momService;

}
