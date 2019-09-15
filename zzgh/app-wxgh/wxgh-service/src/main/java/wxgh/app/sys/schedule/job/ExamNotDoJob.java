package wxgh.app.sys.schedule.job;


import org.quartz.DisallowConcurrentExecution;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import wxgh.app.sys.PushExam;

/**
 * @Version 1.0
 * --------------------------------------------------------- *
 * @Description >_  报名没有考试
 * --------------------------------------------------------- *
 * @Author Ape
 * --------------------------------------------------------- *
 * @Email <16511660@qq.com>
 * --------------------------------------------------------- *
 * @Date 2017-09-25  16:28
 * --------------------------------------------------------- *
 */
@DisallowConcurrentExecution
public class ExamNotDoJob implements Job {

    @Autowired
    private PushExam pushExam;

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        String jobId = (String) jobExecutionContext.getMergedJobDataMap().get("jobId");
        //获取考试id名称
        String examId = jobExecutionContext.getTrigger().getJobKey().getName();
        pushExam.pushNotDo(Integer.valueOf(examId), jobId, null);
    }

}
