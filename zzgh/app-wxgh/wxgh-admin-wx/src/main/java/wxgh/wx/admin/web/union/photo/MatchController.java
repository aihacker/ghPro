package wxgh.wx.admin.web.union.photo;


import com.weixin.WeixinException;
import com.weixin.utils.ApiList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import pub.spring.web.mvc.ActionResult;
import pub.spring.web.mvc.model.Model;
import wxgh.app.callback.SuccessCallBack;
import wxgh.app.session.user.UserSession;
import wxgh.app.sys.api.FileApi;
import wxgh.app.utils.WeixinUtils;
import wxgh.entity.party.match.SheyingMatch;
import wxgh.entity.pub.SysFile;
import wxgh.sys.service.weixin.party.match.SheyingMatchService;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.util.Date;

/**
 * @Version 1.0
 * --------------------------------------------------------- *
 * @Description >_
 * --------------------------------------------------------- *
 * @Author Ape
 * --------------------------------------------------------- *
 * @Email <16511660@qq.com>
 * --------------------------------------------------------- *
 * @Date 2017-12-07  10:16
 * --------------------------------------------------------- *
 */
@Controller
public class MatchController {

    @Autowired
    private FileApi fileApi;

    @Autowired
    private SheyingMatchService sheyingMatchService;

    @RequestMapping
    public void index(Model model) throws WeixinException {
        model.addAttribute("user", UserSession.getUser());
        WeixinUtils.sign(model, ApiList.getCloseApi());
        WeixinUtils.sign(model, ApiList.getImageApi());
    }

    @RequestMapping
    public ActionResult add(SheyingMatch sheyingMatch) throws UnsupportedEncodingException {
        // 只上传一张封面图片
        if(sheyingMatch.getCover() != null)
            fileApi.wxDownload(sheyingMatch.getCover(), new SuccessCallBack() {
                @Override
                public void onSuccess(SysFile file, File toFile) {
                    sheyingMatch.setCover(file.getFileId());
                }
            });
        // 全民投票
        sheyingMatch.setType(1);
        sheyingMatch.setAddTime(new Date());
        sheyingMatchService.addData(sheyingMatch);
        return ActionResult.ok();
    }

}
