package wxgh.wx.web.party.di;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import pub.spring.web.mvc.ActionResult;
import wxgh.app.consts.Status;
import wxgh.app.session.user.UserSession;
import wxgh.data.party.di.ExamList;
import wxgh.data.party.di.ExamResult;
import wxgh.data.party.di.QuestionInfo;
import wxgh.entity.party.di.ExamJoin;
import wxgh.entity.party.di.ExamRecord;
import wxgh.entity.party.di.Examiner;
import wxgh.entity.party.di.Topic;
import wxgh.entity.pub.score.Score;
import wxgh.param.party.di.ListParam;
import wxgh.param.party.exam.JoinListParam;
import wxgh.param.pub.score.ScoreGroup;
import wxgh.param.pub.score.ScoreParam;
import wxgh.param.pub.score.ScoreType;
import wxgh.param.pub.score.SimpleScore;
import wxgh.sys.service.weixin.party.di.ExamMessageService;
import wxgh.sys.service.weixin.party.di.ExamService;
import wxgh.sys.service.weixin.pub.score.ScoreService;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/7/28.
 */
@Controller
public class ApiController {

    @RequestMapping
    public ActionResult list_score(ScoreParam param) {
        param.setPageIs(true);
        param.setStatus(Status.NORMAL.getStatus());
        param.setUserid(UserSession.getUserid());
        param.setGroup(ScoreGroup.DI);

        List<Score> scores = scoreService.listScore(param);

        return ActionResult.okRefresh(scores, param);
    }

    /**
     * 获取考试列表
     *
     * @param param
     * @return
     */
    @RequestMapping
    public ActionResult course_list(ListParam param) {

        List<ExamList> exams = examService.examList(param);

        return ActionResult.okRefresh(exams, param);
    }

    /**
     * 报名参加考试
     *
     * @param join
     * @return
     */
    @RequestMapping
    public ActionResult join(ExamJoin join) {
        examService.join(join);
        return ActionResult.ok();
    }

    /**
     * 获取考题
     *
     * @param id
     * @param numb
     * @return
     */
    @RequestMapping
    public ActionResult question_list(Integer id, Integer numb) {
        QuestionInfo questionInfo = examService.getQuestion(id, numb);
        return ActionResult.okWithData(questionInfo);
    }

    /**
     * 添加考试记录
     *
     * @param record
     * @return
     */
    @RequestMapping
    public ActionResult add_record(ExamRecord record, Boolean last) {
        examService.addRecord(record, last);
        if (last != null && last) {
            ExamResult re = examService.getResult(record.getExamId());
            if (re.getMistake() <= 0) {
                String userid = UserSession.getUserid();

                ScoreParam param = new ScoreParam();
                param.setStatus(Status.NORMAL.getStatus());
                param.setUserid(userid);
                param.setGroup(ScoreGroup.DI);
                param.setType(ScoreType.DI_EXAM);
                param.setById(record.getExamId().toString());

                boolean hasAdd = scoreService.hasScore(param);
                if (!hasAdd) {
                    SimpleScore score = new SimpleScore();
                    score.setScore(10f);
                    score.setUserid(userid);
                    score.setInfo("考试积分");
                    score.setStatus(Status.NORMAL.getStatus());
                    score.setById(record.getExamId().toString());
                    scoreService.di(score, ScoreType.DI_EXAM);
                }
            }
        }
        return ActionResult.ok();
    }

    /**
     * 获取考试人数
     *
     * @param id
     * @return
     */
    @RequestMapping
    public ActionResult get_people(JoinListParam joinparam,Integer id, wxgh.param.union.suggest.ListParam param) {
        param.setPageIs(true);
        List<Examiner> examJoin = new ArrayList<>();
        switch (param.getStatus()) {
            case 0:
                examJoin = examMessageService.getExamJoin(joinparam);
                break;
            case 1:
                examJoin = complete_exam(joinparam);
                break;
            default:
                break;
        }
        return ActionResult.okRefresh(examJoin, param);
    }

    /**
     * 获取完成考试的人
     *
     * @param
     * @return
     */

    public List<Examiner> complete_exam(JoinListParam param) {
        List<Examiner> examiner = examMessageService.getExaminer(param);
        List<Examiner> complete = new ArrayList<>();
        //设置保留一位小数
        DecimalFormat df = new DecimalFormat("#.0");
        for (Examiner e : examiner) {
            String userid = e.getUserid();
            Topic topic = examMessageService.getTopic(param.getId(), userid);
            //计算分数
            Integer total = topic.getTotal();
            Integer right = topic.getRight();
            double score = Double.parseDouble(df.format(100 * right / total));
            e.setScore(score);
            complete.add(e);
        }
        return complete;
    }

    /**
     * 获取考试人数
     *
     * @param id
     * @return
     */
    @RequestMapping
    public ActionResult get_number(Integer id, Integer status) {
        Integer number = null;
        switch (status) {
            case 0:
                number = examMessageService.getNumber(id);
                break;
            case 1:
                number = examMessageService.getCompleteNumber(id);
                break;
            default:
                break;
        }
        return ActionResult.okWithData(number);
    }


    @Autowired
    private ExamService examService;

    @Autowired
    private ScoreService scoreService;

    @Autowired
    private ExamMessageService examMessageService;
}
