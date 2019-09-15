package wxgh.sys.service.weixin.entertain.act;

import com.libs.common.type.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pub.dao.jdbc.PubDao;
import pub.dao.jdbc.sql.SQL;
import wxgh.entity.entertain.act.ScoreRule;

import java.util.List;

/**
 * Created by Administrator on 2017/7/19.
 */
@Service
@Transactional(readOnly = true)
public class ScoreRuleService {

    @Transactional
    public void addRule(ScoreRule rule) {
        rule.setName("积分规则");
        rule.setRuleId(StringUtils.uuid());
        SQL sql = new SQL.SqlBuilder()
                .field("score, leave_score, out_score, name, rule_id")
                .value(":score, :leaveScore, :outScore, :name, :ruleId")
                .insert("score_rule").build();
        pubDao.executeBean(sql.sql(), rule);
    }

    public List<ScoreRule> listRule() {
        String sql = "select * from t_score_rule";
        return pubDao.queryList(ScoreRule.class, sql);
    }

    public ScoreRule getRule(String ruleId){
        String sql = "select * from t_score_rule where rule_id = ?";
        return pubDao.query(ScoreRule.class,sql,ruleId);
    }

    @Autowired
    private PubDao pubDao;

}
