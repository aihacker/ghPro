package wxgh.sys.service.weixin.entertain.sport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pub.dao.jdbc.PubDao;
import pub.dao.jdbc.sql.SQL;
import pub.dao.jdbc.sql.bean.Order;
import pub.functions.DateFuncs;
import wxgh.data.entertain.sport.HistoryList;
import wxgh.data.entertain.sport.HistoryShow;
import wxgh.data.entertain.sport.SportList;
import wxgh.data.entertain.sport.UserSport;
import wxgh.data.entertain.sport.history.DateStep;
import wxgh.data.entertain.sport.history.HistRecord;
import wxgh.data.entertain.sport.history.NameDateStep;
import wxgh.data.pub.user.UseridDeptMobile;
import wxgh.entity.entertain.sport.Sport;
import wxgh.entity.entertain.sport.SportLast;
import wxgh.param.entertain.sport.SportParam;

import java.util.*;

/**
 * Created by Administrator on 2017/8/25.
 */
@Service
@Transactional(readOnly = true)
public class SportService {

    public Integer getStep(String userid, int start, int end) {
        String sql = "select SUM(step_count) from t_sport_last where userid = ? and date_id >= ? and date_id <= ?";
        return pubDao.queryInt(sql, userid,start,end);
    }

    public Integer getRank(String userid, int start, int end) {
        String sql = "select c.rownum from (SELECT @rownum:=@rownum+1 AS rownum,b.userid FROM (SELECT SUM(l.`step_count`) AS step,l.userid FROM t_sport_last l where date_id >=? and date_id <=? GROUP BY l.`userid`) b,(SELECT @rownum:=0) r ORDER BY b.step) c where c.userid =?";
        return pubDao.queryInt(sql,start,end,userid);
    }

    private void buildParam(SportParam param, SQL.SqlBuilder sql) {
        if (param.getDateId() != null) {
            sql.where("s.date_id = :dateId");
        }
        if (param.getDeptid() != null) {
            sql.where("s.deptid = :deptid");
        }
        if (param.getUserid() != null) {
            sql.where("s.userid = :userid");
        }
        if (param.getStartTime() != null) {
            sql.where("s.date_id >= :startTime");
        }
        if (param.getEndTime() != null) {
            sql.where("s.date_id <= :endTime");
        }
    }

    public Map<String, UseridDeptMobile> listUser() {
        String sql = "select userid, mobile, get_split_string(department,',', 50) as deptid from t_user where department !=''";
        List<UseridDeptMobile> users = pubDao.queryList(UseridDeptMobile.class, sql);
        Map<String, UseridDeptMobile> map = new HashMap<>();
        for (UseridDeptMobile user : users) {
            map.put(user.getMobile(), user);
        }
        return map;
    }

    /**
     * 获取指定用户的步数，排名情况
     * <p>
     * 一般需要传的参数： userid，dateId，deptid
     *
     * @param param
     * @return
     */
    public UserSport userSport(SportParam param) {
        SQL.SqlBuilder sql = new SQL.SqlBuilder()
                .table("sport_last s")
                .field("s.step_count as stepCount, s.userid as userid")
                .field("u.avatar, u.name as username")
                .field("d.name as deptname")
                .join("user u", "u.userid = s.userid")
                .join("dept d", "u.deptid = d.id")
                .order("s.step_count", Order.Type.DESC)
                .where("s.date_id = :dateId");
       if (param.getDeptid() != null) {
            sql.where("s.deptid = :deptid");
        }
        StringBuilder msql = new StringBuilder();
        msql.append("select a.* from(select obj.*, @rank:=@rank+1 as paiming from(");
        msql.append(sql.build().sql());
        msql.append(") as obj, (select @rank:=0) r) as a where userid = :userid");

        return pubDao.query(msql.toString(), param, UserSport.class);
    }

    /**
     * 获取历史记录
     * <p>
     * 一般需要传参数：userid, startTime, endTime
     *
     * @param param
     * @return
     */
    public List<HistoryList> listHistory(SportParam param) {
        SQL.SqlBuilder sql = new SQL.SqlBuilder()
                .table("sport_last s")
                .field("s.step_count, s.date_id")
                .order("s.date_id");
        buildParam(param, sql);

        return pubDao.queryList(sql.build().sql(), param, HistoryList.class);
    }

    /**
     * 获取排行榜
     * <p>
     * 一般需要传的参数：dateId, deptid
     *
     * @param param
     * @return
     */
    public List<SportList> listSport(SportParam param) {
        SQL.SqlBuilder sql = new SQL.SqlBuilder()
                .table("sport_last s")
                .field("s.step_count, s.userid")
                .field("u.avatar, u.name as username")
                .field("d.name as deptname")
                .join("user u", "u.userid = s.userid")
                .join("dept d", "u.deptid = d.id")
                .order("s.step_count", Order.Type.DESC);
/*                .table("sport_last s")
                .field("s.step_count, s.userid")
                .field("u.avatar, u.name as username")
                .field("d.name as deptname")
                .join("user u", "u.userid = s.userid")
                .join("dept d", "u.deptid = d.id")
                .order("s.step_count", Order.Type.DESC);*/
        buildParam(param, sql);

        return pubDao.queryList(sql.build().sql(), param, SportList.class);
    }

    @Transactional
    public void addSport(List<Sport> sports) {
        try {
            Date now = new Date();
            for (Sport sport : sports) {
                sport.setAddTime(now);
                //获取用户最后记录
                String getSql = "select * from t_sport_last where userid = ? and date_id = ?";
                SportLast last = pubDao.query(SportLast.class, getSql, sport.getUserid(), sport.getDateId());

                //如果当天最后步数与当前步数相同，则更新最后更新时间即可
                if (null != last && last.getLastTime().before(sport.getCreateTime()) && last.getStepCount().equals(sport.getStepCount())) {
                    String updateSport = "update t_sport set create_time = ? where date_id = ? and step_count = ?";
                    pubDao.execute(updateSport, sport.getCreateTime(), sport.getDateId(), sport.getStepCount());
                } else {
                    SQL.SqlBuilder addSqlBuilder = new SQL.SqlBuilder()
                            .field("date_id, num, like_count, create_time, status, step_count, deptid, userid, add_time, name")
                            .value(":dateId, :num, :likeCount, :createTime, :status, :stepCount, :deptid, :userid, :addTime, :name");
                    //保存一个月记录
                    pubDao.executeBean(addSqlBuilder.insert("sport").build().sql(), sport);

                    //所有数据保存在这个表
                    pubDao.executeBean(addSqlBuilder.insert("sport_tmp").build().sql(), sport);
                }

                //保存用户最后记录
                if (last == null) {
                    SQL lastSql = new SQL.SqlBuilder()
                            .field("date_id, userid, last_time, step_count, deptid")
                            .value("?, ?, ?, ?, ?")
                            .insert("sport_last")
                            .build();
                    pubDao.execute(lastSql.sql(), sport.getDateId(), sport.getUserid(),
                            sport.getCreateTime(), sport.getStepCount(), sport.getDeptid());
                } else if (last.getLastTime().before(sport.getCreateTime())) {
                    String updateLast = "update t_sport_last set last_time = ?, step_count = ? where id = ?";
                    pubDao.execute(updateLast, sport.getCreateTime(), sport.getStepCount(), last.getId());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<HistRecord> history_record(Integer dateId) {
        String sql = "select distinct name, date_id, step_count from t_sport \n" +
                " where date_id >= ? \n" +
                " and date_format(create_time, '%T') = '23:59:59'";
        List<NameDateStep> nameDateSteps = pubDao.queryList(NameDateStep.class, sql, dateId);

        List<HistRecord> records = new ArrayList<>();

        Map<String, HistRecord> histRecordMap = new HashMap<>();
        for (NameDateStep step : nameDateSteps) {
            String name = step.getName();
            HistRecord record = histRecordMap.get(name);
            List<DateStep> dateSteps;
            if (record == null) {
                record = new HistRecord();
                record.setName(name);
                dateSteps = new ArrayList<>();
                record.setDateSteps(dateSteps);
                records.add(record);
                histRecordMap.put(name, record);
            } else {
                dateSteps = record.getDateSteps();
            }
            DateStep dateStep = new DateStep();
            dateStep.setDateId(step.getDateId());
            dateStep.setStepCount(step.getStepCount());
            dateSteps.add(dateStep);
        }
        return records;
    }

    public HistoryShow historyShow(String userid) {
        int todayInt = DateFuncs.getIntToday();
        int start = DateFuncs.addDay(todayInt, -7);
        int end = DateFuncs.addDay(todayInt, -1);

        SportParam param = new SportParam();
        param.setStartTime(start);
        param.setEndTime(end);
        param.setUserid(userid);

        SQL.SqlBuilder sql = new SQL.SqlBuilder()
                .table("sport_last s")
                .field("sum(s.step_count) as avgStep")
                .field("(select step_count from t_sport_last where userid=s.userid and date_id = " + todayInt + ") as todayStep");
        buildParam(param, sql);

        String sqlDept = "select deptid from t_sport_last where userid = ?";
        List<Integer> deptId = pubDao.queryList(Integer.class,sqlDept,userid);
        Integer deptid = deptId.get(0);
        param.setDeptid(deptid);
        String sqlNum = "SELECT \n" +
                "  c.userid,\n" +
                "  c.avgStep,\n" +
                "  @rownum := @rownum + 1 AS rank \n" +
                "FROM\n" +
                "  (SELECT \n" +
                "    SUM(s.step_count) AS avgStep,\n" +
                "    s.`userid` \n" +
                "  FROM\n" +
                "    t_sport_last s,\n" +
                "    (SELECT \n" +
                "      @rownum := 0) b \n" +
                "  WHERE s.deptid = ?\n" +
                "    AND s.date_id >= ?\n" +
                "    AND s.date_id <= ?\n" +
                "  GROUP BY s.`userid` \n" +
                "  ORDER BY avgStep DESC) c,\n" +
                "  (SELECT \n" +
                "    @rownum := 0) B ";
        List<HistoryShow> historyShowList = pubDao.queryList(HistoryShow.class,sqlNum,param.getDeptid(),param.getStartTime(),param.getEndTime());

        HistoryShow historyShow = pubDao.query(sql.build().sql(), param, HistoryShow.class);
        for(int i = 0;i<historyShowList.size();i++){
            if(historyShowList.get(i).getUserid().equals(userid)){
                historyShow.setRank(historyShowList.get(i).getRank());
            }
        }
        return historyShow;
    }

    /**
     * 判断某人某天的sport中是否有异常数据
     *
     * @param userid
     * @param dateId
     */
    public boolean hasExcept(String userid, int dateId) {
        //判断今日是否有异常数据
        boolean hasExcept = false;
        String sql1 = "select * from t_sport where userid=? and date_id=?";
        List<Sport> sports = pubDao.queryList(Sport.class, sql1, userid, dateId);
        if (sports != null && sports.size() > 0) {
            for (Sport s : sports) {
                if (s.getStatus() > Sport.STATUS_NORMAL) {
                    hasExcept = true;
                    break;
                }
            }
        }
        return hasExcept;
    }

    @Autowired
    private PubDao pubDao;
}
