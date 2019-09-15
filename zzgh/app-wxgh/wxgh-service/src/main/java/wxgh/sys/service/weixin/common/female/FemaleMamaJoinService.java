package wxgh.sys.service.weixin.common.female;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import wxgh.entity.common.female.FemaleMamaJoin;
import wxgh.param.common.female.QueryFemaleMamaJoin;
import wxgh.sys.dao.common.female.FemaleMamaJoinDao;

import java.util.List;

/**
 * Created by XDLK on 2016/9/1.
 * <p>
 * Date： 2016/9/1
 */
@Service
@Transactional(readOnly = true)
public class FemaleMamaJoinService {

    @Autowired
    private FemaleMamaJoinDao femaleMamaJoinDao;

    public List<FemaleMamaJoin> getData(FemaleMamaJoin femaleMamaJoin) {
        return femaleMamaJoinDao.getData(femaleMamaJoin);
    }

    @Transactional
    public Integer add(FemaleMamaJoin femaleMamaJoin) {
        femaleMamaJoinDao.save(femaleMamaJoin);
        return femaleMamaJoin.getId();
    }

    public List<FemaleMamaJoin> getDataWX(QueryFemaleMamaJoin queryFemaleMamaJoin) {
        return femaleMamaJoinDao.getDataWX(queryFemaleMamaJoin);
    }

}
