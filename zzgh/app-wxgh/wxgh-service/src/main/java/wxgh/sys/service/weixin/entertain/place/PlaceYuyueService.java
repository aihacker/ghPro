package wxgh.sys.service.weixin.entertain.place;

import com.libs.common.data.DateFuncs;
import com.libs.common.data.DateUtils;
import com.libs.common.type.TypeUtils;
import com.weixin.WeixinException;
import com.weixin.api.MsgApi;
import com.weixin.bean.message.Message;
import com.weixin.bean.message.Text;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pub.dao.jdbc.PubDao;
import pub.dao.jdbc.sql.SQL;
import wxgh.app.consts.WeixinAgent;
import wxgh.data.entertain.place.MeYuyue;
import wxgh.data.entertain.place.MeYuyueInfo;
import wxgh.data.entertain.place.yuyue.YuyueDetail;
import wxgh.data.entertain.place.yuyue.YuyueList;
import wxgh.data.place.YuyueInfo;
import wxgh.entity.entertain.place.PlaceYuyue;
import wxgh.param.entertain.place.ApiParam;
import wxgh.param.entertain.place.PlaceParam;
import wxgh.param.entertain.place.YuYueRecordParam;
import wxgh.param.pub.score.ScoreType;
import wxgh.param.pub.score.SimpleScore;
import wxgh.sys.dao.entertain.place.PlaceTimeDao;
import wxgh.sys.dao.entertain.place.PlaceYuyueDao;
import wxgh.sys.service.weixin.pub.score.ScoreService;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * @Author xlkai
 * @Version 2017/1/13
 */
@Service
@Transactional(readOnly = true)
public class PlaceYuyueService {

    private String getQuerySql(ApiParam query) {
        String sql = "";
        if (query.getNumbWeek() == null) {
            if (query.getWeek() != null) {
                sql += ("AND week=" + query.getWeek() + " ");
            }
            if (query.getDateId() != null) {
                sql += ("AND date_id=" + query.getDateId() + " ");
            }
        } else {
            Calendar calendar = Calendar.getInstance();
            calendar.add(Calendar.WEEK_OF_YEAR, -query.getNumbWeek());
            Date today = calendar.getTime();

            int startInt = DateUtils.getFirstWeekDayInt(today);
            int endInt = DateUtils.getLastWeekDayInt(today);

            sql += ("AND date_id >= " + startInt + " AND date_id <= " + endInt + " ");
        }

        if (query.getStatus() != null) {
            sql += ("AND status=" + query.getStatus() + " ");
        }
        if (query.getPlaceId() != null) {
            sql += ("AND place_id=" + query.getPlaceId() + " ");
        }
        if (sql.startsWith("AND")) {
            sql = sql.substring(3, sql.length());
            sql = "WHERE" + sql;
        }
        return sql.trim();
    }

   /* @Override
    public List<liuhe.web.api.bean.YuyueList> getYuyueList(ApiParam apiQuery) {
        String sql = "select t.`week`, CONCAT(t.start_time,'~', t.end_time) as time, t.id,\n" +
                "y.userid, y.`status`, u.`name`, u.mobile, d.name, y.date_id,\n" +
                "s.name as siteName, c.`name` as cateName, p.title as placeName\n" +
                "from t_place_yuyue y\n" +
                "join t_user u on y.userid = u.userid\n" +
                "join t_dept d on u.deptid = d.deptid\n" +
                "join t_place_time t on y.time_id = t.id\n" +
                "join t_place_site s on y.site_id = s.id\n" +
                "join t_place_cate c on t.cate_id = c.id\n" +
                "join t_place p on s.place_id = p.id ";
        sql += getQuerySql(apiQuery);
        return generalDao.queryList(YuyueList.class, sql);
    }*/

    public List<MeYuyue> getUserYuyue(Integer status, String userid) {
        String sql = "select y.id, t.start_time, t.end_time, t.`week`,\n" +
                "s.name as siteName, c.`name` as cateName, p.title as placeName, y.date_id as dateId\n" +
                "from t_place_yuyue y\n" +
                "join t_place_time t on y.time_id = t.id\n" +
                "join t_place_site s on t.site_id = s.id\n" +
                "join t_place_cate c on t.cate_id = c.id\n" +
                "join t_place p on s.place_id = p.id\n" +
                "where y.`status` = ?\n" +
                "and y.userid = ? order by t.week asc";
        List<MeYuyue> meYuyues = generalDao.queryList(MeYuyue.class, sql, status, userid);
        if (meYuyues != null && meYuyues.size() > 0) {
            for (int i = 0; i < meYuyues.size(); i++) {
                try {
                    meYuyues.get(i).setDateId(DateUtils.formatYMD.format(DateUtils.YMD.parse(meYuyues.get(i).getDateId())));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                Integer week=meYuyues.get(i).getWeek()+1;
                if(week==8){
                    week=1;
                }
                meYuyues.get(i).setWeekName(DateUtils.getWeekName(week));
            }
        }
        return meYuyues;
    }

    public List<MeYuyue> getUserYuyue(YuYueRecordParam param) {
        SQL.SqlBuilder sql = new SQL.SqlBuilder()
                .table("t_place_yuyue y")
                .field("y.id, t.start_time, t.end_time, t.week")
                .field("s.name as siteName, c.`name` as cateName, p.title as placeName, y.date_id as dateId")
                .join("t_place_time t", "y.time_id = t.id")
                .join("t_place_site s", "t.site_id = s.id")
                .join("t_place_cate c", "t.cate_id = c.id")
                .join("t_place p", "s.place_id = p.id")
                .where("y.status = :status")
                .where("y.userid = :userid")
                .order("t.week");

        List<MeYuyue> meYuyues = generalDao.queryPage(sql, param, MeYuyue.class);
        if (meYuyues != null && meYuyues.size() > 0) {
            for (int i = 0; i < meYuyues.size(); i++) {
                try {
                    meYuyues.get(i).setDateId(DateUtils.formatYMD.format(DateUtils.YMD.parse(meYuyues.get(i).getDateId())));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                //zxc 注释 最后状态 服务器ok 代码ok
//                Integer week=meYuyues.get(i).getWeek()+1;
                Integer week=meYuyues.get(i).getWeek();
                if(week==8){
                    week=1;
                }
                meYuyues.get(i).setWeekName(DateUtils.getWeekName(week));
            }
        }
        return meYuyues;
    }

    public List<MeYuyueInfo> getYuyueInfo(Integer status, String userid) {
        String sql = "select y.id, t.start_time, t.end_time, t.`week`,\n" +
                "s.name as siteName, c.`name` as cateName, p.title as placeName,\n" +
                "p.lat, p.lng, p.address, p.price\n" +
                "from t_place_yuyue y\n" +
                "join t_place_time t on y.time_id = t.id\n" +
                "join t_place_site s on t.site_id = s.id\n" +
                "join t_place_cate c on t.cate_id = c.id\n" +
                "join t_place p on s.place_id = p.id\n" +
                "where y.`status` = ?\n" +
                "and y.userid = ? order by t.week asc";
        List<MeYuyueInfo> meYuyues = generalDao.queryList(MeYuyueInfo.class, sql, status, userid);
        if (meYuyues != null && meYuyues.size() > 0) {
            for (int i = 0; i < meYuyues.size(); i++) {
                meYuyues.get(i).setWeekName(DateUtils.getWeekName(meYuyues.get(i).getWeek()));
            }
        }
        return meYuyues;
    }

    @Transactional
    public void saveYuyue(PlaceYuyue placeYuyue) {
        placeYuyueDao.save(placeYuyue);
    }

    @Transactional
    public void saveList(List<PlaceYuyue> placeYuyues) {
        for (PlaceYuyue placeYuyue : placeYuyues) {
            placeYuyueDao.save(placeYuyue);
        }
    }

    @Transactional
    public void delteYuyue(Integer id) {
        PlaceYuyue yuyue = placeYuyueDao.get(id);
        if (yuyue != null) {

            //更新预约时间的状态
            String timeSql = "update t_place_time set status=? where id = ?";
            generalDao.execute(timeSql, 0, yuyue.getTimeId());

            //删除用户的预约
            placeYuyueDao.delete(id);
        }
    }

    @Transactional
    public void delteYuyue(Integer week, Integer id) {
        PlaceYuyue yuyue = placeYuyueDao.get(id);
        if (yuyue != null) {

            if(week == null)
                week = 1;
            // 获取本周 true单  false双
            boolean isSingle = com.libs.common.data.DateUtils.isSingleWeek();
            // 1 为本周 2 位下周
            if(!week.equals(1))
                isSingle = !isSingle;

            //更新预约时间的状态
            String timeSql = isSingle ? "update t_place_time set d_status=? where id = ?" : "update t_place_time set s_status=? where id = ?";
            generalDao.execute(timeSql, 0, yuyue.getTimeId());

            //删除用户的预约
            placeYuyueDao.delete(id);
        }
    }

    /**
     * @param id     若type==1，为t_place_yuyue表ID，若type==2，为t_place_time表ID
     * @param type
     * @param reason
     */
    @Transactional
    public void delteYuyueSe(Integer id, Integer type, String reason) {
        Integer timeId;
        PlaceYuyue yuyue;
        if (type == 1) {
            yuyue = placeYuyueDao.get(id);
            timeId = yuyue.getTimeId();
        } else {
            timeId = id;
            yuyue = getWeekYuyueByTime(timeId, 1);
        }

        //更新预约时间的状态
        String timeSql = "update t_place_time set status=? where id = ?";
        generalDao.execute(timeSql, 0, timeId);

        if (yuyue != null) {
            //删除用户的预约
            placeYuyueDao.delete(id);

            //返回积分
            if (yuyue.getPayType().equals(1)) {
                SimpleScore simpleScore = new SimpleScore();
                simpleScore.setUserid(yuyue.getUserid());
                //1的状态是获得
                simpleScore.setStatus(1);
                simpleScore.setScore((float) yuyue.getPayPrice().doubleValue());
                simpleScore.setInfo("自行取消预约，返回" + yuyue.getPayPrice() + "积分");
                scoreService.user(simpleScore, ScoreType.PLACE);
            }

            //通知用户
            String str;
            if (yuyue.getPayType() == 1) {
                str = "您好，你的预约已被取消,已返回" + yuyue.getPayPrice() + "积分";
            } else {
                str = "您好，你的预约已被取消";
            }
            if (!TypeUtils.empty(reason)) {
                str += "，取消原因：" + reason;
            }

            Text text = new Text(str);
            Message<Text> message = new Message<Text>(WeixinAgent.AGENT_YUYUE, text);
            message.addUser("18402028708");
//            message.addUser(yuyue.getUserid());
            try {
                MsgApi.send(message);
            } catch (WeixinException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * @param id     若type==1，为t_place_yuyue表ID，若type==2，为t_place_time表ID
     * @param type
     * @param reason
     */
    @Transactional
    public void delteYuyueSe(Integer week, Integer id, Integer type, String reason) {
        Integer timeId;
        PlaceYuyue yuyue;
        if (type == 1) {
            yuyue = placeYuyueDao.get(id);
            timeId = yuyue.getTimeId();
        } else {
            timeId = id;
            yuyue = getWeekYuyueByTime(timeId, 1);
        }

        if(week == null){
            // 查询日期是全年的第几周-今天是全年的第几周+1 判断是否为本周 2018-06-25 zxc：因为周日算下周，通过日期回推一天将周日列为本周处理
            String checkWeek = "select (week(date_sub(?,interval 1 day)))-(week(now())) + 1";
            week = generalDao.query(Integer.class, checkWeek, yuyue.getDateId());
        }

        // 获取本周 true单  false双
        boolean isSingle = com.libs.common.data.DateUtils.isSingleWeek();
        // 1 为本周 2 位下周
        if(!week.equals(1))
            isSingle = !isSingle;

        //更新预约时间的状态
        String timeSql = isSingle ? "update t_place_time set d_status=? where id = ?" : "update t_place_time set s_status=? where id = ?"; ;
        generalDao.execute(timeSql, 0, timeId);

        if (yuyue != null) {
            //删除用户的预约
//            placeYuyueDao.delete(id);

            Integer firstWeekDay = null;
            Integer lastWeekDay = null;
            // 本周
            if(week == 1) {
                Date nowDate = new Date();
                firstWeekDay = DateUtils.getFirstWeekDayInt(nowDate);
                lastWeekDay = DateUtils.getLastWeekDayInt(nowDate);
            }else{   // 下周
                Date nowDate = DateFuncs.addDay(new Date(), 7);
                firstWeekDay = DateUtils.getFirstWeekDayInt(nowDate);
                lastWeekDay = DateUtils.getLastWeekDayInt(nowDate);
            }

            String sql = "delete from t_place_yuyue where time_id = ? AND date_id >= ? AND date_id <= ?";
            generalDao.execute(sql, timeId, firstWeekDay, lastWeekDay);

            //返回积分
            if (yuyue.getPayType() == 1) {
                SimpleScore simpleScore = new SimpleScore();
                simpleScore.setUserid(yuyue.getUserid());
                //1的状态是获得
                simpleScore.setStatus(1);
                simpleScore.setScore((float) yuyue.getPayPrice().doubleValue());
                simpleScore.setInfo("自行取消预约，返回" + yuyue.getPayPrice() + "积分");
                scoreService.user(simpleScore, ScoreType.PLACE);
            }

            //通知用户
            String str;
            if (yuyue.getPayType() == 1) {
                str = "您好，你的预约已被取消,已返回" + yuyue.getPayPrice() + "积分";
            } else {
                str = "您好，你的预约已被取消";
            }
            if (!TypeUtils.empty(reason)) {
                str += "，取消原因：" + reason;
            }

            Text text = new Text(str);
            Message<Text> message = new Message<Text>(WeixinAgent.AGENT_YUYUE, text);
//            message.addUser("18402028708");
            message.addUser(yuyue.getUserid());
            try {
                MsgApi.send(message);
            } catch (WeixinException e) {
                e.printStackTrace();
            }
        }
    }

    public List<PlaceYuyue> getList(PlaceParam placeQuery) {
        return placeYuyueDao.getList(placeQuery);
    }

    public Integer getCount(PlaceParam placeQuery) {
        return placeYuyueDao.getCount(placeQuery);
    }

    @Transactional
    public int updateStatus(Integer yuyueId, Integer status) {
        String sql = "update t_place_yuyue set status = ? where id = ?";

        return generalDao.execute(sql, status, yuyueId);
    }

    public List<Integer> getTimeOutYuyue() {
        int dateId = DateFuncs.getIntToday();
        String time = DateFuncs.dateTimeToStr(new Date(), "HH:mm");
        String sql = "select y.id from t_place_yuyue y\n" +
                "join t_place_time t on y.time_id = t.id\n" +
                "where y.date_id < ?\n" +
                "or (y.date_id = ? and t.start_time <= ?)";
        return generalDao.queryList(Integer.class, sql, dateId, dateId, time);
    }

    @Transactional
    public Integer delYuyue(Integer id) {
        placeYuyueDao.delYuyue(id);
        return -1;
    }

    public Integer countDay(String userid) {
        Integer todayInt = DateFuncs.getIntToday();
        String sql = "select count(*) from t_place_yuyue where userid = ?\n" +
                "and date_id = ?";
        Integer count = generalDao.query(Integer.class, sql, userid, todayInt);
        return count == null ? 0 : count;
    }

    public Integer countMonth(String userid) {
        Date today = new Date();
        Integer start = DateUtils.getFirstMonthDayInt(today);
        Integer end = DateUtils.getLastMonthDayInt(today);

        Integer count = count(userid, start, end);
        return count == null ? 0 : count;
    }

    public Integer countWeek(String userid) {
        Date today = new Date();
        Integer start = DateUtils.getFirstWeekDayInt(today);
        Integer end = DateUtils.getLastWeekDayInt(today);

        Integer count = count(userid, start, end);
        return count == null ? 0 : count;
    }

    public List<wxgh.data.entertain.place.YuyueList> getYuyueList(ApiParam apiParam) {
        String sql = "select t.`week`, CONCAT(t.start_time,'~', t.end_time) as time, t.id,\n" +
                "y.userid, y.`status`, u.`name`, u.mobile, d.name as deptname, y.date_id,\n" +
                "s.name as siteName, c.`name` as cateName, p.title as placeName\n" +
                "from t_place_yuyue y\n" +
                "join t_user u on y.userid = u.userid\n" +
                "join t_dept d on u.deptid = d.id\n" +
                "join t_place_time t on y.time_id = t.id\n" +
                "join t_place_site s on y.site_id = s.id\n" +
                "join t_place_cate c on t.cate_id = c.id\n" +
                "join t_place p on s.place_id = p.id ";
        sql += getQuerySql(apiParam);
        return generalDao.queryList(wxgh.data.entertain.place.YuyueList.class, sql);
    }

    public List<YuyueList> getYuyueList(Integer placeId, Integer cateId) {
        Date today = new Date();
        today = new Date(today.getTime()-86400000L);
        Integer start = DateUtils.getFirstWeekDayInt(today);
        Integer end = DateUtils.getLastWeekDayInt(today);

        String sql = "SELECT y.pay_type AS payType, y.site_id, y.time_id, y.userid, y.`status`, s.`name`, t.start_time, t.end_time, t.`week`,u.name as username,u.mobile, d.name FROM t_place_yuyue y\n" +
                "join t_place_site s on y.site_id = s.id\n" +
                "join t_place_time t on y.time_id = t.id\n" +
                "join t_user u on y.userid = u.userid\n" +
                "left join t_dept d on u.deptid = d.id\n" +
                "where s.place_id = ? and s.cate_id = ?\n" +
                "and y.date_id >= ? and y.date_id <= ?";
        return generalDao.queryList(YuyueList.class, sql,
                placeId, cateId, start, end);
    }

    public YuyueDetail getYuyueDetail(Integer id) {
        String sql = "select y.id, t.`week`, t.start_time, t.end_time, s.`name` as siteName, c.`name` as cateName, p.title as placeName, " +
                "y.userid, y.pay_type, y.pay_price, y.status, y.time_id from t_place_yuyue y\n" +
                "join t_place_time t on y.time_id = t.id\n" +
                "join t_place_site s on y.site_id = s.id\n" +
                "join t_place_cate c on s.cate_id = c.id\n" +
                "join t_place p on s.place_id = p.id\n" +
                "where y.id = ?";
        return generalDao.query(YuyueDetail.class, sql, id);
    }

    public PlaceYuyue getWeekYuyueByTime(Integer timeId, Integer status) {
        String sql = "select * from t_place_yuyue\n" +
                "where time_id = ? and status = ?\n" +
                "AND date_id >= ? AND date_id <=?";
        Date today = new Date();
        int startInt = DateUtils.getFirstWeekDayInt(today);
        int endInt = DateUtils.getLastWeekDayInt(today);
        return generalDao.query(PlaceYuyue.class, sql, timeId, status, startInt, endInt);
    }

    /**
     * 查询用户指定时间段预约次数
     *
     * @param userid
     * @param start
     * @param end
     * @return
     */
    private Integer count(String userid, Integer start, Integer end) {
        String sql = "select count(*) from t_place_yuyue where userid = ?\n" +
                "and date_id >= ? and date_id <=?";
        return generalDao.query(Integer.class, sql, userid, start, end);
    }

    /**
     * 根据ID查询预约信息
     *
     * @param timeId
     * @return
     */
    public YuyueInfo getYuyueInfoById(Integer timeId) {
        Date nowDate = new Date();
        Integer firstWeekDay = DateUtils.getFirstWeekDayInt(nowDate);
        Integer lastWeekDay = DateUtils.getLastWeekDayInt(nowDate);
        SQL sql = new SQL.SqlBuilder()
                .select("t_place_yuyue as y")
                .field("y.id,y.time_id,y.userid,u.name as username,(SELECT d.name FROM t_dept as d WHERE d.id = u.deptid) as deptname,u.mobile,y.pay_type,y.pay_price,y.add_time")
                .join("t_user as u", "u.userid = y.userid")
                .where("y.time_id = ? AND y.date_id >= ? AND y.date_id <= ? AND y.status = '1'").build();
        return generalDao.query(YuyueInfo.class, sql.sql(), timeId, firstWeekDay, lastWeekDay);
    }

    /**
     * 根据ID查询预约信息
     * @param week
     * @param timeId
     * @return
     */
    public YuyueInfo getYuyueInfoById(Integer week, Integer timeId) {
        Integer firstWeekDay = null;
        Integer lastWeekDay = null;
        // 本周
        if(week == 1) {
            Date nowDate = new Date();
            firstWeekDay = DateUtils.getFirstWeekDayInt(nowDate);
            lastWeekDay = DateUtils.getLastWeekDayInt(nowDate);
        }else{   // 下周
            Date nowDate = DateFuncs.addDay(new Date(), 7);
            firstWeekDay = DateUtils.getFirstWeekDayInt(nowDate);
            lastWeekDay = DateUtils.getLastWeekDayInt(nowDate);
        }
        SQL sql = new SQL.SqlBuilder()
                .select("t_place_yuyue as y")
                .field("y.id,y.time_id,y.userid,u.name as username,(SELECT d.name FROM t_dept as d WHERE d.id = u.deptid) as deptname,u.mobile,y.pay_type,y.pay_price,y.add_time")
                .join("t_user as u", "u.userid = y.userid")
                .where("y.time_id = ? AND y.date_id >= ? AND y.date_id <= ? AND y.status = '1'").build();
        return generalDao.query(YuyueInfo.class, sql.sql(), timeId, firstWeekDay, lastWeekDay);
    }

    @Autowired
    private PlaceYuyueDao placeYuyueDao;

    @Autowired
    private PubDao generalDao;

    @Autowired
    private PlaceTimeDao placeTimeDao;

    @Autowired
    private ScoreService scoreService;

}
