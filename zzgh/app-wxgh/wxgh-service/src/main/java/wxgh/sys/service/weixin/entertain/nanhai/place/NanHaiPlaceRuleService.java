package wxgh.sys.service.weixin.entertain.nanhai.place;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pub.dao.jdbc.PubDao;
import wxgh.entity.entertain.nanhai.place.NanHaiPlace;
import wxgh.entity.entertain.nanhai.place.NanHaiPlaceRule;
import wxgh.entity.entertain.place.PlaceRule;
import wxgh.sys.dao.entertain.nanhai.place.NanHaiPlaceRuleDao;
import wxgh.sys.dao.entertain.place.PlaceRuleDao;

import java.util.List;

/**
 * @Author xlkai
 * @Version 2017/2/17
 */
@Service
@Transactional(readOnly = true)
public class NanHaiPlaceRuleService {

    /**
     * 根据规则状态获取规则
     *
     * @param stauts
     * @return
     */
    public List<NanHaiPlaceRule> getRules(Integer stauts) {
        List<NanHaiPlaceRule> rules;
        if (stauts == null) {
            rules = placeRuleDao.getAll();
        } else {
            String sql = "select * from t_nanhai_place_rule where status=?";
            rules = generalDao.queryList(NanHaiPlaceRule.class, sql, stauts);
        }
        return rules;
    }

    @Transactional
    public void updateStatus(Integer id, Integer status) {
        String sql = "update t_nanhai_place_rule set status=? where id=?";
        generalDao.execute(sql, status, id);
    }

    @Transactional
    public void save(NanHaiPlaceRule placeRule) {
        placeRuleDao.save(placeRule);
    }

    @Transactional
    public void delete(Integer id) {
        String sql = "delete from t_nanhai_place_rule_expection where rule_id=?";
        generalDao.execute(sql, id);
        placeRuleDao.delete(id);
    }

    public NanHaiPlaceRule get(Integer id) {
        return placeRuleDao.get(id);
    }

    @Autowired
    private NanHaiPlaceRuleDao placeRuleDao;

    @Autowired
    private PubDao generalDao;

}
