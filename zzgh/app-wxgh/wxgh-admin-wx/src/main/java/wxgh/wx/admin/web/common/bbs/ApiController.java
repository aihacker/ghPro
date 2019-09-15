package wxgh.wx.admin.web.common.bbs;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import pub.spring.web.mvc.ActionResult;
import wxgh.app.consts.Status;
import wxgh.app.sys.task.WeixinPush;
import wxgh.data.common.bbs.BbsList;
import wxgh.data.pub.push.ReplyPush;
import wxgh.param.common.bbs.ArticleParam;
import wxgh.sys.service.weixin.common.bbs.ArticleService;

import java.util.List;

/**
 * Created by Administrator on 2017/8/24.
 */
@Controller
public class ApiController {

    @RequestMapping
    public ActionResult list(ArticleParam param) {
        param.setPageIs(true);
        List<BbsList> bbsLists = articleService.listBbs(param);

        return ActionResult.okRefresh(bbsLists, param);
    }

    @RequestMapping
    public ActionResult del(Integer id) {
        articleService.delete(id);
        return ActionResult.ok();
    }


    @RequestMapping
    public ActionResult apply(Integer id, Integer status) {
        articleService.updateStatus(id, status);

        if (status == 1) { //审核通过，发送推文消息
            weixinPush.bbs(id);
        }

        String userid = articleService.getArticleUserid(id);

        //回复消息，通知用户审核已通过
        ReplyPush push = new ReplyPush(userid, status == 1 ? Status.NORMAL.getStatus() : Status.FAILED.getStatus());
        push.setMsg("热点论坛审核结果");
        weixinPush.reply(push);

        return ActionResult.ok();
    }

    @Autowired
    private ArticleService articleService;

    @Autowired
    private WeixinPush weixinPush;
}
