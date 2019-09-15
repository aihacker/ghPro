package wxgh.sys.service.weixin.party.match;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pub.dao.jdbc.PubDao;
import pub.dao.jdbc.sql.SQL;
import pub.dao.jdbc.sql.bean.Join;
import wxgh.entity.party.match.SheyingMatch;
import wxgh.sys.dao.party.match.SheyingMatchDao;

import java.util.List;

/**
 *----------------------------------------------------------
 * @Description 
 *----------------------------------------------------------
 * @Author  Ape
 *----------------------------------------------------------
 * @Email <16511660@qq.com>
 *----------------------------------------------------------
 * @Date 2017-08-07 15:36
 *----------------------------------------------------------
 */
@Service
@Transactional
public class SheyingMatchService {

    @Autowired
    private SheyingMatchDao sheyingMatchDao;

    @Autowired
    private PubDao pubDao;

    public List<SheyingMatch> getData(SheyingMatch sheyingMatch) {
        SQL.SqlBuilder sql = new SQL.SqlBuilder()
                .field("m.id, m.name, m.content, m.tel, m.rule, m.type, m.works_type, m.start_time, m.end_time, m.add_time, f.file_path as cover")
                .table("t_sheying_match m")
                .join("sys_file f", "f.file_id = m.cover", Join.Type.LEFT);

        if(sheyingMatch.getId() != null)
            sql.where("m.id = :id");

        sql.select();
        return pubDao.queryList(sql.build().sql(), sheyingMatch, SheyingMatch.class);
//        return sheyingMatchDao.getData(sheyingMatch);
    }

    public void delData(Integer id) {
        sheyingMatchDao.delete(id);
    }

    public void addData(SheyingMatch sheyingMatch) {
        sheyingMatchDao.save(sheyingMatch);
    }

    public SheyingMatch getMatch(Integer id) {
        return sheyingMatchDao.get(id);
    }
    
}

