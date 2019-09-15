package wxgh.wx.web.entertain.act;

import com.weixin.WeixinException;
import com.weixin.utils.ApiList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import pub.spring.web.mvc.model.Model;
import wxgh.app.utils.WeixinUtils;
import wxgh.data.entertain.act.ActInfo;
import wxgh.sys.service.weixin.entertain.act.ActService;
import wxgh.sys.service.weixin.pub.UserService;

/**
 * Created by Administrator on 2017/7/18.
 */
@Controller
public class MainController {

    @RequestMapping
    public void add(Model model) {

    }

    @RequestMapping
    public void show(Model model, Integer id) throws WeixinException {
        ActInfo actInfo = actService.getActInfo(id);

        model.put("a", actInfo);

        /*WeixinUtils.sign(model, ApiList.getShareApi());*/
    }

    @Autowired
    private ActService actService;


}
