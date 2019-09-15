package wxgh.admin.web.party.article;

import com.libs.common.crypt.URL;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import pub.spring.web.mvc.ActionResult;
import wxgh.app.sys.api.FileApi;
import wxgh.app.utils.HtmlUtils;
import wxgh.data.party.ArticleList;
import wxgh.data.party.di.exam.UserList;
import wxgh.entity.party.PartyArticle;
import wxgh.entity.pub.SysFile;
import wxgh.param.party.ArticleParam;
import wxgh.param.pub.file.FileParam;
import wxgh.sys.service.admin.party.article.ArticleService;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.net.URLDecoder;
import java.util.Date;
import java.util.List;

/**
 * Created by Sheng on 2017/8/28.
 */
@Controller
public class ApiController {

    @Autowired
    private ArticleService articleService;

    @Autowired
    private FileApi fileApi;

    @RequestMapping
    public ActionResult get(ArticleParam param){

        param.setPageIs(true);
        param.setRowsPerPage(6);

        List<ArticleList> list=articleService.getArticleList(param);

        return ActionResult.okAdmin(list,param);
    }

    @RequestMapping
    public ActionResult add_data(PartyArticle partyArticle, @RequestParam("addMyimg") MultipartFile file){
        SysFile sysFile;
        if(!file.isEmpty()){
            sysFile = fileApi.addFile(file, new FileParam());
        } else {
            return ActionResult.error("图片不能为空");
        }

        partyArticle.setAddTime(new Date());
        partyArticle.setContent(URL.decode(partyArticle.getContent()));
        partyArticle.setImg(sysFile.getFileId());

        articleService.saveArticle(partyArticle);

        return ActionResult.ok();
    }

    @RequestMapping
    public ActionResult del_article(String id){
        articleService.delete(id);
        return ActionResult.ok();
    }

    @RequestMapping
    public ActionResult get_one(Integer id){
        ActionResult result = ActionResult.ok();
        PartyArticle partyArticle = articleService.getArticle(id);
        result.setData(partyArticle);
        return result;
    }

    @RequestMapping
    public ActionResult update_data(PartyArticle partyArticle, HttpServletRequest request){

        if (request instanceof MultipartHttpServletRequest) {
            MultipartFile file = ((MultipartHttpServletRequest) request).getFile("editMyimg");
            if (!file.isEmpty()) {
                SysFile sysFile = fileApi.addFile(file, new FileParam());
                partyArticle.setImg(sysFile.getFileId());
            }
        }

        partyArticle.setContent(URL.decode(partyArticle.getContent()));

        articleService.updateArticle(partyArticle);

        return ActionResult.ok();

    }

    @RequestMapping
    public ActionResult search_user(String key) {
        ActionResult result = ActionResult.ok();
        List<UserList> userList = articleService.searchUser(key);
        result.setData(userList);
        return result;
    }

    @RequestMapping
    public ActionResult push(String id, String userid, Boolean isAllPush) {
        articleService.push(id, userid, isAllPush);
        return ActionResult.ok();
    }
}
