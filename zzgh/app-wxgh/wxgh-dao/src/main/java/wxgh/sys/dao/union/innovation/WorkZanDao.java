package wxgh.sys.dao.union.innovation;


import org.springframework.stereotype.Repository;
import pub.dao.mybatis.MyBatisDao;
import wxgh.entity.union.innovation.WorkZan;

/**
 * Created by XDLK on 2016/6/20.
 * <p>
 * Date： 2016/6/20
 */

@Repository
public class WorkZanDao extends MyBatisDao<WorkZan> {

    
    public Integer getCount(Integer id) {
        return selectOne("getZanCount",id);
    }

    
    public Integer delZan(WorkZan workZan) {
        return getSqlSession().delete("delZan",workZan);
    }

    
    public Integer getOne(WorkZan workZan) {
        return selectOne("getOne",workZan);
    }
}
