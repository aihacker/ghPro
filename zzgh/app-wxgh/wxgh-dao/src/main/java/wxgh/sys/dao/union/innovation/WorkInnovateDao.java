package wxgh.sys.dao.union.innovation;


import org.springframework.stereotype.Repository;
import pub.dao.mybatis.MyBatisDao;
import wxgh.data.union.innovation.work.JiShenheData;
import wxgh.entity.union.innovation.WorkInnovate;
import wxgh.param.union.innovation.work.WorkInnovateQuery;

import java.util.List;

/**
 * Created by WIN on 2016/8/24.
 */
@Repository
public class WorkInnovateDao extends MyBatisDao<WorkInnovate> {

    
    public List<WorkInnovate> getInnovates(WorkInnovateQuery query) {
        return selectList("xlkai_getInnovate", query);
    }

    
    public List<WorkInnovate> getWorkInnovates(WorkInnovateQuery query) {
        return selectList("getWorkInnovates", query);
    }

    
    public WorkInnovate getInnovate(WorkInnovateQuery query) {
        return selectOne("xlkai_getInnovate", query);
    }

    
    public Integer shenhe(JiShenheData jiShenheData) {
      return getSqlSession().update("shenhe", jiShenheData);
    }

    
    public Integer del(Integer id) {
       return getSqlSession().delete("del", id);
    }

    
    public Integer getCount(WorkInnovateQuery query) {
        return selectOne("getCount", query);
    }

    
    public List<WorkInnovate> applyListRefresh(WorkInnovateQuery query) {
        return selectList("applyListRefresh",query);
    }

    
    public List<WorkInnovate> applyListMore(WorkInnovateQuery query) {
        return selectList("applyListMore",query);
    }

}
