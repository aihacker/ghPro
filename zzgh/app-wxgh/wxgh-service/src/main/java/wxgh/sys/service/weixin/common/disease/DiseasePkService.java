package wxgh.sys.service.weixin.common.disease;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import wxgh.entity.common.disease.DiseasePk;
import wxgh.param.common.disease.QueryDisease;
import wxgh.sys.dao.common.disease.DiseasePkDao;

/**
 * Created by Administrator on 2016/10/20.
 */
@Service
@Transactional(readOnly = true)
public class DiseasePkService {

    @Transactional
    public void save(DiseasePk diseasePk) {
        diseasePkDao.save(diseasePk);
    }

    public DiseasePk getPk(QueryDisease queryDisease) {
        return diseasePkDao.getPk(queryDisease);
    }

    @Autowired
    private DiseasePkDao diseasePkDao;
}
