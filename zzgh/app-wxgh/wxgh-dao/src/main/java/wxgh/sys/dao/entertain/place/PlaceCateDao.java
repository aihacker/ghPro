package wxgh.sys.dao.entertain.place;

import org.springframework.stereotype.Repository;
import pub.dao.mybatis.MyBatisDao;
import wxgh.entity.entertain.place.PlaceCate;
import wxgh.param.entertain.place.PlaceCateParam;

import java.util.List;

/**
 * Created by 蔡炳炎 on 2017/7/29.
 */
@Repository
public class PlaceCateDao extends MyBatisDao<PlaceCate> {

    public List<PlaceCate> getList(PlaceCateParam placeCateQuery) {
        return selectList("getData", placeCateQuery);
    }

    public PlaceCate getOne(PlaceCateParam placeCateQuery) {
        return selectOne("getData", placeCateQuery);
    }

    public Integer updateData(PlaceCateParam placeCateQuery) {
        return getSqlSession().update("liuhe.sys.entity.PlaceCate.updateData", placeCateQuery);
    }
}
