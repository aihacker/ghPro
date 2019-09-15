package wxgh.app.sys.schedule.job;


import com.libs.common.type.TypeUtils;
import org.quartz.*;
import org.springframework.beans.factory.annotation.Autowired;
import wxgh.app.sys.schedule.JobGroup;
import wxgh.app.sys.task.WeixinPush;
import wxgh.entity.pub.ScheduleJob;
import wxgh.sys.service.admin.notice.NewNoticeService;
import wxgh.sys.service.admin.party.article.ArticleService;
import wxgh.sys.service.weixin.pub.ScheduleJobService;

import java.util.List;

/**
 * @Version 1.0
 * --------------------------------------------------------- *
 * @Description >_  定时推送文章
 * --------------------------------------------------------- *
 * @Author Ape
 * --------------------------------------------------------- *
 * @Email <16511660@qq.com>
 * --------------------------------------------------------- *
 * @Date 2017-12-12  14:49
 * --------------------------------------------------------- *
 */
@DisallowConcurrentExecution
public class ArticlePushJob implements Job{

    @Autowired
    private ScheduleJobService scheduleJobService;

    @Autowired
    private WeixinPush weixinPush;

    @Autowired
    private ArticleService articleService;

    @Autowired
    private NewNoticeService newNoticeService;

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        JobDataMap map = jobExecutionContext.getMergedJobDataMap();
        String jobId = (String) map.get("jobId");
        ScheduleJob scheduleJob = scheduleJobService.get(jobId);

       String jobGroup = scheduleJob.getJobGroup();
       if (jobGroup.equals(JobGroup.PARTY_ARTICLE_PUSH.getName())){    // 党建
           System.out.println("定时推送党建文章");
            articleService.push(scheduleJob.getJobName(), null, true);
       }else if(jobGroup.equals(JobGroup.TRIBE_RESULT_PUSH.getName())){  // 青年部落
           System.out.println("定时推送青年部落");
           List<Integer> ids = TypeUtils.strToList(scheduleJob.getJobName());
           weixinPush.tribe_result(ids, 0);
       }else if(jobGroup.equals(JobGroup.NEWS_PUSH.getName())){   // 新闻频道
           System.out.println("定时推送新闻频道");
           newNoticeService.push(scheduleJob.getJobName(), null, true);
       }

    }



}
