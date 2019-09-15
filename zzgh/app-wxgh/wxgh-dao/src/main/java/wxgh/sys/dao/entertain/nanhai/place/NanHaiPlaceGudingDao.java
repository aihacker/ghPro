package wxgh.sys.dao.entertain.nanhai.place;

import org.springframework.stereotype.Repository;
import pub.dao.mybatis.MyBatisDao;
import wxgh.entity.entertain.nanhai.place.NanHaiPlaceGuding;
import wxgh.entity.entertain.place.PlaceGuding;

/**
 * Created by Administrator on 2017/4/14.
 */
@Repository
public class NanHaiPlaceGudingDao extends MyBatisDao<NanHaiPlaceGuding> {

    public void update(NanHaiPlaceGuding guding) {
        execute("xlkai_update", guding);
    }
}
