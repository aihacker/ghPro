package wxgh.app.sys.quartz;

import com.weixin.api.TokenApi;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class ScheduleTest implements Job{

    //@Scheduled(cron = "0 */30 * * * ?")
    /*public void schTest1() {
        TokenApi.clearToken();
    }*/

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {

    }
}
