package wxgh.sys.service.weixin.canteen;

import com.libs.common.type.TypeUtils;
import com.weixin.bean.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pub.dao.jdbc.PubDao;
import pub.dao.jdbc.sql.SQL;
import pub.dao.jdbc.sql.bean.Order;
import pub.error.ValidationError;
import wxgh.app.consts.Status;
import wxgh.app.consts.WeixinAgent;
import wxgh.app.session.user.UserSession;
import wxgh.app.sys.task.WeixinPush;
import wxgh.data.pub.push.ApplyPush;
import wxgh.data.union.group.add.AddItem;
import wxgh.data.union.group.user.ApplyCount;
import wxgh.data.union.group.user.UserData;
import wxgh.entity.canteen.CanteenUser;
import wxgh.entity.union.group.GroupUser;
import wxgh.param.union.group.user.ListParam;

import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2017/7/20.
 */
@Service
@Transactional(readOnly = true)
public class CanteenUserService {

    public Integer userType(String userid, Integer id) {
        SQL sql = new SQL.SqlBuilder()
                .field("u.type")
                .join("t_canteen g", "g.group_id = u.group_id")
                .where("u.userid = ?")
                .where("g.id = ?")
                .select("canteen_user u").build();
        return pubDao.query(Integer.class, sql.sql(), userid, id);
    }

    public void verify_permisstion(String userid, Integer id) {
        SQL sql = new SQL.SqlBuilder()
                .field("u.*")
                .join("t_canteen g", "g.group_id = u.group_id")
                .where("u.userid = ?")
                .where("g.id = ?")
                .select("canteen_user u").build();
        CanteenUser groupUser = pubDao.query(CanteenUser.class, sql.sql(), userid, id);
        if (groupUser == null || (groupUser != null && groupUser.getType().compareTo(CanteenUser.TYPE_GL) > 0)) {
            throw new ValidationError("对不起，你没有权限");
        }
    }

    public ApplyCount getCount(Integer groupId) {
        String sql = "select (SELECT count(*) from t_canteen_user u join t_group g on g.group_id = u.group_id where g.id = ? and u.`status` = 0) as apply,\n" +
                "(SELECT count(*) from t_canteen_user u join t_canteen g on g.group_id = u.group_id where g.id = ? and u.`status` = 2) as no";
        return pubDao.query(ApplyCount.class, sql, groupId, groupId);
    }

    @Transactional
    public void deleteUser(Integer id) {
        String sql = "delete from t_canteen_user where id = ?";
        pubDao.execute(sql, id);
    }

    public List<UserData> listUser(ListParam param) {
        SQL.SqlBuilder sql = new SQL.SqlBuilder()
                .table("canteen_user gu")
                .field("gu.id, gu.userid, gu.join_time, gu.status, gu.type")
                .field("u.name as username, u.avatar")
                .join("t_user u", "u.userid = gu.userid")
                .order("gu.type")
                .order("gu.join_time");
        if (param.getGroupId() != null) {
            sql.join("t_canteen g", "g.group_id = gu.group_id")
                    .where("g.id = :groupId");
        }
        if (param.getStatus() != null) {
            sql.where("gu.status = :status");
        }
        if (param.getName() != null)
            sql.where("u.name like '%" + param.getName() + "%'");

        return pubDao.queryPage(sql, param, UserData.class);
    }

    public CanteenUser getUser(String userid, String groupId) {
        SQL sql = new SQL.SqlBuilder()
                .where("userid = ? and group_id = ?")
                .select("canteen_user")
                .limit("1")
                .build();
        return pubDao.query(CanteenUser.class, sql.sql(), userid, groupId);
    }

    /**
     * 获取指定协会内用户的头像
     *
     * @param groupId
     * @param numb
     * @return
     */
    public List<String> getGroupAvatar(String groupId, Integer numb) {
        SQL sql = new SQL.SqlBuilder()
                .field("u.avatar")
                .join("t_user u", "u.userid = gu.userid")
                .order("gu.type", Order.Type.ASC)
                .where("gu.group_id = ? and gu.status = ?")
                .limit("0, ?")
                .select("t_canteen_user gu")
                .build();
        return pubDao.queryList(String.class, sql.sql(), groupId, Status.NORMAL.getStatus(), numb);
    }

    @Transactional
    public void addOrUpdateUser(CanteenUser user) {
        String sql = "select * from t_canteen_user where userid=? and group_id=?";
        CanteenUser groupUser = pubDao.query(CanteenUser.class, sql, user.getUserid(), user.getGroupId());
        if (groupUser != null) {
            if (groupUser.getStatus() != 1) { //如果用户存在，且不再协会内，则更新用户在协会的状态
                sql = "update t_canteen_user set status = ? where id = ?";
                pubDao.execute(sql, user.getStatus(), groupUser.getId());
            }
        } else { //用户不存在，则直接添加
            sql = "insert into t_canteen_user(userid, join_time, type, status, group_id) values(:userid, :joinTime, :type, :status, :groupId)";
            pubDao.executeBean(sql, user);
        }
    }

    @Transactional
    public void addUsers(List<AddItem> users, Integer id) {
        if (TypeUtils.empty(users)) {
            throw new ValidationError("请选择用户");
        }
        String sql = "select group_id from t_canteen where id=?";
        String groupId = pubDao.query(String.class, sql, id);

        for (AddItem item : users) {
            CanteenUser user = new CanteenUser();
            user.setUserid(item.getId());
            user.setJoinTime(new Date());
            user.setType(CanteenUser.TYPE_HY);
            user.setStatus(1);
            user.setGroupId(groupId);

            addOrUpdateUser(user);
        }
    }

    /**
     * 申请加入协会
     *
     * @param id
     * @param userid
     */
    @Transactional
    public void apply(Integer id, String userid) {
        SQL sql = new SQL.SqlBuilder()
                .field("u.type, u.group_id, u.status")
                .join("t_canteen g", "g.group_id = u.group_id")
                .where("u.userid = ? and g.id = ?")
                .select("canteen_user u").build();
        CanteenUser user = pubDao.query(CanteenUser.class, sql.sql(), userid, id);
        if (user != null) {
            if (user.getStatus() == Status.NORMAL.getStatus()) {
                throw new ValidationError("你已经是该饭堂的成员");
            } else if (user.getStatus() == Status.APPLY.getStatus()) {
                throw new ValidationError("请不要重复提交申请");
            } else { //更新状态为申请状态
                String upSql = "update t_canteen_user set status = ? where userid = ? and group_id = ?";
                pubDao.execute(upSql, Status.APPLY.getStatus(), userid, user.getGroupId());
            }
        } else {
            String addSql = "insert into t_canteen_user(userid, join_time, type, status, group_id)\n" +
                    "select :userid, :joinTime, :type, :status, group_id from t_canteen where id = :id";

            String findSql = "select name from t_user where userid = ?";
            User user1 = pubDao.query(User.class,findSql,userid);

            CanteenUser addUser = new CanteenUser();
            addUser.setJoinTime(new Date());
            addUser.setType(CanteenUser.TYPE_HY);
            addUser.setStatus(Status.APPLY.getStatus());
            addUser.setId(id);
            addUser.setUserid(userid);

            List<String> users = listUserAdmin(id);

            /**
             * 以下推送消息给管理员，待实现
             */
            ApplyPush push = new ApplyPush(ApplyPush.Type.CANTEEN, UserSession.getUserid(), id.toString());
            push.setAgentId(WeixinAgent.AGENT_CANTEEN);
            push.setMsg("“" +user1.getName()+ "”的饭堂申请");
            push.setToUsers(users);
            weixinPush.apply_to(push);

            pubDao.executeBean(addSql, addUser);
        }
    }

    /*
    获取管理员账号*
    */
    public List<String> listUserAdmin(Integer id){
        SQL.SqlBuilder sql = new SQL.SqlBuilder()
                .table("canteen_user gu")
                .field("gu.userid")
                .join("canteen g","g.group_id = gu.group_id")
                .where("g.id = ? and gu.type != 4");
        return pubDao.queryList(String.class,sql.build().sql(),id);
    }

    @Transactional
    public void updateStatus(Integer id, Integer status) {
        String sql = "update t_canteen_user set status = ? where id = ?";
        pubDao.execute(sql, status, id);
    }

    @Autowired
    private PubDao pubDao;

    @Autowired
    private WeixinPush weixinPush;
}
