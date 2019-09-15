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
import wxgh.entity.common.disease.DiseaseZh;
import wxgh.entity.pub.SysFile;
import wxgh.sys.service.weixin.common.disease.AccessoryService;
import wxgh.sys.service.weixin.common.disease.DiseaseService;
import wxgh.sys.service.weixin.common.disease.DiseaseZhService;

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
public class ApplyzhController {

    public static final String TYPE = "zh";
    public static final Integer STEP = 2;

    @Autowired
    private DiseaseZhService diseaseZhService;

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
    public ActionResult add(DiseaseZh diseaseZh, @RequestParam("files[]") MultipartFile[] files, HttpServletRequest request) {
        SeUser user = UserSession.getUser();
        if (user == null) {
            return ActionResult.error("请登录后再试哦");
        }

        if (StrUtils.empty(diseaseZh.getInfo())) {
            return ActionResult.error("情况需要简单介绍一下哦");
        }

        if ("0".equals(diseaseZh.getLevel()) || StrUtils.empty(diseaseZh.getLevel())) {
            return ActionResult.error("伤残等级不能为空哦");
        }

        diseaseZh.setAddTime(new Date());
        diseaseZh.setUserid(user.getUserid());
        Integer jbId = diseaseZhService.addZh(diseaseZh);

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
        diseaseApply.setId(diseaseZh.getApplyId());
        diseaseApply.setStep(STEP);
        diseaseService.updateApply(diseaseApply);

        //TODO 推送消息
        //推送消息
        ApplyPush push = new ApplyPush(ApplyPush.Type.ZIZHU_ZH, user.getUserid(), diseaseZh.getApplyId().toString());
        push.setAgentId(Agent.ADMIN.getAgentId());
        push.setMsg("自然灾害资助申请");
        weixinPush.apply(push);

        return ActionResult.ok();
    }

}
