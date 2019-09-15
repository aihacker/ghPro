package wxgh.sys.dao.union.race;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import pub.dao.jdbc.PubDao;
import pub.dao.mybatis.MyBatisDao;
import wxgh.entity.union.race.RaceUserScore;

import java.util.Date;

/**
 * Created by Administrator on 2017/3/30.
 */
@Repository
public class RaceUserScoreDao extends MyBatisDao<RaceUserScore> {

    public void saveOrUpdate(Integer arrangeId, Integer lun, String userid, Float score) {
        String sql3 = "select id from t_race_user_score where arrange_id=? and lun_num=? and userid=?";
        String sql4 = "update t_race_user_score set score = ? where id=?";

        Integer id = generalDao.query(Integer.class, sql3, arrangeId, lun, userid);
        if (id == null) {
            save(getScore(userid, score, arrangeId, lun));
        } else {
            generalDao.execute(sql4, score, id);
        }
    }

    private RaceUserScore getScore(String userid, Float score, Integer arrangeId, Integer lunNum) {
        RaceUserScore userScore = new RaceUserScore();
        userScore.setAddTime(new Date());
        userScore.setArrangeId(arrangeId);
        userScore.setLunNum(lunNum);
        userScore.setUserid(userid);
        userScore.setScore(score);
        return userScore;
    }

    @Autowired
    private PubDao generalDao;
}
