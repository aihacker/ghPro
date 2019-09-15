package wxgh.sys.service.weixin.common.suggest;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pub.dao.jdbc.PubDao;
import wxgh.entity.common.suggest.SuggestLov;
import wxgh.param.common.suggest.LovQuery;
import wxgh.sys.dao.common.suggest.SuggestLovDao;

/**
 *----------------------------------------------------------
 * @Description 
 *----------------------------------------------------------
 * @Author  Ape
 *----------------------------------------------------------
 * @Email <16511660@qq.com>
 *----------------------------------------------------------
 * @Date 2017-07-28 09:54
 *----------------------------------------------------------
 */
@Service
@Transactional(readOnly = true)
public class SuggestLovService {

    @Autowired
    private SuggestLovDao suggestLovDao;

    @Autowired
    private PubDao pubDao;

    public SuggestLov getLov(LovQuery query) {
        return suggestLovDao.getLov(query);
    }

    @Transactional
    public Integer addLov(SuggestLov suggestLov) {
        suggestLovDao.save(suggestLov);
        return suggestLov.getId();
    }

    @Transactional
    public Integer updateLovStatus(Integer lovId, Integer status) {
        String sql = "UPDATE t_suggest_lov SET status = " + status + " WHERE id = " + lovId;
        return pubDao.execute(sql);
    }

}

