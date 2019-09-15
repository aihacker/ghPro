package wxgh.wx.web.common.bbs;

import com.weixin.WeixinException;
import com.weixin.utils.ApiList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import pub.spring.web.mvc.ActionResult;
import pub.spring.web.mvc.model.Model;
import wxgh.app.session.bean.SeUser;
import wxgh.app.session.user.UserSession;
import wxgh.app.utils.WeixinUtils;
import wxgh.data.common.bbs.BbsList;
import wxgh.entity.common.Article;
import wxgh.param.common.bbs.ArticleParam;
import wxgh.sys.service.weixin.common.bbs.ArticleService;
import wxgh.sys.service.weixin.common.bbs.ForumService;

import java.util.List;

/**
 * ----------------------------------------------------------
 *
 * @Description 热点论坛首页
 * ----------------------------------------------------------
 * @Author Ape
 * ----------------------------------------------------------
 * @Email <16511660@qq.com>
 * ----------------------------------------------------------
 * @Date 2017-07-26 09:43
 * ----------------------------------------------------------
 */
@Controller
public class MainController {

    @Autowired
    private ForumService forumService;

    @Autowired
    private ArticleService articleService;

    /**
     * 首页
     *
     * @return
     */
    @RequestMapping
    public void index() {

    }

    @RequestMapping
    public ActionResult list(ArticleParam query) {
        query.setPageIs(true);

        List<BbsList> bbsLists = articleService.listBbs(query);

        return ActionResult.okRefresh(bbsLists, query);
    }

    @RequestMapping
    public ActionResult getArticleByName(Model model, String atlName) {

        SeUser user = UserSession.getUser();
        if (user == null) {
            return ActionResult.error("未知用户");
        }

        ActionResult result = ActionResult.ok();
        List<Article> artList;
        artList = forumService.getAreaList(atlName, user.getDeptid());
        model.put("artList", artList);
        result.setData(artList);
        return result;
    }

    @RequestMapping
    public void add(Model model) throws WeixinException {
        WeixinUtils.sign(model, ApiList.getImageApi());
    }

}


