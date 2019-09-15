package wxgh.admin.web.di.exam;

import com.libs.common.crypt.URL;
import com.libs.common.type.TypeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import pub.dao.jdbc.PubDao;
import pub.spring.web.mvc.ActionResult;
import wxgh.app.sys.api.ExamSchedule;
import wxgh.app.sys.excel.exam.ExamExportApi;
import wxgh.app.sys.excel.exam.GuoqingExamExPortApi;
import wxgh.app.sys.task.WeixinPush;
import wxgh.data.party.di.exam.ExamExportResult;
import wxgh.data.party.di.exam.ExamJoinList;
import wxgh.data.party.di.exam.ExamNotDoList;
import wxgh.data.pcadmin.party.di.ExportExam;
import wxgh.entity.party.di.Exam;
import wxgh.param.party.di.exam.ExamJoinParam;
import wxgh.param.party.di.exam.ExamParam;
import wxgh.param.party.di.exam.ExamResultParam;
import wxgh.param.pcadmin.party.di.ExportExamParam;
import wxgh.param.pub.score.ScoreGroup;
import wxgh.param.pub.score.ScoreType;
import wxgh.sys.service.admin.di.exam.ExamService;
import wxgh.sys.service.weixin.party.di.ExamMessageService;

import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class ApiController {

    @Autowired
    private ExamService examService;

    @Autowired
    private ExamMessageService examMessageService;

    @Autowired
    private WeixinPush weixinPush;

    @Autowired
    private ExamSchedule examSchedule;

    @Autowired
    private PubDao pubDao;

    @RequestMapping
    public ActionResult get(ExamParam param) {
        param.setPageIs(true);
        param.setRowsPerPage(9);
        List<ExamParam> list = examService.getExamList(param);

        return ActionResult.okAdmin(list, param);
    }

    @RequestMapping
    public ActionResult del_exam(String id) {
        examService.deleteExam(id);
        return ActionResult.ok();
    }

    @RequestMapping
    public ActionResult get_exam_join(ExamJoinParam param) {
        param.setPageIs(true);
        param.setRowsPerPage(10);
        List<ExamJoinList> list = examService.getExamJoinList(param);
        return ActionResult.okAdmin(list, param);
    }

    // 未考试(包含已报名未考试和未报名)
    @RequestMapping
    public ActionResult get_exam_not_do(ExamResultParam param){
        List<ExamNotDoList> list = examMessageService.getExamNotJoin(param);
        return ActionResult.okAdmin(list, param);
    }

    @RequestMapping
    public ActionResult update(ExamParam param,Integer push) {
        String content = URL.decode(param.getContent());//文章转码
        param.setContent(content);
        examService.update(param);

        String sql = "select * from t_di_exam where id = ?";
        Exam exam = pubDao.query(Exam.class,sql,param.getId());
        // 添加任务
        examSchedule.notifies(exam);
        //推送
        if (push != null && push == 1) {
            weixinPush.di_exam(param.getId());
        }
        return ActionResult.ok();
    }

    @RequestMapping
    public ActionResult del_exam_join(String id) {
        examService.deleteExamJoin(id);
        return ActionResult.ok();
    }

    @RequestMapping
    public ActionResult del_exam_not_do(String id) {
        examService.deleteExamNotDo(id);
        return ActionResult.ok();
    }

    @RequestMapping
    public ActionResult update_exam(Integer id, Integer by_id, String userid) {
        ScoreGroup g = ScoreGroup.DI;
        Integer group = g.getGroup();
        ScoreType diExam = ScoreType.DI_EXAM;
        Integer type = diExam.getType();
        boolean existScore = examService.isExistScore(by_id, userid, group, type);
        if (existScore) {
            examService.deletScoreByuserid(by_id, userid, group, type);
        }
        examService.update_exam(id);
        return ActionResult.ok();
    }

    @RequestMapping
    public ActionResult result(ExamResultParam param) {
        param.setPageIs(true);
        param.setRowsPerPage(10);
        return ActionResult.okAdmin(examService.getExamResultPageList(param), param);
    }

    @RequestMapping
    public void export(Integer id, HttpServletResponse response) {
        List<ExamExportResult> list = examService.getExamResultList(id);
        if (list.size() > 0) {
            GuoqingExamExPortApi api = new GuoqingExamExPortApi();
            api.toExcel(list);
            api.response(response);
        }
    }

    @RequestMapping
    public void export_exam(ExportExamParam param, HttpServletResponse response) {
        List<ExportExam> results = examService.exportExams(param);
        ExamExportApi examExportApi = new ExamExportApi();
        if (!TypeUtils.empty(results)) {
            if (ExportExamParam.TYPE_SING == param.getExportType()) {
                examExportApi.toExcel(results);
            } else {
                Map<String, List<ExportExam>> map = new HashMap<>();
                if (ExportExamParam.TYPE_GROUP == param.getExportType()) {
                    for (ExportExam exam : results) {
                        String key = exam.getGroupName() + "-" + exam.getGroupId();
                        List<ExportExam> exams = map.get(key);
                        if (exams == null) {
                            exams = new ArrayList<>();
                            map.put(key, exams);
                        }
                        exams.add(exam);
                    }
                } else if (ExportExamParam.TYPE_EXAM == param.getExportType()) {
                    for (ExportExam exam : results) {
                        String key = exam.getName() + "-" + exam.getId();
                        List<ExportExam> exams = map.get(key);
                        if (exams == null) {
                            exams = new ArrayList<>();
                            map.put(key, exams);
                        }
                        exams.add(exam);
                    }
                }
                examExportApi.toExcel(map);
            }
        } else {
            examExportApi.toExcel(Collections.emptyList());
        }
        examExportApi.response(response);
    }

}
