package wxgh.wx.web.entertain.act;

import com.weixin.WeixinException;
import com.weixin.utils.ApiList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import pub.spring.web.mvc.ActionResult;
import pub.spring.web.mvc.model.Model;
import wxgh.app.utils.WeixinUtils;
import wxgh.data.entertain.act.SignList;
import wxgh.entity.union.group.ActSign;
import wxgh.param.entertain.act.SignParam;
import wxgh.sys.service.weixin.entertain.act.SignService;

import java.util.List;

/**
 * Created by Administrator on 2017/8/30.
 */
@Controller
public class SignController {

    /**
     * 活动签到
     *
     * @param model
     * @param id
     */
    @RequestMapping
    public void add(Model model, Integer id) throws WeixinException {
        WeixinUtils.sign(model, ApiList.getImageApi());
    }

    @RequestMapping
    public void list() {

    }

    @RequestMapping
    public ActionResult add_api(ActSign sign) {
        signService.sign(sign);
        return ActionResult.ok();
    }

    @RequestMapping
    public ActionResult list_api(SignParam param) {
        param.setPageIs(true);

        List<SignList> signs = signService.listSign(param);
        return ActionResult.okRefresh(signs, param);
    }

    @Autowired
    private SignService signService;
}
