package wxgh.sys.service.weixin.entertain.act;

import com.libs.common.data.DateUtils;
import com.libs.common.type.StringUtils;
import com.libs.common.type.TypeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pub.dao.jdbc.PubDao;
import pub.dao.jdbc.sql.SQL;
import pub.dao.jdbc.sql.bean.Join;
import pub.dao.jdbc.sql.bean.Order;
import pub.error.ValidationError;
import pub.functions.DateFuncs;
import wxgh.app.consts.Status;
import wxgh.app.session.user.UserSession;
import wxgh.app.sys.api.ActSchedule;
import wxgh.app.sys.schedule.JobGroup;
import wxgh.app.sys.schedule.ScheduleManager;
import wxgh.app.sys.task.WeixinPush;
import wxgh.data.entertain.act.ActInfo;
import wxgh.data.entertain.act.ActList;
import wxgh.data.entertain.act.RegularInfo;
import wxgh.entity.entertain.act.Act;
import wxgh.entity.entertain.act.ActJoin;
import wxgh.entity.entertain.act.ActRegular;
import wxgh.entity.entertain.act.ScoreRule;
import wxgh.entity.pub.ScheduleJob;
import wxgh.param.entertain.act.ActParam;
import wxgh.param.entertain.act.ActShowParam;
import wxgh.sys.dao.entertain.act.ActDao;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2017/7/19.
 */
@Service
@Transactional(readOnly = true)
public class ActService {

    /**
     * 取消活动
     *
     * @param id
     */
    @Transactional
    public void cancelAct(Integer id) {
        //todo 更新活动状态
        String sql = "update t_act set status = ? where id = ?";
        pubDao.execute(sql, Status.FAILED.getStatus(), id);

        //todo 停止定时器
        Act act = pubDao.query(Act.class, "select act_id, regular from t_act where id = ?", id);
        if (act != null) {
            String group = 1 == act.getRegular() ? JobGroup.ACT_WEEK.getType() : JobGroup.ACT_PUSH.getType();
            scheduleManager.removeJob(act.getActId(), group);
            //更新定时器状态
            String jobSql = "update t_schedule_job set status = ? where job_name = ? and job_group = ?";
            pubDao.execute(jobSql, ScheduleJob.STATUS_CACEL, act.getActId(), group);
        }
    }

    public String getDetails(Integer actId) {
        String sql = "select info from t_act where id = ?";
        return pubDao.query(String.class, sql, actId);
    }

    public String getName(String actId) {
        String sql = "select name from t_act where act_id = ?";
        return pubDao.query(String.class, sql, actId);
    }

    public ScoreRule getScoreRule(Integer actId) {
        SQL sql = new SQL.SqlBuilder()
                .table("score_rule r")
                .field("r.*")
                .join("act a", "a.score_rule = r.rule_id")
                .where("a.id = ?").select().build();
        return pubDao.query(ScoreRule.class, sql.sql(), actId);
    }

    public List<ActRegular> getActRegular(String actId) {
        SQL sql = new SQL.SqlBuilder()
                .table("act_regular")
                .where("act_id = ?")
                .field("*")
                .select().build();
        return pubDao.queryList(ActRegular.class, sql.sql(), actId);
    }

    public Act getBaseActInfo(Integer actId) {
        SQL.SqlBuilder sqlBuilder = new SQL.SqlBuilder()
                .field("*")
                .table("act")
                .where("id = ?")
                .select();
        return pubDao.query(Act.class, sqlBuilder.build().sql(), actId);
    }

    /**
     * 获取团队活动列表
     *
     * @param param
     * @return
     */
    public List<ActList> getTeamActList(ActParam param) {
        SQL.SqlBuilder sql = new SQL.SqlBuilder()
                .table("act a")
                .field("a.id, a.name, a.address, a.phone")
                .field("s.file_path as path, s.thumb_path as thumb")
                .join("sys_file s", "s.file_id = a.img_id", Join.Type.LEFT)
                .order("a.add_time", Order.Type.DESC);
        if (param.getStatus() != null) {
            sql.where("a.status = :status");
        }
        if (param.getActType() != null) {
            sql.where("a.act_type = :actType");
        }
        if (param.getGroupId() != null) {
            sql.join("chat_group g", "g.group_id = a.group_id")
                    .where("g.id = :groupId");
        }
        if (param.getRegular() != null) {
            if (param.getRegular() == 1) { //如果为定期活动
                sql.field("(select CONCAT('周', GROUP_CONCAT(week_name(`week`) SEPARATOR '、'), DATE_FORMAT(a.start_time,'%H:%i'),'-', DATE_FORMAT(a.end_time,'%H:%i')) from t_act_regular where act_id = a.act_id)as time")
                        .where("a.regular = :regular");
            } else if (param.getRegular() == 0) {
                sql.field("CONCAT(DATE_FORMAT(a.start_time,'%m-%d %H:%i'),'-', DATE_FORMAT(a.end_time,'%m-%d %H:%i')) as time")
                        .where("a.regular = :regular");
            } else {
                sql.field("if(a.regular = 1, (select CONCAT('周', GROUP_CONCAT(week_name(`week`) SEPARATOR '、'), DATE_FORMAT(a.start_time,'%H:%i'),'-', DATE_FORMAT(a.end_time,'%H:%i')) from t_act_regular where act_id = a.act_id), CONCAT(DATE_FORMAT(a.start_time,'%m-%d %H:%i'),'-', DATE_FORMAT(a.end_time,'%m-%d %H:%i'))) as time");
            }
        }

        return pubDao.queryPage(sql, param, ActList.class);
    }

    @Transactional
    public ActInfo getActInfo(Integer actId) {
        SQL reSql = new SQL.SqlBuilder()
                .table("act a")
                .field("a.start_time, a.end_time, a.act_id, a.regular, a.act_type, a.act_numb")
                .field("(select group_concat(`week`) from t_act_regular where act_id = a.act_id order by `week`) as weeks")
                .where("a.id = ?")
                .build();
        RegularInfo info = pubDao.query(RegularInfo.class, reSql.sql(), actId);
        if (info != null) {

            ActShowParam param = new ActShowParam();
            param.setUserid(UserSession.getUserid());
            param.setId(actId);

            SQL.SqlBuilder sql = new SQL.SqlBuilder()
                    .table("act a")
                    .field("a.act_numb,a.id, a.act_id, a.name, a.info, a.address, a.lat, a.lng, a.has_score, a.money, a.regular, a.userid, a.sign_is,a.addr_remark as remark")
                    .field("(select group_concat(file_path) from t_sys_file where find_in_set(file_id, a.image_ids)) path")
                    .field("(select group_concat(thumb_path) from t_sys_file where find_in_set(file_id, a.image_ids)) thumb")
                    .field("u.name as username")
                    .join("user u", "a.userid = u.userid")
                    .where("a.id = :id");
            if (info.getRegular() == 0) {
                sql.field("(select COUNT(*) FROM t_act_join where act_id = a.act_id and type = 1) as joinNumb")
                        .field("(select COUNT(*) FROM t_act_join where act_id = a.act_id and type = 2) as leaveNumb")
                        .field("(select type from t_act_join where act_id = a.act_id and userid = :userid limit 1) as joinType")
                        .field("(select count(*) from t_act_result where act_id = a.act_id and status = 1) as resultCount");
            } else {
                Integer dateId = DateUtils.getNearDate(info.getEndTime(), info.getWeeks());
                param.setDateid(dateId);
                sql.field("(select COUNT(*) FROM t_act_join where act_id = a.act_id and dateid=:dateid and type = 1) as joinNumb")
                        .field("(select COUNT(*) FROM t_act_join where act_id = a.act_id and dateid=:dateid and type = 2) as leaveNumb")
                        .field("(select type from t_act_join where act_id = a.act_id and userid = :userid and dateid=:dateid limit 1) as joinType");
            }

            ActInfo actInfo = pubDao.query(sql.build().sql(), param, ActInfo.class);

            if (actInfo != null) {
                if (actInfo.getPath() != null)
                    actInfo.setCovers(TypeUtils.strToList(actInfo.getPath()));

                Date now = new Date();

                if (info.getRegular() == 0) {
                    actInfo.setTime(DateUtils.formatDate(info.getStartTime(), "MM-dd HH:mm") + "~" + DateUtils.formatDate(info.getEndTime(), "MM-dd HH:mm"));
                    if (now.before(info.getStartTime())) { //活动未开始
                        actInfo.setStatus(1);
                    } else if (now.after(info.getEndTime())) { //活动已结束
                        actInfo.setStatus(3);
                    } else {
                        actInfo.setStatus(2); //活动进行中
                    }
                    if (actInfo.getStatus() >= 2) {
                        actInfo.setSignIs(1);
                    } else {
                        actInfo.setSignIs(0);
                    }
                } else {
                    String start = DateUtils.formatDate(info.getStartTime(), "HH:mm");
                    String end = DateUtils.formatDate(info.getEndTime(), "HH:mm");
                    actInfo.setTime(DateUtils.getWeekName(info.getWeeks().split(","))
                            + " " + start + "~" + end);
                    String nowStr = DateUtils.formatDate(now, "HH:mm");
                    Integer today = DateFuncs.getIntToday();
                    if (today == param.getDateid()) { //同一天
                        if (nowStr.compareTo(start) < 0) {
                            actInfo.setStatus(1);
                        } else if (nowStr.compareTo(start) >= 0 && nowStr.compareTo(end) <= 0) {
                            actInfo.setStatus(2); //活动进行中
                        }
                    } else {
                        actInfo.setStatus(1);
                    }
                    actInfo.setDateid(param.getDateid());
                    if (actInfo.getStatus() == 2) {
                        actInfo.setSignIs(1);
                    } else {
                        actInfo.setSignIs(0);
                    }
                }

                //如果大型活动有报名人数限制
                if (info.getActType() >= Act.ACT_TYPE_BIG && (info.getActNumb() != null && info.getActNumb() > 0)) {
                    String joinNumbSql = new SQL.SqlBuilder()
                            .table("act_join")
                            .where("act_id = ? and type = ?")
                            .count()
                            .build()
                            .sql();
                    Integer joinNumb = pubDao.queryInt(joinNumbSql, info.getActId(), 1);
                    if (joinNumb >= info.getActNumb()) {
                        actInfo.setStatus(4);
                    }
                }
            }
            return actInfo;

        }
           /* pubDao.execute("SET @time = unix_timestamp(NOW())");
            SQL.SqlBuilder sql = new SQL.SqlBuilder()
                    .table("act a")
                    .join("user u", "a.userid = u.userid")
                    .join("act_regular r", "a.act_id = r.act_id", Join.Type.LEFT)
                    //.join("sys_file s", "s.file_id = a.img_id", Join.Type.LEFT)
                    .field("a.act_numb,a.id, a.act_id, a.name, a.info, a.address, a.lat, a.lng, a.has_score, a.money, a.regular, a.userid, a.sign_is")
                    //.field("s.file_path as path, s.thumb_path as thumb")
                    .field("(select group_concat(file_path) from t_sys_file where find_in_set(file_id, a.image_ids)) path")
                    .field("(select group_concat(thumb_path) from t_sys_file where find_in_set(file_id, a.image_ids)) thumb")
                    .field("u.name as username")
                    .field("if(a.regular = 0, CONCAT(a.start_time,'~',a.end_time), CONCAT('周', GROUP_CONCAT(week_name(r.`week`) SEPARATOR '、'),' ', DATE_FORMAT(a.start_time,'%H:%i'),'-', DATE_FORMAT(a.end_time,'%H:%i')))as time")
                    .field("(select COUNT(*) FROM t_act_join where act_id = a.act_id and type = 1) as joinNumb")
                    .field("(select COUNT(*) FROM t_act_join where act_id = a.act_id and type = 2) as leaveNumb")
                    .field("(select type from t_act_join where act_id = a.act_id and userid = ? limit 1) as joinType")
                    .field("IF(@time < UNIX_TIMESTAMP(a.start_time), 1, IF(@time BETWEEN UNIX_TIMESTAMP(a.start_time) AND UNIX_TIMESTAMP(a.end_time), 2, 3)) as status")
                    .field("(select count(*) from t_act_result where act_id = a.act_id and status = 1) as resultCount")
                    .where("a.id = ?")
                    .select();*/

        return null;
    }

    @Transactional
    public void updateActTime(Act act, Integer push) {
        String sql = "update t_act a set a.start_time = :startTime,a.end_time = :endTime where a.act_id = :actId ";
        pubDao.executeBean(sql, act);
        if (act.getRegular().intValue() == 1) {
            if (TypeUtils.empty(act.getWeek())) {
                throw new ValidationError("请选择定期星期");
            }
            String deleteSql = "delete from t_act_regular where act_id = :actId";
            pubDao.executeBean(deleteSql, act);
            String addRegularSql = "insert into t_act_regular(act_id, week, start_time, end_time) values(:actId, :week, :startTime, :endTime)";
            List<ActRegular> regulars = new ArrayList<>();
            String[] weeks = act.getWeek().split(",");
            String startTime = DateUtils.toStr(act.getStartTime(), "HH:mm");
            String endTime = DateUtils.toStr(act.getEndTime(), "HH:mm");

            int[] weekInts = new int[weeks.length];
            int i = 0;
            for (String week : weeks) {
                ActRegular regular = new ActRegular();
                regular.setActId(act.getActId());
                regular.setWeek(Integer.valueOf(week.trim()));
                regular.setStartTime(startTime);
                regular.setEndTime(endTime);
                regulars.add(regular);
                weekInts[i] = regular.getWeek();
                i++;
            }
            pubDao.executeBatch(addRegularSql, regulars);

            //如果需要提醒推送
            if (act.getRemind() > 0) {
                //创建定时器
                actSchedule.weekAct(act, weekInts);
            }
        } else {
            /**
             * 发送提醒
             */
            if (act.getRemind() > 0) {
                actSchedule.act(act);
            }
        }
        /**
         * 推送消息给群组用户
         */
        if (push != null && push.intValue() == 1) {
            weixinPush.act(act.getActId(), null, true);
        }
    }

    @Transactional
    public void addAct(Act act, boolean push) {
        String actId = StringUtils.uuid();
        act.setActId(actId);
        // 协会发布活动页面传递过来的是主键id, 团队活动发布传的是groupId
        if (act.getActType().equals(Act.ACT_TYPE_GROUP)) {
            String sql = "select group_id from t_group where id = ? limit 1 ";
            String groupId = pubDao.query(String.class, sql, act.getGroupId());
            act.setGroupId(groupId);
        }

        if (act.getRegular() == null)
            act.setRegular(0);
        if (act.getRegular().intValue() == 1) { //如果为定期活动
            if (TypeUtils.empty(act.getWeek())) {
                throw new ValidationError("请选择定期星期");
            }
            String addRegularSql = "insert into t_act_regular(act_id, week, start_time, end_time) values(:actId, :week, :startTime, :endTime)";
            List<ActRegular> regulars = new ArrayList<>();
            String[] weeks = act.getWeek().split(",");
            String startTime = DateUtils.toStr(act.getStartTime(), "HH:mm");
            String endTime = DateUtils.toStr(act.getEndTime(), "HH:mm");

            int[] weekInts = new int[weeks.length];
            int i = 0;
            for (String week : weeks) {
                ActRegular regular = new ActRegular();
                regular.setActId(actId);
                regular.setWeek(Integer.valueOf(week.trim()));
                regular.setStartTime(startTime);
                regular.setEndTime(endTime);
                regulars.add(regular);
                weekInts[i] = regular.getWeek();
                i++;
            }
            pubDao.executeBatch(addRegularSql, regulars);

            //如果需要提醒推送
            if (act.getRemind() > 0) {
                //创建定时器
                actSchedule.weekAct(act, weekInts);
            }
        } else { //非定期活动，活动开始提醒
            if (act.getRemind()!= null && act.getRemind() > 0) {
                actSchedule.act(act);
            }
        }
        if (act.getActNumb() == null) {
            act.setActNumb(0); //0表示活动参加人数不限制
        }

        act.setAddTime(new Date());
        act.setAccount(0);
        actDao.save(act);
    }

    /**
     * 获取活动列表
     *
     * @param param
     * @return
     */
    public List<ActList> listAct(ActParam param) {
        SQL.SqlBuilder sql = new SQL.SqlBuilder()
                .table("act a")
                .field("a.id, a.name, a.address, a.phone, a.money,a.info,a.act_numb,a.act_id,a.link")
                .field("s.file_path as path, s.thumb_path as thumb")
                .join("sys_file s", "s.file_id = a.img_id", Join.Type.LEFT)
                .order("a.add_time", Order.Type.DESC);
        if (param.getStatus() != null) {
            sql.where("a.status = :status");
        }
        if (param.getActType() != null) {
            sql.where("a.act_type = :actType");
        } else {
            sql.where("a.act_type between 3 and 5");
        }
        if (param.getGroupId() != null) {
            sql.join("group g", "g.group_id = a.group_id")
                    .where("g.id = :groupId");
        }
        if (param.getUserid() != null) {
            sql.where("a.userid = :userid");
        }
        if (param.getRegular() != null) {
            if (param.getRegular() == 1) { //如果为定期活动
                sql.field("(select CONCAT('周', GROUP_CONCAT(week_name(`week`) SEPARATOR '、'), DATE_FORMAT(a.start_time,'%H:%i'),'-', DATE_FORMAT(a.end_time,'%H:%i')) from t_act_regular where act_id = a.act_id)as time")
                        .where("a.regular = :regular");
            } else if (param.getRegular() == 0) {
                sql.field("CONCAT(DATE_FORMAT(a.start_time,'%m-%d %H:%i'),'-', DATE_FORMAT(a.end_time,'%m-%d %H:%i')) as time")
                        .where("a.regular = :regular");
            } else {
                sql.field("if(a.regular = 1, (select CONCAT('周', GROUP_CONCAT(week_name(`week`) SEPARATOR '、'), DATE_FORMAT(a.start_time,'%H:%i'),'-', DATE_FORMAT(a.end_time,'%H:%i')) from t_act_regular where act_id = a.act_id), CONCAT(DATE_FORMAT(a.start_time,'%m-%d %H:%i'),'-', DATE_FORMAT(a.end_time,'%m-%d %H:%i'))) as time");
            }
        }
        return pubDao.queryPage(sql, param, ActList.class);
    }

    //获取报名名单
    public List<ActJoin> listActJoins(ActParam actParam) {
        String sql = new SQL.SqlBuilder()
                .table("act_join a")
                .join("user u", "a.userid = u.userid")
                .join("dept d", "u.deptid = d.id", Join.Type.LEFT)
                .field("a.*,u.name as name,u.mobile as mobile,d.name as deptName")
                .where("a.act_id = :actId")
                .select()
                .build()
                .sql();
        return pubDao.queryList(sql, actParam, ActJoin.class);
    }

    @Autowired
    private PubDao pubDao;

    @Autowired
    private ActDao actDao;

    @Autowired
    private ScheduleManager scheduleManager;

    @Autowired
    private ActSchedule actSchedule;

    @Autowired
    private WeixinPush weixinPush;
}
