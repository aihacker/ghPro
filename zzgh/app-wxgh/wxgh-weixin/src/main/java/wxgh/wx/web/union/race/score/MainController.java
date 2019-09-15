package wxgh.wx.web.union.race.score;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import pub.spring.web.mvc.model.Model;
import wxgh.app.session.bean.SeUser;
import wxgh.app.session.user.UserSession;
import wxgh.app.utils.UrlUtils;
import wxgh.entity.union.race.Race;
import wxgh.param.pub.TipMsg;
import wxgh.sys.service.weixin.union.race.RaceService;

/**
 * @Version 1.0
 * --------------------------------------------------------- *
 * @Description >_
 * --------------------------------------------------------- *
 * @Author Ape
 * --------------------------------------------------------- *
 * @Email <16511660@qq.com>
 * --------------------------------------------------------- *
 * @Date 2017-10-27  14:34
 * --------------------------------------------------------- *
 */
@Controller
public class MainController {

    @RequestMapping
    public void list() {
    }

    @RequestMapping
    public String match2(Integer id, Model model) {
        Race race = raceService.getRace(id);

        if (race == null) {
            TipMsg tipMsg = TipMsg.info("系统提示", "比赛已被取消哦");
            tipMsg.setUrlMsg("返回上一页");
            tipMsg.setUrl("javascript:history.back(-1)");
            return "redirect:" + UrlUtils.getTipUrl(tipMsg);
        }

        model.put("race", race);
        model.put("dan", race.getRaceType() == 1); //是否为单打
        return null;
    }

    @RequestMapping
    public String match(Integer id, Model model) {
        Race race = raceService.getRace(id);
        if (race != null) {
            if (race.getBianpaiIs() == null || (race.getBianpaiIs() != null && race.getBianpaiIs() != 1)) {
                TipMsg tipMsg = TipMsg.info("比赛还没有进行编排哦", "系统提示");
                tipMsg.setUrlMsg("返回上一页");
                tipMsg.setUrl("javascript:history.back(-1)");
                return "redirect:" + UrlUtils.getTipUrl(tipMsg);
            }

            model.put("race", race);
            model.put("dan", race.getRaceType() == 1); //是否为单打
        }
        return null;
    }

    @RequestMapping
    public void result(Model model, Integer id) {
        Race race = raceService.getRace(id);
        SeUser user = UserSession.getUser();
        if (user == null) {
            model.put("show", false);
        } else {
            model.put("show", user.getUserid().equals(race.getUserid()));
        }
    }

    @Autowired
    private RaceService raceService;

}
