package wxgh.sys.service.weixin.union.race;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pub.dao.jdbc.PubDao;
import wxgh.entity.union.race.RaceGroupDetail;
import wxgh.sys.dao.union.race.RaceGroupDetailDao;

/**
 * Created by Administrator on 2017/3/20.
 */
@Service
@Transactional(readOnly = true)
public class RaceGroupDetailService {

    @Transactional
    public void save(RaceGroupDetail raceGroup) {
        raceGroupDetailDao.save(raceGroup);
    }


    @Autowired
    private RaceGroupDetailDao raceGroupDetailDao;

    @Autowired
    private PubDao generalDao;
}
