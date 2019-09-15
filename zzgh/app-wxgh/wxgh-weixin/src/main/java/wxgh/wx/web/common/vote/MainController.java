package wxgh.wx.web.common.vote;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import pub.spring.web.mvc.ActionResult;
import pub.spring.web.mvc.model.Model;
import pub.utils.DateUtils;
import pub.utils.StrUtils;
import wxgh.app.session.bean.SeUser;
import wxgh.app.session.user.UserSession;
import wxgh.entity.common.vote.VoteOption;
import wxgh.entity.common.vote.VotePicOption;
import wxgh.entity.common.vote.Voted;
import wxgh.entity.common.vote.VotedJoin;
import wxgh.param.common.vote.VoteQuery;
import wxgh.sys.service.weixin.common.vote.VoteOptionService;
import wxgh.sys.service.weixin.common.vote.VotePicOptionService;
import wxgh.sys.service.weixin.common.vote.VoteService;
import wxgh.sys.service.weixin.common.vote.VotedJoinService;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *----------------------------------------------------------
 * @Description 
 *----------------------------------------------------------
 * @Author  Ape
 *----------------------------------------------------------
 * @Email <16511660@qq.com>
 *----------------------------------------------------------
 * @Date 2017-07-29 15:01
 *----------------------------------------------------------
 */
@Controller
public class MainController {

    @Autowired
    private VoteService voteService;

    @Autowired
    private VotedJoinService votedJoinService;

    @Autowired
    private VoteOptionService voteOptionService;

    @Autowired
    private VotePicOptionService votePicOptionService;

    @RequestMapping
    public void detail(Integer voteid){

    }

    @RequestMapping
    public void index(Model model) {
        String userid = UserSession.getUserid();
        model.put("userid", userid);
//        User user = UserSession.getUser();
//        if (user == null) {
//            model.put("msg", "对不起，你还没有登录呢");
//            return;
//        }
//
//        List<Voted> voteds = voteService.getVoteds(new VoteQuery(user.getUserid()));
//
//        if (voteds == null || voteds.size() <= 0) {
//            model.put("msg", "暂无投票记录哦");
//            return;
//        }
//
//        Date date = new Date();
//
//        for (int i = 0; i < voteds.size(); i++) {
//            Voted voted = voteds.get(i);
//            Date date1 = voted.getEffectiveTime();
//            if (null != date1) {
//                if (date.getTime() < date1.getTime()) {
//                    voted.setStatus(1);
//                } else {
//                    voted.setStatus(0);
//                }
//            } else {
//                voted.setStatus(0);
//            }
//            Integer count = 0;
//            voteds.get(i).setCreateTimeStr(DateUtils.formatDate(voted.getCreateTime()));
//            for (VoteOption option : voted.getVoteOptions()) {
//                count += option.getTicketNum();
//            }
//            voteds.get(i).setOptionnum(count);
//        }
//
//        model.put("votes", voteds);
    }

    @RequestMapping
    public void pic(Model model) {

    }

    @RequestMapping
    public ActionResult list(VoteQuery query) {

        SeUser user = UserSession.getUser();
        if (user == null) {
            return ActionResult.error("加载失败");
        }
        Integer deptid = user.getDeptid();
        query.setStatus(1);
        query.setUserid(user.getUserid());
        query.setVoteType(1);
//        query.setDeptid(deptid);
        Integer count = voteService.countVoted(query);
        if (count == null) count = 0;

        query.setTotalCount(count);
        query.setRowsPerPage(5);
        query.setPageIs(true);

        List<Voted> voteds = voteService.getVoteNoOption(query);

        if (voteds != null && voteds.size() > 0) {
            Date date = new Date();

            for (int i = 0; i < voteds.size(); i++) {
                Voted voted = voteds.get(i);
                Date date1 = voted.getCreateTime();
                Date date2 = voted.getEndTime();
                Date date3 = voted.getStartTime();
                Date date4 = voted.getEffectiveTime();
                voted.setStatus(0);//未开始
                if (date.getTime() > date3.getTime() && date.getTime() < date4.getTime()) {
                    voted.setStatus(1);//投票中
                } else if (date.getTime() > date4.getTime()) {
                    voted.setStatus(2);//过期
                }
                voteds.get(i).setStatus(voted.getStatus());
                Integer num = 0;
                voteds.get(i).setCreateTimeStr(DateUtils.formatDate(voted.getCreateTime()));

                List<VoteOption> voteOptions = voteOptionService.getOptions(voted.getId());

                for (VoteOption option : voteOptions) {
                    num += option.getTicketNum();
                }
                voteds.get(i).setVoteOptions(voteOptions);
                voteds.get(i).setOptionnum(num);
            }
        }

        Map<String, Object> map = new HashMap<>();
        map.put("voteds", voteds);
        map.put("total", query.getPages()); //总页数
        map.put("current", query.getCurrentPage());

        return ActionResult.ok(null, map);
    }


    @RequestMapping
    public ActionResult picList(VoteQuery query) {

        SeUser user = UserSession.getUser();
        if (user == null) {
            return ActionResult.error("加载失败");
        }
        Integer deptid = user.getDeptid();
        query.setStatus(1);
        query.setVoteType(2);
        query.setUserid(user.getUserid());
//        query.setDeptid(deptid);
        Integer count = voteService.countVoted(query);
        if (count == null) count = 0;

        query.setTotalCount(count);
        query.setRowsPerPage(5);
        query.setPageIs(true);

        List<Voted> voteds = voteService.getVoteNoOption(query);

        if (voteds != null && voteds.size() > 0) {
            Date date = new Date();

            for (int i = 0; i < voteds.size(); i++) {
                Voted voted = voteds.get(i);
                Date date1 = voted.getCreateTime();
                Date date2 = voted.getEndTime();
                Date date3 = voted.getStartTime();
                Date date4 = voted.getEffectiveTime();
                voted.setStatus(0);//未开始
                if (date.getTime() > date3.getTime() && date.getTime() < date4.getTime()) {
                    voted.setStatus(1);//投票中
                } else if (date.getTime() > date4.getTime()) {
                    voted.setStatus(2);//过期
                }
                voteds.get(i).setStatus(voted.getStatus());
                Integer num = 0;
                voteds.get(i).setCreateTimeStr(DateUtils.formatDate(voted.getCreateTime()));

//                List<VoteOption> voteOptions = voteOptionService.getOptions(voted.getId());
//
//                for (VoteOption option : voteOptions) {
//                    num += option.getTicketNum();
//                }
//                voteds.get(i).setVoteOptions(voteOptions);
//                voteds.get(i).setOptionnum(num);
                List<VotePicOption> votePicOptions = votePicOptionService.getOptions(voted.getId());

                for(VotePicOption votePicOption:votePicOptions){
                    num += votePicOption.getTicketNum();
                }
                voteds.get(i).setVotePicOptions(votePicOptions);
                voteds.get(i).setOptionnum(num);


            }
        }

        Map<String, Object> map = new HashMap<>();
        map.put("voteds", voteds);
        map.put("total", query.getPages()); //总页数
        map.put("current", query.getCurrentPage());

        return ActionResult.ok(null, map);
    }

    //投票
    @RequestMapping
    public ActionResult votes(VotedJoin votedJoin) {
        ActionResult result = null;

        SeUser user = UserSession.getUser();
        if (StrUtils.empty(votedJoin.getUserid())) {
            if (user != null) {
                votedJoin.setUserid(user.getUserid());
            } else {
                return ActionResult.error("登录后才可以投票哦");
            }
        }

        if (votedJoin.getVotedId() == null || votedJoin.getOptionId() == null) {
            return ActionResult.error("投票失败");
        }

        Integer joinId = votedJoinService.addJoin(votedJoin);

        result = ActionResult.ok();
        result.setData(joinId);
        return result;
    }
    
}

