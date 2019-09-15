package wxgh.wx.web.union.innovation.declare;


import com.weixin.WeixinException;
import com.weixin.utils.ApiList;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import pub.spring.web.mvc.model.Model;
import wxgh.app.utils.WeixinUtils;

import java.util.List;

/**
 *----------------------------------------------------------
 * @Description 申报创新项目
 *----------------------------------------------------------
 * @Author  Ape<阿佩>
 *----------------------------------------------------------
 * @Email <16511660@qq.com>
 *----------------------------------------------------------
 * @Date 2017-07-19 16:31
 *----------------------------------------------------------
 */
@Controller
public class MainController {

    @RequestMapping
    public void index(Model model){}

    /**
     * 创新建议
     * @param model
     */
    @RequestMapping
    public void advice(Model model){}

    /**
     * 工作坊
     * @param model
     */
    @RequestMapping
    public void work(Model model, Integer id) throws WeixinException {

        List<String> apiList = ApiList.getImageApi();
        apiList.add(ApiList.OPENENTERPRISECONTACT);
        WeixinUtils.sign(model, apiList);

        model.put("applyId", id);
        WeixinUtils.sign_contact(model);



    }

}
