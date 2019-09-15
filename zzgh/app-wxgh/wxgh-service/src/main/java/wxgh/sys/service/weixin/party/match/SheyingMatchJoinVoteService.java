package wxgh.sys.service.weixin.party.match;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import wxgh.entity.party.match.SheyingMatchJoinVote;
import wxgh.sys.dao.party.match.SheyingMatchJoinVoteDao;

import java.util.List;

/**
 *----------------------------------------------------------
 * @Description 
 *----------------------------------------------------------
 * @Author  Ape
 *----------------------------------------------------------
 * @Email <16511660@qq.com>
 *----------------------------------------------------------
 * @Date 2017-08-07 16:07
 *----------------------------------------------------------
 */
@Service
@Transactional
public class SheyingMatchJoinVoteService {

    public List<SheyingMatchJoinVote> getData(SheyingMatchJoinVote sheyingMatchJoinVote) {
        return sheyingMatchJoinVoteDao.getData(sheyingMatchJoinVote);
    }

    public void delData(Integer id) {
        sheyingMatchJoinVoteDao.delete(id);
    }

    public void addData(SheyingMatchJoinVote sheyingMatchJoinVote) {
        sheyingMatchJoinVoteDao.save(sheyingMatchJoinVote);
    }

    @Autowired
    private SheyingMatchJoinVoteDao sheyingMatchJoinVoteDao;
    
}

