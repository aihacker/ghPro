package wxgh.admin.web.di.exam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import pub.spring.web.mvc.model.Model;
import wxgh.data.pub.NameValue;
import wxgh.param.party.di.exam.ExamParam;
import wxgh.sys.service.admin.di.exam.ExamService;
import wxgh.sys.service.admin.notice.NoticeService;

import java.util.List;

@Controller
public class MainController {

    @RequestMapping
    public void list(Model model) {
        List<NameValue> groupList = noticeService.getGroupList();
        model.put("group", groupList);

        model.put("exams", noticeService.getExamList());
    }

    @RequestMapping
    public void exam(Model model,Integer id){
        model.put("id",id);
    }

    @RequestMapping
    public void edit(Model model,Integer id,String groupId){
        ExamParam exam = examService.getExamListById(id);
        model.put("exam",exam);
        model.put("gId",groupId);
    }

    @RequestMapping
    public void result(Integer id, Model model){
        System.out.println("result:" + id);
        model.put("id", id);
    }

    @Autowired
    private NoticeService noticeService;

    @Autowired
    private ExamService examService;

}

