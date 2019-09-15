package wxgh.sys.service.weixin.common.female;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pub.dao.jdbc.PubDao;
import pub.dao.jdbc.sql.SQL;
import pub.dao.jdbc.sql.bean.Order;
import wxgh.data.common.female.FemaleLessonData;
import wxgh.entity.common.female.FemaleLesson;
import wxgh.param.common.female.QueryFemaleLesson;
import wxgh.sys.dao.common.female.FemaleLessonDao;

import java.util.List;

/**
 *----------------------------------------------------------
 * @Description 
 *----------------------------------------------------------
 * @Author  Ape
 *----------------------------------------------------------
 * @Email <16511660@qq.com>
 *----------------------------------------------------------
 * @Date 2017-08-01 09:17
 *----------------------------------------------------------
 */
@Service
@Transactional(readOnly = true)
public class FemaleLessonService {

    @Autowired
    private FemaleLessonDao femaleLessonDao;

    @Autowired
    private PubDao pubDao;

    public List<FemaleLesson> getData(QueryFemaleLesson queryFemaleLesson) {
        return femaleLessonDao.getData(queryFemaleLesson);
    }

    public List<FemaleLessonData> getDatas(QueryFemaleLesson queryFemaleLesson){
        SQL.SqlBuilder sql = new SQL.SqlBuilder()
                .table("t_female_lesson fl")
                .sys_file("fl.cover")
                .field("fl.*")
                .select();

        if(queryFemaleLesson.getId() != null)
            sql.where("fl.id = :id");
        if(queryFemaleLesson.getUuid() != null)
            sql.where("fl.uuid = :uuid");
        if(queryFemaleLesson.getIndexID() != null)
            sql.where("fl.id < :indexID");
        if(queryFemaleLesson.getDate() != null)
            sql.where("start_time <= :date AND end_time >= :date");

        sql.order("fl.id", Order.Type.DESC);

        return pubDao.queryList(sql.build().sql(), queryFemaleLesson, FemaleLessonData.class);
    }

    @Transactional
    public Integer add(FemaleLesson femaleLesson) {
        femaleLessonDao.save(femaleLesson);
        return femaleLesson.getId();
    }

    @Transactional
    public void del(Integer id) {
        femaleLessonDao.delete(id);
    }

    @Transactional
    public void updateData(FemaleLesson femaleLesson) {
        femaleLessonDao.updateData(femaleLesson);
    }
    
}

