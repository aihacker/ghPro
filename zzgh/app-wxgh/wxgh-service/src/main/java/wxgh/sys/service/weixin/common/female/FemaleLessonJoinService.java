package wxgh.sys.service.weixin.common.female;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pub.dao.jdbc.PubDao;
import pub.dao.jdbc.sql.SQL;
import wxgh.entity.common.female.FemaleLessonJoin;
import wxgh.param.common.female.QueryFemaleLessonJoin;
import wxgh.sys.dao.common.female.FemaleLessonJoinDao;

import java.util.List;

/**
 * Created by XDLK on 2016/9/1.
 * <p>
 * Date： 2016/9/1
 */
@Service
@Transactional(readOnly = true)
public class FemaleLessonJoinService {

    @Autowired
    private FemaleLessonJoinDao femaleLessonJoinDaoDao;

    @Autowired
    private PubDao pubDao;

    public boolean checkJoin(String uid, Integer fid){
        SQL sql = new SQL.SqlBuilder()
                .field("id")
                .table("t_female_lesson_join")
                .where("userid = ? and fid = ?")
                .limit("1")
                .select()
                .build();
        String id = pubDao.query(String.class, sql.sql(), uid, fid);
        return id != null ? true : false;
    }

    public List<FemaleLessonJoin> getData(QueryFemaleLessonJoin queryFemaleLessonJoin) {
        return femaleLessonJoinDaoDao.getData(queryFemaleLessonJoin);
    }

    @Transactional
    public Integer add(FemaleLessonJoin femaleLessonJoin) {
        femaleLessonJoinDaoDao.save(femaleLessonJoin);
        return femaleLessonJoin.getId();
    }

    @Transactional
    public void del(Integer id) {
        femaleLessonJoinDaoDao.delete(id);
    }


}
