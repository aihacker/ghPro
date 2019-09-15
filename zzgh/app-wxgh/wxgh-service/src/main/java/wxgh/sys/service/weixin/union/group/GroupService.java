package wxgh.sys.service.weixin.union.group;

import com.libs.common.type.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pub.dao.jdbc.PubDao;
import pub.dao.jdbc.sql.SQL;
import pub.dao.jdbc.sql.bean.Join;
import pub.error.ValidationError;
import pub.spring.web.mvc.ActionResult;
import wxgh.app.consts.Status;
import wxgh.app.session.user.UserSession;
import wxgh.data.union.group.GroupList;
import wxgh.data.union.group.GroupShow;
import wxgh.data.union.group.SearchList;
import wxgh.entity.union.group.Group;
import wxgh.entity.union.group.GroupUser;
import wxgh.param.union.group.ListParam;
import wxgh.param.union.group.SearchParam;

import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2017/7/20.
 */
@Service
@Transactional(readOnly = true)
public class GroupService {

    @Transactional
    public Integer addGroup(Group group) {
        group.setAddTime(new Date());
        group.setGroupId(StringUtils.uuid());
        group.setUserid(UserSession.getUserid());
        group.setStatus(Status.APPLY.getStatus());
        SQL sql = new SQL.SqlBuilder()
                .field("name, info, add_time, status, userid, avatar_id, group_id")
                .value(":name, :info, :addTime, :status, :userid, :avatarId, :groupId")
                .insert("group").build();
        return pubDao.insertAndGetKey(sql.sql(), group);
    }

    public List<SearchList> getSearchs(SearchParam param) {
        SQL.SqlBuilder sql = new SQL.SqlBuilder()
                .table("group g")
                .field("g.name, g.info, g.id")
                .sys_file("g.avatar_id");
        if (param.getStatus() != null) {
            sql.where("g.status = :status");
        }
        if (param.getKey() != null) {
            sql.where("g.name like  CONCAT('%', :key , '%')");
        }
        if (param.getUserid() != null) {
            sql.field("(select IFNULL(`status`,3) from t_group_user where userid = :userid and group_id = g.group_id limit 1) as status");
        }
        return pubDao.queryPage(sql, param, SearchList.class);
    }

    /**
     * 获取协会基本信息
     *
     * @param id
     * @return
     */
    public GroupShow getInfo(Integer id, String userid) {
        SQL sql = new SQL.SqlBuilder()
                .table("t_group g")
                .field("g.*")
                .field("f.file_path as path, f.thumb_path as thumb")
                .field("u.name as username")
                .field("(select count(*) from t_group_user where group_id = g.group_id and `status` = 1) as userNumb")
                .field("(select type from t_group_user where group_id = g.group_id and userid = ? and status = 1) as userType")
                .field("(select count(*) from t_group_user where group_id = g.group_id and `status` = 0) as applyCount")
                .join("t_user u", "g.userid = u.userid")
                .join("t_sys_file f", "f.file_id = g.avatar_id", Join.Type.LEFT)
                .where("g.id = ?")
                .select()
                .build();
        return pubDao.query(GroupShow.class, sql.sql(), userid, id);
    }

    /**
     * 获取指定用户协会
     *
     * @param param
     * @return
     */
    public List<GroupList> listMeGroup(ListParam param) {
        SQL.SqlBuilder sql = new SQL.SqlBuilder()
                .table("t_group_user u")
                .field("g.id, g.group_id, g.name, g.info")
                .field("f.file_path as path, f.thumb_path as thumb")
                .join("t_group g", "g.group_id = u.group_id")
                .join("t_sys_file f", "f.file_id = g.avatar_id", Join.Type.LEFT)
                .where("g.status = 1");
        if (param.getStatus() != null) {
            sql.where("u.status = :status");
        }
        if (param.getUserid() != null) {
            sql.where("u.userid = :userid");
        }
        return pubDao.queryPage(sql, param, GroupList.class);
    }

    /**
     * 解散协会
     */
    @Transactional
    public ActionResult dissolve(Integer id, String userid) {
        String sql = "select u.type, u.group_id from t_group_user u\n" +
                "join t_group g on u.group_id = g.group_id\n" +
                "where u.userid = ? and g.id = ?";
        GroupUser user = pubDao.query(GroupUser.class, sql, userid, id);
        if (user == null || user.getType() == null) {
            new ValidationError("您不是协会成员哦");
        }
        if (user.getType() == GroupUser.TYPE_QZ) {
            sql = "delete from t_group where id = ?";
            pubDao.execute(sql, id);
            return ActionResult.okWithData("解散成功");
        } else {
            sql = "delete from t_group_user where userid = ? and group_id = ?";
            pubDao.execute(sql, userid, user.getGroupId());
            return ActionResult.okWithData("退出成功");
        }
    }

    /**
     * 协会信息更新
     *
     * @param group
     * @param userid
     */
    @Transactional
    public void updateGroup(Group group, String userid) {
        //判断用户是否有权限进行协会信息的更新
        Integer type;
        if (group.getGroupId() != null) {
            String querySql = "select type from t_group_user where userid = ? and group_id = ?";
            type = pubDao.query(Integer.class, querySql, userid, group.getGroupId());
        } else {
            String querySql = "select u.type from t_group_user u join t_group g on g.group_id = u.group_id where u.userid = ? and g.id = ?";
            type = pubDao.query(Integer.class, querySql, userid, group.getId());
        }

        if (type == null || type.compareTo(GroupUser.TYPE_GL) > 0) {
            throw new ValidationError("对不起，你没有修改权限哦！");
        }

        SQL.SqlBuilder sql = new SQL.SqlBuilder();
        if (group.getAvatarId() != null) {
            sql.set("avatar_id = :avatarId");
        }
        if (group.getName() != null) {
            sql.set("name = :name");
        }
        if (group.getInfo() != null) {
            sql.set("info = :info");
        }
        if (group.getAddTime() != null) {
            sql.set("add_time = :addTime");
        }
        if (group.getUserid() != null) {
            sql.set("userid = :userid");
        }

        if (group.getId() != null) {
            sql.where("id = :id");
        } else if (group.getGroupId() != null) {
            sql.where("group_id = :groupId");
        }

        sql.update("group");

        pubDao.executeBean(sql.build().sql(), group);
    }

    @Autowired
    private PubDao pubDao;
}
