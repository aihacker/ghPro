package wxgh.app.sys.schedule;

import com.libs.common.json.JsonUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.quartz.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import wxgh.entity.pub.ScheduleJob;

/**
 * Created by Administrator on 2017/7/13.
 */
@Component
public class ScheduleManager {

    private static final Log log = LogFactory.getLog(ScheduleManager.class);

    @Autowired
    private Scheduler scheduler;

    public void addJob(ScheduleJob job) {
        try {
            //判断触发器是否已经存在
            TriggerKey key = new TriggerKey(job.getJobName(), job.getJobGroup());
            if (!scheduler.checkExists(key)) {
                JobDetail jobDetail = JobBuilder.newJob((Class<? extends Job>) Class.forName(job.getJobClass()))
                        .withIdentity(job.getJobName(), job.getJobGroup())
                        .build();
                jobDetail.getJobDataMap().put("jobId", job.getJobId());

                CronTrigger trigger = TriggerBuilder.newTrigger()
                        .withSchedule(CronScheduleBuilder.cronSchedule(job.getCron()))
                        .build();

                if (!scheduler.isShutdown()) {
                    scheduler.start();
                }

                scheduler.scheduleJob(jobDetail, trigger);

                if (log.isDebugEnabled()) {
                    log.debug("【ScheduleJob Add】add schedulejob：" + JsonUtils.stringfy(job));
                }
            }
        } catch (ClassNotFoundException e) {
            log.error("【Schedule Add】add schedulejob:" + JsonUtils.stringfy(scheduler), e);
        } catch (SchedulerException e) {
            log.error("【Schedule Add】add schedulejob:" + JsonUtils.stringfy(scheduler), e);
        }
    }

    public void modify(ScheduleJob job) {
        try {
            TriggerKey triggerKey = TriggerKey.triggerKey(job.getJobName(), job.getJobGroup());
            CronTrigger trigger = (CronTrigger) scheduler.getTrigger(triggerKey);

            if (trigger == null) { //如果触发器不存在
                return;
            }

            String old = trigger.getCronExpression();
            if (!old.equalsIgnoreCase(job.getCron())) {
                TriggerBuilder<Trigger> triggerBuilder = TriggerBuilder.newTrigger();
                triggerBuilder.withIdentity(job.getJobName(), job.getJobGroup());
                triggerBuilder.startNow();

                triggerBuilder.withSchedule(CronScheduleBuilder.cronSchedule(job.getCron()));
                trigger = (CronTrigger) triggerBuilder.build();

                scheduler.rescheduleJob(triggerKey, trigger);
            }
            if (log.isDebugEnabled()) {
                log.debug("【ScheduleJob Modify】modify schedulejob：" + JsonUtils.stringfy(job));
            }
        } catch (SchedulerException e) {
            log.error("【Schedule modify】add schedulejob:" + JsonUtils.stringfy(scheduler), e);
        }

    }

    /**
     * 移除一个任务
     *
     * @param jobName
     * @param jobGroup
     */
    public void removeJob(String jobName, String jobGroup) {
        try {

            TriggerKey key = TriggerKey.triggerKey(jobName, jobGroup);

            scheduler.pauseTrigger(key); //停止触发器
            scheduler.unscheduleJob(key); //移除触发器
            scheduler.deleteJob(JobKey.jobKey(jobName, jobGroup)); //删除任务

            if (log.isDebugEnabled()) {
                log.debug("【ScheduleJob Remove】remove schedulejob：" + jobGroup + "-" + jobName);
            }
        } catch (SchedulerException e) {
            log.error("【Schedule remove】add schedulejob:" + JsonUtils.stringfy(scheduler), e);
        }
    }

    /**
     * 启动所有job
     */
    public void startJobs() {
        try {
            scheduler.start();
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
    }

    /**
     * 关闭所有的定时任务
     */
    public void shutdownJobs() {
        try {
            if (!scheduler.isShutdown()) {
                scheduler.shutdown();
            }
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
    }

}
