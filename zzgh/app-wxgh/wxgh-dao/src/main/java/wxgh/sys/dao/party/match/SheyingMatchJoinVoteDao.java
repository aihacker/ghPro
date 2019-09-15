package wxgh.sys.dao.party.match;

import org.springframework.stereotype.Repository;
import pub.dao.mybatis.MyBatisDao;
import wxgh.entity.party.match.SheyingMatchJoinVote;

import java.util.List;

/**
 *----------------------------------------------------------
 * @Description 
 *----------------------------------------------------------
 * @Author  Ape
 *----------------------------------------------------------
 * @Email <16511660@qq.com>
 *----------------------------------------------------------
 * @Date 2017-08-07 16:08
 *----------------------------------------------------------
 */
@Repository
public class SheyingMatchJoinVoteDao extends MyBatisDao<SheyingMatchJoinVote>  {

    public List<SheyingMatchJoinVote> getData(SheyingMatchJoinVote sheyingMatchJoinVote) {
        return selectList("getData", sheyingMatchJoinVote);
    }

}
