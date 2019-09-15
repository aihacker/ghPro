package wxgh.wx.web.entertain.sport;

import com.libs.common.data.DateFuncs;
import com.libs.common.data.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import pub.error.ValidationError;
import pub.spring.web.mvc.ActionResult;
import pub.spring.web.mvc.model.Model;
import wxgh.app.session.bean.SeUser;
import wxgh.app.session.user.UserSession;
import wxgh.app.utils.DeptId;
import wxgh.app.utils.FindParentId;
import wxgh.data.entertain.sport.act.SportActInfo;
import wxgh.data.entertain.sport.score.SportScoreList;
import wxgh.entity.pub.Dept;
import wxgh.entity.pub.User;
import wxgh.param.entertain.sport.SportScoreParam;
import wxgh.sys.service.weixin.entertain.sport.SportActService;
import wxgh.sys.service.weixin.entertain.sport.SportScoreService;
import wxgh.sys.service.weixin.entertain.sport.SportService;
import wxgh.sys.service.weixin.pub.DeptService;
import wxgh.sys.service.weixin.pub.UserService;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * author：xlkai
 * date：2018/1/8
 * time：17:23
 * version：V1.0
 * Description：
 */
@Controller
public class ScoreController {

    private static final int start = 20190401;
    private static final int end = 20191001;

    @RequestMapping
    public void show(Model model, Integer id) {
        String userId = UserSession.getUserid();
        Integer sum = sportScoreService.sumScore(userId, id);
        Integer step = sportService.getStep(userId, start, end);
        model.put("sum", sum);
        model.put("step", step);

        float distance = 0f;
        if (step != 0) {
            BigDecimal bd = new BigDecimal(step * 0.7 / 1000);
            distance = bd.setScale(2, BigDecimal.ROUND_HALF_UP).floatValue();
        }
        model.put("distance", distance);
    }

    @RequestMapping
    public ActionResult list(Integer id, String type) {

        int dateid = DateFuncs.addDay(DateUtils.getFirstWeekDayInt(new Date()), 1);

        SportScoreParam param = new SportScoreParam();
        param.setUserid(UserSession.getUserid());
        param.setType(type);
        param.setActId(id);
        param.setDateid(dateid);

        List<SportScoreList> scores = sportScoreService.listScore(param);

        return ActionResult.ok(null, scores);
    }

    @Autowired
    private UserService userService;

    @Autowired
    private SportActService sportActService;

    @Autowired
    private SportScoreService sportScoreService;

    @Autowired
    private SportService sportService;
}
