package wxgh.wx.web.common.disease;


import com.weixin.Agent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import pub.spring.web.mvc.ActionResult;
import wxgh.app.session.bean.SeUser;
import wxgh.app.session.user.UserSession;
import wxgh.app.sys.task.WeixinPush;
import wxgh.data.common.disease.DiseaseJyData;
import wxgh.data.pub.push.ApplyPush;
import wxgh.entity.common.disease.DiseaseApply;
import wxgh.entity.common.disease.DiseaseJy;
import wxgh.sys.service.weixin.common.disease.DiseaseJyService;
import wxgh.sys.service.weixin.common.disease.DiseaseService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Date;
import java.util.List;

/**
 * Created by XDLK on 2016/9/2.
 * <p>
 * Date： 2016/9/2
 */
@Controller
public class ApplyjyController {

    public static final String TYPE = "jy";
    public static final Integer STEP = 2;

    @Autowired
    private DiseaseJyService diseaseJyService;

    @Autowired
    private DiseaseService diseaseService;

    @Autowired
    private WeixinPush weixinPush;

    @RequestMapping
    public void index() {

    }

    @RequestMapping
    public ActionResult add(DiseaseJyData diseaseJyData, HttpSession session, HttpServletRequest request) {

        SeUser user = UserSession.getUser();
        if (user == null) {
            return ActionResult.error("你还没有登录呢");
        }

        List<DiseaseJy> diseaseJies = diseaseJyData.getDiseaseJies();

        if (diseaseJies == null || diseaseJies.size() <= 0) {
            return ActionResult.error("申请失败");
        }

        for (int i = 0; i < diseaseJies.size(); i++) {
            diseaseJies.get(i).setUserid(user.getUserid());
            diseaseJies.get(i).setAddTime(new Date());
            diseaseJies.get(i).setApplyId(diseaseJyData.getApplyId());
        }

        diseaseJyService.addJys(diseaseJies);

        DiseaseApply diseaseApply = new DiseaseApply();
        diseaseApply.setId(diseaseJyData.getApplyId());
        diseaseApply.setStep(STEP);
        diseaseService.updateApply(diseaseApply);

        // TODO 推送消息
        //推送消息
        ApplyPush push = new ApplyPush(ApplyPush.Type.ZIZHU_JY, user.getUserid(), diseaseJyData.getApplyId().toString());
        push.setAgentId(Agent.ADMIN.getAgentId());
        push.setMsg("教育资助申请");
        weixinPush.apply(push);

        return ActionResult.ok();
    }

}
