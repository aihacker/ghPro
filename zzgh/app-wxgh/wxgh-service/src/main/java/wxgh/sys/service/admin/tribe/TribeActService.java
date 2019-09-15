package wxgh.sys.service.admin.tribe;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pub.dao.jdbc.PubDao;
import pub.dao.jdbc.sql.SQL;
import wxgh.entity.pub.Dept;
import wxgh.entity.pub.User;
import wxgh.entity.tribe.TribeAct;
import wxgh.param.tribe.admin.TribeActParam;
import wxgh.sys.dao.notice.NoticeNewsDao;
import wxgh.sys.dao.tribe.admin.TribeActDao;

import java.util.List;

/**
 * Created by 蔡炳炎 on 2017/8/22.
 */
@Service
@Transactional(readOnly = true)
public class TribeActService {

    @Autowired
    private TribeActDao tribeActDao;
    @Autowired
    private PubDao generalDao;
    @Autowired
    private NoticeNewsDao noticeNewsDao;

    //获取活动信息
    public List<TribeAct> getData(TribeActParam tribeActParam) {
        SQL sql = new SQL.SqlBuilder()
                .table("t_tribe_act a")
                .field("a.id,a.theme,a.start_time AS startTime,a.end_time AS endTime,u.name AS linkman,a.phone,a.total_number AS totalNumber,a.status,d.name as deptname")
                .join("t_dept d", "d.id=a.deptid")
                .join("t_user u","u.userid=a.linkman")
                .where("a.status=?")
                .build();
        List<TribeAct> tribeActs = generalDao.queryList(TribeAct.class, sql.sql(), tribeActParam.getStatus());
        return tribeActs;
    }

    @Transactional
    public void save(TribeAct tribeAct) {
        tribeActDao.save(tribeAct);
    }

    @Transactional
    public void del(String id) {
        generalDao.execute(SQL.deleteByIds("tribe_act", id));
    }

    @Transactional
    public void updateData(TribeActParam tribeActParam) {
        String sql = new SQL.SqlBuilder().table("t_tribe_act")
                .set("status=?")
                .where("id=?")
                .update()
                .build()
                .sql();
        generalDao.execute(sql, tribeActParam.getStatus(), tribeActParam.getId());
    }

    public TribeAct getOne(TribeAct tribeAct) {
        return tribeActDao.getOne(tribeAct);
    }

    public TribeAct getAct(Integer id) {
        SQL sql = new SQL.SqlBuilder()
                .table("t_tribe_act a")
                .field("a.id,a.theme,a.status,a.start_time as startTime,a.end_time as endTime,a.address,a.content,a.remark,a.deptid,a.lng,a.lat")
                .field("a.leader,a.linkman,d.name as deptname,a.phone,a.total_number as totalNumber,a.cross_point as crossPoint")
                .join("t_dept d", "d.id=a.deptid")
                .where("a.id=?")
                .build();
        TribeAct query = generalDao.query(TribeAct.class, sql.sql(), id);
        return query;
    }

    public List<Dept> getDept() {
        SQL sql = new SQL.SqlBuilder()
                .table("t_dept d")
                .field("d.id,d.name,d.deptid,d.parentid,d.order")
                .build();
        List<Dept> query = generalDao.queryList(Dept.class, sql.sql());
        return query;
    }

    @Transactional
    public void updateAct(TribeAct tribeAct) {
        tribeActDao.updateData(tribeAct);
    }

    public List<User> searchUser(String key) {
        String sql = "select u.name,u.mobile,u.deptid,u.userid,u.avatar,d.name AS department from t_user u join t_dept d on d.id = u.deptid where u.name like ? or u.mobile like ? or u.userid like ? group by u.userid limit 8";
        return generalDao.queryList(User.class, sql, "%" + key + "%", key + "%", "%" + key + "%");
    }


}
