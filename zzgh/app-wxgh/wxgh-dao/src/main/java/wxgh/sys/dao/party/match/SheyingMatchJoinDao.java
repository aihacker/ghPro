package wxgh.sys.dao.party.match;

import org.springframework.stereotype.Repository;
import pub.dao.mybatis.MyBatisDao;
import wxgh.entity.party.match.SheyingMatchJoin;
import wxgh.param.party.match.JoinQuery;

import java.util.List;

/**
 *----------------------------------------------------------
 * @Description 
 *----------------------------------------------------------
 * @Author  Ape
 *----------------------------------------------------------
 * @Email <16511660@qq.com>
 *----------------------------------------------------------
 * @Date 2017-08-07 15:50
 *----------------------------------------------------------
 */
@Repository
public class SheyingMatchJoinDao extends MyBatisDao<SheyingMatchJoin> {


    public List<SheyingMatchJoin> getData(SheyingMatchJoin sheyingMatchJoin) {
        return selectList("getData", sheyingMatchJoin);
    }

    public void update(SheyingMatchJoin join) {
        execute("xlkai_update", join);
    }

    public List<SheyingMatchJoin> getJoins(JoinQuery query) {
        return selectList("xlkai_getJoins", query);
    }
}

