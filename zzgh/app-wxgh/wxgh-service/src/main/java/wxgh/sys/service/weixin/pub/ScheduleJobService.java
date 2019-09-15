package wxgh.sys.service.weixin.pub;

import com.libs.common.type.StringUtils;
import com.libs.common.type.TypeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pub.dao.jdbc.PubDao;
import wxgh.entity.pub.ScheduleJob;
import wxgh.sys.dao.pub.ScheduleJobDao;

import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2017/7/13.
 */
@Service
@Transactional(readOnly = true)
public class ScheduleJobService {

    @Transactional
    public void addJob(ScheduleJob job) {
        String sql = "select id from t_schedule_job where job_name = ? and job_group = ? limit 1";
        Integer id = pubDao.query(Integer.class, sql, job.getJobName(), job.getJobGroup());
        if (id == null) {
            job.setCreateTime(new Date());
            job.setJobId(StringUtils.uuid());
            if (job.getStatus() == null) {
                job.setStatus(ScheduleJob.STATUS_NORMAL);
            }
            scheduleJobDao.save(job);
        }
    }

    public ScheduleJob get(String jobId) {
        String sql = "select * from t_schedule_job where job_id = ?";
        return pubDao.query(ScheduleJob.class, sql, jobId);
    }

    @Transactional
    public void addOrUpdateJob(ScheduleJob job) {
        if (TypeUtils.empty(job.getJobId())) {
            addJob(job);
        } else {
            String sql = "select id from t_schedule_job where job_name = ?";
            Integer id = pubDao.query(Integer.class, sql, job.getJobId());
            if (id == null) {
                addJob(job);
            } else {
                job.setUpdateTime(new Date());
                scheduleJobDao.update(job, "job_name");
            }
        }
    }

    @Transactional
    public void addOrUpdateJobAct(ScheduleJob job) {
        addJob(job);
//        if (TypeUtils.empty(job.getJobId())) {
//            addJob(job);
//        } else {
//            job.setUpdateTime(new Date());
//            //scheduleJobDao.update(job, "job_name");
//            String sql = "update t_schedule_job SET cron = :cron, execute_time = :executeTime, " +
//                    "update_time = :updateTime where job_name = :jobName";
//            pubDao.executeBean(sql, job);
//        }
    }

    @Transactional
    public void addOrUpdateJobDiIn(ScheduleJob job) {
        String sql = "select * from t_schedule_job where job_name = ? and descript = ?";
        List<ScheduleJob> scheduleJobs = pubDao.queryList(ScheduleJob.class, sql, job.getJobName(), job.getDescript());
        if (scheduleJobs.size() == 0) {
            addJob(job);
        } else {
            job.setUpdateTime(new Date());
            //scheduleJobDao.update(job, "job_name");
            String sql2 = "update t_schedule_job SET cron = :cron, execute_time = :executeTime, " +
                    "update_time = :updateTime where job_name = :jobName and descript = :descript";
            pubDao.executeBean(sql2, job);
        }
    }

    @Transactional
    public void addOrUpdateJobPushArticle(ScheduleJob job) {
        job.setCreateTime(new Date());
        if (job.getJobId() == null)
            job.setJobId(StringUtils.uuid());
        if (job.getStatus() == null)
            job.setStatus(ScheduleJob.STATUS_NORMAL);
        scheduleJobDao.save(job);
        /*
        String sql = "select * from t_schedule_job where job_name = ? and job_group = ?";
        List<ScheduleJob> scheduleJobs = pubDao.queryList(ScheduleJob.class,sql,job.getJobName(),job.getJobGroup());
        if (scheduleJobs.size() == 0) {
            addJob(job);
        } else {
            job.setUpdateTime(new Date());
            String sql2 = "update t_schedule_job SET cron = :cron, execute_time = :executeTime, " +
                    "update_time = :updateTime where job_id = :jobId";
            pubDao.executeBean(sql2,job);
        }
        */
    }

    @Transactional
    public void addOrUpdateJobDiRe(ScheduleJob job) {
        String sql = "select * from t_schedule_job where job_name = ? and descript = ?";
        List<ScheduleJob> scheduleJobs = pubDao.queryList(ScheduleJob.class, sql, job.getJobName(), job.getDescript());
        if (scheduleJobs.size() == 0) {
            addJob(job);
        } else {
            job.setUpdateTime(new Date());
            //scheduleJobDao.update(job, "job_name");
            String sql2 = "update t_schedule_job SET cron = :cron, execute_time = :executeTime, " +
                    "update_time = :updateTime where job_name = :jobName and descript = :descript";
            pubDao.executeBean(sql2, job);
        }
    }

    public List<ScheduleJob> listJobs(Integer status) {
        String sql = "select * from t_schedule_job where status = ?";
        return pubDao.queryList(ScheduleJob.class, sql, status);
    }

    @Autowired
    private ScheduleJobDao scheduleJobDao;

    @Autowired
    private PubDao pubDao;

}
