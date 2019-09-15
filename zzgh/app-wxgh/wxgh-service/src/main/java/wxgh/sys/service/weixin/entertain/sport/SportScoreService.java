package wxgh.sys.service.weixin.entertain.sport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pub.dao.jdbc.PubDao;
import pub.dao.jdbc.sql.SQL;
import wxgh.data.entertain.sport.score.SportScoreList;
import wxgh.entity.entertain.sport.SportScore;
import wxgh.param.entertain.sport.SportScoreParam;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * author：xlkai
 * date：2018/1/8
 * time：15:23
 * version：V1.0
 * Description：
 */
@Service
@Transactional(readOnly = true)
public class SportScoreService {

    /**
     * 获取用户总积分
     *
     * @param userid
     * @param actId
     * @return
     */
    public Integer sumScore(String userid, Integer actId) {
        String sql = "select sum(score) from t_sport_score where userid = ? and act_id = ?";
        return pubDao.queryInt(sql, userid, actId);
    }

    @Transactional
    public void addScores(List<SportScore> scores) {
        String sql = "select id from t_sport_score where userid = ? and dateid = ? and type = ? and act_id = ? limit 1";
        String addSql = new SQL.SqlBuilder()
                .field("userid, score, dateid, add_time, type, act_id")
                .value(":userid, :score, :dateid, :addTime, :type, :actId")
                .insert("sport_score")
                .build().sql();
        for (SportScore score : scores) {
            Integer id = pubDao.query(Integer.class, sql, score.getUserid(), score.getDateid(), score.getType(), score.getActId());
            if (id == null) {
                score.setAddTime(new Date());
                pubDao.executeBean(addSql, score);
            }
        }
    }

    @Transactional
    public void addScore(SportScore sportScore) {
        List<SportScore> scores = new ArrayList<>();
        scores.add(sportScore);
        addScores(scores);
    }

    public List<SportScoreList> listScore(SportScoreParam param) {
        SQL.SqlBuilder sql = new SQL.SqlBuilder()
                .table("sport_score")
                .field("id, score, type");
        if (param.getActId() != null) {
            sql.where("act_id = :actId");
        }
        if (param.getDateid() != null) {
            sql.where("dateid = :dateid");
        }
        if (param.getType() != null) {
            sql.where("type = :type");
        }
        if (param.getUserid() != null) {
            sql.where("userid = :userid");
        }
        return pubDao.queryList(sql.build().sql(), param, SportScoreList.class);
    }

    @Autowired
    private PubDao pubDao;

}
