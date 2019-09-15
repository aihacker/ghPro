package wxgh.wx.web.manage.judge;

import com.libs.common.json.JsonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import pub.dao.jdbc.PubDao;
import pub.dao.jdbc.sql.SQL;
import pub.spring.web.mvc.ActionResult;
import wxgh.app.session.bean.SeUser;
import wxgh.app.session.user.UserSession;
import wxgh.data.party.di.AnswerInfo;
import wxgh.data.party.di.QuestionInfo;
import wxgh.entity.party.vote.VoteRecord;
import wxgh.param.party.vote.VoteParam;

import java.util.Date;
import java.util.List;

@Controller
public class ApiController {

    @Autowired
    private PubDao pubDao;

    @RequestMapping
    public ActionResult get_question(Integer examid){
        SQL sql = new SQL.SqlBuilder()
                .table("manage_vote_question q")
                .field("q.*")
                .field("q.id as answerId")
                .where("q.vote_id = ?").order("q.order_numb")
                .build();
        List<QuestionInfo> info = pubDao.queryList(QuestionInfo.class,sql.sql(),examid);
        if (info.size()>0) {
            String answerSql = "select id, name, order_numb from t_manage_vote_answer where question_id = ?";
            for(int i=0;i<info.size();i++){
                List<AnswerInfo> answerInfos = pubDao.queryList(AnswerInfo.class, answerSql, info.get(i).getId());
                info.get(i).setAnswers(answerInfos);
            }
        }
        return ActionResult.okWithData(info);
    }

    @RequestMapping
    public ActionResult add_record(Integer voteId, String answer){
        List<VoteRecord> voteRecords = JsonUtils.parseList(answer,VoteRecord.class);
        SeUser user = UserSession.getUser();

        SQL find = new SQL.SqlBuilder()
                .table("manage_branch_vote_record")
                .where("vote_id = ? and userid = ?")
                .select().build();
        List<VoteRecord> finds = pubDao.queryList(VoteRecord.class,find.sql(),voteId,user.getUserid());

        if(finds.size()>0){
            return ActionResult.error("您已经评议过此议题了");
        }

        SQL sql = new SQL.SqlBuilder()
                .field("question_id,answer_id,vote_id,userid")
                .value(":questionId,:answerId,:voteId,:userid")
                .insert("manage_branch_vote_record")
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
    public ActionResult get_result(Integer id){
        SeUser user = UserSession.getUser();
        SQL sql = new SQL.SqlBuilder()
                .table("manage_vote_question q")
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
                        "  t_manage_vote_answer a \n" +
                        "  JOIN t_manage_branch_vote_record r \n" +
                        "    ON r.`answer_id` = a.id \n" +
                        "  AND r.`question_id` = ? \n" +
                        "  GROUP BY a.id";
                List<VoteParam> counts = pubDao.queryList(VoteParam.class,sqlCount,infos.get(i).getId());
                String answerSql = "select id, name, order_numb from t_manage_vote_answer where question_id = ?";
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

}
