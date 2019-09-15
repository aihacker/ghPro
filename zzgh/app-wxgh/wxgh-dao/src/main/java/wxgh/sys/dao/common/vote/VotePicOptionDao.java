package wxgh.sys.dao.common.vote;


import org.springframework.stereotype.Repository;
import pub.dao.mybatis.MyBatisDao;
import wxgh.entity.common.vote.VotePicOption;

import java.util.List;

/**
 * Created by Admin on 2016/7/7.
 */
@Repository
public class VotePicOptionDao extends MyBatisDao<VotePicOption> {

   
    public void AddVotePicOption(VotePicOption votePicOption) {
        this.save(votePicOption);
    }

   
    public List<VotePicOption> getVoteOptionById(Integer id) {
        return getSqlSession().selectList("getVotePicOptionById", id);
    }

   
    public VotePicOption getVoteOptionBy(Integer id) {
        return getSqlSession().selectOne("getVotePicOptionBy", id);
    }

   
    public void updateTicket(VotePicOption votePicOption) {
        getSqlSession().update("updateTicket", votePicOption);
    }

   
    public Integer updateOption(VotePicOption votePicOption) {
        return execute("xlkai_updateOptionPic", votePicOption);
    }

   
    public Integer addOptions(List<VotePicOption> options) {
        return execute("xlkai_addOptionsPic", options);
    }
}
