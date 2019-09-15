package wxgh.sys.service.weixin.union.race;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pub.dao.jdbc.PubDao;
import wxgh.entity.union.race.Race;
import wxgh.param.union.race.RaceQuery;
import wxgh.sys.dao.union.race.RaceDao;

import java.util.List;

/**
 * Created by Administrator on 2017/3/1.
 */
@Service
@Transactional(readOnly = true)
public class RaceService {

    @Transactional
    public void save(Race race) {
        raceDao.save(race);
    }

    public List<Race> getRaces(RaceQuery query) {
        return raceDao.queryRaces(query);
    }

    public Integer getCount(RaceQuery query) {
        return raceDao.queryCount(query);
    }

    public Race getRace(Integer id) {
        String sql = "select r.*, u.name as username from t_race r\n" +
                "join t_user u on r.userid = u.userid\n" +
                "where r.id=?";
        return generalDao.query(Race.class, sql, id);
    }

    public Integer getBianpaiStatus(Integer raceId) {
        String sql = "select bianpai_is from t_race where id = ?";
        return generalDao.query(Integer.class, sql, raceId);
    }

    @Transactional
    public void updateBianpaiStatus(Integer raceId, Integer status) {
        String sql = "update t_race set bianpai_is=? where id=?";
        generalDao.execute(sql, status, raceId);
    }

    @Transactional
    public void delete(String id) {
        String[] ids = id.split(",");
        raceDao.delete(ids);
    }

    @Autowired
    private RaceDao raceDao;

    @Autowired
    private PubDao generalDao;

}
