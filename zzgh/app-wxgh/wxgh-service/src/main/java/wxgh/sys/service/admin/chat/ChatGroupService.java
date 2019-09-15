package wxgh.sys.service.admin.chat;

import com.libs.common.type.StringUtils;
import com.libs.common.type.TypeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pub.dao.jdbc.PubDao;
import pub.dao.jdbc.sql.SQL;
import pub.error.ValidationError;
import wxgh.app.consts.Status;
import wxgh.data.pcadmin.pub.ChatGroupList;
import wxgh.data.pcadmin.pub.ChatGroupuser;
import wxgh.entity.chat.ChatGroup;
import wxgh.entity.chat.ChatUser;
import wxgh.param.admin.pub.ChatGroupParam;
import wxgh.param.admin.pub.ChatGroupuserParam;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2017/8/18.
 */
@Service
@Transactional(readOnly = true)
public class ChatGroupService {

    @Transactional
    public void delete(String id) {
        if (TypeUtils.empty(id)) {
            throw new ValidationError("请选择需要删除的聊天群组");
        }
        pubDao.execute(SQL.deleteByIds("chat_group", id));

        String[] ids = id.split(",");
        String sql = "delete from t_chat_user where group_id = ?";

        pubDao.batch(sql, ids);
    }

    public List<ChatGroupList> listGroup(ChatGroupParam param) {
        SQL.SqlBuilder sql = new SQL.SqlBuilder()
                .table("chat_group g")
                .field("g.*")
                .sys_file("g.avatar");
        if (param.getType() != null) {
            sql.where("g.type = :type");
        }
        return pubDao.queryPage(sql, param, ChatGroupList.class);
    }

    public List<ChatGroupuser> listUser(ChatGroupuserParam param) {
        SQL.SqlBuilder sql = new SQL.SqlBuilder()
                .table("chat_user cu")
                .field("cu.id, cu.status, cu.type, cu.remark")
                .simple_user("cu.userid")
                .order("cu.type");
        if (param.getStatus() != null) {
            sql.where("cu.status = :status");
        }
        if (param.getType() != null) {
            sql.where("cu.type = :type");
        }
        if (param.getGroupId() != null) {
            sql.join("chat_group g", "cu.group_id = g.group_id")
                    .where("g.id = :groupId");
        }
        return pubDao.queryPage(sql, param, ChatGroupuser.class);
    }

    @Transactional
    public void addOrUpdateGroup(ChatGroup group) {
        SQL.SqlBuilder sql = new SQL.SqlBuilder()
                .table("chat_group");
        if (group.getId() != null) {
            if (group.getName() != null) {
                sql.set("name = :name");
            }
            if (group.getAvatar() != null) {
                sql.set("avatar = :avatar");
            }
            if (group.getInfo() != null) {
                sql.set("info = :info");
            }
            sql.where("id = :id")
                    .update();
        } else {
            group.setGroupId(StringUtils.uuid());
            group.setAddTime(new Date());
            sql.field("group_id, name, avatar, info, type, add_time")
                    .value(":groupId, :name, :avatar, :info, :type, :addTime")
                    .insert();
        }
        pubDao.executeBean(sql.build().sql(), group);
    }

    @Transactional
    public void deleteUser(String id) {
        if (TypeUtils.empty(id)) {
            throw new ValidationError("请选择需要删除的用户");
        }
        pubDao.execute(SQL.deleteByIds("chat_user", id));
    }

    @Transactional
    public void addOrUpdateUser(ChatUser user) {
        SQL.SqlBuilder sql = new SQL.SqlBuilder()
                .table("chat_user");
        if (user.getId() != null) {
            if (user.getType() != null) {
                sql.set("type = :type");
            }
            if (user.getStatus() != null) {
                sql.set("status = :status");
            }
            if (user.getRemark() != null) {
                sql.set("remark = :remark");
            }
            sql.where("id = :id")
                    .update();

            pubDao.executeBean(sql.build().sql(), user);
        } else {

            if (TypeUtils.empty(user.getUserid())) {
                throw new ValidationError("请选择用户！");
            }
            String[] userids = user.getUserid().split(",");
            List<ChatUser> users = new ArrayList<>();
            Integer status = user.getStatus() == null ? Status.NORMAL.getStatus() : user.getStatus();
            for (String userid : userids) {
                ChatUser chatUser = new ChatUser();
                chatUser.setGroupId(user.getGroupId());
                chatUser.setAddTime(new Date());
                chatUser.setUserid(userid);
                chatUser.setStatus(status);
                chatUser.setRemark(user.getRemark());
                chatUser.setType(ChatUser.TYPE_USER);

                users.add(chatUser);
            }

            sql.field("userid, type, group_id, add_time, status, remark")
                    .value(":userid, :type, :groupId, :addTime, :status, :remark")
                    .insert();

            pubDao.executeBatch(sql.build().sql(), users);
        }

    }

    @Autowired
    private PubDao pubDao;
}
