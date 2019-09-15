package wxgh.wx.web.pub.apply;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import pub.spring.web.mvc.ActionResult;
import pub.spring.web.mvc.model.Model;
import pub.utils.DateUtils;
import pub.utils.StrUtils;
import wxgh.app.consts.ApplyType;
import wxgh.app.session.bean.SeUser;
import wxgh.app.session.user.UserSession;
import wxgh.app.utils.ComparatorDate;
import wxgh.entity.common.ActApply;
import wxgh.entity.common.disease.DiseaseApply;
import wxgh.entity.common.fraternity.FraternityApply;
import wxgh.entity.four.FourPropagate;
import wxgh.entity.union.group.Group;
import wxgh.entity.union.innovation.WorkInnovate;
import wxgh.param.common.act.ActApplyQuery;
import wxgh.param.common.disease.ApplyQuery;
import wxgh.param.common.fraternity.ApplyParam;
import wxgh.param.four.PropagateParam;
import wxgh.param.pub.apply.ApplyResult;
import wxgh.param.union.innovation.work.WorkInnovateQuery;
import wxgh.sys.service.weixin.common.act.ActApplyService;
import wxgh.sys.service.weixin.common.disease.DiseaseService;
import wxgh.sys.service.weixin.common.fraternity.FraternityService;
import wxgh.sys.service.weixin.four.FourPropagateService;
import wxgh.sys.service.weixin.union.innovation.Group2Service;
import wxgh.sys.service.weixin.union.innovation.WorkInnovateService;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 *----------------------------------------------------------
 * @Description 
 *----------------------------------------------------------
 * @Author  Ape
 *----------------------------------------------------------
 * @Email <16511660@qq.com>
 *----------------------------------------------------------
 * @Date 2017-08-09 16:27
 *----------------------------------------------------------
 */
@Controller
public class MainController {

    @RequestMapping
    public String info(String type, Integer id) {
        String redirect = "/wx/pub/apply/";
        switch (type) {
            case ApplyType.TYPE_ACTIVITIES: //活动申办
                redirect = "act";
                break;
            case ApplyType.TYPE_DISEASE://互助资金申请
                redirect = "disease";
                break;
            case ApplyType.TYPE_FOUR: //四小建设
                redirect = "four";
                break;
            case ApplyType.TYPE_INNOVATE: //创新建议
                redirect = "innovation";
                break;
            case ApplyType.TYPE_GROUP://兴趣协会
                redirect = "group";
                break;
            case ApplyType.TYPE_BIG: //大型活动
                redirect = "bigact";
                break;
        }
//        if (!redirect.equals("/wx/pub/apply/"))
            redirect += ("/index.html?id=" + id);
        return "redirect:" + redirect;
    }

    @RequestMapping
    public ActionResult index(Model model) {
        SeUser user = UserSession.getUser();
        if (user == null) {
            return ActionResult.error("未知用户");
        }

        ApplyParam query = new ApplyParam();
        query.setUserid(user.getUserid());
        List<FraternityApply> fraternityApplies = fraternityService.getApplys(query);
        model.put("fraternity", fraternityApplies.size());

        ActApplyQuery actApplyQuery = new ActApplyQuery();
        actApplyQuery.setUserid(user.getUserid());
        List<ActApply> actApplies = actApplyService.getApplys(actApplyQuery);
        model.put("activities", actApplies.size());

        //大型活动
//        ActivitiesQuery bigquery = new ActivitiesQuery();
//        bigquery.setUserid(user.getUserid());
//        List<Activities> bigacts = actService.getApplys(bigquery);
//        model.put("bigacts", bigacts.size());


        ApplyQuery diseaseQuery = new ApplyQuery();
        diseaseQuery.setUserid(user.getUserid());
        diseaseQuery.setStart(0);
        diseaseQuery.setNum(99);
        List<DiseaseApply> diseaseApplyList = diseaseService.getApplys(diseaseQuery);
        model.put("disease", diseaseApplyList.size());

        PropagateParam propagateQuery = new PropagateParam();
        propagateQuery.setUserid(user.getUserid());
        List<FourPropagate> propagateList = fourPropagateService.getPropagates(propagateQuery);
        model.put("four", propagateList.size());

       /* QueryWorkHonor queryWorkHonor = new QueryWorkHonor();
        queryWorkHonor.setUserId(user.getUserid());
        List<WorkHonor> workHonorList = workHonorService.getWorkHonors(queryWorkHonor);
        model.put("honor", workHonorList.size());*/

        WorkInnovateQuery workInnovateQuery = new WorkInnovateQuery();
        workInnovateQuery.setUserid(user.getUserid());
        List<WorkInnovate> workInnovates = workInnovateService.getInnovates(workInnovateQuery);
        model.put("innovate", workInnovates.size());

        List<Group> groups = group2Service.getApplyGroups(user.getUserid(), "0,2");
        model.put("groups", groups.size());

        return ActionResult.ok();
    }

    @RequestMapping
    public ActionResult get(String type) {

        SeUser user = UserSession.getUser();
        if (user == null) {
            return ActionResult.error("未知用户");
        }

        List<ApplyResult> results = new ArrayList<>();

        switch (type) {
            case ApplyType.TYPE_FRATERNITY:  //互助会入会申请
                ApplyParam query = new ApplyParam();
                query.setUserid(user.getUserid());
                List<FraternityApply> fraternityApplies = fraternityService.getApplys(query);
                if (fraternityApplies != null && fraternityApplies.size() > 0) {
                    ApplyResult result = null;
                    for (FraternityApply apply : fraternityApplies) {
                        result = new ApplyResult();
                        result.setId(apply.getId());
                        result.setTitle("互助会入会申请");
                        result.setUserId(apply.getUserId());

                        if (apply.getStatus() == 0) {
                            result.setStatus("未审核");
                            result.setApplyTime(DateUtils.formatDate(apply.getApplyTime()));
                            if (apply.getStep() == 1) {
                                result.setContent("请补充部门信息后再提交申请哦");
                            } else if (apply.getStep() == 2) {
                                result.setContent("请补充家庭成员后再提交申请哦");
                            } else {
                                result.setContent("您的入会申请信息还未填写完成，请补充完成后，再提交申请...");
                            }
                        } else if (apply.getStatus() == 1) {
                            result.setStatus("已通过");
                            result.setApplyTime(DateUtils.formatDate(apply.getAuditTime()));
                            result.setContent("您的入会申请，经过管理员的耐心审核，已经通过了");
                        } else {
                            result.setStatus("未通过");
                            result.setApplyTime(DateUtils.formatDate(apply.getAuditTime()));
                            String content = "您的入会申请，审核未通过";
                            if (!StrUtils.empty(apply.getAuditIdea())) {
                                content += ("，可能原因是：" + apply.getAuditIdea());
                            }
                            result.setContent(content);
                        }

                        results.add(result);
                    }
                }
                break;
            case ApplyType.TYPE_ACTIVITIES: //活动申办
                ActApplyQuery actApplyQuery = new ActApplyQuery();
                actApplyQuery.setUserid(user.getUserid());
                List<ActApply> actApplies = actApplyService.getApplys(actApplyQuery);
                if (actApplies != null && actApplies.size() > 0) {
                    for (ActApply actApply : actApplies) {
                        ApplyResult applyResult = new ApplyResult();
                        applyResult.setId(actApply.getId());

                        applyResult.setApplyTime(DateUtils.formatDate(actApply.getApplyTime()));

                        applyResult.setUserId(actApply.getUserid());

                        applyResult.setTitle(actApply.getActName() + "活动-经费申请");

                        if (actApply.getStatus() == 0) {
                            applyResult.setStatus("审核中");
                            applyResult.setContent("您的活动申请已提交，静候管理员审核吧...");
                        } else if (actApply.getStatus() == 1) {
                            applyResult.setStatus("已通过");
                            applyResult.setContent("您的活动申请已经通过，审核后费用为：" + actApply.getAuditCost() + "元");

                            applyResult.setApplyTime(DateUtils.formatDate(actApply.getAuditTime()));

                        } else if (actApply.getStatus() == 2) {
                            applyResult.setStatus("未通过");
                            String content = "你的活动申请审核没有通过";
                            if (!StrUtils.empty(actApply.getAuditIdea())) {
                                content += (",可能原因是：" + actApply.getAuditIdea());
                            }
                            applyResult.setContent(content);

                            applyResult.setApplyTime(DateUtils.formatDate(actApply.getAuditTime()));
                        }
                        results.add(applyResult);
                    }
                }
                break;
            //大型活动
            case ApplyType.TYPE_BIG:
//                ActivitiesQuery bigquery = new ActivitiesQuery();
//                bigquery.setUserid(user.getUserid());
//                List<Activities> bigacts = actService.getApplys(bigquery);
//                if (bigacts != null && bigacts.size() > 0) {
//                    for (Activities act : bigacts) {
//                        ApplyResult applyResult = new ApplyResult();
//
//                        applyResult.setId(act.getId());
//                        applyResult.setApplyTime(DateUtils.formatDate(act.getAddTime()));
//                        applyResult.setUserId(act.getUserid());
//                        applyResult.setTitle(act.getName() + "活动-申请");
//
//                        if (act.getStatus() == 0) {
//                            applyResult.setStatus("审核中");
//                            applyResult.setContent("您的活动申请已提交，静候管理员审核吧...");
//                        } else if (act.getStatus() == 1) {
//                            applyResult.setStatus("已通过");
//                            applyResult.setContent("您的活动申请已经通过");
//                            applyResult.setApplyTime(DateUtils.formatDate(act.getAddTime()));
//                        } else if (act.getStatus() == 2) {
//                            applyResult.setStatus("未通过");
//                            String content = "你的活动申请审核没有通过";
//                            if (!StrUtils.empty(act.getAdminApply())) {
//                                content += (",可能原因是：" + act.getAdminApply());
//                            }
//                            applyResult.setContent(content);
//
//                            applyResult.setApplyTime(DateUtils.formatDate(act.getAddTime()));
//                        }
//
//                        results.add(applyResult);
//                    }
//
//                }
                break;

            case ApplyType.TYPE_DISEASE: //互助资金申请
                ApplyQuery diseaseQuery = new ApplyQuery();
                diseaseQuery.setUserid(user.getUserid());
                diseaseQuery.setStart(0);
                diseaseQuery.setNum(99);
                List<DiseaseApply> diseaseApplyList = diseaseService.getApplys(diseaseQuery);
                if (diseaseApplyList != null && diseaseApplyList.size() > 0) {
                    for (DiseaseApply diseaseApply : diseaseApplyList) {
                        ApplyResult applyResult = new ApplyResult();
                        applyResult.setTitle((StrUtils.empty(diseaseApply.getCategory()) ? "互助资金" : diseaseApply.getCategory()) + "申请");
                        applyResult.setUserId(user.getUserid());
                        applyResult.setId(diseaseApply.getId());

                        Date applyTime = null;
                        if (diseaseApply.getStatus() == 0) {
                            applyTime = diseaseApply.getApplyTime();
                            if (diseaseApply.getStep() < 2) {
                                applyResult.setStatus("资料待补全");
                                applyResult.setContent("你的申请资料，还未填写完成，请填写完成后再提交哦");
                            } else {
                                applyResult.setStatus("审核中");
                                applyResult.setContent("你的申请已提交，请静候管理员的审核...");
                            }
                        } else if (diseaseApply.getStatus() == 1 && diseaseApply.getStep() == 2) {
                            applyTime = diseaseApply.getAuditTime();
                            applyResult.setStatus("已通过");
                            applyResult.setContent("你的审核已通过，审核后的金额为：" + diseaseApply.getAuditMoney() + "元");
                        } else if (diseaseApply.getStatus() == 2) {
                            applyTime = diseaseApply.getAuditTime();
                            applyResult.setStatus("未通过");

                            String content = "审核不通过";
                            if (!StrUtils.empty(diseaseApply.getAuditIdea())) {
                                content += (",可能原因是：" + diseaseApply.getAuditIdea());
                            }
                            applyResult.setContent(content);
                        }

                        applyResult.setApplyTime(DateUtils.formatDate(applyTime));

                        results.add(applyResult);
                    }
                }
                break;
            case ApplyType.TYPE_FOUR: //四小需求
                PropagateParam propagateQuery = new PropagateParam();
                propagateQuery.setUserid(user.getUserid());
                List<FourPropagate> propagateList = fourPropagateService.getPropagates(propagateQuery);
                if (propagateList != null && propagateList.size() > 0) {
                    for (FourPropagate fourPropagate : propagateList) {
                        ApplyResult applyResult = new ApplyResult();
                        applyResult.setUserId(user.getUserid());
                        applyResult.setId(fourPropagate.getId());
                        applyResult.setTitle("四小需求申请：" + fourPropagate.getFpName());

                        applyResult.setApplyTime(DateUtils.formatDate(fourPropagate.getApplyTime()));

                        if (fourPropagate.getStatus() == 0) {
                            applyResult.setContent("您的申请已提交，请静候管理员审核...");
                            applyResult.setStatus("审核中");
                        } else if (fourPropagate.getStatus() == 1) {
                            applyResult.setContent("你的申请已经通过");
                            applyResult.setStatus("已通过");
                        } else if (fourPropagate.getStatus() == 2) {
                            applyResult.setContent("您的申请未通过哦");
                            applyResult.setStatus("未通过");
                        }

                        results.add(applyResult);
                    }
                }
                break;
//            case ApplyType.TYPE_HONOR: //荣誉
//                QueryWorkHonor queryWorkHonor = new QueryWorkHonor();
//                queryWorkHonor.setUserId(user.getUserid());
//                List<WorkHonor> workHonorList = workHonorService.getWorkHonors(queryWorkHonor);
//                if (workHonorList != null && workHonorList.size() > 0) {
//                    for (WorkHonor workHonor : workHonorList) {
//                        ApplyResult applyResult = new ApplyResult();
//                        applyResult.setUserId(user.getUserid());
//                        applyResult.setId(workHonor.getId());
//                        applyResult.setTitle("荣誉体系审核：" + workHonor.getName());
//                        applyResult.setApplyTime(DateUtils.formatDate(workHonor.getApplyTime()));
//
//                        if (workHonor.getStatus() == 0) {
//                            applyResult.setContent("您的申请已提交，请静候管理员审核...");
//                            applyResult.setStatus("审核中");
//                        } else if (workHonor.getStatus() == 1) {
//                            applyResult.setContent("你的申请已经通过");
//                            applyResult.setStatus("已通过");
//                        } else if (workHonor.getStatus() == 2) {
//                            applyResult.setContent("您的申请未通过哦");
//                            applyResult.setStatus("未通过");
//                        }
//
//                        results.add(applyResult);
//                    }
//                }
//                break;
            case ApplyType.TYPE_INNOVATE: //创新建议
                WorkInnovateQuery workInnovateQuery = new WorkInnovateQuery();
                workInnovateQuery.setUserid(user.getUserid());
                List<WorkInnovate> workInnovates = workInnovateService.getInnovates(workInnovateQuery);
                if (workInnovates != null && workInnovates.size() > 0) {
                    for (WorkInnovate workInnovate : workInnovates) {
                        ApplyResult applyResult = new ApplyResult();
                        applyResult.setUserId(user.getUserid());
                        applyResult.setId(workInnovate.getId());
                        applyResult.setTitle("创新建议申请：" + workInnovate.getName());

                        applyResult.setApplyTime(DateUtils.formatDate(workInnovate.getApplyTime()));

                        if (workInnovate.getStatus() == 0) {
                            applyResult.setContent("您的申请已提交，请静候管理员审核...");
                            applyResult.setStatus("审核中");
                        } else if (workInnovate.getStatus() == 1) {
                            applyResult.setContent("你的申请已经通过");
                            applyResult.setStatus("已通过");
                        } else if (workInnovate.getStatus() == 2) {
                            applyResult.setContent("您的申请未通过哦");
                            applyResult.setStatus("未通过");
                        }

                        results.add(applyResult);
                    }
                }
                break;
            case ApplyType.TYPE_GROUP: //兴趣协会
                List<Group> groups = group2Service.getApplyGroups(user.getUserid(), "0,2");
                if (groups != null && groups.size() > 0) {
                    for (Group group : groups) {
                        ApplyResult applyResult = new ApplyResult();
                        applyResult.setUserId(user.getUserid());
                        applyResult.setId(group.getId());
                        applyResult.setTitle("兴趣组：" + group.getName());

                        applyResult.setApplyTime(DateUtils.formatDate(group.getAddTime()));

                        if (group.getStatus() == 0) {
                            applyResult.setContent("您的申请已提交，请静候管理员审核...");
                            applyResult.setStatus("审核中");
                        } else if (group.getStatus() == 2) {
                            applyResult.setContent("你的申请未通过哦");
                            applyResult.setStatus("未通过");
                        }
                        results.add(applyResult);
                    }
                }
                break;
        }

        Collections.sort(results, new ComparatorDate());

        return ActionResult.ok(null, results);
    }

    @Autowired
    private FraternityService fraternityService;

    @Autowired
    private ActApplyService actApplyService;

    @Autowired
    private DiseaseService diseaseService;

    @Autowired
    private FourPropagateService fourPropagateService;

    @Autowired
    private WorkInnovateService workInnovateService;

    @Autowired
    private Group2Service group2Service;

}

