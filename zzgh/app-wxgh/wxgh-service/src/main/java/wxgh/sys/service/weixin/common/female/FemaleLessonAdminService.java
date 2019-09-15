package wxgh.sys.service.weixin.common.female;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pub.dao.jdbc.PubDao;
import wxgh.entity.common.female.FemaleLesson;
import wxgh.param.common.female.FemaleLessonAdminQuery;
import wxgh.sys.dao.common.female.FemaleLessonAdminDao;

import java.util.List;

/**
 * Created by Administrator on 2017/4/25.
 */
@Service
@Transactional(readOnly = true)
public class FemaleLessonAdminService {

    @Transactional
    public void delete(String id) {
        String sql = "delete from t_female_lesson where id in(" + id + ")";
        pubDao.execute(sql);
    }

    public List<FemaleLesson> getRecords(FemaleLessonAdminQuery query) {
        return femaleLessonAdminDao.getRecords(query);
    }

    public Integer countRecords(FemaleLessonAdminQuery query) {
        return femaleLessonAdminDao.countRecords(query);
    }

    @Transactional
    public void updateRecord(FemaleLesson femaleLesson) {
        femaleLessonAdminDao.update(femaleLesson);
    }

    @Autowired
    private FemaleLessonAdminDao femaleLessonAdminDao;

    @Autowired
    private PubDao pubDao;

}
