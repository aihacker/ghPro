package wxgh.app.web.test;

import com.libs.common.data.DateUtils;
import com.libs.common.type.TypeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import pub.dao.jdbc.PubDao;
import pub.functions.DateFuncs;
import pub.spring.web.mvc.ActionResult;
import wxgh.app.sys.api.sport.SportApi;
import wxgh.app.sys.schedule.ScheduleManager;
import wxgh.app.sys.task.WeixinPush;
import wxgh.data.entertain.sport.push.PushList;
import wxgh.entity.pub.ScheduleJob;
import wxgh.sys.service.weixin.entertain.sport.SportPushService;
import wxgh.sys.service.weixin.pub.ScheduleJobService;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * author：xlkai
 * date：2018/1/5
 * time：10:53
 * version：V1.0
 * Description：
 */
@Controller
public class SportController {

    @Autowired
    private ScheduleJobService scheduleJobService;

    @Autowired
    private ScheduleManager scheduleManager;

    @Autowired
    private SportApi sportApi;

    public static Boolean start = false;

    @RequestMapping
    public ActionResult start() {
        if (!start) {
            List<ScheduleJob> jobs = scheduleJobService.listJobs(ScheduleJob.STATUS_NORMAL);
            for (ScheduleJob job : jobs) {
                scheduleManager.addJob(job);
            }
            start = true;
        }
        return ActionResult.ok();
    }

    @RequestMapping
    public ActionResult week(int dateid, int deptid, int actid) {
        int end = DateFuncs.addDay(dateid, -2);
        int start = DateFuncs.addDay(end, -6);
        sportApi.week(start, end, deptid, actid, dateid);

        return ActionResult.ok();
    }

    @RequestMapping
    public ActionResult month(int dateid, int deptid, int actid) {
        int end = DateFuncs.addDay(dateid, -2);
        int start = DateUtils.getFirstMonthDayInt(DateFuncs.fromIntDate(end));
        sportApi.month(start, end, deptid, actid, dateid);
        return ActionResult.ok();
    }


    @RequestMapping
    public ActionResult list(Integer dateid) {
        if (dateid == null) {
            dateid = DateFuncs.getIntToday();
        }

        List<PushList> users = sportPushService.listPush(dateid);
        return ActionResult.ok(null, users);
    }

    @RequestMapping
    public ActionResult push(String userid) {
        PushList pushList = new PushList();
        pushList.setUserid(TypeUtils.empty(userid) ? "18402028708" : userid);
        pushList.setName("测试");
        pushList.setStepCount(6000);

        weixinPush.sportToday(pushList, DateFuncs.getIntToday());
        return ActionResult.ok();
    }

    @RequestMapping
    public ActionResult reset(int start, int count) {
        for (int i = 0; i < count; i++) {

        }
        return ActionResult.ok();
    }

    @Autowired
    private SportPushService sportPushService;

    @Autowired
    private WeixinPush weixinPush;

    @Autowired
    private PubDao pubDao;
}
