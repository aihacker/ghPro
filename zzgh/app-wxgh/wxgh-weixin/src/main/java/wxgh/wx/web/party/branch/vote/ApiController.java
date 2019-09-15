package wxgh.wx.web.party.branch.vote;

import com.libs.common.json.JsonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import pub.dao.jdbc.PubDao;
import pub.dao.jdbc.sql.SQL;
import pub.dao.page.Page;
import pub.spring.web.mvc.ActionResult;
import pub.spring.web.mvc.model.Model;
import wxgh.app.session.bean.SeUser;
import wxgh.app.session.user.UserSession;
import wxgh.data.party.PartyDeptUser;
import wxgh.data.party.branch.EchartsData;
import wxgh.data.party.branch.PartyDept;
import wxgh.data.party.di.AnswerInfo;
import wxgh.data.party.di.QuestionInfo;
import wxgh.entity.party.opinion.PartyOpinion;
import wxgh.entity.party.vote.AnswerText;
import wxgh.entity.party.vote.PartyBranchVote;
import wxgh.entity.party.vote.VoteRecord;
import wxgh.param.party.ArticleParam;
import wxgh.param.party.dept.PartyDeptParam;
import wxgh.param.party.vote.VoteParam;
import wxgh.param.party.vote.VoteRecordParam;

import javax.swing.*;
import java.util.Date;
import java.util.List;

@Controller
public class ApiController {

    @Autowired
    private PubDao pubDao;

    @RequestMapping
    public ActionResult get_list(ArticleParam param){
        param.setPageIs(true);
        param.setRowsPerPage(5);
        String sql = "select * from t_party_branch_vote where type = 3";
        List<PartyBranchVote> partyBranchVotes = pubDao.queryList(sql,param,PartyBranchVote.class);
        return ActionResult.okRefresh(partyBranchVotes,param);
    }

    @RequestMapping
    public ActionResult get_question(Integer examid){
        SQL sql = new SQL.SqlBuilder()
                .table("party_branch_vote_question q")
                .field("q.*")
                .field("q.id as answerId")
                .where("q.vote_id = ?").order("q.order_numb")
                .build();
        List<QuestionInfo> info = pubDao.queryList(QuestionInfo.class,sql.sql(),examid);
        if (info.size()>0) {
            String answerSql = "select id, name, order_numb from t_party_branch_vote_answer where question_id = ?";
            for(int i=0;i<info.size();i++){
                List<AnswerInfo> answerInfos = pubDao.queryList(AnswerInfo.class, answerSql, info.get(i).getId());
                info.get(i).setAnswers(answerInfos);
            }
        }
        return ActionResult.okWithData(info);
    }

    @RequestMapping
    public ActionResult add_record(Integer voteId,String answer){
        List<VoteRecord> voteRecords = JsonUtils.parseList(answer,VoteRecord.class);
        SeUser user = UserSession.getUser();

        SQL find = new SQL.SqlBuilder()
                .table("party_branch_vote_record")
                .where("vote_id = ? and userid = ?")
                .select().build();
        List<VoteRecord> finds = pubDao.queryList(VoteRecord.class,find.sql(),voteId,user.getUserid());

        if(finds.size()>0){
            return ActionResult.error("您已经评议过此议题了");
        }

        SQL sql = new SQL.SqlBuilder()
                .field("question_id,answer_id,vote_id,userid,answer_text")
                .value(":questionId,:answerId,:voteId,:userid,:answerText")
                .insert("party_branch_vote_record")
                .build();
        Date now = new Date();
        for(int i = 0;i<voteRecords.size();i++){
            voteRecords.get(i).setVoteId(voteId);
            voteRecords.get(i).setUserid(user.getUserid());
            pubDao.insertAndGetKey(sql.sql(),voteRecords.get(i));
        }
        return ActionResult.okWithData("添加成功");
    }

    @RequestMapping
    public ActionResult get_result3(Integer id){
        SeUser user = UserSession.getUser();
       /* if(!checkPower(user.getUserid())){
            return ActionResult.error("没有权限");
        }*/
        /*String sqlParty = "select party_id from t_party_dept_user where userid = ?";
        Integer partyId = pubDao.query(Integer.class,sqlParty,user.getUserid());*/
        SQL sql = new SQL.SqlBuilder()
                .table("party_branch_vote_question q")
                .field("q.*")
                .field("q.id as answerId")
                .where("q.vote_id = ?").order("q.order_numb")
                .build();
        List<QuestionInfo> infos = pubDao.queryList(QuestionInfo.class,sql.sql(),id);
        if (infos.size()>0) {
            for(int i = 0;i<infos.size();i++){
                String sqlCount = "SELECT \n" +
                        "  a.name,\n" +
                        "  r.`answer_id`,\n" +
                        "  IFNULL(COUNT(r.`answer_id`), 0) AS COUNT\n" +
                        "FROM\n" +
                        "  t_party_branch_vote_answer a \n" +
                        "  JOIN t_party_branch_vote_record r \n" +
                        "    ON r.`answer_id` = a.id \n" +
                        "  JOIN t_user u \n" +
                        "    ON r.`userid` = u.`userid` \n" +
                        "WHERE \n"+//u.`party_id` = ? \n" +
                        //AND
                        "r.`question_id` = ? \n" +
                        "  GROUP BY a.id";
                List<VoteParam> counts = pubDao.queryList(VoteParam.class,sqlCount,infos.get(i).getId());//,partyId
                String answerSql = "select id, name, order_numb from t_party_branch_vote_answer where question_id = ?";
                List<AnswerInfo> answerInfos = pubDao.queryList(AnswerInfo.class, answerSql, infos.get(i).getId());
                for(int j = 0;j<answerInfos.size();j++){
                    for(int s = 0;s<counts.size();s++){
                        String name = answerInfos.get(j).getName();
                        String name2 = counts.get(s).getName();
                        if(name.equals(name2)){
                            Integer countNum = counts.get(s).getCount();
                            answerInfos.get(j).setCount(countNum);
                        }
                    }
                }
                infos.get(i).setAnswers(answerInfos);
            }
        }
        return ActionResult.okWithData(infos);
    }

    @RequestMapping
    public void text_list(Integer id,Model model){
        String sql = "SELECT\n" +
                "\tr.answer_text AS answer,\n" +
                "\t(SELECT name FROM t_user WHERE userid = r.userid) AS username\n" +
                "FROM\n" +
                "\tt_party_branch_vote_record r\n" +
                "WHERE\n" +
                "\tquestion_id = ?\n" +
                "ORDER BY\n" +
                "\tid DESC";
        List<AnswerText> ats = pubDao.queryList(AnswerText.class, sql, id);
        model.put("ats", ats);
    }

    @RequestMapping
    public ActionResult get_result(Integer id){
        SeUser user = UserSession.getUser();
        /*if(!checkPower(user.getUserid())){
            return ActionResult.error("没有权限");
        }*/
        SQL sql = new SQL.SqlBuilder()
                .table("party_branch_vote_question q")
                .field("q.*")
                .field("q.id as answerId")
                .where("q.vote_id = ?").order("q.order_numb")
                .build();
        List<QuestionInfo> infos = pubDao.queryList(QuestionInfo.class,sql.sql(),id);
        if (infos.size()>0) {
            for(int i = 0;i<infos.size();i++){
                String sqlCount = "SELECT \n" +
                        "  a.name,\n" +
                        "  r.`answer_id`,\n" +
                        "  IFNULL(COUNT(r.`answer_id`), 0) AS COUNT\n" +
                        "FROM\n" +
                        "  t_party_branch_vote_answer a \n" +
                        "  JOIN t_party_branch_vote_record r \n" +
                        "    ON r.`answer_id` = a.id \n" +
                        "  JOIN t_user u \n" +
                        "    ON r.`userid` = u.`userid` \n" +
                        "  AND r.`question_id` = ? \n" +
                        "  GROUP BY a.id";
                List<VoteParam> counts = pubDao.queryList(VoteParam.class,sqlCount,infos.get(i).getId());
                String answerSql = "select id, name, order_numb from t_party_branch_vote_answer where question_id = ?";
                List<AnswerInfo> answerInfos = pubDao.queryList(AnswerInfo.class, answerSql, infos.get(i).getId());
                for(int j = 0;j<answerInfos.size();j++){
                    String recordSql = "select count(*) from t_party_branch_vote_record where answer_id = ?";
                    Integer count = pubDao.queryInt(recordSql, answerInfos.get(j).getId());
                    answerInfos.get(j).setCount(count);
//                    for(int s = 0;s<counts.size();s++){
//                        String name = answerInfos.get(j).getName();
//                        String name2 = counts.get(s).getName();
//                        if(name.equals(name2)){
//                            Integer countNum = counts.get(s).getCount();
//                            answerInfos.get(j).setCount(countNum);
//                        }
//                    }
                }
                infos.get(i).setAnswers(answerInfos);
            }
        }
        return ActionResult.okWithData(infos);
    }

    @RequestMapping
    public ActionResult get_party_list(PartyDeptParam param){
        param.setPageIs(true);
        param.setRowsPerPage(10);
        SQL.SqlBuilder sql = new SQL.SqlBuilder()
                .table("party_dept")
                .field("*").where("is_party = 0");
        List<PartyDept> partyDepts = pubDao.queryPage(sql,param,PartyDept.class);
        return ActionResult.okRefresh(partyDepts,param);
    }

    @RequestMapping
    public ActionResult get_result_chart(Integer id,Integer voteid){
        String sql = "SELECT \n" +
                "  a.name ,\n" +
                "  SUM(IFNULL(b.num,0)) AS VALUE \n" +
                "FROM\n" +
                "  t_party_branch_vote_answer a \n" +
                "  LEFT JOIN \n" +
                "    (SELECT \n" +
                "      COUNT(r.userid) AS num,\n" +
                "      a.id,a.`order_numb` \n" +
                "    FROM\n" +
                "      t_party_branch_vote_record r \n" +
                "      LEFT JOIN t_party_dept_user u \n" +
                "        ON u.`userid` = r.`userid` \n" +
                "      LEFT JOIN t_party_dept d \n" +
                "        ON u.`party_id` = d.`id` \n" +
                "      LEFT JOIN t_party_branch_vote_answer a \n" +
                "        ON a.id = r.`answer_id` \n" +
                "    WHERE r.`vote_id` = ? \n" +
                "      AND d.id = ? \n" +
                "    GROUP BY a.id) b \n" +
                "    ON a.id = b.id\n" +
                "    GROUP BY a.name\n" +
                "    ORDER BY a.`order_numb`";
        List<EchartsData> echartsData = pubDao.queryList(EchartsData.class,sql,voteid,id);
        return ActionResult.okWithData(echartsData);
    }

    public boolean checkPower(String userid){
        String sql = "select * from t_party_dept_user where userid = ? and party_worker is not null";
        List<PartyDeptUser> partyDeptUsers = pubDao.queryList(PartyDeptUser.class,sql,userid);
        if(partyDeptUsers != null && partyDeptUsers.size()>0){
            return true;
        }else {
            return false;
        }
    }

}
