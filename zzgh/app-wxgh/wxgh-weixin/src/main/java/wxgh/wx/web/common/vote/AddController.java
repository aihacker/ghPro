package wxgh.wx.web.common.vote;

import com.weixin.Agent;
import com.weixin.WeixinException;
import com.weixin.utils.ApiList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import pub.spring.web.mvc.ActionResult;
import pub.spring.web.mvc.model.Model;
import wxgh.app.session.bean.SeUser;
import wxgh.app.session.user.UserSession;
import wxgh.app.sys.task.WeixinPush;
import wxgh.app.utils.WeixinUtils;
import wxgh.data.pub.push.ApplyPush;
import wxgh.entity.common.vote.VoteOption;
import wxgh.entity.common.vote.Voted;
import wxgh.sys.service.weixin.common.vote.VoteOptionService;
import wxgh.sys.service.weixin.common.vote.VoteService;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
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
 * @Date 2017-07-29 15:34
 *----------------------------------------------------------
 */
@Controller
public class AddController {

    @Autowired
    private VoteService voteService;

    @Autowired
    private VoteOptionService voteOptionService;

    @Autowired
    private WeixinPush weixinPush;

    /**
     * 发布投票页面
     * @param model
     */
    @RequestMapping
    public void index(Model model) throws WeixinException{
       WeixinUtils.sign(model, ApiList.getContactApi());
        WeixinUtils.sign_contact(model);
    }

    /**
     * 发布投票插入数据
     * @param voted
     * @param options
     * @param request
     * @return
     */
    @RequestMapping
    public ActionResult add(Voted voted, @RequestParam("options[]") List<String> options, HttpServletRequest request) {

        SeUser user = UserSession.getUser();
        if (options == null || options.size() <= 0) {
            return ActionResult.error("投票选项不能为空哦");
        }

        voted.setCreateTime(new Date());
        voted.setStatus(0); //默认不显示
        voted.setType(1);
        voted.setIsdel(1);
        voted.setDeptid(user.getDeptid());

        Integer voteId = voteService.AddVoted(voted);

        List<VoteOption> voteOptionList = new ArrayList<>();
        for (String opStr : options) {
            VoteOption voteOption = new VoteOption();
            voteOption.setCreateTime(new Date());
            voteOption.setVoteid(voteId);
            voteOption.setOptions(opStr);
            voteOption.setTicketNum(0);
            voteOption.setIsdel(1);
            voteOptionList.add(voteOption);
        }
        voteOptionService.addOptions(voteOptionList);

        //推送消息
        ApplyPush push = new ApplyPush(ApplyPush.Type.VOTE, user.getUserid(), voteId.toString());
        push.setAgentId(Agent.ADMIN.getAgentId());
        push.setMsg("投票发布申请");
        weixinPush.apply(push);
        return ActionResult.ok();
    }
    
}

