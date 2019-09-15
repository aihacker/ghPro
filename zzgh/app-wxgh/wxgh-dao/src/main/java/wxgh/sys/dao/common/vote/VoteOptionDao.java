package wxgh.sys.dao.common.vote;


import org.springframework.stereotype.Repository;
import pub.dao.mybatis.MyBatisDao;
import wxgh.entity.common.vote.VoteOption;

import java.util.List;

/**
 * Created by Admin on 2016/7/7.
 */
@Repository
public class VoteOptionDao extends MyBatisDao<VoteOption> {

   
    public void AddVotedOption(VoteOption voteOption) {
        this.save(voteOption);
    }

   
    public List<VoteOption> getVoteOptionById(Integer id) {
        return getSqlSession().selectList("getVoteOptionById", id);
    }

   
    public VoteOption getVoteOptionBy(Integer id) {
        return getSqlSession().selectOne("getVoteOptionBy", id);
    }

   
    public void updateTicket(VoteOption voteOption) {
        getSqlSession().update("updateTicket", voteOption);
    }

   
    public Integer updateOption(VoteOption voteOption) {
        return execute("xlkai_updateOption", voteOption);
    }

   
    public Integer addOptions(List<VoteOption> options) {
        return execute("xlkai_addOptions", options);
    }
}
