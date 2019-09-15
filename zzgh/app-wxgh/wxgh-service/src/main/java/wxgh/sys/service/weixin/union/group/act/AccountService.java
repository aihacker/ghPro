package wxgh.sys.service.weixin.union.group.act;

import com.libs.common.type.TypeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pub.dao.jdbc.PubDao;
import pub.dao.jdbc.sql.SQL;
import pub.dao.jdbc.sql.bean.Order;
import pub.error.ValidationError;
import wxgh.data.union.group.account.ActList;
import wxgh.data.union.group.act.UserList;
import wxgh.data.union.group.act.account.AccountData;
import wxgh.data.union.group.act.account.AccountInfo;
import wxgh.data.union.group.act.account.ScoreMoney;
import wxgh.entity.entertain.act.ActJoin;
import wxgh.entity.union.group.ActRecord;
import wxgh.param.union.group.account.ActParam;
import wxgh.param.union.group.act.UserParam;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2017/7/25.
 */
@Service
@Transactional(readOnly = true)
public class AccountService {

    public List<ActList> listAct(ActParam param) {
        SQL.SqlBuilder sql = new SQL.SqlBuilder()
                .table("act a")
                .field("a.id, a.name, a.info")
                .sys_file("a.img_id")
                .join("group g", "g.group_id = a.group_id")
                .where("a.has_score = :hasScore")
                .where("a.account = :account")
                .where("g.id = :groupId")
                .order("a.end_time", Order.Type.DESC);
        return pubDao.queryPage(sql, param, ActList.class);
    }

    /**
     * 活动积分结算
     *
     * @param actId
     * @param total
     */
    @Transactional
    public void account(Integer actId, Float total) {
        //更新活动实际费用
        String sql = "update t_act set real_money = ?, account = ? where id = ?";
        pubDao.execute(sql, total, 1, actId);

        //获取结算信息
        SQL getSql = new SQL.SqlBuilder()
                .table("act a")
                .field("a.act_id, a.group_id")
                .field("r.score, r.leave_score, r.out_score")
                .field("(select count(*) from t_act_join where act_id = a.act_id and type = 1) as joinNumb")
                .join("score_rule r", "a.score_rule = r.rule_id")
                .where("a.id = ?")
                .build();
        AccountInfo info = pubDao.query(AccountInfo.class, getSql.sql(), actId);

        Float everyPay;
        if (0 == info.getJoinNumb() || 0 == total) {
            everyPay = 0f;
        } else {
            everyPay = total / info.getJoinNumb();
            BigDecimal bd = new BigDecimal(everyPay);
            everyPay = bd.setScale(2, BigDecimal.ROUND_HALF_UP).floatValue();
        }

        //积分结算
        String joinSql = "select userid, type from t_act_join where act_id = ?";
        List<ActJoin> joins = pubDao.queryList(ActJoin.class, joinSql, info.getActId());
        if (TypeUtils.empty(joins)) {
            //一般情况下不可能为空
            throw new ValidationError("活动参与人数不能为空哦");
        }

        //新增group_act_record记录
        List<ActRecord> records = new ArrayList<>();
        List<ScoreMoney> scoreMonies = new ArrayList<>();
        for (ActJoin join : joins) {
            ActRecord record = new ActRecord();
            record.setStatus(join.getType());
            record.setAddTime(new Date());
            record.setUserid(join.getUserid());
            record.setGroupId(info.getGroupId());
            record.setActId(info.getActId());

            ScoreMoney scoreMoney = new ScoreMoney();
            scoreMoney.setUserid(join.getUserid());
            scoreMoney.setGroupId(info.getGroupId());

            if (join.getType() == 1) {
                record.setScore(info.getScore());
                record.setMoney(everyPay);

                scoreMoney.setScore(info.getScore());
                scoreMoney.setMoney(-everyPay);
            } else if (join.getType() == 2) {
                record.setScore(info.getLeaveScore());
                record.setMoney(0f);

                scoreMoney.setScore(info.getLeaveScore());
                scoreMoney.setMoney(0f);
            } else if (join.getType() == 3) {
                record.setScore(info.getOutScore());
                record.setMoney(0f);

                scoreMoney.setScore(info.getOutScore());
                scoreMoney.setMoney(0f);
            }

            records.add(record);
            scoreMonies.add(scoreMoney);
        }
        SQL recordSql = new SQL.SqlBuilder()
                .field("act_id, score, money, status, add_time, userid, group_id")
                .value(":actId, :score, :money, :status, :addTime, :userid, :groupId")
                .insert("group_act_record")
                .build();
        pubDao.executeBatch(recordSql.sql(), records);

        //更新用户积分和会费
        SQL updateSql = new SQL.SqlBuilder()
                .set("score = score+:score")
                .set("money = money+:money")
                .where("userid = :userid")
                .where("group_id = :groupId")
                .update("group_user")
                .build();
        pubDao.executeBatch(updateSql.sql(), scoreMonies);
    }

    @Transactional
    public void addOutJoins(Integer actId) {
        //获取缺席用户列表
        String sql = "select userid from t_group_user \n" +
                "where group_id = (select group_id from t_act where id = ?)\n" +
                "and userid not in(select j.userid from t_act_join j join t_act a on a.act_id = j.act_id where a.id = ?)" +
                " and status = 1";
        List<String> userids = pubDao.queryList(String.class, sql, actId, actId);

        if (!TypeUtils.empty(userids)) {
            List<ActJoin> joins = new ArrayList<>();
            for (String userid : userids) {
                ActJoin join = new ActJoin();
                join.setId(actId);
                join.setAddTime(new Date());
                join.setType(ActJoin.TYPE_OUT);
                join.setUserid(userid);
                joins.add(join);
            }

            String addSql = "INSERT INTO t_act_join (userid, act_id, type, add_time)\n" +
                    "SELECT :userid, act_id, :type, :addTime FROM t_act where id = :id";
            pubDao.executeBatch(addSql, joins);
        }
    }

    @Transactional
    public void editJoinType(ActJoin join) {
        //判断用户是否已经报名
        SQL seSql = new SQL.SqlBuilder()
                .table("act_join j")
                .join("act a", "j.act_id = a.act_id")
                .field("j.act_id, j.id, j.type")
                .where("a.id = ? and j.userid = ?").build();
        ActJoin actJoin = pubDao.query(ActJoin.class, seSql.sql(), join.getId(), join.getUserid());

        if (actJoin == null) { //保存
            String sql = "INSERT INTO t_act_join (userid, act_id, type, add_time)\n" +
                    "SELECT :userid, act_id, :type, :addTime FROM t_act where id = :id";
            join.setAddTime(new Date());
            pubDao.executeBean(sql, join);
        } else {
            if (join.getType().equals(actJoin.getType())) {
                return;
            }
            String sql = "update t_act_join set type = ? where id = ?";
            pubDao.execute(sql, join.getType(), actJoin.getId());
        }
    }

    /**
     * 获取活动结算信息
     *
     * @param actId
     * @return
     */
    public AccountData getAccountData(Integer actId) {
        SQL sql = new SQL.SqlBuilder()
                .table("act a")
                .field("a.name as actName, a.id, a.account")
                .field("r.score, r.leave_score, r.out_score")
                .field("(select count(*) from t_act_join where act_id = a.act_id and type = 1) as joinNumb")
                .field("(select count(*) from t_group_user where group_id = a.group_id and status = 1) as numb")
                .join("score_rule r", "r.rule_id = a.score_rule")
                .where("a.id = ?")
                .build();
        return pubDao.query(AccountData.class, sql.sql(), actId);
    }

    /**
     * 获取指定活动参与人员
     *
     * @param param
     * @return
     */
    public List<UserList> getUserList(UserParam param) {
        SQL.SqlBuilder sql = new SQL.SqlBuilder()
                .field("u.name as username, u.avatar");
        if (param.getType() != null && param.getType().intValue() == 3) {
            sql.table("group_user gu")
                    .field("gu.userid, 3 as type")
                    .join("user u", "u.userid = gu.userid")
                    .where("gu.group_id = (select group_id from t_act where id = :actId)")
                    .where("gu.userid not in(select j.userid from t_act_join j join t_act a on a.act_id = j.act_id where a.id = :actId and j.type != 3)")
                    .where("gu.status = 1");
        } else {
            sql.table("act_join j")
                    .field("j.userid, j.type")
                    .join("user u", "u.userid = j.userid")
                    .join("act a", "a.act_id = j.act_id")
                    .where("a.id = :actId");
            if (param.getType() != null) {
                sql.where("j.type = :type");
            }
            if (!param.getPageIs()) {
                sql.limit(":rowsPerPage");
            }
        }
        return pubDao.queryPage(sql, param, UserList.class);
    }

    @Autowired
    private PubDao pubDao;

}
