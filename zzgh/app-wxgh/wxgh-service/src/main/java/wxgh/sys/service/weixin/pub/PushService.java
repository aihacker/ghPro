package wxgh.sys.service.weixin.pub;

import com.weixin.Agent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pub.dao.jdbc.PubDao;
import pub.dao.jdbc.sql.SQL;
import pub.dao.jdbc.sql.bean.Join;
import wxgh.data.common.suggest.SuggestPush;
import wxgh.data.entertain.act.ActPushInfo;
import wxgh.data.party.beauty.WorkPush;
import wxgh.data.party.sug.SugPush;
import wxgh.data.pub.push.info.*;
import wxgh.data.pub.woman.TeachPush;
import wxgh.data.union.group.act.result.PushResult;
import wxgh.entity.chat.ChatGroup;
import wxgh.entity.pub.tag.TagType;
import wxgh.entity.pub.tag.WxTag;
import wxgh.sys.service.weixin.chat.ChatGroupService;

import java.util.List;

/**
 * Created by Administrator on 2017/7/19.
 */
@Service
@Transactional(readOnly = true)
public class PushService {

    public PushVote pushVote(Integer id) {
        SQL sql = new SQL.SqlBuilder()
                .table("voted")
                .field("id, username,type ,theme as title")
                .where("id = ?")
                .limit("1")
                .select()
                .build();
        return pubDao.query(PushVote.class, sql.sql(), id);
    }

    public PushSug pushSug(Integer id) {
        SQL sql = new SQL.SqlBuilder()
                .table("party_sug us")
                .join("user u", "u.userid = us.userid")
                .field("us.content, us.userid, u.name as username")
                .limit("1")
                .where("us.id = ?")
                .select()
                .build();
        return pubDao.query(PushSug.class, sql.sql(), id);
    }

    public PushBBS pushBBS(Integer bbsId) {
        SQL sql = new SQL.SqlBuilder()
                .table("article a")
                .field("a.atl_id as id, a.atl_name as title, a.atl_content as content")
                .field("(if(ISNULL(a.file_ids), '', (select file_path from t_sys_file where FIND_IN_SET(file_id,a.file_ids) limit 1))) as image")
                .where("a.atl_id = ?")
                .build();
        return pubDao.query(PushBBS.class, sql.sql(), bbsId);
    }


    public PushExam pushExam(Integer examId) {
        SQL sql = new SQL.SqlBuilder()
                .table("di_exam e")
                .field("e.id, e.name, e.info, e.end_time, e.group_id, e.party_id")
                .sys_file("e.cover_id")
                .field("g.name as groupName")
                .field("d.name as partyName")
                .join("t_chat_group g", "g.group_id = e.group_id", Join.Type.LEFT)
                .join("t_party_dept d", "d.id = e.party_id", Join.Type.LEFT)
                .where("e.id = ?")
                .build();
        return pubDao.query(PushExam.class, sql.sql(), examId);
    }

    public PushResult getActResult(Integer id) {
        SQL sql = new SQL.SqlBuilder()
                .table("act_result r")
                .field("r.id, r.title, r.info, r.group_id")
                .field("a.name as actName, a.id as actId")
                .field("(select file_path from t_sys_file where find_in_set(file_id, r.imgs) limit 1) as avatar")
                .join("act a", "a.act_id = r.act_id")
                .where("r.id = ?")
                .build();
        return pubDao.query(PushResult.class, sql.sql(), id);
    }

    public PushResult getPartyActResult(Integer id) {
        SQL sql = new SQL.SqlBuilder()
                .table("party_act_result r")
                .field("r.id, r.title, r.info, r.group_id")
                .field("a.name as actName, a.id as actId")
                .field("(select file_path from t_sys_file where find_in_set(file_id, r.imgs) limit 1) as avatar")
                .join("party_act a", "a.id = r.act_id")
                .where("r.id = ?")
                .build();
        return pubDao.query(PushResult.class, sql.sql(), id);
    }

    public WxTag getTag(TagType tagType) {
        String sql = "select * from t_wx_tag where tag_type = ?";
        return pubDao.query(WxTag.class, sql, tagType.getType());
    }

    /**
     * 根据用户ID获取用户姓名
     *
     * @param userid
     * @return
     */
    public String getUser(String userid) {
        SQL sql = new SQL.SqlBuilder()
                .table("user")
                .field("name")
                .where("userid = ?")
                .build();
        return pubDao.query(String.class, sql.sql(), userid);
    }

    public List<String> getGroupUser(String groupId) {
        if (ChatGroupService.DY_GROUPID.equals(groupId)) {
            String sql = "select cu.userid from t_chat_user cu " +
                    "join t_chat_group g on cu.group_id = g.group_id " +
                    "where g.type =5 and cu.`status`=1";
            return pubDao.queryList(String.class, sql);
        } else {
            String sql = "select userid from t_chat_user where group_id = ? and status = 1";
            return pubDao.queryList(String.class, sql, groupId);
        }
    }

    // 获取党员
    public List<String> getPartyUser(Integer partyId){
        String sql = "select userid from t_party_dept_user where find_in_set(branch_id, get_party_dept_child(?)) and userid != ''";
        return pubDao.queryList(String.class, sql, partyId);
    }

    /**
     * 获取协会成员
     *
     * @param groupId
     * @return
     */
    public List<String> getUsers(String groupId) {
        String sql = "select userid from t_group_user where group_id = ? and status = ?";
        List<String> userids = pubDao.queryList(String.class, sql, groupId, 1);
        return userids;
    }

    public List<String> getCanteenUsers(String groupId) {
        String sql = "select userid from t_canteen_user where group_id = ? and status = ?";
        List<String> userids = pubDao.queryList(String.class, sql, groupId, 1);
        return userids;
    }

    /**
     * 获取群聊成员
     *
     * @param groupId
     * @return
     */
    public List<String> getChatUsers(String groupId) {
        String sql = "select userid from t_chat_user where group_id = ? and status = ?";
        List<String> userids = pubDao.queryList(String.class, sql, groupId, 1);
        return userids;
    }

    public ActPushInfo getGroupPushInfo(String actId) {
        SQL sql = new SQL.SqlBuilder()
                .table("act a")
                .field("a.id, a.name, a.info, a.address, a.regular, a.all_is, a.group_id, a.act_type, a.link")
//                .field("g.name as groupName")
                .field("if(a.regular = 0, CONCAT(DATE_FORMAT(a.start_time,'%Y-%m-%d %H:%i'),'~', DATE_FORMAT(a.end_time,'%Y-%m-%d %H:%i')), CONCAT(DATE_FORMAT(a.start_time,'%H:%i'),'~', DATE_FORMAT(a.end_time,'%H:%i'))) as time")
                .sys_file("a.img_id")
//                .join("group g", "g.group_id = a.group_id")
                .where("a.act_id = ?")
                .build();
        return pubDao.query(ActPushInfo.class, sql.sql(), actId);
    }

    public ActPushInfo getCanteenPushInfo(String actId) {
        SQL sql = new SQL.SqlBuilder()
                .table("canteen_act a")
                .field("a.id, a.name, a.info, a.address, a.regular, a.all_is, a.group_id, a.act_type, a.link")
//                .field("g.name as groupName")
                .field("if(a.regular = 0, CONCAT(DATE_FORMAT(a.start_time,'%Y-%m-%d %H:%i'),'~', DATE_FORMAT(a.end_time,'%Y-%m-%d %H:%i')), CONCAT(DATE_FORMAT(a.start_time,'%H:%i'),'~', DATE_FORMAT(a.end_time,'%H:%i'))) as time")
                .sys_file("a.img_id")
//                .join("group g", "g.group_id = a.group_id")
                .where("a.act_id = ?")
                .build();
        return pubDao.query(ActPushInfo.class, sql.sql(), actId);
    }

    @Transactional
    public void updateStatus(String jobId, Integer status) {
        String sql = "update t_schedule_job set status = ? where job_id = ?";
        pubDao.execute(sql, status, jobId);
    }

    /**
     * 摄影推送详情
     *
     * @param id
     * @return
     */
    private WorkPush getSheYingInfo(Integer id) {
        SQL sql = new SQL.SqlBuilder()
                .field("w.id, w.name, w.type, w.remark, u.name as username, u.userid")
                .table("work w")
                .join("user u", "u.userid = w.user_id")
                .where("w.id = ?")
                .limit("1")
                .select()
                .build();
        return pubDao.query(WorkPush.class, sql.sql(), id);
    }

    /**
     * 总经理直通车 转发的详情
     *
     * @param id
     * @return
     */
    public SugPush getPartySugTranInfo(Integer id) {
        SQL sql = new SQL.SqlBuilder()
                .table("party_sug ps")
                .join("user u", "u.userid = ps.userid")
                .join("dept d", "u.deptid = d.deptid")
                .field("ps.id, ps.content, ps.userid, u.name as username, d.name as deptname")
                .where("ps.id = ?")
                .limit("1")
                .select()
                .build();
        return pubDao.query(SugPush.class, sql.sql(), id);
    }

    /**
     * 会员提案 转发详情
     *
     * @param id
     * @return
     */
    public SuggestPush getSuggestTranPushInfo(Integer id) {
        SQL sql = new SQL.SqlBuilder()
                .field("us.id, us.title, us.content, u.name as username, us.userid")
                .table("user_suggest us")
                .join("user u", "u.userid = us.userid")
                .where("us.id = ?")
                .limit("1")
                .select()
                .build();

        return pubDao.query(SuggestPush.class, sql.sql(), id);
    }

    public List<String> getPartyUsers(Integer partyId){
        String sql = new SQL.SqlBuilder()
                .table("t_party_dept_user")
                .field("userid")
                .where("party_id = ?")
                .select()
                .build()
                .sql();
        return pubDao.queryList(String.class, sql, partyId);
    }

    public List<String> getAllPartyUsers(){
        String sql = new SQL.SqlBuilder()
                .table("t_party_dept_user")
                .field("userid")
                .where("userid != ''")
                .select()
                .build()
                .sql();
        return pubDao.queryList(String.class, sql);
    }

    public List<String> getPartyGroupUsers(String groupIds){
        String sql = new SQL.SqlBuilder()
                .table("chat_user cu")
                .join("chat_group g", "g.group_id = cu.group_id")
                .field("cu.userid")
                .where("g.id in ("+groupIds+")")
                .where("g.type = ?")
                .groupBy("cu.userid")
                .select().build().sql();
        return pubDao.queryList(String.class, sql, ChatGroup.TYPE_PARTY_GROUP);
    }

    public List<String> getPartyDeptUsers(String groupIds) {
        String sql = new SQL.SqlBuilder()
                .table("party_dept_user")
                .field("userid")
                .where("branch_id = ? and userid is not null").select().build().sql();
        return pubDao.queryList(String.class, sql, groupIds);
    }

    public Integer countPartyUser(){
        String sql = new SQL.SqlBuilder()
                .table("t_party_dept_user")
                .field("userid")
                .count()
                .build()
                .sql();
        return pubDao.queryInt(sql);
    }

    public TeachPush pushTeach(Integer id) {
        SQL sql = new SQL.SqlBuilder()
                .table("woman_teach t")
                .field("t.id, t.name, t.content")
                .field("(concat(date_format(t.start_time, '%m-%d %H:%i'), '-', date_format(t.end_time, '%m-%d %H:%i'))) as time")
                .field("f.file_path as cover")
                .join("t_sys_file f", "f.file_id = t.cover")
                .where("t.id = ?")
                .build();
        return pubDao.query(TeachPush.class, sql.sql(), id);
    }

    @Autowired
    private PubDao pubDao;

    public PushPartyAct pushPartyAct(Integer actid) {
        String sql = new SQL.SqlBuilder()
                .table("party_act a")
                .field("a.name, a.type_name, a.join_type, a.info, a.groupid")
                .field("(concat(date_format(a.start_time, '%m-%d %H:%i'), '-', date_format(a.end_time, '%m-%d %H:%i'))) as time")
                .sys_file("a.img_url")
                .where("a.id = ?")
                .build().sql();
        return pubDao.query(PushPartyAct.class, sql, actid);

    }

}
