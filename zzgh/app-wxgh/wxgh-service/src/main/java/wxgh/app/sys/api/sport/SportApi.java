package wxgh.app.sys.api.sport;

import com.libs.common.data.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import wxgh.data.entertain.sport.push.MonthList;
import wxgh.data.entertain.sport.push.WeekList;
import wxgh.data.entertain.sport.score.SportScoreType;
import wxgh.entity.entertain.sport.SportScore;
import wxgh.sys.service.weixin.entertain.sport.SportPushService;
import wxgh.sys.service.weixin.entertain.sport.SportScoreService;
import wxgh.sys.service.weixin.pub.UserService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * author：xlkai
 * date：2018/1/8
 * time：15:21
 * version：V1.0
 * Description：
 */
@Component
public class SportApi {

    public static final Integer DB = 6000;
    public static final Integer SCORE = 5;
    public static final Double PARTYRATE=0.8;//部门人数参与率
    public static final Integer PARTYSCORE=3;

    /**
     * 周结算，指定用户
     *
     * @param start
     * @param end
     * @param userid
     * @param actId
     */
    public void weekUser(int start, int end, String userid, Integer actId, int dateid) {
        int diff = DateUtils.diff(start, end);
        Integer step = sportPushService.totalStep(start, end, userid);
        if (step / diff >= 6000) {
            SportScore sportScore = new SportScore(userid, SCORE, dateid, SportScoreType.WEEK.getKey());
            sportScore.setActId(actId);
            sportScoreService.addScore(sportScore);
        }
    }

    /**
     * 每周结算
     *
     * @param start
     * @param end
     * @param deptid
     * @param actId
     */
    public void week(int start, int end, int deptid, Integer actId, int dateid) {
        int diff = DateUtils.diff(start, end);
        List<WeekList> sports = sportPushService.week(start, end, deptid);

        List<SportScore> scores = new ArrayList<>();
        for (WeekList week : sports) {
            if (week.getStep() / diff >= 6000) {
                SportScore sportScore = new SportScore(week.getUserid(), SCORE, dateid, SportScoreType.WEEK.getKey());
                sportScore.setActId(actId);
                scores.add(sportScore);
            }
        }
        sportScoreService.addScores(scores);
    }

    public void month(int start, int end, int deptid, Integer actId, int dateid) {
        int diff = DateUtils.diff(start, end);
        List<MonthList> sports = sportPushService.month(start, end, deptid);
        Map<Integer, List<MonthList>> map = new HashMap<>();
        for (MonthList m : sports) {
            List<MonthList> tmps = map.get(m.getDeptid());
            if (tmps == null) {
                tmps = new ArrayList<>();
            }
            tmps.add(m);
        }
        sports.clear();
        sports = null;

        List<SportScore> scores = new ArrayList<>();
        for (Map.Entry<Integer, List<MonthList>> entry : map.entrySet()) {
            int sum = 0;
            List<MonthList> values = entry.getValue();
            for (MonthList m : values) {
                sum += m.getStep();
            }
            if (sum / diff / values.size() > DB) {
                for (MonthList m : values) {
                    SportScore sportScore = new SportScore(m.getUserid(), SCORE, dateid, SportScoreType.MONTH.getKey());
                    sportScore.setActId(actId);
                    scores.add(sportScore);
                }
            }
        }
        sportScoreService.addScores(scores);
    }

    public void monthParty(int start, int end, int deptid, Integer actId, int dateid){
        List<MonthList> sports=sportPushService.monthPartyRate(start,end,deptid);

        List<SportScore> scores = new ArrayList<>();
        for (MonthList m:sports) {
           Integer sum= m.getCount();
           Integer count=userService.getCount(deptid);
           Integer partyRate=sum/count;
           if(partyRate >= 0.8){
               SportScore sportScore = new SportScore(m.getUserid(), PARTYSCORE, dateid, SportScoreType.MONTH.getKey());
               sportScore.setActId(actId);
               scores.add(sportScore);
           }
        }
        sportScoreService.addScores(scores);
    }

    @Autowired
    private UserService userService;

    @Autowired
    private SportPushService sportPushService;

    @Autowired
    private SportScoreService sportScoreService;
}
