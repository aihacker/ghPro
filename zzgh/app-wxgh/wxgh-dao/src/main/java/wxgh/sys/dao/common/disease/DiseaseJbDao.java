package wxgh.sys.dao.common.disease;


import org.springframework.stereotype.Repository;
import pub.dao.mybatis.MyBatisDao;
import wxgh.entity.common.disease.DiseaseJb;
import wxgh.param.common.disease.QueryDisease;

/**
 * Created by XDLK on 2016/9/1.
 * <p>
 * Date： 2016/9/1
 */
@Repository
public class DiseaseJbDao extends MyBatisDao<DiseaseJb> {

    public DiseaseJb getJB(QueryDisease queryDisease) {
        return selectOne("getJB", queryDisease);
    }
}
