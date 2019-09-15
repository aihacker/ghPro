package wxgh.sys.dao.entertain.nanhai.place;

import org.springframework.stereotype.Repository;
import pub.dao.mybatis.MyBatisDao;
import wxgh.entity.entertain.nanhai.place.NanHaiPlaceTime;
import wxgh.entity.entertain.place.PlaceTime;
import wxgh.param.entertain.place.TimeParam;

import java.util.List;

/**
 * @Author xlkai
 * @Version 2017/1/12
 */
@Repository
public class NanHaiPlaceTimeDao extends MyBatisDao<NanHaiPlaceTime>{


    public List<NanHaiPlaceTime> getTimes(TimeParam query) {
        return selectList("xlkai_getTimes", query);
    }
}
