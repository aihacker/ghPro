package wxgh.sys.service.admin.tribe;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pub.dao.jdbc.PubDao;
import pub.dao.jdbc.sql.SQL;
import wxgh.entity.tribe.Score;
import wxgh.entity.tribe.TribeAct;
import wxgh.param.pub.score.ScoreGroup;
import wxgh.param.pub.score.ScoreType;
import wxgh.param.tribe.admin.PointParam;

import java.util.List;

/**
 * Created by cby on 2017/8/21.
 */
@Service
@Transactional(readOnly = true)
public class PointService {

    @Autowired
    private PubDao pubDao;
    //获取所有的活动名
    public List<TribeAct> getAllTribeAct(){
        SQL sql=new SQL.SqlBuilder()
                .table("t_tribe_act")
                .field("id,theme")
                .build();
        List<TribeAct> tribeActs = pubDao.queryList(TribeAct.class, sql.sql());
        return tribeActs;
    }
    //查询出所有这个活动下的用户的积分信息

    public List<Score> getScoreMessage(PointParam pointParam){
        ScoreGroup tribe = ScoreGroup.TRIBE;
        Integer group = tribe.getGroup();
        ScoreType tribeAct = ScoreType.TRIBE_ACT;
        Integer type = tribeAct.getType();
        SQL sql=new SQL.SqlBuilder()
                .table("t_score s")
                .field("u.name as username,d.name as deptname,u.mobile as phone,s.score")
                .join("t_user u","s.userid=u.userid")
                .join("t_tribe_act a","a.id=s.by_id")
                .join("t_dept d","d.id=u.deptid")
                .where(" s.score_group=?")
                .where("s.score_type=?")
                .where("s.by_id=?")
                .build();
        List<Score> scores = pubDao.queryList(Score.class, sql.sql(),group,type,pointParam.getActId());
        return scores;
    }

    //更新积分
    @Transactional
    public void updateScore(Integer by_id,Float point,String username){
        ScoreGroup tribe = ScoreGroup.TRIBE;
        Integer group = tribe.getGroup();
        ScoreType tribeAct = ScoreType.TRIBE_ACT;
        Integer type = tribeAct.getType();
        String sql="UPDATE t_score s\n" +
                "SET score = ?\n" +
                "WHERE\n" +
                "\ts.score_group = ?\n" +
                "AND s.score_type = ?\n" +
                "AND by_id = ?\n" +
                "AND s.userid = (\n" +
                "\tSELECT\n" +
                "\t\tuserid\n" +
                "\tFROM\n" +
                "\t\tt_user\n" +
                "\tWHERE\n" +
                "\t\tNAME = ?\n" +
                ")";
        pubDao.execute(sql,point,group,type,by_id,username);
    }

    //删除一条数据
    @Transactional
    public void delete(Integer by_id,String username){
        ScoreGroup tribe = ScoreGroup.TRIBE;
        Integer group = tribe.getGroup();
        ScoreType tribeAct = ScoreType.TRIBE_ACT;
        Integer type = tribeAct.getType();
        String sql="DELETE\n" +
                "FROM\n" +
                "\tt_score\n" +
                "WHERE\n" +
                "\tscore_group = ?\n" +
                "AND score_type = ?\n" +
                "AND by_id =? AND userid = (\n" +
                "\tSELECT\n" +
                "\t\tuserid\n" +
                "\tFROM\n" +
                "\t\tt_user\n" +
                "\tWHERE\n" +
                "\t\tNAME = ?\n" +
                ")";

        pubDao.execute(sql,group,type,by_id,username);
    }

}
