package wxgh.sys.service.weixin.entertain.nanhai.place;


import com.libs.common.data.DateFuncs;
import com.libs.common.data.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pub.dao.jdbc.PubDao;
import pub.dao.jdbc.sql.SQL;
import pub.dao.jdbc.sql.bean.Order;
import pub.dao.jdbc.sql.bean.Where;
import pub.utils.TypeUtils;
import wxgh.app.consts.WeixinAgent;
import wxgh.app.sys.api.NanHaiPlaceSchedule;
import wxgh.app.sys.api.PlaceSchedule;
import wxgh.app.sys.task.PushAsync;
import wxgh.data.entertain.place.SelectTime;
import wxgh.data.entertain.place.yuyue.TimeIdMap;
import wxgh.data.pub.push.Push;
import wxgh.entity.entertain.nanhai.place.NanHaiPlaceTime;
import wxgh.entity.entertain.nanhai.place.NanHaiPlaceYuyue;
import wxgh.entity.entertain.place.PlaceTime;
import wxgh.entity.entertain.place.PlaceYuyue;
import wxgh.param.entertain.place.PlaceParam;
import wxgh.param.entertain.place.TimeParam;
import wxgh.param.pub.score.ScoreType;
import wxgh.param.pub.score.SimpleScore;
import wxgh.sys.dao.entertain.nanhai.place.NanHaiPlaceTimeDao;
import wxgh.sys.dao.entertain.place.PlaceTimeDao;
import wxgh.sys.service.weixin.pub.score.ScoreService;

import java.util.*;

/**
 * @Author xlkai
 * @Version 2017/1/12
 */
@Service
@Transactional(readOnly = true)
public class NanHaiPlaceTimeService {

    @Autowired
    private PushAsync pushAsync;

    @Autowired
    private ScoreService scoreService;

    @Transactional
    public void updatePlaceTimeClose(String ids, Boolean isSingle, String dateId){
        System.out.println("ids:" + ids);
        if(com.libs.common.type.TypeUtils.empty(ids))
            return ;
        String sql = isSingle ? "update t_nanhai_place_time set d_status = 4 where id in ( " +ids +")" : "update t_nanhai_place_time set s_status = 4 where id in ( " +ids +")";
        generalDao.execute(sql);

        // 给予积分预约的用户退回  （条件：积分支付，预约成功）
        String ssql = "select * from t_nanhai_place_yuyue where date_id = ? and time_id in ("+ids+") and pay_type = 1 and status = 1";
        List<NanHaiPlaceYuyue> list = generalDao.queryList(NanHaiPlaceYuyue.class, ssql, dateId);
        // 更新预约状态
        String updateSql = "update t_nanhai_place_yuyue set status = 4 where date_id = ? and time_id in ("+ids+") and pay_type = 1 and status = 1";
        generalDao.execute(updateSql, dateId);

        // 返回积分
        for (NanHaiPlaceYuyue placeYuyue : list){
            SimpleScore simpleScore = new SimpleScore();
            simpleScore.setUserid(placeYuyue.getUserid());
            //1的状态是获得
            simpleScore.setStatus(1);
            simpleScore.setScore((float) placeYuyue.getPayPrice().doubleValue());
            simpleScore.setInfo("场馆关闭，强行取消预约，返回" + placeYuyue.getPayPrice() + "积分");
            scoreService.user(simpleScore, ScoreType.PLACE);

            //通知用户
            String info = "您好，因场馆关闭，你的预约已被取消,已返回" + placeYuyue.getPayPrice() + "积分";

            Push push = new Push();
            List<String> tousers = new ArrayList<>();
            tousers.add(placeYuyue.getUserid());
            push.setTousers(tousers);
            push.setAgentId(WeixinAgent.NANHAI_AGENT_YUYUE);
            pushAsync.sendByPlaceClose(info, push);
        }

    }

    /**
     * 获取指定星期的时间段场地
     * @param week
     * @param timeId
     * @return
     */
    public List<NanHaiPlaceTime> getWeekTimeList(Integer week, Integer placeId, Integer cateId, String timeId){
        SQL.SqlBuilder sql = new SQL.SqlBuilder()
                .table("nanhai_place_time t")
                .field("t.*")
                .join("t_nanhai_place_site s","s.id = t.site_id")
                .where("s.place_id = ?")
                .where("s.cate_id = ?", Where.Logic.AND)
                .where("t.week = ?", Where.Logic.AND);
        List<String> timeList = TypeUtils.strToList(timeId);
        if(TypeUtils.empty(timeList))
            return null;

        // 以开场时间为分界线
        // startTime小于12点为上午场
        // startTime小于18点大于等于12点为下午场
        // startTime大于等于18点为晚上场
        List<String> sqls = new ArrayList<>();
        for (String t : timeList){
            if(t.equals("1"))
                sqls.add("t.start_time < '12:00'");
            else if(t.equals("2"))
                sqls.add("(t.start_time >= '12:00' and t.start_time < '18:00')");
            else if(t.equals("3"))
                sqls.add("t.start_time >= '18:00'");
        }
//        (start_time < '12:00' OR (start_time >= '12:00' and start_time < '18:00') OR (start_time >= '18:00'))


        return generalDao.queryList(NanHaiPlaceTime.class, sql.select().build().sql() + " AND ("+TypeUtils.listToString(sqls, " OR ")+")", placeId, cateId, week);
    }

    /**
     * 定时任务业务
     *
     * @param id
     * @param isSingle true单周  false双周
     */
    @Transactional
    public void placeExpire(Integer id, Boolean isSingle) {
        // 更新场馆信息
        String sql = null;
        if (isSingle)
            sql = "update t_nanhai_place_time set d_status=? where id = ? and d_status != ?";
        else
            sql = "update t_nanhai_place_time set s_status=? where id = ? and s_status != ?";
        generalDao.execute(sql, 2, id, 3);

        // 更新预约信息
        String date = DateUtils.formatDate(new Date(), "yyyyMMdd");
        String sql2 = "update t_nanhai_place_yuyue set status = 3 where status = 1 and date_id = ? and time_id = ?";
        generalDao.execute(sql2, date, id);
    }

    public List<NanHaiPlaceTime> getTimes(TimeParam query) {
        return placeTimeDao.getTimes(query);
    }

    @Transactional
    public void updateStatus(Integer id, Integer status) {
        String sql = "update t_nanhai_place_time set status=? where id = ? and status != ?";
        generalDao.execute(sql, status, id, status);
    }

    // 更新状态, 1 单周 , 0 双周
    @Transactional
    public void updateStatus(Integer id, Integer status, Integer isSingle) {
        String sql = null;
        if (isSingle == 1)
            sql = "update t_nanhai_place_time set d_status=? where id = ? and d_status != ?";
        else
            sql = "update t_nanhai_place_time set s_status=? where id = ? and s_status != ?";
        generalDao.execute(sql, status, id, status);
    }

    @Transactional
    public void updateStatus(List<Integer> ids, Integer status) {
        if (TypeUtils.empty(ids)) {
            return;
        }
        String sql = "update t_nanhai_place_time set status=? where status != ? and status != 3 and id in (" + TypeUtils.listToStr(ids) + ")";
        generalDao.execute(sql, status, status);
    }

    // 增加单双周更新状态
    @Transactional
    public void updateStatus(List<Integer> ids, Integer status, boolean isSingle) {
        if (TypeUtils.empty(ids) || ids.size() == 0) {
            return;
        }
        String sql = "";
        if (isSingle)
            sql = "update t_nanhai_place_time set d_status=? where d_status != 3 and id in (" + TypeUtils.listToStr(ids) + ")";
        else
            sql = "update t_nanhai_place_time set s_status=? where s_status != 3 and id in (" + TypeUtils.listToStr(ids) + ")";
        generalDao.execute(sql, status);

        // 更新预约信息
        String date = DateUtils.formatDate(new Date(), "yyyyMMdd");
        String sql2 = "update t_nanhai_place_yuyue set status = 3 where status = 1 and date_id = ? and time_id in (" + TypeUtils.listToStr(ids) + ")";
        generalDao.execute(sql2, date);
    }

    @Transactional
    public void updateStatusNot(List<Integer> ids, Integer status) {
        if (TypeUtils.empty(ids)) {
            return;
        }
        String sql = "update t_nanhai_place_time set status=? where status != ? and id not in (" + TypeUtils.listToStr(ids) + ")";
        generalDao.execute(sql, status, status);
    }

    @Transactional
    public void updateWeekStatus(Integer week, Integer status) {
        String sql = "update t_nanhai_place_time set status=? where week=? and status != 3";
        generalDao.execute(sql, status, week);
    }

    // 新增，单双周
    @Transactional
    public void updateWeekStatus(Integer status, boolean isSingle) {
        Integer w = isSingle ? 1 : 0;

        // 查询数据库
        String sql_val = "select val from t_nanhai_sys_val where name = ?";
        String weekVal = generalDao.query(String.class, sql_val, NanHaiPlaceSchedule.THIS_WEEK);
        // 如果记录不存在则插入
        if (weekVal == null) {
            String insertSql = "insert into t_nanhai_sys_val(name, val) values(?, ?)";
            generalDao.execute(insertSql, NanHaiPlaceSchedule.THIS_WEEK, w);
            weekVal = generalDao.query(String.class, sql_val, NanHaiPlaceSchedule.THIS_WEEK);
        }

        // 如果记录存在，判断记录值与当前值是否相等
        Integer weekValInteger = Integer.valueOf(weekVal);

        // 如果相等则是同一周，不执行
        if (w.equals(weekValInteger)) {
            System.out.println("same week");
            return;
        }

        System.out.println("更新上一周数据");

        String updateVal = "update t_nanhai_sys_val set val = ? where name = ?";
        generalDao.execute(updateVal, w, NanHaiPlaceSchedule.THIS_WEEK);

        String sql = null;
        // 如果本周是单周，则更新双周 s_status 如果本周是双周，则更新单周 d_status
        if (!isSingle)
            sql = "update t_nanhai_place_time set d_status=? where d_status != 3";
        else
            sql = "update t_nanhai_place_time set s_status=? where s_status != 3";
        generalDao.execute(sql, status);

    }

    public NanHaiPlaceTime getPlaceTime(Integer id) {
        return placeTimeDao.get(id);
    }

    public NanHaiPlaceTime getPriceTime(Integer id) {
        String sql = "select t.*, s.price, s.score from t_nanhai_place_time t\n" +
                "join t_nanhai_place_site s on t.site_id = s.id where t.id=?";
        return generalDao.query(NanHaiPlaceTime.class, sql, id);
    }

    // 新增, 区分单双周
    public NanHaiPlaceTime getPriceTime(Integer id, Integer isSingle) {
        SQL.SqlBuilder sql = new SQL.SqlBuilder()
                .table("t_nanhai_place_time t")
                .field("t.*, s.price, s.score")
                .join("t_nanhai_place_site s", "t.site_id = s.id")
                .where("t.id=?")
                .select();

        if (isSingle == 1)
            sql.field("d_status as status");
        else
            sql.field("s_status as status");

        return generalDao.query(NanHaiPlaceTime.class, sql.build().sql(), id);
    }

    public List<Integer> getTimeOutTime() {

        int week = DateUtils.getWeek(new Date());
        String startTime = DateUtils.formatDate(new Date(), "HH:mm");

        String sql = "select id from t_nanhai_place_time\n" +
                "where (week < ? or (week = ? and start_time <= ?))";

        return generalDao.queryList(Integer.class, sql, week, week, startTime);
    }

    public List<Integer> getSunDayOutTime() {
        String startTime = DateUtils.formatDate(new Date(), "HH:mm");

        String sql = "select id from t_nanhai_place_time\n" +
                "where week = 7 and start_time <= ?";
        return generalDao.queryList(Integer.class, sql, startTime);
    }

    @Transactional
    public int updateStatusAll(Integer status) {
        String sql = "update t_nanhai_place_time set status=?";
        return generalDao.execute(sql, status);
    }

    @Transactional
    public void saveTime(NanHaiPlaceTime placeTime) {
        placeTimeDao.save(placeTime);
    }

    @Transactional
    public void del(Integer id) {
        placeTimeDao.delete(id);

        //删除固定场
        String sql = "delete from t_nanhai_place_guding where time_id = ?";
        generalDao.execute(sql, id);
    }

    public List<SelectTime> getTime(Integer placeId, Integer cateId, Integer week) {
        String sql = "select DISTINCT start_time,end_time from t_nanhai_place_time t\n" +
                "join t_nanhai_place_site s on t.site_id = s.id\n" +
                "where s.place_id = ? and s.cate_id = ?\n" +
                "and week = ? order by t.start_time ASC";
        return generalDao.queryList(SelectTime.class, sql, placeId, cateId, week);
    }
//
//    // 改
//    public List<SelectTime> getTime(TimeParam param){
//        SQL.SqlBuilder sql = new SQL.SqlBuilder()
//                .table("t_place_time t")
//                .field("DISTINCT start_time,end_time")
//                .join("join t_place_site s", "t.site_id = s.id")
//                .where("s.place_id = :placeId")
//                .where("s.cate_id = :cateId")
//                .where("week = :week")
//                .order("t.start_time", Order.Type.ASC)
//                .select();
//        if(param.getIsSingle() == 1)
//            sql.where("d_status = :isSingle");
//    }

    // 改  List<SelectTime> getTime(Integer siteId, Integer week)
    public List<SelectTime> getTimes(Integer siteId, Integer week, Integer isSingle) {
        SQL.SqlBuilder sql = new SQL.SqlBuilder()
                .table("t_nanhai_place_time")
                .field("start_time, end_time, id as timeId, time_type as type")
                .where("site_id=?")
                .where("week=?")
                .order("start_time", Order.Type.ASC)
                .select();
        if (isSingle == 1)
            sql.field("d_status as status");  //下周
        else
            sql.field("s_status as status"); //本周
        return generalDao.queryList(SelectTime.class, sql.build().sql(), siteId, week);
    }

    public List<SelectTime> getTime(Integer siteId, Integer week) {
        String sql = "select start_time, end_time, status, id as timeId, time_type as type from t_nanhai_place_time where site_id=? and week=? ORDER BY start_time asc";
        return generalDao.queryList(SelectTime.class, sql, siteId, week);
    }

    public List<NanHaiPlaceTime> getPlaceTimes(Integer placeId, Integer cateId) {
        SQL.SqlBuilder sql = new SQL.SqlBuilder()
                .table("t_nanhai_place_time t")
                .field("t.*")
                .join("t_nanhai_place_site s", "t.site_id = s.id")
                .where("s.place_id = ? and t.cate_id = ?");
//        String sql = "select t.* from t_place_time t\n" +
//                "join t_place_site s on t.site_id = s.id\n" +
//                "where s.place_id = ? and t.cate_id = ?";
        return generalDao.queryList(NanHaiPlaceTime.class, sql.select().build().sql(), placeId, cateId);
    }

    public Map<String, Integer> getTimeIdMap(Integer placeId, Integer cateId) {
        String sql = "select CONCAT(t.`week`,'-', t.start_time, '-', t.end_time, '-',  s.id) as mk, t.id as val from t_nanhai_place_time t\n" +
                "join t_nanhai_place_site s on t.site_id = s.id\n" +
                "where s.place_id=? and s.cate_id=?";
        List<TimeIdMap> timeIdMaps = generalDao.queryList(TimeIdMap.class, sql, placeId, cateId);
        Map<String, Integer> map = new HashMap<>();
        for (TimeIdMap id : timeIdMaps) {
            map.put(id.getMk(), id.getVal());
        }
        return map;
    }

    /**
     * 通过ID修改状态
     *
     * @param id
     * @param state
     * @return
     */
    @Transactional
    public void changeStatusById(Integer id, Integer state) {
        NanHaiPlaceTime placeTime = placeTimeDao.get(id);
        placeTime.setStatus(state);
        SQL sql = new SQL.SqlBuilder()
                .update("t_nanhai_place_time t")
                .set("t.site_id = :siteId,t.cate_id = :cateId,t.start_time = :startTime,t.end_time = :endTime,t.week = :week,t.status = :status,t.time_type = :timeType")
                .where("t.id = :id").build();
        generalDao.executeBean(sql.sql(), placeTime);
    }

    @Transactional
    public void changeStatusById(Integer week, Integer id, Integer state) {
        NanHaiPlaceTime placeTime = placeTimeDao.get(id);
        placeTime.setStatus(state);

        if(week == null)
            week = 1;
        // 获取本周 true单  false双
        boolean isSingle = DateUtils.isSingleWeek();
        // 1 为本周 2 位下周
        if(!week.equals(1))
            isSingle = !isSingle;
        SQL.SqlBuilder sql = new SQL.SqlBuilder()
                .update("t_nanhai_place_time t")
                .set("t.site_id = :siteId,t.cate_id = :cateId,t.start_time = :startTime,t.end_time = :endTime,t.week = :week,t.time_type = :timeType")
                .where("t.id = :id");
        if(isSingle)
            sql.set("t.d_status = :status");
        else
            sql.set("t.s_status = :status");
        generalDao.executeBean(sql.build().sql(), placeTime);
    }

    // -------------------------------------------------

    /**
     * 获取预约列表
     * @param query
     * @return
     */
    public List<NanHaiPlaceYuyue> getYuYueList(PlaceParam query){

        SQL.SqlBuilder sql = new SQL.SqlBuilder().field("y.*,s.`name` AS siteName,u.mobile, u.`name` AS userName," +
                "t.`start_time` AS startTime, t.`end_time` AS endTime, t.`week` AS week," +
                "pc.name AS cateName," +
                "p.`title` AS placeTitle")
                .field("y.date_id as dateId")
                .table("nanhai_place_yuyue y")
                .join("nanhai_place_site s","y.site_id=s.id")
                .join("user u","y.userid = u.userid")
                .join("nanhai_place_time t","t.id=y.time_id")
                .join("nanhai_place_cate pc","pc.id=s.cate_id")
                .join("nanhai_place p","p.id=s.place_id")
                .order("y.add_time", Order.Type.DESC)
                .where("y.status = :status");

        if(query.getIsThis() != null){
            Date mondayDate = DateUtils.getFirstWeekDay(new Date()); //周一
            Date start = null;
            Date end = null;

            if(query.getIsThis().equals(1)){
                start = mondayDate;
                end = DateFuncs.addDay(mondayDate, 7);
            } else if(query.getIsThis().equals(2)){
                start = DateFuncs.addDay(mondayDate, 7);
                end = DateFuncs.addDay(mondayDate, 14);
            }
            if(start != null && end != null){
                String s = DateFuncs.dateTimeToStr(start, "yyyyMMdd");
                String e = DateFuncs.dateTimeToStr(end, "yyyyMMdd");
                sql.where("y.date_id >= " + s  + " and y.date_id < " + e);
            }
        }

        List<NanHaiPlaceYuyue> list = generalDao.queryPage(sql,query,NanHaiPlaceYuyue.class);

        return list;
    }

    @Autowired
    private NanHaiPlaceTimeDao placeTimeDao;

    @Autowired
    private PubDao generalDao;
}
