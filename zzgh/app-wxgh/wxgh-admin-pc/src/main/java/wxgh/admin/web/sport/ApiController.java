package wxgh.admin.web.sport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import pub.spring.web.mvc.ActionResult;
import wxgh.app.sys.schedule.JobGroup;
import wxgh.app.sys.schedule.ScheduleManager;
import wxgh.app.sys.schedule.job.sport.SportWeekJob;
import wxgh.data.entertain.sport.act.SportActList;
import wxgh.entity.entertain.sport.SportAct;
import wxgh.entity.pub.ScheduleJob;
import wxgh.param.entertain.sport.SportActParam;
import wxgh.sys.service.weixin.entertain.sport.SportActService;
import wxgh.sys.service.weixin.pub.ScheduleJobService;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * author：xlkai
 * date：2018/1/8
 * time：8:49
 * version：V1.0
 * Description：
 */
@Controller
public class ApiController {

    @RequestMapping
    public ActionResult act_list(SportActParam param) {
        List<SportActList> acts = sportActService.listAct(param);
        return ActionResult.okAdmin(acts, param);
    }

    @RequestMapping
    public ActionResult act_del(String id) {
        sportActService.delete(id);
        return ActionResult.ok();
    }

    @RequestMapping
    public ActionResult act_add(SportAct act) {
        Integer actId = sportActService.add(act);

        ScheduleJob job = new ScheduleJob();
        job.setCron("");
        job.setCreateTime(act.getAddTime());
        job.setJobClass(SportWeekJob.class.getName());
        job.setJobGroup(JobGroup.SPORT_ACT.getType());
        job.setJobName(String.valueOf(actId));
        job.setDescript("【健步活动】" + act.getName());

        scheduleJobService.addJob(job);
        return ActionResult.ok();
    }

    @RequestMapping
    public ActionResult act_bg(Integer id, String bg) {
        sportActService.editBg(id, bg);
        return ActionResult.ok();
    }

    @RequestMapping
    public ActionResult act_start(Integer id) {
        return ActionResult.ok();
    }

    @RequestMapping
    public ActionResult act_end(Integer id) {

        return ActionResult.ok();
    }

    @Autowired
    private SportActService sportActService;

    @Autowired
    private ScheduleJobService scheduleJobService;

    @Autowired
    private ScheduleManager scheduleManager;
}
