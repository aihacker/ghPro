package wxgh.app.sys.schedule.job;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;
import pub.utils.DateUtils;
import wxgh.sys.service.weixin.entertain.place.PlaceTimeService;
import wxgh.sys.service.weixin.entertain.place.PlaceYuyueService;

import java.util.Date;
import java.util.List;

@Controller
@Lazy(false)
@EnableScheduling
public class PlaceYuyueWork {

    @Autowired
    private PlaceTimeService placeTimeService;

    @Scheduled(cron = "0 */1 * * * ?")
    public void work(){
        System.out.println("excute benbu work");
        int week = DateUtils.getWeek(new Date());
        // 是否为单周
        boolean isSingle = com.libs.common.data.DateUtils.isSingleWeek();

        //检测已过期的预约时间，并更新他们的状态
        List<Integer> timeIds;
        if (week == 7) { //如果为周末
            timeIds = placeTimeService.getSunDayOutTime();
        } else {
            timeIds = placeTimeService.getTimeOutTime();
            if (week == 1) { //如果为周一，更新上一周的场馆信息（恢复为可预约状态）一周执行一次
                placeTimeService.updateWeekStatus(0, isSingle);
            }
        }
        placeTimeService.updateStatus(timeIds, 2, isSingle); //设置时间已过时
    }

}
