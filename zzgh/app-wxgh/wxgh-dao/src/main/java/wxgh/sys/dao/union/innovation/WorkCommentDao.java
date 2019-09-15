package wxgh.sys.dao.union.innovation;


import org.springframework.stereotype.Repository;
import pub.dao.mybatis.MyBatisDao;
import wxgh.entity.party.beauty.WorkComment;

import java.util.List;

/**
 * Created by XDLK on 2016/6/20.
 * <p>
 * Date： 2016/6/20
 */

@Repository
public class WorkCommentDao extends MyBatisDao<WorkComment> {

    
    public Integer getCount(Integer id) {
        return selectOne("getComCount",id);
    }

    
    public List<WorkComment> getList(Integer id) {
        return selectList("getComList",id);
    }
}
