package wxgh.sys.service.weixin.common.vote;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pub.dao.jdbc.PubDao;
import pub.dao.jdbc.sql.SQL;
import wxgh.data.union.SendInfo;
import wxgh.entity.common.vote.VoteOption;
import wxgh.entity.common.vote.VotePicOption;
import wxgh.entity.common.vote.Voted;
import wxgh.param.common.vote.QueryVoted;
import wxgh.param.common.vote.VoteQuery;
import wxgh.sys.dao.common.vote.VoteDao;

import java.util.List;

/**
 *----------------------------------------------------------
 * @Description 
 *----------------------------------------------------------
 * @Author  Ape
 *----------------------------------------------------------
 * @Email <16511660@qq.com>
 *----------------------------------------------------------
 * @Date 2017-07-29 15:02
 *----------------------------------------------------------
 */
@Service
@Transactional(readOnly = true)
public class VoteService {

    @Autowired
    private VoteDao voteDao;

    @Autowired
    private PubDao pubDao;

    
    public Integer countVoted(VoteQuery query) {
//        String sql = "select count(*) from t_voted where status=? and FIND_IN_SET(deptid, query_dept_child(query_dept_company_id2(?))) > 0";
//        String sql = "select count(*) from t_voted where status=? ";
//        return pubDao.query(Integer.class, sql, query.getStatus(), query.getDeptid());
        String sql = "select count(v.id) from t_voted v left join t_voted_user vu on v.id=vu.votedid where status=? and vu.userid=?";
//        return pubDao.query(Integer.class, sql, query.getStatus());
        return pubDao.query(Integer.class, sql, query.getStatus(), query.getUserid());
    }

    
    @Transactional
    public Integer AddVoted(Voted voted) {
        Integer votedid = voteDao.AddVoted(voted);
        String[] userIds = new String[1];
        if (voted.getUserids()!=null){
            userIds = voted.getUserids().split(",");
        }else{
            userIds[0] = "1";
        }
        for (String userId:userIds){
            String sql = "insert into t_voted_user (votedid,userid) values (?,?)";
            pubDao.execute(sql,votedid,userId);
        }
        return votedid;
    }

    
    public List<Voted> getAllVote() {
        return voteDao.getAllVote();
    }

    
    public Voted getVoteById(Integer id) {
        return voteDao.getVoteById(id);
    }

    
    @Transactional
    public void DelVoteById(Integer id) {
        voteDao.DelVoteById(id);
    }

    
    @Transactional
    public void UpdateStatus(Integer id) {
        voteDao.UpdateStatus(id);
    }

    
    public List<Voted> getVoteds(VoteQuery query) {
        return voteDao.getVoteds(query);
    }

    
    public List<Voted> getVoteNoOption(VoteQuery query) {
        return voteDao.getVoteNoOption(query);
    }

    
    @Transactional
    public Integer del(Integer id) {
        return voteDao.del(id);
    }

    
    @Transactional
    public Integer shenhe(QueryVoted queryVoted) {
        return voteDao.shenhe(queryVoted);
    }

    
    public Voted getOne(Integer id) {
        return voteDao.getOne(id);
    }

    
    public List<Voted> applyListRefresh(QueryVoted query) {
        return voteDao.applyListRefresh(query);
    }

    
    public List<Voted> applyListMore(QueryVoted query) {
        return voteDao.applyListMore(query);
    }

    public List<VoteOption> getOptionsByVoteId(Integer id) {
        String sql = "select * from t_voted_option where voteId=? and isdel=1;";
        return pubDao.queryList(VoteOption.class, sql, id);
    }

    public List<VotePicOption> getPicOptionsByVoteId(Integer id) {
        String sql = "select v.*,f.file_path path,f.thumb_path thumb from t_voted_option_pic v join t_sys_file f on v.options_file=f.file_id where voteId=? and isdel=1;";
        return pubDao.queryList(VotePicOption.class, sql, id);
    }

    @Transactional
    public Integer updateStatus(QueryVoted query){
        String sql = "update t_voted set status = ? where id = ?";
        return pubDao.execute(sql,query.getStatus(),query.getId());
    }

    public String getUseridByVoteId(Integer id){
        SQL sql = new SQL.SqlBuilder()
                .table("voted")
                .field("userid")
                .where("id = ?")
                .limit("1")
                .select()
                .build();
        return pubDao.query(String.class, sql.sql(), id);
    }


    public List<String> getUserIds(Integer id){
        SQL sql = new SQL.SqlBuilder()
                .table("voted_user")
                .field("userid")
                .where("votedid = ?")
                .build();
        return pubDao.queryList(String.class,sql.sql(), id);
    }

    public List<Voted> getApplysNotInTargetDeptId(QueryVoted query, List<Integer> ids) {
        String in = "";
        if (ids != null) {
            for (Integer id : ids) {
                in += id + ",";
            }
            in = in.substring(0, in.length() - 1);
        }

        String and = "";
        if (query.getVoteOldestId() != null) {
            and = " and id<" + query.getVoteOldestId() + " ";
        }
        String sql = "SELECT * FROM t_voted\n" +
                "where deptid not in (" + in + ") and status=?\n" + and +
                "ORDER BY id DESC limit 0, 15";
        return pubDao.queryList(Voted.class, sql, query.getStatus());
    }

    
    public SendInfo getSendInfoById(Integer id) {
        String sql = "select '投票申请' as sendType,\n" +
                "                s.id as itemId,\n" +
                "                u.name as username,u.userid as userId,\n" +
                "                d.name as deptName\n" +
                "                from t_voted s join t_user u on u.userid=s.userid\n" +
                "                left join t_dept d on u.deptid=d.id \n" +
                "                where s.id=?;";
        return pubDao.query(SendInfo.class, sql, id);
    }
    
}

