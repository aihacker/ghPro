package wxgh.wx.admin.web.common.act;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import pub.spring.web.mvc.ActionResult;
import pub.spring.web.mvc.model.Model;
import wxgh.app.consts.Status;
import wxgh.app.session.bean.SeUser;
import wxgh.app.session.user.UserSession;
import wxgh.app.sys.task.WeixinPush;
import wxgh.data.pub.push.ReplyPush;
import wxgh.entity.common.ActApply;
import wxgh.param.common.act.ActApplyQuery;
import wxgh.sys.service.weixin.common.act.ActApplyService;
import wxgh.sys.service.weixin.pub.UserInfoService;

import java.util.ArrayList;
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
 * @Date 2017-08-08 15:35
 *----------------------------------------------------------
 */
@Controller
public class MainController {

    @Autowired
    private WeixinPush weixinPush;

    @RequestMapping
    public void index(){

    }

    @RequestMapping
    public void main(Model model) {

    }

    @RequestMapping
    public void detail(Model model, Integer id) {
        ActApplyQuery actApplyQuery = new ActApplyQuery();
        actApplyQuery.setId(id);
        ActApply actApply = actApplyService.getApply(actApplyQuery);
        model.put("act", actApply);
        model.put("thisStatus", actApply.getStatus());
    }

    @RequestMapping
    public ActionResult applyListRefresh(ActApplyQuery query) {
        ActionResult actionResult = ActionResult.ok();
        SeUser user = UserSession.getUser();
        //List<Integer> deptIds = userInfoService.getTestUserDeptIds(null);

        List<ActApply> actApplies = actApplyService.applyListRefresh(query);
        actionResult.setData(actApplies);

        /*
        if (!UserUtils.isInList(deptIds, user.getDeptid())) {
            List<ActApply> actApplies1 = new ArrayList<>();
            for (ActApply actApply : actApplies) {
                if (!UserUtils.isInList(deptIds, actApply.getDeptid())) {
                    actApplies1.add(actApply);
                }
            }
            Integer num = query.getNum() == null ? 15 : query.getNum();
            if (actApplies1.size() < num) {
                actApplies1 = actApplyService.getApplysNotInTargetDeptId(query, deptIds);
            }
            actionResult.setData(actApplies1);
        }*/
        return actionResult;
    }

    @RequestMapping
    public ActionResult applyListMore(ActApplyQuery query) {
        ActionResult actionResult = ActionResult.ok();
        SeUser user = UserSession.getUser();
//        List<Integer> deptIds = userInfoService.getTestUserDeptIds(null);

        List<ActApply> actApplies = new ArrayList<>();
        if (1 == query.getIsFirst()) {
            actApplies = actApplyService.applyListRefresh(query);
        } else if (0 == query.getIsFirst()) {
            actApplies = actApplyService.applyListMore(query);
        }
        actionResult.setData(actApplies);

//        if (!UserUtils.isInList(deptIds, user.getDeptid())) {
//            List<ActApply> applies1 = new ArrayList<>();
//            for (ActApply actApply : actApplies) {
//                if (!UserUtils.isInList(deptIds, actApply.getDeptid())) {
//                    applies1.add(actApply);
//                }
//            }
//            Integer num = query.getNum() == null ? 15 : query.getNum();
//            if (applies1.size() < num) {
//                applies1 = actApplyService.getApplysNotInTargetDeptId(query, deptIds);
//            }
//            actionResult.setData(applies1);
//        }
        return actionResult;
    }

    @RequestMapping
    public ActionResult shenhe(ActApply actApply) {
        ActionResult actionResult = ActionResult.ok();
        SeUser user = UserSession.getUser();
        actApply.setAuditUserid(user.getUserid());
        actApply.setAuditTime(new Date());
        actApplyService.apply(actApply);
        Integer integer = actApplyService.checkStatus(actApply.getId());
        Integer status = 0;

        //回复消息，通知用户审核已通过
        ReplyPush push = new ReplyPush(actApplyService.getUserid(actApply.getId()), status);
        push.setMsg("工会家园活动审核结果");

        if (integer > 0) {
            status = 1;
            actionResult.setMsg("success");
            push.setStatus(Status.NORMAL.getStatus());
        } else {
            actionResult.setMsg("fail");
            push.setStatus(Status.FAILED.getStatus());
        }

        push.setAgentId(1000005);

        weixinPush.reply(push);

        return actionResult;
    }


    @RequestMapping
    public ActionResult del(Integer id) {
        ActionResult actionResult = ActionResult.ok();
        Integer integer = actApplyService.del(id);
        if (null != integer) {
            if (integer > 0) {
                actionResult.setMsg("success");
            }
        } else {
            actionResult.setMsg("fail");
        }
        return actionResult;
    }

    @Autowired
    private ActApplyService actApplyService;
    @Autowired
    private UserInfoService userInfoService;
    
}

