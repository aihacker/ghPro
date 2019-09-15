package wxgh.wx.web.common.fraternity;

import com.libs.common.data.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import pub.spring.web.mvc.ActionResult;
import pub.spring.web.mvc.model.Model;
import wxgh.app.session.bean.SeUser;
import wxgh.app.session.user.UserSession;
import wxgh.entity.common.UserFamily;
import wxgh.entity.common.fraternity.FraternityApply;
import wxgh.entity.pub.Dept;
import wxgh.entity.pub.UserInfo;
import wxgh.param.common.fraternity.ApplyParam;
import wxgh.param.common.fraternity.FamilyQuery;
import wxgh.param.pub.dept.WxDeptQuery;
import wxgh.sys.service.weixin.common.fraternity.FraternityService;
import wxgh.sys.service.weixin.common.fraternity.UserFamilyService;
import wxgh.sys.service.weixin.pub.DeptService;
import wxgh.sys.service.weixin.pub.UserInfoService;
import wxgh.sys.service.weixin.pub.WeixinDeptService;

import javax.servlet.http.HttpSession;
import java.util.List;

/**
 *----------------------------------------------------------
 * @Description 
 *----------------------------------------------------------
 * @Author  Ape
 *----------------------------------------------------------
 * @Email <16511660@qq.com>
 *----------------------------------------------------------
 * @Date 2017-08-01 10:49
 *----------------------------------------------------------
 */
@Controller
public class MainController {

    @Autowired
    private UserFamilyService userFamilyService;

    @Autowired
    private FraternityService fraternityService;

    @Autowired
    private UserInfoService userInfoService;

    @Autowired
    private WeixinDeptService weixinDeptService;

    @Autowired
    private DeptService deptService;

    @RequestMapping
    public String index(HttpSession session) {
        SeUser user = UserSession.getUser();

        ApplyParam applyQuery = new ApplyParam();
        applyQuery.setUserid(user.getUserid());

        FraternityApply apply = fraternityService.getApply(applyQuery);

        String redirect = "/wx/common/fraternity/";

        if (apply == null) {
            redirect += "apply1.html";
        } else {
            if (apply.getStatus() == 0) {
                if (apply.getStep() < 3) {
                    redirect += ("apply" + (apply.getStep() + 1) + ".html");
                } else {
                    redirect += "status.html";
                }
            } else {
                redirect += "status.html";
            }
        }

        if ("/wx/common/fraternity/".equals(redirect)) {
            redirect += "status.html";
        }

        return "redirect:" + redirect;
    }

    @RequestMapping
    public String status(Model model) {

        SeUser user = UserSession.getUser();

        if (user == null) {
            // TODO 登陆接口
            return "redirect:/wx/login2.html?action=oauth&redirect=/wx/common/fraternity/status.html";
        }

        ApplyParam applyQuery = new ApplyParam();
        applyQuery.setUserid(user.getUserid());

        FraternityApply apply = fraternityService.getApply(applyQuery);

        String msg = "";
        if (null != apply) {

            if (apply.getStatus() == 0 && apply.getStep() == 3) {
                msg = "入会申请已提交，静候管理员审核吧，审核结果我们将第一时间通知您...";
            } else if (apply.getStatus() == 1) {
                msg = "经核实，您的入会申请已经审核成功...";
            } else if (apply.getStatus() == 2) {
                msg = "经核实，您的入会申请不成功，可能原因是：" + apply.getAuditIdea();
            }
        }else {
            msg = "入会申请已提交，静候管理员审核吧，审核结果我们将第一时间通知您...";
        }
        model.put("msg", msg);
        return null;
    }

    @RequestMapping
    public void apply1(Model model) {
        SeUser user = UserSession.getUser();
        if (user != null) {
            UserInfo userInfo = userInfoService.getInfo(user.getUserid());
            if (userInfo != null) {
                if (userInfo.getBirth() != null) {
                    userInfo.setBirthStr(DateUtils.formatDate(userInfo.getBirth(), "yyyy-MM-dd"));
                }
                model.put("userinfo", userInfo);
            }
        }
    }

    @RequestMapping
    public void apply2(Model model) {

        SeUser user = UserSession.getUser();
        if (user != null) {
            UserInfo userInfo = userInfoService.getInfo(user.getUserid());
            if (userInfo != null) {
                if (userInfo.getBirth() != null) {
                    userInfo.setBirthStr(DateUtils.formatDate(userInfo.getBirth(), "yyyy-MM-dd"));
                }
                model.put("userinfo", userInfo);
            }
        }
        //如果用户部门不为空，则获取部门名称即可
        if (user != null && user.getDeptid() != null) {
//            model.put("dept", weixinDeptService.getDeptStr(user.getUserid()));
            model.put("dept", deptService.getDeptNameByUid(user.getUserid()));
        } else {
            WxDeptQuery query = new WxDeptQuery();
            query.setParentid(1);
            model.put("companys", weixinDeptService.getDepts(query));
        }
    }

    @RequestMapping
    public void apply3(Model model) {
        SeUser user = UserSession.getUser();
        if (user != null) {
            List<UserFamily> families = userFamilyService.getFamilys(new FamilyQuery(user.getUserid()));
            model.put("families", families);
        }
    }

    /**
     * 获取微信部门接口
     * @param parentid
     * @return
     */
    @RequestMapping
    public ActionResult get(Integer parentid) {
        WxDeptQuery wxDeptQuery = new WxDeptQuery();
        wxDeptQuery.setParentid(parentid);
        List<Dept> depts = weixinDeptService.getDepts(wxDeptQuery);
        return ActionResult.ok(null, depts);
    }
    
}

