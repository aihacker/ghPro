package wxgh.sys.service.weixin.union.innovation;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pub.dao.jdbc.PubDao;
import wxgh.data.union.innovation.member.UserQueryParam;
import wxgh.entity.pub.User;

import java.util.List;

/**
 *----------------------------------------------------------
 * @Description 
 *----------------------------------------------------------
 * @Author  Ape
 *----------------------------------------------------------
 * @Email <16511660@qq.com>
 *----------------------------------------------------------
 * @Date 2017-07-21 17:24
 *----------------------------------------------------------
 */
@Transactional(readOnly = true)
@Service
public class User2Service {

    @Autowired
    private PubDao pubDao;

    /**
     * 查询用户
     * @param deptid
     * @param uname
     * @return
     */
    public List<UserQueryParam> getList(Integer deptid, String uname){
        String sql = "SELECT u.name, u.userid, u.mobile, u.avatar, d.name as deptname FROM t_user u join t_dept d on d.id = u.deptid WHERE  u.deptid = ? AND u.name like concat('%', ? , '%')";
        return pubDao.queryList(UserQueryParam.class, sql, deptid, uname);
    }

    /**
     * 查询用户
     * @param uname
     * @return
     */
    public List<UserQueryParam> getList( String uname){
        String sql = "SELECT u.name, u.userid, u.mobile, u.avatar, d.name as deptname FROM t_user u left join t_dept d on d.id = u.deptid WHERE u.name like concat('%', ? , '%')";
        return pubDao.queryList(UserQueryParam.class, sql, uname);
    }

    public List<User> searchUser(String key) {
        String sql = "select u.name,u.mobile,u.deptid,u.userid,u.avatar, wd.name as deptname from t_user u join t_dept wd on wd.id = u.deptid where u.name like ? or u.mobile like ? or u.userid like ? group by u.userid limit 8";
        return pubDao.queryList(User.class, sql, "%" + key + "%", key + "%", "%" + key + "%");
    }
    
}

