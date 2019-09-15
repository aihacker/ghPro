package wxgh.wx.web.union.race;


import com.weixin.WeixinException;
import com.weixin.utils.ApiList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import pub.spring.web.mvc.model.Model;
import pub.utils.PathUtils;
import wxgh.app.session.bean.SeUser;
import wxgh.app.session.user.UserSession;
import wxgh.app.utils.WeixinUtils;
import wxgh.entity.union.race.Race;
import wxgh.entity.union.race.RaceJoin;
import wxgh.param.union.race.RaceJoinQuery;
import wxgh.sys.service.weixin.union.race.RaceJoinService;
import wxgh.sys.service.weixin.union.race.RaceService;

import java.util.Date;
import java.util.List;

/**
 * @Version 1.0
 * --------------------------------------------------------- *
 * @Description >_
 * --------------------------------------------------------- *
 * @Author Ape
 * --------------------------------------------------------- *
 * @Email <16511660@qq.com>
 * --------------------------------------------------------- *
 * @Date 2017-10-27  11:08
 * --------------------------------------------------------- *
 */
@Controller
public class MainController  {

    @RequestMapping
    public void list() {
    }

    @RequestMapping
    public void show(Integer id, Model model) throws WeixinException {
        Race race = raceService.getRace(id);
        race.setImg(PathUtils.getImagePath(race.getImg()));

        int status = 1;
        String btn_msg = "立即报名";
        //计算当前比赛状态
        Date today = new Date();
        if (race.getEntryTime().after(today)) {
            //判断用户是否报名
            SeUser user = UserSession.getUser();
            if (user == null) {
                status = 6;
                btn_msg = "未登录，请重新进入";
            } else {
                RaceJoin raceJoin = raceJoinService.getJoin(user.getUserid(), id);
                if (raceJoin != null) {
                    status = 5;
                    btn_msg = "已报名";
                } else {
                    //比赛名额限制
                    if (race.getLimitNum() != -1) {
                        RaceJoinQuery query = new RaceJoinQuery();
                        query.setRaceId(id);
                        Integer joinCount = raceJoinService.coutJoin(query);
                        if (joinCount >= race.getLimitNum()) {
                            status = 4; //名额已满
                            btn_msg = "名额已满";
                        }
                    } else {
                        status = 1; //报名中
                        btn_msg = "立即报名";
                    }
                }
            }
        } else if (race.getStartTime().before(today) && race.getEndTime().after(today)) {
            status = 2; //进行中
            btn_msg = "比赛进行中";
        } else if (race.getEndTime().before(today)) {
            status = 3; //已结束
            btn_msg = "比赛已结束";
        } else {
            status = 7;
            btn_msg = "已截止报名";
        }

        model.put("status", status);
        model.put("r", race);
        model.put("btn", btn_msg);

        if (race.getRaceType() == 2) {
            List<String> apiList = ApiList.getImageApi();
            apiList.add(ApiList.OPENENTERPRISECONTACT);
            WeixinUtils.sign(model, apiList);
            WeixinUtils.sign_contact(model);
        }

        SeUser user = UserSession.getUser();
        if (user != null) {
            model.put("userid", user.getUserid().equals(race.getUserid()));
        }
    }

    @RequestMapping
    public void add(Model model) throws WeixinException {
        WeixinUtils.sign(model, ApiList.getImageApi());
    }

    @Autowired
    private RaceService raceService;

    @Autowired
    private RaceJoinService raceJoinService;

}
