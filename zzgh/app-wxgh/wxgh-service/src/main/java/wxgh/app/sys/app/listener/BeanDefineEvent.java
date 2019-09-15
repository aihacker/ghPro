package wxgh.app.sys.app.listener;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import wxgh.app.sys.schedule.ScheduleManager;
import wxgh.entity.pub.ScheduleJob;
import wxgh.sys.service.weixin.pub.ScheduleJobService;

import java.util.List;

/**
 * Created by Administrator on 2017/7/14.
 */
public class BeanDefineEvent implements ApplicationListener<ContextRefreshedEvent> {

    public static boolean run = true;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        if (!BeanDefineEvent.run) {
            //初始化定时器
            List<ScheduleJob> jobs = scheduleJobService.listJobs(ScheduleJob.STATUS_NORMAL);
            for (ScheduleJob job : jobs) {
                scheduleManager.addJob(job);
            }
            BeanDefineEvent.run = true;
        }
    }

    @Autowired
    private ScheduleJobService scheduleJobService;

    @Autowired
    private ScheduleManager scheduleManager;
}
