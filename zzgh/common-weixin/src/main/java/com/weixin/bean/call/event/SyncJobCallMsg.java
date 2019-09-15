package com.weixin.bean.call.event;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.weixin.bean.batch.BatchJob;

/**
 * 异步任务完成事件推送
 * Created by Administrator on 2017/7/13.
 */
public class SyncJobCallMsg extends EventCallMsg {

    @JsonProperty("BatchJob")
    private BatchJob batchJob;

    public SyncJobCallMsg() {
        super("batch_job_result");
    }

    public BatchJob getBatchJob() {
        return batchJob;
    }

    public void setBatchJob(BatchJob batchJob) {
        this.batchJob = batchJob;
    }
}
