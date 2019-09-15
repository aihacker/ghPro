package wxgh.sys.service.admin.party.di;

import com.libs.common.type.TypeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pub.dao.jdbc.PubDao;
import pub.dao.jdbc.sql.SQL;
import pub.error.ValidationError;
import wxgh.app.sys.api.ExamSchedule;
import wxgh.data.pub.NameValue;
import wxgh.entity.chat.ChatGroup;
import wxgh.entity.party.di.Exam;
import wxgh.param.admin.di.ExamParam;
import wxgh.param.admin.di.OptionParam;
import wxgh.param.admin.di.QuestionParam;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

/**
 * ----------------------------------------------------------
 *
 * @Description ----------------------------------------------------------
 * @Author Ape
 * ----------------------------------------------------------
 * @Email <16511660@qq.com>
 * ----------------------------------------------------------
 * @Date 2017-07-31 11:29
 * ----------------------------------------------------------
 */
@Service
@Transactional(readOnly = true)
public class Exam2Service {

    @Autowired
    private PubDao pubDao;

    @Autowired
    private ExamSchedule examSchedule;

    public List<NameValue> getDiGroup() {
        String sql = "select name, group_id as value from t_chat_group where type = ?";
        return pubDao.queryList(NameValue.class, sql, ChatGroup.TYPE_DI);
    }

    public List<NameValue> listYearPlan() {
        String sql = "SELECT CONCAT(DATE_FORMAT(start_time,'%Y-%m-%d'),' ', content) as name, id as value from t_di_plan where YEAR(start_time) = YEAR(NOW()) and ISNULL(exam_id) ORDER BY start_time";
        return pubDao.queryList(NameValue.class, sql);
    }

    /**
     * 获取党建群体列表
     * @return
     */
    public List<NameValue> getPartyGroup() {
        String sql = "select name, group_id as value from t_chat_group where type = ?";
        return pubDao.queryList(NameValue.class, sql, ChatGroup.TYPE_PARTY);
    }

    /**
     * 获取考试计划列表
     * @param type DiPlan.Type
     * @return
     */
    public List<NameValue> listYearPlan(Integer type) {
        String sql = "SELECT CONCAT(DATE_FORMAT(start_time,'%Y-%m-%d'),' ', content) as name, id as value from t_di_plan where type = ? and YEAR(start_time) = YEAR(NOW()) and ISNULL(exam_id) ORDER BY start_time";
        return pubDao.queryList(NameValue.class, sql, type);
    }

    public List<NameValue> getPlan(String group_id){
        String sql="SELECT p.content as name,p.id as value FROM t_di_plan p where p.group_id=?";
        return pubDao.queryList(NameValue.class,sql,group_id);
    }

    /**
     * 获取指定指定下的全部考试计划
     * @param partyId
     * @return
     */
    public List<NameValue> getPlanByParty(Integer partyId){
        String sql="SELECT p.content as name,p.id as value FROM t_di_plan p where find_in_set(p.group_id, (select group_concat(group_id) from t_party_dept where group_id is not null and FIND_IN_SET(id, get_party_dept_child(?)) > 0))";
        return pubDao.queryList(NameValue.class, sql, partyId);
    }

    /**
     * 获取指定党委的考试计划
     * @param partyId
     * @return
     */
    public List<NameValue> getPlanByPartyId(Integer partyId){
        String sql="SELECT p.content as name,p.id as value FROM t_di_plan p where p.party_id = ?";
        return pubDao.queryList(NameValue.class, sql, partyId);
    }

    @Transactional
    public Integer add(ExamParam examParam) {
        if (examParam.getPlanId() == null || examParam.getPlanId() == -1) {
            throw new ValidationError("请选择学习计划~");
        }

        // 插入exam表并获取主键值
        examParam.setTotal(examParam.getQuestions().size());
        examParam.setAddTime(new Date());
        String insertExam = "insert t_di_exam(name, info, cover_id, files, add_time, end_time, total, content, group_id, plan_id) values(:name, :info, :coverId, :files, :addTime, :endTime, :total, :content, :groupId, :planId)";
        Integer examId = pubDao.insertAndGetKey(insertExam, examParam);

        Exam exam = new Exam();
        exam.setId(examId);
        exam.setName(examParam.getName());
        exam.setInfo(examParam.getInfo());
        exam.setContent(examParam.getContent());
        exam.setCoverId(examParam.getCoverId());
        exam.setAddTime(examParam.getAddTime());
        exam.setEndTime(examParam.getEndTime());
        exam.setGroupId(examParam.getGroupId());
        // 添加任务
        examSchedule.notifies(exam);

        // 设置examId
        for (QuestionParam questionParam : examParam.getQuestions()) {
            questionParam.setExamId(examId);
        }

        SQL questionSql = new SQL.SqlBuilder()
                .field("name, type, order_numb, exam_id")
                .value(":name, :type, :orderNumb, :examId")
                .insert("t_di_exam_question")
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
                .field("name, order_numb, is_answer, question_id")
                .value(":name, :orderNumb, :isAnswer, :questionId")
                .insert("t_di_exam_answer")
                .build();
        pubDao.executeBatch(answerSql.sql(), answers);

        //更新t_di_plan表 exam_id字段
        String sql = "update t_di_plan set exam_id = ? where id = ?";
        pubDao.execute(sql, examId, examParam.getPlanId());
        return examId;
    }

    // By党建 发布考试
    @Transactional
    public Integer addByParty(ExamParam examParam) {
        if (examParam.getPlanId() == null || examParam.getPlanId() == -1) {
            throw new ValidationError("请选择学习计划~");
        }

        // 支部
        if(examParam.getIsParty() == 1) {
            String sqlGroupId = "select group_id from t_party_dept where id = ?";
            String gId = pubDao.query(String.class, sqlGroupId, examParam.getGroupId());
            if (TypeUtils.empty(gId))
                throw new ValidationError("请选择支部");
            examParam.setGroupId(gId);
            examParam.setPartyId(null);
        }else{  // 党委
            if(examParam.getPartyId() == null || examParam.getPartyId() <= 0)
                throw new ValidationError("请选择党委");
            examParam.setGroupId(null);
        }

        // 插入exam表并获取主键值
        examParam.setTotal(examParam.getQuestions().size());
        examParam.setAddTime(new Date());
        String insertExam = "insert t_di_exam(name, info, cover_id, files, add_time, end_time, total, content, group_id, plan_id, party_id) values(:name, :info, :coverId, :files, :addTime, :endTime, :total, :content, :groupId, :planId, :partyId)";
        Integer examId = pubDao.insertAndGetKey(insertExam, examParam);

        Exam exam = new Exam();
        exam.setId(examId);
        exam.setName(examParam.getName());
        exam.setInfo(examParam.getInfo());
        exam.setContent(examParam.getContent());
        exam.setCoverId(examParam.getCoverId());
        exam.setAddTime(examParam.getAddTime());
        exam.setEndTime(examParam.getEndTime());
        exam.setGroupId(examParam.getGroupId());
        // 添加任务
        examSchedule.notifies(exam);

        // 设置examId
        for (QuestionParam questionParam : examParam.getQuestions()) {
            questionParam.setExamId(examId);
        }

        SQL questionSql = new SQL.SqlBuilder()
                .field("name, type, order_numb, exam_id")
                .value(":name, :type, :orderNumb, :examId")
                .insert("t_di_exam_question")
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
                .field("name, order_numb, is_answer, question_id")
                .value(":name, :orderNumb, :isAnswer, :questionId")
                .insert("t_di_exam_answer")
                .build();
        pubDao.executeBatch(answerSql.sql(), answers);

        //更新t_di_plan表 exam_id字段
        String sql = "update t_di_plan set exam_id = ? where id = ?";
        pubDao.execute(sql, examId, examParam.getPlanId());
        return examId;
    }

}

