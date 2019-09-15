package wxgh.sys.dao.common.vote;


import org.springframework.stereotype.Repository;
import pub.dao.mybatis.MyBatisDao;
import wxgh.entity.common.vote.VotedJoin;
import wxgh.param.common.vote.VoteJoinQuery;

import java.util.List;

/**
 * Created by Admin on 2016/7/11.
 */
@Repository
public class VotedJoinDao extends MyBatisDao<VotedJoin> {

   
    public List<VotedJoin> getJoins(VoteJoinQuery query) {
        return selectList("xlkai_getJoin", query);
    }

   
    public VotedJoin getJoin(VoteJoinQuery query) {
        return selectOne("xlkai_getJoin", query);
    }
}
