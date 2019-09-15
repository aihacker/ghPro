package wxgh.sys.service.weixin.union.race;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import wxgh.entity.union.race.RaceUserScore;
import wxgh.sys.dao.union.race.RaceUserScoreDao;

import java.util.List;

/**
 * Created by Administrator on 2017/3/30.
 */
@Service
@Transactional(readOnly = true)
public class RaceUserScoreService {

    @Transactional
    public void addScore(RaceUserScore score) {
        raceUserScoreDao.save(score);
    }

    @Transactional
    public void addScores(List<RaceUserScore> scores) {
        for (RaceUserScore score : scores) {
            raceUserScoreDao.save(score);
        }
    }

    @Autowired
    private RaceUserScoreDao raceUserScoreDao;

}
