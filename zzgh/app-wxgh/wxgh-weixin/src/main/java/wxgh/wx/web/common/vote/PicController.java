package wxgh.wx.web.common.vote;

import com.libs.common.type.TypeUtils;
import com.weixin.Agent;
import com.weixin.WeixinException;
import com.weixin.utils.ApiList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import pub.spring.web.mvc.ActionResult;
import pub.spring.web.mvc.model.Model;
import wxgh.app.callback.SuccessCallBack;
import wxgh.app.session.bean.SeUser;
import wxgh.app.session.user.UserSession;
import wxgh.app.sys.api.FileApi;
import wxgh.app.sys.task.WeixinPush;
import wxgh.app.utils.WeixinUtils;
import wxgh.data.pub.push.ApplyPush;
import wxgh.entity.common.vote.VotePicOption;
import wxgh.entity.common.vote.Voted;
import wxgh.entity.pub.SysFile;
import wxgh.sys.service.weixin.common.vote.VotePicOptionService;
import wxgh.sys.service.weixin.common.vote.VoteService;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
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
public class PicController {

    @Autowired
    private VoteService voteService;

    @Autowired
    private VotePicOptionService votePicOptionService;

    @Autowired
    private WeixinPush weixinPush;

    @Autowired
    private FileApi fileApi;

    /**
     * 图片投票页面
     * @param model
     */
    @RequestMapping
    public void index(Model model) throws WeixinException {
        WeixinUtils.sign(model, ApiList.getImageApi());
    }



    /**
     * 发布投票插入数据
     * @param voted
     * @param
     * @param request
     * @return
     */
    @RequestMapping
    public ActionResult add(Voted voted, @RequestParam("options[]") List<String> options, HttpServletRequest request) {

        if (!TypeUtils.empty(voted.getImageIds())) {

        }else {
            return ActionResult.error("投票选项不能为空哦");
        }

        if (options == null || options.size() <= 0) {
            return ActionResult.error("投票选项不能为空哦");
        }


        SeUser user = UserSession.getUser();
        voted.setCreateTime(new Date());
        voted.setStatus(0); //默认不显示
        voted.setType(2);
        voted.setIsdel(1);
        voted.setDeptid(user.getDeptid());

        Integer voteId = voteService.AddVoted(voted);

        List<String> mediaList = TypeUtils.strToList(voted.getImageIds());
        List<String> images = new ArrayList<>();
        for(String media : mediaList)
            //根据图片id从微信服务器下载图片
            fileApi.wxDownload(media, new SuccessCallBack() {
                @Override
                public void onSuccess(SysFile file, File toFile) {
                    images.add(file.getFileId());
                }
            });

        List<VotePicOption> votePicOptionList = new ArrayList<>();

        for(String m:images){

            VotePicOption votePicOption = new VotePicOption();
            votePicOption.setCreateTime(new Date());
            votePicOption.setVoteid(voteId);
            votePicOption.setOptionsFile(m);
            votePicOption.setTicketNum(0);
            votePicOption.setIsdel(1);
            votePicOptionList.add(votePicOption);
        }
        for (int i = 0; i <votePicOptionList.size(); i++) {
            votePicOptionList.get(i).setOptions(options.get(i));
        }
        votePicOptionService.addOptions(votePicOptionList);



        //推送消息
        ApplyPush push = new ApplyPush(ApplyPush.Type.VOTEPIC, user.getUserid(), voteId.toString());
        push.setAgentId(Agent.ADMIN.getAgentId());
        push.setMsg("投票发布申请");
        weixinPush.apply(push);



        return ActionResult.ok();
    }
    
}

