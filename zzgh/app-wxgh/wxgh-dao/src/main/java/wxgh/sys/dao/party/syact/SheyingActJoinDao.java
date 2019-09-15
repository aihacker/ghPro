package wxgh.sys.dao.party.syact;

import org.springframework.stereotype.Repository;
import pub.dao.mybatis.MyBatisDao;
import wxgh.entity.party.match.SheyingActJoin;

import java.util.List;

/**
 *----------------------------------------------------------
 * @Description 
 *----------------------------------------------------------
 * @Author  Ape
 *----------------------------------------------------------
 * @Email <16511660@qq.com>
 *----------------------------------------------------------
 * @Date 2017-08-08 08:55
 *----------------------------------------------------------
 */
@Repository
public class SheyingActJoinDao extends MyBatisDao<SheyingActJoin> {

    public List<SheyingActJoin> getData(SheyingActJoin sheyingActJoin) {
        return selectList("getData", sheyingActJoin);
    }
    
}

