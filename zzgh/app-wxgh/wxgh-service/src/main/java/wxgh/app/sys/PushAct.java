package wxgh.app.sys;

import com.libs.common.json.JsonUtils;
import com.libs.common.type.TypeUtils;
import com.weixin.Agent;
import com.weixin.WeixinException;
import com.weixin.api.MsgApi;
import com.weixin.bean.message.Article;
import com.weixin.bean.message.Message;
import com.weixin.bean.message.News;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import wxgh.app.consts.WeixinAgent;
import wxgh.app.sys.schedule.JobGroup;
import wxgh.app.sys.schedule.ScheduleManager;
import wxgh.app.sys.variate.GlobalValue;
import wxgh.data.entertain.act.ActPushInfo;
import wxgh.entity.canteen.CanteenAct;
import wxgh.entity.entertain.act.Act;
import wxgh.entity.pub.ScheduleJob;
import wxgh.sys.service.weixin.pub.PushService;

import java.util.List;

/**
 * Created by Administrator on 2017/8/31.
 */
@Component
public class PushAct {

    private static final Log log = LogFactory.getLog(PushAct.class);

    private static final String ALL = "@all";

    public void push(String actId, String jobId, boolean all) {
        push(actId, jobId, all, null);
    }

    public void push(String actId, String jobId, boolean all, Integer agentid) {
        //获取活动名称
        ActPushInfo pushInfo = pushService.getGroupPushInfo(actId);

        Integer magent;
        String actType;
        String actUrl = "/wx/entertain/act/show.html?id=";
        if (Act.ACT_TYPE_GROUP.equals(pushInfo.getActType())) {
            magent = WeixinAgent.AGENT_GROUP;
            actType = "协会活动";
        } else if (Act.ACT_TYPE_TEAM.equals(pushInfo.getActType())) {
            magent = WeixinAgent.AGENT_ID;
            actType = "团队活动";
            actUrl = "/wx/entertain/act/team/show.html?id=";
        } else if (Act.ACT_TYPE_BIG.equals(pushInfo.getActType())) {
            magent = WeixinAgent.AGENT_ID;
            actType = "大型活动";
            actUrl = "/wx/entertain/act/big/show.html?id=";
            all = true;
        } else if (Act.ACT_TYPE_REGION.equals(pushInfo.getActType())) {
            magent = WeixinAgent.AGENT_ID;
            actType = "大型活动";
            actUrl = "/wx/entertain/act/big/show.html?id=";
            all = true;
        } else if (Act.ACT_TYPE_DEPT.equals(pushInfo.getActType())) {
            magent = WeixinAgent.AGENT_ID;
            actType = "大型活动";
            actUrl = "/wx/entertain/act/big/show.html?id=";
            all = true;
        }else if(Act.ACT_TYPE_CANTEEN.equals(pushInfo.getActType())){
            magent=WeixinAgent.AGENT_CANTEEN;
            actType="饭堂活动";
            actUrl="/wx/canteen/act/show.html?id=";
            all=true;
        }else {
            magent = 0;
            actType = "";
        }

        if (agentid == null) {
            agentid = magent;
        }

        String title;
        if (pushInfo.getRegular() == 1) {
            title = "定期活动";
        } else {
            title = "活动提醒";
        }
        title = "【" + title + "】" + (actType) + "--" + pushInfo.getName();

        StringBuffer cnt = new StringBuffer();
        if (!TypeUtils.empty(pushInfo.getTime())) {
            cnt.append("活动时间：" + pushInfo.getTime() + (1 == pushInfo.getAllIs() ? "全体成员参加 \n" : "\n"));
        }
        if (!TypeUtils.empty(pushInfo.getAddress())) {
            cnt.append("活动地点：" + pushInfo.getAddress() + " \n");
        }
        cnt.append("简介：" + pushInfo.getInfo());

        String imgPath;
        if (!TypeUtils.empty(pushInfo.getPath())) {
            imgPath = GlobalValue.getUrl() + pushInfo.getPath();
        } else {
            imgPath = GlobalValue.getUrl() + "/image/default/act.png";
        }

        String url = new String();
        if (!TypeUtils.empty(pushInfo.getLink())) {
            url = pushInfo.getLink();
        } else {
            url = GlobalValue.getUrl() + actUrl + pushInfo.getId()+"&agentId="+Agent.GROUP.getAgentId();
        }

        News news = new News();
        news.addArtice(new Article(title, cnt.toString(), url, imgPath));
        Message<News> message = new Message<>(agentid, news);
        if (all) {
            message.addUser(ALL);
        } else {
            List<String> userids = pushService.getUsers(pushInfo.getGroupId());
            message.setTouser(userids);
        }
        try {
            MsgApi.send(message);
        } catch (/*Weixin*/Exception e) {
            log.error("send weixin message[" + JsonUtils.stringfy(message) + "] error！！！", e);
        }

        //非定期活动，更新job状态为停止
        if (jobId != null && pushInfo.getRegular() == 0) {
            pushService.updateStatus(jobId, ScheduleJob.STATUS_STOP);
            scheduleManager.removeJob(actId, JobGroup.ACT_PUSH.getType());
        }
    }

    public void canteenPush(String actId, String jobId, boolean all, Integer agentid) {
        //获取活动名称
        ActPushInfo pushInfo = pushService.getCanteenPushInfo(actId);

        Integer magent;
        String actType;
        String actUrl="/wx/canteen/act/show.html?id=";
        if(CanteenAct.ACT_TYPE_CANTEEN.equals(pushInfo.getActType())){
            magent=WeixinAgent.AGENT_CANTEEN;
            actType="饭堂活动";
            all=true;
        }else {
            magent = 0;
            actType = "";
        }

        if (agentid == null) {
            agentid = magent;
        }

        String title;
        if (pushInfo.getRegular() == 1) {
            title = "定期活动";
        } else {
            title = "活动提醒";
        }
        title = "【" + title + "】" + (actType) + "--" + pushInfo.getName();

        StringBuffer cnt = new StringBuffer();
        if (!TypeUtils.empty(pushInfo.getTime())) {
            cnt.append("活动时间：" + pushInfo.getTime() + (1 == pushInfo.getAllIs() ? "全体成员参加 \n" : "\n"));
        }
        if (!TypeUtils.empty(pushInfo.getAddress())) {
            cnt.append("活动地点：" + pushInfo.getAddress() + " \n");
        }
        cnt.append("简介：" + pushInfo.getInfo());

        String imgPath;
        if (!TypeUtils.empty(pushInfo.getPath())) {
            imgPath = GlobalValue.getUrl() + pushInfo.getPath();
        } else {
            imgPath = GlobalValue.getUrl() + "/image/default/act.png";
        }

        String url = new String();
        if (!TypeUtils.empty(pushInfo.getLink())) {
            url = pushInfo.getLink();
        } else {
            url = GlobalValue.getUrl() + actUrl + pushInfo.getId()+"&agentId="+Agent.CANTEEN.getAgentId();
        }

        News news = new News();
        news.addArtice(new Article(title, cnt.toString(), url, imgPath));
        Message<News> message = new Message<>(agentid, news);
        if (all) {
            message.addUser(ALL);
        } else {
            List<String> userids = pushService.getCanteenUsers(pushInfo.getGroupId());
            message.setTouser(userids);
        }
        try {
            MsgApi.send(message);
        } catch (/*Weixin*/Exception e) {
            log.error("send weixin message[" + JsonUtils.stringfy(message) + "] error！！！", e);
        }

        //非定期活动，更新job状态为停止
        if (jobId != null && pushInfo.getRegular() == 0) {
            pushService.updateStatus(jobId, ScheduleJob.STATUS_STOP);
            scheduleManager.removeJob(actId, JobGroup.ACT_PUSH.getType());
        }
    }

    @Autowired
    private PushService pushService;

    @Autowired
    private ScheduleManager scheduleManager;


    public void execute(String actId, String jobId, boolean all) {
        push(actId, jobId, all, Agent.GROUP.getAgentId());
    }

}
