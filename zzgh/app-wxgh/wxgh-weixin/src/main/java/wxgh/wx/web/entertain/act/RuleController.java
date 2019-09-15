package wxgh.wx.web.entertain.act;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import pub.spring.web.mvc.ActionResult;
import wxgh.entity.entertain.act.ScoreRule;
import wxgh.sys.service.weixin.entertain.act.ScoreRuleService;

import java.util.List;

/**
 * Created by Administrator on 2017/7/19.
 */
@Controller
public class RuleController {

    /**
     * 获取系统全部积分规则列表
     *
     * @return
     */
    @RequestMapping
    public ActionResult list() {
        List<ScoreRule> rules = scoreRuleService.listRule();
        return ActionResult.okWithData(rules);
    }

    /**
     * 添加积分规则
     *
     * @param rule
     * @return
     */
    @RequestMapping
    public ActionResult add(ScoreRule rule) {
        scoreRuleService.addRule(rule);
        return ActionResult.ok();
    }

    @Autowired
    private ScoreRuleService scoreRuleService;

}
