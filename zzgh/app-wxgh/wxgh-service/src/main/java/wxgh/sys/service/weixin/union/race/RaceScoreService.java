package wxgh.sys.service.weixin.union.race;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pub.dao.jdbc.PubDao;
import pub.utils.StrUtils;
import wxgh.data.union.race.ArrangeUser;
import wxgh.entity.union.race.RaceScore;
import wxgh.param.union.race.score.config.MatchScoreConst;
import wxgh.param.union.race.score.result.EditInfo;
import wxgh.param.union.race.score.result.ResultDb;
import wxgh.sys.dao.union.race.RaceScoreDao;
import wxgh.sys.dao.union.race.RaceUserScoreDao;

import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2017/3/9.
 */
@Service
@Transactional(readOnly = true)
public class RaceScoreService {

    @Transactional
    public void editScores(List<EditInfo> editInfos) {
        String sql = "select id from t_race_score where arrange_id=? and lun_num=?";

        String sql1 = "select raceId from t_race_arrange where id=? limit 1";

        String sql2 = "update t_race_score set rival1_score=?, rival2_score=? where id=?";

        for (EditInfo info : editInfos) {
            Integer infoId = info.getId();
            Integer lun = info.getLun();
            String[] scores = info.getScore().split(":");

            Integer id = generalDao.query(Integer.class, sql, infoId, lun);

            if (id == null) { //没有则添加
                Integer raceId = generalDao.query(Integer.class, sql1, infoId);
                RaceScore raceScore = new RaceScore();
                raceScore.setRaceId(raceId);
                raceScore.setLunNum(lun);
                raceScore.setRival1Score(Float.valueOf(scores[0]));
                raceScore.setRival2Score(Float.valueOf(scores[1]));
                raceScore.setArrangeId(infoId);
                raceScore.setAddTime(new Date());
                raceScore.setRule(-1);
                raceScore.setRonda(-1);
                raceScore.setEndIs(1);

                save(raceScore);
            } else {
                generalDao.execute(sql2, scores[0], scores[1], id);
            }

            //修改积分
            ArrangeUser arrangeUser = raceArrangeService.getArrangeUser(infoId);
            String[] user1s = arrangeUser.getRival1().split(",");
            String[] user2s = arrangeUser.getRival2().split(",");
            if (scores[0].compareTo(scores[1]) > 0) {
                for (String userid : user1s) {
                    raceUserScoreDao.saveOrUpdate(infoId, lun, userid, MatchScoreConst.WIN);
                }
                for (String userid : user2s) {
                    raceUserScoreDao.saveOrUpdate(infoId, lun, userid, MatchScoreConst.SHU);
                }
            } else if (scores[0].compareTo(scores[1]) < 0) {
                for (String userid : user1s) {
                    raceUserScoreDao.saveOrUpdate(infoId, lun, userid, MatchScoreConst.SHU);
                }
                for (String userid : user2s) {
                    raceUserScoreDao.saveOrUpdate(infoId, lun, userid, MatchScoreConst.WIN);
                }
            }
        }
    }

    public List<ResultDb> getRaceResults(Integer raceId) {
        String sql = "select r.cate_type, r.race_type, a.name1, a.name2, a.remark, a.order_num, a.arrange_type, a.lun_num as lun,\n" +
                "s.lun_num, s.rival1_score, s.rival2_score, s.end_is, s.arrange_id\n" +
                "from t_race_score s\n" +
                "join t_race r on s.race_id = r.id\n" +
                "join t_race_arrange a on s.arrange_id = a.id\n" +
                "where s.race_id = ? ORDER BY s.lun_num ASC";
        return generalDao.queryList(ResultDb.class, sql, raceId);
    }

    @Transactional
    public void save(RaceScore raceScore) {
        raceScoreDao.save(raceScore);
    }

    public RaceScore getScore(Integer arrangeId, Integer lunNum) {
        String sql = "select * from t_race_score where arrange_id=? and lun_num=? limit 1";
        return generalDao.query(RaceScore.class, sql, arrangeId, lunNum);
    }

    /**
     * 获取指定编排比赛进行到第几轮了
     *
     * @param arrangeId
     * @return
     */
    public int getMaxLun(Integer arrangeId) {
        String sql = "select MAX(lun_num) from t_race_score where arrange_id = ?";
        Integer lunNumb = generalDao.query(Integer.class, sql, arrangeId);
        return lunNumb == null ? 1 : lunNumb + 1;
    }

    @Transactional
    public void updateEnd(Integer arrangeId, Integer lun, Integer status) {
        String sql = "update t_race_score set end_is=? where arrange_id=? and lun_num=?";
        generalDao.execute(sql, status, arrangeId, lun);
    }

    @Transactional
    public void startScore(RaceScore raceScore) {
        String sql = "select id from t_race_score where arrange_id=? and lun_num=?";
        Integer id = generalDao.query(Integer.class, sql, raceScore.getArrangeId(), raceScore.getLunNum());
        if (id == null) {
            raceScore.setRival1Score(0f);
            raceScore.setRival2Score(0f);

            raceScore.setAddTime(new Date());
            raceScore.setRonda(1);
            raceScore.setEndIs(0);
            raceScoreDao.save(raceScore);
        } else {
            sql = "update t_race_score set rule=? where id=?";
            generalDao.execute(sql, raceScore.getRule(), id);
        }

        sql = "update t_race_arrange set status=? where id=?";
        generalDao.execute(sql, 1, raceScore.getArrangeId());
    }

    /**
     * 获取比赛最后一条记录
     *
     * @param arrangeId
     * @return
     */
    public RaceScore getLastScore(Integer arrangeId) {
        String sql = "select * from t_race_score where arrange_id = ? ORDER BY lun_num DESC limit 1";
        return generalDao.query(RaceScore.class, sql, arrangeId);
    }

    public RaceScore getScoreByArrange(String rival1, String rival2, Integer lun, Integer raceId, Integer arrangeType) {
        String sql = "select s.* from t_race_arrange a\n" +
                "join t_race_score s on a.id = s.arrange_id\n" +
                "where a.rival1=? and a.rival2=? and a.raceId=? and a.lun_num=? and a.arrange_type=?";
        return generalDao.query(RaceScore.class, sql, rival1, rival2, raceId, lun, arrangeType);
    }

    public String getUserIdsScore(String userids, Integer arrangeId, Integer raceLun) {
        if (StrUtils.empty(userids)) {
            return "";
        }

        String[] strs = userids.split(",");
        String sql = "select sum(score) from t_race_score_detail where arrange_id=? and race_lun=? and userid=?";
        String scores = "";
        for (String s : strs) {
            Float sco = generalDao.query(Float.class, sql, arrangeId, raceLun, s);
            if (sco == null) {
                sco = 0f;
            }
            scores += ((int) (float) sco) + ",";
        }
        if (!scores.equals("")) {
            scores.substring(0, scores.length() - 1);
        }
        return scores;
    }

    @Autowired
    private RaceScoreDao raceScoreDao;

    @Autowired
    private PubDao generalDao;

    @Autowired
    private RaceArrangeService raceArrangeService;

    @Autowired
    private RaceUserScoreDao raceUserScoreDao;

}
