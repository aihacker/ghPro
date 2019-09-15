package wxgh.wx.web.common.disease;


import com.weixin.Agent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import pub.spring.web.mvc.ActionResult;
import pub.utils.StrUtils;
import wxgh.app.session.bean.SeUser;
import wxgh.app.session.user.UserSession;
import wxgh.app.sys.api.FileApi;
import wxgh.app.sys.task.PushAsync;
import wxgh.app.sys.task.WeixinPush;
import wxgh.data.pub.push.ApplyPush;
import wxgh.entity.common.disease.ApplyAccessory;
import wxgh.entity.common.disease.DiseaseApply;
import wxgh.entity.common.disease.DiseaseJb;
import wxgh.entity.pub.SysFile;
import wxgh.sys.service.weixin.common.disease.AccessoryService;
import wxgh.sys.service.weixin.common.disease.DiseaseJbService;
import wxgh.sys.service.weixin.common.disease.DiseaseService;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 疾病资助
 * Created by XDLK on 2016/9/1.
 * <p>
 * Date： 2016/9/1
 */
@Controller
public class ApplyjbController {

    public static final String TYPE = "jb";
    public static final Integer STEP = 2;

    @Autowired
    private AccessoryService accessoryService;

    @Autowired
    private DiseaseJbService diseaseJbService;

    @Autowired
    private DiseaseService diseaseService;

    @Autowired
    private FileApi fileApi;

    @Autowired
    private WeixinPush weixinPush;

    @Autowired
    private PushAsync pushAsync;

    @RequestMapping
    public void index() {

    }

    @RequestMapping
    public ActionResult add(DiseaseJb diseaseJb, @RequestParam("files[]") MultipartFile[] files, HttpServletRequest request) {
        SeUser user = UserSession.getUser();
        if (user == null) {
            return ActionResult.error("请登录后再试哦");
        }

        if (StrUtils.empty(diseaseJb.getHospital())) {
            return ActionResult.error("就诊医院不能为空哦");
        }
        if (diseaseJb.getSeeTime() == null) {
            return ActionResult.error("就诊时间不能为空哦");
        }
//        if (StrUtils.empty(diseaseJb.getCategoryName())) {
//            return ActionResult.error("疾病类别不能为空哦");
//        }

        diseaseJb.setAddTime(new Date());
        Integer jbId = diseaseJbService.addJb(diseaseJb);

        List<ApplyAccessory> applyAccessoryList = new ArrayList<>();
        if (files != null && files.length > 0) {
            for (MultipartFile file : files) {
                if (!StrUtils.empty(file.getOriginalFilename())) {

                    SysFile sysFile = fileApi.addFile(file, null);

                    ApplyAccessory applyAccessory = new ApplyAccessory();
                    applyAccessory.setUserid(user.getUserid());
                    applyAccessory.setApplyType(TYPE);
                    applyAccessory.setFilename(sysFile.getFilename());
                    applyAccessory.setFileSize(sysFile.getSize());
                    applyAccessory.setFileType(sysFile.getMimeType());
                    applyAccessory.setSavePath(sysFile.getFilePath());
                    applyAccessory.setStatus(1);
                    applyAccessory.setUserid(user.getUserid());
                    applyAccessory.setJbId(jbId);

                    applyAccessoryList.add(applyAccessory);
                }
            }
        }

        if (applyAccessoryList.size() > 0) {
            accessoryService.addFiles(applyAccessoryList);
        }

        DiseaseApply diseaseApply = new DiseaseApply();
        diseaseApply.setId(diseaseJb.getApplyId());
        diseaseApply.setStep(STEP);
        diseaseService.updateApply(diseaseApply);

        // TODO 推送消息
        //推送消息
        ApplyPush push = new ApplyPush(ApplyPush.Type.ZIZHU_JB, user.getUserid(), diseaseJb.getApplyId().toString());
        push.setAgentId(Agent.ADMIN.getAgentId());
        push.setMsg("疾病资助申请");
        weixinPush.apply(push);
        return ActionResult.ok();
    }

}
