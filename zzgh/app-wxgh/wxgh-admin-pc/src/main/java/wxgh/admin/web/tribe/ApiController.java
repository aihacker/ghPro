package wxgh.admin.web.tribe;

import com.libs.common.crypt.URL;
import com.libs.common.json.JsonUtils;
import com.weixin.WeixinException;
import com.weixin.api.MsgApi;
import com.weixin.bean.message.Article;
import com.weixin.bean.message.Message;
import com.weixin.bean.message.MpArticle;
import com.weixin.bean.message.News;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import pub.spring.web.mvc.ActionResult;
import wxgh.app.consts.WeixinAgent;
import wxgh.app.sys.api.FileApi;
import wxgh.app.sys.task.impl.WeixinPushImpl;
import wxgh.app.sys.variate.GlobalValue;
import wxgh.entity.notice.NoticeNews;
import wxgh.entity.pub.SysFile;
import wxgh.entity.pub.User;
import wxgh.entity.tribe.Score;
import wxgh.entity.tribe.TribeAct;
import wxgh.entity.tribe.TribeResult;
import wxgh.param.pub.file.FileParam;
import wxgh.param.tribe.admin.PointParam;
import wxgh.param.tribe.admin.TribeActParam;
import wxgh.sys.service.admin.notice.NewNoticeService;
import wxgh.sys.service.admin.tribe.PointService;
import wxgh.sys.service.admin.tribe.TribeActService;
import wxgh.sys.service.admin.tribe.TribeResultService;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by cby on 2017/8/18.
 */
@Controller
public class ApiController {

    private static final Log log = LogFactory.getLog(WeixinPushImpl.class);

    @Autowired
    private TribeResultService tribeResultService;

    @Autowired
    private PointService pointService;

    @Autowired
    private TribeActService tribeActService;

    @Autowired
    private NewNoticeService newNoticeService;

    @Autowired
    private FileApi fileApi;

    @RequestMapping
    public ActionResult send(MpArticle mpArticle, Integer safe, Integer send, @RequestParam("img") MultipartFile multipartFile) throws WeixinException {

        if (multipartFile != null && !StringUtils.isEmpty(multipartFile.getOriginalFilename())) {
            SysFile sysFile = fileApi.addFile(multipartFile, new FileParam());
            String fileId = sysFile.getFileId();
            String filePath = sysFile.getFilePath();
            String path = GlobalValue.getUrl() + filePath;
            String content = URL.decode(mpArticle.getContent());//文章转码

            TribeResult tribeResult = new TribeResult();
            tribeResult.setTitle(mpArticle.getTitle());
            tribeResult.setBriefInfo(mpArticle.getDigest());
            tribeResult.setAuthor(mpArticle.getAuthor());
            tribeResult.setLink(mpArticle.getUrl());
            tribeResult.setAddDate(new Date());
            tribeResult.setCoverImg(fileId);
            tribeResult.setContent(content);
            int id = tribeResultService.save(tribeResult);
            if (send != null && send == 1) {
                //将消息进行推送
                News news = new News();
                List<Article> list = new ArrayList<>();
                String url = GlobalValue.getUrl() + "/wx/party/tribe/result/info.html?id="+id;
                Article article = new Article(mpArticle.getTitle(),mpArticle.getDigest(),url,path);
                list.add(article);
                news.setArticles(list);
                Message<News> message = new Message<>(WeixinAgent.AGENT_TRIBE, news);
                message.addUser("@all");
                message.setSafe(safe);
                sendMessage(message);
            }

        }
        return ActionResult.ok();
    }

    private void sendMessage(Message message) {
        try {
            MsgApi.send(message);
        } catch (WeixinException e) {
            log.error("send weixin message[" + JsonUtils.stringfy(message) + "] error！！！", e);
        }
    }

    @RequestMapping
    public ActionResult get(PointParam pointParam) {
        pointParam.setPageIs(true);
        List<Score> scoreMessage = pointService.getScoreMessage(pointParam);
        pointParam.setTotalCount(scoreMessage.size());
        for (int i = 1; i <= scoreMessage.size(); i++) {
            scoreMessage.get(i - 1).setId(i);
        }
        return ActionResult.okAdmin(scoreMessage, pointParam);
    }

    @RequestMapping
    public ActionResult update(Integer by_id, Float point, String username) {
        pointService.updateScore(by_id, point, username);
        return ActionResult.ok();
    }

    @RequestMapping
    public ActionResult delete(Integer by_id, String username) {
        pointService.delete(by_id, username);
        return ActionResult.ok();
    }

    @RequestMapping
    public ActionResult get_act(TribeActParam tribeActParam) {
        tribeActParam.setPageIs(true);
        List<TribeAct> tribeActList = tribeActService.getData(tribeActParam);
        for (int i = 1; i <= tribeActList.size(); i++)
            tribeActList.get(i - 1).setDeptid(i);
        int size = tribeActList.size();
        tribeActParam.setTotalCount(size);
        return ActionResult.okAdmin(tribeActList, tribeActParam);
    }

    @RequestMapping
    public ActionResult del_act(String id) {
        String[] ids = id.split(",");
        for (String i : ids) {
            tribeActService.del(i);
        }
        return ActionResult.ok();
    }

    @RequestMapping
    public ActionResult update_status(TribeActParam tribeActParam) {
        tribeActService.updateData(tribeActParam);
        return ActionResult.ok();
    }

    @RequestMapping
    public ActionResult edit(TribeAct tribeAct) {
        tribeActService.updateAct(tribeAct);
        return ActionResult.ok();
    }

    @RequestMapping
    public ActionResult search(String key) {
        List<User> users = tribeActService.searchUser(key);
        return ActionResult.okWithData(users);
    }

    @RequestMapping
    public ActionResult add_act(TribeAct tribeAct) {
        tribeAct.setStatus(1);
        tribeAct.setAddTime(new Date());
        tribeActService.save(tribeAct);
        return ActionResult.ok();
    }

    @RequestMapping
    public ActionResult send_news(MpArticle mpArticle, Integer safe, Integer send, Integer type, @RequestParam("img") MultipartFile multipartFile) {
        if (multipartFile != null && !StringUtils.isEmpty(multipartFile.getOriginalFilename())) {
            SysFile sysFile = fileApi.addFile(multipartFile, new FileParam());
            String fileId = sysFile.getFileId();
            String filePath = sysFile.getFilePath();
            String content = URL.decode(mpArticle.getContent());//文章转码
            NoticeNews noticeNews = new NoticeNews();
            noticeNews.setTitle(mpArticle.getTitle());
            noticeNews.setContent(content);
            noticeNews.setAddTime(new Date());
            noticeNews.setImage(fileId);
            noticeNews.setLink(mpArticle.getUrl());
            noticeNews.setType(type);
            String path = GlobalValue.getUrl() + filePath;
            int id = newNoticeService.saveNews(noticeNews);
            if (fileId != null) {
                if (send != null && send == 1) {
                    //将消息进行推送
                    List<Article> articles = new ArrayList<>();
                    String url = GlobalValue.getUrl() + "/wx/notice/news/show.html?id=" + id;
                    Article article = new Article(mpArticle.getTitle(), mpArticle.getDigest(), url, path);
                    articles.add(article);
                    News news = new News();

                    news.setArticles(articles);
                    Message<News> message = new Message<>(WeixinAgent.AGENT_NEWS, news);
                    message.addUser("@all");
                    message.setSafe(safe);
                    sendMessage(message);
                }

            }
        }
        return ActionResult.ok();
    }

    @RequestMapping
    public ActionResult new_lists(NoticeNews noticeNews){
        noticeNews.setPageIs(true);
        List<NoticeNews> newList = newNoticeService.getNewList(noticeNews);
        for (int i=0;i<newList.size();i++){
            newList.get(i).setDeptid(i+1);
        }
        return ActionResult.okAdmin(newList,noticeNews);
    }

    @RequestMapping
    public ActionResult del_new(String id){
        newNoticeService.del_new(id);
        return ActionResult.ok();
    }

}