package wxgh.sys.service.weixin.common.vote;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pub.dao.jdbc.PubDao;
import wxgh.entity.common.vote.VotedJoin;
import wxgh.param.common.vote.VoteJoinQuery;
import wxgh.sys.dao.common.vote.VotedJoinDao;

import java.util.Date;
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
public class VotedJoinService  {

    @Autowired
    private VotedJoinDao votedJoinDao;

    @Autowired
    private PubDao pubDao;


    @Transactional
    public Integer addJoin(VotedJoin votedJoin) {
        votedJoin.setJoinTime(new Date());
        votedJoin.setStatus(1);
        votedJoinDao.save(votedJoin);

        String sql = "select type from t_voted where id =?";
        Integer type = pubDao.queryInt(sql,votedJoin.getVotedId());

        //更新投票数量
        String sql2 = "UPDATE t_voted_option SET ticketNum=ticketNum+1 WHERE id=" + votedJoin.getOptionId();
        String sql3 = "UPDATE t_voted_option_pic SET ticketNum=ticketNum+1 WHERE id=" + votedJoin.getOptionId();

        if(type.equals(1)){
            pubDao.execute(sql2);
        }else if(type.equals(2)){
            pubDao.execute(sql3);
        }

        return null;
    }

    public List<VotedJoin> getJoins(VoteJoinQuery query) {
        return votedJoinDao.getJoins(query);
    }

    public VotedJoin getJoin(VoteJoinQuery query) {
        return votedJoinDao.getJoin(query);
    }
}

