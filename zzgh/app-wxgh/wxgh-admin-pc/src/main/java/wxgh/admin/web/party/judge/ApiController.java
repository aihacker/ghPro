package wxgh.admin.web.party.judge;

import com.libs.common.json.JsonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import pub.dao.jdbc.PubDao;
import pub.dao.jdbc.sql.SQL;
import pub.dao.jdbc.sql.bean.Order;
import pub.dao.page.PageBen;
import pub.spring.web.mvc.ActionResult;
import pub.type.RefData;
import wxgh.admin.session.AdminSession;
import wxgh.app.sys.task.WeixinPush;
import wxgh.param.admin.di.ExamParam;
import wxgh.param.admin.di.OptionParam;
import wxgh.param.admin.di.QuestionParam;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

@Controller
public class ApiController {

    @Autowired
    private PubDao pubDao;

    @Autowired
    private WeixinPush weixinPush;


    @RequestMapping
    public ActionResult list(ExamParam param) {
        if (param.getStatus() == null) {
            param.setStatus(0);
        }
        param.setPageIs(true);
        param.setRowsPerPage(7);
        SQL.SqlBuilder sql = new SQL.SqlBuilder().table("party_branch_vote v")
                .field("v.*")
                .field("CASE v.type\n" +
                        "\t\tWHEN 1 THEN (select name from t_party_dept c where v.group_id = c.id)" +
                        "\t\tWHEN 2 THEN (select name from t_chat_group c where v.group_id = c.id)\n" +
                        "\t\tWHEN 3 THEN (select name from t_party_dept t where v.group_id = t.id)\n" +
                        "\t\tWHEN 4 THEN (select name from t_party_dept t where v.group_id = t.id)\n" +
                        "\tEND AS gname")
                .order("v.add_time", Order.Type.DESC);

        if (param.getType() != null) {
            sql.where("v.type = :type");
        }

        if (param.getPageIs()) {
            Integer count = pubDao.queryParamInt(sql.count().build().sql(), param);
            param.setTotalCount(count);
            sql.limit(":pagestart, :rowsPerPage");
        }

        List<ExamParam> list =  pubDao.queryList(sql.select().build().sql(), param, ExamParam.class);


        RefData refData = new RefData();
        refData.put("datas", list);
        refData.put("page", new PageBen(param));

        return ActionResult.ok(null, refData);
    }

    @RequestMapping
    public ActionResult add(String json, Integer push) {
        ExamParam examParam = (ExamParam) JsonUtils.parse(json, ExamParam.class);

        // 插入exam表并获取主键值
        examParam.setTotal(examParam.getQuestions().size());
        examParam.setAddTime(new Date());
        examParam.setStatus(push);
        if(examParam.getType() == 1){
            examParam.setGroupId(AdminSession.getAdmin().getExtraMap().get("PARTYID"));
        }
        String insertExam = "insert t_party_branch_vote(name,brief_info,add_time,total,status,type,group_id) values(:name, :info,:addTime,:total,:status,:type,:groupId)";
        Integer examId = pubDao.insertAndGetKey(insertExam, examParam);

        // 设置examId
        for (QuestionParam questionParam : examParam.getQuestions()) {
            questionParam.setExamId(examId);
        }

        SQL questionSql = new SQL.SqlBuilder()
                .field("name, type, order_numb, vote_id")
                .value(":name, :type, :orderNumb, :examId")
                .insert("t_party_branch_vote_question")
                .build();

        // 插入question表
        List<OptionParam> answers = new LinkedList<>();
        for (QuestionParam questionParam : examParam.getQuestions()) {
            Integer qId = pubDao.insertAndGetKey(questionSql.sql(), questionParam);
            for (OptionParam optionParam : questionParam.getAnswers()) {
                optionParam.setQuestionId(qId);
                answers.add(optionParam);
            }
        }

        // 插入answer表
        SQL answerSql = new SQL.SqlBuilder()
                .field("name, order_numb, question_id")
                .value(":name, :orderNumb, :questionId")
                .insert("t_party_branch_vote_answer")
                .build();
        pubDao.executeBatch(answerSql.sql(), answers);

        if(push == 1){
            weixinPush.party_judge(examParam.getType(), examParam.getGroupId(), examId, examParam.getName());
        }

        return ActionResult.ok("null","添加成功");
    }

    @RequestMapping
    public ActionResult del(String id) {

        String[] ids = id.split(",");
        for (String eid : ids){
            String sql = "delete from t_party_branch_vote where id = ?";
            pubDao.execute(sql,eid);
        }
        return ActionResult.ok();
    }
}
