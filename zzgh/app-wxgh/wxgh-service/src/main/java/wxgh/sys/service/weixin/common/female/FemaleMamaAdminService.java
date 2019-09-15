package wxgh.sys.service.weixin.common.female;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pub.dao.jdbc.PubDao;
import wxgh.entity.common.female.FemaleMama;
import wxgh.param.common.female.FemaleMamaAdminQuery;
import wxgh.sys.dao.common.female.FemaleLMamaAdminDao;

import java.util.List;

/**
 * Created by Administrator on 2017/4/25.
 */
@Service
@Transactional(readOnly = true)
public class FemaleMamaAdminService {

    @Transactional
    public void delete(String id) {
        String sql = "delete from t_female_mama where id in(" + id + ")";
        pubDao.execute(sql);
    }

    public List<FemaleMama> getRecords(FemaleMamaAdminQuery query) {
        return femaleLMamaAdminDao.getRecords(query);
    }

    public Integer countRecords(FemaleMamaAdminQuery query) {
        return femaleLMamaAdminDao.countRecords(query);
    }

    @Transactional
    public void updateRecord(FemaleMama femaleMama) {
        femaleLMamaAdminDao.update(femaleMama);
    }

    @Autowired
    private FemaleLMamaAdminDao femaleLMamaAdminDao;

    @Autowired
    private PubDao pubDao;

}
