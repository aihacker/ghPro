package wxgh.sys.service.weixin.tribe;

import com.libs.common.data.DateUtils;
import com.libs.common.type.TypeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pub.dao.jdbc.PubDao;
import pub.dao.jdbc.sql.SQL;
import pub.dao.jdbc.sql.bean.Order;
import wxgh.app.session.user.UserSession;
import wxgh.data.party.tribe.CommInfo;
import wxgh.data.party.tribe.TribeActInfo;
import wxgh.entity.tribe.TribeAct;
import wxgh.query.tribe.TribeActQuery;
import wxgh.query.tribe.TribeActQuery2;

import java.util.List;

/**
 * @author hhl
 * @create 2017-08-04
 **/
@Service
@Transactional(readOnly = true)
public class TribeActService {

    public String getInfo(Integer type, Integer id) {
        String filed;
        if (type == 1) {
            filed = "content";
        } else {
            filed = "remark";
        }
        SQL sql = new SQL.SqlBuilder()
                .field(filed)
                .table("tribe_act")
                .where("id = ?")
                .build();
        return pubDao.query(String.class, sql.sql(), id);
    }

    public List<CommInfo> listCommManage(Integer actId) {
        SQL sql = new SQL.SqlBuilder()
                .table("manage_act_comment c")
                .field("c.id, c.userid, c.add_time, c.content")
                .field("u.name as username, u.avatar")
                .field("(select group_concat(file_path) from t_sys_file where find_in_set(file_id, c.img)) as img")
                .join("user u", "c.userid = u.userid")
                .where("c.act_id = ?")
                .order("c.add_time", Order.Type.DESC)
                .build();

        List<CommInfo> commInfos = pubDao.queryList(CommInfo.class, sql.sql(), actId);
        if (!TypeUtils.empty(commInfos)) {
            for (int i = 0, len = commInfos.size(); i < len; i++) {
                CommInfo info = commInfos.get(i);
                commInfos.get(i).setImgs(TypeUtils.strToList(info.getImg()));
                commInfos.get(i).setImg(null);
                commInfos.get(i).setAddTime(DateUtils.formatDate(info.getAddTime()));
            }
        }
        return commInfos;
    }

    public List<TribeAct> getDatas2( TribeActQuery2 tribeActQuery) {
        SQL.SqlBuilder sql = new SQL.SqlBuilder()
                .table("t_manage_act ta")
                .field("ta.*")
                .sys_file("ta.cover_img")
                .where("ta.status = :status")
                .order("ta.start_time", Order.Type.DESC);
        return pubDao.queryPage(sql, tribeActQuery, TribeAct.class);
    }

    public List<TribeAct> getData2(TribeActQuery2 tribeActQuery2) {
        String sql = "select * from t_tribe_act where status = ?";
        return pubDao.queryList(TribeAct.class, sql, tribeActQuery2.getStatus());
    }

    public List<TribeAct> getData(TribeActQuery tribeActQuery) {
        String sql = "select * from t_tribe_act where status = ?";
        return pubDao.queryList(TribeAct.class, sql, tribeActQuery.getStatus());
    }

    public String getInfoManage(Integer type, Integer id) {
        String filed;
        if (type == 1) {
            filed = "content";
        } else {
            filed = "remark";
        }
        SQL sql = new SQL.SqlBuilder()
                .field(filed)
                .table("manage_act")
                .where("id = ?")
                .build();
        return pubDao.query(String.class, sql.sql(), id);
    }

    public TribeAct getOne(Integer id) {
        String sql = "select * from t_tribe_act where id = ?";
        return pubDao.query(TribeAct.class, sql, id);
    }

    public TribeActInfo getManageAct(Integer actId) {
        SQL sql = new SQL.SqlBuilder()
                .table("manage_act a")
                .field("a.id, a.theme, a.start_time, a.end_time, a.address, a.lng, a.lat, a.remark, a.content, a.total_number")
                .field("u.name as username, u.userid")
                .field("(select count(*) from t_manage_act_join where act_id = a.id and status=1) as joinNumb")
                .field("(SELECT IFNULL(1,0) from t_manage_act_join where act_id = a.id and userid = ? and `status` = 1) as joinIs")
                .field("(select count(*) from t_manage_act_comment where act_id = a.id) as commNumb")
                .sys_file("a.cover_img")
                .join("user u", "u.userid = a.linkman")
                .where("a.id = ?")
                .build();
        String sql1="select t_manage_act a join t_user u on u,userid=a.linkman where a.id=61";
        TribeActInfo info = pubDao.query(TribeActInfo.class, sql.sql(), UserSession.getUserid(), actId);
        if (info != null) {
            info.setJoinIs(info.getJoinIs() == null ? 0 : info.getJoinIs());
        }
        return info;
    }

    public TribeActInfo getTribeAct(Integer actId) {
        SQL sql = new SQL.SqlBuilder()
                .table("tribe_act a")
                .field("a.id, a.theme, a.start_time, a.end_time, a.address, a.lng, a.lat, a.remark, a.content, a.total_number")
                .field("u.name as username, u.userid")
                .field("(select count(*) from t_tribe_act_join where act_id = a.id and status=1) as joinNumb")
                .field("(SELECT IFNULL(1,0) from t_tribe_act_join where act_id = a.id and userid = ? and `status` = 1) as joinIs")
                .field("(select count(*) from t_tribe_act_comment where act_id = a.id) as commNumb")
                .sys_file("a.cover_img")
                .join("user u", "u.userid = a.linkman")
                .where("a.id = ?")
                .build();
        String sql1="select t_tribe_act a join t_user u on u,userid=a.linkman where a.id=61";
        TribeActInfo info = pubDao.query(TribeActInfo.class, sql.sql(), UserSession.getUserid(), actId);
        if (info != null) {
            info.setJoinIs(info.getJoinIs() == null ? 0 : info.getJoinIs());
        }
        return info;
    }

    public List<CommInfo> listComm(Integer actId) {
        SQL sql = new SQL.SqlBuilder()
                .table("tribe_act_comment c")
                .field("c.id, c.userid, c.add_time, c.content")
                .field("u.name as username, u.avatar")
                .field("(select group_concat(file_path) from t_sys_file where find_in_set(file_id, c.img)) as img")
                .join("user u", "c.userid = u.userid")
                .where("c.act_id = ?")
                .order("c.add_time", Order.Type.DESC)
                .build();

        List<CommInfo> commInfos = pubDao.queryList(CommInfo.class, sql.sql(), actId);
        if (!TypeUtils.empty(commInfos)) {
            for (int i = 0, len = commInfos.size(); i < len; i++) {
                CommInfo info = commInfos.get(i);
                commInfos.get(i).setImgs(TypeUtils.strToList(info.getImg()));
                commInfos.get(i).setImg(null);
                commInfos.get(i).setAddTime(DateUtils.formatDate(info.getAddTime()));
            }
        }
        return commInfos;
    }

    @Autowired
    private PubDao pubDao;
}
