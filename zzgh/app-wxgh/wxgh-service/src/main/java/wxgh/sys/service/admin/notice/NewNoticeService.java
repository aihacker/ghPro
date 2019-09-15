package wxgh.sys.service.admin.notice;

import com.libs.common.crypt.URL;
import com.libs.common.type.TypeUtils;
import com.weixin.WeixinException;
import com.weixin.api.MsgApi;
import com.weixin.bean.message.Article;
import com.weixin.bean.message.Message;
import com.weixin.bean.message.News;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import pub.dao.jdbc.PubDao;
import pub.dao.jdbc.sql.SQL;
import pub.dao.jdbc.sql.bean.Join;
import pub.spring.web.mvc.ActionResult;
import wxgh.app.consts.WeixinAgent;
import wxgh.app.sys.api.FileApi;
import wxgh.app.sys.variate.GlobalValue;
import wxgh.entity.notice.NoticeNews;
import wxgh.entity.party.PartyArticle;
import wxgh.entity.pub.SysFile;
import wxgh.param.pub.file.FileParam;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by cby on 2017/8/29.
 */
@Service
public class NewNoticeService {

    public static final String USERIDS = "@all";

    @Autowired
    private PubDao generalDao;

    @Autowired
    private FileApi fileApi;

    @Transactional
    public int saveNews(NoticeNews noticeNews) {
        SQL sql = new SQL.SqlBuilder()
                .field("title,content,image,link,type,add_time,brief_info,author")
                .value(":title,:content,:image,:link,:type,:addTime,:briefInfo,:author")
                .insert("news_notice")
                .build();
        int i = generalDao.insertAndGetKey(sql.sql(), noticeNews);
        return i;
    }

    public List<NoticeNews> getNewList(NoticeNews param){
        SQL.SqlBuilder sql=new SQL.SqlBuilder()
                .table("news_notice n")
                .field("n.id,n.title,n.content,s.file_path as image,n.link,n.type,n.deptid,n.add_time")
                .join("t_sys_file s","s.file_id=n.image", Join.Type.LEFT);
        List<NoticeNews> noticeNewss = generalDao.queryPage(sql, param, NoticeNews.class);
        return noticeNewss;
    }

    @Transactional
    public void del_new(String id){
        generalDao.execute(SQL.deleteByIds("news_notice", id));
    }

    public NoticeNews get_one(Integer id){
        SQL.SqlBuilder sql=new SQL.SqlBuilder()
                .table("news_notice n")
                .field("n.brief_info,n.id,n.title,n.content,s.file_path as image,n.link,n.type,n.deptid,n.add_time")
                .join("t_sys_file s","s.file_id=n.image", Join.Type.LEFT).where("n.id = ?");
        return generalDao.query(NoticeNews.class,sql.build().sql(),id);
    }

    public void push(String id, String userid, Boolean isAllPush) {
        String[] ids = id.split(",");
        List<Article> articles = new LinkedList<>();
        for (int i = 0; i < ids.length; i++) {
            SQL sql = new SQL.SqlBuilder()
                    .select("t_news_notice a")
                    .field("a.title,a.link as url,a.brief_info as description,(SELECT f.file_path FROM t_sys_file f WHERE f.file_id = a.image ) as picurl")
                    .where("a.id = ?")
                    .build();
            Article article = generalDao.query(Article.class, sql.sql(), ids[i]);
            if(TypeUtils.empty(article.getUrl()))
                article.setUrl(GlobalValue.getUrl() + "/wx/notice/news/show.html?id=" + ids[i]);
            article.setPicurl(GlobalValue.getUrl() + article.getPicurl());

            articles.add(article);
        }
        News news = new News();
        news.setArticles(articles);

        Message<News> message = new Message<>(WeixinAgent.AGENT_NEWS, news);

        if (userid != null) {
            message.addUser(userid);
        } else if (isAllPush != null && isAllPush) {
            message.addUser(USERIDS);
        }

        try {
            MsgApi.send(message);
        } catch (WeixinException e) {
            e.printStackTrace();
        }
    }

    @Transactional
    public void updateNotice(NoticeNews noticeNews){
        SQL.SqlBuilder sql= new SQL.SqlBuilder().update("news_notice n")
                .set("n.title=:title,n.content=:content,n.type=:type")
                .where("n.id = :id");
        if(noticeNews.getImage()!=null){
            sql.set("n.image = :image");
        }
        if(noticeNews.getBriefInfo()!=null){
            sql.set("n.brief_info = :briefInfo");
        }
        if(noticeNews.getLink()!=null){
            sql.set("n.link= :link");
        }
        generalDao.executeBean(sql.build().sql(),noticeNews);
    }

}
