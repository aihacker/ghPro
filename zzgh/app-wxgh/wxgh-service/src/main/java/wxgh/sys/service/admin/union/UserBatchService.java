package wxgh.sys.service.admin.union;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pub.dao.jdbc.PubDao;
import pub.dao.jdbc.sql.SQL;
import wxgh.entity.pub.User;

import java.util.List;

/**
 * Created by Administrator on 2017/9/26.
 */
@Service
@Transactional(readOnly = true)
public class UserBatchService {

    public String getDeptName(Integer deptid) {
        String sql = "select name from t_dept where id = ?";
        return pubDao.query(String.class, sql, deptid);
    }

    public List<User> batchUsers(Integer deptid) {
        String sql = new SQL.SqlBuilder()
                .table("user u")
                .field("u.userid,u.name,u.position,u.mobile,u.email,u.weixinid,u.gender")
                .field("(select GROUP_CONCAT(`name` separator '-') from t_dept where FIND_IN_SET(id, u.department)) as department")
                .where("find_in_set(?, u.department)")
                .build().sql();
        return pubDao.queryList(User.class, sql, deptid, deptid);
    }

    @Autowired
    private PubDao pubDao;

}
