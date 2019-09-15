package wxgh.sys.dao.entertain.place;

import org.springframework.stereotype.Repository;
import pub.dao.mybatis.MyBatisDao;
import wxgh.entity.entertain.place.Place;
import wxgh.param.entertain.place.PlaceParam;

import java.util.List;

/**
 * Created by XDLK on 2016/9/5.
 * <p>
 * Date： 2016/9/5
 */
@Repository
public class PlaceDao extends MyBatisDao<Place> {

    public List<Place> getPlaces(PlaceParam query) {
        return selectList("xlkai_getPlace", query);
    }

    public List<Place> getSchePlaces(PlaceParam query) {
        return selectList("xlkai_getAllPlace", query);
    }

    public Place getSchePlace(PlaceParam query) {
        return selectOne("xlkai_getAllPlace", query);
    }

    public Place getPlace(PlaceParam query) {
        return selectOne("xlkai_getPlace", query);
    }

    public Integer updatePlace(Place place) {
        return execute("xlkai_updatePlace", place);
    }

    public Integer addPlace(Place place) {
        return getSqlSession().insert("liuhe.sys.entity.Place.addPlace", place);
    }

    public Integer delPlace(Integer id) {
        return getSqlSession().delete("liuhe.sys.entity.Place.delPlace", id);
    }

    public Integer getCount(PlaceParam placeQuery) {
        return selectOne("getCount", placeQuery);
    }
}
