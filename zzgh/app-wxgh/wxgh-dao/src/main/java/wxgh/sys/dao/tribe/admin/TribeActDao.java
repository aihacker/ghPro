package wxgh.sys.dao.tribe.admin;

import org.springframework.stereotype.Repository;
import pub.dao.mybatis.MyBatisDao;
import wxgh.entity.tribe.TribeAct;
import wxgh.param.tribe.admin.TribeActParam;
import wxgh.query.tribe.TribeActQuery;

import java.util.List;

/**
 * Created by 蔡炳炎 on 2017/8/22.
 */
@Repository
public class TribeActDao extends MyBatisDao<TribeAct> {

    public List<TribeAct> getData(TribeActParam tribeActParam) {
        return selectList("getData", tribeActParam);
    }

    public void updateData(TribeAct tribeAct) {
        getSqlSession().update("wxgh.entity.tribe.TribeAct.updateData",tribeAct);
    }

    public TribeAct getOne(TribeAct tribeAct) {
        return selectOne("getOne", tribeAct);
    }

    public List<TribeAct> sheheTribeAct(TribeActQuery query) {
        return selectList("sheheTribeAct",query);
    }
}
