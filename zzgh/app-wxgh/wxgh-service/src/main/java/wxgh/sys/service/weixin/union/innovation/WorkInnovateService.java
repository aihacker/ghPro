package wxgh.sys.service.weixin.union.innovation;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import wxgh.data.union.innovation.work.JiShenheData;
import wxgh.entity.union.innovation.WorkInnovate;
import wxgh.param.union.innovation.work.WorkInnovateQuery;
import wxgh.sys.dao.union.innovation.WorkInnovateDao;

import java.util.List;

/**
 * Created by XDLK on 2016/8/29.
 * <p>
 * Date： 2016/8/29
 */
@Service
@Transactional(readOnly = true)
public class WorkInnovateService {

    @Autowired
    private WorkInnovateDao workInnovateDao;

    
    @Transactional
    public Integer addInnovate(WorkInnovate workInnovate) {
        workInnovateDao.save(workInnovate);
        return workInnovate.getId();
    }

    
    public WorkInnovate getInnovate(Integer id) {
        return workInnovateDao.get(id);
    }

    
    public List<WorkInnovate> getInnovates(WorkInnovateQuery query) {
        return workInnovateDao.getInnovates(query);
    }

    
    @Transactional
    public Integer delete(Integer id) {
        return workInnovateDao.del(id);
    }

    
    @Transactional
    public Integer shenhe(JiShenheData jiShenheData) {
        return workInnovateDao.shenhe(jiShenheData);
    }

    
    public WorkInnovate getOne(WorkInnovateQuery query) {
        return workInnovateDao.getInnovate(query);
    }

    
    public List<WorkInnovate> applyListRefresh(WorkInnovateQuery query) {
        return workInnovateDao.applyListRefresh(query);
    }

    
    public List<WorkInnovate> applyListMore(WorkInnovateQuery query) {
        return workInnovateDao.applyListMore(query);
    }
}
