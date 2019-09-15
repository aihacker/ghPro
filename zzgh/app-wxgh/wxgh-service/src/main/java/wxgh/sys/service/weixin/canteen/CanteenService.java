package wxgh.sys.service.weixin.canteen;

import com.libs.common.type.StringUtils;
import com.weixin.Agent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pub.dao.jdbc.PubDao;
import pub.dao.jdbc.sql.SQL;
import pub.dao.jdbc.sql.bean.Join;
import pub.error.ValidationError;
import pub.spring.web.mvc.ActionResult;
import pub.web.ServletUtils;
import wxgh.app.consts.Status;
import wxgh.app.session.user.UserSession;
import wxgh.data.pub.push.ReplyPush;
import wxgh.data.union.group.GroupList;
import wxgh.data.union.group.GroupShow;
import wxgh.data.union.group.SearchList;
import wxgh.entity.canteen.Canteen;
import wxgh.entity.canteen.CanteenShow;
import wxgh.entity.canteen.CanteenUser;
import wxgh.entity.union.group.Group;
import wxgh.entity.union.group.GroupUser;
import wxgh.param.union.group.ListParam;
import wxgh.param.union.group.SearchParam;

import java.util.Date;
import java.util.List;

@Service
@Transactional(readOnly = true)
public class CanteenService {

    @Autowired
    private PubDao pubDao;

    @Transactional
    public Integer addGroup(Canteen canteen) {
        canteen.setAddTime(new Date());
        canteen.setGroupId(StringUtils.uuid());
        canteen.setUserid(UserSession.getUserid());
        canteen.setStatus(1);


            //将创建者添加到协会成员表
            if (Status.NORMAL.getStatus().equals(canteen.getStatus())) {
                //判断用户是否已经添加
                String userSql = "select * from t_canteen_user where userid = ? and group_id = ?";
                CanteenUser canteenUser = pubDao.query(CanteenUser.class, userSql, canteen.getUserid(), canteen.getGroupId());
                if (canteenUser == null) {
                    canteenUser = new CanteenUser();
                    canteenUser.setUserid(canteen.getUserid());
                    canteenUser.setJoinTime(new Date());
                    canteenUser.setType(GroupUser.TYPE_QZ);
                    canteenUser.setStatus(Status.NORMAL.getStatus());
                    canteenUser.setGroupId(canteen.getGroupId());
                    canteenUser.setScore(0);
                    canteenUser.setMoney(0f);
                    SQL addSql = new SQL.SqlBuilder()
                            .field("userid, join_time, type, status, group_id, score, money")
                            .value(":userid, :joinTime, :type, :status, :groupId, :score, :money")
                            .insert("canteen_user")
                            .build();
                    pubDao.executeBean(addSql.sql(), canteenUser);
                } else {
                    if (!Status.NORMAL.getStatus().equals(canteenUser.getStatus())) {
                        pubDao.execute("update t_canteen_user set status = ? where id = ?", Status.NORMAL.getStatus(), canteenUser.getId());
                    }
                }
            }   
        SQL sql = new SQL.SqlBuilder()
                .field("name, info, add_time, status, userid, avatar_id, group_id")
                .value(":name, :info, :addTime, :status, :userid, :avatarId, :groupId")
                .insert("canteen").build();
        return pubDao.insertAndGetKey(sql.sql(), canteen);
    }

    public List<SearchList> getSearchs(SearchParam param) {
        SQL.SqlBuilder sql = new SQL.SqlBuilder()
                .table("canteen g")
                .field("g.name, g.info, g.id")
                .sys_file("g.avatar_id");
        if (param.getStatus() != null) {
            sql.where("g.status = :status");
        }
        if (param.getKey() != null) {
            sql.where("g.name like  CONCAT('%', :key , '%')");
        }
        if (param.getUserid() != null) {
            sql.field("(select IFNULL(`status`,3) from t_canteen_user where userid = :userid and group_id = g.group_id limit 1) as status");
        }
        return pubDao.queryPage(sql, param, SearchList.class);
    }

    /**
     * 获取饭堂基本信息
     *
     * @param id
     * @return
     */
    public CanteenShow getInfo(Integer id, String userid) {
        SQL sql = new SQL.SqlBuilder()
                .table("t_canteen g")
                .field("g.*")
                .field("f.file_path as path, f.thumb_path as thumb")
                .field("u.name as username")
                .field("(select count(*) from t_canteen_user where group_id = g.group_id and `status` = 1) as userNumb")
                .field("(select type from t_canteen_user where group_id = g.group_id and userid = ? and status = 1) as userType")
                .field("(select count(*) from t_canteen_user where group_id = g.group_id and `status` = 0) as applyCount")
                .join("t_user u", "g.userid = u.userid")
                .join("t_sys_file f", "f.file_id = g.avatar_id", Join.Type.LEFT)
                .where("g.id = ?")
                .select()
                .build();
        return pubDao.query(CanteenShow.class, sql.sql(), userid, id);
    }

    /**
     * 获取指定用户饭堂
     *
     * @param param
     * @return
     */
    public List<GroupList> listMeGroup(ListParam param) {
        SQL.SqlBuilder sql = new SQL.SqlBuilder()
                .table("t_canteen_user u")
                .field("g.id, g.group_id, g.name, g.info")
                .field("f.file_path as path, f.thumb_path as thumb")
                .join("t_canteen g", "g.group_id = u.group_id")
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
        String sql = "select u.type, u.group_id from t_canteen_user u\n" +
                "join t_canteen g on u.group_id = g.group_id\n" +
                "where u.userid = ? and g.id = ?";
        CanteenUser user = pubDao.query(CanteenUser.class, sql, userid, id);
        if (user == null || user.getType() == null) {
            new ValidationError("您不是协会成员哦");
        }
        if (user.getType() == CanteenUser.TYPE_QZ) {
            sql = "delete from t_canteen where id = ?";
            pubDao.execute(sql, id);
            return ActionResult.okWithData("解散成功");
        } else {
            sql = "delete from t_canteen_user where userid = ? and group_id = ?";
            pubDao.execute(sql, userid, user.getGroupId());
            return ActionResult.okWithData("退出成功");
        }
    }

    /**
     * 协会信息更新
     *
     * @param canteen
     * @param userid
     */
    @Transactional
    public void updateGroup(Canteen canteen, String userid) {
        //判断用户是否有权限进行协会信息的更新
        Integer type;
        if (canteen.getGroupId() != null) {
            String querySql = "select type from t_canteen_user where userid = ? and group_id = ?";
            type = pubDao.query(Integer.class, querySql, userid, canteen.getGroupId());
        } else {
            String querySql = "select u.type from t_canteen_user u join t_canteen g on g.group_id = u.group_id where u.userid = ? and g.id = ?";
            type = pubDao.query(Integer.class, querySql, userid, canteen.getId());
        }

        if (type == null || type.compareTo(GroupUser.TYPE_GL) > 0) {
            throw new ValidationError("对不起，你没有修改权限哦！");
        }

        SQL.SqlBuilder sql = new SQL.SqlBuilder();
        if (canteen.getAvatarId() != null) {
            sql.set("avatar_id = :avatarId");
        }
        if (canteen.getName() != null) {
            sql.set("name = :name");
        }
        if (canteen.getInfo() != null) {
            sql.set("info = :info");
        }
        if (canteen.getAddTime() != null) {
            sql.set("add_time = :addTime");
        }
        if (canteen.getUserid() != null) {
            sql.set("userid = :userid");
        }

        if (canteen.getId() != null) {
            sql.where("id = :id");
        } else if (canteen.getGroupId() != null) {
            sql.where("group_id = :groupId");
        }

        sql.update("canteen");

        pubDao.executeBean(sql.build().sql(), canteen);
    }

//    @Transactional
//    public void apply(Integer id, Integer status) {
//        SQL sql = new SQL.SqlBuilder()
//                .set("status = ?")
//                .where("id = ?")
//                .update("group")
//                .build();
//        pubDao.execute(sql.sql(), status, id);
//
//        //推送审核结果
//        Group group = pubDao.query(Group.class, "select userid, name, group_id from t_group where id = ?", id);
//        //如果为通过协会申请
//        if (status.intValue() > Status.APPLY.getStatus().intValue()) {
//
//            //将创建者添加到协会成员表
//            if (Status.NORMAL.getStatus().equals(status)) {
//                //判断用户是否已经添加
//                String userSql = "select * from t_group_user where userid = ? and group_id = ?";
//                GroupUser groupUser = pubDao.query(GroupUser.class, userSql, group.getUserid(), group.getGroupId());
//                if (groupUser == null) {
//                    groupUser = new GroupUser();
//                    groupUser.setUserid(group.getUserid());
//                    groupUser.setJoinTime(new Date());
//                    groupUser.setType(GroupUser.TYPE_QZ);
//                    groupUser.setStatus(Status.NORMAL.getStatus());
//                    groupUser.setGroupId(group.getGroupId());
//                    groupUser.setScore(0);
//                    groupUser.setMoney(0f);
//                    SQL addSql = new SQL.SqlBuilder()
//                            .field("userid, join_time, type, status, group_id, score, money")
//                            .value(":userid, :joinTime, :type, :status, :groupId, :score, :money")
//                            .insert("group_user")
//                            .build();
//                    pubDao.executeBean(addSql.sql(), groupUser);
//                } else {
//                    if (!Status.NORMAL.getStatus().equals(groupUser.getStatus())) {
//                        pubDao.execute("update t_group_user set status = ? where id = ?", Status.NORMAL.getStatus(), groupUser.getId());
//                    }
//                }
//            }
//
////            ReplyPush push = new ReplyPush(group.getUserid(), status);
////            push.setHost(ServletUtils.getHostAddr());
////            push.setAgentId(Agent.GROUP.getAgentId());
////            push.setMsg("“" + group.getName() + "”协会审核结果");
////            weixinPush.reply(push);
//        }
//    }


}
