package wxgh.app.sys.utils.place;

import com.libs.common.json.JsonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pub.utils.StrUtils;
import pub.utils.TypeUtils;
import wxgh.entity.entertain.nanhai.place.NanHaiPlaceRule;
import wxgh.entity.entertain.nanhai.place.NanHaiPlaceTime;
import wxgh.entity.entertain.nanhai.place.NanHaiPlaceYuyue;
import wxgh.entity.entertain.nanhai.place.NanHaiRuleExpection;
import wxgh.entity.entertain.place.*;
import wxgh.sys.service.weixin.entertain.nanhai.place.NanHaiPlaceRuleService;
import wxgh.sys.service.weixin.entertain.nanhai.place.NanHaiPlaceTimeService;
import wxgh.sys.service.weixin.entertain.nanhai.place.NanHaiPlaceYuyueService;
import wxgh.sys.service.weixin.entertain.nanhai.place.NanHaiRuleExpectionService;
import wxgh.sys.service.weixin.entertain.place.PlaceRuleService;
import wxgh.sys.service.weixin.entertain.place.PlaceTimeService;
import wxgh.sys.service.weixin.entertain.place.PlaceYuyueService;
import wxgh.sys.service.weixin.entertain.place.RuleExpectionService;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * @Author xlkai
 * @Version 2017/2/21
 */
@Component
public class NanHaiVerifyUtils {

    @Autowired
    private NanHaiRuleExpectionService ruleExpectionService;

    @Autowired
    private NanHaiPlaceRuleService placeRuleService;

    @Autowired
    private NanHaiPlaceYuyueService placeYuyueService;

    @Autowired
    private NanHaiPlaceTimeService placeTimeService;

   /* @Autowired
    private ScoreService scoreService;*/

    /**
     * 判断指定用户是否有权限预约
     *
     * @param yuyue
     * @return
     */
    public String verify(NanHaiPlaceYuyue yuyue) {
        String msg = null;

        //如果为积分支付，先验证用户积分是否足够
        /*if (yuyue.getPayType() == 1) {
            ScoreParam query = new ScoreParam();
            query.setStatus(1);
            query.setUserId(yuyue.getUserid());
            query.setScoreType(Score.SCORE_TYPE_USER);
            Float sumScore = scoreService.sumScore(query);
            if (sumScore == null || sumScore < yuyue.getPayPrice()) {
                return "你的积分不够哦";
            }
        }
*/
        //验证规则
        List<NanHaiPlaceRule> rules = placeRuleService.getRules(1);
        if (!TypeUtils.empty(rules)) {
            for (NanHaiPlaceRule r : rules) {
                if (r.getType() == NanHaiPlaceRule.TYPE_NUMB) { //数量限制
                    String yan = verifyNumb(r, yuyue.getUserid());
                    if (null != yan) {
                        msg = yan;
                        break;
                    }
                } else if (r.getType() == NanHaiPlaceRule.TYPE_SITE) { //场地限制
                    String yan = verifyTime(r, yuyue.getUserid(), yuyue.getTimeId());
                    if (null != yan) {
                        msg = yan;
                        break;
                    }
                }
            }
        }
        return msg;
    }

    /**
     * 数量规则验证
     *
     * @param rule
     * @param userid
     * @return
     */
    private String verifyNumb(NanHaiPlaceRule rule, String userid) {
        String msg = null;
        String ruleStr = rule.getRule();

        NumbRule numbRule;
        if (!StrUtils.empty(ruleStr)) {
            numbRule = JsonUtils.parseBean(ruleStr, NumbRule.class);
        } else {
            return null;
        }

        Integer yuyueNum = null; //用户预约数量

        //先进行规则验证
        if (yuyueNum == null) {
            yuyueNum = getUserYuyueNumb(userid, numbRule.getType());
        }
        if (yuyueNum >= numbRule.getNum()) {
            msg = "对不起，您" + getTipByType(numbRule.getType()) + "只能预约" + numbRule.getNum() + "次";
        } else {
            msg = null;
        }

        //再进行例外验证
        List<NanHaiRuleExpection> expections = ruleExpectionService.getExpections(rule.getId());
        if (!TypeUtils.empty(expections)) {
            Map<String, NanHaiRuleExpection> map = toMap(expections);
            NanHaiRuleExpection userExpection = map.getOrDefault(userid, null);
            if (userExpection != null) { //如果该用户存在例外
                String userRuleStr = userExpection.getRule();
                if (!StrUtils.empty(userRuleStr)) {
                    NumbRule userRule = JsonUtils.parseBean(userRuleStr, NumbRule.class);
                    if (yuyueNum == null) {
                        yuyueNum = getUserYuyueNumb(userid, userRule.getType());
                    }
                    if (yuyueNum < userRule.getNum()) {//如果用户当天预约次数小于规则步数
                        msg = null;
                    } else {
                        msg = "对不起，您" + getTipByType(userRule.getType()) + "只能预约" + userRule.getNum() + "次";
                    }
                }
            }
        }
        return msg;
    }

    private String verifyTime(NanHaiPlaceRule rule, String userid, Integer timeId) {
        String msg = null;
        String ruleStr = rule.getRule();

        TimeRule r = null;
        if (!StrUtils.empty(ruleStr)) {
            r = JsonUtils.parseBean(ruleStr, TimeRule.class);
        } else {
            return null;
        }

        //先验证用户预约的时间是否为规则时间
        if (r.getTimeId() != timeId) {
            return null;
        }

        //如果用户预约的时间是规则时间，则验证当前规则用户
        if (r.getUserid().equals(userid)) {
            return null;
        }

        //如果当前规则不属于此预约用户，则验证预约时间是否小于4小时
        NanHaiPlaceTime t = placeTimeService.getPlaceTime(r.getTimeId());
        if (t != null) {
            Date today = new Date();
            int week = pub.utils.DateUtils.getWeek(today);
            if (week == t.getWeek() && isLessThreeHour(today, t.getStartTime())) { //如果是同一天
                return null;
            }
        }
        msg = "对不起，该场地为固定场地，暂不能被预订";
        return msg;
    }

    private Map<String, NanHaiRuleExpection> toMap(List<NanHaiRuleExpection> expections) {
        Map<String, NanHaiRuleExpection> map = new HashMap<>();
        for (NanHaiRuleExpection e : expections) {
            map.put(e.getUserid(), e);
        }
        return map;
    }

    /**
     * 判断时间是否在0~3小时之间
     *
     * @param today
     * @param start
     * @return
     */
    private boolean isLessThreeHour(Date today, String start) {
        String date = pub.utils.DateUtils.formatDate(today, "yyyy-MM-dd");
        int mil = pub.utils.DateUtils.getMinute(today,
                pub.utils.DateUtils.formatStr(date + " " + start, "yyyy-MM-dd HH:mm"));
        return mil > 0 && mil < (3 * 60);
    }

    private Integer getUserYuyueNumb(String userid, String type) {
        Integer yuyueNum = 0;
        if (Rule.TYPE_DAY.equals(type)) { //日限制
            yuyueNum = placeYuyueService.countDay(userid);
        } else if (Rule.TYPE_WEEK.equals(type)) {
            yuyueNum = placeYuyueService.countWeek(userid);
        } else if (Rule.TYPE_MONTH.equals(type)) {
            yuyueNum = placeYuyueService.countMonth(userid);
        }
        return yuyueNum;
    }

    private String getTipByType(String type) {
        if (NumbRule.TYPE_DAY.equals(type)) {
            return "每天";
        } else if (NumbRule.TYPE_WEEK.equals(type)) {
            return "每周";
        } else if (NumbRule.TYPE_MONTH.equals(type)) {
            return "每月";
        }
        return "";
    }
}
