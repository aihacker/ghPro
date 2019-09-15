package wxgh.sys.dao.common.female;


import org.springframework.stereotype.Repository;
import pub.dao.mybatis.MyBatisDao;
import wxgh.entity.common.female.FemaleMama;
import wxgh.param.common.female.FemaleMamaAdminQuery;

import java.util.List;

/**
 * Created by Administrator on 2017/4/25.
 */
@Repository
public class FemaleLMamaAdminDao extends MyBatisDao<FemaleMama> {

    public void update(FemaleMama femaleMama) {
        execute("xlkai_upates", femaleMama);
    }

    public List<FemaleMama> getRecords(FemaleMamaAdminQuery query) {
        return selectList("xlkai_getRecords", query);
    }

    public Integer countRecords(FemaleMamaAdminQuery query) {
        Integer count = selectOne("xlkai_counts", query);
        return count == null ? 0 : count;
    }
}
