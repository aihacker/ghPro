package wxgh.admin.web.di;

import com.libs.common.crypt.URL;
import com.libs.common.json.JsonUtils;
import com.libs.common.type.TypeUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import pub.spring.web.mvc.ActionResult;
import pub.spring.web.mvc.model.Model;
import wxgh.app.sys.api.FileApi;
import wxgh.app.sys.task.WeixinPush;
import wxgh.data.pub.NameValue;
import wxgh.entity.party.di.DiPlan;
import wxgh.entity.pub.SysFile;
import wxgh.param.admin.di.ExamParam;
import wxgh.sys.service.admin.party.di.Exam2Service;

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
 * @Date 2017-07-31 09:05
 * ----------------------------------------------------------
 */
@Controller
public class MainController {

    @Autowired
    private Exam2Service exam2Service;

    @Autowired
    private FileApi fileApi;

    @Autowired
    private WeixinPush weixinPush;

    @RequestMapping
    public void index(Model model) {
        List<NameValue> groups = exam2Service.getDiGroup();
        List<NameValue> plans = exam2Service.listYearPlan(DiPlan.Type.DI.getValue());
        model.put("groups", groups);
        model.put("plans", plans);
    }

    @RequestMapping
    public ActionResult add(String json, Integer push) {
        ExamParam examParam = (ExamParam) JsonUtils.parse(json, ExamParam.class);
        if(examParam != null && examParam.getContent() != null)
            examParam.setContent(URL.decode(examParam.getContent()));
//        if (!TypeUtils.empty(examParam.getContent())) {
//            examParam.setContent(fileApi.replaceImage(examParam.getContent()));
//        }
        Integer examId = exam2Service.add(examParam);
        //推送
        if (push != null && push == 1) {
            weixinPush.di_exam(examId);
        }
        return ActionResult.ok(null, "添加成功");
    }

    @RequestMapping
    public ActionResult get_plan(String id){
        List<NameValue> plan = exam2Service.getPlan(id);
        return ActionResult.okWithData(plan);
    }

    /**
     * @param multipartFile
     * @return
     */
    @RequestMapping
    public ActionResult upload(@RequestParam("file") MultipartFile[] multipartFile) {
        List<SysFile> list = new LinkedList<>();
        for (MultipartFile m : multipartFile) {
            if (m != null && !StringUtils.isEmpty(m.getOriginalFilename())) {
                SysFile sysFile = fileApi.addFile(m, null);
                list.add(sysFile);
            }
        }
        if (list.size() == 0)
            return ActionResult.ok();
        else if (list.size() == 1) {
            return ActionResult.ok(null, list.get(0).getFileId());
        } else {
            List<String> ids = new LinkedList<>();
            for (SysFile sysFile : list)
                ids.add(sysFile.getFileId());
            return ActionResult.ok(null, TypeUtils.listToStr(ids));
        }
    }

    public static void main(String[] args) {

        String t = "{\"name\":\"c\",\"info\":\"cccc\",\"coverId\":\"dbf0ba99100c426182967c3a3b48cf9c\",\"endTime\":\"2017-09-26 15:55\",\"questions\":[],\"content\":\"%3Cp%3Eccc%3C%2Fp%3E\",\"groupId\":\"a930e5e9d0b24e818634ae520d109dc7\",\"planId\":\"33\"}";
        ExamParam examParam = (ExamParam) JsonUtils.parse(t, ExamParam.class);
        System.out.println(examParam.getEndTime());

//        String json = "{\"name\":\"程序\",\"info\":\"先把dv\",\"questions\":[{\"type\":\"1\",\"name\":\"题目名称1\",\"orderNumb\":1,\"answers\":[{\"name\":\"1单选1\",\"orderNumb\":0,\"isAnswer\":0},{\"name\":\"1单选2\",\"orderNumb\":1,\"isAnswer\":1},{\"name\":\"1单选答案2\",\"orderNumb\":2,\"isAnswer\":0}]},{\"type\":\"1\",\"name\":\"题目名称2\",\"orderNumb\":2,\"answers\":[{\"name\":\"2单选12\",\"orderNumb\":0,\"isAnswer\":0},{\"name\":\"2单选22\",\"orderNumb\":1,\"isAnswer\":1}]},{\"type\":\"2\",\"name\":\"题目名称233\",\"orderNumb\":3,\"answers\":[{\"name\":\"33多3选13\",\"orderNumb\":0,\"isAnswer\":0},{\"name\":\"多3选2\",\"orderNumb\":1,\"isAnswer\":1},{\"name\":\"33多选答案33\",\"orderNumb\":2,\"isAnswer\":1}]}]}";
//        ExamParam examParam = (ExamParam) JsonUtils.parse(json, ExamParam.class);
//        System.out.println(examParam.getQuestions().get(0).getAnswers().get(0).getName());
    }

}

