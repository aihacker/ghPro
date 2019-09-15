package wxgh.sys.service.weixin.union.woman;

import com.libs.common.data.DateUtils;
import com.libs.common.type.TypeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pub.dao.jdbc.PubDao;
import pub.dao.jdbc.sql.SQL;
import pub.dao.jdbc.sql.bean.Order;
import pub.error.ValidationError;
import pub.functions.DateFuncs;
import wxgh.app.session.user.UserSession;
import wxgh.data.common.FileData;
import wxgh.data.union.woman.mom.*;
import wxgh.entity.union.woman.MomTime;
import wxgh.entity.union.woman.WomanMom;
import wxgh.entity.union.woman.WomanMomData;
import wxgh.entity.union.woman.WomanMomTime;
import wxgh.param.union.woman.MomParam;
import wxgh.param.union.woman.MomYuyueParam;
import wxgh.param.union.woman.YuyueParam;

import java.util.*;

/**
 * Created by Administrator on 2017/9/14.
 */
@Service
@Transactional(readOnly = true)
public class MomService {

    public List<YuyueList> yuyueList(Integer id) {
        Integer dateId = DateFuncs.getIntToday();
        SQL sql = new SQL.SqlBuilder()
                .table("woman_mom_yuyue y")
                .field("u.name as username")
                .field("y.week, concat(y.start_time, '-', y.end_time) as time, y.dateid")
                .join("user u", "y.userid = u.userid")
                .where("y.dateid >= ?")
                .where("y.mom_id = ?")
                .order("y.dateid")
                .order("y.start_time")
                .build();
        List<YuyueTmp> yuyueTmps = pubDao.queryList(YuyueTmp.class, sql.sql(), dateId, id);
        Map<Integer, List<YuyueTmp>> map = new HashMap<>();
        for (YuyueTmp yuyueTmp : yuyueTmps) {
            List<YuyueTmp> tmps = map.get(yuyueTmp.getWeek());
            if (tmps == null) {
                tmps = new ArrayList<>();
                tmps.add(yuyueTmp);
                map.put(yuyueTmp.getWeek(), tmps);
            } else {
                map.get(yuyueTmp.getWeek()).add(yuyueTmp);
            }
        }
        yuyueTmps.clear();

        List<YuyueList> yuyueLists = new ArrayList<>();
        for (Map.Entry<Integer, List<YuyueTmp>> entry : map.entrySet()) {
            List<YuyueTmp> tmps = entry.getValue();
            YuyueList yuyueList = new YuyueList();
            yuyueList.setWeekName(DateUtils.getWeekName(entry.getKey()));
            if (!TypeUtils.empty(tmps)) {
                yuyueList.setTime(DateFuncs.dateTimeToStr(DateFuncs.fromIntDate(tmps.get(0).getDateid()), "MM/dd"));
            }
            List<YuyueInfo> yuyueInfos = new ArrayList<>();
            for (YuyueTmp tmp : tmps) {
                YuyueInfo yuyueInfo = new YuyueInfo();
                yuyueInfo.setTime(tmp.getTime());
                yuyueInfo.setUsername(tmp.getUsername());
                yuyueInfos.add(yuyueInfo);
            }
            yuyueList.setYuyues(yuyueInfos);
            yuyueLists.add(yuyueList);
        }
        return yuyueLists;
    }

    public MomShow show(Integer id) {
        SQL sql = new SQL.SqlBuilder()
                .table("woman_mom m")
                .field("m.id, m.name, m.info, m.cover, m.week")
                .field("(select count(*) from t_woman_mom_yuyue where mom_id = m.id and dateid >= ?) as yuyueNum")
                .where("m.id = ?")
                .build();
        MomShow momShow = pubDao.query(MomShow.class, sql.sql(), DateFuncs.getIntToday(), id);
        if (momShow != null) {
            if (!TypeUtils.empty(momShow.getCover())) {
                String fileSql = "select file_path as path, thumb_path as thumb from t_sys_file where find_in_set(file_id, ?)";
                List<FileData> fileDatas = pubDao.queryList(FileData.class, fileSql, momShow.getCover());
                momShow.setFileList(fileDatas);
            }
            if (!TypeUtils.empty(momShow.getWeek())) {
                String[] weeks = momShow.getWeek().split(",");
                momShow.setWeek(DateUtils.getWeekName(weeks));
            }
        }
        return momShow;
    }

    public String week(Integer id) {
        String sql = "select week from t_woman_mom where id = ?";
        return pubDao.query(String.class, sql, id);
    }

    @Transactional
    public void yuyue(YuyueParam param) {
        //判断用户预约时间是否超过开放时间
        String isSql = "select id, start_time, end_time, status from t_woman_mom_time where mom_id = ? and week = ?";
        MomTime momTime = pubDao.query(MomTime.class, isSql, param.getId(), param.getWeek());
        if (momTime == null) {
            throw new ValidationError("时间未开放！");
        }
        //开放时间判断
        if (!(param.getStart().compareTo(momTime.getStartTime()) >= 0 && param.getStart().compareTo(momTime.getEndTime()) <= 0)
                || !(param.getEnd().compareTo(momTime.getStartTime()) >= 0 && param.getEnd().compareTo(momTime.getEndTime()) <= 0)) {
            throw new ValidationError("小屋开放时间：" + momTime.getStartTime() + "-" + momTime.getEndTime());
        }

        //与其他用户预约是否有重叠
        String hasSql = "select id from t_woman_mom_yuyue\n" +
                "where ((start_time >= :start AND start_time <= :end) OR (end_time >= :start AND end_time <= :end))" +
                " and mom_id = :id and week = :week";
        List<Integer> ids = pubDao.queryList(hasSql, param, Integer.class);
        if (!TypeUtils.empty(ids)) {
            throw new ValidationError("预约时间与其他用户冲突！");
        }

        //保存预约记录
        String addSql = new SQL.SqlBuilder()
                .table("woman_mom_yuyue")
                .field("userid, start_time, end_time, mom_id, add_time, week, dateid")
                .value("?, ?, ?, ?, ?, ?, ?")
                .insert()
                .build().sql();
        pubDao.execute(addSql, UserSession.getUserid(), param.getStart(),
                param.getEnd(), param.getId(), new Date(), param.getWeek(),
                param.getDateId());
    }

    public List<TimeList> listTime(Integer id) {
        SQL.SqlBuilder sql = new SQL.SqlBuilder()
                .table("woman_mom_time")
                .field("concat('周', week_name(week)) as weekName")
                .field("concat(start_time, '-', end_time) as time")
                .where("status = 1")
                .where("mom_id = ?")
                .order("week", Order.Type.ASC);
        return pubDao.queryList(TimeList.class, sql.build().sql(), id);
    }

    public List<MomList> listMom(MomParam param) {
        SQL.SqlBuilder sql = new SQL.SqlBuilder()
                .table("woman_mom m")
                .field("m.id, m.name, m.info")
                .field("f.file_path as path, f.thumb_path as thumb")
                .join("sys_file f", "f.file_id = get_split_string(m.cover, ',', 1)");
        if (param.getStatus() != null) {
            sql.where("m.status = :status");
        }
        if (param.getWeek() != null) {
            sql.where("find_in_set(:week, m.week)");
        }
        return pubDao.queryPage(sql, param, MomList.class);
    }

    public List<WomanMom> listAdminMom(MomParam param) {
        SQL.SqlBuilder sql = new SQL.SqlBuilder()
                .table("woman_mom m")
                .field("m.*")
                .order("m.add_time");
        if (param.getStatus() != null) {
            sql.where("m.status = :status");
        }
        if (param.getWeek() != null) {
            sql.where("find_in_set(:week, m.week)");
        }
        return pubDao.queryPage(sql, param, WomanMom.class);
    }

    @Transactional
    public void delete(String id) {
        pubDao.execute(SQL.deleteByIds("woman_mom", id));
    }

    @Transactional
    public Integer add_mom(WomanMomData mom){
       SQL.SqlBuilder sql=new SQL.SqlBuilder()
               .insert("t_woman_mom")
               .field("name,info,cover,week,status,add_time")
               .value(":name,:info,:cover,:week,:status,:addTime");

        Integer id = pubDao.insertAndGetKey(sql.build().sql(), mom);
        return id;
    }

    @Transactional
    public void add_times(List<WomanMomTime> list){
        SQL.SqlBuilder sql=new SQL.SqlBuilder()
                .insert("t_woman_mom_time")
                .field("mom_id,week,start_time,end_time,status")
                .value(":mid,:week,:startTime,:endTime,:status");
        pubDao.executeBatch(sql.build().sql(),list);
    }

    public WomanMom getWomanMonById(Integer id){
        SQL.SqlBuilder sql=new SQL.SqlBuilder()
                .table("t_woman_mom")
                .field("id,name,info,week,cover")
                .where("id=?");
        WomanMom mom = pubDao.query(WomanMom.class, sql.build().sql(),id);
        return mom;
    }

    //根据mom_id删除mom_time时间
    @Transactional
    public void deleteByMid(Integer mid){
        SQL.SqlBuilder sql=new SQL.SqlBuilder()
                .delete("t_woman_mom_time")
                .where("mom_id=?");
        pubDao.execute(sql.build().sql(),mid);
    }

    //更新t_woman_mom
    @Transactional
    public void updateMom(WomanMomData mom){
        SQL.SqlBuilder sql=new SQL.SqlBuilder()
                .update("t_woman_mom")
                .where("id=:id");
        if(mom.getName()!=null){
            sql.set("name=:name");
        }
        if(mom.getInfo()!=null){
            sql.set("info=:info");
        }
        if(mom.getCover()!=null){
            sql.set("cover=:cover");
        }
        if(mom.getWeek()!=null){
            sql.set("week=:week");
        }
        if(mom.getAddTime()!=null){
            sql.set("add_time=:addTime");
        }
        pubDao.executeBean(sql.build().sql(),mom);
    }

    //获取预约者信息
    public List<MomYuyueParam> getMomYuyes(MomYuyueParam momYuyueParam){
        SQL.SqlBuilder sql=new SQL.SqlBuilder()
                .table("t_woman_mom_yuyue y")
                .field("u.name,y.start_time as startTime,y.end_time as endTime,y.dateid as date")
                .join("t_user u","u.userid = y.userid")
                .join("t_woman_mom m","y.mom_id = m.id")
                .where("y.mom_id=:id")
                .where("y.week=:week")
                .where("y.dateid>=:firstweekDay and y.dateid<=:lastweekDay")
                .order("y.dateid", Order.Type.DESC);
        List<MomYuyueParam> momYuyueParams = pubDao.queryPage(sql, momYuyueParam, MomYuyueParam.class);
        return momYuyueParams;
    }

    //根据id获取week
    public String getWeek(Integer id){
        SQL.SqlBuilder sql=new SQL.SqlBuilder()
                .table("t_woman_mom")
                .field("week")
                .where("id=?");
        String week = pubDao.query(String.class, sql.build().sql(), id);
        return week;
    }

    //获取封面图片的路径
    public List<MomImage> getImagePath(String [] fileIds){
        List<MomImage> filePaths = new ArrayList<>();
        String sql="SELECT file_path FROM `t_sys_file` where file_id=?";
        for (int i=0;i<fileIds.length;i++) {
            String filePath = pubDao.query(String.class, sql, fileIds[i]);
            MomImage momImage = new MomImage(i+1,filePath);
            filePaths.add(momImage);
        }
        return filePaths;
    }

    //获取开放时间
    public List<MomTime> getTimeList(Integer id){
        String sql="SELECT id,week,start_time as startTime,end_time as endTime FROM `t_woman_mom_time` where mom_id=?";
        List<MomTime> momTimes = pubDao.queryList(MomTime.class, sql, id);
        return momTimes;
    }

    @Autowired
    private PubDao pubDao;
}
