package wxgh.wx.admin.web.common.fraternity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import pub.spring.web.mvc.ActionResult;
import pub.spring.web.mvc.model.Model;
import wxgh.app.consts.Status;
import wxgh.app.session.bean.SeUser;
import wxgh.app.session.user.UserSession;
import wxgh.app.sys.task.WeixinPush;
import wxgh.data.FAShenheData;
import wxgh.data.pub.push.ReplyPush;
import wxgh.entity.common.fraternity.FraternityApply;
import wxgh.param.common.fraternity.ApplyParam;
import wxgh.sys.service.weixin.common.fraternity.FraternityService;
import wxgh.sys.service.weixin.common.fraternity.UserFamilyService;
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
 * @Date 2017-08-08 14:23
 *----------------------------------------------------------
 */
@Controller
public class MainController {

    @Autowired
    private UserFamilyService userFamilyService;

    @Autowired
    private WeixinPush weixinPush;

    @RequestMapping
    public void index(){

    }

    @RequestMapping
    public void main(Model model) {

    }

    @RequestMapping
    public void detail(Model model, String userid) {
        ApplyParam applyQuery = new ApplyParam();
        applyQuery.setUserid(userid);
        FraternityApply fraternityApply = fraternityService.getApply(applyQuery);
        model.put("fraternity", fraternityApply);
        model.put("families", userFamilyService.getUFs(userid));
        model.put("thisStatus", fraternityApply.getStatus());
    }


    @RequestMapping
    public ActionResult applyListRefresh(ApplyParam query) {
        ActionResult actionResult = ActionResult.ok();
        SeUser user = UserSession.getUser();
        //List<Integer> deptIds = userInfoService.getTestUserDeptIds(null);

        List<FraternityApply> fraternityApplies = fraternityService.applyListRefresh(query);
        actionResult.setData(fraternityApplies);
/*
        if (!UserUtils.isInList(deptIds, user.getDeptid())) {
            List<FraternityApply> fraternityApplies1 = new ArrayList<>();
            for (FraternityApply fraternityApply : fraternityApplies) {
                if (!UserUtils.isInList(deptIds, fraternityApply.getDeptId())) {
                    fraternityApplies1.add(fraternityApply);
                }
            }
            Integer num = query.getNum() == null ? 15 : query.getNum();
            if (fraternityApplies1.size() < num) {
                fraternityApplies1 = fraternityService.getApplysNotInTargetDeptId(query, deptIds);
            }
            actionResult.setData(fraternityApplies1);
        }*/

        return actionResult;
    }

    @RequestMapping
    public ActionResult applyListMore(ApplyParam query) {
        ActionResult actionResult = ActionResult.ok();
        SeUser user = UserSession.getUser();
       // List<Integer> deptIds = userInfoService.getTestUserDeptIds(null);

        List<FraternityApply> applies = new ArrayList<>();
        if (1 == query.getIsFirst()) {
            applies = fraternityService.applyListRefresh(query);
        } else if (0 == query.getIsFirst()) {
            applies = fraternityService.applyListMore(query);
        }
        actionResult.setData(applies);
/*
        if (!UserUtils.isInList(deptIds, user.getDeptid())) {
            List<FraternityApply> applies1 = new ArrayList<>();
            for (FraternityApply voted : applies) {
                if (!UserUtils.isInList(deptIds, voted.getDeptId())) {
                    applies1.add(voted);
                }
            }
            Integer num = query.getNum() == null ? 15 : query.getNum();
            if (applies1.size() < num) {
                applies1 = fraternityService.getApplysNotInTargetDeptId(query, deptIds);
            }
            actionResult.setData(applies1);
        }
*/
        return actionResult;
    }

    @RequestMapping
    public ActionResult shenhe(FAShenheData faShenheData) {
        ActionResult actionResult = ActionResult.ok();
        faShenheData.setAuditTime(new Date());
        Integer integer = fraternityService.apply(faShenheData);
        Integer status = 0;
        if (null != integer) {
            if (integer > 0) {
                actionResult.setMsg("success");
                status = 1;
            }
        } else {
            actionResult.setMsg("fail");
        }

        //回复消息，通知用户审核已通过
        ReplyPush push = new ReplyPush(fraternityService.getUserid(faShenheData.getId()), status == 1 ? Status.NORMAL.getStatus() : Status.FAILED.getStatus());
        push.setMsg("互助会入会申请审核结果");
        weixinPush.reply(push);

        return actionResult;
    }


    @RequestMapping
    public ActionResult del(Integer id) {
        ActionResult actionResult = ActionResult.ok();
        Integer integer = fraternityService.del(id);
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
    private FraternityService fraternityService;
    @Autowired
    private UserInfoService userInfoService;
    
}

