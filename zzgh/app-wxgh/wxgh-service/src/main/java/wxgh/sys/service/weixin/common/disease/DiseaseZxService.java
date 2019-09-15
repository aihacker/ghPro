package wxgh.sys.service.weixin.common.disease;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import wxgh.entity.common.disease.DiseaseZx;
import wxgh.param.common.disease.QueryDisease;
import wxgh.sys.dao.common.disease.DiseaseZxDao;

/**
 * Created by XDLK on 2016/9/2.
 * <p>
 * Date： 2016/9/2
 */
@Service
@Transactional(readOnly = true)
public class DiseaseZxService {

    @Autowired
    private DiseaseZxDao diseaseZxDao;

    @Transactional
    public Integer addZx(DiseaseZx diseaseZx) {
        diseaseZxDao.save(diseaseZx);
        return diseaseZx.getId();
    }

    public DiseaseZx getZX(QueryDisease queryDisease) {
        return diseaseZxDao.getZX(queryDisease);
    }
}
