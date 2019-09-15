package wxgh.sys.service.weixin.tribe;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pub.dao.jdbc.PubDao;
import wxgh.entity.tribe.TribeResult;

import java.util.List;

/**
 * @author hhl
 * @create 2017-08-07
 **/
@Service
public class TribeResultService {

    @Transactional
    public List<TribeResult> getData(){
        String sql = "select * from t_tribe_result";
        return pubDao.queryList(TribeResult.class,sql);
    }

    public TribeResult getInfo(Integer id){
        String sql = "select * from t_tribe_result where id = ?";
        return pubDao.query(TribeResult.class,sql,id);
    }

    @Autowired
    private PubDao pubDao;
}
