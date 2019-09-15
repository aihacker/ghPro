package wxgh.sys.service.weixin.act;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pub.dao.jdbc.PubDao;
import pub.dao.jdbc.sql.SQL;
import wxgh.entity.canteen.CanteenAct;
import wxgh.entity.entertain.act.Act;
import wxgh.entity.entertain.act.ScoreRule;
import wxgh.entity.pub.score.Score;
import wxgh.sys.service.weixin.entertain.act.ScoreRuleService;

import java.util.Date;

@Service
@Transactional(readOnly = true)
public class ActScoreService {

    private int execute;

    //兴趣协会积分管理 添加
    @Transactional
    public void add_union_score(Act act, String userid, Integer type){
        SQL sql = new SQL.SqlBuilder()
                .field("act_id, score, money, status, add_time, group_id,userid")
                .value(":actId, :score, :money, :status,:addTime, :groupId, :userid")
                .insert("group_act_record")
                .build();

        ScoreRule scoreRule = scoreRuleService.getRule(act.getScoreRule());
        Score score = new Score();
        score.setActId(act.getActId());
        score.setGroupId(act.getGroupId());
        score.setMoney(act.getMoney());
        score.setStatus(type);
        score.setAddTime(new Date());
        score.setUserid(userid);
        if(scoreRule!=null){
            if(type == 1){
                score.setScore(scoreRule.getScore());
            }else if(type == 2){
                score.setScore(scoreRule.getLeaveScore());
            }

            pubDao.executeBean(sql.sql(),score);
        }

    }

    @Transactional
    public void update_union_score(Act act, String userid, Integer type){
        String sql = "update t_group_act_record set score=:score,status=:status where userid=:userid and act_id=:actId";
        Score score = new Score();
        score.setStatus(type);
        score.setUserid(userid);
        score.setActId(act.getActId());
        ScoreRule scoreRule = scoreRuleService.getRule(act.getScoreRule());
        if(type == 1){
            score.setScore(scoreRule.getScore());
        }else if(type == 2){
            score.setScore(scoreRule.getLeaveScore());
        }

        pubDao.executeBean(sql,score);

    }

    @Transactional
    public void update_union_score(CanteenAct act, String userid, Integer type){
        String sql = "update t_group_act_record set score=:score,status=:status where userid=:userid and act_id=:actId";
        Score score = new Score();
        score.setStatus(type);
        score.setUserid(userid);
        score.setActId(act.getActId());
        ScoreRule scoreRule = scoreRuleService.getRule(act.getScoreRule());
        if(type == 1){
            score.setScore(scoreRule.getScore());
        }else if(type == 2){
            score.setScore(scoreRule.getLeaveScore());
        }

        pubDao.executeBean(sql,score);

    }

    @Transactional
    public void add_union_score(CanteenAct act, String userid, Integer type){
        SQL sql = new SQL.SqlBuilder()
                .field("act_id, score, money, status, add_time, group_id,userid")
                .value(":actId, :score, :money, :status,:addTime, :groupId, :userid")
                .insert("group_act_record")
                .build();

        ScoreRule scoreRule = scoreRuleService.getRule(act.getScoreRule());
        Score score = new Score();
        score.setActId(act.getActId());
        score.setGroupId(act.getGroupId());
        score.setMoney(act.getMoney());
        score.setStatus(type);
        score.setAddTime(new Date());
        score.setUserid(userid);
        if(scoreRule!=null){
            if(type == 1){
                score.setScore(scoreRule.getScore());
            }else if(type == 2){
                score.setScore(scoreRule.getLeaveScore());
            }

            pubDao.executeBean(sql.sql(),score);
        }

    }

    @Autowired
    private PubDao pubDao;

    @Autowired
    private ScoreRuleService scoreRuleService;

}
