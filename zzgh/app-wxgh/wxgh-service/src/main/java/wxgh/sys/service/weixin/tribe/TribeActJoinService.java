package wxgh.sys.service.weixin.tribe;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pub.dao.jdbc.PubDao;
import pub.dao.jdbc.sql.SQL;
import pub.error.ValidationError;
import wxgh.data.party.tribe.JoinVerify;
import wxgh.entity.tribe.TribeActJoin;
import wxgh.param.party.tribe.JoinParam;
import wxgh.sys.service.weixin.pub.UserService;

import java.util.List;

/**
 * @author hhl
 * @create 2017-08-04
 **/
@Service
@Transactional(readOnly = true)
public class TribeActJoinService {

    public List<TribeActJoin> listJoin(JoinParam param) {
        SQL.SqlBuilder sql = new SQL.SqlBuilder()
                .table("tribe_act_join j")
                .field("j.id")
                .field("u.name, u.avatar")
                .field("d.name as deptname")
                .join("user u", "u.userid = j.userid")
                .join("dept d", "d.id = u.deptid")
                .where("j.act_id = :actId")
                .where("j.status = :status");
        return pubDao.queryPage(sql, param, TribeActJoin.class);
    }

    public TribeActJoin getOne(TribeActJoin tribeActJoin) {
        String sql = "select * from t_tribe_act_join where userid = ? and act_id = ?";
        return pubDao.query(TribeActJoin.class, sql, tribeActJoin.getUserid(), tribeActJoin.getActId());
    }

    public List<TribeActJoin> getAll(TribeActJoin tribeActJoin) {
        String sql = "select * from t_tribe_act_join where act_id = ?";
        return pubDao.queryList(TribeActJoin.class, sql, tribeActJoin.getActId());
    }

    @Transactional
    public void addManage(TribeActJoin actJoin) {
        SQL veriSql = new SQL.SqlBuilder()
                .table("manage_act a")
                .field("a.total_number")
                .field("(select ifnull(1, 0) from t_manage_act_join where act_id = a.id and userid = ?) as joinIs")
                .field("(select count(*) from t_manage_act_join where act_id = a.id and status=1) as joinNumb")
                .where("a.id = ?")
                .build();

        JoinVerify verify = pubDao.query(JoinVerify.class, veriSql.sql(), actJoin.getUserid(), actJoin.getActId());
        if (verify.getJoinIs() != null && verify.getJoinIs() == 1) {
            throw new ValidationError("你已报名哦");
        }
        if (verify.getTotalNumber() > 0 && verify.getJoinNumb() >= verify.getTotalNumber()) {
            throw new ValidationError("名额已满");
        }



        SQL sql = new SQL.SqlBuilder()
                .field("userid,status,integral,join_time,act_id")
                .value(":userid,:status,:integral,:joinTime,:actId")
                .insert("manage_act_join").build();
        pubDao.executeBean(sql.sql(), actJoin);

        //插入报名积分
       /* SimpleScore score = new SimpleScore();
        score.setById(actJoin.getActId().toString());
        score.setUserid(actJoin.getUserid());
        score.setStatus(0);
        score.setInfo("参加活动积分");
        scoreService.tribe(score, ScoreType.TRIBE_ACT);*/
    }

    public List<TribeActJoin> listJoinManage(JoinParam param) {
        SQL.SqlBuilder sql = new SQL.SqlBuilder()
                .table("manage_act_join j")
                .field("j.id")
                .field("u.name, u.avatar")
                .field("d.name as deptname")
                .join("user u", "u.userid = j.userid")
                .join("dept d", "d.id = u.deptid")
                .where("j.act_id = :actId")
                .where("j.status = :status");
        return pubDao.queryPage(sql, param, TribeActJoin.class);
    }

    @Transactional
    public void add(TribeActJoin actJoin) {
        SQL veriSql = new SQL.SqlBuilder()
                .table("tribe_act a")
                .field("a.total_number")
                .field("(select ifnull(1, 0) from t_tribe_act_join where act_id = a.id and userid = ?) as joinIs")
                .field("(select count(*) from t_tribe_act_join where act_id = a.id and status=1) as joinNumb")
                .where("a.id = ?")
                .build();

        JoinVerify verify = pubDao.query(JoinVerify.class, veriSql.sql(), actJoin.getUserid(), actJoin.getActId());
        if (verify.getJoinIs() != null && verify.getJoinIs() == 1) {
            throw new ValidationError("你已报名哦");
        }
        if (verify.getTotalNumber() > 0 && verify.getJoinNumb() >= verify.getTotalNumber()) {
            throw new ValidationError("名额已满");
        }


        SQL sql = new SQL.SqlBuilder()
                .field("userid,status,integral,join_time,act_id")
                .value(":userid,:status,:integral,:joinTime,:actId")
                .insert("tribe_act_join").build();
        pubDao.executeBean(sql.sql(), actJoin);
    }

    @Autowired
    private PubDao pubDao;

    @Autowired
    private UserService userService;
}
