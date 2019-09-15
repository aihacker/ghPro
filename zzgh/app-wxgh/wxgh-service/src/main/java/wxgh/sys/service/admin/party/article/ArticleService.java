package wxgh.sys.service.admin.party.article;

import com.libs.common.type.TypeUtils;
import com.weixin.WeixinException;
import com.weixin.api.MsgApi;
import com.weixin.bean.message.Article;
import com.weixin.bean.message.Message;
import com.weixin.bean.message.News;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pub.dao.jdbc.PubDao;
import pub.dao.jdbc.sql.SQL;
import pub.error.ValidationError;
import wxgh.app.consts.WeixinAgent;
import wxgh.app.sys.variate.GlobalValue;
import wxgh.data.party.ArticleList;
import wxgh.data.party.di.exam.UserList;
import wxgh.entity.party.PartyArticle;
import wxgh.param.party.ArticleParam;
import wxgh.sys.dao.party.PartyArticleDao;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Sheng on 2017/8/28.
 */
@Service
public class ArticleService {
    public static final String USERIDS = "@all";

    @Autowired
    private PubDao pubDao;

    @Autowired
    private PartyArticleDao partyArticleDao;

    public List<ArticleList> getArticleList(ArticleParam param) {
        SQL.SqlBuilder sql = new SQL.SqlBuilder()
                .select("t_party_article as a")
                .field("a.id,(SELECT file_path FROM t_sys_file as f WHERE f.file_id = a.img) as img,a.title,a.brief_info as briefInfo,a.content,a.add_time as addTime")
                .where("a.type = :type");
        return pubDao.queryPage(sql, param, ArticleList.class);
    }

    public void saveArticle(PartyArticle partyArticle) {
        partyArticleDao.save(partyArticle);
    }

    public void delete(String id) {
        if (TypeUtils.empty(id)) {
            throw new ValidationError("请选择需要删除的文章！");
        }
        pubDao.execute(SQL.deleteByIds("t_party_article", id));
    }

    public PartyArticle getArticle(Integer id) {
        SQL sql = new SQL.SqlBuilder()
                .select("t_party_article as a")
                .field("a.id,(SELECT file_path FROM t_sys_file as f WHERE f.file_id = a.img) as img,a.title,a.brief_info as briefInfo,a.content,a.add_time as addTime,a.type")
                .where("a.id = ?")
                .build();
        return pubDao.query(PartyArticle.class,sql.sql(),id);
    }

    public void updateArticle(PartyArticle partyArticle) {
        partyArticleDao.updateArticle(partyArticle);
    }

    public List<UserList> searchUser(String key) {
        String name = "%" + key + "%";
        String mobile = key + "%";
        SQL sql = new SQL.SqlBuilder()
                .select("t_user u")
                .field("u.id,u.userid,u.name,u.mobile,(SELECT d.name FROM t_dept d WHERE d.id = u.deptid) as department,u.avatar")
                .where("u.name like ? or u.mobile like ?")
                .build();
        return pubDao.queryList(UserList.class, sql.sql(), name , mobile);
    }


    public void push(String id, String userid, Boolean isAllPush) {
        String[] ids = id.split(",");
        List<Article> articles = new ArrayList<>();
        for(int i = 0; i < ids.length; i++){
            SQL sql = new SQL.SqlBuilder()
                    .select("t_party_article a")
                    .field("a.title,a.brief_info as description,(SELECT f.file_path FROM t_sys_file f WHERE f.file_id = a.img ) as picurl")
                    .where("a.id = ?")
                    .build();
            Article article = pubDao.query(Article.class,sql.sql(),ids[i]);
            article.setUrl(GlobalValue.getUrl()+"/wx/party/di/notice/show.html?id=" + ids[i]);
            article.setPicurl(GlobalValue.getUrl()+article.getPicurl());

            articles.add(article);
        }
        News news =new News();
        news.setArticles(articles);

        Message<News> message = new Message<>(WeixinAgent.AGENT_PARTY, news);

        if(userid != null){
            message.addUser(userid);
        }else if (isAllPush != null && isAllPush){
            message.addUser(USERIDS);
        }

        try {
            MsgApi.send(message);
        } catch (WeixinException e) {
            e.printStackTrace();
        }
    }
}
