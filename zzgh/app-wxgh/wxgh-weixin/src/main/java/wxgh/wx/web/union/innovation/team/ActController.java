package wxgh.wx.web.union.innovation.team;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import pub.spring.web.mvc.ActionResult;
import wxgh.app.consts.Status;
import wxgh.app.session.user.UserSession;
import wxgh.data.entertain.act.ActList;
import wxgh.entity.entertain.act.Act;
import wxgh.param.entertain.act.ActParam;
import wxgh.sys.service.weixin.entertain.act.ActService;

import java.util.List;

/**
 * ----------------------------------------------------------
 * @Description
 * ----------------------------------------------------------
 * @Author Ape
 * ----------------------------------------------------------
 * @Email <16511660@qq.com>
 * ----------------------------------------------------------
 * @Date 2017-08-16  10:05
 * ----------------------------------------------------------
 */
@Controller
public class ActController {

    @Autowired
    private ActService actService;

    @RequestMapping
    public void index(){}

    @RequestMapping
    public ActionResult list(ActParam param) {
        param.setPageIs(true);
        param.setRowsPerPage(10);
        if (param.getRegular() == null) {
            param.setRegular(0);
        } else if (param.getRegular() == 2) {
            param.setUserid(UserSession.getUserid());
        }
        param.setStatus(Status.NORMAL.getStatus());
        param.setActType(Act.ACT_TYPE_TEAM);
        List<ActList> acts = actService.getTeamActList(param);

        return ActionResult.okRefresh(acts, param);
    }

}
