package wxgh.admin.web.di.plan;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import pub.spring.web.mvc.ActionResult;
import wxgh.entity.party.di.DiPlan;
import wxgh.param.admin.di.plan.PlanParam;
import wxgh.sys.service.admin.di.PlanService;

import java.util.List;

/**
 * Created by Administrator on 2017/8/21.
 */
@Controller
public class ApiController {

    @RequestMapping
    public ActionResult list(PlanParam param) {
        param.setPageIs(true);
        // 2017-10-10 增加类型字段
        param.setType(DiPlan.Type.DI.getValue());
        List<DiPlan> plans = planService.listPlan(param);
        return ActionResult.okAdmin(plans, param);
    }

    @RequestMapping
    public ActionResult del(String id) {
        planService.delPlan(id);

        return ActionResult.ok();
    }

    @RequestMapping
    public ActionResult add(DiPlan plan) {
        // 2017-10-10 增加类型字段
        plan.setType(DiPlan.Type.DI.getValue());
        planService.addPlan(plan);
        return ActionResult.ok();
    }

    @Autowired
    private PlanService planService;

}
