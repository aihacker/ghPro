package wxgh.wx.web.common.disease;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import pub.spring.web.mvc.ActionResult;
import pub.spring.web.mvc.model.Model;
import pub.utils.PathUtils;
import pub.utils.StrUtils;
import wxgh.app.session.bean.SeUser;
import wxgh.app.session.user.UserSession;
import wxgh.entity.common.disease.DiseaseApply;
import wxgh.sys.service.weixin.common.disease.DiseaseService;
import wxgh.sys.service.weixin.pub.UserInfoService;

import java.util.Date;

/**
 *----------------------------------------------------------
 * @Description 
 *----------------------------------------------------------
 * @Author  Ape
 *----------------------------------------------------------
 * @Email <16511660@qq.com>
 *----------------------------------------------------------
 * @Date 2017-08-01 15:31
 *----------------------------------------------------------
 */
@Controller
public class MainController {

    public static final Integer STEP = 1;

    @Autowired
    private UserInfoService userInfoService;

    @Autowired
    private DiseaseService diseaseService;


    @RequestMapping
    public void index( Model model) {
        SeUser user = UserSession.getUser();
        if (user == null) {
            model.put("msg", "你还没有登录哦");
            return;
        }

//        UserInfo userInfo = userInfoService.getInfo(user.getUserid());
//        model.put("user", userInfo);
    }

    @RequestMapping
    public ActionResult add(DiseaseApply apply) {

        SeUser user = UserSession.getUser();
        if (user == null) {
            return ActionResult.error("你还没有登录哦");
        }

        if (StrUtils.empty(apply.getCateId()) || "0".equals(apply.getCateId())) {
            return ActionResult.error("申请资助金分类不能为空哦");
        }

        if (apply.getMobile().length() != 11) {
            return ActionResult.error("请填写11个数字哦~");
        }

        String redirect = PathUtils.getHostAddr() + "/wx/common/disease/apply";
        if ("jb".equals(apply.getCateId())) {
            redirect += ApplyjbController.TYPE;
            apply.setCategory("疾病资助");
        } else if ("jy".equals(apply.getCateId())) {
            redirect += ApplyjyController.TYPE;
            apply.setCategory("教育资助");
        } else if ("zc".equals(apply.getCateId())) {
            redirect += ApplyzcController.TYPE;
            apply.setCategory("因公致残资助");
        } else if ("qs".equals(apply.getCateId())) {
            redirect += ApplyqsController.TYPE;
            apply.setCategory("去世资助");
        } else if ("zh".equals(apply.getCateId())) {
            redirect += ApplyzhController.TYPE;
            apply.setCategory("自然灾害资助");
        } else if ("zx".equals(apply.getCateId())) {
            redirect += ApplyzxController.TYPE;
            apply.setCategory("直系亲属去世慰问");
        } else if ("pk".equals(apply.getCateId())) {
            redirect += ApplypkController.TYPE;
            apply.setCategory("困难家庭资助");
        }

        apply.setApplyTime(new Date());
        apply.setUserid(user.getUserid());
        apply.setStatus(0);
        apply.setStep(STEP);
        Integer applyId = diseaseService.addApply(apply);

        redirect += ("/index.html?id=" + applyId);

        return ActionResult.ok(null, redirect);
    }
    
}

