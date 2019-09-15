package wxgh.sys.service.weixin.common.disease;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import wxgh.entity.common.disease.DiseaseJy;
import wxgh.param.common.disease.QueryDisease;
import wxgh.sys.dao.common.disease.DiseaseJyDao;

import java.util.List;

/**
 * Created by XDLK on 2016/9/1.
 * <p>
 * Date： 2016/9/1
 */
@Service
@Transactional(readOnly = true)
public class DiseaseJyService {

    @Autowired
    private DiseaseJyDao diseaseJyDao;

    @Transactional
    public Integer addJys(List<DiseaseJy> diseaseJies) {
        return diseaseJyDao.addJys(diseaseJies);
    }

    public DiseaseJy getJY(QueryDisease queryDisease) {
        return diseaseJyDao.getJY(queryDisease);
    }

    public List<DiseaseJy> getJYs(QueryDisease queryDisease) {
        return diseaseJyDao.getJYs(queryDisease);
    }
}
