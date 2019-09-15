package wxgh.sys.service.weixin.union.race;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pub.dao.jdbc.PubDao;
import wxgh.data.union.race.ArrangeData;
import wxgh.data.union.race.ArrangeUser;
import wxgh.entity.union.race.RaceArrange;
import wxgh.sys.dao.union.race.RaceArrangeDao;

import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2017/3/6.
 */
@Service
@Transactional(readOnly = true)
public class RaceArrangeService {

    public int getLastOrderNum(Integer raceId) {
        String sql = "select order_num from t_race_arrange where raceId=? order by order_num DESC limit 1";
        Integer orderNum = generalDao.query(Integer.class, sql, raceId);
        if (orderNum == null) {
            return 1;
        } else {
            return orderNum + 1;
        }
    }

    public ArrangeUser getArrangeUser(Integer id) {
        String sql = "select rival1, rival2, type from t_race_arrange where id=?";
        return generalDao.query(ArrangeUser.class, sql, id);
    }

    @Transactional
    public void saveList(List<RaceArrange> arranges) {
        for (RaceArrange arrange : arranges) {
            raceArrangeDao.save(arrange);
        }
    }

    @Transactional
    public Integer saveOrUpdate(RaceArrange raceArrange) {
        String sql = "select id from t_race_arrange where rival1=? and rival2=? and raceId=? and arrange_type=?";
        Integer id = generalDao.query(Integer.class, sql, raceArrange.getRival1(), raceArrange.getRival2(), raceArrange.getRaceId(), RaceArrange.ARRANGE_TYPE_RANDOM);
        if (id == null) {
            Integer orderNum = getLastOrderNum(raceArrange.getRaceId());
            raceArrange.setOrderNum(orderNum);
            raceArrange.setRaceTime(new Date());
            raceArrange.setStatus(1);
            raceArrange.setLunkong(0);
            raceArrangeDao.save(raceArrange);
            return raceArrange.getId();
        }
        return id;
    }

    @Transactional
    public void updateAddress(Integer raceId, String old, String edit) {
        String sql = "update t_race_arrange set address = ? where raceId=? and address=?";
        generalDao.execute(sql, edit, raceId, old);
    }

    @Transactional
    public void updateTime(Integer raceId, String old, String edit) {
        String sql = "UPDATE t_race_arrange set race_time = CONCAT(DATE_FORMAT(race_time,'%Y-%m-%d'),' ', ?, ':00') where raceId=? and race_time=?";
        generalDao.execute(sql, edit, raceId, old);
    }

    @Transactional
    public void updateStatus(Integer id, Integer status) {
        String sql = "update t_race_arrange set status=? where id=?";
        generalDao.execute(sql, status, id);
    }

    public List<RaceArrange> getArranges(Integer raceId, Integer status, Integer lunkong) {
        String sql = "select * from t_race_arrange\n" +
                "where raceId = ? and lunkong = ?";
        if (status != null) {
            sql += " AND status=?";
            return generalDao.queryList(RaceArrange.class, sql, raceId, lunkong, status);
        }
        return generalDao.queryList(RaceArrange.class, sql, raceId, lunkong);
    }

    /**
     * 获取非轮空编排，并返回目前进行到第几轮
     *
     * @param raceId
     * @param status
     * @return
     */
    public List<ArrangeData> getArrangeAndLun(Integer raceId, Integer status) {
        List<ArrangeData> arrangeDatas;
        String sql = "select a.id, a.rival1, a.rival2, a.name1, a.name2, a.status, " +
                "(SELECT MAX(d.lun_num) from t_race_score d where d.arrange_id = a.id) as lunNumb" +
                " from t_race_arrange a\n" +
                "where a.raceId = ? AND lunkong =?";
        if (status != null) {
            sql += " AND a.status=?";
            arrangeDatas = generalDao.queryList(ArrangeData.class, sql, raceId, 0, status);
        } else {
            arrangeDatas = generalDao.queryList(ArrangeData.class, sql, raceId, 0);
        }
        return arrangeDatas;
    }

    @Autowired
    private RaceArrangeDao raceArrangeDao;

    @Autowired
    private PubDao generalDao;

}
