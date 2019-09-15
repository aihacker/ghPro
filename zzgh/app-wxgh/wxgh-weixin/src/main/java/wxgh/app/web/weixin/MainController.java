package wxgh.app.web.weixin;

import com.libs.common.type.TypeUtils;
import com.qq.weixin.mp.aes.AesException;
import com.weixin.WeixinException;
import com.weixin.api.OAuthApi;
import com.weixin.api.UserApi;
import com.weixin.bean.oauth.UserInfo;
import com.weixin.bean.signature.Signature;
import com.weixin.bean.user.User;
import com.weixin.utils.ApiList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import pub.spring.web.mvc.model.Model;
import wxgh.app.session.bean.SeUser;
import wxgh.app.session.user.ParamSession;
import wxgh.app.session.user.UserSession;
import wxgh.app.sys.api.AppCryptApi;
import wxgh.app.sys.task.WeixinAsync;
import wxgh.app.utils.WeixinUtils;
import wxgh.param.pub.TipMsg;
import wxgh.sys.service.weixin.pub.DeptService;
import wxgh.sys.service.weixin.pub.UserService;

/**
 * Created by Administrator on 2017/7/13.
 */
@Controller
public class MainController {

    @RequestMapping
    public String auoth(String code, Model model) throws WeixinException {
        UserInfo userInfo = OAuthApi.getuserinfo(code);
        if (TypeUtils.empty(userInfo.getOpenId())) { //非公司成员
            TipMsg tipMsg = TipMsg.error("系统提示", "对不起，您不是公司成员，请联系管理员");
            return tipMsg.getTipUrl();
        } else { //公司成员
            User user = UserApi.get(userInfo.getUserid());
            if (TypeUtils.empty(user.getDepartment())) {
                model.put("userid", userInfo.getUserid());
                WeixinUtils.sign(model, ApiList.getCloseApi());
            } else {
                UserApi.authsucc(userInfo.getUserid());
                TipMsg tipMsg = TipMsg.success("系统提示", "个人信息验证成功~");
                return tipMsg.getTipUrl();
            }
        }
        return null;
    }

    @RequestMapping
    public String login(String code, String redirect) throws WeixinException {
        System.out.println("什me鬼的code:"+code);
        System.out.println("去你丫的redirect:"+redirect);
        UserInfo userInfo = OAuthApi.getuserinfo(code);
        System.out.println("buzhidao ::"+userInfo.toString());
        if (!TypeUtils.empty(userInfo.getOpenId())) { //非工会成员
            TipMsg tipMsg = TipMsg.success("工会提示", "对不起！您不是工会成员或个人信息未导入企业号，请联系管理员！");
            System.out.println("what the fuck::"+tipMsg.getTipUrl());
            //tipMsg.setUrl("立即申请入会");
//            tipMsg.setUrl(ServletUtils.getHostAddr() + "/pub/user/apply.html?openid=" + userInfo.getOpenId());
            return tipMsg.getTipUrl();
        } else {
            userService.addOrUpdateUser(userInfo.getUserid());
            UserSession.setUser(new SeUser(userService.getUser(userInfo.getUserid())));
        }

        if (!TypeUtils.empty(redirect)) {
            String params = ParamSession.get();
            if (!TypeUtils.empty(params)) {
                redirect += ("?" + params);
                ParamSession.setEmpty();
            }
            System.out.println("要去哪个地址:"+redirect);
            return "redirect:" + redirect;
        }
        return null;
    }

    @RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    public String callback(Signature signature) throws AesException {
        return appCryptApi.verifyURL(signature);
    }

    @RequestMapping(method = RequestMethod.POST)
    @ResponseBody
    public String callback(Signature signature, @RequestBody String xml) throws AesException {
        String xmlStr = appCryptApi.decryptMsg(signature, xml);
        weixinAsync.process_callback(xmlStr);
        return "";
    }

    @Autowired
    private UserService userService;

    @Autowired
    private DeptService deptService;

    @Autowired
    private AppCryptApi appCryptApi;

    @Autowired
    private WeixinAsync weixinAsync;

}
