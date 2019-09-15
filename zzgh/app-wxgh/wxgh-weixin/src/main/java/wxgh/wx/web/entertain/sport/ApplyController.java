package wxgh.wx.web.entertain.sport;

import com.libs.common.data.DateUtils;
import com.libs.common.type.TypeUtils;
import com.weixin.WeixinException;
import com.weixin.utils.ApiList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import pub.spring.web.mvc.ActionResult;
import pub.spring.web.mvc.model.Model;
import wxgh.app.callback.SuccessCallBack;
import wxgh.app.sys.api.FileApi;
import wxgh.app.sys.task.WeixinPush;
import wxgh.app.utils.WeixinUtils;
import wxgh.data.pub.push.ApplyPush;
import wxgh.entity.entertain.sport.SportApply;
import wxgh.entity.pub.SysFile;
import wxgh.sys.service.weixin.entertain.sport.SportApplyService;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * author：xlkai
 * date：2018/1/9
 * time：9:50
 * version：V1.0
 * Description：
 */
@Controller
public class ApplyController {

    @RequestMapping
    public void index(Model model) {
        try {
            WeixinUtils.sign(model, ApiList.getImageApi());
        } catch (WeixinException e) {
        }
    }


    @RequestMapping
    public ActionResult add(SportApply apply) {
        //时间判断
        int startWeek = DateUtils.getFirstWeekDayInt(new Date());
        if (apply.getDateid() >= startWeek) {
            return ActionResult.error("暂不能申述，本周积分将在下一周进行结算哦！");
        }

        if (!TypeUtils.empty(apply.getImgs())) {
            String[] imgs = apply.getImgs().split(",");
            List<String> imgList = new ArrayList<>();
            for (String img : imgs) {
                fileApi.wxDownload(img, new SuccessCallBack() {
                    @Override
                    public void onSuccess(SysFile file, File toFile) {
                        imgList.add(file.getFileId());
                    }
                });
            }
            apply.setImgs(TypeUtils.listToStr(imgList));
        }
        Integer id = sportApplyService.add(apply);

        //推送消息给管理员进行审核
        weixinPush.apply(new ApplyPush(ApplyPush.Type.SPORT_APPLY, apply.getUserid(), String.valueOf(id)));

        return ActionResult.ok();
    }

    @Autowired
    private SportApplyService sportApplyService;

    @Autowired
    private FileApi fileApi;

    @Autowired
    private WeixinPush weixinPush;
}
