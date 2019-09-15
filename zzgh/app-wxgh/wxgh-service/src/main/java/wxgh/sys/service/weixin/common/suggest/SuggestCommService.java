package wxgh.sys.service.weixin.common.suggest;


import com.libs.common.data.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pub.dao.jdbc.PubDao;
import wxgh.entity.common.suggest.SuggestComm;
import wxgh.param.common.suggest.CommentQuery;
import wxgh.sys.dao.common.suggest.SuggestCommDao;

import java.util.List;

/**
 *----------------------------------------------------------
 * @Description 
 *----------------------------------------------------------
 * @Author  Ape
 *----------------------------------------------------------
 * @Email <16511660@qq.com>
 *----------------------------------------------------------
 * @Date 2017-07-28 09:46
 *----------------------------------------------------------
 */
@Service
@Transactional(readOnly = true)
public class SuggestCommService {

    @Autowired
    private SuggestCommDao suggestCommDao;

    @Autowired
    private PubDao pubDao;


    @Transactional
    public Integer addComment(SuggestComm suggestComm) {
        suggestCommDao.save(suggestComm);
        if (suggestComm.getParentid() == 0) {
            String sql = "UPDATE t_user_suggest SET comm_num=comm_num+1 WHERE id=" + suggestComm.getSugId();
            pubDao.execute(sql);
        }
        return suggestComm.getId();
    }

    public List<SuggestComm> getComments(CommentQuery query) {
        List<SuggestComm> suggestComms = suggestCommDao.getComments(query);
        if (suggestComms != null && suggestComms.size() > 0) {
            for (int i = 0; i < suggestComms.size(); i++) {
                SuggestComm suggestComm = suggestComms.get(i);
                if (suggestComm.getAddTime() != null) {
                    suggestComms.get(i).setTimeStr(DateUtils.formatDate(suggestComm.getAddTime()));
                }

                //查询子类
                CommentQuery commentQuery = new CommentQuery();
                commentQuery.setParentid(suggestComm.getId());
                commentQuery.setOrderBy("s.add_time ASC");
                List<SuggestComm> comms = suggestCommDao.getComments(commentQuery);
                if (comms != null && comms.size() > 0) {
                    for (int j = 0; j < comms.size(); j++) {
                        if (comms.get(j).getAddTime() != null) {
                            comms.get(j).setTimeStr(DateUtils.formatDate(comms.get(j).getAddTime()));
                        }
                    }
                    suggestComms.get(i).setSuggestComms(comms);
                }
            }
        }
        return suggestComms;
    }

    @Transactional
    public Integer updateLov(Integer id) {
        String sql = "UPDATE t_suggest_comm SET lov_num=lov_num+1 WHERE id=" + id;
        return pubDao.execute(sql);
    }
    
}

