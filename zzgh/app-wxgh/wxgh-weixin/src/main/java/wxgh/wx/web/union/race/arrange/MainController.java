package wxgh.wx.web.union.race.arrange;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import pub.spring.web.mvc.model.Model;
import pub.utils.StrUtils;
import wxgh.app.session.bean.SeUser;
import wxgh.app.session.user.UserSession;
import wxgh.app.utils.UrlUtils;
import wxgh.entity.union.race.Race;
import wxgh.param.pub.TipMsg;
import wxgh.param.union.race.RaceJoinQuery;
import wxgh.sys.service.weixin.union.race.RaceArrangeService;
import wxgh.sys.service.weixin.union.race.RaceGroupService;
import wxgh.sys.service.weixin.union.race.RaceJoinService;
import wxgh.sys.service.weixin.union.race.RaceService;

import java.util.Date;

/**
 * @Version 1.0
 * --------------------------------------------------------- *
 * @Description >_
 * --------------------------------------------------------- *
 * @Author Ape
 * --------------------------------------------------------- *
 * @Email <16511660@qq.com>
 * --------------------------------------------------------- *
 * @Date 2017-10-27  14:14
 * --------------------------------------------------------- *
 */
@Controller
public class MainController {

    @Autowired
    private RaceJoinService raceJoinService;

    @Autowired
    private RaceArrangeService raceArrangeService;

    @Autowired
    private RaceService raceService;

    @Autowired
    private RaceGroupService raceGroupService;

    @RequestMapping
    public String arrange(Integer id, Model model) {
        String msg = null;
        Race race = raceService.getRace(id);
        if (race == null) {
            msg = "比赛可能取消了哦";
        }
        if (race.getBianpaiIs() == 1) {
            msg = "比赛已编排哦";

        }
        Date today = new Date();
        if (race.getEndTime().before(today)) {
            msg = "比赛已结束哦";
        }
        if (!StrUtils.empty(msg)) {
            TipMsg tipMsg = TipMsg.info(msg, "系统提示");
            return "redirect:" + UrlUtils.getTipUrl(tipMsg);
        }

        RaceJoinQuery joinQuery = new RaceJoinQuery();
        joinQuery.setRaceId(id);
        Integer count = raceJoinService.coutJoin(joinQuery);

        model.put("joinCount", count == null ? 0 : count);
        model.put("r", race);

        return null;
    }

    @RequestMapping
    public void group(Integer id) {
    }

    @RequestMapping
    public String result(Integer id, Model model) {
        Race race = raceService.getRace(id);
        if (race != null && 1 != race.getBianpaiIs()) {
            TipMsg tipMsg = TipMsg.error("系统提示", "比赛还未进行编排哦，不能查看对阵情况");
            return "redirect:" + UrlUtils.getTipUrl(tipMsg);
        }
        SeUser user = UserSession.getUser();
        if (user != null) {
            model.put("userIs", (race != null && race.getUserid().equals(user.getUserid())));
        } else {
            model.put("userIs", false);
        }

        return null;
    }

}
