package wxgh.sys.service.weixin.common.vote;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pub.dao.jdbc.PubDao;
import wxgh.entity.common.vote.VotePicOption;
import wxgh.sys.dao.common.vote.VotePicOptionDao;

import java.util.List;

// add by MK_H 2018/12/26


@Service
@Transactional(readOnly = true)
public class VotePicOptionService {

    @Autowired
    private VotePicOptionDao votePicOptionDao;

    @Autowired
    private PubDao pubDao;


    @Transactional
    public void AddVotedOption(VotePicOption votePicOption) {
        votePicOptionDao.AddVotePicOption(votePicOption);
    }


    @Transactional
    public Integer addOptions(List<VotePicOption> options) {
        return votePicOptionDao.addOptions(options);
    }

    
    public List<VotePicOption> getOptions(Integer voteId) {
        String sql = "select v.*,f.file_path path,f.thumb_path thumb from t_voted_option_pic v join t_sys_file f on v.options_file=f.file_id where voteId=?";
        return pubDao.queryList(VotePicOption.class, sql, voteId);
    }
}


