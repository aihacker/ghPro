package wxgh.sys.dao.common.disease;


import org.springframework.stereotype.Repository;
import pub.dao.mybatis.MyBatisDao;
import wxgh.entity.common.disease.DiseaseZc;
import wxgh.param.common.disease.QueryDisease;

/**
 * Created by XDLK on 2016/9/1.
 * <p>
 * Date： 2016/9/1
 */
@Repository
public class DiseaseZcDao extends MyBatisDao<DiseaseZc> {

    public DiseaseZc getZC(QueryDisease queryDisease) {
        return selectOne("getZC", queryDisease);
    }
}
