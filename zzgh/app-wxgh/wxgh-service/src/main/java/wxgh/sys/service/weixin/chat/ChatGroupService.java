package wxgh.sys.service.weixin.chat;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pub.dao.jdbc.PubDao;
import pub.dao.jdbc.sql.SQL;
import wxgh.app.sys.api.FileApi;
import wxgh.data.chat.ChatGroupInfo;
import wxgh.data.chat.ChatList;
import wxgh.data.pcadmin.pub.ChatGroupuser;
import wxgh.data.pub.user.SimpleUser;
import wxgh.entity.chat.ChatGroup;
import wxgh.entity.chat.ChatUser;
import wxgh.param.chat.group.ChatGroupParam;
import wxgh.param.union.group.user.ListParam;

import java.util.List;

/**
 * Created by Administrator on 2017/8/21.
 */
@Service
@Transactional(readOnly = true)
public class ChatGroupService {

    public static final Integer DY_CHATID = 5; //党员
    public static final String DY_GROUPID = "4c536254c49d4d2f9568a3761be4dc70"; //党员

    @Transactional
    public void delUser(Integer id) {
        String sql = "delete from t_chat_user where id = ?";
        pubDao.execute(sql, id);
    }

    public ChatUser getUserInfo(Integer id, String userid){
        String sql = new SQL.SqlBuilder()
                .table("chat_user cu")
                .field("cu.*")
                .join("chat_group g", "g.group_id = cu.group_id")
                .where("g.id = ?")
                .where("cu.userid = ?")
                .select()
                .limit("1")
                .build()
                .sql();
        return pubDao.query(ChatUser.class, sql, id, userid);
    }

    /**
     * 判断用户是否用权限
     *
     * @param id
     * @param userid
     * @return
     */
    public Integer hasPermission(Integer id, String userid) {
        SQL.SqlBuilder sql = new SQL.SqlBuilder()
                .table("chat_user cu")
                .field("cu.type")
                .join("chat_group g", "g.group_id = cu.group_id")
                .where("cu.userid = ?")
                .limit("1");
        if (DY_CHATID.equals(id)) {
            sql.where("g.type = ?");
        } else {
            sql.where("g.id = ?");
        }
        return pubDao.query(Integer.class, sql.build().sql(), userid, id);
    }

    /**
     * 获取我的支部前50个人
     *
     * @param userid
     * @param type
     * @return
     */
    public List<SimpleUser> userList50(String userid, int type) {
//        String sql = "select u.userid, tu.name as username, tu.avatar FROM t_chat_user u " +
//                "  join t_party_dept d on d.group_id = u.group_id " +
//                "  join t_user tu ON tu.userid = u.userid " +
//                "  where find_in_set(u.group_id, (select g.group_id from t_chat_user u1 join t_chat_group g on g.group_id = u1.group_id join t_party_dept d on d.group_id = g.group_id where u1.userid = ? and g.type = ? group by u1.userid))" +
//                "  limit 50";

        String sql = "select u.userid, tu.name as username, tu.avatar FROM t_party_dept_user u " +
                "  join t_party_dept d on d.id = u.party_id " +
                "  join t_chat_user cu on cu.group_id = d.group_id and u.userid = cu.userid " +
                "  join t_user tu ON tu.userid = u.userid " +
                "  where find_in_set(u.party_id , (select party_id from t_party_dept_user where userid = ? limit 1)) " +
//                "  where find_in_set(d.group_id, (select g.group_id from t_chat_user u1 join t_chat_group g on g.group_id = u1.group_id join t_party_dept d on d.group_id = g.group_id where u1.userid = ? and g.type = ? group by u1.userid))" +
                "  limit 50";

        return pubDao.queryList(SimpleUser.class, sql, userid);
    }

    public List<ChatList> listGroup(ChatGroupParam param) {
        SQL.SqlBuilder sql = new SQL.SqlBuilder()
                .table("chat_group g")
                .field("g.*")
                .sys_file("g.avatar");

        if (param.getUserid() != null) {
            sql.join("chat_user u", "g.group_id = u.group_id")
                    .where("u.userid = :userid").where("u.status = 1");
        }
        if (param.getType() != null) {
            sql.where("g.type = :type");
        }
        return pubDao.queryPage(sql, param, ChatList.class);
    }

    // 党建在线 支部listGroup
    public List<ChatList> listGroupByParty(ChatGroupParam param) {
        SQL.SqlBuilder sql = new SQL.SqlBuilder()
                .table("party_dept d")
                .join("chat_group g", "d.group_id = g.group_id")
                .field("g.*")
                .sys_file("g.avatar");

        if (param.getUserid() != null) {
            sql.join("party_dept_user u", "d.id = u.party_id")
                    .field("d.name")
//                    .join("party_dept_user du", "d.id = du.party_id")
                    .where("u.userid = :userid");
        }
        if (param.getType() != null) {
            sql.where("g.type = :type");
        }
        return pubDao.queryPage(sql, param, ChatList.class);
    }

    public List<ChatGroupuser> listUser(ListParam param) {
        SQL.SqlBuilder sql = new SQL.SqlBuilder()
                .table("chat_user cu")
                .field("cu.id, cu.status, cu.type, cu.remark")
                .simple_user("cu.userid")
                .order("cu.type");
        if (param.getStatus() != null) {
            sql.where("cu.status = :status");
        }

        // tod
        if (param.getGroupId() != null) {
            sql.join("chat_group g", "g.group_id = cu.group_id");
            if (DY_CHATID.equals(param.getGroupId())) {
                sql.where("g.type = :groupId");
            } else {
                sql.where("g.id = :groupId");
            }
        }
        if (param.getName() != null)
            sql.where("u.name like concat('%', :name , '%')");
        return pubDao.queryPage(sql, param, ChatGroupuser.class);
    }

    /**
     * 获取群组信息
     *
     * @param id
     * @return
     */
    public ChatGroupInfo groupInfo(Integer id) {
        SQL.SqlBuilder sql = new SQL.SqlBuilder()
                .table("chat_group g")
                .field("g.id, g.name, g.info, g.add_time, g.group_id")
                .sys_file("g.avatar")
                .where("g.id = ?");
        if (DY_CHATID.equals(id)) { //纪检党员群体，特殊处理
            sql.field("(select count(*) from t_chat_user where group_id in (SELECT group_id from t_chat_group where type = 5)) as userNum");
        } else {
            sql.field("(select count(*) from t_chat_user where group_id = g.group_id) as userNum");
        }

        ChatGroupInfo info = pubDao.query(ChatGroupInfo.class, sql.build().sql(), id);
        if (info != null) {
            SQL.SqlBuilder avatarSql = new SQL.SqlBuilder()
                    .table("chat_user cu")
                    .field("u.avatar")
                    .join("user u", "u.userid = cu.userid")
                    .order("cu.type")
                    .limit("4");
            List<String> avatars;
            if (DY_CHATID.equals(id)) {
                avatarSql.where("cu.group_id in (SELECT group_id from t_chat_group where type = 5)");
                avatars = pubDao.queryList(String.class, avatarSql.build().sql());

            } else {
                avatarSql.where("cu.group_id = ?");
                avatars = pubDao.queryList(String.class, avatarSql.build().sql(), info.getGroupId());
            }
            info.setAvatars(avatars);
        }
        return info;
    }

    @Transactional
    public void editGroup(ChatGroup group) {
        SQL.SqlBuilder sql = new SQL.SqlBuilder()
                .table("chat_group")
                .where("id = :id")
                .update();
        if (group.getName() != null) {
            sql.set("name = :name");
        }
        if (group.getAvatar() != null) {
            sql.set("avatar = :avatar");
        }
        if (group.getInfo() != null) {
            sql.set("info = :info");
        }
        pubDao.executeBean(sql.build().sql(), group);
    }

    @Autowired
    private PubDao pubDao;

    @Autowired
    private FileApi fileApi;

}
