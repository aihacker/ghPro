package wxgh.wx.web.union.innovation.declare;


import com.weixin.Agent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import pub.spring.web.mvc.ActionResult;
import wxgh.app.session.bean.SeUser;
import wxgh.app.session.user.UserSession;
import wxgh.app.sys.task.WeixinPush;
import wxgh.app.utils.ObjectTransUtils;
import wxgh.data.pub.push.ApplyPush;
import wxgh.entity.union.innovation.InnovateAdvice;
import wxgh.entity.union.innovation.InnovateApply;
import wxgh.sys.service.weixin.union.innovation.InnovateAdviceService;
import wxgh.sys.service.weixin.union.innovation.InnovateApplyService;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

/**
 *----------------------------------------------------------
 * @Description 
 *----------------------------------------------------------
 * @Author  Ape<阿佩>
 *----------------------------------------------------------
 * @Email <16511660@qq.com>
 *----------------------------------------------------------
 * @Date 2017-07-19 17:40
 *----------------------------------------------------------
 */
@Controller
public class AdviceController {

    @Autowired
    private InnovateAdviceService innovateAdviceService;

    @Autowired
    private InnovateApplyService innovateApplyService;

    @Autowired
    private WeixinPush weixinPush;

    @RequestMapping
    public ActionResult add(InnovateAdvice innovateAdvice, HttpServletRequest request) {

        SeUser user = UserSession.getUser();
        if (user == null) {
            return ActionResult.error("登录后重试！");
        }

        //保存申请记录
        Integer applyId = innovateApplyService.add(ObjectTransUtils.SeUserToUser(user), InnovateApply.TYPE_ADVICE, null);

        innovateAdvice.setPid(applyId);
        innovateAdvice.setDeptid(user.getDeptid());
        innovateAdvice.setAddTime(new Date());
        innovateAdvice.setSeeNumb(0);
        innovateAdvice.setZanNumb(0);
        Integer id = innovateAdviceService.addOne(innovateAdvice);
        if (null != id) {

            //推送消息
            ApplyPush push = new ApplyPush(ApplyPush.Type.INNOVATION_ADVICE, user.getUserid(), id.toString());
            push.setAgentId(Agent.ADMIN.getAgentId());
            push.setMsg("创新建议");
            weixinPush.apply(push);

            return ActionResult.ok("success");
        } else {
            return ActionResult.error("fail");
        }
    }

}
