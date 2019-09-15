package wxgh.sys.dao.union.race;


import org.springframework.stereotype.Repository;
import pub.dao.mybatis.MyBatisDao;
import wxgh.entity.union.race.RaceJoin;
import wxgh.param.union.race.RaceJoinQuery;

import java.util.List;

/**
 * Created by Administrator on 2017/3/2.
 */
@Repository
public class RaceJoinDao extends MyBatisDao<RaceJoin> {


    public List<RaceJoin> getJoins(RaceJoinQuery query) {
        return selectList("xlkai_queryJoin", query);
    }

    public Integer countJoin(RaceJoinQuery query) {
        return selectOne("xlkai_queryCount", query);
    }
}
