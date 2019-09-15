package wxgh.sys.service.weixin.test;

import com.libs.common.type.TypeUtils;
import com.weixin.bean.dept.Dept;
import com.weixin.bean.tag.Tag;
import com.weixin.bean.user.SimpleUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pub.dao.jdbc.PubDao;
import pub.dao.jdbc.sql.JdbcSQL;
import pub.dao.jdbc.sql.SQL;
import wxgh.entity.chat.ChatGroup;
import wxgh.entity.chat.ChatUser;
import wxgh.entity.pub.DeptUser;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/8/1.
 */
@Service
@Transactional(readOnly = true)
public class DeptService {

    @Transactional
    public void addTag(List<Tag> tags) {
        String sql = "insert into t_wx_tag(tagid, tagname) values(:tagid, :tagname)";
        pubDao.executeBatch(sql, tags);
    }

    @Transactional
    public void addDept(Dept dept) {
//        String sql = "insert into t_dept (deptid, name, parentid, `order`)\n"
//                + "select :id, :name, id, :order from t_dept where deptid = :parentid";
        String sql = "insert into t_dept (deptid, name, parentid, `order`) " +
                "values(:id, :name, :parentid, :order)";
        pubDao.executeBean(sql, dept);

    }

    @Transactional
    public void addUser(SimpleUser user) {

        String sql = "insert into t_user (userid, name, department, deptid)\n" +
                "select :userid, :name, query_dept_parent(id), id from t_dept where deptid = " + user.getDepartment().get(0);
        pubDao.executeBean(sql, user);

        String deptSql = "select query_dept_parent((select id from t_dept where deptid = ?))";
        String depts = pubDao.query(String.class, deptSql, user.getDepartment().get(0));

        if (!TypeUtils.empty(depts)) {
            String[] newdepts = depts.split(",");
            List<DeptUser> deptUsers = new ArrayList<>();
            for (String did : newdepts) {
                deptUsers.add(new DeptUser(user.getUserid(), Integer.valueOf(did)));
            }
            if (!TypeUtils.empty(deptUsers)) {
                //去重
                TypeUtils.removeRepetition(deptUsers);
                pubDao.executeBatch(JdbcSQL.save(DeptUser.class), deptUsers);
            }
        } else {
            System.out.println("-----------------------------------------");
            System.err.println("发生错误了：........................");
        }
    }

    @Transactional
    public void next(){
        String sql = "SELECT d.id,d.name,d.deptid,t.id AS parentid from t_dept d LEFT JOIN t_dept t ON d.parentid = t.deptid WHERE d.parentid !=0";
        List<Dept> list = pubDao.queryList(Dept.class,sql);
        String updateSQL = null;
        for (Dept dept: list) {
            updateSQL =  "update t_dept set parentid =" + dept.getParentid() + " where id = " + dept.getId();
            pubDao.execute(updateSQL);
        }
    }

    public String getUserid(String name) {
        String sql = "select group_concat(userid) from t_user where name = ?";
        return pubDao.query(String.class, sql, name);
    }

    @Transactional
    public void addGroups(List<ChatGroup> groups) {
        SQL sql = new SQL.SqlBuilder()
                .field("group_id, name, type")
                .value(":groupId, :name, :type")
                .insert("chat_group")
                .build();
        pubDao.executeBatch(sql.sql(), groups);
    }

    @Transactional
    public void addChatUsers(List<ChatUser> users) {
        SQL sql = new SQL.SqlBuilder()
                .field("userid, group_id, add_time")
                .value(":userid, :groupId, :addTime")
                .insert("chat_user")
                .build();
        pubDao.executeBatch(sql.sql(), users);
    }

    @Autowired
    private PubDao pubDao;

    @Transactional
    public void updateMobile(String userid, String mobile) {
        String sql = "update t_user set mobile = '"+ mobile +"' where userid = '" + userid + "'";
        pubDao.execute(sql);
    }
}
