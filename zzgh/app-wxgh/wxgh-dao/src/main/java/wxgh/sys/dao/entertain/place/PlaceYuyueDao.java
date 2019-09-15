package wxgh.sys.dao.entertain.place;

import org.springframework.stereotype.Repository;
import pub.dao.mybatis.MyBatisDao;
import wxgh.entity.entertain.place.PlaceYuyue;
import wxgh.param.entertain.place.PlaceParam;

import java.util.List;

/**
 * @Author xlkai
 * @Version 2017/1/13
 */
@Repository
public class PlaceYuyueDao extends MyBatisDao<PlaceYuyue>{

    public List<PlaceYuyue> getList(PlaceParam placeQuery) {
        return selectList("getList",placeQuery);
    }

    public Integer getCount(PlaceParam placeQuery) {
        return selectOne("getCount",placeQuery);
    }

    public Integer delYuyue(Integer id) {
        return getSqlSession().delete("delYuyue",id);
    }
}
