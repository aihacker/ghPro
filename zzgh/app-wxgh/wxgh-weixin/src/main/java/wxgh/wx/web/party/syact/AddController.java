package wxgh.wx.web.party.syact;


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
import wxgh.entity.party.match.SheyingAct;
import wxgh.entity.pub.SysFile;
import wxgh.sys.service.weixin.party.syact.SheyingActService;
import wxgh.sys.service.weixin.union.innovation.User2Service;

import java.io.File;
import java.io.UnsupportedEncodingException;

/**
 * Created by ✔ on 2017/4/20.
 */
@Controller
public class AddController {

    @Autowired
    private SheyingActService sheyingActService;

    @Autowired
    private User2Service user2Service;

    @Autowired
    private FileApi fileApi;

    @RequestMapping
    public void index(Model model) throws WeixinException {
        model.addAttribute("user", UserSession.getUser());
        WeixinUtils.sign(model, ApiList.getCloseApi());
        WeixinUtils.sign(model, ApiList.getImageApi());
    }

    @RequestMapping
    public ActionResult addData(SheyingAct sheyingAct) throws UnsupportedEncodingException {
//        String[] imgs = sheyingAct.getCover().split(",");
//        for (String mediaId : imgs) {

//                File file = PathUtils.getUpload(PathUtils.PATH_MATERIA, "", mediaId, false);
//                downWxImgTask.asyncDownloadImage(mediaId, file.getAbsolutePath());
//        }
        //sheyingAct.setContent(URLEncoder.encode(sheyingAct.getContent(), "UTF-8"));
        //sheyingAct.setRemark(URLEncoder.encode(sheyingAct.getRemark(), "UTF-8"));

        // 只上传一张封面图片
        if(sheyingAct.getCover() != null)
        fileApi.wxDownload(sheyingAct.getCover(), new SuccessCallBack() {
            @Override
            public void onSuccess(SysFile file, File toFile) {
                sheyingAct.setCover(file.getFileId());
            }
        });

        sheyingActService.addData(sheyingAct);
        return ActionResult.ok();
    }

    @RequestMapping
    public ActionResult delData(Integer id){
        sheyingActService.delData(id);
        return ActionResult.ok();
    }

    @RequestMapping
    public ActionResult search(String key) {
        return ActionResult.ok(null, user2Service.searchUser(key));
    }

}
