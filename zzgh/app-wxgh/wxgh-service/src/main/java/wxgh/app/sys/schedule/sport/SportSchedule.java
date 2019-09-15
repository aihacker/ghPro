package wxgh.app.sys.schedule.sport;

import com.libs.common.type.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import wxgh.app.sys.schedule.JobGroup;
import wxgh.app.sys.schedule.ScheduleManager;
import wxgh.app.sys.schedule.job.sport.SportMonthJob;
import wxgh.app.sys.schedule.job.sport.SportWeekJob;
import wxgh.entity.pub.ScheduleJob;
import wxgh.sys.service.weixin.pub.ScheduleJobService;

import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * author：xlkai
 * date：2018/1/8
 * time：16:50
 * version：V1.0
 * Description：
 */
@Component
public class SportSchedule {

    public void week(Integer actId, String des) {
        job(actId + "_week", des, SportWeekJob.class,
                "0 5 0 ? 7-12 2 2018-2018", "每周2凌晨0点5分");
    }

    public void month(Integer actId, String des) {
        job(actId + "_month", des, SportMonthJob.class,
                "0 5 0 2 7-12 ? 2018-2018", "每月2号凌晨0点5分");
    }

    private ScheduleJob job(String jobName, String des, Class clazz, String cron, String exeTime) {
        ScheduleJob job = new ScheduleJob();
        job.setJobName(jobName);
        job.setJobClass(clazz.getName());
        job.setDescript(des);
        job.setStatus(ScheduleJob.STATUS_NORMAL);
        job.setJobId(StringUtils.uuid());
        job.setJobGroup(JobGroup.SPORT_ACT.getType());
        job.setCreateTime(new Date());
        job.setExecuteTime(exeTime);
        job.setCron(cron);

        scheduleJobService.addJob(job);

        scheduleManager.addJob(job);
        return job;
    }

    @Autowired
    private ScheduleManager scheduleManager;

    @Autowired
    private ScheduleJobService scheduleJobService;
}
