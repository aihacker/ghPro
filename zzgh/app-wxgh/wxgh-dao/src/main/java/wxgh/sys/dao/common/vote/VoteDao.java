package wxgh.sys.dao.common.vote;


import org.springframework.stereotype.Repository;
import pub.dao.mybatis.MyBatisDao;
import wxgh.entity.common.vote.Voted;
import wxgh.param.common.vote.QueryVoted;
import wxgh.param.common.vote.VoteQuery;

import java.util.List;

/**
 *----------------------------------------------------------
 * @Description
 *----------------------------------------------------------
 * @Author  Ape
 *----------------------------------------------------------
 * @Email <16511660@qq.com>
 *----------------------------------------------------------
 * @Date 2017-07-29 15:34
 *----------------------------------------------------------
 */
@Repository
public class VoteDao extends MyBatisDao<Voted> {

    public Integer AddVoted(Voted voted) {
        execute("xlkai_addVoted", voted);
        return voted.getId();
    }

    
    public List<Voted> getAllVote() {
        return getSqlSession().selectList("getAllVote");
    }

    
    public Voted getVoteById(Integer id) {
        return getSqlSession().selectOne("getVoteById", id);
    }

    
    public void DelVoteById(Integer id) {
        getSqlSession().update("DelVoteById", id);
    }

    
    public void UpdateStatus(Integer id) {
        getSqlSession().update("UpdateStatus", id);
    }

    
    public List<Voted> getVoteds(VoteQuery query) {
        return selectList("xlkai_getVoted", query);
    }

    
    public List<Voted> getVoteNoOption(VoteQuery query) {
        return selectList("xlkai_getVotes", query);
    }

    
    public List<Voted> getList(QueryVoted queryVoted) {
        return selectList("getList",queryVoted);
    }

    
    public Integer getCount(QueryVoted queryVoted) {
        return selectOne("getCount",queryVoted);
    }

    
    public Integer del(Integer id) {
        return getSqlSession().delete("del", id);
    }

    
    public Integer shenhe(QueryVoted queryVoted) {
        return getSqlSession().update("shenhe", queryVoted);
    }

    
    public Voted getOne(Integer id) {
        return selectOne("getOne", id);
    }

    
    public List<Voted> applyListRefresh(QueryVoted query) {
        return selectList("applyListRefresh", query);
    }

    
    public List<Voted> applyListMore(QueryVoted query) {
        return selectList("applyListMore", query);
    }
}
