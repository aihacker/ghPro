package wxgh.wx.web.canteen.act;

import com.libs.common.type.TypeUtils;
import com.weixin.WeixinException;
import com.weixin.utils.ApiList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import pub.spring.web.mvc.ActionResult;
import pub.spring.web.mvc.model.Model;
import wxgh.app.callback.SuccessCallBack;
import wxgh.app.consts.Status;
import wxgh.app.session.bean.SeUser;
import wxgh.app.session.user.UserSession;
import wxgh.app.sys.api.FileApi;
import wxgh.app.sys.task.WeixinPush;
import wxgh.app.utils.WeixinUtils;
import wxgh.data.entertain.act.ActInfo;
import wxgh.entity.canteen.CanteenAct;
import wxgh.entity.entertain.act.Act;
import wxgh.entity.pub.SysFile;
import wxgh.sys.service.weixin.canteen.CanteenActService;
import wxgh.sys.service.weixin.entertain.act.ActService;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * ----------------------------------------------------------
 *
 * @Description ----------------------------------------------------------
 * @Author Ape
 * ----------------------------------------------------------
 * @Email <16511660@qq.com>
 * ----------------------------------------------------------
 * @Date 2017-08-16  09:40
 * ----------------------------------------------------------
 */
@Controller
public class TeamController {

    @RequestMapping
    public void index(Model model) throws WeixinException {
        WeixinUtils.sign(model);
    }

    /**
     * 添加活动
     *
     * @param act
     * @param push
     * @return
     */
    @RequestMapping
    public ActionResult add(CanteenAct act, Integer push) {

        if (act.getGroupId() == null)
            return ActionResult.error("团队为空");

        if (!TypeUtils.empty(act.getImgId())) {
            List<String> mediaList = TypeUtils.strToList(act.getImgId());
            List<String> images = new ArrayList<>();
            for (String media : mediaList)
                fileApi.wxDownload(media, new SuccessCallBack() {
                    @Override
                    public void onSuccess(SysFile file, File toFile) {
                        images.add(file.getFileId());
                    }
                });
            if (images.size() > 0) {
                act.setImgId(images.get(0));       // 单张封面图
                act.setImageIds(TypeUtils.listToStr(images));   // 轮播图
            }
        }

        SeUser user = UserSession.getUser();
        act.setUserid(user.getUserid());
        act.setStatus(Status.NORMAL.getStatus()); //协会活动默认正常状态
        act.setActType(CanteenAct.ACT_TYPE_TEAM); //团队活动
        act.setAllIs(0);

        boolean pushIs = push != null && push.intValue() == 1;

        actService.addAct(act, pushIs);

        /**
         * 推送消息给群组用户
         */
        if (pushIs) {
            //weixinPush.act(act.getActId(), null, true);
            weixinPush.act(act.getActId(),null,true,act.getActType());
        }
        return ActionResult.okWithData(act.getId());
    }

    @RequestMapping
    public void show(Model model, Integer id) throws WeixinException {
        ActInfo actInfo = actService.getActInfo(id);
        model.put("a", actInfo);
        WeixinUtils.sign(model, ApiList.getShareApi());
    }

    @Autowired
    private FileApi fileApi;

    @Autowired
    private CanteenActService actService;

    @Autowired
    private WeixinPush weixinPush;

}
