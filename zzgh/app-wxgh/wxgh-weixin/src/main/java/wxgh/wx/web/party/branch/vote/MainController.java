package wxgh.wx.web.party.branch.vote;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import pub.dao.jdbc.PubDao;
import pub.spring.web.mvc.model.Model;
import wxgh.app.session.bean.SeUser;
import wxgh.app.session.user.UserSession;
import wxgh.entity.party.vote.PartyBranchVote;

import java.util.List;

@Controller
public class MainController {

    @Autowired
    private PubDao pubDao;

    @RequestMapping
    public void index(){

    }

    @RequestMapping
    public void show(Integer id,Model model){
        String sql = "select * from t_party_branch_vote where id = ?";
        PartyBranchVote partyBranchVote = pubDao.query(PartyBranchVote.class,sql,id);
        model.put("s",partyBranchVote);
    }

    @RequestMapping
    public void result(Integer id,Model model){
        SeUser user = UserSession.getUser();
       /* String sql = "select u.party_id from t_party_dept_user u where u.userid = ?";
        Integer partyId = pubDao.query(Integer.class,sql,user.getUserid());
        String sqlCount = "select count(u.userid) as num from t_party_dept_user u where u.party_id = ?";
        Integer num = pubDao.query(Integer.class,sqlCount,partyId);*/
        String sqlJoin = "SELECT \n" +
                " COUNT(u.userid) AS num\n" +
                "FROM\n" +
                /*"  t_party_dept_user u \n" +*/
                "  t_user u \n" +
                "  JOIN t_party_branch_vote_record r \n" +
                "    ON u.`userid` = r.userid \n" +
                "WHERE r.vote_id = ?\n" +//u.party_id = ? and
                "GROUP BY r.`userid` ";
        List<Integer> joinNums = pubDao.queryList(Integer.class,sqlJoin,id);//,partyid
        String sqlJoinType = "SELECT COUNT(userid) AS num FROM t_party_branch_vote_record WHERE vote_id = ? GROUP BY userid";
        List<Integer> joinNumsNotZhiBu = pubDao.queryList(Integer.class,sqlJoinType,id);

        /*model.put("num",num);*/
        String typeSql = "select type from t_party_branch_vote where id = ?";
        Integer voteType = pubDao.query(Integer.class,typeSql,id);
        if(voteType == 3 ){
            if(joinNums.size()>0){
                model.put("joinNum",joinNums.size());
            }else{
                model.put("joinNum",0);
            }
        }else{
            if(joinNumsNotZhiBu.size()>0){
                model.put("joinNum",joinNumsNotZhiBu.size());
            }else{
                model.put("joinNum",0);
            }
        }
        model.put("voteType",voteType);
    }

    @RequestMapping
    public void resultlist(){

    }

    @RequestMapping
    public void resultchart(Integer id,Integer voteid,Model model){
        String sqlCount = "select count(u.userid) as num from t_party_dept_user u where u.party_id = ?";
        Integer num = pubDao.query(Integer.class,sqlCount,id);
        String sqlJoin = "SELECT \n" +
                " COUNT(u.userid) AS num\n" +
                "FROM\n" +
                "  t_party_dept_user u \n" +
                "  JOIN t_party_branch_vote_record r \n" +
                "    ON u.`userid` = r.userid \n" +
                "WHERE u.party_id = ? and r.vote_id = ?\n" +
                "GROUP BY r.`userid` ";
        List<Integer> joinNums = pubDao.queryList(Integer.class,sqlJoin,id,voteid);;

        model.put("num",num);
        if(joinNums.size()>0){
            model.put("joinNum",joinNums.size());
        }else{
            model.put("joinNum",0);
        }
    }

    @RequestMapping
    public void showall(Integer id,Model model){
        SeUser user = UserSession.getUser();
        String sql = "select u.party_id from t_party_dept_user u where u.userid = ?";
        Integer partyId = pubDao.query(Integer.class,sql,user.getUserid());
        String sqlCount = "select count(u.userid) as num from t_party_dept_user u where u.party_id = ?";
        Integer num = pubDao.query(Integer.class,sqlCount,partyId);
        String sqlJoin = "SELECT \n" +
                " COUNT(u.userid) AS num\n" +
                "FROM\n" +
                "  t_party_dept_user u \n" +
                "  JOIN t_party_branch_vote_record r \n" +
                "    ON u.`userid` = r.userid \n" +
                "WHERE u.party_id = ? and r.vote_id = ?\n" +
                "GROUP BY r.`userid` ";
        List<Integer> joinNums = pubDao.queryList(Integer.class,sqlJoin,partyId,id);;
        String sqlJoinType = "SELECT COUNT(userid) AS num FROM t_party_branch_vote_record WHERE vote_id = ? GROUP BY userid";
        List<Integer> joinNumsNotZhiBu = pubDao.queryList(Integer.class,sqlJoinType,id);

        model.put("num",num);
        String typeSql = "select type from t_party_branch_vote where id = ?";
        Integer voteType = pubDao.query(Integer.class,typeSql,id);

            if(joinNumsNotZhiBu.size()>0){
                model.put("joinNum",joinNumsNotZhiBu.size());
            }else{
                model.put("joinNum",0);
            }
        model.put("voteType",voteType);
    }
}
