package wxgh.wx.web.common.suggest;


import com.weixin.Agent;
import com.weixin.WeixinException;
import com.weixin.utils.ApiList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import pub.spring.web.mvc.ActionResult;
import pub.spring.web.mvc.model.Model;
import wxgh.app.consts.WeixinAgent;
import wxgh.app.session.bean.SeUser;
import wxgh.app.session.user.UserSession;
import wxgh.app.sys.task.PushAsync;
import wxgh.app.sys.task.WeixinPush;
import wxgh.app.utils.WeixinUtils;
import wxgh.data.pub.push.ApplyPush;
import wxgh.entity.common.suggest.SuggestCate;
import wxgh.entity.common.suggest.UserSuggest;
import wxgh.param.common.suggest.SuggestCateParam;
import wxgh.param.common.suggest.SuggestParam;
import wxgh.param.common.suggest.UserSuggestQuery;
import wxgh.sys.service.weixin.common.suggest.SuggestCateService;
import wxgh.sys.service.weixin.common.suggest.UserSuggestService;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;

/**
 * ----------------------------------------------------------
 *
 * @Description ----------------------------------------------------------
 * @Author Ape
 * ----------------------------------------------------------
 * @Email <16511660@qq.com>
 * ----------------------------------------------------------
 * @Date 2017-07-27 16:39
 * ----------------------------------------------------------
 */
@Controller
public class MainController {

    @Autowired
    private SuggestCateService suggestCateService;

    @Autowired
    private UserSuggestService userSuggestService;

    @Autowired
    private PushAsync pushAsync;

    @Autowired
    private WeixinPush weixinPush;

    @RequestMapping
    public void index(Model model) throws WeixinException {
        List<SuggestCate> cates = suggestCateService.getCates(new SuggestCateParam());
        SeUser user = UserSession.getUser();
        Integer deptid = user.getDeptid();
        if (cates != null && cates.size() > 0) {
            for (int i = 0; i < cates.size(); i++) {
                cates.get(i).setSuggests(userSuggestService.getSuggests(new UserSuggestQuery(cates.get(i).getId(), UserSuggest.STATUS_PASS, deptid)));
            }
        }
        model.put("cates", cates);

        WeixinUtils.sign(model, ApiList.getApiList());
    }

    @RequestMapping
    public ActionResult list(Model model, SuggestParam suggestParam) {
        SeUser user = UserSession.getUser();
        Integer deptid = user.getDeptid();
        suggestParam.setDeptid(deptid);
        if (suggestParam.getCid() != null)
            return ActionResult.okRefresh(userSuggestService.getSuggestList(suggestParam), suggestParam);
        return ActionResult.okRefresh(null, suggestParam);
    }

    @RequestMapping
    public ActionResult add(Model model) throws WeixinException {
        ActionResult result = ActionResult.ok();
        model.put("cates", suggestCateService.getCates(new SuggestCateParam()));
        WeixinUtils.sign(model, ApiList.getApiList());
        return result;
    }

    /**
     * 添加数据
     *
     * @param info
     * @param request
     * @return
     */
    @RequestMapping
    public ActionResult save(UserSuggest info, HttpServletRequest request) {

//        List<AreaAdmin> admins = areaAdminService.getAreaAdmins(new AreaAdminQuery(info.getUserid()));
//        if (admins != null && admins.size() > 0) {
//            info.setStatus(1);
//        } else {
//            info.setStatus(0);
//        }
        SeUser user = UserSession.getUser();
        if (user == null) {
            return ActionResult.error("你还没有登录哦");
        }

        info.setType(1);
        info.setUserid(user.getUserid());
        info.setDeptid(user.getDeptid().toString());
        info.setCreateTime(new Date());
        info.setStatus(UserSuggest.STATUS_SH); //默认通过
        info.setCommNum(0);
        info.setLovNum(0);
        info.setSeeNum(0);
        info.setTranNum(0);

        Integer id = userSuggestService.addSuggest(info);

        //推送到 管理员审核 应用
        ApplyPush push = new ApplyPush(ApplyPush.Type.SUGGEST, user.getUserid(), id.toString());
        push.setAgentId(Agent.ADMIN.getAgentId());
        push.setMsg("会员提案发布申请");
        weixinPush.apply(push);
        return ActionResult.ok();
    }

    @RequestMapping
    public void msg(Model model) {
    }

}

