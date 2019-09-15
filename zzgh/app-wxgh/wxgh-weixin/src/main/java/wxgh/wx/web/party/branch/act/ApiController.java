package wxgh.wx.web.party.branch.act;

import com.libs.common.data.DateUtils;
import com.libs.common.type.TypeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import pub.spring.web.mvc.ActionResult;
import wxgh.app.session.user.UserSession;
import wxgh.data.party.branch.PartyActList;
import wxgh.data.party.branch.PartyJoinList;
import wxgh.entity.party.party.PartyAct;
import wxgh.entity.party.party.PartyActJoin;
import wxgh.param.party.branch.ActListParam;
import wxgh.param.party.tribe.JoinParam;
import wxgh.sys.service.weixin.party.branch.PartyActService;

import java.util.List;

/**
 * Created by Administrator on 2017/8/22.
 */
@Controller
public class ApiController {

    /**
     * 活动列表
     */
    @RequestMapping
    public ActionResult act_list(ActListParam param) {
        param.setPageIs(true);
        List<PartyActList> acts = partyActService.listAct(param);
        if (!TypeUtils.empty(acts)) {
            for (int i = 0, len = acts.size(); i < len; i++) {
                PartyActList act = acts.get(i);
                acts.get(i).setTimeStr(DateUtils.formatDate(act.getStartTime(), act.getEndTime()));
            }
        }
        return ActionResult.okRefresh(acts, param);
    }

    /**
     * 添加活动
     *
     * @param partyAct
     * @return
     */
    @RequestMapping
    public ActionResult add(PartyAct partyAct) {
        Integer actid = partyActService.addAct(partyAct);
        return ActionResult.okWithData(actid);
    }

    /**
     * 报名列表
     *
     * @param param
     * @return
     */
    @RequestMapping
    public ActionResult list_join(JoinParam param) {
        param.setPageIs(true);
        List<PartyJoinList> joins = partyActService.listJoin(param);

        return ActionResult.okRefresh(joins, param);
    }

    @RequestMapping
    public ActionResult join(Integer id, Integer type) {
        PartyActJoin join = new PartyActJoin();
        join.setActId(id);
        join.setStatus(type);
        join.setUserid(UserSession.getUserid());
        partyActService.addJoin(join);
        return ActionResult.ok();
    }

    @Autowired
    private PartyActService partyActService;
}
