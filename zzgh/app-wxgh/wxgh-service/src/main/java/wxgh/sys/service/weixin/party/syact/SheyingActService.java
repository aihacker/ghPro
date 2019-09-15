package wxgh.sys.service.weixin.party.syact;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pub.dao.jdbc.PubDao;
import pub.dao.jdbc.sql.SQL;
import pub.dao.jdbc.sql.bean.Join;
import pub.dao.jdbc.sql.bean.Order;
import wxgh.entity.party.match.SheyingAct;
import wxgh.sys.dao.party.syact.SheyingActDao;

import java.util.List;

/**
 *----------------------------------------------------------
 * @Description 
 *----------------------------------------------------------
 * @Author  Ape
 *----------------------------------------------------------
 * @Email <16511660@qq.com>
 *----------------------------------------------------------
 * @Date 2017-08-08 08:45
 *----------------------------------------------------------
 */
@Service
@Transactional
public class SheyingActService {

    @Autowired
    private PubDao pubDao;

    public List<SheyingAct> getData(SheyingAct sheyingActQuery) {
        SQL.SqlBuilder sql = new SQL.SqlBuilder()
                .table("t_sheying_act sa")
                .field("sa.id, f.file_path as cover, sa.name, sa.linkman, sa.tel, sa.address, sa.content, sa.remark, sa.start_time as startTime, sa.end_time as endTime ")
                .join("sys_file f", "sa.cover = f.file_id", Join.Type.LEFT);
        sql.order("sa.start_time", Order.Type.DESC);
        if(sheyingActQuery.getId() != null)
            sql.where("sa.id = :id");
        return pubDao.queryList(sql.select().build().sql(), sheyingActQuery, SheyingAct.class);
//        return sheyingActDao.getData(sheyingAct);
    }

    public void delData(Integer id) {
        sheyingActDao.delete(id);
    }

    public void addData(SheyingAct sheyingAct) {
        sheyingActDao.save(sheyingAct);
    }

    @Autowired
    private SheyingActDao sheyingActDao;
    
}

