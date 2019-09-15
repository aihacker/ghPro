package wxgh.wx.admin.web.union.suggest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import pub.spring.web.mvc.ActionResult;
import wxgh.app.consts.Status;
import wxgh.app.sys.task.WeixinPush;
import wxgh.data.pub.push.ReplyPush;
import wxgh.data.wxadmin.suggest.SuggestList;
import wxgh.param.union.suggest.ListParam;
import wxgh.sys.service.wxadmin.union.SuggestService;

import java.util.List;

/**
 * Created by Administrator on 2017/8/8.
 */
@Controller
public class ApiController {

    @Autowired
    private WeixinPush weixinPush;

    @RequestMapping
    public ActionResult list(ListParam param) {
        param.setPageIs(true);
        List<SuggestList> suggestLists = suggestService.listSuggest(param);
        return ActionResult.okRefresh(suggestLists, param);
    }

    @RequestMapping
    public ActionResult delete(Integer id) {
        suggestService.delete(id);
        return ActionResult.ok();
    }

    @RequestMapping
    public ActionResult apply(Integer id, Integer status){
        suggestService.apply(id, status);
        //回复消息，通知用户审核已通过
        ReplyPush push = new ReplyPush(suggestService.getUserid(id), status);
        push.setMsg("会员提案审核结果");
        push.setAgentId(1000005);
        if(status == 1)
            push.setStatus(Status.NORMAL.getStatus());
        else
            push.setStatus(Status.FAILED.getStatus());
        weixinPush.reply(push);
        return ActionResult.ok();
    }

    @Autowired
    private SuggestService suggestService;

}
