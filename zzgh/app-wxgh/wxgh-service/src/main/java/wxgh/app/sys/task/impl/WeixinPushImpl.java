package wxgh.app.sys.task.impl;

import com.libs.common.data.DateUtils;
import com.libs.common.json.JsonUtils;
import com.libs.common.type.ListUtils;
import com.libs.common.type.TypeUtils;
import com.weixin.Agent;
import com.weixin.WeixinException;
import com.weixin.api.MsgApi;
import com.weixin.bean.message.Article;
import com.weixin.bean.message.Message;
import com.weixin.bean.message.News;
import com.weixin.bean.message.Text;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import pub.dao.jdbc.PubDao;
import pub.dao.jdbc.sql.SQL;
import wxgh.app.consts.Status;
import wxgh.app.consts.WeixinAgent;
import wxgh.app.sys.PushAct;
import wxgh.app.sys.task.WeixinPush;
import wxgh.app.sys.variate.GlobalValue;
import wxgh.data.common.publicity.PublicityData;
import wxgh.data.entertain.sport.push.PushList;
import wxgh.data.party.member.PartyList;
import wxgh.data.pub.push.ApplyPush;
import wxgh.data.pub.push.ReplyPush;
import wxgh.data.pub.push.info.PushBBS;
import wxgh.data.pub.push.info.PushExam;
import wxgh.data.pub.push.info.PushPartyAct;
import wxgh.data.pub.push.info.PushVote;
import wxgh.data.pub.woman.TeachPush;
import wxgh.data.tribe.TribeResultData;
import wxgh.data.union.group.act.result.PushResult;
import wxgh.entity.canteen.CanteenAct;
import wxgh.entity.party.party.PartyNotice;
import wxgh.entity.pub.tag.TagType;
import wxgh.entity.pub.tag.WxTag;
import wxgh.param.party.member.PartyParam;
import wxgh.sys.service.admin.party.member.PartyMemberService;
import wxgh.sys.service.admin.tribe.TribeResultService;
import wxgh.sys.service.weixin.common.vote.VoteService;
import wxgh.sys.service.weixin.party.branch.PartyNoticeService;
import wxgh.sys.service.weixin.pub.PushService;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2017/8/10.
 */
@Component
public class WeixinPushImpl implements WeixinPush {

    private static final Log log = LogFactory.getLog(WeixinPushImpl.class);

    private static final String ALL = "@all";

    @Override
    @Async
    public void apply(ApplyPush push) {
        if (null == push.getAgentId()) {
            push.setAgentId(Agent.ADMIN.getAgentId());
        }

        WxTag tag = pushService.getTag(TagType.WX_ADMIN);
        if (tag == null) {
            log.warn("push weixin message failed：admin tag not exist...");
            return;
        }

        //判断用户是否设置域名
        if (TypeUtils.empty(push.getHost())) {
            push.setHost(GlobalValue.getUrl());
        }
        String userUrl = push.getHost() + "/wx/pub/user/show.html?userid=" + push.getFromUser();

        String url = push.getHost() + push.getType().getUrl();
        if (!TypeUtils.empty(push.getParamVal())) {
            url += push.getParamVal();
        }

        String username = pushService.getUser(push.getFromUser());

        StringBuffer msg = new StringBuffer();
        msg.append("【" + push.getType().getType() + "】\n");
        msg.append("来自：<a href=\"" + userUrl + "\">" + username + "</a>\n");
        if (!TypeUtils.empty(push.getMsg())) {
            msg.append("消息：" + push.getMsg());
            msg.append("\n");
        }
        msg.append("<a href=\"" + url + "\">查看详情</a>");

        Text text = new Text(msg.toString());
        Message<Text> message = new Message<>(1000010, text);
        message.addTag(tag.getTagid());

//        List<String> userIds=new ArrayList<String>();
//        userIds.add("wxgh13450753745");
//        userIds.add("oXSY13318399898");
//        userIds.add("wxgh18022231602");
//        message.setTouser(userIds);
        message.addUser(ALL);
        sendMessage(message);
    }

    @Override
    @Async
    public void reply(ReplyPush push) {
        if (null == push.getAgentId()) {
            push.setAgentId(Agent.ADMIN.getAgentId());
        }

        String statusTxt = "未知";
        if (Status.NORMAL.getStatus().equals(push.getStatus())) {
            statusTxt = "已通过";
        } else if (Status.FAILED.getStatus().equals(push.getStatus())) {
            statusTxt = "未通过";
        }

        StringBuffer msg = new StringBuffer();
        msg.append("【审核结果】\n");
        if (!TypeUtils.empty(push.getMsg())) {
            msg.append(push.getMsg() + "\n");
        }
        msg.append("审核结果：" + statusTxt);

        Text text = new Text(msg.toString());
        Message<Text> message = new Message<>(1000004, text);
        message.addUser(push.getToUser());
        sendMessage(message);
    }

    @Async
    @Override
    public void act(String actId, String jobId, boolean all) {
        pushAct.execute(actId, jobId, all);
    }
    @Async
    @Override
    public void act(String actId, String jobId, boolean all, Integer agentId) {
        if(agentId.equals(WeixinAgent.AGENT_CANTEEN)){
            pushAct.canteenPush(actId,jobId,all,agentId);
        }else{
            pushAct.push(actId, jobId, all, agentId);
        }
    }

    @Async
    @Override
    public void act_result(Integer pushType, Integer resultId) {
        PushResult result = pushService.getActResult(resultId);
        if (result != null) {
            String host = GlobalValue.getUrl();

            StringBuffer cnt = new StringBuffer();
            cnt.append("活动：“" + result.getActName() + "”\n");
            cnt.append(result.getInfo());

            String url = host + "/wx/union/group/act/result/show.html?id=" + result.getId()+"&agentId="+Agent.GROUP.getAgentId();

            String img;
            if (TypeUtils.empty(result.getAvatar())) {
                img = host + getDefaultImg();
            } else {
                img = host + result.getAvatar();
            }
            News news = new News();
            news.addArtice(new Article("【活动成果】" + result.getTitle(), cnt.toString(), url, img));
            Message<News> message = new Message<>(Agent.GROUP.getAgentId(), news);

            if (pushType == 2) {
                message.addUser(ALL);
            } else {
                List<String> toUsers = pushService.getUsers(result.getGroupId());
                message.setTouser(toUsers);
            }
            sendMessage(message);
        }
    }

    @Async
    @Override
    public void act_canteen_result(Integer pushType, Integer resultId) {
        PushResult result = pushService.getActResult(resultId);
        if (result != null) {
            String host = GlobalValue.getUrl();

            StringBuffer cnt = new StringBuffer();
            cnt.append("活动：“" + result.getActName() + "”\n");
            cnt.append(result.getInfo());

            String url = host + "/wx/canteen/act/result/show.html?id=" + result.getId()+"$agentId="+Agent.CANTEEN.getAgentId();

            String img;
            if (TypeUtils.empty(result.getAvatar())) {
                img = host + getDefaultImg();
            } else {
                img = host + result.getAvatar();
            }
            News news = new News();
            news.addArtice(new Article("【活动成果】" + result.getTitle(), cnt.toString(), url, img));
            Message<News> message = new Message<>(Agent.CANTEEN.getAgentId(), news);

            if (pushType == 2) {
                message.addUser(ALL);
            } else {
                List<String> toUsers = pushService.getUsers(result.getGroupId());
                message.setTouser(toUsers);
            }
            sendMessage(message);
        }
    }

    @Async
    @Override
    public void di_exam(Integer examId) {
        PushExam exam = pushService.pushExam(examId);
        if (exam == null) return;
        String host = GlobalValue.getUrl();

        String url = host + "/wx/party/di/show.html?id=" + exam.getId();
        String picUrl = host + (TypeUtils.empty(exam.getPath()) ? getDefaultImg() : exam.getPath());

        StringBuffer cnt = new StringBuffer();
        cnt.append("结束时间：" + DateUtils.toStr(exam.getEndTime(), "yyyy-MM-dd HH:mm"));
        cnt.append("\n面向群体：" + (TypeUtils.empty(exam.getGroupName()) ? "全体人员" : exam.getGroupName()));
        cnt.append("\n" + exam.getInfo());

        Article article = new Article("【考试】" + exam.getName(), cnt.toString(), url, picUrl);
        News news = new News();
        news.addArtice(article);

        Message<News> message = new Message<>(WeixinAgent.AGENT_JIJIAN, news);
        if (exam.getGroupId() == null || exam.getGroupId().equals("0")) {
            message.addUser(ALL);
        } else {
            List<String> toUsers = pushService.getGroupUser(exam.getGroupId());
            message.setTouser(toUsers);
        }
        sendMessage(message);
    }

    @Async
    @Override
    public void bbs(Integer bbsId) {
        PushBBS bbs = pushService.pushBBS(bbsId);
        if (bbs == null) return;

        String host = GlobalValue.getUrl();
        String url = host + "/wx/common/bbs/article/show.html?id=" + bbs.getId()+"&agentId="+Agent.UNION.getAgentId();
        String picUrl = host + (TypeUtils.empty(bbs.getImage()) ? getDefaultImg() : bbs.getImage());

        Article article = new Article("【工会活动】" + bbs.getTitle(), bbs.getContent(), url, picUrl);
        News news = new News();
        news.addArtice(article);

        Message<News> message = new Message<>(Agent.UNION.getAgentId(), news);
        message.addUser(ALL);

        sendMessage(message);
    }

    @Override
    @Async
    public void sportToday(PushList push, Integer dateId) {
        String host = GlobalValue.getUrl();
        String url = host + "/wx/entertain/sport/list.html?dateid=" + dateId +"&agentId="+Agent.SPORT.getAgentId();
        String picurl = host + "/weixin/image/entertain/sport/push.png";

        StringBuffer sb = new StringBuffer();
        sb.append(push.getName() + ", ");
        sb.append("你今天走了：" + push.getStepCount() + "步");
        sb.append("\n<< 点击查看今日排行 >>");

        News news = new News();
        news.addArtice(new Article("今日步数排行", sb.toString(), url, picurl));
        Message<News> message = new Message<>(Agent.SPORT.getAgentId(),news);
        message.setMsg(news);
        message.addUser(push.getUserid());
        sendMessage(message);
    }

    /*@Override
    public void vote(Integer voteId) {
        PushVote pushVote = pushService.pushVote(voteId);
        if (pushVote == null) return;

        String text1 = "【" + pushVote.getUsername() + "】发起了投票,快来围观啊</br>";
        StringBuilder sb = new StringBuilder(text1);
        sb.append(pushVote.getTitle() + "<br>");
        String url = GlobalValue.getUrl() + "/wx/common/vote/index.html";
        sb.append("<a href=\"" + url + "\">查看详情</a>");
        Text text = new Text(sb.toString());

        Message<Text> message = new Message<>(Agent.WORK.getAgentId(), text);
        message.addUser(ALL);

        sendMessage(message);
    }*/


    @Override
    public void vote(Integer voteId) {
        PushVote pushVote = pushService.pushVote(voteId);
        if (pushVote == null) return;

        String text1 = "【" + pushVote.getUsername() + "】发起了投票,快来围观啊</br>";
        StringBuilder sb = new StringBuilder(text1);
        Integer type = pushVote.getType() ;
        String url =null;
        if(type==1) {
            sb.append("普通投票主题：" + pushVote.getTitle() + "<br>");
            url = GlobalValue.getUrl() + "/wx/common/vote/index.html?agentId="+Agent.WORK.getAgentId();
        }else if(type==2){
            sb.append("图片投票主题：" + pushVote.getTitle() + "<br>");
            url = GlobalValue.getUrl() + "/wx/common/vote/pic.html?agentId="+Agent.WORK.getAgentId();
        }
        sb.append("<a href=\"" + url + "\">查看详情</a>");
        Text text = new Text(sb.toString());

        //List<String> userIds = voteService.getUserIds(voteId);

        Message<Text> message = new Message<>(Agent.WORK.getAgentId(), text);
        //if(userIds.get(0).equals("1")){
            message.addUser(ALL);
       /* }else{
            message.setTouser(userIds);
        }
*/
        sendMessage(message);
    }

    private void sendMessage(Message message) {
        try {
            MsgApi.send(message);
        } catch (WeixinException e) {
            log.error("send weixin message[" + JsonUtils.stringfy(message) + "] error！！！", e);
        }
    }

    private String getDefaultImg() {
        return "/image/default/act.png";
    }

    @Override
    @Async
    public void apply_to(ApplyPush push) {

//        WxTag tag = pushService.getTag(TagType.WX_ADMIN);
//        if (tag == null) {
//            log.warn("push weixin message failed：admin tag not exist...");
//            return;
//        }

        //判断用户是否设置域名
        if (TypeUtils.empty(push.getHost())) {
            push.setHost(GlobalValue.getUrl());
        }
        String userUrl = push.getHost() + "/wx/pub/user/show.html?userid=" + push.getFromUser();

        String url = push.getHost() + push.getType().getUrl();

        List<String> toUsers = new ArrayList<String>();
        if (!TypeUtils.empty(push.getParamVal())) {
            url += push.getParamVal();
        }

        String username = pushService.getUser(push.getFromUser());

        StringBuffer msg = new StringBuffer();
        msg.append("【" + push.getType().getType() + "】\n");
        msg.append("来自：<a href=\"" + userUrl + "\">" + username + "</a>\n");
        if (!TypeUtils.empty(push.getMsg())) {
            msg.append("消息：" + push.getMsg());
            msg.append("\n");
        }
        msg.append("<a href=\"" + url + "\">查看详情</a>");

        Text text = new Text(msg.toString());
        Message<Text> message = new Message<>(push.getAgentId(), text);
//        message.addTag(tag.getTagid());
        message.setTouser(push.getToUsers());

        sendMessage(message);
    }

    @Async
    @Override
    public void party_act_result(Integer pushType, Integer resultId) {
        PushResult result = pushService.getPartyActResult(resultId);
        if (result != null) {
            String host = GlobalValue.getUrl();

            StringBuffer cnt = new StringBuffer();
            cnt.append("活动：“" + result.getActName() + "”\n");
            cnt.append(result.getInfo());

            String url = host + "/wx/party/branch/result/show.html?id=" + result.getId();

            String img;
            if (TypeUtils.empty(result.getAvatar())) {
                img = host + getDefaultImg();
            } else {
                img = host + result.getAvatar();
            }
            News news = new News();
            news.addArtice(new Article("【活动成果】" + result.getTitle(), cnt.toString(), url, img));
            Message<News> message = new Message<>(WeixinAgent.AGENT_ZHIBU, news);

            if (pushType == 2) {
                message.addUser(ALL);
            } else {
                List<String> toUsers = pushService.getChatUsers(result.getGroupId());
                message.setTouser(toUsers);
            }
            sendMessage(message);
        }
    }

    @Async
    @Override
    public void party_opinion(Integer type, String groupIds, Integer opinionId, String title) {

        List<String> userids = new ArrayList<>();
        // 全体党员
        if(type.equals(1)){
            userids = pushService.getAllPartyUsers();
        }else{  // 指定群体
            userids = pushService.getPartyGroupUsers(groupIds);
        }

        String text1 = "你有一个意见征集【"+title+"】，快来参与填写吧。";
        StringBuilder sb = new StringBuilder(text1);
        String url = GlobalValue.getUrl() + "/wx/party/opinion/info.html?id=" + opinionId;
        sb.append("<a href=\""+ url +"\">查看详情</a>");
        Text text = new Text(sb.toString());
        Message<Text> message = new Message<>(WeixinAgent.AGENT_PARTY, text);

        message.setTouser(userids);
        sendMessage(message);
    }

    @Async
    @Override
    public void party_judge(Integer type, String groupIds, Integer examId, String title) {

        List<String> userids = new ArrayList<>();
        // 全体党员
        if(type.equals(1) || type.equals(3)){
            userids = pushService.getAllPartyUsers();
        } else if(type.equals(4)){
            //指定分党委
            userids = pushService.getPartyDeptUsers(groupIds);

        } else{  // 指定群体
            userids = pushService.getPartyGroupUsers(groupIds);
        }

        String text1 = "你有一个问卷调查【"+title+"】，快来参与填写吧。";
        StringBuilder sb = new StringBuilder(text1);
        String url = GlobalValue.getUrl() + "/wx/party/branch/vote/show.html?id="+examId;
        sb.append("<a href=\""+ url +"\">查看详情</a>");
        Text text = new Text(sb.toString());
        Message<Text> message = new Message<>(WeixinAgent.AGENT_ID, text);

        message.setTouser(userids);
        sendMessage(message);
    }

    @Async
    @Override
    public void manage_judge(Integer examId, String title) {

        String text1 = "你有一个民主评议【"+title+"】，快来参与填写吧。";
        StringBuilder sb = new StringBuilder(text1);
        String url = GlobalValue.getUrl() + "/wx/manage/judge/show.html?id="+examId;
        sb.append("<a href=\""+ url +"\">查看详情</a>");
        Text text = new Text(sb.toString());
        Message<Text> message = new Message<>(WeixinAgent.AGENT_SUG, text);

        message.addUser(ALL);
        sendMessage(message);
    }

    @Override
    public void manage_act(Integer actId, String title) {

        String text1 = "本月总经理接待日即将开始，快来参与报名吧。";
        StringBuilder sb = new StringBuilder(text1);
        String url = GlobalValue.getUrl() +"/wx/manage/commit/act.html?id="+actId;
        sb.append("<a href=\""+ url +"\">查看详情</a>");
        Text text = new Text(sb.toString());
        Message<Text> message = new Message<>(WeixinAgent.AGENT_SUG, text);

        message.addUser(ALL);
//        message.addUser("wxgh18022231602");
        sendMessage(message);
    }

    @Async
    @Override
    public void di_exam_party(Integer examId) {
        PushExam exam = pushService.pushExam(examId);
        if (exam == null) return;
        String host = GlobalValue.getUrl();

        String url = host + "/wx/party/exam/show.html?id=" + exam.getId();
        String picUrl = host + (TypeUtils.empty(exam.getPath()) ? getDefaultImg() : exam.getPath());

        StringBuffer cnt = new StringBuffer();
        cnt.append("结束时间：" + DateUtils.toStr(exam.getEndTime(), "yyyy-MM-dd HH:mm"));

        if (!TypeUtils.empty(exam.getGroupName()))
            cnt.append("\n面向支部：" + exam.getGroupName());
        else if (!TypeUtils.empty(exam.getPartyName()))
            cnt.append("\n面向党委：" + exam.getPartyName());
        else
            cnt.append("\n面向群体：" + "全体人员");
        cnt.append("\n" + exam.getInfo());

        Article article = new Article("【考试】" + exam.getName(), cnt.toString(), url, picUrl);
        News news = new News();
        news.addArtice(article);

        Message<News> message = new Message<>(WeixinAgent.AGENT_PARTY, news);
        List<String> toUsers = null;

        // 面向党委
        if (exam.getPartyId() != null && exam.getPartyId() > 0)
            toUsers = pushService.getPartyUser(exam.getPartyId());
            // 面向支部
        else if (exam.getGroupId() != null)
            toUsers = pushService.getGroupUser(exam.getGroupId());

        if (toUsers != null) {
            int max = 800;
            if (toUsers.size() > max) {
                List<List<String>> lists = ListUtils.averageAssign(toUsers, toUsers.size() / max + 1);
                for (List<String> list : lists) {
                    if (list != null && list.size() > 0) {
                        System.out.println("分批推送: size (" + list.size() + ")");
                        message.setTouser(list);
                        sendMessage(message);
                    }
                }
            } else {
                message.setTouser(toUsers);
                sendMessage(message);
            }
        } else if (exam.getGroupId() == null || exam.getGroupId().equals("0")) {
            message.addUser(ALL);
            sendMessage(message);
        }
    }

    @Async
    @Override
    public void tribe_result(List<Integer> ids, Integer safe) {

        News news = new News();
        List<Article> list = new ArrayList<>();
        List<TribeResultData> tribeResults = tribeResultService.getList(ids);

        for (TribeResultData result : tribeResults) {
            String url = GlobalValue.getUrl() + "/wx/party/tribe/result/info.html?id=" + result.getId();
            Article article = new Article(result.getTitle(), result.getBriefInfo(), url, GlobalValue.getUrl() + result.getPath());
            list.add(article);
        }

        news.setArticles(list);
        Message<News> message = new Message<>(WeixinAgent.AGENT_TRIBE, news);
        message.addUser(ALL);
        message.setSafe(safe);
        sendMessage(message);

    }

    @Async
    @Override
    public void publicity(String ids) {
        SQL.SqlBuilder sql = new SQL.SqlBuilder()
                .table("publicity p")
//                .sys_file("p.picture")
                .field("(select group_concat(file_path) from t_sys_file where find_in_set(file_id, p.picture)) as imgs")
                .field("p.*");

        sql.where("p.id = ?");
        String[] id = ids.split(",");
        //host地址
        String host = GlobalValue.getUrl();
        for (String i : id) {
            int a = Integer.parseInt(i);
            PublicityData publicity = pubDao.query(PublicityData.class, sql.select().build().sql(), a);

            if (publicity.getImgs() != null)
                publicity.setImgList(TypeUtils.strToList(publicity.getImgs()));

            //描述
            StringBuffer cnt = new StringBuffer();
            cnt.append(publicity.getName());
            //url
            String url = "";
            Map map = null;
            if ("url".equals(publicity.getType())) {
//                map = JsonUtils.parseMap(publicity.getContent(), String.class, Object.class);
//                if (map.containsKey("url")) {
//                    url = (String) map.get("url");
//                }
                url = publicity.getContent();
            } else {
                url = host + "/wx/common/publicity/info.html?id=" + publicity.getId();
            }

            //img
            String img = host + getDefaultImg();
//            if (publicity.getPath() != null) {
            if (publicity.getImgList() != null && publicity.getImgList().size() > 0) {
                img = host + publicity.getImgList().get(0);
            }

            News news = new News();
            news.addArtice(new Article("【工会宣传】" + cnt.toString(), cnt.toString(), url, img));
            Message<News> message = new Message<>(WeixinAgent.AGENT_ID, news);

            message.addUser(ALL);

            sendMessage(message);
        }
    }

    @Async
    @Override
    public void womanTeach(Integer id) {
        TeachPush push = pushService.pushTeach(id);

        String host = GlobalValue.getUrl();
        String url = host + "/wx/union/woman/teach/show.html?id=" + push.getId();
        String imgurl;
        if (TypeUtils.empty(push.getCover())) {
            imgurl = host + getDefaultImg();
        } else {
            imgurl = host + push.getCover();
        }
        News news = new News();
        news.addArtice(new Article("【女子课堂】" + push.getName(), push.getContent(), url, imgurl));

        Message<News> message = new Message<>(WeixinAgent.AGENT_ID, news);
        message.addUser(ALL);
        sendMessage(message);
    }

    @Async
    @Override
    public void partyAct(Integer actid) {
        PushPartyAct act = pushService.pushPartyAct(actid);
        String host = GlobalValue.getUrl();
        String url = host + "/wx/party/branch/act/show.html?id=" + actid;
        String imgurl;
        if (TypeUtils.empty(act.getPath())) {
            imgurl = host + getDefaultImg();
        } else {
            imgurl = host + act.getPath();
        }

        StringBuilder cnt = new StringBuilder();
        cnt.append("活动时间：" + act.getTime() + "\n");
        cnt.append("报名方式：" + (act.getJoinType() == 1 ? "自愿参加" : "必须参加") + "\n");
        cnt.append("简介：" + act.getInfo());

        News news = new News();
        news.addArtice(new Article("【" + act.getTypeName() + "】" + act.getName(), cnt.toString(), url, imgurl));

        Message<News> message = new Message<>(WeixinAgent.AGENT_ZHIBU, news);
        if (act.getJoinType() == 3) {
            message.addUser(ALL);
        } else {
            List<String> userids = pushService.getGroupUser(act.getGroupid());
            message.setTouser(userids);
        }

        sendMessage(message);
    }

    @Async
    @Override
    public void partyNotice(Integer id) {
        PartyNotice partyNotice = partyNoticeService.get(id);

        if (partyNotice == null) return;

        String type = "提醒";
        if (partyNotice.getType().equals(PartyNotice.TYPE_PARTY_COST))
            type = "党费提醒";
        else if (partyNotice.getType().equals(PartyNotice.TYPE_MEETING))
            type = "会议通知";
        else if (partyNotice.getType().equals(PartyNotice.TYPE_EXAM))
            type = "考试提醒";

        if (TypeUtils.empty(partyNotice.getBranchName()))
            partyNotice.setBranchName("支部");

        String text1 = "你有一条" + partyNotice.getBranchName() + "通知~\n【" + type + "】" + partyNotice.getTitle() + "\n☛";
        StringBuilder sb = new StringBuilder(text1);
        String url = GlobalValue.getUrl() + "/wx/party/branch/notice/detail.html?id=" + id;
        sb.append("<a href=\"" + url + "\">查看详情</a>");
        Text text = new Text(sb.toString());

        Message<Text> message = new Message<>(WeixinAgent.AGENT_PARTY, text);

        // 推送给全部党员
        if (partyNotice.getBranchId() == null)
            partyNotice.setBranchId(0);
        if (partyNotice.getBranchId().equals(0)) {
            List<String> userids = pushService.getAllPartyUsers();

            Integer size = userids.size();

            // 大于1000的话进行分批推送
            if (size > 1000) {
                List<List<String>> list = ListUtils.averageAssign(userids, Math.round(size / 1000f));
                for (List<String> item : list) {
                    message.setTouser(item);
                    sendMessage(message);
                }
            } else {
                message.setTouser(userids);
                sendMessage(message);
            }

        } else {
            List<String> userids = pushService.getPartyUsers(partyNotice.getBranchId());
            message.setTouser(userids);
            sendMessage(message);
        }

    }

    @Async
    @Override
    public void pubInfoToUser(String content,Integer isParty,List<String> zhiBuUserList,List<String> dangWuUserList){
        List<String> userIds=new ArrayList<String>();
        String url="";
        if(isParty==1){
            url = GlobalValue.getUrl() + "/wx/party/secreport/add.html?isParty=1";
//            userIds=zhiBuUserList;
        }else{
            url = GlobalValue.getUrl() + "/wx/party/secreport/add.html?isParty=2";
//            userIds=dangWuUserList;
        }
        userIds.add("wxgh13450767972");
        StringBuilder sb=new StringBuilder(content);
        sb.append("<a href=\"" + url + "\">查看详情</a>");
        Text text = new Text(sb.toString());
        Message<Text> message = new Message<>(WeixinAgent.AGENT_ZHIBU, text);
        message.setTouser(userIds);
        sendMessage(message);
    }

    @Async
    public void submitAccount(String news) {
        PartyParam partyParam=new PartyParam();
        partyParam.setWorker("2");
        List<PartyList> partyLists=partyMemberService.partyList(partyParam);
        List<String> userIds=new ArrayList<String>();
//        for(PartyList p:partyLists){
//            if(p.getUserid()!=null){
//                userIds.add(p.getUserid());
//            }
//        }
        String text1 = news;
        StringBuilder sb = new StringBuilder(text1);
        String url=GlobalValue.getUrl() +"/wx/party/account_new/add.html";
        sb.append("<a href=\""+ url +"\">查看详情</a>");
        Text text = new Text(sb.toString());
        Message<Text> message = new Message<>(WeixinAgent.AGENT_ZHIBU, text);
        userIds.add("wxgh13680971392");
        userIds.add("wxgh13889925511");
        message.setTouser(userIds);
//        message.addUser("wxgh13680971392");
        sendMessage(message);
    }

    @Autowired
    private PushService pushService;

    @Autowired
    private PushAct pushAct;

    @Autowired
    private TribeResultService tribeResultService;

    @Autowired
    private PartyNoticeService partyNoticeService;

    @Autowired
    private PartyMemberService partyMemberService;

    @Autowired
    private VoteService voteService;

    @Autowired
    private PubDao pubDao;
}
