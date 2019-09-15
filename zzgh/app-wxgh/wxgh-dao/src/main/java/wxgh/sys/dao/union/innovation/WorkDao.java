package wxgh.sys.dao.union.innovation;


import org.springframework.stereotype.Repository;
import pub.dao.mybatis.MyBatisDao;
import wxgh.entity.party.beauty.Work;
import wxgh.param.party.beauty.WorkQuery;

import java.util.List;

/**
 * Created by XDLK on 2016/6/20.
 * <p>
 * Date： 2016/6/20
 */

@Repository
public class WorkDao extends MyBatisDao<Work> {

    
    public Work show(Integer id) {
        return selectOne("getOne", id);
    }

    
    public List<Work> getWorkList(Integer type) {
        return selectList("getWorkList", type);
    }

    
    public List<Work> listWorks(WorkQuery query) {
        return selectList("xlkai_getWorks", query);
    }

    
    public Integer countWorks(WorkQuery query) {
        return selectOne("xlkai_count", query);
    }
}
