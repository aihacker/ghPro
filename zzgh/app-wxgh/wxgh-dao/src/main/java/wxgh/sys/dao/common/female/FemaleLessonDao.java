package wxgh.sys.dao.common.female;

import org.springframework.stereotype.Repository;
import pub.dao.mybatis.MyBatisDao;
import wxgh.entity.common.female.FemaleLesson;
import wxgh.param.common.female.QueryFemaleLesson;

import java.util.List;

/**
 *----------------------------------------------------------
 * @Description 
 *----------------------------------------------------------
 * @Author  Ape
 *----------------------------------------------------------
 * @Email <16511660@qq.com>
 *----------------------------------------------------------
 * @Date 2017-08-01 09:18
 *----------------------------------------------------------
 */
@Repository
public class FemaleLessonDao extends MyBatisDao<FemaleLesson> {

    public List<FemaleLesson> getData(QueryFemaleLesson queryFemaleLesson) {
        return selectList("getDataWX",queryFemaleLesson );
    }

    public void updateData(FemaleLesson femaleLesson) {
        getSqlSession().update("updateData", femaleLesson);
    }


}

