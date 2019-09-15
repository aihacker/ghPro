package wxgh.wx.admin.web.union.advice;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import pub.spring.web.mvc.ActionResult;
import pub.spring.web.mvc.model.Model;
import wxgh.app.sys.task.WeixinPush;
import wxgh.data.pub.push.ReplyPush;
import wxgh.entity.union.innovation.InnovateAdvice;
import wxgh.entity.union.innovation.InnovateApply;
import wxgh.param.union.innovation.QueryInnovateAdvice;
import wxgh.sys.service.weixin.union.innovation.InnovateAdviceService;
import wxgh.sys.service.weixin.union.innovation.InnovateApplyService;

import java.util.Date;

/**
 * Created by Administrator on 2017/6/7.
 */
@Controller
public class ShowController {

    @Autowired
    private WeixinPush weixinPush;

    public static final String[] types = {"技能", "营销", "服务", "管理", "其他"};

    @RequestMapping
    public void index(Integer id, Model model) {
        QueryInnovateAdvice query = new QueryInnovateAdvice();
        query.setId(id);
        InnovateAdvice advice = innovateAdviceService.getOne(query);
        advice.setTypeName(getType(advice.getType()));

        model.put("a", advice);
    }

    @RequestMapping
    public ActionResult apply(InnovateApply apply) {
        apply.setAuditTime(new Date());
        Integer result = innovateApplyService.updateApply(apply);

        // 审核
        if(apply.getStatus() != null && apply.getId() != null){
            //回复消息，通知用户审核结果  纠正错误：1、未设置agentId;2、查询错误，未将更改的状态传递。
            apply = innovateApplyService.getUseridAndStatus(apply.getId());
            ReplyPush push = new ReplyPush(apply.getUserid(), apply.getStatus());
            push.setAgentId(1000003);
            push.setMsg("创新建议审核结果");
            weixinPush.reply(push);
        }

        return ActionResult.ok();
    }

    private String getType(Integer type) {
        String str;
        if (null == type) {
            str = "未知类型";
        } else {
            str = types[type - 1];
        }
        return str;
    }

    @Autowired
    private InnovateAdviceService innovateAdviceService;

    @Autowired
    private InnovateApplyService innovateApplyService;

}
