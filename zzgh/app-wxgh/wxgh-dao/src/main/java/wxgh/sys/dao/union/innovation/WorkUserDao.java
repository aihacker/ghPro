package wxgh.sys.dao.union.innovation;


import org.springframework.stereotype.Repository;
import pub.dao.mybatis.MyBatisDao;
import wxgh.entity.union.innovation.WorkUser;
import wxgh.param.union.innovation.work.WorkUserQuery;

import java.util.List;

/**
 * Created by XDLK on 2016/8/19.
 * <p>
 * Date： 2016/8/19
 */
@Repository
public class WorkUserDao extends MyBatisDao<WorkUser> {

    
    public List<WorkUser> getUsers(WorkUserQuery query) {
        return selectList("xlkai_getUser", query);
    }


    
    public void del(Integer id) {
        getSqlSession().delete("del", id);
    }

    
    public Integer getCount(WorkUserQuery query) {
        Integer count = selectOne("xlkai_countUser", query);
        return count == null ? 0 : count;
    }

}
