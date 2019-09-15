package wxgh.sys.service.weixin.entertain.place;

import com.libs.common.data.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pub.dao.jdbc.PubDao;
import pub.utils.TypeUtils;
import wxgh.data.entertain.place.GudingList;
import wxgh.entity.entertain.place.PlaceGuding;
import wxgh.entity.entertain.place.PlaceTime;
import wxgh.param.entertain.place.GudingParam;
import wxgh.sys.dao.entertain.place.PlaceGudingDao;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2017/4/14.
 */
@Service
@Transactional(readOnly = true)
public class PlaceGudingService {

    @Transactional
    public void save(PlaceGuding guding) {
        guding.setAddTime(new Date());
        placeGudingDao.save(guding);

        String sql = "update t_place_time set time_type=? where id=?";
        generalDao.execute(sql, 2, guding.getTimeId());
    }

    public Integer getStatus(Integer id) {
        String sql = "select status from t_place_guding where id=?";
        return generalDao.query(Integer.class, sql, id);
    }

    public PlaceGuding getOne(Integer timeId) {
        String sql = "select * from t_place_guding where time_id=? limit 1";
        return generalDao.query(PlaceGuding.class, sql, timeId);
    }

    /**
     * 获取全部可用的固定场
     *
     * @param query
     * @return
     */
    public List<GudingList> getGudings(GudingParam query) {
        String sql = "select t.id as timeId, CONCAT(t.start_time,'-',t.end_time) as time, t.`week`,\n" +
                "g.username, g.deptname, g.mobile, g.userid, g.remark, g.time_id, g.status, g.id,\n" +
                "s.`name` as siteName, c.`name` as cateName\n" +
                "from t_place_guding g\n" +
                "join t_place_time t on g.time_id = t.id\n" +
                "join t_place_site s on t.site_id = s.id\n" +
                "join t_place_cate c on t.cate_id = c.id where s.place_id=?";
        List<Object> params = new ArrayList();
        params.add(query.getPlaceId());
        if (query.getCateId() != null) {
            sql += " and t.cate_id=?";
            params.add(query.getCateId());
        }
        if (query.getSiteId() != null) {
            sql += " and t.site_id=?";
            params.add(query.getSiteId());
        }
        if (query.getStatus() != null) {
            sql += " and g.status = ?";
            params.add(query.getStatus());
        }
        if (query.getWeek() != null) {
            sql += " and t.week = ?";
            params.add(query.getWeek());
        }
        sql += " order by s.name ASC";
        return generalDao.queryList(GudingList.class, sql, TypeUtils.listToObject(params));
    }

    /**
     * 删除固定场
     *
     * @param timeId
     */
    @Transactional
    public void del(Integer timeId) {
        //删除固定场
        String sql = "delete from t_place_guding where id = ?";
        generalDao.execute(sql, timeId);

        //判断时间是否过期
        sql = "select * from t_place_time where id=?";
        PlaceTime time = generalDao.query(PlaceTime.class, sql, timeId);
        if (time != null) {
            Integer status;

            int week = DateUtils.getWeek(new Date());
            String startTime = DateUtils.formatDate(new Date(), "HH:mm");

            //未过期
            if (time.getWeek() >= week && time.getStartTime().compareTo(startTime) > 0) {
                status = 0;
            } else { //已过期
                status = 2;
            }
            //更新时间状态
//            sql = "update t_place_time set status=? where id=?";
            sql = "update t_place_time set time_type=?,d_status=?,s_status=? where id=?";
            generalDao.execute(sql, 1, status, status, timeId);
        }
    }

    @Transactional
    public void edit(PlaceGuding guding) {
        placeGudingDao.update(guding);
    }

    @Autowired
    private PlaceGudingDao placeGudingDao;

    @Autowired
    private PubDao generalDao;

}
