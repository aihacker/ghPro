package wxgh.sys.service.weixin.pub;

import com.libs.common.type.TypeUtils;
import com.weixin.WeixinException;
import com.weixin.api.UserApi;
import com.weixin.bean.dept.Dept;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pub.dao.jdbc.PubDao;
import pub.dao.jdbc.sql.JdbcSQL;
import pub.dao.jdbc.sql.SQL;
import pub.error.ValidationError;
import wxgh.app.session.user.UserSession;
import wxgh.data.pub.user.UserDept;
import wxgh.data.pub.user.UserInfo;
import wxgh.data.union.group.act.UserList;
import wxgh.entity.pub.DeptUser;
import wxgh.entity.pub.User;
import wxgh.entity.pub.user.ApiUser;
import wxgh.entity.pub.user.UserDepts;
import wxgh.param.union.group.act.UserParam;
import wxgh.sys.dao.pub.UserDao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/7/13.
 */
@Service
@Transactional(readOnly = true)
public class UserService {

    /**
     * 获取用户是否通知状态
     *
     * @param userid
     * @return
     */
    public Integer getSportPush(String userid) {
        String sql = new SQL.SqlBuilder()
                .table("user")
                .field("sport_push")
                .where("userid = ?")
                .build().sql();
        return pubDao.query(Integer.class, sql, userid);
    }

    @Transactional
    public void updateSportPush(String userid, Integer status) {
        if (!UserSession.getUserid().equals(userid)) {
            throw new ValidationError("你没有权限哦");
        }
        String sql = "update t_user set sport_push = ? where userid = ?";
        pubDao.execute(sql, status, userid);
    }

    public UserInfo getUserInfo(String userid) {
        SQL sql = new SQL.SqlBuilder()
                .table("user u")
                .field("u.name, u.position, u.mobile, u.gender, u.email, u.weixinid, u.avatar, u.status")
                .field("(select GROUP_CONCAT(`name` separator '-') from t_dept where FIND_IN_SET(id, u.department)) as deptname")
                .where("u.userid = ?")
                .build();
        return pubDao.query(UserInfo.class, sql.sql(), userid);
    }

    public User getUser(String userid) {
        String sql = "select * from t_user where userid = ?";
        return pubDao.query(User.class, sql, userid);
    }

    public User getUserNameInfo(String username) {
        String sql = "select * from t_user u where u.name = ?";
        return pubDao.query(User.class, sql, username);
    }

    @Transactional
    public void deleteUser(String userid) {
        String sql = "delete from t_user where userid = ?";
        pubDao.execute(sql, userid);
    }

    @Transactional
    public void updateUserStatus(String userid, com.weixin.bean.user.User.Status status) {
        String sql = "update t_user set status= ? where userid = ?";
        pubDao.execute(sql, status.getStatus(), userid);
    }

    /**
     * 更新部门
     *
     * @param deptid
     * @param userid
     */
    @Transactional
    public void updateDept(Integer deptid, String userid) {
        String sql = "update t_user u, t_dept d\n" +
                "set u.deptid = d.id, u.department = query_dept_parent(d.id)\n" +
                "where u.userid = ? and d.deptid=?";
//        String sql = "update t_user u, t_dept d\n" +
//                "set u.deptid = d.id\n" +
//                "where u.userid = ? and d.deptid=?";
        pubDao.execute(sql, userid, deptid);
    }

    @Transactional
    public void update(User user) {
        userDao.update(user, "userid");
    }

    @Transactional
    public void addOrUpdateUser(String userid) throws WeixinException {
        User user = pubDao.query(User.class, "select * from t_user where userid = ?", userid);
        com.weixin.bean.user.User wxUser = UserApi.get(userid);
        if (user != null) { //默认用户都是已经存在数据库表
            SQL.SqlBuilder sql = new SQL.SqlBuilder();
            sql.set("name = :name")
                    .set("position = :position")
//                    .set("mobile = :mobile")
                    .set("gender = :gender")
                    .set("email = :email")
                    .set("weixinid = :weixinid")
                    .set("avatar = :avatar")
                    .set("status = :status")
                    .where("userid = :userid")
                    .update("user");
            pubDao.executeBean(sql.build().sql(), wxUser);

            //更新部门
            if (!TypeUtils.empty(wxUser.getDepartment())) {
                updateDept(wxUser.getDepartment().get(0), wxUser.getUserid());
            }
        } else {
            createUser(new User(wxUser));
        }
    }

    @Transactional
    public void updateUser(User user) {
        String[] depts = user.getDepartment().split(",");

        SQL deptSql = new SQL.SqlBuilder()
                .table("user")
                .field("(select id from t_dept where deptid = ?) as newDeptid")
                .field("(select query_dept_parent(?)) as depts")
                .field("deptid as oldDeptid")
                .where("userid = ?")
                .build();
        UserDepts updateUser = pubDao.query(UserDepts.class, deptSql.sql(), depts[0], depts[0], user.getUserid());

        SQL.SqlBuilder sql = new SQL.SqlBuilder()
                .set("avatar = :avatar")
                .set("email = :email")
                .set("gender = :gender")
                .set("status = :status")
                .set("position = :position")
                .set("name = :name")
                .set("mobile = :mobile")
                .where("userid = :userid")
                .update("user");

        if (!updateUser.getNewDeptid().equals(updateUser.getOldDeptid())) {
            sql.set("deptid = " + updateUser.getNewDeptid() + ")")
                    .set("department = (select query_dept_parent(" + depts[0] + "))");

            String[] newdepts = updateUser.getDepts().split(",");
            List<DeptUser> deptUsers = new ArrayList<>();
            for (String did : newdepts) {
                if (did.equals("$")) continue;
                deptUsers.add(new DeptUser(user.getUserid(), Integer.valueOf(did)));
            }
            if (!TypeUtils.empty(deptUsers)) {
                //去重
                TypeUtils.removeRepetition(deptUsers);
                pubDao.executeBatch(JdbcSQL.save(DeptUser.class), deptUsers);
            }
        }

        pubDao.executeBean(sql.build().sql(), user);
    }

    @Transactional
    public void createUser(User user) {
        String[] depts = user.getDepartment().split(",");

        String sql = "insert into t_user (userid, name, position, mobile, gender, email, avatar, status, department, deptid)\n" +
                "select :userid, :name, :position, :mobile, :gender, :email, :avatar, :status, query_dept_parent(id), id from t_dept where deptid = " + depts[0];
        pubDao.executeBean(sql, user);

        /*
        //添加到dept_user
        String deptSql = "select query_dept_parent(deptid) as depts, deptid as newDeptid from t_dept where deptid = ?";
        UserDepts updateUser = pubDao.query(UserDepts.class, deptSql, depts[0]);
        String[] deptids = updateUser.getDepts().split(",");
        List<DeptUser> users = new ArrayList<>();
        for (String deptid : deptids) {
            DeptUser muser = new DeptUser(user.getUserid(), Integer.valueOf(deptid));
            users.add(muser);
        }
        String batchSql = "insert into t_dept_user (userid, deptid) values(:userid, :deptid)";
        pubDao.executeBatch(batchSql, users);*/
    }

    public String getUserName(String userid) {
        String sql = "select name from t_user where userid=?";
        return pubDao.query(String.class, sql, userid);
    }

    public List<Dept> deptList(Integer parentid) {
        String sql = "select d.name, d.parentid, d.deptid,\n" +
                "(select count(*)>0 from t_dept f where f.parentid = d.id) as hasChild\n" +
                "from t_dept d where d.parentid = ?";

        return pubDao.queryList(Dept.class, sql, parentid);
    }

    public List<UserList> userAllList(UserParam param) {
        String sql = "select d.name, u.id, u.deptid, u.`name` as username, u.avatar, u.mobile, u.`status`, u.position, u.userid, u.email, u.weixinid\n" +
                "from t_user u join t_dept d on u.deptid = d.deptid where !ISNULL(u.userid)";
        if (param.getPageIs()) {
            String countSql = "select count(*) from t_user where !ISNULL(userid)";
            Integer count = pubDao.query(Integer.class, countSql);
            param.setTotalCount(count == null ? 0 : count);

            sql += " limit :pagestart, :rowsPerPage";
        }
        return pubDao.queryList(sql, param, UserList.class);
    }

    public List<UserList> userList(UserParam param) {
        String sql = "select d.name, u.id, u.deptid, u.`name`, u.avatar, u.mobile, u.`status`, u.position, u.userid, u.email, u.weixinid\n" +
                "from t_user_dept ud\n" +
                "join t_user u on ud.userid = u.userid\n" +
                "join t_dept d on u.deptid = d.deptid\n" +
                "where ud.deptid = :deptid";
        if (param.getPageIs()) {
            String countSql = "select count(*) from t_user_dept d\n" +
                    "join t_user u on d.userid = u.userid\n" +
                    "where d.deptid = :deptid";
            Integer count = pubDao.query(countSql, param, Integer.class);
            param.setTotalCount(count == null ? 0 : count);
            sql += " limit :pagestart, :rowsPerPage";
        }
        return pubDao.queryList(sql, param, UserList.class);
    }

    /**
     * 获取所有已关注用户
     *
     * @return
     */
    public List<UserDept> getAllUser() {
        String sql = "select userid, department from t_user where status = ?";
        return pubDao.queryList(UserDept.class, sql, 1);
    }

    public Map<String, ApiUser> mapUser() {
        String sql = "select userid, name, mobile from t_user";
        List<ApiUser> users = pubDao.queryList(ApiUser.class, sql);
        Map<String, ApiUser> map = new HashMap<>();
        for (ApiUser user : users) {
            map.put(user.getMobile(), user);
        }
        return map;
    }

    public String getDeptId(String department,String userid){
        String sql="select substring_index(substring_index(?,',',-2),',',2) from t_user where userid=?";
        return pubDao.query(String.class,sql,department,userid);
    }

    public Integer getCount(Integer deptid){
        String sql1="select count(*) from t_user where department like '%,?,%'";
        String sql2="select count(*) from t_user where department like '?,%'";
        String sql3="select count(*) from t_user where department like '%,?'";
        String sql4="select count(*) from t_user where department=?";
        Integer count=pubDao.queryInt(sql1,deptid)+pubDao.queryInt(sql2,deptid)+pubDao.queryInt(sql3,deptid)+pubDao.queryInt(sql4,deptid);
        return count;
    }

    public Integer getTotal(){
        String sql="select count(*) from t_user";
        return pubDao.queryInt(sql);
    }

    @Autowired
    private PubDao pubDao;

    @Autowired
    private UserDao userDao;
}
