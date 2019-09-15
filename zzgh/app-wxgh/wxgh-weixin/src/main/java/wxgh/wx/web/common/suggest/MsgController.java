package wxgh.wx.web.common.suggest;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import pub.spring.web.mvc.ActionResult;
import pub.spring.web.mvc.model.Model;
import wxgh.app.session.bean.SeUser;
import wxgh.app.session.user.UserSession;
import wxgh.param.common.suggest.UserSuggestQuery;
import wxgh.sys.service.weixin.common.suggest.UserSuggestService;

/**
 *----------------------------------------------------------
 * @Description 
 *----------------------------------------------------------
 * @Author  Ape
 *----------------------------------------------------------
 * @Email <16511660@qq.com>
 *----------------------------------------------------------
 * @Date 2017-07-28 16:21
 *----------------------------------------------------------
 */
@Controller
public class MsgController {

    @Autowired
    private UserSuggestService userSuggestService;

    @RequestMapping
    public void index(Model model){}

    @RequestMapping
    public ActionResult del(Integer id) {
        ActionResult actionResult = ActionResult.ok();
        try {
            userSuggestService.del(id);
            actionResult.setMsg("success");
        } catch (Exception e) {
            actionResult.setMsg("fail");
        }
        return actionResult;
    }

    @RequestMapping
    public ActionResult applyListRefresh(UserSuggestQuery query) {
        ActionResult actionResult = ActionResult.ok();
        SeUser user = UserSession.getUser();
        query.setUserid(user.getUserid());
        actionResult.setData(userSuggestService.applyListRefresh(query));
        return actionResult;
    }

    @RequestMapping
    public ActionResult applyListMore(UserSuggestQuery query) {
        ActionResult actionResult = ActionResult.ok();
        SeUser user = UserSession.getUser();
        query.setUserid(user.getUserid());
        Integer oldestId = query.getUserSuggestOldestId();
        if (1 == query.getIsFirst()) {
            actionResult.setData(userSuggestService.applyListRefresh(query));
        } else if (0 == query.getIsFirst()) {
            actionResult.setData(userSuggestService.applyListMore(query));
        }
        return actionResult;
    }

    @RequestMapping
    public void detail(Model model, Integer id){
        model.put("userSuggest", userSuggestService.getOneSuggest(id));
    }

}

