package wxgh.app.sys.api;


import com.libs.common.data.DateUtils;
import com.libs.common.scheduler.CronUtils;
import com.libs.common.type.StringUtils;
import com.libs.common.type.TypeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import wxgh.app.sys.schedule.JobGroup;
import wxgh.app.sys.schedule.ScheduleManager;
import wxgh.app.sys.schedule.job.ArticlePushJob;
import wxgh.entity.pub.ScheduleJob;
import wxgh.sys.service.weixin.pub.ScheduleJobService;

import java.util.Date;
import java.util.List;

/**
 * @Version 1.0
 * --------------------------------------------------------- *
 * @Description >_
 * --------------------------------------------------------- *
 * @Author Ape
 * --------------------------------------------------------- *
 * @Email <16511660@qq.com>
 * --------------------------------------------------------- *
 * @Date 2017-12-12  15:03
 * --------------------------------------------------------- *
 */
@Component
public class ArticleSchedule {

    @Autowired
    private ScheduleJobService scheduleJobService;

    @Autowired
    private ScheduleManager scheduleManager;

    /**
     * 党建文章推送
     * @param id
     * @param pushTime
     */
    public void pushByParty(String id, Date pushTime){
        if(pushTime == null || pushTime.before(new Date()))
            return;
        String cron = CronUtils.getCron(pushTime);
        ScheduleJob job = new ScheduleJob();
        job.setJobId(StringUtils.uuid());
        job.setCreateTime(new Date());
        job.setExecuteTime(DateUtils.toStr(pushTime));
        job.setJobName(id);
        job.setJobGroup(JobGroup.PARTY_ARTICLE_PUSH.getName());
        job.setStatus(ScheduleJob.STATUS_NORMAL);
        job.setCron(cron);
        job.setDescript("【" + id + "】定时推送党建文章：" + DateUtils.toStr(pushTime));
        job.setJobClass(ArticlePushJob.class.getName());
        scheduleJobService.addOrUpdateJobPushArticle(job);

        //创建定时器
        scheduleManager.addJob(job);
    }

    /**
     * 青年部落成果
     * @param ids
     * @param pushTime
     */
    public void pushByTribeResult(List<Integer> ids, Date pushTime){
        if(pushTime == null || pushTime.before(new Date()))
            return;
        String cron = CronUtils.getCron(pushTime);
        ScheduleJob job = new ScheduleJob();
        job.setJobId(StringUtils.uuid());
        job.setCreateTime(new Date());
        job.setExecuteTime(DateUtils.toStr(pushTime));
        job.setJobName(TypeUtils.listToStr(ids));
        job.setJobGroup(JobGroup.TRIBE_RESULT_PUSH.getName());
        job.setStatus(ScheduleJob.STATUS_NORMAL);
        job.setCron(cron);
        job.setDescript("【" + TypeUtils.listToStr(ids) + "】定时推送青年部落成果：" + DateUtils.toStr(pushTime));
        job.setJobClass(ArticlePushJob.class.getName());
        scheduleJobService.addOrUpdateJobPushArticle(job);
        scheduleManager.addJob(job);
    }

    /**
     * 新闻频道
     * @param id
     * @param pushTime
     */
    public void pushByNews(String id, Date pushTime){
        if(pushTime == null || pushTime.before(new Date()))
            return;
        String cron = CronUtils.getCron(pushTime);
        ScheduleJob job = new ScheduleJob();
        job.setJobId(StringUtils.uuid());
        job.setCreateTime(new Date());
        job.setExecuteTime(DateUtils.toStr(pushTime));
        job.setJobName(id);
        job.setJobGroup(JobGroup.NEWS_PUSH.getName());
        job.setStatus(ScheduleJob.STATUS_NORMAL);
        job.setCron(cron);
        job.setDescript("【" + id + "】定时推送新闻频道文章：" + DateUtils.toStr(pushTime));
        job.setJobClass(ArticlePushJob.class.getName());
        scheduleJobService.addOrUpdateJobPushArticle(job);
        scheduleManager.addJob(job);
    }

}
