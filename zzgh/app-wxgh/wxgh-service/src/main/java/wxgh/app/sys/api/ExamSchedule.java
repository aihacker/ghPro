package wxgh.app.sys.api;


import com.libs.common.data.DateUtils;
import com.libs.common.scheduler.CronUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import wxgh.app.sys.schedule.JobGroup;
import wxgh.app.sys.schedule.ScheduleManager;
import wxgh.app.sys.schedule.job.ExamNotDoJob;
import wxgh.app.sys.schedule.job.ExamNotJoinJob;
import wxgh.entity.party.di.Exam;
import wxgh.entity.pub.ScheduleJob;
import wxgh.sys.service.weixin.pub.ScheduleJobService;

import java.util.Calendar;
import java.util.Date;

/**
 * @Version 1.0
 * --------------------------------------------------------- *
 * @Description >_
 * --------------------------------------------------------- *
 * @Author Ape
 * --------------------------------------------------------- *
 * @Email <16511660@qq.com>
 * --------------------------------------------------------- *
 * @Date 2017-09-26  08:56
 * --------------------------------------------------------- *
 */
@Component
public class ExamSchedule {

    public void notifies(Exam exam){
        Integer examNotifyDay = 3;
        Integer examJoinNotifyDay = 7;
        Date now = new Date();

        // 測試數據 提前 3分鐘 7分鐘
//        Date examNotifyTime = DateUtils.add(exam.getEndTime(), Calendar.MINUTE, -1 * examNotifyDay);
//        Date examJoinNotifyTime = DateUtils.add(exam.getEndTime(), Calendar.MINUTE, -1 * examJoinNotifyDay);
//        String cron3 = CronUtils.getCronOne(exam.getEndTime(), examNotifyDay * 1.0F / 60 * 1.0F);
//        String cron7 = CronUtils.getCronOne(exam.getEndTime(), examJoinNotifyDay * 1.0F / 60 * 1.0F);

        // 正式版数据 提前 3天 7天
        Date examNotifyTime = DateUtils.add(exam.getEndTime(), Calendar.DAY_OF_YEAR, -1 * examNotifyDay);
        Date examJoinNotifyTime = DateUtils.add(exam.getEndTime(), Calendar.DAY_OF_YEAR, -1 * examJoinNotifyDay);
        String cron3 = CronUtils.getCronOne(exam.getEndTime(), examNotifyDay * 24 * 1.0F);
        String cron7 = CronUtils.getCronOne(exam.getEndTime(), examJoinNotifyDay * 24 * 1.0F);

        // 提醒考试定时器
        if (!now.after(examNotifyTime)) {
            ScheduleJob job = new ScheduleJob();
            job.setExecuteTime(DateUtils.toStr(examNotifyTime));
            job.setJobName(exam.getId().toString());
            job.setJobGroup(JobGroup.EXAM_PUSH.getType());
            job.setCron(cron3);
            job.setDescript("【" + exam.getName() + "】考试通知");
            job.setJobClass(ExamNotDoJob.class.getName());

            scheduleJobService.addOrUpdateJobDiIn(job);
            scheduleManager.addJob(job);
        }

        // 提醒报名定时器
        if (!now.after(examJoinNotifyTime)) {
            ScheduleJob job = new ScheduleJob();
            job.setExecuteTime(DateUtils.toStr(examJoinNotifyTime));
            job.setJobName(exam.getId().toString());
            job.setJobGroup(JobGroup.EXAM_JOIN_PUSH.getType());
            job.setCron(cron7);
            job.setDescript("【" + exam.getName() + "】考试报名通知");
            job.setJobClass(ExamNotJoinJob.class.getName());

            scheduleJobService.addOrUpdateJobDiRe(job);
            scheduleManager.addJob(job);
        }

    }

    @Autowired
    private ScheduleJobService scheduleJobService;

    @Autowired
    private ScheduleManager scheduleManager;

}
