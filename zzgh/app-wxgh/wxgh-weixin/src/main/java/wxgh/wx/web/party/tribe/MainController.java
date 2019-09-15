package wxgh.wx.web.party.tribe;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import pub.spring.web.mvc.model.Model;
import wxgh.app.consts.Status;
import wxgh.app.session.user.UserSession;
import wxgh.param.pub.score.ScoreGroup;
import wxgh.param.pub.score.ScoreParam;
import wxgh.sys.service.weixin.pub.score.ScoreService;

/**
 * Created by Administrator on 2017/8/17.
 */
@Controller
public class MainController {

    /**
     * 活动列表
     *
     * @param model
     */
    @RequestMapping
    public void act_list(Model model) {
    }

    /**
     * 我的积分
     *
     * @param model
     */
    @RequestMapping
    public void score(Model model) {
        ScoreParam param = new ScoreParam();
        param.setGroup(ScoreGroup.TRIBE);
        param.setUserid(UserSession.getUserid());
        param.setStatus(Status.NORMAL.getStatus());
        Float score = scoreService.sumScore(param);
        model.put("sumScore", score);
    }

    @Autowired
    private ScoreService scoreService;

}
