package wxgh.sys.service.weixin.union.innovation;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pub.dao.jdbc.PubDao;
import pub.dao.jdbc.sql.SQL;
import pub.dao.jdbc.sql.bean.Join;
import pub.dao.jdbc.sql.bean.Order;
import pub.dao.page.Page;
import pub.utils.StrUtils;
import wxgh.data.union.SendInfo;
import wxgh.data.union.group.user.UserData;
import wxgh.data.union.innovation.group.MyGroupData;
import wxgh.entity.union.group.Group;
import wxgh.entity.union.group.GroupUser;
import wxgh.param.union.innovation.group.GroupQuery;

import java.util.List;

/**
 *----------------------------------------------------------
 * @Description 
 *----------------------------------------------------------
 * @Author  Ape
 *----------------------------------------------------------
 * @Email <16511660@qq.com>
 *----------------------------------------------------------
 * @Date 2017-08-03 15:12
 *----------------------------------------------------------
 */
@Service
@Transactional(readOnly = true)
public class Group2Service {


    @Autowired
    private PubDao pubDao;

    public static final Log log = LogFactory.getLog(Group2Service.class);

    /**
     * 获取全部团队
     * @param page
     * @return
     */
    public List<MyGroupData> getList(Page page){
        SQL.SqlBuilder sql = new SQL.SqlBuilder()
                .field("g.*, f.file_path as avatar")
                .table("group g")
                .join("sys_file f", "f.file_id = g.avatar_id", Join.Type.LEFT)
                .where("g.status = 1")
                .order("g.add_time", Order.Type.DESC)
                .select();
        return pubDao.queryPage(sql, page, MyGroupData.class);
    }

    
    public MyGroupData getGroup(Integer id) {
        SQL sql = new SQL.SqlBuilder()
                .table("group g")
                .field("g.name, g.info, g.add_time as addTime, g.group_id as groupId, f.file_path as avatar")
                .join("sys_file f", "f.file_id = g.avatar_id")
                .where("g.id = ?")
                .select().build();
        return pubDao.query(MyGroupData.class, sql.sql(), id);
    }

    

    
    public Integer count(GroupQuery query) {
        String sql = "SELECT COUNT(*) FROM t_group g JOIN t_user u ON g.user_id = u.userid";
        if (query.getStatus() != null) {
            sql += " WHERE g.status = " + query.getStatus();
        }
        if (!StrUtils.empty(query.getFuzzyName())) {
            sql += " AND g.name LIKE '" + query.getFuzzyName() + "'";
        }
        if (query.getType() != null) {
            sql += " AND g.type = " + query.getType();
        }
        sql += " AND FIND_IN_SET(u.deptid, query_dept_child(query_dept_company_id2(" + query.getDeptid() + "))) > 0";

        log.debug("自定义查询：" + sql);

        return pubDao.query(Integer.class, sql);
    }

    
    public GroupUser getGroupUser(String userId, String groupId){
        SQL.SqlBuilder sql = new SQL.SqlBuilder()
                .field("*")
                .table("group_user")
                .where("userid = ?")
                .where("group_id = ?");
        return pubDao.query(GroupUser.class, sql.build().sql(), userId, groupId);
    }

    /**
     * 获取团队的人
     * @param groupId
     * @return
     */
    public List<UserData> getGroupUser(Integer groupId){
        SQL.SqlBuilder sql = new SQL.SqlBuilder()
                .field("u.name as username, u.avatar, gu.join_time as joinTime, gu.status, gu.type, u.userid")
                .table("group_user gu")
                .join("user u", "u.userid = gu.userid")
                .where("gu.id = ?");
        return pubDao.queryList(UserData.class, sql.build().sql(), groupId);
    }

    /**
     * 获取我的团队
     * @param groupQuery
     * @return
     */
    public List<MyGroupData> getMyGroups(GroupQuery groupQuery) {
        SQL.SqlBuilder sql = new SQL.SqlBuilder()
                .field("g.*, f.file_path as avatar")
                .table("group g")
                .join("group_user m", "g.group_id = m.group_id")
                .join("sys_file f ", "f.file_id = g.avatar_id", Join.Type.LEFT)
                .where("g.status = 1")
                .where("m.status = 1")
                .groupBy("g.id");

        if(groupQuery.getUserid() != null)
            sql.where("m.userid = :userid");
        if(groupQuery.getType() != null)
            sql.where("g.type = :type");
        return pubDao.queryList(sql.build().sql(), groupQuery, MyGroupData.class);
//        return group2Dao.getMyGroups(groupQuery);
    }

    
    public List<Group> getApplyGroups(String userid, String status) {
        String sql = "select * from t_group where userid=? and status in(?)";
        return pubDao.queryList(Group.class, sql, userid, status);
    }

    
    public Group getGroupDetail(Integer id) {
//        String sql = "select * from t_group where id=?";
        SQL sql = new SQL.SqlBuilder()
                .table("group g")
                .field("g.*, f.file_path as avatarId")
                .join("sys_file f", "f.file_id = g.avatar_id", Join.Type.LEFT)
                .where("id = ?")
                .build();
        return pubDao.query(Group.class, sql.sql(), id);
    }

    
    public SendInfo getSendInfoById(Integer id) {
        String sql = "select '兴趣组申请' as sendType,\n" +
                "                s.id as itemId,\n" +
                "                u.name as username,u.userid as userId,\n" +
                "                d.name as deptName\n" +
                "                from t_group s join t_user u on u.userid=s.user_id\n" +
                "                join t_dept d on u.deptid=d.deptid \n" +
                "                where s.id=?;";
        return pubDao.query(SendInfo.class, sql, id);
    }


}

