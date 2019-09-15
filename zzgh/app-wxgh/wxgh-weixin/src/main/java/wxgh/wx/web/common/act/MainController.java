package wxgh.wx.web.common.act;

import com.weixin.Agent;
import com.weixin.WeixinException;
import com.weixin.utils.ApiList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import pub.spring.web.mvc.ActionResult;
import pub.spring.web.mvc.model.Model;
import pub.utils.StrUtils;
import wxgh.app.session.bean.SeUser;
import wxgh.app.session.user.UserSession;
import wxgh.app.sys.task.WeixinPush;
import wxgh.app.utils.WeixinUtils;
import wxgh.data.pub.push.ApplyPush;
import wxgh.entity.common.ActApply;
import wxgh.sys.service.weixin.common.act.ActApplyService;
import wxgh.sys.service.weixin.pub.WeixinDeptService;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

/**
 *----------------------------------------------------------
 * @Description 
 *----------------------------------------------------------
 * @Author  Ape
 *----------------------------------------------------------
 * @Email <16511660@qq.com>
 *----------------------------------------------------------
 * @Date 2017-08-01 08:59
 *----------------------------------------------------------
 */
@Controller
public class MainController {

    @Autowired
    private ActApplyService actApplyService;

    @Autowired
    private WeixinDeptService weixinDeptService;

    @Autowired
    private WeixinPush weixinPush;

    @RequestMapping
    public void index(Model model) throws WeixinException {

        WeixinUtils.sign(model, ApiList.getCloseApi());

//        User user = UserSession.getUser();
//        if (user != null) {
//            model.put("deptinfo", weixinDeptService.getDeptStr(user.getUserid()));
//        }
    }

    @RequestMapping
    public ActionResult add(ActApply actApply, HttpServletRequest request) {

        SeUser user = UserSession.getUser();
        if (user == null) {
            return ActionResult.error("对不起，你还没有登录呢");
        }

        if (actApply.getActTime() == null) {
            return ActionResult.error("活动时间不能为空哦");
        }
        if (actApply.getJoinNumb() == null) {
            return ActionResult.error("参加人数不能为空哦");
        }
        if (actApply.getActCost() == null) {
            return ActionResult.error("预算金额不能为空哦");
        }
        if (StrUtils.empty(actApply.getMobile())) {
            return ActionResult.error("联系号码不能为空哦");
        }
        if (StrUtils.empty(actApply.getAddress())) {
            return ActionResult.error("活动进行地址信息不能为空哦");
        }

        actApply.setDeptid(user.getDeptid());
        actApply.setStatus(0);
        actApply.setApplyTime(new Date());
        actApply.setUserid(user.getUserid());

        Integer actId = actApplyService.addApply(actApply);

        // TODO 推送消息
        // 推送消息
        ApplyPush push = new ApplyPush(ApplyPush.Type.HOME_ACT, user.getUserid(), actId.toString());
        push.setAgentId(Agent.ADMIN.getAgentId());
        push.setMsg("工会家园活动申请");
        weixinPush.apply(push);

        return ActionResult.ok();
    }
    
}

