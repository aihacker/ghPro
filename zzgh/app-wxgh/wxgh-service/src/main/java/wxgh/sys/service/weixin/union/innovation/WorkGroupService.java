package wxgh.sys.service.weixin.union.innovation;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import wxgh.entity.union.innovation.WorkGroup;
import wxgh.param.union.innovation.work.WorkQuery;
import wxgh.sys.dao.union.innovation.WorkGroupDao;

import java.util.List;

/**
 * Created by XDLK on 2016/8/19.
 * <p>
 * Date： 2016/8/19
 */
@Service
@Transactional(readOnly = true)
public class WorkGroupService {

    
    public List<WorkGroup> getWorks(WorkQuery query) {
        return workGroupDao.getWorks(query);
    }

    
    public WorkGroup getWork(Integer id) {
        WorkQuery query = new WorkQuery();
        query.setId(id);
        return workGroupDao.getWork(query);
    }

    
    public WorkGroup getWork(WorkQuery query) {
        return workGroupDao.getWork(query);
    }

    
    @Transactional
    public Integer saveWork(WorkGroup group) {
        workGroupDao.save(group);
        return group.getId();
    }

    
    @Transactional
    public Integer updateWork(WorkGroup workGroup) {
        return workGroupDao.updateWork(workGroup);
    }

    @Autowired
    private WorkGroupDao workGroupDao;

}
