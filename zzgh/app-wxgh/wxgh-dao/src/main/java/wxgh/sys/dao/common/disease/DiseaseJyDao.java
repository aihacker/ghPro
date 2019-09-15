package wxgh.sys.dao.common.disease;


import org.springframework.stereotype.Repository;
import pub.dao.mybatis.MyBatisDao;
import wxgh.entity.common.disease.DiseaseJy;
import wxgh.param.common.disease.QueryDisease;

import java.util.List;

/**
 * Created by XDLK on 2016/9/1.
 * <p>
 * Date： 2016/9/1
 */
@Repository
public class DiseaseJyDao extends MyBatisDao<DiseaseJy> {

    public Integer addJys(List<DiseaseJy> diseaseJies) {
        return execute("xlkai_addJys", diseaseJies);
    }

    public DiseaseJy getJY(QueryDisease queryDisease) {
        return selectOne("getJY", queryDisease);
    }

    public List<DiseaseJy> getJYs(QueryDisease queryDisease) {
        return selectList("getJY", queryDisease);
    }
}
