package wxgh.sys.dao.party.syact;

import org.springframework.stereotype.Repository;
import pub.dao.mybatis.MyBatisDao;
import wxgh.entity.party.match.SheyingAct;

import java.util.List;

/**
 *----------------------------------------------------------
 * @Description 
 *----------------------------------------------------------
 * @Author  Ape
 *----------------------------------------------------------
 * @Email <16511660@qq.com>
 *----------------------------------------------------------
 * @Date 2017-08-08 08:46
 *----------------------------------------------------------
 */
@Repository
public class SheyingActDao extends MyBatisDao<SheyingAct> {

    public List<SheyingAct> getData(SheyingAct sheyingAct) {
        return selectList("getData", sheyingAct);
    }
    
}

