package wxgh.app.sys.schedule.job.sport;

import org.quartz.*;
import org.springframework.beans.factory.annotation.Autowired;
import pub.functions.DateFuncs;
import wxgh.app.sys.api.sport.SportApi;

/**
 * 每周周二凌晨0点执行
 * <p>
 * Created with IntelliJ IDEA.
 * author：xlkai
 * date：2018/1/5
 * time：17:38
 * version：V1.0
 * Description：
 */
@DisallowConcurrentExecution
public class SportWeekJob implements Job {

    private final int[] deptids = {3, 9};

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
        JobDataMap map = context.getMergedJobDataMap();
        String jobId = (String) map.get("jobId");
        Integer actId = Integer.valueOf(jobId.replace("_week", ""));

        int dateid = DateFuncs.getIntToday();
        int end = DateFuncs.addDay(dateid, -2);
        int start = DateFuncs.addDay(end, -6);
        for (int deptid : deptids) {
            if (deptid == 9) {
                actId = 3;
            }
            sportApi.week(start, end, deptid, actId, dateid);
        }
    }

    @Autowired
    private SportApi sportApi;

}
