package wxgh.sys.service.weixin.party.match;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pub.dao.jdbc.PubDao;
import pub.dao.jdbc.sql.SQL;
import pub.dao.jdbc.sql.bean.Order;
import wxgh.data.union.group.user.UserData;
import wxgh.entity.party.match.SheyingMatchJoin;
import wxgh.entity.party.match.SheyingMatchJoinVote;
import wxgh.param.party.match.JoinQuery;
import wxgh.param.union.group.user.ListParam;
import wxgh.sys.dao.party.match.SheyingMatchJoinDao;

import java.util.List;

/**
 *----------------------------------------------------------
 * @Description 
 *----------------------------------------------------------
 * @Author  Ape
 *----------------------------------------------------------
 * @Email <16511660@qq.com>
 *----------------------------------------------------------
 * @Date 2017-08-07 15:48
 *----------------------------------------------------------
 */
@Service
@Transactional(readOnly = true)
public class SheyingMatchJoinService {

    public List<SheyingMatchJoin> getData(SheyingMatchJoin sheyingMatchJoin) {
        return sheyingMatchJoinDao.getData(sheyingMatchJoin);
    }

    public List<UserData> listUser(ListParam param){
        SQL.SqlBuilder sql = new SQL.SqlBuilder()
                .table("sheying_match_join j")
                .field("j.id, j.userid, j.status")
                .field("u.name as username, u.avatar")
                .join("user u", "u.userid = j.userid")
                .where("j.mid = :groupId");
            if (param.getName() != null)
                sql.where("u.name like '%" + param.getName() + "%'");
        return pubDao.queryPage(sql, param, UserData.class);
    }

    public Integer check(SheyingMatchJoin sheyingMatchJoin){
        SQL.SqlBuilder sql = new SQL.SqlBuilder()
                .field("id")
                .table("sheying_match_join")
                .where("userid = :userid")
                .where("mid = :mid")
                .limit("1");
        if(sheyingMatchJoin.getType() != null)
            sql.where("type = :type");
        Integer id = pubDao.query(sql.select().build().sql(), sheyingMatchJoin, Integer.class);
        return id;
    }

    public SheyingMatchJoin get(String uid, Integer id){
        SQL sql = new SQL.SqlBuilder()
                .table("sheying_match_join")
                .field("*")
                .where("userid = ? and id = ?")
                .limit("1")
                .select()
                .build();
        return pubDao.query(SheyingMatchJoin.class, sql.sql(), uid, id);
    }
    
    @Transactional
    public void delData(Integer id) {
        sheyingMatchJoinDao.delete(id);
    }

    
    @Transactional
    public void addData(SheyingMatchJoin sheyingMatchJoin) {
        sheyingMatchJoinDao.save(sheyingMatchJoin);
    }

    
    @Transactional
    public void updateJoin(SheyingMatchJoin join) {
        sheyingMatchJoinDao.update(join);
    }

    
    @Transactional
    public void updateStatus(JoinQuery query) {
        String sql = "update t_sheying_match_join set status=? where id=? and userid=? and type=?";
        pubDao.execute(sql, query.getStatus(), query.getMid(), query.getUserid(), query.getType());
    }

    @Transactional
    public void updateStatus(Integer id) {
        String sql = "update t_sheying_match_join set status=1 where id=? ";
        pubDao.execute(sql, id);
    }

    @Transactional
    public void updateStatus(Integer id, Integer status) {
        String sql = "update t_sheying_match_join set status=? where id=? ";
        pubDao.execute(sql, status, id);
    }

    
    public Integer getStatus(JoinQuery query) {
        String sql = "select status from t_sheying_match_join where mid=? and userid=? and type=?";
        Integer status = pubDao.query(Integer.class, sql, query.getMid(), query.getUserid(), query.getType());
        return status;
    }

    
    public SheyingMatchJoin getStatusAndId(JoinQuery query) {
        String sql = "select status, id from t_sheying_match_join where mid=? and userid=? and type=? limit 1";
        return pubDao.query(SheyingMatchJoin.class, sql, query.getMid(), query.getUserid(), query.getType());
    }

    
    public SheyingMatchJoin getJoin(Integer id) {
        return sheyingMatchJoinDao.get(id);
    }

    /**
     * 统计当天用户的投票数
     * @param userid
     * @return
     */
    public int countVotesByDay(String userid){
        String sql = new SQL.SqlBuilder()
                .table("t_sheying_match_join_vote")
                .where("DATE_FORMAT(add_time,'%Y-%m-%d') = DATE_FORMAT(NOW(),'%Y-%m-%d')")
                .where("userid = ?")
                .field("count(*)")
                .select()
                .build()
                .sql();
        return pubDao.queryInt(sql, userid);
    }

    /**
     * 判断当天用户是否已投过票
     * @param userid
     * @param id
     * @return
     */
    public boolean checkVote(String userid, Integer id){
        String sql = new SQL.SqlBuilder()
                .table("t_sheying_match_join_vote")
                .where("DATE_FORMAT(add_time,'%Y-%m-%d') = DATE_FORMAT(NOW(),'%Y-%m-%d')")
                .where("userid = ?")
                .where("join_id = ?")
                .limit("1")
                .select()
                .build()
                .sql();
        SheyingMatchJoinVote vote = pubDao.query(SheyingMatchJoinVote.class, sql, userid, id);
        if(vote == null)
            return true;
        return false;
    }

    public List<SheyingMatchJoin> getJoins(JoinQuery query) {
//        List<SheyingMatchJoin> joins = sheyingMatchJoinDao.getJoins(query);

        // 2017-12-7 修改为当天
        SQL.SqlBuilder sql = new SQL.SqlBuilder()
                .table("t_sheying_match_join j")
                .field("j.*,u.name as username, !ISNULL((select v.id from t_sheying_match_join_vote v where v.userid = :curUserid\n" +
                        "        and v.join_id=j.id and DATE_FORMAT(v.add_time,'%Y-%m-%d') = DATE_FORMAT(NOW(),'%Y-%m-%d'))) as isVote,\n" +
                        "        (select count(DISTINCT id) from t_sheying_match_join_vote where join_id = j.id) as voteCount")
                .join("t_user u", "j.userid = u.userid");

        if(query.getMid() != null)
            sql.where("j.mid = :mid");
        if(query.getUserid() != null)
            sql.where("j.userid = :userid");
        if(query.getStatus() != null)
            sql.where("j.status = :status");
        if(query.getType() != null)
            sql.where("j.type = :type");
        sql.order("voteCount", Order.Type.DESC);

        List<SheyingMatchJoin> joins = pubDao.queryList(sql.select().build().sql(), query, SheyingMatchJoin.class);
        return joins;
    }

    public List<SheyingMatchJoin> getJoinsBy() {

        SQL.SqlBuilder sql = new SQL.SqlBuilder()
                .table("t_sheying_match_join j")
                .field("j.*,u.name as username, \n" +
                        "        (select count(DISTINCT id) from t_sheying_match_join_vote where join_id = j.id) as voteCount")
                .join("t_user u", "j.userid = u.userid");

            sql.where("j.mid = 1");
//            sql.where("j.userid = :userid");
            sql.where("(j.status = 1 or j.status = 2)");
        sql.order("voteCount", Order.Type.DESC);

        List<SheyingMatchJoin> joins = pubDao.queryList(SheyingMatchJoin.class, sql.select().build().sql());
        return joins;
    }

    @Autowired
    private SheyingMatchJoinDao sheyingMatchJoinDao;

    @Autowired
    private PubDao pubDao;
    
}

