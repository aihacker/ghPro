package wxgh.wx.web.party.syact;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import pub.spring.web.mvc.ActionResult;
import pub.spring.web.mvc.model.Model;
import wxgh.app.session.bean.SeUser;
import wxgh.app.session.user.UserSession;
import wxgh.entity.party.match.SheyingActJoin;
import wxgh.sys.service.weixin.party.syact.SheyingActJoinService;

import java.util.Date;
import java.util.List;

/**
 * Created by ✔ on 2017/4/21.
 */
@Controller
public class JoinController {

    @Autowired
    private SheyingActJoinService sheyingActJoinService;

    @RequestMapping
    public void index(Model model, Integer aid){
        SheyingActJoin sheyingActJoinQuery = new SheyingActJoin();
        sheyingActJoinQuery.setAid(aid);
        List<SheyingActJoin> sheyingActJoinList = sheyingActJoinService.getData(sheyingActJoinQuery);
        model.put("list", sheyingActJoinList);
    }

    @RequestMapping
    public ActionResult join(SheyingActJoin sheyingActJoin){
        SeUser user = UserSession.getUser();
        if(sheyingActJoinService.check(user.getUserid(), sheyingActJoin.getAid()))
            return ActionResult.ok();
        sheyingActJoin.setUserid(user.getUserid());
        sheyingActJoin.setStatus(1);
        sheyingActJoin.setAddTime(new Date());
        sheyingActJoinService.addData(sheyingActJoin);
        return ActionResult.ok();
    }

}
