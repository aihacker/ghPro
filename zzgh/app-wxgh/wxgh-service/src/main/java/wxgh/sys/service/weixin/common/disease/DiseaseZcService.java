package wxgh.sys.service.weixin.common.disease;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import wxgh.entity.common.disease.DiseaseZc;
import wxgh.param.common.disease.QueryDisease;
import wxgh.sys.dao.common.disease.DiseaseZcDao;

/**
 * Created by XDLK on 2016/9/2.
 * <p>
 * Date： 2016/9/2
 */
@Service
@Transactional(readOnly = true)
public class DiseaseZcService {

    @Autowired
    private DiseaseZcDao diseaseZcDao;

    @Transactional
    public Integer addZc(DiseaseZc diseaseZc) {
        diseaseZcDao.save(diseaseZc);
        return diseaseZc.getId();
    }

    public DiseaseZc getZC(QueryDisease queryDisease) {
        return diseaseZcDao.getZC(queryDisease);
    }
}
