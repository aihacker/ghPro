package wxgh.sys.service.weixin.pub.score;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pub.dao.jdbc.PubDao;
import pub.dao.jdbc.sql.SQL;
import pub.dao.jdbc.sql.bean.Order;
import wxgh.entity.pub.score.Score;
import wxgh.entity.pub.score.ScoreExcel;
import wxgh.param.pub.score.ScoreGroup;
import wxgh.param.pub.score.ScoreParam;
import wxgh.param.pub.score.ScoreType;
import wxgh.param.pub.score.SimpleScore;

import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2017/7/31.
 */
@Service
@Transactional(readOnly = true)
public class ScoreService {

    private void buildParam(ScoreParam param, SQL.SqlBuilder sql) {
        if (param.getGroup() != null) {
            sql.where("score_group = " + param.getGroup().getGroup());
        }
        if (param.getType() != null) {
            sql.where("score_type = " + param.getType().getType());
        }
        if (param.getStatus() != null) {
            sql.where("status = :status");
        }
        if (param.getUserid() != null) {
            sql.where("userid = :userid");
        }
        if (param.getById() != null) {
            sql.where("by_id = :byId");
        }
        if (param.getInTypes() != null) {
            sql.where("score_type in(" + param.getInTypes() + ")");
        }
        if (param.getNotTypes() != null) {
            sql.where("score_type not in(" + param.getNotTypes() + ")");
        }
    }

    /**
     * 判断是否已经添加积分
     *
     * @param param
     * @return
     */
    public boolean hasScore(ScoreParam param) {
        SQL.SqlBuilder sql = new SQL.SqlBuilder()
                .table("score")
                .field("id")
                .limit("1");
        buildParam(param, sql);
        Integer id = pubDao.query(sql.build().sql(), param, Integer.class);
        return id != null;
    }

    public List<Score> listScore(ScoreParam param) {
        SQL.SqlBuilder sql = new SQL.SqlBuilder()
                .table("score");
        buildParam(param, sql);
        sql.order("id", Order.Type.DESC);
        System.out.println("sql="+sql.toString());
        return pubDao.queryPage(sql, param, Score.class);
    }


    /**
     * 查询场地总分
     *
     * @param param
     * @return
     */
    public Float sumScore(ScoreParam param) {
        SQL.SqlBuilder sql = new SQL.SqlBuilder()
                .table("score")
                .field("sum(score)").where("add_time > '2017-05-15'");
        buildParam(param, sql);
        Float sum = pubDao.query(sql.build().sql(), param, Float.class);
        return sum == null ? 0 : sum;
    }

    /**
     * 查询可兑换总分
     *
     * @param param
     * @return
     */
    public Float sumScoreExchange(ScoreParam param) {
        SQL.SqlBuilder sql = new SQL.SqlBuilder()
                .table("score")
                .field("sum(score)");
        buildParam(param, sql);
        Float sum = pubDao.query(sql.build().sql(), param, Float.class);
        return sum == null ? 0 : sum;
    }

    /**
     * 查询总分
     *
     * @param param
     * @return
     */
    public Float sumTotalScore(ScoreParam param) {
        SQL.SqlBuilder sql = new SQL.SqlBuilder()
                .table("score")
                .field("sum(score)");
        buildParam(param, sql);
        Float sum = pubDao.query(sql.build().sql(), param, Float.class);
        return sum == null ? 0 : sum;
    }

    /**
     * 添加个人积分
     *
     * @param score
     * @param type
     */
    @Transactional
    public void user(SimpleScore score, ScoreType type) {
        addScore(score, ScoreGroup.USER, type);
    }

    /**
     * 青年部落积分
     *
     * @param score
     * @param type
     */
    @Transactional
    public void tribe(SimpleScore score, ScoreType type) {
        addScore(score, ScoreGroup.TRIBE, type);
    }

    /**
     * 纪检积分
     *
     * @param score
     * @param type
     */
    @Transactional
    public void di(SimpleScore score, ScoreType type) {
        addScore(score, ScoreGroup.DI, type);
    }


    private void addScore(SimpleScore simpleScore, ScoreGroup group, ScoreType type) {
        SQL sql = new SQL.SqlBuilder()
                .field("userid, score, score_group, score_type, status, info, add_time, by_id")
                .value(":userid, :score, :scoreGroup, :scoreType, :status, :info, :addTime, :byId")
                .insert("score")
                .build();
        if (simpleScore.getStatus() == null) {
            simpleScore.setStatus(1);
        }
        Score score = new Score();
        score.setStatus(simpleScore.getStatus());
        score.setUserid(simpleScore.getUserid());
        score.setInfo(simpleScore.getInfo());
        score.setScore(simpleScore.getScore());
        score.setById(simpleScore.getById());
        score.setAddTime(new Date());
        score.setScoreGroup(group.getGroup());
        score.setScoreType(type.getType());

        pubDao.executeBean(sql.sql(), score);
    }

    public List<ScoreExcel> downexcel(){

        String sql = "select s1.userid,s1.uname as username,d.name as dept,s1.department,s2.score as loca_score,s3.score as locaPay_score,s4.score as gh_score,s5.score as ghPay_score from " +
                "(SELECT s.userid as userid,t.deptid as deptid,t.name as uname,t.department as department from t_score s " +
                "INNER JOIN t_user t " +
                "ON t.userid=s.userid GROUP BY t.userid) as s1 " +
                "LEFT JOIN " +
                "(select userid,sum(score) as score from t_score WHERE score_type=2 GROUP BY userid) as s2 " +
                "ON s1.userid=s2.userid " +
                "LEFT JOIN " +
                "(select userid,sum(score) as score from t_score WHERE score_type=5 GROUP BY userid) as s3 " +
                "ON s1.userid=s3.userid " +
                "LEFT JOIN " +
                "(select userid,sum(score) as score from t_score WHERE score_type=1 GROUP BY userid) as s4 " +
                "ON s1.userid=s4.userid " +
                "LEFT JOIN " +
                "(select userid,sum(score) as score from t_score WHERE score_type=6 GROUP BY userid) as s5 " +
                "ON s1.userid=s5.userid " +
                "INNER JOIN t_dept d " +
                "ON d.id=s1.deptid";
        List<ScoreExcel> scoreExcels = pubDao.queryList(ScoreExcel.class,sql,null);

        return scoreExcels;
    }

    @Autowired
    private PubDao pubDao;

    public List<Score> listScore_pay(ScoreParam param) {
        String sql = "select *from t_score where score_group = ? AND status = ? AND userid = ? AND score_type in(5,6)";
        List<Score> list = pubDao.queryList(Score.class,sql,param.getGroup(),param.getStatus(),param.getUserid());
        //System.out.println("size="+list.size());
        return list;
    }
}
