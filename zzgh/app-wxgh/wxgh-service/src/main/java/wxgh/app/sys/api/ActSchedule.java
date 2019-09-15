package wxgh.app.sys.api;

import com.libs.common.data.DateUtils;
import com.libs.common.scheduler.CronUtils;
import com.libs.common.type.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import pub.dao.jdbc.PubDao;
import wxgh.app.sys.schedule.JobGroup;
import wxgh.app.sys.schedule.ScheduleManager;
import wxgh.app.sys.schedule.job.ActPushJob;
import wxgh.entity.canteen.Canteen;
import wxgh.entity.canteen.CanteenAct;
import wxgh.entity.entertain.act.Act;
import wxgh.entity.pub.ScheduleJob;
import wxgh.sys.service.weixin.pub.ScheduleJobService;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by Administrator on 2017/9/6.
 */
@Component
public class ActSchedule {

    public void weekAct(Act act, int[] weekInts) {
        String cron = CronUtils.getCronWeek(act.getStartTime(), weekInts, act.getRemind());
        ScheduleJob job = new ScheduleJob();
        job.setJobId(StringUtils.uuid());
        job.setCreateTime(new Date());
        job.setExecuteTime(DateUtils.getWeekName(weekInts));
        job.setJobName(act.getActId());
        job.setJobGroup(JobGroup.ACT_WEEK.getType());
        job.setStatus(ScheduleJob.STATUS_NORMAL);
        job.setCron(cron);
        job.setDescript("【" + act.getName() + "】定期活动：" + DateUtils.getWeekName(weekInts));
        job.setJobClass(ActPushJob.class.getName());

        scheduleJobService.addOrUpdateJobAct(job);

        //创建定时器
        scheduleManager.addJob(job);
    }

    public void act(Act act) {
        Date now = new Date();
        //如果活动已过期，则不处理
        Date startTime = DateUtils.add(act.getStartTime(), Calendar.MINUTE, -((int) (act.getRemind() * 60)));
        if (now.after(startTime)) {
            return;
        }
        String cron = CronUtils.getCronOne(act.getStartTime(), act.getRemind());
        ScheduleJob job = new ScheduleJob();
        job.setExecuteTime(DateUtils.toStr(startTime));
        job.setJobName(act.getActId());
        job.setJobGroup(JobGroup.ACT_PUSH.getType());
        job.setCron(cron);
        job.setDescript("【" + act.getName() + "】普通活动");
        job.setJobClass(ActPushJob.class.getName());
        String sql = "select job_id from t_schedule_job where job_name = ?";
        String jobId = pubDao.query(String.class, sql, job.getJobName());
        job.setJobId(jobId);
        scheduleJobService.addOrUpdateJobAct(job);

        //创建定时器
        scheduleManager.addJob(job);
    }

    public void weekAct(CanteenAct act, int[] weekInts) {
        String cron = CronUtils.getCronWeek(act.getStartTime(), weekInts, act.getRemind());
        ScheduleJob job = new ScheduleJob();
        job.setJobId(StringUtils.uuid());
        job.setCreateTime(new Date());
        job.setExecuteTime(DateUtils.getWeekName(weekInts));
        job.setJobName(act.getActId());
        job.setJobGroup(JobGroup.ACT_WEEK.getType());
        job.setStatus(ScheduleJob.STATUS_NORMAL);
        job.setCron(cron);
        job.setDescript("【" + act.getName() + "】定期活动：" + DateUtils.getWeekName(weekInts));
        job.setJobClass(ActPushJob.class.getName());

        scheduleJobService.addOrUpdateJobAct(job);

        //创建定时器
        scheduleManager.addJob(job);
    }

    public void act(CanteenAct act) {
        Date now = new Date();
        //如果活动已过期，则不处理
        Date startTime = DateUtils.add(act.getStartTime(), Calendar.MINUTE, -((int) (act.getRemind() * 60)));
        if (now.after(startTime)) {
            return;
        }
        String cron = CronUtils.getCronOne(act.getStartTime(), act.getRemind());
        ScheduleJob job = new ScheduleJob();
        job.setExecuteTime(DateUtils.toStr(startTime));
        job.setJobName(act.getActId());
        job.setJobGroup(JobGroup.ACT_PUSH.getType());
        job.setCron(cron);
        job.setDescript("【" + act.getName() + "】普通活动");
        job.setJobClass(ActPushJob.class.getName());
        String sql = "select job_id from t_schedule_job where job_name = ?";
        String jobId = pubDao.query(String.class, sql, job.getJobName());
        job.setJobId(jobId);
        scheduleJobService.addOrUpdateJobAct(job);

        //创建定时器
        scheduleManager.addJob(job);
    }

    @Autowired
    private ScheduleJobService scheduleJobService;

    @Autowired
    private ScheduleManager scheduleManager;

    @Autowired
    private PubDao pubDao;
}
