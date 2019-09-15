package wxgh.wx.web.common.disease;


import com.weixin.WeixinException;
import com.weixin.utils.ApiList;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import pub.spring.web.mvc.model.Model;
import wxgh.app.utils.WeixinUtils;

/**
 * Created by XDLK on 2016/9/2.
 * <p>
 * Date： 2016/9/2
 */
@Controller
public class SuccessController {

    @RequestMapping
    public void index(Model model) throws WeixinException {

        WeixinUtils.sign(model, ApiList.getCloseApi());

    }

}
