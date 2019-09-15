package wxgh.sys.service.weixin.common.disease;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import wxgh.entity.common.disease.DiseaseZh;
import wxgh.param.common.disease.QueryDisease;
import wxgh.sys.dao.common.disease.DiseaseZhDao;

/**
 * Created by XDLK on 2016/9/2.
 * <p>
 * Date： 2016/9/2
 */
@Service
@Transactional(readOnly = true)
public class DiseaseZhService {

    @Autowired
    private DiseaseZhDao diseaseZhDao;

    @Transactional
    public Integer addZh(DiseaseZh diseaseZh) {
        diseaseZhDao.save(diseaseZh);
        return diseaseZh.getId();
    }

    public DiseaseZh getZH(QueryDisease queryDisease) {
        return diseaseZhDao.getZH(queryDisease);
    }
}
