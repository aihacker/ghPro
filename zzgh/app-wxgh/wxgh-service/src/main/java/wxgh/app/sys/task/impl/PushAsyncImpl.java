package wxgh.app.sys.task.impl;

import com.libs.common.json.JsonUtils;
import com.libs.common.type.TypeUtils;
import com.weixin.Agent;
import com.weixin.WeixinException;
import com.weixin.api.MsgApi;
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
import wxgh.app.consts.WeixinAgent;
import wxgh.app.sys.task.PushAsync;
import wxgh.app.sys.variate.GlobalValue;
import wxgh.data.common.publicity.PublicityData;
import wxgh.data.entertain.act.ActList;
import wxgh.data.pub.push.Push;
import wxgh.data.pub.push.info.PushSug;
import wxgh.entity.common.Article;
import wxgh.entity.party.party.PartyAct;
import wxgh.entity.pub.SysFile;
import wxgh.sys.service.weixin.pub.PushService;

import java.util.List;
import java.util.Map;

import static wxgh.app.sys.variate.GlobalValue.host;

/**
 * Created by Administrator on 2017/7/19.
 */
@Component
public class PushAsyncImpl implements PushAsync {

    private static final Log log = LogFactory.getLog(PushAsyncImpl.class);

    private static final String ALL = "@all";

    @Autowired
    private PushService pushService;

    @Autowired
    private PubDao pubDao;

    @Override
    @Async
    public void send_act(String actId, Push push) {

    }

    @Async
    @Override
    public void sendBySheYing(Integer id, Push push) {
        String text1 = "【有新作品上传了哦~】";
        StringBuilder sb = new StringBuilder(text1);
        String url = GlobalValue.getUrl() + "/wx/party/beauty/show/index.html?id=" + id +"&agentId="+ Agent.SHEYING.getAgentId();
        sb.append("<a href=\""+ url +"\">查看详情</a>");
        Text text = new Text(sb.toString());
        Message<Text> message = new Message<>(push.getAgentId(), text);

        if (push.getAll()) {
            message.addUser(ALL);
        } else {
            List<String> userids = push.getTousers();
            message.setTouser(userids);
        }
        sendMessage(message);
    }

    @Async
    @Override
    public void sendByGarden(Integer id, Push push) {
        String sql = "select d.name from t_party_dept d left join t_party_act a on a.groupid = d.group_id where a.id =? ";
        String name = pubDao.query(String.class,sql,id);
        String text1 = "【"+name+"】"+"发布了一个新活动,进入支部园地";
        StringBuilder sb = new StringBuilder(text1);
        String url = GlobalValue.getUrl() + "/wx/party/garden/index.html";
        sb.append("<a href=\""+ url +"\">查看详情</a>");
        Text text = new Text(sb.toString());
        Message<Text> message = new Message<>(push.getAgentId(), text);

        if (push.getAll()) {
            message.addUser(ALL);
        } else {
            List<String> userids = push.getTousers();
            message.setTouser(userids);
        }
        sendMessage(message);
    }

    @Override
    @Async
    public void sendByPartySugTran(String userid, Integer id, Push push) {
        String text1 = "【你有一条新建议】";
        StringBuilder sb = new StringBuilder(text1);
        String url = GlobalValue.getUrl() + "/wx/party/sug/show/index.html?id=" + id;
        sb.append("<a href=\""+ url +"\">查看详情</a>");
        Text text = new Text(sb.toString());

        Message<Text> message = new Message<>(push.getAgentId(), text);
        message.setTouser(push.getTousers());
        sendMessage(message);

    }

    @Async
    @Override
    public void sendByPartySugAite(Integer id, Push push) {
        PushSug pushSug = pushService.pushSug(id);
        String username = pushSug.getUsername();

        String userUrl = GlobalValue.getUrl() + "/wx/pub/user/show.html?userid=" + pushSug.getUserid();
        String text1 = "【<a href=\"" + userUrl + "\">" + username + "</a>】@了你一下\n";
        StringBuilder sb = new StringBuilder(text1);
        String url = GlobalValue.getUrl() + "/wx/party/sug/show/index.html?id=" + id;
        sb.append("来自建议提出\n");
        sb.append("<a href=\""+ url +"\">查看详情</a>");
        Text text = new Text(sb.toString());

        Message<Text> message = new Message<>(push.getAgentId(), text);
        /*message.setTouser(push.getTousers());*/
        message.addUser(ALL);
        sendMessage(message);
    }

    @Override
    @Async
    public void sendBySuggestTran(String userid, Integer id, Push push) {
        String username = pushService.getUser(userid);

        String text1 = "【" + username + "】给你转发了一条建议,";
        StringBuilder sb = new StringBuilder(text1);
        String url = GlobalValue.getUrl() + "/wx/common/suggest/show/index.html?id=" + id;
        sb.append("<a href=\""+ url +"\">查看详情</a>");
        Text text = new Text(sb.toString());

        Message<Text> message = new Message<>(push.getAgentId(), text);
        message.setTouser(push.getTousers());
        sendMessage(message);
    }

    private void sendMessage(Message message) {
        try {
            MsgApi.send(message);
        } catch (WeixinException e) {
            log.error("send weixin message[" + JsonUtils.stringfy(message) + "] error！！！", e);
        }
    }

    @Override
    @Async
    public void sendByPlaceClose(String text, Push push) {
        String text1 = "【停场公告】";
        StringBuilder sb = new StringBuilder(text1);
        sb.append(text);
        Text text2 = new Text(sb.toString());
        Message<Text> message = new Message<>(push.getAgentId(), text2);

        if (push.getAll()) {
            message.addUser(ALL);
        } else {
            List<String> userids = push.getTousers();
            message.setTouser(userids);
        }
        sendMessage(message);
    }

    @Override
    @Async
    public void sendArticle(Integer ids){
        //host地址
        String host = GlobalValue.getUrl();

        SQL.SqlBuilder sql= new SQL.SqlBuilder()
                .table("article a")
                .field("a.*")
                .where("a.atl_id = ?")
                .select();
        Article article = pubDao.query(Article.class,sql.build().sql(),ids);

        if(article.getFileIds()!=""){
            String[] imgList = article.getFileIds().split(",");
            String img = imgList[imgList.length-1];
            SQL.SqlBuilder sql2 = new SQL.SqlBuilder()
                    .table("sys_file s")
                    .field("s.file_path")
                    .where("s.file_id = ?")
                    .select();
            article.setAvatar(host+pubDao.query(SysFile.class,sql2.build().sql(),img).getFilePath());
        }else{
            article.setAvatar(host + getDefaultImg());
        }

        //url
        String url = host + "/wx/common/bbs/article/show.html?id=" + article.getAtlId()+"&agentId="+Agent.BBS.getAgentId();

        News news = new News();
        news.addArtice(new com.weixin.bean.message.Article("【热点论坛】" + article.getAtlName(), article.getAtlContent(), url,article.getAvatar() ));
        Message<News> message = new Message<>(WeixinAgent.AGENT_ID, news);

        message.addUser(ALL);

        sendMessage(message);
    }

    @Override
    @Async
    public void sendActBig(Integer ids){
        //host地址
        String host = GlobalValue.getUrl();

        SQL.SqlBuilder sql= new SQL.SqlBuilder()
                .table("act a")
                .field("a.*")
                .where("a.id = ?")
                .select();
        ActList actList = pubDao.query(ActList.class,sql.build().sql(),ids);

        if(actList.getImgId()!=""){
            String[] imgList = actList.getImgId().split(",");
            String img = imgList[imgList.length-1];
            SQL.SqlBuilder sql2 = new SQL.SqlBuilder()
                    .table("sys_file s")
                    .field("s.file_path")
                    .where("s.file_id = ?")
                    .select();
            actList.setPath(host+pubDao.query(SysFile.class,sql2.build().sql(),img).getFilePath());
        }else{
            actList.setPath(host + getDefaultImg());
        }

        //url
        String url = host + "/wx/entertain/act/big/show.html?id=" + actList.getId()+"&agentId="+Agent.ACT.getAgentId();    News news = new News();
        news.addArtice(new com.weixin.bean.message.Article("【大型活动】" + actList.getName(), actList.getInfo(), url,actList.getPath() ));
        Message<News> message = new Message<>(WeixinAgent.AGENT_ID, news);

        message.addUser(ALL);

        sendMessage(message);
    }

    private String getDefaultImg() {
        return "/image/default/act.png";
    }

}
