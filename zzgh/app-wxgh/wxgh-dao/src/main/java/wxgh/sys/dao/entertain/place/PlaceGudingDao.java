package wxgh.sys.dao.entertain.place;

import org.springframework.stereotype.Repository;
import pub.dao.mybatis.MyBatisDao;
import wxgh.entity.entertain.place.PlaceGuding;

/**
 * Created by Administrator on 2017/4/14.
 */
@Repository
public class PlaceGudingDao extends MyBatisDao<PlaceGuding> {

    public void update(PlaceGuding guding) {
        execute("xlkai_update", guding);
    }
}
