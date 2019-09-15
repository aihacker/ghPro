package wxgh.sys.service.weixin.union.race;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pub.dao.jdbc.PubDao;
import wxgh.entity.union.race.RaceScore;
import wxgh.entity.union.race.RaceScoreDetail;
import wxgh.param.union.race.score.ArrangeInfo;
import wxgh.sys.dao.union.race.RaceScoreDao;
import wxgh.sys.dao.union.race.RaceScoreDetailDao;

import java.util.Date;

/**
 * Created by Administrator on 2017/3/8.
 */
@Service
@Transactional(readOnly = true)
public class RaceScoreDetailService {

    @Transactional
    public void save(RaceScoreDetail raceScoreDetail, Integer raceId, Integer rule) {

        raceScoreDetailDao.save(raceScoreDetail);

        //添加比赛记录的同时，更新race_score表相应的分数情况
        String sql = "select id from t_race_score where arrange_id=? and lun_num=?";
        Integer id = generalDao.query(Integer.class, sql, raceScoreDetail.getArrangeId(), raceScoreDetail.getRaceLun());
        if (id == null) {
            RaceScore raceScore = new RaceScore();
            raceScore.setRaceId(raceId);
            raceScore.setLunNum(1);
            if (raceScoreDetail.getAddGroup() == 1) {
                raceScore.setRival1Score(raceScoreDetail.getScore());
                raceScore.setRival2Score(0f);
            } else {
                raceScore.setRival2Score(raceScoreDetail.getScore());
                raceScore.setRival1Score(0f);
            }
            raceScore.setArrangeId(raceScoreDetail.getArrangeId());
            raceScore.setAddTime(new Date());
            raceScore.setRonda(1);
            raceScore.setRule(rule);
            raceScore.setEndIs(0);
            raceScoreDao.save(raceScore);
        } else {
            sql = "update t_race_score set ronda=ronda+1, ";
            if (raceScoreDetail.getAddGroup() == 1) {
                sql += "rival1_score=rival1_score+" + raceScoreDetail.getScore();
            } else {
                sql += "rival2_score=rival2_score+" + raceScoreDetail.getScore();
            }
            sql += " where arrange_id=? and lun_num=?";
            generalDao.execute(sql, raceScoreDetail.getArrangeId(), raceScoreDetail.getRaceLun());
        }
    }

    public ArrangeInfo getLastDetail(Integer arrangeId, String userid1, String userid2) {
        String sql = "SELECT a.arrange_id as id, a.race_lun as lun, a.ronda, SUM(a.score) AS score1, (select sum(b.score) from t_race_score_detail b where b.userid=? and b.arrange_id = a.arrange_id) as score2 FROM t_race_score_detail a \n" +
                "where a.arrange_id = ? and a.userid = ? ORDER BY a.race_lun,a.ronda DESC LIMIT 1";
        return generalDao.query(ArrangeInfo.class, sql, userid2, arrangeId, userid1);
    }

    @Autowired
    private PubDao generalDao;

    @Autowired
    private RaceScoreDetailDao raceScoreDetailDao;

    @Autowired
    private RaceScoreDao raceScoreDao;

}
