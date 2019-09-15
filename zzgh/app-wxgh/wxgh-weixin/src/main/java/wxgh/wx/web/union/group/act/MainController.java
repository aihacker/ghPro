package wxgh.wx.web.union.group.act;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import pub.spring.web.mvc.ActionResult;
import wxgh.app.consts.Status;
import wxgh.app.session.user.UserSession;
import wxgh.data.entertain.act.ActList;
import wxgh.param.entertain.act.ActParam;
import wxgh.sys.service.weixin.entertain.act.ActService;

import java.util.List;

/**
 * Created by Administrator on 2017/8/15.
 */
@Controller
public class MainController {

    /**
     * 活动列表页面
     */
    @RequestMapping
    public void index() {

    }

    @RequestMapping
    public ActionResult list(ActParam param) {
        param.setPageIs(true);
        param.setActType(1);
        param.setRowsPerPage(10);
        param.setStatus(Status.NORMAL.getStatus());
        if (param.getRegular() == null) {
            param.setRegular(0);
        } else if (param.getRegular() == 2) {
            param.setUserid(UserSession.getUserid());
        }

        List<ActList> acts = actService.listAct(param);

        return ActionResult.okRefresh(acts, param);
    }

    /**
     * 取消活动
     *
     * @param id
     * @return
     */
    @RequestMapping
    public ActionResult cancel(Integer id) {
        actService.cancelAct(id);
        return ActionResult.ok();
    }

    @Autowired
    private ActService actService;

}
