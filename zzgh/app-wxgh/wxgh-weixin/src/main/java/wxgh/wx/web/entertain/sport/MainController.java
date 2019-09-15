package wxgh.wx.web.entertain.sport;

import com.libs.common.type.TypeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import pub.dao.jdbc.PubDao;
import pub.error.ValidationError;
import pub.functions.DateFuncs;
import pub.spring.web.mvc.model.Model;
import wxgh.app.session.bean.SeUser;
import wxgh.app.session.user.UserSession;
import wxgh.app.utils.DeptId;
import wxgh.app.utils.FindParentId;
import wxgh.data.entertain.sport.HistoryShow;
import wxgh.data.entertain.sport.UserSport;
import wxgh.data.entertain.sport.act.SportActInfo;
import wxgh.entity.pub.Dept;
import wxgh.entity.pub.User;
import wxgh.param.entertain.sport.SportParam;
import wxgh.sys.service.weixin.entertain.sport.SportActService;
import wxgh.sys.service.weixin.entertain.sport.SportScoreService;
import wxgh.sys.service.weixin.entertain.sport.SportService;
import wxgh.sys.service.weixin.pub.DeptService;
import wxgh.sys.service.weixin.pub.UserService;

import java.math.BigDecimal;

/**
 * Created by Administrator on 2017/8/25.
 */
@Controller
public class MainController {

    public static final Float AVG = 0.7f;
//    private static final int start = 20180106;
//    private static final int end = 20180415;

    @Autowired
    private SportScoreService sportScoreService;

    /**
     * 排行榜
     */
    @RequestMapping
    public void list(Model model, Integer dateid) {

        if (dateid == null) {
            dateid = DateFuncs.getIntToday();
        }
        SportParam param = new SportParam();
        User user = null;
        SeUser userSession = UserSession.getUser();
        String userid = userSession.getUserid();
        FindParentId findParentId = new FindParentId();

        if (userid != null) {
            user = userService.getUser(userid);
        } else {
            throw new ValidationError("此用户不存在!请检查微信是否登陆错误!");
        }

        param.setUserid(userid);
        param.setDateId(dateid);

        String deptId = user.getDepartment();
        UserSport userSport = null;
        if ("1".equals(user.getDepartment()) && user.getDeptid() == 1) {
            deptId = "1";
        }

        String[] tmp = findParentId.getSpiltIds(deptId);
        deptId = findParentId.find(tmp).toString();

        Integer id = Integer.parseInt(deptId);
        param.setDeptid(id);
        userSport = sportService.userSport(param);
        model.put("s", userSport);
    }


    @RequestMapping
    public void history(Model model, String userid) {
        HistoryShow historyShow = sportService.historyShow(TypeUtils.empty(userid) ? UserSession.getUserid() : userid);
        Float step = historyShow.getAvgStep();
        float avg = 0f;
        float distance = 0f;
        int start = 20190401;
        int end = 20191001;
        if (step != null && step != 0) {
            BigDecimal b = new BigDecimal(step / 7);
            avg = b.setScale(1, BigDecimal.ROUND_HALF_UP).floatValue();

            BigDecimal bd = new BigDecimal(step * AVG / 1000);
            distance = bd.setScale(2, BigDecimal.ROUND_HALF_UP).floatValue();
        }
        historyShow.setAvgStep(avg);
        historyShow.setDistance(distance);
        historyShow.setTodayStep(historyShow.getTodayStep() == null ? 0 : historyShow.getTodayStep());
        int i = step.intValue();
        historyShow.setTotalStep(i);


        model.put("h", historyShow);

        Integer sum = sportScoreService.sumScore(TypeUtils.empty(userid) ? UserSession.getUserid() : userid, 4);
        model.put("totalsum", sum);

        Integer step2 = sportService.getStep(TypeUtils.empty(userid) ? UserSession.getUserid() : userid, start, end);
        model.put("totalstep", step2);

        Integer rank = sportService.getRank(TypeUtils.empty(userid) ? UserSession.getUserid() : userid, start, end);
        model.put("rank", rank);

        float distance2 = 0f;
        if (step2 != 0) {
            BigDecimal bd = new BigDecimal(step2 * 0.7 / 1000);
            distance2 = bd.setScale(2, BigDecimal.ROUND_HALF_UP).floatValue();
        }
        model.put("totaldistance", distance2);
    }

    @Autowired
    private UserService userService;

    @Autowired
    private SportActService sportActService;

    @Autowired
    private SportService sportService;
}
