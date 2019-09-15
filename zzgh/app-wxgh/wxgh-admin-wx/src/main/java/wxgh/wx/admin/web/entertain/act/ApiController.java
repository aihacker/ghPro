package wxgh.wx.admin.web.entertain.act;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import pub.spring.web.mvc.ActionResult;
import wxgh.data.wxadmin.act.ActList;
import wxgh.entity.entertain.act.Act;
import wxgh.param.entertain.act.ActParam;
import wxgh.sys.service.wxadmin.act.ActService;

import java.util.List;

/**
 * Created by Administrator on 2017/9/6.
 */
@Controller
public class ApiController {

    @RequestMapping
    public ActionResult list(ActParam param) {

        param.setPageIs(true);
        param.setActType(Act.ACT_TYPE_BIG);
        List<ActList> acts = actService.listAct(param);

        return ActionResult.okRefresh(acts, param);
    }

    @RequestMapping
    public ActionResult delete(Integer id) {
        if (id == null) {
            return ActionResult.error("请选择需要删除的活动！");
        }
        actService.delAct(id);
        return ActionResult.ok();
    }

    @RequestMapping
    public ActionResult apply(Integer id, Integer status) {
        actService.apply(id, status);
        return ActionResult.ok();
    }

    @Autowired
    private ActService actService;

}
