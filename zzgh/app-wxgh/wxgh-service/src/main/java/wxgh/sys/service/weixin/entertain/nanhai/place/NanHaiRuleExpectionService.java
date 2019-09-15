package wxgh.sys.service.weixin.entertain.nanhai.place;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pub.dao.jdbc.PubDao;
import wxgh.data.entertain.place.ExpectionInfo;
import wxgh.data.entertain.place.NanHaiExpectionInfo;
import wxgh.entity.entertain.nanhai.place.NanHaiRuleExpection;
import wxgh.entity.entertain.place.RuleExpection;
import wxgh.sys.dao.entertain.nanhai.place.NanHaiRuleExpectionDao;
import wxgh.sys.dao.entertain.place.RuleExpectionDao;

import java.util.List;

/**
 * @Author xlkai
 * @Version 2017/2/18
 */
@Service
@Transactional(readOnly = true)
public class NanHaiRuleExpectionService {

    public List<NanHaiRuleExpection> getExpections(Integer ruleId) {
        String sql = "select * from t_nanhai_place_rule_expection where rule_id=?";
        return generalDao.queryList(NanHaiRuleExpection.class, sql, ruleId);
    }

    public List<NanHaiExpectionInfo> getInfos(Integer ruleId) {
        String sql = "select e.*, u.name as username, d.name as deptname,pr.name as ruleName, pr.type as ruleType from t_nanhai_place_rule_expection e\n" +
                "join t_user u on e.userid = u.userid\n" +
                "join t_dept d on d.deptid = u.deptid\n" +
                "join t_nanhai_place_rule pr on pr.id = e.rule_id\n" +
                "where e.rule_id = ?";
        return generalDao.queryList(NanHaiExpectionInfo.class, sql, ruleId);
    }

    public NanHaiExpectionInfo getInfo(Integer expectionId) {
        String sql = "select e.*, u.name as username, d.name as deptname,pr.name, pr.type as ruleType from t_nanhai_place_rule_expection e\n" +
                "join t_user u on e.userid = u.userid\n" +
                "join t_dept d on d.deptid = u.deptid\n" +
                "join t_nanhai_place_rule pr on pr.id = e.rule_id\n" +
                "where e.id = ?";
        return generalDao.query(NanHaiExpectionInfo.class, sql, expectionId);
    }

    @Transactional
    public void save(NanHaiRuleExpection ruleExpection) {
        ruleExpectionDao.save(ruleExpection);
    }

    @Transactional
    public void saveList(List<NanHaiRuleExpection> ruleExpections) {
        for (NanHaiRuleExpection e : ruleExpections) {
            ruleExpectionDao.save(e);
        }
    }

    @Transactional
    public void delete(Integer id) {
        ruleExpectionDao.delete(id);
    }

    @Autowired
    private NanHaiRuleExpectionDao ruleExpectionDao;

    @Autowired
    private PubDao generalDao;

}
