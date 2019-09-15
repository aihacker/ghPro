package wxgh.sys.service.weixin.common.disease;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import wxgh.entity.common.disease.DiseaseJb;
import wxgh.param.common.disease.QueryDisease;
import wxgh.sys.dao.common.disease.DiseaseJbDao;

/**
 * Created by XDLK on 2016/9/1.
 * <p>
 * Date： 2016/9/1
 */
@Service
@Transactional(readOnly = true)
public class DiseaseJbService {

    @Autowired
    private DiseaseJbDao diseaseJbDao;

    @Transactional
    public Integer addJb(DiseaseJb diseaseJb) {
        diseaseJbDao.save(diseaseJb);
        return diseaseJb.getId();
    }

    public DiseaseJb getJB(QueryDisease queryDisease) {
        return diseaseJbDao.getJB(queryDisease);
    }
}
