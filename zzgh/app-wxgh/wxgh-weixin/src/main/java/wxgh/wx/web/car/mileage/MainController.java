package wxgh.wx.web.car.mileage;

import com.weixin.WeixinException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import pub.spring.web.mvc.model.Model;
import wxgh.app.utils.WeixinUtils;

@Controller
public class MainController {

    /**
     * 上传页面
     */
    @RequestMapping
    public void add(Model model) throws WeixinException {
        WeixinUtils.sign(model);
    }


}
