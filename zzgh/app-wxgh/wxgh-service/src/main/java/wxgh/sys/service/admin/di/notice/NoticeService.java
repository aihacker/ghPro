package wxgh.sys.service.admin.di.notice;

import com.libs.common.type.TypeUtils;
import com.weixin.WeixinException;
import com.weixin.api.MsgApi;
import com.weixin.bean.message.Article;
import com.weixin.bean.message.Message;
import com.weixin.bean.message.News;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pub.dao.jdbc.PubDao;
import pub.dao.jdbc.sql.SQL;
import pub.dao.jdbc.sql.bean.Join;
import pub.error.ValidationError;
import wxgh.app.consts.WeixinAgent;
import wxgh.app.sys.variate.GlobalValue;
import wxgh.data.party.di.exam.UserList;
import wxgh.data.party.di.notice.NoticeInfo;
import wxgh.data.pub.NameValue;
import wxgh.entity.chat.ChatGroup;
import wxgh.entity.chat.ChatUser;
import wxgh.entity.party.di.Notice;
import wxgh.param.party.di.notice.NoticeParam;
import wxgh.sys.service.weixin.chat.ChatGroupService;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sheng on 2017/8/17.
 */
@Service
public class NoticeService {
    public static final String USERIDS = "@all";

    @Autowired
    private PubDao pubDao;

    public List<String> pushUsers(String groupId) {
        //Todo 党员，特殊处理
        if (ChatGroupService.DY_GROUPID.equals(groupId)) {
            String sql = "select cu.userid from t_chat_user cu " +
                    "join t_chat_group g on cu.group_id = g.group_id " +
                    "where g.type =5 and cu.`status`=1";
            return pubDao.queryList(String.class, sql);
        } else {
            String sql = "select userid from t_chat_user where group_id = ? and status = 1";
            return pubDao.queryList(String.class, sql, groupId);
        }
    }

    public List<NameValue> getGroupList() {
        SQL sql = new SQL.SqlBuilder()
                .table("chat_group")
                .field("group_id as value, name")
                .where("type = ?")
                .build();
        return pubDao.queryList(NameValue.class, sql.sql(), ChatGroup.TYPE_DI);
    }

    public List<ChatUser> getGroupUserList(String groupId) {
        SQL sql = new SQL.SqlBuilder()
                .select("t_chat_user tcu")
                .where("tcu.group_id = ?")
                .build();
        return pubDao.queryList(ChatUser.class, sql.sql(), groupId);
    }

    public Integer addNotice(Notice notice) {
        String sql = "insert into t_di_notice(title,content,image,group_id,information_data,add_time,author,link,description) " +
                "value(:title,:content,:image,:groupId,:informationData,:addTime,:author,:link,:description)";
        return pubDao.insertAndGetKey(sql, notice);
    }

    public Integer getNoticeCount() {
        SQL sql = new SQL.SqlBuilder()
                .select("t_di_notice")
                .count("*")
                .build();
        return pubDao.queryInt(sql.sql());
    }

    public List<NoticeInfo> getNoticeList(NoticeParam param) {
        SQL.SqlBuilder sql = new SQL.SqlBuilder()
                .select("t_di_notice t_n")
                .join("t_sys_file t_f", "t_n.image = t_f.file_id", Join.Type.LEFT)
                .field("t_n.id,t_n.title,t_n.content,t_f.file_path as image,t_n.group_id,t_n.add_time,t_n.author");
        if (param.getGroupId() != null) {
            sql.where("t_n.group_id = :groupId");
        }
        return pubDao.queryPage(sql, param, NoticeInfo.class);
    }

    @Transactional
    public void delete(String id) {
        if (TypeUtils.empty(id)) {
            throw new ValidationError("请选择需要删除的推文！");
        }
//        pubDao.execute(SQL.deleteByIds("t_di_notice", id));

        String[] ids = id.split(",");
        String sql = "delete from t_di_notice where id = ?";

        pubDao.batch(sql, ids);
    }

    public void push(String id, String userid, String group_id) {
        String[] ids = id.split(",");
        List<Article> articles = new ArrayList<>();
        for (int i = 0; i < ids.length; i++) {
            SQL sql = new SQL.SqlBuilder()
                    .select("t_di_notice n")
                    .field("n.title,n.description,(SELECT f.file_path FROM t_sys_file f WHERE f.file_id = n.image ) as picurl")
                    .where("n.id = ?")
                    .build();
            Article article = pubDao.query(Article.class, sql.sql(), ids[i]);
            article.setUrl(GlobalValue.getUrl() + "/wx/party/di/notice/show.html?id=" + ids[i]);
            article.setPicurl(GlobalValue.getUrl() + article.getPicurl());
            articles.add(article);
        }
        News news = new News();
        news.setArticles(articles);

        Message<News> message = new Message<>(WeixinAgent.AGENT_JIJIAN, news);

        if (userid != null) {
            message.addUser(userid);
        } else if (group_id != null) {
            if (group_id.equals("0")) {
                message.addUser(USERIDS);
            } else {
                List<String> groupUserList = pushUsers(group_id);
                message.setTouser(groupUserList);
            }
        }
        try {
            MsgApi.send(message);
        } catch (WeixinException e) {
            e.printStackTrace();
        }
    }

    public List<UserList> searchUser(String key) {
        String name = "%" + key + "%";
        String mobile = key + "%";
        SQL sql = new SQL.SqlBuilder()
                .select("t_user u")
                .field("u.id,u.userid,u.name,u.mobile,(SELECT d.name FROM t_dept d WHERE d.id = u.deptid) as department,u.avatar")
                .where("u.name like ? or u.mobile like ?")
                .build();
        return pubDao.queryList(UserList.class, sql.sql(), name, mobile);
    }

    public Notice getNoticeById(Integer id) {
        SQL sql = new SQL.SqlBuilder()
                .select("t_di_notice")
                .field("id,title,content")
                .where("id=?")
                .build();
        Notice query = pubDao.query(Notice.class, sql.sql(), id);
        return query;
    }

    @Transactional
    public void update_notice(NoticeParam param) {
        SQL.SqlBuilder sql = new SQL.SqlBuilder()
                .update("t_di_notice");
        if (param.getTitle() != null) {
            sql.set("title=:title");
        }
        if (param.getContent() != null) {
            sql.set("content=:content");
        }
        sql.where("id=:id");
        pubDao.executeBean(sql.build().sql(), param);
    }
}
