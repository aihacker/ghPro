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
import wxgh.app.sys.task.WeixinPush;
import wxgh.data.pub.push.ApplyPush;
import wxgh.entity.common.disease.ApplyAccessory;
import wxgh.entity.common.disease.DiseaseApply;
import wxgh.entity.common.disease.DiseaseZc;
import wxgh.entity.pub.SysFile;
import wxgh.sys.service.weixin.common.disease.AccessoryService;
import wxgh.sys.service.weixin.common.disease.DiseaseService;
import wxgh.sys.service.weixin.common.disease.DiseaseZcService;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by XDLK on 2016/9/2.
 * <p>
 * Date： 2016/9/2
 */
@Controller
public class ApplyzcController {

    public static final String TYPE = "zc";
    public static final Integer STEP = 2;

    @Autowired
    private DiseaseZcService diseaseZcService;

    @Autowired
    private AccessoryService accessoryService;

    @Autowired
    private DiseaseService diseaseService;

    @Autowired
    private FileApi fileApi;

    @Autowired
    private WeixinPush weixinPush;

    @RequestMapping
    public void index() {

    }

    @RequestMapping
    public ActionResult add(DiseaseZc diseaseZc, @RequestParam("files[]") MultipartFile[] files, HttpServletRequest request) {

        SeUser user = UserSession.getUser();
        if (user == null) {
            return ActionResult.error("请登录后再试哦");
        }

        if (StrUtils.empty(diseaseZc.getInfo())) {
            return ActionResult.error("情况需要简单介绍一下哦");
        }

        if ("0".equals(diseaseZc.getLevel()) || StrUtils.empty(diseaseZc.getLevel())) {
            return ActionResult.error("伤残等级不能为空哦");
        }

        diseaseZc.setAddTime(new Date());
        diseaseZc.setUserid(user.getUserid());
        Integer jbId = diseaseZcService.addZc(diseaseZc);

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
        diseaseApply.setId(diseaseZc.getApplyId());
        diseaseApply.setStep(STEP);
        diseaseService.updateApply(diseaseApply);

        //TODO 推送消息
        ApplyPush push = new ApplyPush();
        push.setAgentId(Agent.ADMIN.getAgentId());
        push.setMsg("因公致残资助申请");
        weixinPush.apply(push);

        return ActionResult.ok();
    }

}
