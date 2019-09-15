package wxgh.wx.web.party;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import pub.spring.web.mvc.ActionResult;
import pub.spring.web.mvc.model.Model;
import wxgh.app.session.user.UserSession;
import wxgh.data.chat.ChatList;
import wxgh.data.party.ArticleList;
import wxgh.entity.chat.ChatGroup;
import wxgh.entity.party.PartyArticle;
import wxgh.entity.party.Zan;
import wxgh.param.party.ArticleParam;
import wxgh.sys.service.weixin.chat.ChatService;
import wxgh.sys.service.weixin.party.PartyArticleService;
import wxgh.sys.service.weixin.party.ZanService;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;

/**
 * Created by cby on 2017/7/29.
 */
@Controller
public class MainController {

    @RequestMapping
    public void index() {

    }

    @RequestMapping
    public void chat_old(Model model) {
        String userid = UserSession.getUserid();
        Map<Integer, List<ChatList>> map = chatService.listGroup(new int[]{ChatGroup.TYPE_TRIBE, ChatGroup.TYPE_PARTY, ChatGroup.TYPE_DI}, userid);

        if (map != null) {
            model.put("partys", map.get(ChatGroup.TYPE_PARTY));
            model.put("tribes", map.get(ChatGroup.TYPE_TRIBE));
            model.put("dis", map.get(ChatGroup.TYPE_DI));
        }
    }

    @RequestMapping
    public void info(Integer id, Model model, HttpSession session) {

        Boolean isSee = (Boolean) session.getAttribute("party_zan_session_" + id);
        if (isSee == null || !isSee) {
            partyArticleService.updateSeeNumb(id);
            session.setAttribute("party_zan_session_" + id, true);
        }

        PartyArticle article = partyArticleService.get(id);

        String userid = UserSession.getUserid();
        Zan zan = zanService.getZan(userid, Zan.TYPE_PARTY, id);
        model.put("isZan", zan != null);
        model.put("a", article);
    }

    @RequestMapping
    public void list(Integer type, Model model) {
        String typename = null;
        if (type == 1) {
            typename = "中央精神";
        } else if (type == 2) {
            typename = "教育学习";
        } else if (type == 3) {
            typename = "党内规章";
        } else if (type == 4) {
            typename = "企业党建";
        } else if (type == 5) {
            typename = "文件制度";
        } else if (type == 6) {
            typename = "他山之石";
        } else {
            typename = "未知";
        }
        model.put("title", typename);
    }

    @RequestMapping
    public ActionResult get_list(ArticleParam param) {
        param.setPageIs(true);
        List<ArticleList> list = partyArticleService.getPartys(param);
        return ActionResult.okRefresh(list, param);
    }

    @RequestMapping
    public ActionResult zan(Integer id) {
        String userid = UserSession.getUserid();
        synchronized (this) {
            Zan zan = zanService.getZan(userid, Zan.TYPE_PARTY, id);
            boolean isZan = zan == null;
            if (zan != null) {
                zanService.delZan(zan.getId()); //删除赞
                partyArticleService.updateZanNumb(id, "jian"); //更新赞数量
            } else {
                zan = new Zan();
                zan.setUserid(userid);
                zan.setZanType(Zan.TYPE_PARTY);
                zan.setZanId(id);
                zanService.save(zan);

                partyArticleService.updateZanNumb(id, "add");
            }
            return ActionResult.ok(null, isZan);
        }

    }

    @Autowired
    private ZanService zanService;

    @Autowired
    private PartyArticleService partyArticleService;

    @Autowired
    private ChatService chatService;
}
