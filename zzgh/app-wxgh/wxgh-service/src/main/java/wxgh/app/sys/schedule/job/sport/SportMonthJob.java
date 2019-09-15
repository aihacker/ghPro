package wxgh.app.sys.schedule.job.sport;

import com.libs.common.data.DateUtils;
import org.quartz.*;
import org.springframework.beans.factory.annotation.Autowired;
import pub.functions.DateFuncs;
import wxgh.app.sys.api.sport.SportApi;

/**
 * 每月2号凌晨0点5分执行
 * <p>
 * Created with IntelliJ IDEA.
 * author：xlkai
 * date：2018/1/5
 * time：17:38
 * version：V1.0
 * Description：
 */
@DisallowConcurrentExecution
public class SportMonthJob implements Job {

    private final int[] deptids = {3,9};

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        JobDataMap map = context.getMergedJobDataMap();
        String jobId = (String) map.get("jobId");
        Integer actId = Integer.valueOf(jobId.replace("_month", ""));

        int dateid = DateFuncs.getIntToday();
        int end = DateFuncs.addDay(dateid, -2);
        int start = DateUtils.getFirstMonthDayInt(DateFuncs.fromIntDate(end));
        for (int deptid : deptids) {
            if(deptid==9){
               actId=3;
            }
            sportApi.month(start, end, deptid, actId, dateid);
            sportApi.monthParty(start,end,deptid,actId,dateid);
        }
    }

    @Autowired
    private SportApi sportApi;

}
