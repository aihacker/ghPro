package wxgh.sys.service.weixin.common.disease;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import wxgh.entity.common.disease.DiseaseQs;
import wxgh.param.common.disease.QueryDisease;
import wxgh.sys.dao.common.disease.DiseaseQsDao;

/**
 * Created by XDLK on 2016/9/2.
 * <p>
 * Date： 2016/9/2
 */
@Service
@Transactional(readOnly = true)
public class DiseaseQsService {

    @Autowired
    private DiseaseQsDao diseaseQsDao;

    @Transactional
    public Integer addQs(DiseaseQs diseaseQs) {
        diseaseQsDao.save(diseaseQs);
        return diseaseQs.getId();
    }

    public DiseaseQs getQS(QueryDisease queryDisease) {
        return diseaseQsDao.getQS(queryDisease);
    }
}
