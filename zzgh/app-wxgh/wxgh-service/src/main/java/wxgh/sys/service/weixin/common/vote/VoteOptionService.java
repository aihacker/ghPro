package wxgh.sys.service.weixin.common.vote;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pub.dao.jdbc.PubDao;
import wxgh.entity.common.vote.VoteOption;
import wxgh.sys.dao.common.vote.VoteOptionDao;

import java.util.List;

/**
 *----------------------------------------------------------
 * @Description 
 *----------------------------------------------------------
 * @Author  Ape
 *----------------------------------------------------------
 * @Email <16511660@qq.com>
 *----------------------------------------------------------
 * @Date 2017-07-29 15:02
 *----------------------------------------------------------
 */
@Service
@Transactional(readOnly = true)
public class VoteOptionService {

    @Autowired
    private VoteOptionDao voteOptionDao;

    @Autowired
    private PubDao pubDao;

    
    @Transactional
    public void AddVotedOption(VoteOption voteOption) {
        voteOptionDao.AddVotedOption(voteOption);
    }

    
    public List<VoteOption> getVoteOptionById(Integer id) {
        return voteOptionDao.getVoteOptionById(id);
    }

    
    public VoteOption getVoteOptionBy(Integer id) {
        return voteOptionDao.getVoteOptionBy(id);
    }

    
    @Transactional
    public void updateTicket(VoteOption voteOption) {
        voteOptionDao.updateTicket(voteOption);
    }

    
    @Transactional
    public Integer updateOption(VoteOption voteOption) {
        return voteOptionDao.updateOption(voteOption);
    }

    
    @Transactional
    public Integer addOptions(List<VoteOption> options) {
        return voteOptionDao.addOptions(options);
    }

    
    public List<VoteOption> getOptions(Integer voteId) {
        String sql = "select * from t_voted_option where voteId=?";
        return pubDao.queryList(VoteOption.class, sql, voteId);
    }

    public Integer getUsernum(Integer voteId) {
        String sql = "select count(distinct userid) from t_voted_join where voted_id=?";
        return pubDao.query(Integer.class, sql, voteId);
    }
}


