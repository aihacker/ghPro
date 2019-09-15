package wxgh.sys.service.weixin.entertain.sport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pub.dao.jdbc.PubDao;
import pub.dao.jdbc.sql.SQL;
import wxgh.data.entertain.sport.push.MonthList;
import wxgh.data.entertain.sport.push.PushList;
import wxgh.data.entertain.sport.push.WeekList;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * author：xlkai
 * date：2018/1/5
 * time：10:24
 * version：V1.0
 * Description：
 */
@Service
@Transactional(readOnly = true)
public class SportPushService {

    /**
     * 指定日期需要推送步数的用户
     *
     * @param dateId
     * @return
     */
    public List<PushList> listPush(Integer dateId) {
        String sql = new SQL.SqlBuilder()
                .table("sport_last l")
                .field("l.userid, l.step_count, u.name")
                .join("user u", "l.userid = u.userid")
                .where("u.sport_push = 1")
                .where("l.date_id = ?")
                .build().sql();
        return pubDao.queryList(PushList.class, sql, dateId);
    }

    /**
     * 每天达标6000步，每周5分，每周结算
     *
     * @param start
     * @param end
     * @param deptid
     * @return
     */
    public List<WeekList> week(int start, int end, int deptid) {
        String sql = "select userid, SUM(step_count) as step from t_sport_last\n" +
                "where date_id >= ? and date_id <= ? and deptid = ?\n" +
                "GROUP BY userid";
        return pubDao.queryList(WeekList.class, sql, start, end, deptid);
    }

    /**
     * 获取用户指定日期范围的总步数
     *
     * @param start
     * @param end
     * @param userid
     * @return
     */
    public Integer totalStep(int start, int end, String userid) {
        String sql = "select SUM(step_count) as step from t_sport_last\n" +
                "where date_id >= ? and date_id <= ? and userid = ?";
        return pubDao.queryInt(sql, start, end, userid);
    }

    /**
     * 部门人均达标的，每月奖励参与者人均5分
     *
     * @param start
     * @param end
     * @param deptid
     * @return
     */
    public List<MonthList> month(int start, int end, int deptid) {
        String sql = "select l.userid, SUM(l.step_count) as step, u.deptid from t_sport_last l\n" +
                "join t_user u on u.userid = l.userid\n" +
                "where l.date_id >= ? and l.date_id <= ? and l.deptid = ?\n" +
                "GROUP BY l.userid";
        return pubDao.queryList(MonthList.class, sql, start, end, deptid);
    }

    /**
     *  部门参与率达80%，每月奖励3分
     *
     * @param start
     * @param end
     * @param deptid
     * @return
     */
    public List<MonthList> monthPartyRate(int start, int end, int deptid) {
        String sql = "select count(DISTINCT userid) as count from t_sport_last l\n" +
                "where l.date_id >=? and l.date_id <=? and l.deptid =?";
        return pubDao.queryList(MonthList.class, sql, start, end, deptid);
    }

    @Autowired
    private PubDao pubDao;

}
