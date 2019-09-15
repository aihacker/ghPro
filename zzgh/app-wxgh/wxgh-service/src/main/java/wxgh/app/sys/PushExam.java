package wxgh.app.sys;


import com.libs.common.json.JsonUtils;
import com.libs.common.type.TypeUtils;
import com.weixin.WeixinException;
import com.weixin.api.MsgApi;
import com.weixin.bean.message.Article;
import com.weixin.bean.message.Message;
import com.weixin.bean.message.News;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pub.dao.jdbc.sql.SQL;
import pub.utils.DateUtils;
import wxgh.app.consts.WeixinAgent;
import wxgh.app.sys.schedule.JobGroup;
import wxgh.app.sys.schedule.ScheduleManager;
import wxgh.app.sys.variate.GlobalValue;
import wxgh.data.party.di.ExamDetailInfo;
import wxgh.entity.pub.ScheduleJob;
import wxgh.sys.service.weixin.party.di.ExamMessageService;
import wxgh.sys.service.weixin.party.di.ExamService;
import wxgh.sys.service.weixin.pub.PushService;

import java.util.List;

/**
 * @Version 1.0
 * --------------------------------------------------------- *
 * @Description >_
 * --------------------------------------------------------- *
 * @Author Ape
 * --------------------------------------------------------- *
 * @Email <16511660@qq.com>
 * --------------------------------------------------------- *
 * @Date 2017-09-25  16:30
 * --------------------------------------------------------- *
 */
@Component
public class PushExam {

    @Autowired
    private ScheduleManager scheduleManager;

    @Autowired
    private PushService pushService;

    @Autowired
    private ExamService examService;

    @Autowired
    private ExamMessageService examMessageService;

    private static final Log log = LogFactory.getLog(PushExam.class);


    /**
     * 考试报名提醒
     * @param id
     * @param jobId
     */
    public void pushNotJoin(Integer id, String jobId, Integer agentid){
        ExamDetailInfo info = examService.getPushDetail(id);
        String examUrl = "/wx/party/di/show.html?id=" + id;
        String title = "【考试报名提醒】" + info.getGroupName() + "--" + info.getName();
        StringBuffer cnt = new StringBuffer();
        cnt.append("考试截止时间：" + DateUtils.formatDate(info.getEndTime()) + " \n");
        cnt.append("简介：" + info.getInfo());

        String imgPath;
        if (!TypeUtils.empty(info.getPath())) {
            imgPath = GlobalValue.getUrl() + info.getPath();
        } else {
            imgPath = GlobalValue.getUrl() + "/image/default/act.png";
        }

        String url = GlobalValue.getUrl() + examUrl;

        if (agentid == null) {
            agentid = WeixinAgent.AGENT_JIJIAN;
        }

        News news = new News();
        news.addArtice(new Article(title, cnt.toString(), url, imgPath));
        Message<News> message = new Message<>(agentid, news);
        List<String> userids = examMessageService.getExamNotJoinIn(id);
        message.setTouser(userids);

        try {
            MsgApi.send(message);
        } catch (WeixinException e) {
            log.error("send weixin message[" + JsonUtils.stringfy(message) + "] error！！！", e);
        }

        if (jobId != null) {
            pushService.updateStatus(jobId, ScheduleJob.STATUS_STOP);
            scheduleManager.removeJob(id.toString(), JobGroup.EXAM_JOIN_PUSH.getType());
        }

    }

    /**
     * 考试提醒
     * @param id
     * @param jobId
     */
    public void pushNotDo(Integer id, String jobId, Integer agentid){
        ExamDetailInfo info = examService.getPushDetail(id);
        String examUrl = "/wx/party/di/show.html?id=" + id;
        String title = "【考试提醒】" + info.getGroupName() + "--" + info.getName();
        StringBuffer cnt = new StringBuffer();
        cnt.append("考试截止时间：" + DateUtils.formatDate(info.getEndTime()) + " \n");
        cnt.append("简介：" + info.getInfo());

        String imgPath;
        if (!TypeUtils.empty(info.getPath())) {
            imgPath = GlobalValue.getUrl() + info.getPath();
        } else {
            imgPath = GlobalValue.getUrl() + "/image/default/act.png";
        }

        String url = GlobalValue.getUrl() + examUrl;

        if (agentid == null) {
            agentid = WeixinAgent.AGENT_JIJIAN;
        }

        News news = new News();
        news.addArtice(new Article(title, cnt.toString(), url, imgPath));
        Message<News> message = new Message<>(agentid, news);
        List<String> userids = examMessageService.getExamNotDo(id);
        message.setTouser(userids);

        try {
            MsgApi.send(message);
        } catch (WeixinException e) {
            log.error("send weixin message[" + JsonUtils.stringfy(message) + "] error！！！", e);
        }

        if (jobId != null) {
            pushService.updateStatus(jobId, ScheduleJob.STATUS_STOP);
            scheduleManager.removeJob(id.toString(), JobGroup.EXAM_PUSH.getType());
        }
    }

}
