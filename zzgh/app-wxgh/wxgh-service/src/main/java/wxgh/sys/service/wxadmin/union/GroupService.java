package wxgh.sys.service.wxadmin.union;

import com.weixin.Agent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pub.dao.jdbc.PubDao;
import pub.dao.jdbc.sql.SQL;
import pub.web.ServletUtils;
import wxgh.app.consts.Status;
import wxgh.app.sys.task.WeixinPush;
import wxgh.data.pub.push.ReplyPush;
import wxgh.data.union.group.GroupList;
import wxgh.data.wxadmin.group.GroupInfo;
import wxgh.entity.union.group.Group;
import wxgh.entity.union.group.GroupUser;
import wxgh.param.union.group.ListParam;

import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2017/8/7.
 */
@Service
@Transactional(readOnly = true)
public class GroupService {

    @Transactional
    public void apply(Integer id, Integer status) {
        SQL sql = new SQL.SqlBuilder()
                .set("status = ?")
                .where("id = ?")
                .update("group")
                .build();
        pubDao.execute(sql.sql(), status, id);

        //推送审核结果
        Group group = pubDao.query(Group.class, "select userid, name, group_id from t_group where id = ?", id);
        //如果为通过协会申请
        if (status.intValue() > Status.APPLY.getStatus().intValue()) {

            //将创建者添加到协会成员表
            if (Status.NORMAL.getStatus().equals(status)) {
                //判断用户是否已经添加
                String userSql = "select * from t_group_user where userid = ? and group_id = ?";
                GroupUser groupUser = pubDao.query(GroupUser.class, userSql, group.getUserid(), group.getGroupId());
                if (groupUser == null) {
                    groupUser = new GroupUser();
                    groupUser.setUserid(group.getUserid());
                    groupUser.setJoinTime(new Date());
                    groupUser.setType(GroupUser.TYPE_QZ);
                    groupUser.setStatus(Status.NORMAL.getStatus());
                    groupUser.setGroupId(group.getGroupId());
                    groupUser.setScore(0);
                    groupUser.setMoney(0f);
                    SQL addSql = new SQL.SqlBuilder()
                            .field("userid, join_time, type, status, group_id, score, money")
                            .value(":userid, :joinTime, :type, :status, :groupId, :score, :money")
                            .insert("group_user")
                            .build();
                    pubDao.executeBean(addSql.sql(), groupUser);
                } else {
                    if (!Status.NORMAL.getStatus().equals(groupUser.getStatus())) {
                        pubDao.execute("update t_group_user set status = ? where id = ?", Status.NORMAL.getStatus(), groupUser.getId());
                    }
                }
            }

            ReplyPush push = new ReplyPush(group.getUserid(), status);
            push.setHost(ServletUtils.getHostAddr());
            push.setAgentId(Agent.GROUP.getAgentId());
            push.setMsg("“" + group.getName() + "”协会审核结果");
            weixinPush.reply(push);
        }
    }

    public GroupInfo getGroup(Integer id) {
        SQL sql = new SQL.SqlBuilder()
                .table("group g")
                .field("g.id, g.name, g.info, g.add_time, g.status, g.userid")
                .field("u.name as username")
                .sys_file("g.avatar_id")
                .join("user u", "u.userid = g.userid")
                .where("g.id = ?")
                .build();
        return pubDao.query(GroupInfo.class, sql.sql(), id);
    }

    public List<GroupList> listGroup(ListParam param) {
        SQL.SqlBuilder sql = new SQL.SqlBuilder()
                .field("g.id, g.group_id, g.name, g.info")
                .sys_file("g.avatar_id")
                .table("group g");
        if (param.getStatus() != null) {
            sql.where("g.status = :status");
        }
        return pubDao.queryPage(sql, param, GroupList.class);
    }

    @Transactional
    public void deleteGroup(Integer id) {
        String delchatGroup = "delete from t_chat_group where " +
                "group_id=(select group_id from t_group where id = ?) and type = 1";
        pubDao.execute(delchatGroup, id);

        String sql = "delete from t_group where id = ?";
        pubDao.execute(sql, id);
    }

    @Autowired
    private PubDao pubDao;

    @Autowired
    private WeixinPush weixinPush;
}
