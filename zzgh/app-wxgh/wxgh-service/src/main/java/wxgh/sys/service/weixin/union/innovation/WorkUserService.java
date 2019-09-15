package wxgh.sys.service.weixin.union.innovation;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pub.dao.jdbc.PubDao;
import wxgh.entity.union.innovation.WorkUser;
import wxgh.param.union.innovation.work.WorkUserQuery;
import wxgh.sys.dao.union.innovation.WorkUserDao;

import java.util.List;

/**
 * Created by XDLK on 2016/8/19.
 * <p>
 * Date： 2016/8/19
 */
@Service
@Transactional(readOnly = true)
public class WorkUserService {


    @Autowired
    private WorkUserDao workUserDao;

    
    public List<WorkUser> getUsers(WorkUserQuery query) {
        return workUserDao.getUsers(query);
    }

    
    public WorkUser getUser(Integer id) {
        return workUserDao.get(id);
    }

    public List<String> getUserImg(WorkUserQuery query) {
        String sql = "select u.avatar from t_work_user w\n" +
                "join t_user u on w.userid = u.userid\n" +
                "where w.`status`=? and w.work_id=? and w.work_type=?";
        sql += " ORDER BY w.user_type DESC";
        if (query.getPageIs()) {
            sql += " limit " + query.getPagestart() + ", " + query.getRowsPerPage();
        }
        return pubDao.queryList(String.class, sql, query.getStatus(), query.getWorkId(), query.getWorkType());
    }
    
    @Transactional
    public Integer saveUser(WorkUser workUser) {
        return (Integer) workUserDao.save(workUser);
    }

    public Integer countUser(WorkUserQuery query) {
        return workUserDao.getCount(query);
    }

    @Autowired
    private PubDao pubDao;
}
