package wxgh.app.sys.api;


import com.libs.common.type.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import wxgh.app.sys.schedule.JobGroup;
import wxgh.app.sys.schedule.ScheduleManager;
import wxgh.app.sys.schedule.job.PlaceYuyueJob;
import wxgh.entity.pub.ScheduleJob;
import wxgh.sys.service.weixin.pub.ScheduleJobService;

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
 * @Date 2017-09-30  11:31
 * --------------------------------------------------------- *
 */
@Component
public class PlaceSchedule {

    public static final String THIS_WEEK = "this_week";

    @Autowired
    private ScheduleJobService scheduleJobService;

    @Autowired
    private ScheduleManager scheduleManager;

    // 到期，过时
//    public void expire(PlaceTime placeTime) {
//        int[] weeks = new int[]{placeTime.getWeek()};
//
//        Date dayOfStart = DateUtils.getDayStartTime(new Date());
//        // 结束执行
//        Date hourTimeOfDay = DateUtils.formatStr(placeTime.getEndTime(), "HH:mm");
//        // 加上时区 8 h
//        Date executeDate = new Date(dayOfStart.getTime() + hourTimeOfDay.getTime() + 8 * 60 * 60 * 1000);
//
//        String cron = CronUtils.getCronWeek(executeDate, weeks, 0F);
//        ScheduleJob job = new ScheduleJob();
//        job.setJobId(StringUtils.uuid());
//        job.setCreateTime(new Date());
//        job.setExecuteTime(DateUtils.getWeekName(weeks));
//        job.setJobName(placeTime.getId() + "");
//        job.setJobGroup(JobGroup.PLACE_WEEK.getType());
//        job.setStatus(ScheduleJob.STATUS_NORMAL);
//        job.setCron(cron);
//        job.setDescript("【周" + placeTime.getWeek() + "("+placeTime.getStartTime() + "-" + placeTime.getEndTime() +")】场馆失效：" + DateUtils.getWeekName(weeks));
//        job.setJobClass(PlaceExpireJob.class.getName());
//
//        scheduleJobService.addOrUpdateJob(job);
//
//        //创建定时器
//        scheduleManager.addJob(job);
//    }

    public ScheduleJob getJob(){
        ScheduleJob job = new ScheduleJob();
        job.setJobId(StringUtils.uuid());
        job.setCreateTime(new Date());
        job.setJobName("place_yuyue");
        job.setJobGroup(JobGroup.PLACE_WEEK.getType());
        job.setStatus(ScheduleJob.STATUS_ING);
        job.setCron("*/20 * * * * ? ");
        job.setJobClass(PlaceYuyueJob.class.getName());
        return job;
    }

}
