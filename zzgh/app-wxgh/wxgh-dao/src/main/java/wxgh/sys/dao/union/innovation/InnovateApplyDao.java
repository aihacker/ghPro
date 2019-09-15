package wxgh.sys.dao.union.innovation;


import org.springframework.stereotype.Repository;
import pub.dao.mybatis.MyBatisDao;
import wxgh.entity.union.innovation.InnovateApply;
import wxgh.param.union.innovation.QueryApply;

import java.util.List;

/**
 * Created by ✔ on 2016/11/10.
 */
@Repository
public class InnovateApplyDao extends MyBatisDao<InnovateApply> {

    
    public Integer updateApply(InnovateApply innovateApply) {
        return getSqlSession().update("updateApply", innovateApply);
    }

    
    public List<InnovateApply> getApplys(QueryApply queryApply) {
        return selectList("xlkai_getApplys", queryApply);
    }

    
    public Integer countApply(QueryApply queryApply) {
        return selectOne("xlkai_getCounts", queryApply);
    }

    
    public List<InnovateApply> getAllApplys(QueryApply queryApply) {
        return selectList("get_applys",queryApply);
    }
}
