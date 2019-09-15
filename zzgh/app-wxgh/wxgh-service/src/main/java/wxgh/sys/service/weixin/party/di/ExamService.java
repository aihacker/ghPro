package wxgh.sys.service.weixin.party.di;

import com.libs.common.type.TypeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pub.dao.jdbc.PubDao;
import pub.dao.jdbc.sql.SQL;
import pub.dao.jdbc.sql.bean.Join;
import pub.dao.jdbc.sql.bean.Order;
import pub.error.ValidationError;
import wxgh.app.session.user.UserSession;
import wxgh.data.common.FileInfo;
import wxgh.data.party.di.*;
import wxgh.entity.party.di.DiPlan;
import wxgh.entity.party.di.ExamJoin;
import wxgh.entity.party.di.ExamRecord;
import wxgh.param.party.di.ListParam;

import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2017/7/28.
 */
@Service
@Transactional(readOnly = true)
public class ExamService {

    public List<DiPlan> getPlans(Integer groupId) {
        SQL sql = new SQL.SqlBuilder()
                .table("di_plan p")
                .field("p.id, p.content, p.start_time, p.end_time, p.exam_id")
                .join("chat_group g", "g.group_id = p.group_id")
                .order("p.start_time", Order.Type.DESC)
                .where("g.id = ?")
                .build();
        return pubDao.queryList(DiPlan.class, sql.sql(), groupId);
    }

    public ExamResult getResult(Integer id) {
        SQL sql = new SQL.SqlBuilder()
                .table("di_exam e")
                .field("e.name, e.id")
                .field("(select COUNT(*) from t_di_exam_record where exam_id = e.id and right_is = 1 and userid = ?) as 'right'")
                .field("(select COUNT(*) from t_di_exam_record where exam_id = e.id and right_is = 0 and userid = ?) as 'mistake'")
                .field("(select count(*) from t_di_exam_question where exam_id = e.id) as total")
                .field("(select count(*) from t_di_exam_record where exam_Id = e.id and userid = ?) as complete")
                .where("e.id = ?")
                .build();
        String userid = UserSession.getUserid();
        return pubDao.query(ExamResult.class, sql.sql(), userid, userid, userid, id);
    }

    /**
     * 新增用户答题记录
     *
     * @param record
     */
    @Transactional
    public void addRecord(ExamRecord record, Boolean last) {
        SQL seSql = new SQL.SqlBuilder()
                .table("di_exam_record")
                .field("id, answer_id")
                .where("userid = ?")
                .where("question_id = ?")
                .build();
        String userid = UserSession.getUserid();
        ExamRecord tmp = pubDao.query(ExamRecord.class, seSql.sql(), userid, record.getQuestionId());
        record.setAddTime(new Date());

        //获取全部正确答案
        String rightSql = "select GROUP_CONCAT(id) from t_di_exam_answer where question_id = ? and is_answer=1";
        String[] rights = pubDao.query(String.class, rightSql, record.getQuestionId()).split(",");
        String[] answers = record.getAnswerId().split(",");
        boolean rightIs = false;
        if (rights.length == answers.length) {
            for (int i = 0, len = answers.length; i < len; i++) {
                boolean contais = false;
                for (int j = 0, rlen = rights.length; j < rlen; j++) {
                    if (answers[i].equals(rights[j])) {
                        contais = true;
                        break;
                    }
                }
                if (contais && i == len - 1) {
                    rightIs = true;
                } else if (!contais) {
                    break;
                }
            }
        }
        record.setRightIs(rightIs ? 1 : 0);

        if (tmp == null) {
            record.setUserid(userid);
            String sql = "insert into t_di_exam_record(answer_id, add_time, question_id, userid, right_is, exam_id)" +
                    " values(:answerId, :addTime, :questionId, :userid, :rightIs, :examId)";
            pubDao.executeBean(sql, record);
        } else {
            record.setId(tmp.getId());
            String sql = "update t_di_exam_record set answer_id = :answerId, add_time = :addTime, right_is = :rightIs where id = :id";
            pubDao.executeBean(sql, record);
        }

        //更新用户已经考试
        if (last != null && last) {
            String upSql = "update t_di_exam_join set exam = ? where userid = ? and eid = ?";
            pubDao.execute(upSql, 1, userid, record.getExamId());
        }
    }

    /**
     * 获取考试时，考试的详细信息
     *
     * @param id
     * @return
     */
    public ExamInfo getExamInfo(Integer id) {
        SQL sql = new SQL.SqlBuilder()
                .table("di_exam")
                .field("id, name, total")
                .where("id = ?")
                .build();
        return pubDao.query(ExamInfo.class, sql.sql(), id);
    }

    /**
     * 获取考题内容
     *
     * @param examId
     * @param numb
     * @return
     */
    public QuestionInfo getQuestion(Integer examId, Integer numb) {
        SQL sql = new SQL.SqlBuilder()
                .table("di_exam_question q")
                .field("q.*")
                .field("(select answer_id from t_di_exam_record where q.id = question_id and userid = ?) as answerId")
                .where("q.exam_id = ? and order_numb = ?")
                .build();
        QuestionInfo info = pubDao.query(QuestionInfo.class, sql.sql(), UserSession.getUserid(), examId, numb);
        if (info != null) {
            String answerSql = "select id, name, order_numb from t_di_exam_answer where question_id = ?";
            List<AnswerInfo> answerInfos = pubDao.queryList(AnswerInfo.class, answerSql, info.getId());
            info.setAnswers(answerInfos);
        }
        return info;
    }

    /**
     * 判断用户是否已经考试
     *
     * @param userid
     * @param examId
     * @return
     */
    public boolean isExam(String userid, Integer examId) {
        String sql = "select exam from t_di_exam_join where userid=? and eid=? limit 1";
        Integer exam = pubDao.query(Integer.class, sql, userid, examId);
        return exam != null && exam == 1;
    }

    /**
     * 报名参加考试
     *
     * @param join
     */
    @Transactional
    public void join(ExamJoin join) {
        String userid = UserSession.getUserid();
        String querySql = "select id from t_di_exam_join where userid= ? and eid = ? limit 1";
        Integer id = pubDao.query(Integer.class, querySql, userid, join.getEid());
        if (id != null) {
            throw new ValidationError("您已经报名哦");
        }
        join.setAddTime(new Date());
        join.setUserid(userid);
        join.setExam(0);
        SQL sql = new SQL.SqlBuilder()
                .field("userid, eid, add_time, exam")
                .value(":userid, :eid, :addTime, :exam")
                .insert("di_exam_join")
                .build();
        pubDao.executeBean(sql.sql(), join);
    }

    /**
     * 考试列表
     *
     * @param param
     * @return
     */
    public List<ExamList> examList(ListParam param) {
        SQL.SqlBuilder sql = new SQL.SqlBuilder()
                .table("di_exam e")
                .field("e.id, e.name")
                .sys_file("e.cover_id");
        if (param.getGroupId() != null) {
            sql.join("chat_group g", "g.group_id = e.group_id")
                    .where("g.id = :groupId");
        }
        return pubDao.queryPage(sql, param, ExamList.class);
    }

    public ShowInfo getShowInfo(Integer id) {
        String userid = UserSession.getUserid();
        SQL sql = new SQL.SqlBuilder()
                .table("di_exam e")
                .field("e.id, e.name, e.content")
                .field("j.exam")
                .field("!ISNULL(j.id) as joinIs")
                .join("di_exam_join j", "j.eid = e.id and j.userid = ?", Join.Type.LEFT)
                .where("e.id = ?")
                .build();
        return pubDao.query(ShowInfo.class, sql.sql(), userid, id);
    }

    /**
     * 获取学习详情
     *
     * @param id
     * @return
     */
    public StudyInfo getStudyInfo(Integer id) {
        SQL sql = new SQL.SqlBuilder()
                .table("di_exam e")
                .field("e.id, e.name, e.info, e.add_time, e.end_time, e.files")
                .field("j.exam")
                .field("!ISNULL(j.id) as joinIs")
                .join("di_exam_join j", "j.eid = e.id and j.userid = ?", Join.Type.LEFT)
                .sys_file("e.cover_id")
                .where("e.id = ?")
                .build();
        StudyInfo info = pubDao.query(StudyInfo.class, sql.sql(), UserSession.getUserid(), id);
        if (info != null) {
            info.setExam(info.getExam() == null ? 0 : info.getExam());
            info.setEndIs(new Date().after(info.getEndTime()));

            if (!TypeUtils.empty(info.getFiles())) {
                //查询文件
                SQL fileSql = new SQL.SqlBuilder()
                        .table("sys_file")
                        .field("file_path as path, thumb_path as thumb, filename, mime_type, size")
                        .where("find_in_set(file_id, ?)")
                        .build();
                List<FileInfo> fileInfos = pubDao.queryList(FileInfo.class, fileSql.sql(), info.getFiles());
                info.setFileInfos(fileInfos);
            }
        }

        return info;
    }

    /**
     * 获取考试推送详情 (针对未报名未考试用户)
     * @param id
     * @return
     */
    public ExamDetailInfo getPushDetail(Integer id){
        SQL sql = new SQL.SqlBuilder()
                .table("t_di_exam e")
                .field("e.id, e.name, e.info, e.end_time as endTime, g.name as groupName")
                .join("t_chat_group g", "g.group_id = e.group_id")
                .sys_file("e.cover_id")
                .where("e.id = ?")
                .select()
                .build();
        return pubDao.query(ExamDetailInfo.class, sql.sql(), id);
    }


    @Autowired
    private PubDao pubDao;

}
