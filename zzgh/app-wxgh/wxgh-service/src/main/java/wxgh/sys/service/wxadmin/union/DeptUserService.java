package wxgh.sys.service.wxadmin.union;

import com.libs.common.type.StringUtils;
import com.libs.common.type.TypeUtils;
import com.weixin.WeixinException;
import com.weixin.api.DeptApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pub.dao.jdbc.PubDao;
import pub.dao.jdbc.sql.SQL;
import pub.error.ValidationError;
import wxgh.app.sys.task.UserAsync;
import wxgh.data.pub.user.DeptUserList;
import wxgh.data.pub.user.UserExist;
import wxgh.data.wxadmin.user.DeptList;
import wxgh.entity.pub.Dept;
import wxgh.entity.pub.User;
import wxgh.param.pcadmin.user.UserListParam;

import java.util.List;

/**
 * Created by Administrator on 2017/9/25.
 */
@Service
@Transactional(readOnly = true)
public class DeptUserService {

    public Integer getDeptId(Integer deptid) {
        String sql = "select deptid from t_dept where id= ?";
        return pubDao.query(Integer.class, sql, deptid);
    }

    public List<String> getUserids(String userids) {
        String sql = "select userid from t_user where id in(" + userids + ")";
        return pubDao.queryList(String.class, sql);
    }

    /*public List<DeptUserList> listUsers(UserListParam param) {
        SQL.SqlBuilder sql = new SQL.SqlBuilder()
                .table("user u")
                .field("u.id, u.userid, u.name as username, u.avatar, u.mobile, d.name as deptname, u.email, u.position, u.weixinid, d.id as deptid, IFNULL(pu.remark,'')as remark")
                .field("(select GROUP_CONCAT(`name` separator '-') from t_dept where FIND_IN_SET(id, u.department)) as dept")
                .join("dept d", "d.id = u.deptid")
                .join("party_dept_user pu", "pu.userid = u.userid", Join.Type.LEFT)
                .order("u.deptid");
        if (param.getDeptid() != null && param.getDeptid() != 1) {
            sql.where("find_in_set(:deptid, u.department)");
        }
        if (param.getSearch() != null) {
            sql.where("(u.name like '%" + param.getSearch() + "%' or u.mobile like '" + param.getSearch() + "%')");
        }
        return pubDao.queryPage(sql, param, DeptUserList.class);
    }*/

    public List<DeptUserList> listUsers(UserListParam param) {
        SQL.SqlBuilder sql = new SQL.SqlBuilder()
                .table("user u")
                .field("u.id, u.userid, u.name as username, u.avatar, u.mobile, d.name as deptname, u.email, u.position, u.weixinid, d.id as deptid")
                .field("(select GROUP_CONCAT(`name` separator '-') from t_dept where FIND_IN_SET(id, u.department)) as dept")
                .join("dept d", "d.id = u.deptid")
                .order("u.deptid");
        if (param.getDeptid() != null && param.getDeptid() != 1) {
            sql.where("find_in_set(:deptid, u.department)");
        }
        if (param.getSearch() != null) {
            sql.where("(u.name like '%" + param.getSearch() + "%' or u.mobile like '" + param.getSearch() + "%')");
        }
        return pubDao.queryPage(sql, param, DeptUserList.class);
    }

    public List<DeptList> listDept(Integer parentid) {

        String sql = new SQL.SqlBuilder()
                .table("dept d")
                .field("d.*")
                .field("(select count(*)>0 from t_dept where parentid = d.id) as hasChild")
                .where("parentid = ?")
                .build()
                .sql();
        return pubDao.queryList(DeptList.class, sql, parentid);
    }

    @Transactional
    public void updateUser(User user) {
        if (TypeUtils.empty(user.getUserid())) {
            user.setUserid("wxgh" + user.getMobile());
        }

        String existSql = "select " +
                "(SELECT count(*) > 0 from t_user where mobile = ? and userid != ?) as mobile, " +
                "(SELECT count(*) > 0 from t_user where email = ? and userid != ?) as email, " +
                "(SELECT count(*) > 0 from t_user where weixinid = ? and userid != ?) as weixinid";
        UserExist userExist = pubDao.query(UserExist.class, existSql,
                user.getMobile(), user.getUserid(),
                user.getEmail(), user.getEmail(),
                user.getWeixinid(), user.getWeixinid());

        if (!TypeUtils.empty(user.getMobile()) && userExist.getMobile() == 1) {
            throw new ValidationError("手机号码已存在");
        }
        if (!TypeUtils.empty(user.getEmail()) && userExist.getEmail() == 1) {
            throw new ValidationError("邮箱地址已存在");
        }
        if (!TypeUtils.empty(user.getWeixinid()) && userExist.getWeixinid() == 1) {
            throw new ValidationError("微信号已存在");
        }
        //更新
        if (user.getId() != null) {
            SQL.SqlBuilder sql = new SQL.SqlBuilder()
                    .update("user");
            if (user.getName() != null) {
                sql.set("name = :name");
            }
            if (user.getMobile() != null) {
                sql.set("mobile = :mobile");
            }
            if (user.getEmail() != null) {
                sql.set("email = :email");
            }
            if (user.getWeixinid() != null) {
                sql.set("weixinid = :weixinid");
            }
            if (user.getDeptid() != null) {
                sql.set("deptid = :deptid");
                sql.set("department = query_dept_parent(:deptid)");
            }
            if (user.getPosition() != null) {
                sql.set("position = :position");
            }
            sql.where("id = :id");
            pubDao.executeBean(sql.build().sql(), user);

            userAsync.updateUser(user);
        } else {

            // 2017 - 12 - 27 判断userid
            user.setUserid(uniqueUserid(user.getUserid(), user.getMobile()));

            String sql = "insert into t_user(userid, name, mobile, email, weixinid, deptid, department, position)\n" +
                    "select :userid, :name, :mobile, :email, :weixinid, :deptid, query_dept_parent(id), :position from t_dept where id = :deptid";
            pubDao.executeBean(sql, user);

            userAsync.addUser(user);
        }
    }

    private String uniqueUserid(String userid, String mobile) {
        String hasSql = "select userid from t_user where userid = ?";
        String hUserid = pubDao.query(String.class, hasSql, userid);
        if (TypeUtils.empty(hUserid))
            return userid;
        else
            return uniqueUserid(StringUtils.randLetter(4) + mobile, mobile);
    }

    @Transactional
    public void delUser(String id) {
        userAsync.delUser(id);

        pubDao.execute(SQL.deleteByIds("user", id));
    }

    @Transactional
    public void delDept(Integer id) {
        //判断是否有子部门，若有则无法删除
        String hasSql = "select count(*) from t_dept where parentid = (SELECT id from t_dept where deptid = ?)";
        Integer count = pubDao.queryInt(hasSql, id);
        if (count > 0) {
            throw new ValidationError("请删除次部门下的成员或子部门后，再删除此部门！");
        }
        String sql = "delete from t_dept where deptid = ?";
        pubDao.execute(sql, id);

        userAsync.delDept(id);
    }

    @Transactional
    public void updateDept(Dept dept) throws WeixinException {
        if (dept.getId() != null) {
            SQL.SqlBuilder sql = new SQL.SqlBuilder()
                    .update("dept")
                    .where("id = :id");
            if (dept.getName() != null) {
                sql.set("name = :name");
            }
            if (dept.getParentid() != null) {
                sql.set("parentid = :parentid");
            }
            if (dept.getOrder() != null) {
                sql.set("`order` = :order");
            }
            pubDao.executeBean(sql.build().sql(), dept);

            userAsync.updateDept(dept);
        } else {
            com.weixin.bean.dept.Dept wxDept = new com.weixin.bean.dept.Dept();
            wxDept.setName(dept.getName());
            wxDept.setParentid(dept.getParentid());
            Integer deptid = DeptApi.create(wxDept);

            dept.setDeptid(deptid);
            String sql = "insert into t_dept(deptid, name, parentid, `order`) " +
                    "select :deptid, :name, id, :order from t_dept where deptid = :parentid";
            pubDao.executeBean(sql, dept);
        }
    }

    @Autowired
    private PubDao pubDao;

    @Autowired
    private UserAsync userAsync;
}
