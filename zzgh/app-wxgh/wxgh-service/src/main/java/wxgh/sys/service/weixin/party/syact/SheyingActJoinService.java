package wxgh.sys.service.weixin.party.syact;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pub.dao.jdbc.PubDao;
import pub.dao.jdbc.sql.SQL;
import wxgh.entity.party.match.SheyingActJoin;
import wxgh.sys.dao.party.syact.SheyingActJoinDao;

import java.util.List;

/**
 *----------------------------------------------------------
 * @Description 
 *----------------------------------------------------------
 * @Author  Ape
 *----------------------------------------------------------
 * @Email <16511660@qq.com>
 *----------------------------------------------------------
 * @Date 2017-08-08 08:54
 *----------------------------------------------------------
 */
@Service
@Transactional
public class SheyingActJoinService {

    @Autowired
    private PubDao pubDao;

    public List<SheyingActJoin> getData(SheyingActJoin sheyingActJoin) {
        return sheyingActJoinDao.getData(sheyingActJoin);
    }

    public void delData(Integer id) {
        sheyingActJoinDao.delete(id);
    }

    public void addData(SheyingActJoin sheyingActJoin) {
        sheyingActJoinDao.save(sheyingActJoin);
    }

    /**
     * true已存在
     * @param userid
     * @param aid
     * @return
     */
    public boolean check(String userid, Integer aid){
        SQL sql = new SQL.SqlBuilder()
                .table("sheying_act_join")
                .field("userid")
                .where("userid = ? and aid = ?")
                .limit("1")
                .select().build();
        String uid = pubDao.query(String.class, sql.sql(), userid, aid);
        return uid == null ? false : true;
    }

    @Autowired
    private SheyingActJoinDao sheyingActJoinDao;
    
}

