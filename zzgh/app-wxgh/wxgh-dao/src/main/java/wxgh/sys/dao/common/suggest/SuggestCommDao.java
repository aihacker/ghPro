package wxgh.sys.dao.common.suggest;


import org.springframework.stereotype.Repository;
import pub.dao.mybatis.MyBatisDao;
import wxgh.entity.common.suggest.SuggestComm;
import wxgh.param.common.suggest.CommentQuery;

import java.util.List;

/**
 *----------------------------------------------------------
 * @Description 
 *----------------------------------------------------------
 * @Author  Ape
 *----------------------------------------------------------
 * @Email <16511660@qq.com>
 *----------------------------------------------------------
 * @Date 2017-07-28 09:48
 *----------------------------------------------------------
 */
@Repository
public class SuggestCommDao extends MyBatisDao<SuggestComm> {

    public List<SuggestComm> getComments(CommentQuery query) {
        return selectList("xlkai_getComms", query);
    }
    
}

