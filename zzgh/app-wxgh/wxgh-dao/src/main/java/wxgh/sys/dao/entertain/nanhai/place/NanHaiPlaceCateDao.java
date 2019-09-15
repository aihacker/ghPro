package wxgh.sys.dao.entertain.nanhai.place;

import org.springframework.stereotype.Repository;
import pub.dao.mybatis.MyBatisDao;
import wxgh.entity.entertain.nanhai.place.NanHaiPlaceCate;
import wxgh.entity.entertain.place.PlaceCate;
import wxgh.param.entertain.place.PlaceCateParam;

import java.util.List;

/**
 * Created by 蔡炳炎 on 2017/7/29.
 */
@Repository
public class NanHaiPlaceCateDao extends MyBatisDao<NanHaiPlaceCate> {

    public List<NanHaiPlaceCate> getList(PlaceCateParam placeCateQuery) { return selectList("getData", placeCateQuery); }

    public NanHaiPlaceCate getOne(PlaceCateParam placeCateQuery) {
        return selectOne("getData", placeCateQuery);
    }

    public Integer updateData(PlaceCateParam placeCateQuery) {
        return getSqlSession().update("liuhe.sys.entity.PlaceCate.updateData", placeCateQuery);
    }
}
