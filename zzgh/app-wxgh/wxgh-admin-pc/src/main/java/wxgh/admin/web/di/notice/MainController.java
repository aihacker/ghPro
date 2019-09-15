package wxgh.admin.web.di.notice;

import com.libs.common.crypt.URL;
import com.libs.common.file.FileUtils;
import com.weixin.WeixinException;
import com.weixin.api.MsgApi;
import com.weixin.bean.message.Article;
import com.weixin.bean.message.Message;
import com.weixin.bean.message.MpArticle;
import com.weixin.bean.message.News;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import pub.spring.web.mvc.ActionResult;
import pub.spring.web.mvc.model.Model;
import pub.web.ServletUtils;
import wxgh.app.consts.WeixinAgent;
import wxgh.app.sys.api.FileApi;
import wxgh.app.sys.variate.GlobalValue;
import wxgh.data.pub.NameValue;
import wxgh.entity.party.di.Notice;
import wxgh.entity.pub.SysFile;
import wxgh.param.pub.file.FileParam;
import wxgh.sys.service.admin.di.notice.NoticeService;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
public class MainController {
    public static final String USERIDS = "@all";

    @Autowired
    private NoticeService noticeService;

    @RequestMapping
    public void add(Model model) {
        List<NameValue> groupList = noticeService.getGroupList();
        model.put("group", groupList);
    }

    @RequestMapping
    public void edit(Model model, Integer id) {
        Notice notice = noticeService.getNoticeById(id);
        model.put("notice", notice);
    }

    @RequestMapping
    public void list(Model model) {
        List<NameValue> groupList = noticeService.getGroupList();
        model.put("group", groupList);
    }

    @RequestMapping
    public void show(Model model, Integer id) {
        Notice notice = noticeService.getNoticeById(id);
        model.put("notice", notice);
    }


    /*微信图文*/
    @RequestMapping
    public ActionResult mpnews(MpArticle mpArticle, Integer safe, String groupId, Integer informationData, Integer push, @RequestParam("img") MultipartFile multipartFile) {
        if (multipartFile != null && !StringUtils.isEmpty(multipartFile.getOriginalFilename())) {
            File toFile = null;
            try {
                SysFile sysFile = fileApi.addFile(multipartFile, new FileParam());

                String real = ServletUtils.getRequest().getSession().getServletContext().getRealPath("/");
                toFile = new File(real, sysFile.getFilePath());

                String content = URL.decode(mpArticle.getContent());
                content = fileApi.replaceImage(content);

//                MediaResult mediaResult = MediaApi.upload(MediaType.IMAGE, toFile);
//                mpArticle.setMediaId(mediaResult.getMediaId());
//                mpArticle.setContent(content);

                Notice notice = new Notice();
                notice.setTitle(mpArticle.getTitle());
                notice.setContent(content);
                notice.setImage(sysFile.getFileId());
                notice.setGroupId(groupId);
                notice.setInformationData(informationData);
                notice.setAddTime(new Date());
                notice.setAuthor(mpArticle.getAuthor());
                notice.setLink(mpArticle.getUrl());
                notice.setDescription(mpArticle.getDigest());

                Integer noticeId = noticeService.addNotice(notice);

                if (push != null && push == 1) {
                    String host = GlobalValue.getUrl();
                    String url = host + "/wx/party/di/notice/show.html?id=" + noticeId;
                    String imgurl = host + sysFile.getFilePath();

                    List<Article> articles = new ArrayList<>();
                    articles.add(new Article("【公告】" + notice.getTitle(), notice.getDescription(), url, imgurl));

                    News news = new News();
                    news.setArticles(articles);
                    Message<News> message = new Message<>(WeixinAgent.AGENT_JIJIAN, news);
                    message.setSafe(safe);
                    if (groupId.equals("0")) {
                        message.addUser("18402028708");
                    } else {
                        List<String> groupUserList = noticeService.pushUsers(groupId);
                        message.setTouser(groupUserList);
                    }
                    MsgApi.send(message);
                }
            } catch (WeixinException e) {
                FileUtils.delete(toFile);
                ActionResult.fail(e);
            }
        } else {
            return ActionResult.fail("请上传封面图片");
        }
        return ActionResult.ok();
    }

    @Autowired
    private FileApi fileApi;
}

