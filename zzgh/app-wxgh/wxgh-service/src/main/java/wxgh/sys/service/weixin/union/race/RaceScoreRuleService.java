package wxgh.sys.service.weixin.union.race;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import wxgh.entity.union.race.RaceScoreRule;
import wxgh.sys.dao.union.race.RaceScoreRuleDao;

/**
 * Created by Administrator on 2017/3/30.
 */
@Service
@Transactional(readOnly = true)
public class RaceScoreRuleService {

    public void addRule(RaceScoreRule raceScoreRule) {
        raceScoreRuleDao.save(raceScoreRule);
    }


    @Autowired
    private RaceScoreRuleDao raceScoreRuleDao;
}
