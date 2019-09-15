package wxgh.app.sys.schedule.job;


import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import pub.utils.DateUtils;
import wxgh.sys.service.weixin.entertain.nanhai.place.NanHaiPlaceTimeService;
import wxgh.sys.service.weixin.entertain.place.PlaceTimeService;
import wxgh.sys.service.weixin.entertain.place.PlaceYuyueService;

import java.util.Date;
import java.util.List;

/**
 * @Version 1.0
 * --------------------------------------------------------- *
 * @Description >_  每分钟执行一次， 清除时间段失效记录，清除上一周的预约场馆状态数据
 * --------------------------------------------------------- *
 * @Author Ape
 * --------------------------------------------------------- *
 * @Email <16511660@qq.com>
 * --------------------------------------------------------- *
 * @Date 2017-08-28  17:25
 * --------------------------------------------------------- *
 */
public class NanHaiPlaceYuyueJob implements Job {

    private static final Log log = LogFactory.getLog(NanHaiPlaceYuyueJob.class);


    @Autowired
    private NanHaiPlaceTimeService placeTimeService;

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {

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
