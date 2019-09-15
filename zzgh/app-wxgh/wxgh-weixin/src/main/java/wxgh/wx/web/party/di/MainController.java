package wxgh.wx.web.party.di;

import com.weixin.WeixinException;
import com.weixin.utils.ApiList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import pub.spring.web.mvc.model.Model;
import wxgh.app.consts.Status;
import wxgh.app.session.user.UserSession;
import wxgh.app.utils.WeixinUtils;
import wxgh.data.party.di.ExamInfo;
import wxgh.data.party.di.ExamResult;
import wxgh.data.party.di.ShowInfo;
import wxgh.data.party.di.StudyInfo;
import wxgh.entity.party.di.DiPlan;
import wxgh.param.pub.score.ScoreGroup;
import wxgh.param.pub.score.ScoreParam;
import wxgh.sys.service.weixin.party.di.ExamService;
import wxgh.sys.service.weixin.pub.score.ScoreService;

import java.util.List;

/**
 * Created by Administrator on 2017/7/28.
 */
@Controller
public class MainController {

    /**
     * 课程列表
     *
     * @param id chat_group表ID
     */
    @RequestMapping
    public void course(Integer id) {
    }

    @RequestMapping
    public void score(Model model) {
        ScoreParam param = new ScoreParam();
        param.setGroup(ScoreGroup.DI);
        param.setUserid(UserSession.getUserid());
        param.setStatus(Status.NORMAL.getStatus());
        Float score = scoreService.sumScore(param);
        model.put("sumScore", score);
    }

    /**
     * 课程详情
     *
     * @param id
     */
    @RequestMapping
    public void show(Integer id, Model model) {
        StudyInfo studyInfo = examService.getStudyInfo(id);

        model.put("s", studyInfo);
    }

    @RequestMapping
    public void show_info(Integer id, Model model) {
        ShowInfo showInfo = examService.getShowInfo(id);
        model.put("s", showInfo);
    }

    @RequestMapping
    public void exam_message(Integer id, Model model) {
        model.put("id", id);
    }

    /**
     * 考试详情
     *
     * @param id 考试ID
     */
    @RequestMapping
    public void exam(Integer id, Model model) {
        ExamInfo examInfo = examService.getExamInfo(id);
        model.put("e", examInfo);
    }

    @RequestMapping
    public void exam_result(Integer id, Model model) throws WeixinException {
        ExamResult result = examService.getResult(id);
        model.put("r", result);
        WeixinUtils.sign(model, ApiList.getCloseApi());
    }

    /**
     * 学习计划
     *
     * @param id chat_group 表ID
     */
    @RequestMapping
    public void plan(Integer id, Model model) {
        List<DiPlan> plans = examService.getPlans(id);
        model.put("plans", plans);
    }

    @Autowired
    private ExamService examService;

    @Autowired
    private ScoreService scoreService;

}
