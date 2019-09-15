package wxgh.sys.service.weixin.entertain.sport;

import com.libs.common.data.DateFuncs;
import com.libs.common.data.DateUtils;
import com.libs.common.type.TypeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pub.dao.jdbc.PubDao;
import pub.dao.jdbc.sql.SQL;
import pub.dao.jdbc.sql.bean.Join;
import wxgh.app.session.user.UserSession;
import wxgh.app.sys.api.sport.SportApi;
import wxgh.data.common.FileData;
import wxgh.data.entertain.sport.apply.SportApplyList;
import wxgh.data.entertain.sport.apply.SportApplyShow;
import wxgh.data.entertain.sport.score.SportScoreType;
import wxgh.entity.entertain.sport.SportApply;
import wxgh.param.entertain.sport.SportApplyParam;

import java.util.Date;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * author：xlkai
 * date：2018/1/9
 * time：9:51
 * version：V1.0
 * Description：
 */
@Service
@Transactional(readOnly = true)
public class SportApplyService {

    @Transactional
    public Integer add(SportApply apply) {
        String sql = new SQL.SqlBuilder()
                .field("userid, dateid, imgs, remark")
                .value(":userid, :dateid, :imgs, :remark")
                .insert("sport_apply")
                .build().sql();
        apply.setUserid(UserSession.getUserid());

        return pubDao.insertAndGetKey(sql, apply);
    }

    @Transactional
    public void apply(SportApply apply) {
        apply.setAuditTime(new Date());
        apply.setStatus(1);
        String sql = "update t_sport_apply set audit_idea = :auditIdea, audit_time = :auditTime, status = :status where id = :id";
        pubDao.executeBean(sql, apply);

        String userid = UserSession.getUserid();
        int start = DateUtils.getFirstWeekDayInt(DateFuncs.fromIntDate(apply.getDateid()));
        int end = DateFuncs.addDay(start, -6);
        int thdy = DateFuncs.addDay(end, 2);

        //todo 周积分，未处理月积分
        String getSql = "select id from t_sport_score where userid = ? and dateid = ? and type = ?";
        Integer scoreId = pubDao.query(Integer.class, getSql, userid, thdy, SportScoreType.WEEK.getKey());
        if (scoreId == null) {
            sportApi.weekUser(start, end, userid, apply.getActId(), thdy);
        }
    }

    public SportApplyShow applyShow(Integer id) {
        String sql = new SQL.SqlBuilder()
                .table("sport_apply a")
                .field("a.*, u.name as username")
                .field("l.last_time, l.step_count as step")
                .join("user u", "u.userid = a.userid")
                .join("sport_last l", "(l.userid = a.userid and l.date_id = a.dateid)", Join.Type.LEFT)
                .where("a.id = ?")
                .build().sql();
        SportApplyShow apply = pubDao.query(SportApplyShow.class, sql, id);
        if (apply != null && !TypeUtils.empty(apply.getImgs())) {
            String fileSql = "select file_path as path, thumb_path as thumb from t_sys_file where find_in_set(file_id, ?)";
            List<FileData> files = pubDao.queryList(FileData.class, fileSql, apply.getImgs());
            apply.setImgs(null);
            apply.setFiles(files);
        }
        return apply;
    }

    public List<SportApplyList> applyList(SportApplyParam param) {
        SQL.SqlBuilder sql = new SQL.SqlBuilder()
                .table("sport_apply s")
                .field("s.id, s.userid, s.add_time, s.dateid")
                .field("u.name as username, u.avatar")
                .join("user u", "u.userid = s.userid")
                .order("dateid, add_time");
        return pubDao.queryPage(sql, param, SportApplyList.class);
    }

    @Autowired
    private PubDao pubDao;

    @Autowired
    private SportApi sportApi;

}
