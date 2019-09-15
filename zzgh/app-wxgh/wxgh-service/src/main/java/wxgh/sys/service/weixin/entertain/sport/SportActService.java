package wxgh.sys.service.weixin.entertain.sport;

import com.libs.common.type.TypeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pub.dao.jdbc.PubDao;
import pub.dao.jdbc.sql.SQL;
import pub.error.ValidationError;
import wxgh.data.entertain.sport.act.SportActInfo;
import wxgh.data.entertain.sport.act.SportActList;
import wxgh.entity.entertain.sport.SportAct;
import wxgh.param.entertain.sport.SportActParam;

import java.util.Date;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * author：xlkai
 * date：2018/1/5
 * time：17:28
 * version：V1.0
 * Description：
 */
@Service
@Transactional(readOnly = true)
public class SportActService {

    public SportActInfo get(Integer id) {
        String sql = new SQL.SqlBuilder()
                .field("a.*")
                .field("(select file_path from t_sys_file where file_id = a.img) as imgPath")
                .field("(select file_path from t_sys_file where file_id = a.bg_img) as bgPath")
                .table("sport_act a")
                .where("id = ?")
                .build().sql();
        return pubDao.query(SportActInfo.class, sql, id);
    }

    /**
     * 根据deptids取正在进行中的健步活动id
     *
     * @param deptId
     * @return
     */
    public SportActInfo getId(String deptId) {
        String sql = new SQL.SqlBuilder()
                .field("a.*")
                .field("(select file_path from t_sys_file where file_id = a.img) as imgPath")
                .field("(select file_path from t_sys_file where file_id = a.bg_img) as bgPath")
                .table("sport_act a")
                .where("deptids=?")
                .where("status=1")
                .build().sql();
        return pubDao.query(SportActInfo.class, sql, deptId);
    }

    @Transactional
    public Integer add(SportAct act) {
        SQL.SqlBuilder sql = new SQL.SqlBuilder()
                .insert("sport_act")
                .field("name, start, end, info, img, bg_img, deptids, status, add_time")
                .value(":name, :start, :end, :info, :img, :bgImg, :deptids, :status, :addTime");
        act.setAddTime(new Date());
        act.setStart(2);

        return pubDao.insertAndGetKey(sql.build().sql(), act);
    }

    @Transactional
    public void editBg(Integer id, String bg) {
        String sql = "update t_sport_act set bg_img = ? where id = ?";
        pubDao.execute(sql, bg, id);
    }

    @Transactional
    public void delete(String id) {
        if (TypeUtils.empty(id)) {
            throw new ValidationError("请选择需要删除的活动");
        }
        String[] ids = id.split(",");
        String sql = "delete from t_sport_act where id = ?";

        pubDao.batch(sql, ids);
    }

    public List<SportActList> listAct(SportActParam param) {
        SQL.SqlBuilder sql = new SQL.SqlBuilder()
                .field("a.id, a.name, a.info, a.deptids, a.status")
                .field("(select file_path from t_sys_file where file_id = a.img) as imgPath")
                .field("(select file_path from t_sys_file where file_id = a.bg_img) as bgPath")
                .field("(select group_concat(name) from t_dept where id in (a.deptids)) as deptname")
                .field("CONCAT(a.start, '-', a.end) as time")
                .table("sport_act a");
        if (param.getStatus() != null) {
            sql.where("a.status = :status");
        }
        if (param.getDeptid() != null) {
            sql.where("find_in_set(:deptid, a.deptids)");
        }
        return pubDao.queryPage(sql, param, SportActList.class);
    }

    @Autowired
    private PubDao pubDao;

}
