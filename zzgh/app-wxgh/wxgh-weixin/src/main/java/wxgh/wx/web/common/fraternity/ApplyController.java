package wxgh.wx.web.common.fraternity;

import com.weixin.Agent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import pub.spring.web.mvc.ActionResult;
import wxgh.app.session.bean.SeUser;
import wxgh.app.session.user.UserSession;
import wxgh.app.sys.task.WeixinPush;
import wxgh.data.pub.push.ApplyPush;
import wxgh.entity.common.UserFamily;
import wxgh.entity.common.fraternity.FraternityApply;
import wxgh.sys.service.weixin.common.fraternity.FraternityService;
import wxgh.sys.service.weixin.common.fraternity.UserFamilyService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.List;

/**
 *----------------------------------------------------------
 * @Description 
 *----------------------------------------------------------
 * @Author  Ape
 *----------------------------------------------------------
 * @Email <16511660@qq.com>
 *----------------------------------------------------------
 * @Date 2017-08-01 11:15
 *----------------------------------------------------------
 */
@Controller
public class ApplyController {

    public static final Integer STEP1 = 1; //步骤流程
    public static final Integer STEP2 = 2; //步骤流程
    public static final Integer STEP3 = 3; //步骤流程

    @Autowired
    private UserFamilyService userFamilyService;

    @Autowired
    private FraternityService fraternityService;

    @Autowired
    private WeixinPush weixinPush;

    @RequestMapping
    public ActionResult add1(FraternityApply apply, HttpSession session) {

        SeUser user = UserSession.getUser();
        if (user == null) {
            return ActionResult.error("你还没有登录呢？");
        }

        apply.setUserId(user.getUserid());
        apply.setStep(STEP1);
        apply.setApplyTime(new Date());
        apply.setStatus(0);
        apply.setDeptId(user.getDeptid());

        fraternityService.insertOrUpdate(apply);

        return ActionResult.ok();
    }


    @RequestMapping
    public ActionResult add2(FraternityApply apply, HttpSession session) {

        SeUser user = UserSession.getUser();
        if (user == null) {
            return ActionResult.error("你还没有登录呢？");
        }

        apply.setUserId(user.getUserid());
        apply.setStep(STEP2);
        apply.setApplyTime(new Date());
        apply.setStatus(0);

        fraternityService.updateApply(apply);

        return ActionResult.ok();
    }

    @RequestMapping
    public ActionResult add3(FraternityApply apply, HttpServletRequest request) {

        SeUser user = UserSession.getUser();
        if (user == null) {
            return ActionResult.error("你还没有登录呢？");
        }

        List<UserFamily> userFamilies = apply.getFamilys();

        if (userFamilies == null) {
            return ActionResult.error("家庭成员不能为空哦");
        }

        for (int i = 0; i < userFamilies.size(); i++) {
            userFamilies.get(i).setUserId(user.getUserid());
        }

        userFamilyService.insertOrUpdate(userFamilies);

        apply.setUserId(user.getUserid());
        apply.setStep(STEP3);
        apply.setApplyTime(new Date());
        apply.setStatus(0);
        fraternityService.updateApply(apply);

        // TODO 推送消息
        //推送消息
        ApplyPush push = new ApplyPush(ApplyPush.Type.FRATERNITY, user.getUserid(),user.getUserid());
        push.setAgentId(Agent.ADMIN.getAgentId());
        push.setMsg("互助会入会申请");
        weixinPush.apply(push);

        return ActionResult.ok();
    }

}

