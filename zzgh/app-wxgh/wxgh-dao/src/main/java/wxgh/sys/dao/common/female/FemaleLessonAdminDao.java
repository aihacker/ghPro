package wxgh.sys.dao.common.female;


import org.springframework.stereotype.Repository;
import pub.dao.mybatis.MyBatisDao;
import wxgh.entity.common.female.FemaleLesson;
import wxgh.param.common.female.FemaleLessonAdminQuery;

import java.util.List;

/**
 * Created by Administrator on 2017/4/25.
 */
@Repository
public class FemaleLessonAdminDao extends MyBatisDao<FemaleLesson> {

    public void update(FemaleLesson femaleLesson) {
        execute("xlkai_upates", femaleLesson);
    }

    public List<FemaleLesson> getRecords(FemaleLessonAdminQuery query) {
        return selectList("xlkai_getRecords", query);
    }

    public Integer countRecords(FemaleLessonAdminQuery query) {
        Integer count = selectOne("xlkai_counts", query);
        return count == null ? 0 : count;
    }
}
