package wxgh.sys.dao.common.disease;


import org.springframework.stereotype.Repository;
import pub.dao.mybatis.MyBatisDao;
import wxgh.entity.common.disease.DiseasePk;
import wxgh.param.common.disease.QueryDisease;

/**
 * Created by Administrator on 2016/10/20.
 */
@Repository
public class DiseasePkDao extends MyBatisDao<DiseasePk> {

    public DiseasePk getPk(QueryDisease queryDisease) {
        return selectOne("getPK", queryDisease);
    }
}
