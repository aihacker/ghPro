package wxgh.sys.dao.common.suggest;


import org.springframework.stereotype.Repository;
import pub.dao.mybatis.MyBatisDao;
import wxgh.entity.common.suggest.SuggestLov;
import wxgh.param.common.suggest.LovQuery;

/**
 *----------------------------------------------------------
 * @Description 
 *----------------------------------------------------------
 * @Author  Ape
 *----------------------------------------------------------
 * @Email <16511660@qq.com>
 *----------------------------------------------------------
 * @Date 2017-07-28 09:55
 *----------------------------------------------------------
 */
@Repository
public class SuggestLovDao extends MyBatisDao<SuggestLov> {

    public SuggestLov getLov(LovQuery query) {
        return selectOne("xlkai_getLov", query);
    }
    
}

