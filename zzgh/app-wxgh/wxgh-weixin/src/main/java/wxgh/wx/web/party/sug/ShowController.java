package wxgh.wx.web.party.sug;


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
import wxgh.app.consts.WeixinAgent;
import wxgh.app.session.bean.SeUser;
import wxgh.app.session.user.UserSession;
import wxgh.app.sys.task.PushAsync;
import wxgh.app.utils.WeixinUtils;
import wxgh.data.pub.push.Push;
import wxgh.entity.party.sug.PartySug;
import wxgh.sys.service.weixin.party.sug.PartySugService;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/5/10.
 */
@Controller
public class ShowController {

    @RequestMapping
    public void index(Model model, Integer id) throws WeixinException {

        PartySug partySug = partySugService.getSug(id);
        if (partySug != null) {
            partySug.setTimeStr(DateUtils.formatDate(partySug.getAddTime()));

            model.put("s", partySug);

            List<String> apis = ApiList.getContactApi();
            WeixinUtils.sign(model, apis);
            WeixinUtils.sign_contact(model);
        }
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
            push.setAgentId(WeixinAgent.AGENT_SUG);
            pushAsync.sendByPartySugTran(user.getUserid(), id, push);
        }
        return ActionResult.ok();
    }

    @RequestMapping
    public ActionResult s(){
        Push push = new Push();
        List<String> list = new ArrayList<>();
        list.add("15902064445");
        push.setTousers(list);
        push.setAgentId(WeixinAgent.AGENT_SUG);
        pushAsync.sendByPartySugTran("15902064445", 5, push);
        return ActionResult.ok();
    }


    @Autowired
    private PushAsync pushAsync;

    @Autowired
    private PartySugService partySugService;

}
