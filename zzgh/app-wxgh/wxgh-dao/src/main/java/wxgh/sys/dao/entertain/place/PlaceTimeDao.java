package wxgh.sys.dao.entertain.place;

import org.springframework.stereotype.Repository;
import pub.dao.mybatis.MyBatisDao;
import wxgh.entity.entertain.place.PlaceTime;
import wxgh.param.entertain.place.TimeParam;

import java.util.List;

/**
 * @Author xlkai
 * @Version 2017/1/12
 */
@Repository
public class PlaceTimeDao extends MyBatisDao<PlaceTime>{


    public List<PlaceTime> getTimes(TimeParam query) {
        return selectList("xlkai_getTimes", query);
    }
}
