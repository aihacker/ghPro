package wxgh.sys.dao.common.female;

import org.springframework.stereotype.Repository;
import pub.dao.mybatis.MyBatisDao;
import wxgh.entity.common.female.FemaleLessonJoin;
import wxgh.param.common.female.QueryFemaleLessonJoin;

import java.util.List;

/**
 *----------------------------------------------------------
 * @Description 
 *----------------------------------------------------------
 * @Author  Ape
 *----------------------------------------------------------
 * @Email <16511660@qq.com>
 *----------------------------------------------------------
 * @Date 2017-08-01 09:37
 *----------------------------------------------------------
 */
@Repository
public class FemaleLessonJoinDao extends MyBatisDao<FemaleLessonJoin> {

    public List<FemaleLessonJoin> getData(QueryFemaleLessonJoin queryFemaleLessonJoin) {
        return selectList("getDataWX",queryFemaleLessonJoin );
    }

}


