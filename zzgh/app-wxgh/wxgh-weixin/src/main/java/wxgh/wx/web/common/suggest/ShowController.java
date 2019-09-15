package wxgh.wx.web.common.suggest;


import com.libs.common.data.DateUtils;
import com.libs.common.type.TypeUtils;
import com.weixin.WeixinException;
import com.weixin.utils.ApiList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import pub.spring.web.mvc.ActionResult;
import pub.spring.web.mvc.model.Model;
import pub.utils.StrUtils;
import wxgh.app.session.bean.SeUser;
import wxgh.app.session.user.UserSession;
import wxgh.app.sys.task.PushAsync;
import wxgh.app.utils.WeixinUtils;
import wxgh.app.utils.suggest.SuggestSeeSession;
import wxgh.data.pub.push.Push;
import wxgh.entity.common.suggest.SuggestComm;
import wxgh.entity.common.suggest.SuggestLov;
import wxgh.entity.common.suggest.UserSuggest;
import wxgh.param.common.suggest.CommentQuery;
import wxgh.param.common.suggest.LovQuery;
import wxgh.sys.service.weixin.common.suggest.SuggestCommService;
import wxgh.sys.service.weixin.common.suggest.SuggestLovService;
import wxgh.sys.service.weixin.common.suggest.UserSuggestService;

import java.util.Date;
import java.util.List;

/**
 *----------------------------------------------------------
 * @Description 
 *----------------------------------------------------------
 * @Author  Ape
 *----------------------------------------------------------
 * @Email <16511660@qq.com>
 *----------------------------------------------------------
 * @Date 2017-07-28 09:35
 *----------------------------------------------------------
 */
@Controller
public class ShowController {

    @Autowired
    private UserSuggestService userSuggestService;

    @Autowired
    private SuggestCommService suggestCommService;

    @Autowired
    private SuggestLovService suggestLovService;

    @Autowired
    private PushAsync pushAsync;

    @RequestMapping
    public void index(Model model, Integer id) throws WeixinException {

        //更新浏览次数
        if (!SuggestSeeSession.getIsSee(id)) {
            userSuggestService.updateSeeNum(id);
            SuggestSeeSession.setIsSee(true, id);
        }

        UserSuggest userSuggest = userSuggestService.getSuggest(id);
        model.put("sugget", userSuggest);

        SeUser user = UserSession.getUser();
        boolean isLov = true;
        if (user != null) {
            SuggestLov suggestLov = suggestLovService.getLov(new LovQuery(userSuggest.getId(), user.getUserid(), SuggestLov.TYPE_SUG));
            if (suggestLov == null) {
                isLov = false;
            }
        }
        model.put("isLov", isLov);

        WeixinUtils.sign(model, ApiList.getApiList());
        WeixinUtils.sign_contact(model);
    }

    @RequestMapping
    public ActionResult list(Integer id) {
        CommentQuery query = new CommentQuery();
        query.setSugId(id);
        query.setParentid(0);
        query.setOrderBy("s.add_time DESC");
        List<SuggestComm> suggestComms = suggestCommService.getComments(query);

        SeUser user = UserSession.getUser();
        if (user != null) {
            if (suggestComms != null && suggestComms.size() > 0) {
                for (int i = 0; i < suggestComms.size(); i++) {
                    SuggestComm suggestComm = suggestComms.get(i);
                    SuggestLov suggestLov = suggestLovService.getLov(new LovQuery(suggestComm.getId(), user.getUserid(), 2));
                    if (suggestLov == null) {
                        suggestComms.get(i).setLovIs(false);
                    }
                }
            }
        }
        return ActionResult.ok(null, suggestComms);
    }

    @RequestMapping
    public ActionResult lov(Integer id, Integer type) {

        SeUser user = UserSession.getUser();
        if (user == null) {
            return ActionResult.error("对不起，您暂时还不能操作哦");
        }

        SuggestLov suggestLov = new SuggestLov();
        suggestLov.setAddTime(new Date());
        suggestLov.setUserid(user.getUserid());
        suggestLov.setSugId(id);
        suggestLov.setStatus(1);
        suggestLov.setSugType(type);

        suggestLovService.addLov(suggestLov);

        if (type == 1) { //更新建言池
            userSuggestService.updateLovNumb(id);
        } else { //更新回复
            suggestCommService.updateLov(id);
        }

        UserSuggest userSuggest = userSuggestService.getOneSuggest(id);
        Integer isFromInnovate = 0;
        if (userSuggest != null) {
            isFromInnovate = userSuggest.getIsFromInnovate();
        }
        if (null != isFromInnovate) {
            if (isFromInnovate == 1) {
                userSuggestService.addScore(user.getUserid(), id);
            }
        }

        return ActionResult.ok();
    }

    @RequestMapping
    public ActionResult comment(SuggestComm suggestComm) {
        SeUser user = UserSession.getUser();
        if (user == null) {
            return ActionResult.error("你暂时不能发表评论哦");
        }

        suggestComm.setUserid(user.getUserid());
        suggestComm.setAddTime(new Date());
        suggestComm.setStatus(SuggestComm.STATUS_NORMAL);
        suggestComm.setLovNum(0);
        if (suggestComm.getParentid() == null) {
            suggestComm.setParentid(0);
        }

        Integer sugId = suggestCommService.addComment(suggestComm);

        suggestComm.setTimeStr(DateUtils.formatDate(suggestComm.getAddTime()));
        suggestComm.setId(sugId);
        suggestComm.setUsername(user.getName());
        suggestComm.setAvatar(user.getAvatar());
        suggestComm.setLovIs(false);

        UserSuggest userSuggest1 = userSuggestService.getOneSuggest(suggestComm.getSugId());
        Integer isFromInnovate = 0;
        if (userSuggest1 != null) {
            isFromInnovate = userSuggest1.getIsFromInnovate();
        }
        if (null != isFromInnovate) {
            if (isFromInnovate == 1) {
                userSuggestService.addScore(user.getUserid(), suggestComm.getSugId());
            }
        }

        return ActionResult.ok(null, suggestComm);
    }

    private Integer addScore(String userId, Integer addId, Integer type) {
        String sql = "select 1 from t_suggest_lov where userid='zhb' and sug_id=47 order by add_time desc limit 1;";
//        Integer isLovExist =
        if (0 < type) {

        }
        // TODO 用户积分
//        UserScore userScore = new UserScore();
//        userScore.setUserid(userId);
//        userScore.setScore(1f);
//        userScore.setAddTime(new Date());
//        userScore.setAddType("lov_comm_suggest");
//        userScore.setAddId(addId);
//        userScore.setStatus(1);
//        userScore.setScoreType(2);
//        userScore.setGroupStr("association_score");
//        userScore.setJsTime(DateFuncs.dateTimeToStr(new Date(), "yyyy-MM-dd"));
//        Integer id = userScoreService.save(userScore);
        return 0;
    }

    /**
     * 转发
     *
     * @param id
     * @return
     */
    @RequestMapping
    public ActionResult tran(Integer id, String users) {
        SeUser user = UserSession.getUser();
        if (user == null) {
            return ActionResult.error("未知用户");
        }
        if (!StrUtils.empty(users)) {
            List<String> userids = TypeUtils.strToList(users);
            // TODO 推送
            Push push = new Push();
            push.setTousers(userids);
            pushAsync.sendBySuggestTran(user.getUserid(), id, push);
        }
        return ActionResult.ok();
    }
    
}

