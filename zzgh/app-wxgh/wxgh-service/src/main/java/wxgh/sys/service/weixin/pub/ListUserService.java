package wxgh.sys.service.weixin.pub;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pub.dao.jdbc.PubDao;
import pub.dao.jdbc.sql.SQL;
import wxgh.entity.pub.user.UserDept;

import java.util.List;

/**
 * Created by Administrator on 2017/8/18.
 */
@Service
@Transactional(readOnly = true)
public class ListUserService {

    public List<UserDept> getUser(Integer id) {
        SQL sql = new SQL.SqlBuilder()
                .table("user u")
                .field("u.userid, u.mobile, u.avatar, u.name, u.deptid")
                .field("d.name as deptname")
                .join("dept d", "d.id = u.deptid")
                .where("u.deptid = ?")
                .build();
        return pubDao.queryList(UserDept.class, sql.sql(), id);
    }

    public Integer getparentid(Integer parentid){
        String sqls = "select deptid from t_dept where id = ?";
        Integer deptid = pubDao.query(Integer.class,sqls,parentid);
        return  deptid;
    }

    public List<UserDept> getDept(Integer id) {
        SQL sql = new SQL.SqlBuilder()
                .table("dept")
                .field("name as deptname, id as deptid")
                .where("parentid = ?")
                .build();
        return pubDao.queryList(UserDept.class, sql.sql(), id);
    }

    public List<UserDept> searchUser(String key) {
        SQL sql = new SQL.SqlBuilder()
                .table("user u")
                .field("u.userid, u.mobile, u.avatar, u.name, u.deptid")
                .field("d.name as deptname")
                .join("dept d", "d.id = u.deptid")
                .where("u.name like '%" + key + "%' or u.mobile like '%" + key + "%'")
                .build();
        return pubDao.queryList(UserDept.class, sql.sql());
    }

    public List<UserDept> searchDept(String key) {
        SQL sql = new SQL.SqlBuilder()
                .table("dept")
                .field("name as deptname, id as deptid")
                .where("name like '%" + key + "%'")
                .build();
        return pubDao.queryList(UserDept.class, sql.sql());
    }

    @Autowired
    private PubDao pubDao;

}
