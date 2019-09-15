package wxgh.sys.service.admin.party.member;

import com.libs.common.type.TypeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pub.dao.jdbc.PubDao;
import pub.dao.jdbc.sql.SQL;
import pub.dao.jdbc.sql.bean.Join;
import pub.error.ValidationError;
import wxgh.data.party.member.DeptList;
import wxgh.data.party.member.PartyList;
import wxgh.data.party.member.UserGroup;
import wxgh.entity.chat.ChatUser;
import wxgh.entity.party.member.PartyMember;
import wxgh.param.party.member.PartyParam;
import wxgh.sys.dao.party.member.PartyMemberDao;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Sheng on 2017/9/5.
 */
@Service
@Transactional(readOnly = true)
public class PartyMemberService {

    @Autowired
    private PubDao pubDao;

    @Autowired
    private PartyMemberDao partyMemberDao;

    public List<PartyList> partyList(PartyParam param) {
        SQL.SqlBuilder sql = new SQL.SqlBuilder()
                .table("party_dept_user pu")
                .field("pu.id,pu.userid,pu.in_time,pu.zz_time,pu.party_worker,u.avatar,pu.education")
                .field("if(ISNULL(pu.userid), pu.username, u.`name`) as username")
                .field("if(ISNULL(pu.userid), pu.mobile, u.mobile) as moblie")
                .field("(SELECT cu.type from t_chat_user cu where cu.userid = pu.userid and cu.group_id = (SELECT group_id from t_party_dept where id = pu.party_id)) as type")
                .field("(SELECT p.group_id FROM t_party_dept p WHERE p.id=pu.party_id) AS groupId")
                .field("(SELECT p.name FROM t_party_dept p WHERE p.id=pu.party_id) AS deptName")
                .join("user u", "pu.userid=u.userid", Join.Type.LEFT);
        if (param.getDeptid() != null) {
            if (param.getDeptid().equals(1)) {
            } else if (param.getDeptid() <= 6 || param.getDeptid() == 29) {
                sql.where("pu.branch_id = :deptid");
            } else if (param.getDeptid() > 6) {
                sql.where("pu.party_id = :deptid");
            }
        } else if (param.getUserid() != null) {
            sql.where("pu.userid=:userid");
        }
        if (param.getSearch() != null) {
            param.setSearch(param.getSearch() + "%");
            sql.where("(if(ISNULL(pu.userid), pu.mobile, u.mobile) like :search or if(ISNULL(pu.userid), pu.username, u.`name`) like :search)");
        }
        if (param.getWorker() != null) {
            param.setWorker("%" + param.getWorker() + "%");
            sql.where("pu.party_worker like :worker");
        }
        return pubDao.queryPage(sql, param, PartyList.class);
    }

    @Transactional
    public void delete(String id) {
        if (TypeUtils.empty(id)) {
            throw new ValidationError("请选择需要删除的党员！");
        }

        //删除聊天表党员
        String sql = "SELECT du.userid, d.group_id as groupId from t_party_dept_user du\n" +
                "join t_party_dept d on du.party_id = d.id\n" +
                "where du.id in (" + id + ")";
        List<UserGroup> userGroups = pubDao.queryList(UserGroup.class, sql);
        sql = "delete from t_chat_user where userid = :userid and group_id = :groupId";
        pubDao.executeBatch(sql, userGroups);

        //删除党员
        pubDao.execute(SQL.deleteByIds("t_party_dept_user", id));
    }

    @Transactional
    public void savePartyMember(PartyMember partyMember) {
        partyMemberDao.save(partyMember);

        //添加聊天党员
        String sql = "insert into t_chat_user(userid, type, group_id, add_time, status)\n" +
                "select ?, ?, group_id, NOW(), ? from t_party_dept where id = ?";
        pubDao.execute(sql, partyMember.getUserid(), ChatUser.TYPE_USER, 1, partyMember.getPartyId());
    }

    public PartyMember getPartyByUserid(String userid) {
        SQL sql = new SQL.SqlBuilder()
                .select("t_party_dept_user u")
                .field("u.*")
                .where("u.userid = ?").build();
        return pubDao.query(PartyMember.class, sql.sql(), userid);
    }

    @Transactional
    public void update_type(PartyList list) {
        String sql = "UPDATE t_chat_user c set type=? WHERE c.userid=? AND c.group_id=?";
        pubDao.execute(sql, list.getType(), list.getUserid(), list.getGroupId());
    }

    @Transactional
    public void updateDept(List<DeptList> deptList) {

        String beforeGroupId = null, afterGroupId = null;
        // 支部关联的群组, 正常情况是当前选中的用户属于同一个支部
        if (deptList.size() > 0) {
            DeptList dept = deptList.get(0);
            if (dept != null) {
                if (dept.getId() != null && dept.getParty_id() != null) {
                    // 找出之前所在的群组GroupID
                    String sql = new SQL.SqlBuilder()
                            .table("party_dept d")
                            .field("d.group_id")
                            .join("party_dept_user du", "du.party_id = d.id")
                            .where("du.id = ?")
                            .limit("1")
                            .select()
                            .build().sql();
                    beforeGroupId = pubDao.query(String.class, sql, dept.getId());
                    // 找出要转入的群组GroupId
                    String gSql = new SQL.SqlBuilder()
                            .table("party_dept")
                            .field("group_id")
                            .where("id = ?")
                            .limit("1")
                            .select()
                            .build()
                            .sql();
                    afterGroupId = pubDao.query(String.class, gSql, dept.getParty_id());
                }
            }
        }

        if (beforeGroupId != null && afterGroupId != null) {

            List<Integer> ids = new ArrayList<>();
            for (DeptList deptList1 : deptList)
                ids.add(deptList1.getId());

            // 找出用户id
            List<String> userids = pubDao.queryList(String.class,
                    new SQL.SqlBuilder()
                            .table("t_party_dept_user")
                            .field("userid")
                            .where("id in (?)")
                            .select().build().sql(), TypeUtils.listToStr(ids));

            List<ChatUser> list = new ArrayList<>();
            // 要更新的信息
            for (String userid : userids) {
                ChatUser chatUser = new ChatUser();
                chatUser.setStatus(1);
                chatUser.setType(ChatUser.TYPE_USER);
                chatUser.setAddTime(new Date());
                chatUser.setGroupId(afterGroupId);
                chatUser.setUserid(userid);
                chatUser.setRemark(beforeGroupId);
                list.add(chatUser);
            }

            // 批量更新群组信息
            String uSql = new SQL.SqlBuilder()
                    .table("chat_user")
                    .set("status = :status")
                    .set("add_time = :addTime")
                    .set("type = :type")
                    .set("group_id = :groupId")
                    .where("userid = :userid")
                    .where("group_id = :remark")
                    .update()
                    .build().sql();

            pubDao.executeBatch(uSql, list);

            // 更新支部信息
            SQL.SqlBuilder sql = new SQL.SqlBuilder()
                    .table("t_party_dept_user")
                    .set("branch_id=:branch_id")
                    .set("party_id=:party_id")
                    .where("id=:id")
                    .update();
            pubDao.executeBatch(sql.build().sql(), deptList);
        }

    }

    @Transactional
    public void transferParty(String userid, Integer partyId, Integer branchId) {

        String beforeGroupId = null, afterGroupId = null;
        // 支部关联的群组, 正常情况是当前选中的用户属于同一个支部
        if (userid != null && partyId != null) {
            // 找出之前所在的群组GroupID
            String sql = new SQL.SqlBuilder()
                    .table("party_dept d")
                    .field("d.group_id")
                    .join("party_dept_user du", "du.party_id = d.id")
                    .where("du.userid = ?")
                    .limit("1")
                    .select()
                    .build().sql();
            beforeGroupId = pubDao.query(String.class, sql, userid);
            // 找出要转入的群组GroupId
            String gSql = new SQL.SqlBuilder()
                    .table("party_dept")
                    .field("group_id")
                    .where("id = ?")
                    .limit("1")
                    .select()
                    .build()
                    .sql();
            afterGroupId = pubDao.query(String.class, gSql, partyId);
        }

        if (beforeGroupId != null && afterGroupId != null) {

            // 要更新的信息
            ChatUser chatUser = new ChatUser();
            chatUser.setStatus(1);
            chatUser.setType(ChatUser.TYPE_USER);
            chatUser.setAddTime(new Date());
            chatUser.setGroupId(afterGroupId);
            chatUser.setUserid(userid);
            chatUser.setRemark(beforeGroupId);

            // 批量更新群组信息
            String uSql = new SQL.SqlBuilder()
                    .table("chat_user")
                    .set("status = :status")
                    .set("add_time = :addTime")
                    .set("type = :type")
                    .set("group_id = :groupId")
                    .where("userid = :userid")
                    .where("group_id = :remark")
                    .update()
                    .build().sql();

            pubDao.executeBean(uSql, chatUser);

            // 更新支部信息
            SQL.SqlBuilder sql = new SQL.SqlBuilder()
                    .table("t_party_dept_user")
                    .set("branch_id=?")
                    .set("party_id=?")
                    .where("userid=?")
                    .update();
            pubDao.execute(sql.build().sql(), branchId, partyId, userid);
        }

    }

    @Transactional
    public void updateEducation(Integer id, String educate) {
        String sql = "UPDATE t_party_dept_user set education=? where id=?";
        pubDao.execute(sql, educate, id);
    }
}
