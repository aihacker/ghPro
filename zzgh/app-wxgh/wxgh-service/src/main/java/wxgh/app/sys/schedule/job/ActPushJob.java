package wxgh.app.sys.schedule.job;

import org.quartz.*;
import org.springframework.beans.factory.annotation.Autowired;
import wxgh.app.sys.PushAct;
import wxgh.sys.service.weixin.canteen.CanteenActService;

/**
 * 活动推送job
 * Created by Administrator on 2017/7/13.
 */
@DisallowConcurrentExecution
public class ActPushJob implements Job {

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        JobDataMap map = context.getMergedJobDataMap();
        String jobId = (String) map.get("jobId");

        //获取活动名称
        String actId = context.getTrigger().getJobKey().getName();

        //定期活动只推送给协会成员
        if (actService.getBaseInfo(actId) != null) {
            pushAct.canteenPush(actId, jobId, false, null);
        } else {
            pushAct.push(actId, jobId, false, null);
        }
    }

    @Autowired
    private CanteenActService actService;

    @Autowired
    private PushAct pushAct;
}
