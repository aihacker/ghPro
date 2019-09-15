package wxgh.wx.admin.web.beauty;

import com.weixin.Agent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import pub.spring.web.mvc.ActionResult;
import wxgh.app.consts.Status;
import wxgh.app.sys.task.PushAsync;
import wxgh.app.sys.task.WeixinPush;
import wxgh.data.pub.push.Push;
import wxgh.data.pub.push.ReplyPush;
import wxgh.data.wxadmin.suggest.SuggestList;
import wxgh.entity.party.beauty.Work;
import wxgh.param.party.beauty.WorkQuery;
import wxgh.param.union.suggest.ListParam;
import wxgh.sys.service.weixin.party.beauty.WorkService;

import java.util.List;

@Controller
public class ApiController {

    @Autowired
    private WeixinPush weixinPush;

    @Autowired
    private WorkService workService;

    @Autowired
    private PushAsync pushAsync;

    @RequestMapping
    public ActionResult list(WorkQuery param) {
        param.setPageIs(true);
        List<Work> suggestLists = workService.listWorks(param);
        return ActionResult.okRefresh(suggestLists, param);
    }

    @RequestMapping
    public ActionResult apply(Integer id, Integer status){
        workService.updateStatus(id, status);
        if(status == 1){
            //TODO 推送
            Push push = new Push();
            push.setAll(true);
//        List<String> us = new ArrayList<>();
//        us.add("15902064445");
//        push.setTousers(us);
            push.setAgentId(Agent.SHEYING.getAgentId());
            pushAsync.sendBySheYing(id, push);
        }
        //回复消息，通知用户审核已通过
        ReplyPush push = new ReplyPush(workService.getUserid(id), status);
        push.setMsg("魅美影像审核结果");
        if(status == 1)
            push.setStatus(Status.NORMAL.getStatus());
        else
            push.setStatus(Status.FAILED.getStatus());
        weixinPush.reply(push);
        return ActionResult.ok();


    }
}
