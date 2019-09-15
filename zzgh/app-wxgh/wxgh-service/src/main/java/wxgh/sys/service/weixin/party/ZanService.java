package wxgh.sys.service.weixin.party;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pub.dao.jdbc.PubDao;
import wxgh.entity.party.Zan;
import wxgh.sys.dao.party.ZanDao;

import java.util.Date;

/**
 * Created by Administrator on 2017/4/20.
 */
@Service
@Transactional(readOnly = true)
public class ZanService {

    @Transactional
    public void save(Zan zan) {
        zan.setAddTime(new Date());
        zanDao.save(zan);
    }

    public Zan getZan(String userid, Integer type, Integer zanId) {
        String sql = "select * from t_zan where userid=? and zan_type=? and zan_id=?";
        return generalDao.query(Zan.class, sql, userid, type, zanId);
    }

    @Transactional
    public void delZan(Integer id) {
        zanDao.delete(id);
    }

    @Autowired
    private ZanDao zanDao;

    @Autowired
    private PubDao generalDao;
}
