package wxgh.sys.service.weixin.union.race;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pub.dao.jdbc.PubDao;
import wxgh.entity.union.race.RaceJoin;
import wxgh.param.union.race.RaceJoinQuery;
import wxgh.sys.dao.union.race.RaceJoinDao;

import java.util.List;

/**
 * Created by Administrator on 2017/3/2.
 */
@Service
@Transactional(readOnly = true)
public class RaceJoinService {

    public List<RaceJoin> getjoins(RaceJoinQuery query) {
        return raceJoinDao.getJoins(query);
    }

    public List<RaceJoin> getJoinsAndName(RaceJoinQuery query) {
        String sql = "SELECT %s from t_race_join j\n" +
                "where j.race_id = :raceId";
        String seSql = String.format(sql, "j.*, (select GROUP_CONCAT(u.`name` ORDER BY FIND_IN_SET(u.userid, j.userid)) from t_user u where FIND_IN_SET(u.userid,j.userid)) as name");
        if (query.getPageIs()) {
            String countSql = String.format(sql, "count(*)");
            Integer count = pubDao.queryParamInt(countSql, query);

            query.setTotalCount(count);
            seSql += " limit " + query.getPagestart() + ", " + query.getRowsPerPage();
        }
        return pubDao.queryList(seSql, query, RaceJoin.class);
    }

    public Integer coutJoin(RaceJoinQuery query) {
        return raceJoinDao.countJoin(query);
    }

    public RaceJoin getJoin(String userid, Integer raceId) {
        String sql = "select * from t_race_join where find_in_set(?, userid) and race_id=? limit 1";
        return pubDao.query(RaceJoin.class, sql, userid, raceId);
    }

    public boolean isJoin(String userid, Integer raceId) {
        String sql = "select id from t_race_join where FIND_IN_SET(?, userid) and race_id = ? limit 1";
        Integer id = pubDao.query(Integer.class, sql, userid, raceId);
        if (id != null) {
            return true;
        }
        return false;
    }

    @Transactional
    public void save(RaceJoin raceJoin) {
        raceJoinDao.save(raceJoin);
    }

    @Autowired
    private RaceJoinDao raceJoinDao;

    @Autowired
    private PubDao pubDao;

}
