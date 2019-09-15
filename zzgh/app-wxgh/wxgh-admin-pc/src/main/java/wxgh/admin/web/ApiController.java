package wxgh.admin.web;

import com.libs.common.type.StringUtils;
import com.libs.common.type.TypeUtils;
import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import pub.error.ValidationError;
import pub.spring.web.mvc.ActionResult;
import pub.spring.web.mvc.model.Model;
import wxgh.admin.session.AdminSession;
import wxgh.admin.session.bean.SeAdmin;
import wxgh.app.utils.CheckPwdComplexity;
import wxgh.data.pcadmin.nav.NavList;
import wxgh.entity.admin.NewAdmin;
import wxgh.sys.service.admin.control.AdminService;
import wxgh.sys.service.admin.control.NavService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * Created by Administrator on 2017/8/9.
 */
@Controller
public class ApiController {

    @RequestMapping
    public ActionResult nav_list(Integer cateId,HttpSession session) {
        String flag = (String)session.getAttribute("failMsg");
        Integer changePwdDate = (Integer) session.getAttribute("changePwdDate");

        //若登录密码状态不符合规则，不显示菜单;如果修改密码时间大于90天，不显示菜单
        if(flag != null && flag == "false" || changePwdDate != null && changePwdDate > 90){return null;}
        else{
            SeAdmin admin = AdminSession.getAdmin();
            List<NavList> navLists = navService.getNavs(cateId, admin.getNavId());
            return ActionResult.okWithData(navLists);
        }
    }

    /**
     * 后台登录
     *
     * @param name
     * @param password
     */
    @RequestMapping
    public String login(String name, String password, String url, Model model, HttpSession session) {
        //尝试进行用户登录，如果用户名或密码错误，抛出异常，回退到登录页面
        CheckPwdComplexity checkPwdComplexity = new CheckPwdComplexity();
        CheckPwdComplexity.Message message = checkPwdComplexity.check("","",password);
//        if (message == null) {
//            // 校验通过
//        } else {
//            // 校验未通过
//            throw new MisException("错误类型：" + message.getType() + ", 提示：" + message.getMsg());
//        }

//        CheckPwdComplexity checkPwdComplexity = new CheckPwdComplexity();
//        CheckPwdComplexity.Message message = checkPwdComplexity.check("","",password);
//        if (message != null) {
//            // 校验通过
//            model.put("info",message.getMsg());
//        }
        try {
            NewAdmin admin = adminService.login(name, password,session);
            AdminSession.setAdmin(new SeAdmin(admin));
        } catch (ValidationError e) {
            String toUrl = "redirect:../login.html";
            session.setAttribute("loginMsg",e.getMessage());
            if (!TypeUtils.empty(url)) {
                toUrl += "?url=" + new String(Base64.decodeBase64(url));
            }
            return toUrl;
        }
        //用户登录成功后，校验密码是否符合规则，不符合则设置session域对象标识
        if (message == null) {
            // 校验通过
        } else {
            // 校验未通过,在session域设置对象标识校验未通过
            session.setAttribute("failMsg","false");
        }

        if (TypeUtils.empty(url)) {

            url = "../index.html";
        } else {
            url = new String(Base64.decodeBase64(url));
        }

        return "redirect:" + url;
    }

    @RequestMapping(produces = {"text/html;charset=UTF-8;","application/json"})
    @ResponseBody
    public String edit_password(String oldpassword, String password,HttpSession session) {

        //校验密码
        CheckPwdComplexity checkPwdComplexity = new CheckPwdComplexity();
        CheckPwdComplexity.Message message = checkPwdComplexity.check("","",password);
        if (message == null) {
            // 校验通过
        } else {
            // 校验未通过
            System.out.println("message:"+message.getMsg());
            return message.getMsg();
        }

        String str = adminService.editPassword(oldpassword, password, AdminSession.getAdmin().getId());
        if(str != null){
            return str;
        }
        session.setAttribute("failMsg","true");
        session.setAttribute("changePwdDate",0);
        session.removeAttribute("changePwdDate");
        session.removeAttribute("failMsg");
        return "修改成功";
    }

    @Autowired
    private NavService navService;

    @Autowired
    private AdminService adminService;

}
