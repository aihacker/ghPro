package wxgh.wx.web.common.vote;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import pub.spring.web.mvc.ActionResult;
import wxgh.entity.common.vote.VoteOption;
import wxgh.entity.common.vote.Voted;
import wxgh.sys.service.weixin.common.vote.VoteOptionService;
import wxgh.sys.service.weixin.common.vote.VoteService;

import java.util.Date;

/**
 *----------------------------------------------------------
 * @Description 
 *----------------------------------------------------------
 * @Author  Ape
 *----------------------------------------------------------
 * @Email <16511660@qq.com>
 *----------------------------------------------------------
 * @Date 2017-07-29 15:37
 *----------------------------------------------------------
 */
@Controller
public class SaveController {

    @Autowired
    private VoteService voteService;
    @Autowired
    private VoteOptionService voteOptionService;

    private Integer id;

    @RequestMapping
    public ActionResult index() {
        ActionResult result = ActionResult.ok();
        return result;
    }

    /*添加主题*/
    @RequestMapping
    public void AddVoted(Voted voted){
        long current = System.currentTimeMillis();//获取当前时间，单位是毫秒
        current += 60*60*1000*24;//在当前时间加一天
        Date date = new Date(current);//格式化时间
        voted.setCreateTime(new Date());
        voted.setEndTime(date);
        voted.setStatus(1);
        voted.setType(1);
        voted.setIsdel(1);
        voteService.AddVoted(voted);//添加主题
        id = voted.getId();

    }

    /*添加选项*/
    @RequestMapping
    public void AddVoteOption(VoteOption voteOption) {
        voteOption.setVoteid(id);
        voteOption.setCreateTime(new Date());
        voteOptionService.AddVotedOption(voteOption);//添加选项
    }
    
}

