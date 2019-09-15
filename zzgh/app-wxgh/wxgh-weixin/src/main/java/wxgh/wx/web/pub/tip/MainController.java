package wxgh.wx.web.pub.tip;

import com.weixin.WeixinException;
import com.weixin.utils.ApiList;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import pub.spring.web.mvc.model.Model;
import wxgh.app.utils.WeixinUtils;
import wxgh.param.pub.TipMsg;

/**
 * Created by Administrator on 2017/7/13.
 */
@Controller
public class MainController {

    @RequestMapping
    public void show(Model model, TipMsg tipMsg) throws WeixinException {
        model.put("tip", tipMsg);
        WeixinUtils.sign(model, ApiList.getCloseApi());
    }

}
