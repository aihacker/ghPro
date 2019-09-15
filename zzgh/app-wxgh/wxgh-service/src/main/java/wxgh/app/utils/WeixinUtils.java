package wxgh.app.utils;

import com.libs.common.json.JsonUtils;
import com.weixin.Weixin;
import com.weixin.WeixinException;
import com.weixin.utils.ApiList;
import pub.spring.web.mvc.model.Model;
import pub.web.ServletUtils;

import java.util.List;

/**
 * ----------------------------------------------------------
 *
 * @Description ----------------------------------------------------------
 * @Author Ape
 * ----------------------------------------------------------
 * @Email <16511660@qq.com>
 * ----------------------------------------------------------
 * @Date 2017-07-19 18:15
 * ----------------------------------------------------------
 */
public class WeixinUtils {

    public static void sign(Model model) throws WeixinException {
        sign(model, ApiList.getImageApi());
    }

    public static void sign(Model model, List<String> apis) throws WeixinException {
        sign(model, apis, null);
    }

    public static void sign(Model model, List<String> apis, List<String> hideMenus) throws WeixinException {
        String json = JsonUtils.stringfy(Weixin.sign(ServletUtils.getSelf(), apis, hideMenus));
        model.put("weixin", json);
    }

    public static void sign_contact(Model model) throws WeixinException {
        String json = JsonUtils.stringfy(Weixin.sign_contact(ServletUtils.getSelf()));
        model.put("wx_contact", json);
    }

}
