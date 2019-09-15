package wxgh.wx.web.party.sug;

import com.weixin.WeixinException;
import com.weixin.utils.ApiList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import pub.spring.web.mvc.ActionResult;
import pub.spring.web.mvc.model.Model;
import pub.type.RefData;
import pub.utils.StrUtils;
import pub.utils.TypeUtils;
import wxgh.app.consts.WeixinAgent;
import wxgh.app.session.bean.SeUser;
import wxgh.app.session.user.UserSession;
import wxgh.app.sys.task.PushAsync;
import wxgh.app.utils.WeixinUtils;
import wxgh.data.pub.push.Push;
import wxgh.entity.party.sug.PartySug;
import wxgh.param.party.sug.SugQuery;
import wxgh.sys.service.weixin.party.sug.PartySugService;

import java.util.List;

/**
 * ----------------------------------------------------------
 * @Description
 * ----------------------------------------------------------
 * @Author Ape
 * ----------------------------------------------------------
 * @Email <16511660@qq.com>
 * ----------------------------------------------------------
 * @Date 2017-08-18  17:17
 * ----------------------------------------------------------
 */
@Controller
public class MainController {

    @Autowired
    private PartySugService partySugService;

    @Autowired
    private PushAsync pushAsync;

    @RequestMapping
    public void index() {

    }

    @RequestMapping
    public ActionResult list(SugQuery query) {

        SeUser user = UserSession.getUser();
        if (user == null) {
            return ActionResult.error("未知用户");
        }
        query.setUserid(user.getUserid());

        Integer count = partySugService.getCount(query);

        query.setPageIs(true);
        query.setRowsPerPage(10);
        query.setTotalCount(count);

        List<PartySug> sugs = partySugService.getSugs(query);

        RefData refData = new RefData();
        refData.put("sugs", sugs);
        refData.put("total", query.getPages());
        refData.put("current", query.getCurrentPage());

        return ActionResult.ok(null, refData);
    }

    @RequestMapping
    public void add(Model model) throws WeixinException {
        List<String> apiList = ApiList.getImageApi();
        apiList.add(ApiList.OPENENTERPRISECONTACT);
        WeixinUtils.sign(model, apiList);
        WeixinUtils.sign_contact(model);
    }

    @RequestMapping
    public ActionResult add1(PartySug sug) {
        SeUser user = UserSession.getUser();
        if (user == null) {
            return ActionResult.error("请重新进入页面！");
        }

        /*if (StrUtils.empty(sug.getUsers())) {
            return ActionResult.error("必须要@领导哦！");
        }*/

        sug.setUserid(user.getUserid());
        partySugService.save(sug);

        // TODO 推送
        Push push = new Push();
        push.setAgentId(WeixinAgent.AGENT_SUG);
        /*push.setTousers(TypeUtils.strToList(sug.getUsers()));*/
        pushAsync.sendByPartySugAite(sug.getId(), push);
        return ActionResult.ok();
    }


}
