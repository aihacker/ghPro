package wxgh.sys.service.weixin.union.innovation.team;

import com.libs.common.type.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pub.dao.jdbc.PubDao;
import pub.dao.jdbc.sql.SQL;
import pub.dao.jdbc.sql.bean.Join;
import pub.dao.jdbc.sql.bean.Order;
import pub.error.ValidationError;
import wxgh.app.consts.Status;
import wxgh.data.union.group.user.ApplyCount;
import wxgh.data.union.group.user.UserData;
import wxgh.data.union.innovation.group.ChatGroupData;
import wxgh.data.union.innovation.group.ChatUserData;
import wxgh.data.union.innovation.group.MyGroupData;
import wxgh.data.union.innovation.team.UserJsonItem;
import wxgh.entity.chat.ChatGroup;
import wxgh.entity.chat.ChatUser;
import wxgh.param.union.innovation.group.GroupQuery;
import wxgh.param.union.innovation.team.TeamListParam;

import java.util.Date;
import java.util.List;

/**
 *----------------------------------------------------------
 * @Description 
 *----------------------------------------------------------
 * @Author  Ape
 *----------------------------------------------------------
 * @Email <16511660@qq.com>
 *----------------------------------------------------------
 * @Date 2017-08-11 18:02
 *----------------------------------------------------------
 */
@Service
public class ChatGroupService {

    @Autowired
    private PubDao pubDao;

    /**
     * 创建团队
     * @param uid
     * @param group
     */
    @Transactional
    public ChatGroup addGroup(String uid, ChatGroup group) {
        group.setGroupId(StringUtils.uuid());
        group.setType(ChatGroup.TYPE_TEAM);
        group.setAddTime(new Date());
        SQL sql = new SQL.SqlBuilder()
                .field("name, info, type, avatar, group_id, add_time")
                .value(":name, :info, :type, :avatar, :groupId, :addTime")
                .insert("chat_group").build();

        pubDao.executeBean(sql.sql(), group);

        // 创建人
        SQL uSql = new SQL.SqlBuilder()
                .field("userid, type, group_id, add_time, status")
                .value("?, ?, ?, ?, ?")
                .insert("chat_user")
                .build();
        // type = 1, TYPE_CREATEOR
        pubDao.execute(uSql.sql(), uid, ChatUser.TYPE_CREATEOR, group.getGroupId(), new Date(), 1);
        return group;
    }

    /**
     * 添加会员
     * @param chatUser
     */
    @Transactional
    public void addUser(ChatUser chatUser){

        SQL sSql = new SQL.SqlBuilder()
                .table("chat_user")
                .field("*")
                .where("userid = ?")
                .where("group_id = ?")
                .where("status = 1")
                .limit("1")
                .select()
                .build();

        // 检查是否存在
        ChatUser c = pubDao.query(ChatUser.class, sSql.sql(), chatUser.getUserid(), chatUser.getGroupId());
        if(c != null){
            String s = "update t_chat_user set status = 1 where userid = ? and group_id = ?";
            pubDao.execute(s, chatUser.getUserid(), chatUser.getGroupId());
            return ;
        }

        SQL sql = new SQL.SqlBuilder()
                .field("userid, type, group_id, add_time, status")
                .value(":userid, :type, :groupId, :addTime, :status")
                .insert("chat_user")
                .build();
        pubDao.executeBean(sql.sql(), chatUser);
    }

    /**
     * 获取协会成员信息
     * @param param
     * @return
     */
    public List<ChatUserData> listUser(TeamListParam param) {
        SQL.SqlBuilder sql = new SQL.SqlBuilder()
                .table("chat_user cu")
                .field("cu.id, cu.userid, cu.add_time as addTime, cu.type, cu.status")
                .field("u.name as username, u.avatar")
                .join("user u", "u.userid = cu.userid")
                .order("cu.add_time");
//        if (param.getGroupId() != null) {
            sql.join("chat_group g", "g.group_id = cu.group_id")
                    .where("g.id = :groupId");
//        }
        if (param.getStatus() != null) {
            sql.where("cu.status = :status");
        }
        if(param.getName() != null)
//            sql.where("u.name like '%"+param.getName()+"%'");
            sql.where("u.name like concat('%', :name , '%')");

        return pubDao.queryPage(sql, param, ChatUserData.class);
    }

    /**
     * 获取团队信息
     * @param id
     * @return
     */
    public MyGroupData getGroup(Integer id) {
        SQL sql = new SQL.SqlBuilder()
                .table("chat_group g")
                .field("g.id, g.name, g.info, g.add_time as addTime, g.group_id as groupId, f.file_path as avatar")
                .join("sys_file f", "f.file_id = g.avatar", Join.Type.LEFT)
                .where("g.id = ?")
                .select()
                .build();
        return pubDao.query(MyGroupData.class, sql.sql(), id);
    }

    /**
     * 获取团队用户
     * @param userid
     * @param groupId
     * @return
     */
    public ChatUserData getUser(String userid, String groupId) {
        SQL sql = new SQL.SqlBuilder()
                .field("cu.status, cu.type, cu.add_time as addTime, u.name as username, u.userid")
                .join("user u", "u.userid = cu.userid")
                .where("cu.userid = ? and cu.group_id = ?")
                .table("chat_user cu")
                .limit("1")
                .build();

        return pubDao.query(ChatUserData.class, sql.sql(), userid, groupId);
    }

    /**
     * 获取团队用户
     * @param groupQuery
     * @return
     */
    public ChatUserData getUser(GroupQuery groupQuery) {
        SQL.SqlBuilder sql = new SQL.SqlBuilder()
                .field("cu.status, cu.type, cu.add_time as addTime, u.name as username")
                .join("user u", "u.userid = cu.userid")
                .where("cu.userid = :userid and cu.group_id = :groupId")
                .table("chat_user cu")
                .limit("1");

        if(groupQuery.getStatus() != null)
            sql.where("cu.status = :status");
        if(groupQuery.getType() != null)
            sql.where("cu.type = :type");

        return pubDao.query( sql.build().sql(), groupQuery, ChatUserData.class);
    }

    /**
     * 统计团队 待审核人数, 审核中人数
     * @param groupId
     * @return
     */
    public ApplyCount getCount(Integer groupId) {
        String sql = "select (SELECT count(*) from t_chat_user u join t_chat_group g on g.group_id = u.group_id where g.id = ? and u.`status` = 0) as apply,\n" +
                "(SELECT count(*) from t_chat_user u join t_chat_group g on g.group_id = u.group_id where g.id = ? and u.`status` = 2) as no";
        return pubDao.query(ApplyCount.class, sql, groupId, groupId);
    }

    /**
     * 统计成员
     * @param id
     * @return
     */
    public Integer getUserCount(Integer id){
        SQL.SqlBuilder sql = new SQL.SqlBuilder()
                .table("chat_user cu")
                .join("user u", "u.userid = cu.userid")
                .join("chat_group g","g.group_id = cu.group_id")
                .where("g.id = ?")
                .where("cu.status = 1")
                .count();
        return pubDao.queryInt(sql.build().sql(), id);
    }

    /**
     * 获取团队列表
     * @param page
     * @return
     */
    public List<ChatGroupData> getList(GroupQuery page){
        SQL.SqlBuilder sql = new SQL.SqlBuilder()
                .field("g.*, f.file_path as avatar")
                .table("chat_group g")
                .join("sys_file f", "f.file_id = g.avatar", Join.Type.LEFT)
                .order("g.add_time", Order.Type.DESC)
                .select();
        if(page.getType() != null)
            sql.where("g.type = :type");
        return pubDao.queryPage(sql, page, ChatGroupData.class);
    }

    /**
     * 获取我的团队
     * @param groupQuery
     * @return
     */
    public List<ChatGroupData> getMyGroups(GroupQuery groupQuery) {
        SQL.SqlBuilder sql = new SQL.SqlBuilder()
                .field("g.*, f.file_path as avatar")
                .table("chat_group g")
                .join("chat_user m", "g.group_id = m.group_id")
                .join("sys_file f ", "f.file_id = g.avatar", Join.Type.LEFT)
                .where("m.status = 1")
                .groupBy("g.id");

        if(groupQuery.getUserid() != null)
            sql.where("m.userid = :userid");
        if(groupQuery.getType() != null)
            sql.where("g.type = :type");
        return pubDao.queryList(sql.build().sql(), groupQuery, ChatGroupData.class);
    }

    /**
     * 更新会员状态, 审核是否通过
     * @param id
     * @param status
     */
    @Transactional
    public void updateStatus(Integer id, Integer status) {
        String sql = "update t_chat_user set status = ? where id = ?";
        pubDao.execute(sql, status, id);
    }

    /**
     * 验证权限
     * @param userid
     * @param id
     */
    public void verify_permisstion(String userid, Integer id) {
        SQL sql = new SQL.SqlBuilder()
                .field("u.*")
                .join("chat_group g", "g.group_id = u.group_id")
                .join("chat_user vu", "vu.group_id = g.group_id")
                .where("u.userid = ?")
                .where("vu.id = ?")
                .limit("1")
                .select("chat_user u").build();
        ChatUser chatUser = pubDao.query(ChatUser.class, sql.sql(), userid, id);
        if (chatUser == null)
            throw new ValidationError("对不起，你没有权限");
        if(chatUser.getType() != ChatUser.TYPE_MANAGER && chatUser.getType() != ChatUser.TYPE_CREATEOR)
            throw new ValidationError("对不起，你没有权限");
    }

    /**
     * 批量添加用户
     * @param users
     * @param id
     */
//    @Transactional
//    public void addUsers(List<AddItem> users, Integer id) {
//        String sql = "select group_id from t_chat_group where id=?";
//        String groupId = pubDao.query(String.class, sql, id);
//
//        for (AddItem item : users) {
//            ChatUser user = new ChatUser();
//            user.setUserid(item.getId());
//            user.setAddTime(new Date());
//            user.setType(ChatUser.TYPE_MEMBER);
//            user.setStatus(1);
//            user.setGroupId(groupId);
//            addUser(user);
//        }
//    }

    /**
     * 批量添加用户
     * @param users
     * @param id
     */
    @Transactional
    public void addUsers(List<UserJsonItem> users, Integer id) {
        String sql = "select group_id from t_chat_group where id=?";
        String groupId = pubDao.query(String.class, sql, id);

        for (UserJsonItem item : users) {
            ChatUser user = new ChatUser();
            user.setUserid(item.getId());
            user.setAddTime(new Date());
            user.setType(ChatUser.TYPE_MEMBER);
            user.setStatus(1);
            user.setGroupId(groupId);
            addUser(user);
        }
    }

    /**
     * 检查是否是团队成员
     * @param id
     * @param userid
     * @return
     */
    public boolean checkInGroup(Integer id, String userid){
        SQL sql = new SQL.SqlBuilder()
                .field("u.type, u.group_id, u.status")
                .join("chat_group g", "g.group_id = u.group_id")
                .where("u.userid = ? and g.id = ?")
                .select("chat_user u").build();
        ChatUser user = pubDao.query(ChatUser.class, sql.sql(), userid, id);
        if(user != null && user.getStatus() == Status.NORMAL.getStatus())
            return true;
        return false;
    }

    /**
     * 申请加入
     *
     * @param id
     * @param userid
     */
    @Transactional
    public void apply(Integer id, String userid) {
        SQL sql = new SQL.SqlBuilder()
                .field("u.type, u.group_id, u.status")
                .join("chat_group g", "g.group_id = u.group_id")
                .where("u.userid = ? and g.id = ?")
                .select("chat_user u").build();
        ChatUser user = pubDao.query(ChatUser.class, sql.sql(), userid, id);
        if (user != null) {
            if (user.getStatus() == Status.NORMAL.getStatus()) {
                throw new ValidationError("你已经是协会成员");
            } else if (user.getStatus() == Status.APPLY.getStatus()) {
                throw new ValidationError("请不要重复提交申请");
            } else { //更新状态为申请状态
                String upSql = "update t_chat_user set status = ? where userid = ? and group_id = ?";
                pubDao.execute(upSql, Status.APPLY.getStatus(), userid, user.getGroupId());
            }
        } else {
            String addSql = "insert into t_chat_user(userid, add_time, type, status, group_id)\n" +
                    "select :userid, :addTime, :type, :status, group_id from t_chat_group where id = :id";

            ChatUser addUser = new ChatUser();
            addUser.setAddTime(new Date());
            addUser.setType(ChatUser.TYPE_MEMBER);
            addUser.setStatus(Status.APPLY.getStatus());
            addUser.setId(id);
            addUser.setUserid(userid);

            pubDao.executeBean(addSql, addUser);
        }
    }

    /**
     * 退出或解散
     * @param id
     * @param userid
     * @return
     */
    @Transactional
    public String out(Integer id, String userid) {
        String sql = "select u.type, u.group_id from t_chat_user u\n" +
                "join t_chat_group g on u.group_id = g.group_id\n" +
                "where u.userid = ? and g.id = ?";
        ChatUser user = pubDao.query(ChatUser.class, sql, userid, id);
        if (user == null || user.getType() == null) {
            return "您不是协会成员哦";
        }
        if (user.getType() == ChatUser.TYPE_CREATEOR) {
            sql = "delete from t_chat_group where id = ?";
            pubDao.execute(sql, id);
            return "解散成功";
        } else {
            sql = "delete from t_chat_user where userid = ? and group_id = ?";
            pubDao.execute(sql, userid, user.getGroupId());
            return "退出成功";
        }
    }

    @Transactional
    public void updateChatGroup(ChatGroup chatGroup, String uid){
        // 验证权限
        SQL sql = new SQL.SqlBuilder()
                .table("chat_user")
                .field("group_id")
                .where("userid = ? and id = ?")
                .select()
                .build();
        String gId = pubDao.query(String.class, sql.sql(), uid, chatGroup.getId());
        if(gId == null)
            new ValidationError("没有权限");

        SQL.SqlBuilder updateSql = new SQL.SqlBuilder();
        if(chatGroup.getAvatar() != null)
            updateSql.set("avatar = :avatar");
        if(chatGroup.getName() != null)
            updateSql.set("name = :name");
        if(chatGroup.getInfo() != null)
            updateSql.set("info = :info");
        if(chatGroup.getId() != null)
            updateSql.where("id = :id");
        if(chatGroup.getGroupId() != null)
            updateSql.where("group_id = :groupId");
        pubDao.executeBean(updateSql.update("chat_group").build().sql(), chatGroup);
    }

    /**
     * 获取团队的人
     * @param groupId
     * @return
     */
    public List<UserData> getChatUsers(Integer groupId){
        SQL.SqlBuilder sql = new SQL.SqlBuilder()
                .field("u.name as username, u.avatar, cu.add_time as joinTime, cu.status, cu.type, u.userid")
                .table("chat_user cu")
                .join("chat_group cg", "cg.group_id = cu.group_id")
                .join("user u", "u.userid = cu.userid")
                .where("cg.id = ?");
        return pubDao.queryList(UserData.class, sql.build().sql(), groupId);
    }

}

