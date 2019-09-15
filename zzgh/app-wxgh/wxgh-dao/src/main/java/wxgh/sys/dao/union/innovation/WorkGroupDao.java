package wxgh.sys.dao.union.innovation;


import org.springframework.stereotype.Repository;
import pub.dao.mybatis.MyBatisDao;
import wxgh.data.union.innovation.work.WorkGroupData;
import wxgh.entity.union.innovation.WorkGroup;
import wxgh.param.union.innovation.work.WorkQuery;

import java.util.List;

/**
 * Created by XDLK on 2016/8/19.
 * <p>
 * Date： 2016/8/19
 */
@Repository
public class WorkGroupDao extends MyBatisDao<WorkGroup> {

    
    public List<WorkGroup> getWorks(WorkQuery query) {
        return selectList("xlkai_getWork", query);
    }

    
    public WorkGroup getWork(WorkQuery query) {
        return selectOne("xlkai_getWork", query);
    }

    
    public void addWorkGroup(WorkGroupData workGroupData) {
        getSqlSession().insert("addWorkGroup", workGroupData);
    }

    
    public List<WorkGroup> getWorkGroups(WorkQuery workQuery) {
        return getSqlSession().selectList("getWorkGroups", workQuery);
    }

    
    public void del(Integer id) {
        getSqlSession().delete("del", id);
    }

    
    public void updateWorkGroup(WorkGroupData workGroupData) {
        getSqlSession().update("updateWorkGroup", workGroupData);
    }

    
    public Integer getCount(WorkQuery workQuery) {
        return selectOne("getCount", workQuery);
    }

    
    public Integer updateWork(WorkGroup workGroup) {
        return execute("xlkai_editWork", workGroup);
    }
}
