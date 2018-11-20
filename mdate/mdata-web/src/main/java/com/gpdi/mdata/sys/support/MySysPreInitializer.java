package com.gpdi.mdata.sys.support;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.InitializingBean;

public class MySysPreInitializer implements InitializingBean {

    Logger logger = LogManager.getLogger(MySysPreInitializer.class.getName());

    @Override
    public void afterPropertiesSet() {
        //getDsSynTask();
    }

//    private void getDsSynTask(){
//        DsRmdbJobFactory factory = DsRmdbJobFactory.getInstance();
//        factory.setDataSourceDao(dataSourceDao);
//        factory.setGeneralDao(generalDao);
//        new DsSynThread().start();
//    }
//
//    @Autowired
//    private GeneralDao generalDao;
//
//    @Autowired
//    private DsRmdbTaskDao dsRmdbTaskDao;
//
//    @Autowired
//    private DataSourceDao dataSourceDao;
//
//
//
//    private class DsSynThread extends Thread {
//        private JobManager jobManager = JobManager.getInstance();
//        @Override
//        public void run() {
//            //getDsSynTask();
//        }
//
//        //从数据库中读取数据源同步任务
//        private void getDsSynTask() {
//            DsRmdbJobFactory factory = DsRmdbJobFactory.getInstance();
//            System.out.println("into getDsSynTask GeneralDao : " + generalDao.hashCode());
//            Query<DsRmdbTaskEntity> query = new ListQuery();
//            String sql = "select * from t_ds_rmdb_task where max_err_num is not null and " +
//                    " max_err_num > 0 and is_enable=?";
//                    //+ ConstantVal.DS_TASK_ENABLE;
//            query.setSql(sql);
//            List<DsRmdbTaskEntity> list = generalDao.queryList(DsRmdbTaskEntity.class,
//                    sql, ConstantVal.DS_TASK_ENABLE);
//            if (list == null || list.size() == 0) {
//                return;
//            }
//            System.err.println("size : " + list.size());
//            for (DsRmdbTaskEntity entity : list) {
//                //System.out.println("taskid : " + entity.getId());
//                int curErrNum = entity.getCurErrNum() == null ? 0:entity.getCurErrNum();
//                if(curErrNum >= entity.getMaxErrNum()){
//                    entity.setIsEnable(ConstantVal.DS_TASK_DISABLE);
//                    try{
//                        dsRmdbTaskDao.saveAndToJOB(entity);
//                    }catch (Exception e){
//                    }
//                    continue;
//                }
//                Integer full_status = entity.getFullStatus();
//                Integer inc_status = entity.getIncrementStatus();
//                DataSource dataSource = null;
//                try {
//                    dataSource= dataSourceDao.get(entity.getDsId());
//                }catch (Exception e){
//                }
//                if(dataSource == null){
//                    continue;
//                }
//                if (full_status != null && full_status == ConstantVal.DS_TASK_ENABLE){
//                    DsRmdbTask task = factory.getTaskByTaskEntry(entity, false, true);
//                    if(task == null){
//                        continue;
//                    }
//                    DsRmdbJob job = new DsRmdbJob(task, dsRmdbTaskDao);
//                    JobDataMap dataMap = new JobDataMap();
//                    dataMap.put(DsRmdbJob.TASK_PARA, task);
//                    dataMap.put(DsRmdbJob.TASK_DAO_PARA, dsRmdbTaskDao);
//                    Trigger trigger = JobManager.getTriggerByTask(task, entity, job);
//                    if(trigger != null) {
//                        jobManager.addJob(job, dataMap, trigger, dataSource);
//                    }
//                }
//                if(inc_status != null && inc_status == ConstantVal.DS_TASK_ENABLE){
//                    DsRmdbTask task = factory.getTaskByTaskEntry(entity, false, false);
//                    if(task == null){
//                        continue;
//                    }
//                    DsRmdbJob job = new DsRmdbJob(task, dsRmdbTaskDao);
//                    JobDataMap dataMap = new JobDataMap();
//                    dataMap.put(DsRmdbJob.TASK_PARA, task);
//                    dataMap.put(DsRmdbJob.TASK_DAO_PARA, dsRmdbTaskDao);
//                    Trigger trigger = JobManager.getTriggerByTask(task, entity, job);
//                    if(trigger != null) {
//                        jobManager.addJob(job, dataMap, trigger, dataSource);
//                    }
//                }
//            }
//            System.out.println("job size : " + jobManager.getJobSize());
//        }
//    }


}
