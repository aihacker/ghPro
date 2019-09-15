package wxgh.wx.web.common.disease;


import com.weixin.Agent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import pub.spring.web.mvc.ActionResult;
import pub.spring.web.mvc.model.Model;
import pub.utils.StrUtils;
import wxgh.app.session.bean.SeUser;
import wxgh.app.session.user.UserSession;
import wxgh.app.sys.api.FileApi;
import wxgh.app.sys.task.WeixinPush;
import wxgh.data.pub.push.ApplyPush;
import wxgh.entity.common.disease.ApplyAccessory;
import wxgh.entity.common.disease.DiseaseApply;
import wxgh.entity.common.disease.DiseasePk;
import wxgh.entity.pub.SysFile;
import wxgh.sys.service.weixin.common.disease.AccessoryService;
import wxgh.sys.service.weixin.common.disease.DiseasePkService;
import wxgh.sys.service.weixin.common.disease.DiseaseService;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2016/10/20.
 */
@Controller
public class ApplypkController {

    public static final String TYPE = "pk";
    public static final Integer STEP = 2;

    @Autowired
    private DiseasePkService diseasePkService;

    @Autowired
    private AccessoryService accessoryService;

    @Autowired
    private DiseaseService diseaseService;

    @Autowired
    private FileApi fileApi;

    @Autowired
    private WeixinPush weixinPush;

    @RequestMapping
    public void index(Model model, HttpServletRequest request) {
//        String request_url = Weixin.getUrl(request);
//        model.put("weixin", weixin.sign(request_url));
    }

    @RequestMapping
    public ActionResult add(DiseasePk diseasePk, @RequestParam("files[]") MultipartFile[] files, HttpServletRequest request) {
        SeUser user = UserSession.getUser();
        if (user == null) {
            return ActionResult.error("请登录后再试哦");
        }

        diseasePk.setAddTime(new Date());
        diseasePk.setUserid(user.getUserid());

        diseasePkService.save(diseasePk);

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
                    applyAccessory.setJbId(diseasePk.getId());

                    applyAccessoryList.add(applyAccessory);
                }
            }
        }

        if (applyAccessoryList.size() > 0) {
            accessoryService.addFiles(applyAccessoryList);
        }

        DiseaseApply diseaseApply = new DiseaseApply();
        diseaseApply.setId(diseasePk.getApplyId());
        diseaseApply.setStep(STEP);
        diseaseService.updateApply(diseaseApply);

        // TODO 推送消息
        //推送消息
        ApplyPush push = new ApplyPush(ApplyPush.Type.ZIZHU_PK, user.getUserid(), diseasePk.getApplyId().toString());
        push.setAgentId(Agent.ADMIN.getAgentId());
        push.setMsg("困难家庭资助申请");
        weixinPush.apply(push);

        return ActionResult.ok();
    }

}
