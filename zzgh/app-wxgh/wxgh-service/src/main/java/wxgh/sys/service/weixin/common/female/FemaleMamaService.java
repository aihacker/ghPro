package wxgh.sys.service.weixin.common.female;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pub.dao.jdbc.PubDao;
import pub.dao.jdbc.sql.SQL;
import pub.dao.jdbc.sql.bean.Order;
import wxgh.data.common.female.FemaleMamaData;
import wxgh.entity.common.female.FemaleMama;
import wxgh.param.common.female.QueryFemaleMama;
import wxgh.sys.dao.common.female.FemaleMamaDao;

import java.util.List;

/**
 * Created by XDLK on 2016/9/1.
 * <p>
 * Date： 2016/9/1
 */
@Service
@Transactional(readOnly = true)
public class FemaleMamaService {

    @Autowired
    private FemaleMamaDao femaleMamaDao;

    @Autowired
    private PubDao pubDao;

    public List<FemaleMama> getData(QueryFemaleMama queryFemaleMama) {
        return femaleMamaDao.getData(queryFemaleMama);
    }

    public List<FemaleMamaData> getDatas(QueryFemaleMama queryFemaleMama){
        SQL.SqlBuilder sql = new SQL.SqlBuilder()
                .table("t_female_mama fl")
                .sys_file("fl.cover")
                .field("fl.*")
                .select();

        if(queryFemaleMama.getId() != null)
            sql.where("fl.id = :id");
        if(queryFemaleMama.getIndexID() != null)
            sql.where("fl.id < :indexID");

        sql.order("fl.id", Order.Type.DESC);

        return pubDao.queryList(sql.build().sql(), queryFemaleMama, FemaleMamaData.class);
    }

    @Transactional
    public Integer add(FemaleMama femaleMama) {
        femaleMamaDao.save(femaleMama);
        return femaleMama.getId();
    }

    @Transactional
    public void del(Integer id) {
        femaleMamaDao.delete(id);
    }

}
