package wxgh.wx.admin.web.common.disease;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import pub.spring.web.mvc.ActionResult;
import pub.spring.web.mvc.model.Model;
import pub.utils.PathUtils;
import wxgh.app.consts.Status;
import wxgh.app.session.bean.SeUser;
import wxgh.app.session.user.UserSession;
import wxgh.app.sys.task.WeixinPush;
import wxgh.data.pub.push.ReplyPush;
import wxgh.entity.common.disease.ApplyAccessory;
import wxgh.entity.common.disease.DiseaseApply;
import wxgh.entity.common.disease.DiseaseJb;
import wxgh.entity.common.disease.DiseaseJy;
import wxgh.entity.common.disease.DiseasePk;
import wxgh.entity.common.disease.DiseaseQs;
import wxgh.entity.common.disease.DiseaseZc;
import wxgh.entity.common.disease.DiseaseZh;
import wxgh.entity.common.disease.DiseaseZx;
import wxgh.param.common.disease.AccessoryQuery;
import wxgh.param.common.disease.ApplyQuery;
import wxgh.param.common.disease.QueryDisease;
import wxgh.sys.service.weixin.common.disease.AccessoryService;
import wxgh.sys.service.weixin.common.disease.DiseaseJbService;
import wxgh.sys.service.weixin.common.disease.DiseaseJyService;
import wxgh.sys.service.weixin.common.disease.DiseasePkService;
import wxgh.sys.service.weixin.common.disease.DiseaseQsService;
import wxgh.sys.service.weixin.common.disease.DiseaseService;
import wxgh.sys.service.weixin.common.disease.DiseaseZcService;
import wxgh.sys.service.weixin.common.disease.DiseaseZhService;
import wxgh.sys.service.weixin.common.disease.DiseaseZxService;
import wxgh.sys.service.weixin.pub.UserInfoService;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by ✔ on 2016/10/21.
 */
@Controller
public class MainController {

    @Autowired
    private UserInfoService userInfoService;


    @Autowired
    private DiseaseJbService diseaseJBService;

    @Autowired
    private DiseaseJyService diseaseJYService;

    @Autowired
    private AccessoryService accessoryService;

    @Autowired
    private DiseaseQsService diseaseQSService;

    @Autowired
    private DiseaseZcService diseaseZCService;

    @Autowired
    private DiseaseZhService diseaseZHService;

    @Autowired
    private DiseaseZxService diseaseZXService;

    @Autowired
    private DiseaseService diseaseService;

    @Autowired
    private DiseasePkService diseasePkService;

    @Autowired
    private WeixinPush weixinPush;

    @RequestMapping
    public void index(){

    }

    @RequestMapping
    public void main(Model model) {

    }

    @RequestMapping
    public ActionResult applyListRefresh(ApplyQuery query) {
        ActionResult actionResult = ActionResult.ok();
        SeUser user = UserSession.getUser();
        //List<Integer> deptIds = userInfoService.getTestUserDeptIds(null);

        List<DiseaseApply> diseaseApplies = diseaseService.applyListRefresh(query);
        actionResult.setData(diseaseApplies);
/*
        if (!UserUtils.isInList(deptIds, user.getDeptid())) {
            List<DiseaseApply> diseaseApplies1 = new ArrayList<>();
            for (DiseaseApply diseaseApply : diseaseApplies) {
                if (!UserUtils.isInList(deptIds, diseaseApply.getDeptid())) {
                    diseaseApplies1.add(diseaseApply);
                }
            }
            Integer num = query.getNum() == null ? 15 : query.getNum();
            if (diseaseApplies1.size() < num) {
                diseaseApplies1 = diseaseService.getApplysNotInTargetDeptId(query, deptIds);
            }
            actionResult.setData(diseaseApplies1);
        }*/
        return actionResult;
    }

    @RequestMapping
    public ActionResult applyListMore(ApplyQuery query) {
        ActionResult actionResult = ActionResult.ok();
        SeUser user = UserSession.getUser();
        //List<Integer> deptIds = userInfoService.getTestUserDeptIds(null);

        List<DiseaseApply> diseaseApplies = new ArrayList<>();
        if (1 == query.getIsFirst()) {
            diseaseApplies = diseaseService.applyListRefresh(query);
        } else if (0 == query.getIsFirst()) {
            diseaseApplies = diseaseService.applyListMore(query);
        }
        actionResult.setData(diseaseApplies);
/*
        if (!UserUtils.isInList(deptIds, user.getDeptid())) {
            List<DiseaseApply> diseaseApplies1 = new ArrayList<>();
            for (DiseaseApply diseaseApply : diseaseApplies) {
                if (!UserUtils.isInList(deptIds, diseaseApply.getDeptid())) {
                    diseaseApplies1.add(diseaseApply);
                }
            }
            Integer num = query.getNum() == null ? 15 : query.getNum();
            if (diseaseApplies1.size() < num) {
                diseaseApplies1 = diseaseService.getApplysNotInTargetDeptId(query, deptIds);
            }
            actionResult.setData(diseaseApplies1);
        }*/

        return actionResult;
    }

    @RequestMapping
    public ActionResult del(Integer id) {
        ActionResult actionResult = ActionResult.ok();
        Integer del = diseaseService.del(id);
        if (1 == del) {
            actionResult.setMsg("success");
        } else {
            actionResult.setMsg("fail");
        }
        return actionResult;
    }

    @RequestMapping
    public ActionResult shenhe(DiseaseApply diseaseApply) {
        ActionResult actionResult = ActionResult.ok();
        SeUser user = UserSession.getUser();
        diseaseApply.setAuditAdmin(user.getUserid());
        diseaseApply.setAuditTime(new Date());
        Integer apply = diseaseService.updateApply(diseaseApply);
        if (1 == apply) {
            actionResult.setMsg("success");
        } else {
            actionResult.setMsg("fail");
        }

        //回复消息，通知用户审核已通过
        ReplyPush push = new ReplyPush(diseaseService.getUserid(diseaseApply.getId()), apply == 1 ? Status.NORMAL.getStatus() : Status.FAILED.getStatus());
        push.setMsg("资助申请审核结果");
        push.setAgentId(1000005);
        weixinPush.reply(push);

        return actionResult;
    }

    @RequestMapping
    public void detail(Model model, QueryDisease queryDisease) {
        String type = queryDisease.getType();
        AccessoryQuery accessoryQuery = new AccessoryQuery();
        if (type.equals("getJB")) {
            model.put("type", "jb");
            ApplyQuery applyQuery = new ApplyQuery();
            applyQuery.setId(queryDisease.getApplyId());
            DiseaseApply diseaseApply = diseaseService.getApply(applyQuery);
            model.put("info", diseaseApply);
            DiseaseJb diseaseJb = new DiseaseJb();
            diseaseJb = diseaseJBService.getJB(queryDisease);
            model.put("jb", diseaseJb);

            accessoryQuery.setJbId(diseaseJb.getId());
            accessoryQuery.setApplyType("jb");
            model.put("thisStatus", diseaseApply.getStatus());
        } else if (type.equals("getJY")) {
            model.put("type", "jy");
            ApplyQuery applyQuery = new ApplyQuery();
            applyQuery.setId(queryDisease.getApplyId());
            DiseaseApply diseaseApply = diseaseService.getApply(applyQuery);
            model.put("info", diseaseApply);
            List<DiseaseJy> diseaseJys = new ArrayList<DiseaseJy>();
            diseaseJys = diseaseJYService.getJYs(queryDisease);
            model.put("jy", diseaseJys);
            model.put("thisStatus", diseaseApply.getStatus());
        } else if (type.equals("getQS")) {
            model.put("type", "qs");
            ApplyQuery applyQuery = new ApplyQuery();
            applyQuery.setId(queryDisease.getApplyId());
            DiseaseApply diseaseApply = diseaseService.getApply(applyQuery);
            model.put("info", diseaseApply);
            DiseaseQs diseaseQs = new DiseaseQs();
            diseaseQs = diseaseQSService.getQS(queryDisease);
            model.put("qs", diseaseQs);

            accessoryQuery.setJbId(diseaseQs.getId());
            accessoryQuery.setApplyType("qs");
            model.put("thisStatus", diseaseApply.getStatus());
        } else if (type.equals("getZC")) {
            model.put("type", "zc");
            ApplyQuery applyQuery = new ApplyQuery();
            applyQuery.setId(queryDisease.getApplyId());
            DiseaseApply diseaseApply = diseaseService.getApply(applyQuery);
            model.put("info", diseaseApply);
            DiseaseZc diseaseZc = new DiseaseZc();
            diseaseZc = diseaseZCService.getZC(queryDisease);
            model.put("zc", diseaseZc);

            accessoryQuery.setJbId(diseaseZc.getId());
            accessoryQuery.setApplyType("zc");
            model.put("thisStatus", diseaseApply.getStatus());
        } else if (type.equals("getZH")) {
            model.put("type", "zh");
            ApplyQuery applyQuery = new ApplyQuery();
            applyQuery.setId(queryDisease.getApplyId());
            DiseaseApply diseaseApply = diseaseService.getApply(applyQuery);
            model.put("info", diseaseApply);
            DiseaseZh diseaseZh = new DiseaseZh();
            diseaseZh = diseaseZHService.getZH(queryDisease);
            model.put("zh", diseaseZh);

            accessoryQuery.setJbId(diseaseZh.getId());
            accessoryQuery.setApplyType("zh");
            model.put("thisStatus", diseaseApply.getStatus());
        } else if (type.equals("getZX")) {
            model.put("type", "zx");
            ApplyQuery applyQuery = new ApplyQuery();
            applyQuery.setId(queryDisease.getApplyId());
            DiseaseApply diseaseApply = diseaseService.getApply(applyQuery);
            model.put("info", diseaseApply);
            DiseaseZx diseaseZx = new DiseaseZx();
            diseaseZx = diseaseZXService.getZX(queryDisease);
            model.put("zx", diseaseZx);

            accessoryQuery.setJbId(diseaseZx.getId());
            accessoryQuery.setApplyType("zx");
            model.put("thisStatus", diseaseApply.getStatus());
        } else if (type.equals("getPK")) {
            model.put("type", "pk");
            ApplyQuery applyQuery = new ApplyQuery();
            applyQuery.setId(queryDisease.getApplyId());
            DiseaseApply diseaseApply = diseaseService.getApply(applyQuery);
            model.put("info", diseaseApply);
            DiseasePk diseasePk = new DiseasePk();
            diseasePk = diseasePkService.getPk(queryDisease);
            model.put("pk", diseasePk);

            accessoryQuery.setJbId(diseasePk.getId());
            accessoryQuery.setApplyType("pk");
            model.put("thisStatus", diseaseApply.getStatus());
        }

        if (!type.equals("jy")) {
            List<ApplyAccessory> files = accessoryService.getFiles(accessoryQuery);
            if (files != null && files.size() > 0) {
                for (int i = 0; i < files.size(); i++) {
                    files.get(i).setSavePath(PathUtils.getImagePath(files.get(i).getSavePath()));
                }
            }
            model.put("files", files);
        }

    }

}
