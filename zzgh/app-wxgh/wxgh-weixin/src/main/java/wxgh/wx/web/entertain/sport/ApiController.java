package wxgh.wx.web.entertain.sport;

import com.libs.common.type.TypeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import pub.functions.DateFuncs;
import pub.spring.web.mvc.ActionResult;
import pub.type.RefData;
import wxgh.app.session.bean.SeUser;
import wxgh.app.session.user.UserSession;
import wxgh.data.entertain.sport.HistoryList;
import wxgh.data.entertain.sport.SportList;
import wxgh.param.entertain.sport.SportParam;
import wxgh.sys.service.weixin.entertain.sport.SportService;
import wxgh.sys.service.weixin.pub.UserService;

import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * author：xlkai
 * date：2017/11/30
 * time：10:04
 * version：V1.0
 * Description：
 */
@Controller
public class ApiController {

    /**
     * 获取用户公司今日排行榜
     *
     * @param param
     * @return
     */
    @RequestMapping
    public ActionResult today(SportParam param) {
        SeUser user = UserSession.getUser();
        //param.setPageIs(true);
//        String deptId=user.getCompanyId().toString();
//        if("1".equals(deptId)){
//            User user1=userService.getUser(user.getUserid());
//            deptId=userService.getDeptId(user1.getDepartment(),user.getUserid());
//        }
//        Integer id=Integer.parseInt(deptId);
//        param.setDeptid(id);
        if (param.getDateId() == null) {
            param.setDateId(DateFuncs.getIntToday());
        }
        List<SportList> sports = sportService.listSport(param);
        for (int i=0;i<sports.size();i++) {
            sports.get(i).setPaiming(i+1);
        }
        return ActionResult.okRefresh(sports, param);
    }

    /**
     * 获取用户今天之后7天的运动步数
     *
     * @param param
     * @return
     */
    @RequestMapping
    public ActionResult history_list(SportParam param) {
        int todayInt = DateFuncs.getIntToday();
        int day7 = DateFuncs.addDay(todayInt, -7);
        param.setStartTime(day7);
        param.setEndTime(DateFuncs.addDay(todayInt, -1));
        if (TypeUtils.empty(param.getUserid())) {
            param.setUserid(UserSession.getUserid());
        }

        List<HistoryList> sports = sportService.listHistory(param);
        Map<String, Integer> map = new HashMap<>();
        for (HistoryList sport : sports) {
            String timeStr = DateFuncs.dateTimeToStr(DateFuncs.fromIntDate(sport.getDateId()), "dd日");
            map.put(timeStr, sport.getStepCount());
        }

        List<String> times = new ArrayList<>();
        for (int i = 0; i < 7; i++) {
            Date date = DateFuncs.fromIntDate(DateFuncs.addDay(day7, i));
            times.add(DateFuncs.dateTimeToStr(date, "dd日"));
        }

        List<Integer> steps = new ArrayList<>();
        for (String time : times) {
            Integer step = map.getOrDefault(time, null);
            steps.add(step == null ? 0 : step);
        }

        RefData refData = new RefData();
        refData.put("times", times);
        refData.put("steps", steps);

        return ActionResult.okWithData(refData);
    }

    @Autowired
    private SportService sportService;

    @Autowired
    private UserService userService;

}
