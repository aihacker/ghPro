package wxgh.sys.dao.four;

import org.springframework.stereotype.Repository;
import pub.dao.mybatis.MyBatisDao;
import wxgh.data.FpShenheData;
import wxgh.entity.four.FourPropagate;
import wxgh.param.four.PropagateParam;
import wxgh.param.four.QueryPropagateParam;

import java.util.List;

/**
 * Created by WIN on 2016/8/24.
 */
@Repository
public class PropagateDao extends MyBatisDao<FourPropagate> {


    public List<FourPropagate> getPropagates(QueryPropagateParam queryPropagate) {
        return getSqlSession().selectList("getPropagates", queryPropagate);
    }

    public Integer shenhe(FpShenheData fpShenheData) {
        return getSqlSession().update("shenhe", fpShenheData);
    }

    public List<FourPropagate> getPropagates(PropagateParam query) {
        return selectList("xlkai_getPropagate", query);
    }

    public Integer getCount(QueryPropagateParam queryPropagate) {
        return selectOne("getCount", queryPropagate);
    }

    public FourPropagate getPropagate(PropagateParam query) {
        return selectOne("xlkai_getPropagate", query);
    }

    public List<FourPropagate> applyListRefresh(QueryPropagateParam query) {
        return selectList("applyListRefresh", query);
    }

    public List<FourPropagate> applyListMore(QueryPropagateParam query) {
        return selectList("applyListMore", query);
    }

    public FourPropagate getOne(QueryPropagateParam queryPropagate) {
        return selectOne("getPropagates", queryPropagate);
    }

    public Integer updateOne(FourPropagate fourPropagate) {
        return getSqlSession().update("updateOne", fourPropagate);
    }

}
