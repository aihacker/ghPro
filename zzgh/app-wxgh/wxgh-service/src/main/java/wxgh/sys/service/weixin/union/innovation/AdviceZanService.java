package wxgh.sys.service.weixin.union.innovation;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pub.dao.jdbc.PubDao;
import wxgh.entity.union.innovation.AdviceZan;
import wxgh.sys.dao.union.innovation.AdviceZanDao;

import java.io.Serializable;

/**
 * @Author xlkai
 * @Version 2016/11/28
 */
@Service
@Transactional(readOnly = true)
public class AdviceZanService {

    
    @Transactional
    public void addZan(AdviceZan zan) {
        adviceZanDao.save(zan);
    }

    
    public AdviceZan getZan(String userid, Integer type) {
        String sql = "select * from t_innovate_advice_zan where userid=? and type=? limit 1";
        return pubDao.query(AdviceZan.class, sql, userid, type);
    }

    
    public boolean isZan(String userid, Integer type, Integer adviceId, Integer commId) {

        Integer id = null;
        String sql = "select id from t_innovate_advice_zan where userid=? and type=? and advice_id=?";
        if (commId == null) {
            sql += " limit 1";
            id = pubDao.query(Integer.class, sql, userid, type, adviceId);
        } else {
            sql += " and comm_id=? limit 1";
            id = pubDao.query(Integer.class, sql, userid, type, adviceId, commId);
        }
        return id != null;
    }

    
    public AdviceZan get(Serializable id) {
        return adviceZanDao.get(id);
    }

    @Autowired
    private AdviceZanDao adviceZanDao;

    @Autowired
    private PubDao pubDao;
}
