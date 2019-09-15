package wxgh.sys.dao.union.race;


import org.springframework.stereotype.Repository;
import pub.dao.mybatis.MyBatisDao;
import wxgh.entity.union.race.Race;
import wxgh.param.union.race.RaceQuery;

import java.util.List;

/**
 * Created by Administrator on 2017/3/1.
 */
@Repository
public class RaceDao extends MyBatisDao<Race> {

    public List<Race> queryRaces(RaceQuery query) {
        return selectList("xlkai_queryRace", query);
    }

    public Integer queryCount(RaceQuery query) {
        return selectOne("xlkai_queryCount", query);
    }
}
