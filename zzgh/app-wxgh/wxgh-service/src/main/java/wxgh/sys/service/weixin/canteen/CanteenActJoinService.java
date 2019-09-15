package wxgh.sys.service.weixin.canteen;

import com.libs.common.data.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pub.dao.jdbc.PubDao;
import pub.dao.jdbc.sql.SQL;
import pub.dao.jdbc.sql.bean.Order;
import pub.error.ValidationError;
import wxgh.app.session.user.UserSession;
import wxgh.data.entertain.act.JoinList;
import wxgh.entity.canteen.CanteenAct;
import wxgh.entity.canteen.CanteenActJoin;
import wxgh.entity.entertain.act.Act;
import wxgh.entity.entertain.act.ActJoin;
import wxgh.param.entertain.act.JoinParam;
import wxgh.sys.service.weixin.act.ActScoreService;
import wxgh.sys.service.weixin.entertain.act.ScoreRuleService;

import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2017/7/25.
 */
@Service
@Transactional(readOnly = true)
public class CanteenActJoinService {

    /**
     * 大型活动报名
     *
     * @param actId 活动ID
     * @param type  活动类型
     */
    @Transactional
    public void actJoinBig(Integer actId, Integer type) {
        String userid = UserSession.getUserid();
        //获取活动类型
        CanteenAct act = pubDao.query(CanteenAct.class, "select * from t_canteen_act where id = ?", actId);

        //判断用户是否已经报名
        SQL seSql = new SQL.SqlBuilder()
                .table("canteen_act_join j")
                .join("canteen_act a", "j.act_id = a.act_id")
                .field("j.act_id, j.id, j.type")
                .where("a.id = ? and j.userid = ?").build();
        CanteenActJoin actJoin = pubDao.query(CanteenActJoin.class, seSql.sql(), actId, userid);
        if (actJoin == null) { //保存
            if(type == 1){
                //判断报名人数
                SQL SqlNum = new SQL.SqlBuilder()
                        .table("canteen_act_join j")
                        .join("act a", "j.act_id = a.act_id")
                        .field("a.act_numb")
                        .where("j.type = 1")
                        .where("a.id = ?").build();
                List<CanteenAct> actJoins = pubDao.queryList(CanteenAct.class,SqlNum.sql(),actId);
                if(actJoins.size()>0){
                    if(actJoins.size()>=actJoins.get(0).getActNumb()){
                        return;
                    }
                }
            }
            String sql = "INSERT INTO t_canteen_act_join (userid, act_id, type, add_time)\n" +
                    "SELECT :userid, act_id, :type, :addTime FROM t_canteen_act where id = :id";
            actJoin = new CanteenActJoin();
            actJoin.setId(actId);
            actJoin.setUserid(userid);
            actJoin.setType(type);
            actJoin.setAddTime(new Date());
            pubDao.executeBean(sql, actJoin);
                //todo
               if(act.getActType() >= Act.ACT_TYPE_BIG)
                actScoreService.add_union_score(act, userid, type);

        } else {
            if (type.equals(actJoin.getType())) {
                throw new ValidationError("对不起，你已经" + (type == 1 ? "报名" : "请假") + "了");
            }
            String sql="";
            if(type == 3){
                sql ="delete from t_canteen_act_join where id=?";
                pubDao.execute(sql,actJoin.getId());
            }else{
                sql = "update t_canteen_act_join set type = ?, add_time = ? where id = ?";
                pubDao.execute(sql, type, new Date(), actJoin.getId());
            }


            if(act.getActType() >= Act.ACT_TYPE_BIG)
                actScoreService.update_union_score(act, userid, type);
        }
    }

    /**
     * 活动报名
     *
     * @param actId 活动ID
     * @param type  活动类型
     */
    @Transactional
    public void actJoin(Integer actId, Integer type) {
        String userid = UserSession.getUserid();
        //获取活动类型
        CanteenAct act = pubDao.query(CanteenAct.class, "select a.group_id,a.money,a.act_id,a.has_score,a.score_rule, a.act_numb, a.act_type, a.regular, a.start_time, a.end_time, if(a.regular=1, (SELECT GROUP_CONCAT(`week`) from t_act_regular where a.act_id = act_id order by `week`),NULL) as `week` from t_canteen_act a where a.id = ?", actId);
        if (CanteenAct.ACT_TYPE_CANTEEN.equals(act.getActType())) {
            // 判断用户是否为协会成员
            String groupSql = "select status from t_canteen_user where userid = ? and group_id = (select group_id from t_canteen_act where id = ?)";
            Integer userStatus = pubDao.query(Integer.class, groupSql, userid, actId);
            if (userStatus == null || userStatus != 1) {
                throw new ValidationError("对不起，你不是协会成员");
            }
        } else if (CanteenAct.ACT_TYPE_TEAM.equals(act.getActType())) {
            String teamSql = "select status from t_chat_user where userid = ? and group_id = (select group_id from t_canteen_act where id = ?)";
            Integer userStatus = pubDao.query(Integer.class, teamSql, userid, actId);
            if (userStatus == null || userStatus != 1) {
                throw new ValidationError("对不起，你不是团队成员");
            }
        }/* else if (act.getActType() >= Act.ACT_TYPE_BIG) { //大型活动
            String joinNumbSql = new SQL.SqlBuilder()
                    .table("act_join")
                    .where("act_id = ? and type = ?")
                    .count()
                    .build()
                    .sql();
            Integer joinNumb = pubDao.queryInt(joinNumbSql, act.getActId(), 1);
            if (act.getActNumb() <= 0 && joinNumb >= act.getActNumb()) {
                throw new ValidationError("对不起 ，报名人数已达到最大限额~");
            }
        }*/

        //判断用户是否已经报名
        CanteenActJoin actJoin;
        Integer dateId = null;
        SQL.SqlBuilder seSql = new SQL.SqlBuilder()
                .table("canteen_act_join j")
                .join("canteen_act a", "j.act_id = a.act_id")
                .field("j.act_id, j.id, j.type");
        if (act.getRegular() == 0) {
            seSql.where("a.id = ? and j.userid = ?");
            actJoin = pubDao.query(CanteenActJoin.class, seSql.build().sql(), actId, userid);
        } else {
            dateId = DateUtils.getNearDate(act.getStartTime(), act.getWeek());
            seSql.where("a.id = ? and j.userid = ? and j.dateid = ?");
            actJoin = pubDao.query(CanteenActJoin.class, seSql.build().sql(), actId, userid, dateId);
        }


//            if(type == 1){
//                //判断报名人数
//                SQL SqlNum = new SQL.SqlBuilder()
//                        .table("act_join j")
//                        .join("act a", "j.act_id = a.act_id")
//                        .field("a.act_numb")
//                        .where("j.type = 1")
//                        .where("a.id = ?").build();
//                List<Act> actJoins = pubDao.queryList(Act.class,SqlNum.sql(),actId);
//                if(actJoins.size()>0){
//                    if(actJoins.size()>=actJoins.get(0).getActNumb()){
//                        return;
//                    }
//                }
//            }

        if(actJoin != null){
            // 已有参加记录
            if(actJoin.getType() != null){

                String updateSql = "update t_canteen_act_join set type = ?, add_time = ? where id = ?";
                pubDao.execute(updateSql, type, new Date(), actJoin.getId());

                if(act.getHasScore() != null && act.getActType() != null)
                    if(act.getHasScore().equals(1) && act.getActType() >= Act.ACT_TYPE_BIG){
                        actScoreService.update_union_score(act, userid, type);
                    }

            }
        }else{  // 第一次操作报名 或者 请假

            // 给予大型参加活动增加积分
            if(act.getHasScore() != null && act.getActType() != null)
                if(act.getHasScore().equals(1) && act.getActType() >= Act.ACT_TYPE_BIG){
                    actScoreService.add_union_score(act, userid, type);
                }

            //  插入数据
            String sql = "INSERT INTO t_canteen_act_join (userid, act_id, type, add_time, dateid)\n" +
                    "SELECT :userid, act_id, :type, :addTime, :dateid FROM t_canteen_act where id = :id";
            actJoin = new CanteenActJoin();
            actJoin.setId(actId);
            actJoin.setUserid(userid);
            actJoin.setType(type);
            actJoin.setAddTime(new Date());
            actJoin.setDateid(dateId);

            pubDao.executeBean(sql, actJoin);
        }

        if(false)
        if (actJoin == null) { // 保存参加
            //给根据积分规则给用户积分进行相应操作
            if(act.getHasScore() != null)
                if(act.getHasScore().equals(1) && act.getActType() >= Act.ACT_TYPE_BIG){
                    actScoreService.add_union_score(act,userid,type);
                }

            //  插入数据
            String sql = "INSERT INTO t_canteen_act_join (userid, act_id, type, add_time, dateid)\n" +
                    "SELECT :userid, act_id, :type, :addTime, :dateid FROM t_canteen_act where id = :id";
            actJoin = new CanteenActJoin();
            actJoin.setId(actId);
            actJoin.setUserid(userid);
            actJoin.setType(type);
            actJoin.setAddTime(new Date());
            actJoin.setDateid(dateId);

            pubDao.executeBean(sql, actJoin);
        } else {  // 已经参加
            if (type.equals(actJoin.getType())) {
                throw new ValidationError("对不起，你已经" + (type == 1 ? "报名" : "请假") + "了");
            }
            String sql = "update t_canteen_act_join set type = ?, add_time = ? where id = ?";
            pubDao.execute(sql, type, new Date(), actJoin.getId());
            actScoreService.update_union_score(act,userid,type);
        }
    }

    public List<JoinList> listJoins(JoinParam param) {
        SQL.SqlBuilder sql = new SQL.SqlBuilder()
                .table("canteen_act_join j")
                .field("j.userid, j.add_time")
                .field("u.name as username, u.avatar,u.mobile as mobile,u.department as dept")
                .field("d.name as deptname")
                .join("user u", "j.userid = u.userid")
                .join("dept d", "u.deptid = d.id")
                .order("j.add_time", Order.Type.DESC);
        if (param.getActId() != null) {
            sql.join("canteen_act a", "a.act_id = j.act_id")
                    .where("a.id = :actId");
        }
        if (param.getType() != null) {
            sql.where("j.type = :type");
        }
        if (param.getDateid() != null) {
            sql.where("j.dateid = :dateid");
        }
        return pubDao.queryPage(sql, param, JoinList.class);
    }


    @Autowired
    private PubDao pubDao;

    @Autowired
    private ScoreRuleService scoreRuleService;

    @Autowired
    private ActScoreService actScoreService;
}
