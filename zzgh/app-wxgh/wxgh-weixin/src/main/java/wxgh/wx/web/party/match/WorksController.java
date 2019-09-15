package wxgh.wx.web.party.match;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import pub.spring.web.mvc.ActionResult;
import pub.spring.web.mvc.model.Model;
import pub.utils.TypeUtils;
import wxgh.app.session.bean.SeUser;
import wxgh.app.session.user.UserSession;
import wxgh.app.utils.UrlUtils;
import wxgh.entity.party.beauty.WorkFile;
import wxgh.entity.party.match.SheyingMatch;
import wxgh.entity.party.match.SheyingMatchJoin;
import wxgh.entity.party.match.SheyingMatchJoinVote;
import wxgh.param.party.match.JoinQuery;
import wxgh.sys.service.weixin.party.beauty.WorkFileService;
import wxgh.sys.service.weixin.party.match.SheyingMatchJoinService;
import wxgh.sys.service.weixin.party.match.SheyingMatchJoinVoteService;
import wxgh.sys.service.weixin.party.match.SheyingMatchService;

import java.io.UnsupportedEncodingException;
import java.util.Date;
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
 * @Date 2017-08-07 16:04
 *----------------------------------------------------------
 */
@Controller
public class WorksController {

    @Autowired
    private SheyingMatchJoinService sheyingMatchJoinService;

    @Autowired
    private SheyingMatchJoinVoteService sheyingMatchJoinVoteService;

    @Autowired
    private WorkFileService workFileService;

    @Autowired
    private SheyingMatchService sheyingMatchService;

    @RequestMapping
    public void index(Model model, Integer id) throws UnsupportedEncodingException {
        JoinQuery query = new JoinQuery();
        query.setMid(id);
        query.setStatus(1);

        SeUser user = UserSession.getUser();
        System.out.println(user.getUserid());
        query.setCurUserid(user.getUserid());

        Map<Integer, List<WorkFile>> fileMap = workFileService.getMatchFiles(id);

        List<SheyingMatchJoin> joins = sheyingMatchJoinService.getJoins(query);
        if (!TypeUtils.empty(joins)) {
            for (int i = 0; i < joins.size(); i++) {
                List<WorkFile> files = fileMap.getOrDefault(joins.get(i).getId(), null);
                if (!TypeUtils.empty(files)) {
                    for (int j = 0; j < files.size(); j++) {
                        WorkFile tmpFile = files.get(j);
                        files.get(j).setPath(tmpFile.getPath());
                        files.get(j).setThumb(tmpFile.getThumb());
                    }
                }
                joins.get(i).setFiles(files);
                joins.get(i).setRemark(UrlUtils.URLDecode(joins.get(i).getRemark()));
            }
        }

        SheyingMatch match = sheyingMatchService.getMatch(id);

        model.put("joins", joins);
        model.put("match", match);

        Date startTime = match.getStartTime();
        Date endTime = match.getEndTime();
        Date nowTime = new Date();
        int status = 0;//1未开始，2进行中，3已结束
        if (startTime.getTime() > nowTime.getTime()) {
            status = 1;
        } else if (nowTime.getTime() > startTime.getTime() && endTime.getTime() > nowTime.getTime()) {
            status = 2;
        } else if (nowTime.getTime() > endTime.getTime()) {
            status = 3;
        }
        model.addAttribute("matchStatus", status);
    }

    @RequestMapping
    public ActionResult vote(SheyingMatchJoinVote sheyingMatchJoinVote) {
        SeUser user = UserSession.getUser();
        // 2017-12-7 新增 每人每天最多投5票  一个作品最多一票
        // 1 判断当天是否投了5票
        // 2 判断当天是否对当前作品已进行了投票

        if(sheyingMatchJoinService.countVotesByDay(user.getUserid()) >= 5)
            return ActionResult.error("每天最多投5票哦");

        if(!sheyingMatchJoinService.checkVote(user.getUserid(), sheyingMatchJoinVote.getJoinId()))
            return ActionResult.error("今天你已投过该作品了,明天再来吧。");

        sheyingMatchJoinVote.setUserid(user.getUserid());
        sheyingMatchJoinVote.setAddTime(new Date());
        sheyingMatchJoinVoteService.addData(sheyingMatchJoinVote);
        return ActionResult.ok();
    }
    
}

