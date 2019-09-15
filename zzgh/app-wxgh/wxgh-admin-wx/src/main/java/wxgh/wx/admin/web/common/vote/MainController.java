package wxgh.wx.admin.web.common.vote;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import pub.spring.web.mvc.ActionResult;
import pub.spring.web.mvc.model.Model;
import wxgh.app.consts.Status;
import wxgh.app.session.bean.SeUser;
import wxgh.app.session.user.UserSession;
import wxgh.app.sys.task.WeixinPush;
import wxgh.data.pub.push.ReplyPush;
import wxgh.entity.common.vote.VotePicOption;
import wxgh.entity.common.vote.Voted;
import wxgh.param.common.vote.QueryVoted;
import wxgh.sys.service.weixin.common.vote.VoteService;
import wxgh.sys.service.weixin.pub.UserInfoService;

import java.util.ArrayList;
import java.util.List;

/**
 * @author hhl
 * @create 2017-08-09
 **/
@Controller
public class MainController {

    @Autowired
    private WeixinPush weixinPush;

    @RequestMapping
    public void index(){}

    @RequestMapping
    public void main(Model model){

    }

    @RequestMapping
    public ActionResult applyListRefresh(QueryVoted query){
        ActionResult actionResult = ActionResult.ok();
        SeUser seUser = UserSession.getUser();

        List<Integer> deptIds= userInfoService.getTestUserDeptIds(null);
        System.out.print(deptIds);

        List<Voted> voteds = new ArrayList<>();
        if (1 == query.getIsFirst()) {
            voteds = voteService.applyListRefresh(query);
        } else if (0 == query.getIsFirst()) {
            voteds = voteService.applyListMore(query);
        }
        System.out.print(voteds);
        actionResult.setData(voteds);

//        if (!UserUtils.isInList(deptIds, seUser.getDeptid())) {
//            List<Voted> voteds1 = new ArrayList<>();
//            for (Voted voted : voteds) {
//                if (!UserUtils.isInList(deptIds, voted.getDeptid())) {
//                    voteds1.add(voted);
//                }
//            }
//            Integer num = query.getNum() == null ? 15 : query.getNum();
//            if (voteds1.size() < num) {
//                voteds1 = voteService.getApplysNotInTargetDeptId(query, deptIds);
//            }
//            actionResult.setData(voteds1);
//        }

        return actionResult;
    }

    @RequestMapping
    public void detail(Model model,Integer id){
        Voted voted = voteService.getOne(id);
        model.put("vote", voted);
        model.put("options", voteService.getOptionsByVoteId(id));
        model.put("thisStatus", voted.getStatus());
    }

    @RequestMapping
    public ActionResult shenhe(QueryVoted queryVoted){
        ActionResult actionResult = ActionResult.ok();
        Integer integer = voteService.updateStatus(queryVoted);
        Integer status = 0;
        if (null != integer) {
            if (integer > 0) {
                status = 1;
                if (status == 1) { //审核通过，发送推文消息
                    weixinPush.vote(queryVoted.getId());
                }
                actionResult.setMsg("success");
            }
        } else {
            actionResult.setMsg("fail");
        }

        String toUserid = voteService.getUseridByVoteId(queryVoted.getId());
        //回复消息，通知用户审核已通过
        ReplyPush push = new ReplyPush(toUserid, status == 1 ? Status.NORMAL.getStatus() : Status.FAILED.getStatus());
        push.setMsg("投票审核结果");
        weixinPush.reply(push);

        return actionResult;
    }

    @RequestMapping
    public void pic(Model model,Integer id){
        Voted voted = voteService.getOne(id);
        model.put("vote", voted);
        model.put("options", voteService.getPicOptionsByVoteId(id));
        model.put("thisStatus", voted.getStatus());
    }


    @Autowired
    private UserInfoService userInfoService;

    @Autowired
    private VoteService voteService;

}
