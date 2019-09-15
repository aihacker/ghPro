package wxgh.admin.web.manage;


import com.libs.common.json.JsonUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import pub.dao.jdbc.PubDao;
import pub.dao.jdbc.sql.SQL;
import pub.dao.jdbc.sql.bean.Order;
import pub.dao.page.PageBen;
import pub.spring.web.mvc.ActionResult;
import pub.type.RefData;
import wxgh.app.sys.api.FileApi;
import wxgh.app.sys.task.WeixinPush;
import wxgh.entity.manage.ManageAct;
import wxgh.entity.pub.SysFile;
import wxgh.param.admin.di.ExamParam;
import wxgh.param.admin.di.OptionParam;
import wxgh.param.admin.di.QuestionParam;
import wxgh.param.manage.ManageParam;
import wxgh.param.pub.file.FileParam;
import wxgh.sys.service.admin.tribe.TribeActService;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;

@Controller
public class ApiController {

    @Autowired
    private PubDao pubDao;

    @Autowired
    private WeixinPush weixinPush;

    @Autowired
    private TribeActService tribeActService;

    @Autowired
    private FileApi fileApi;

    @RequestMapping
    public ActionResult add(String json, Integer push) {
        ExamParam examParam = (ExamParam) JsonUtils.parse(json, ExamParam.class);

        // 插入exam表并获取主键值
        examParam.setTotal(examParam.getQuestions().size());
        examParam.setAddTime(new Date());
        examParam.setStatus(push);
        String insertExam = "insert t_manage_branch_vote(name,brief_info,add_time,total,status) values(:name, :info,:addTime,:total,:status)";
        Integer examId = pubDao.insertAndGetKey(insertExam, examParam);

        // 设置examId
        for (QuestionParam questionParam : examParam.getQuestions()) {
            questionParam.setExamId(examId);
        }

        SQL questionSql = new SQL.SqlBuilder()
                .field("name, type, order_numb, vote_id")
                .value(":name, :type, :orderNumb, :examId")
                .insert("t_manage_vote_question")
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
                .insert("t_manage_vote_answer")
                .build();
        pubDao.executeBatch(answerSql.sql(), answers);

        if(push == 1){
            weixinPush.manage_judge(examId, examParam.getName());
        }

        return ActionResult.ok("null","添加成功");
    }

    @RequestMapping
    public ActionResult getlist(ManageParam param){
        param.setPageIs(true);
        param.setRowsPerPage(5);
        SQL.SqlBuilder sql = new SQL.SqlBuilder()
                .table("manage_branch_vote c").field("c.*")
                .order("c.add_time", Order.Type.DESC);

        if (param.getPageIs()) {
            Integer count = pubDao.queryParamInt(sql.count().build().sql(), param);
            param.setTotalCount(count);

            sql.limit(":pagestart, :rowsPerPage");
        }

        List<ManageParam> list =  pubDao.queryList(sql.select().build().sql(), param, ManageParam.class);

        RefData refData = new RefData();
        refData.put("datas", list);
        refData.put("page", new PageBen(param));

        return ActionResult.ok(null, refData);
    }

    @RequestMapping
    private ActionResult del(String id){
        String[] ids = id.split(",");
        for(String i:ids){
            pubDao.execute(SQL.deleteByIds("manage_branch_vote",i));
        }
        return ActionResult.ok();
    }

    @RequestMapping
    public ActionResult add_act(ManageAct manageAct, @RequestParam("img") MultipartFile multipartFile) {
        if (multipartFile != null && !StringUtils.isEmpty(multipartFile.getOriginalFilename())) {
            SysFile sysFile = fileApi.addFile(multipartFile, new FileParam());
            String fileId = sysFile.getFileId();
           /* String filePath = sysFile.getFilePath();
            String path = GlobalValue.getUrl() + filePath;*/

            manageAct.setStatus(1);
            manageAct.setAddTime(new Date());
            manageAct.setCrossPoint(0); //设置默认50积分
            manageAct.setCoverImg(fileId);
            SQL sql = new SQL.SqlBuilder()
                    .field("theme,start_time,end_time,address,lng,lat,leader,linkman,phone,total_number,cover_img,remark,content,status,add_time,deptid")
                    .value(":theme,:startTime,:endTime,:address,:lng,:lat,:leader,:linkman,:phone,:totalNumber,:coverImg,:remark,:content,:status,:addTime,:deptid")
                    .insert("manage_act")
                    .build();
            int i = pubDao.insertAndGetKey(sql.sql(), manageAct);
           /* if (fileId != null) {
                //将消息进行推送
                List<Article> articles = new ArrayList<>();
                String url = GlobalValue.getUrl() + "/wx/party/tribe/act/show.html?id=" + id;
                Article article = new Article(tribeAct.getTheme(), tribeAct.getContent(), url, path);
                articles.add(article);
                News news = new News();

                news.setArticles(articles);
                Message<News> message = new Message<>(WeixinAgent.AGENT_TRIBE, news);
                message.addUser("@all");
                sendMessage(message);
            }*/
           weixinPush.manage_act(i,"总经理直通车");
        }
        return ActionResult.ok();

    }

}
