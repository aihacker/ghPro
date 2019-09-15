package com.weixin.bean.batch;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by Administrator on 2017/7/13.
 */
public class BatchJob {

    @JsonProperty("JobId")
    private String jobId;
    @JsonProperty("JobType")
    private String jobType;
    @JsonProperty("ErrCode")
    private Integer errCode;
    @JsonProperty("ErrMsg")
    private String errMsg;

    public String getJobId() {
        return jobId;
    }

    public void setJobId(String jobId) {
        this.jobId = jobId;
    }

    public String getJobType() {
        return jobType;
    }

    public void setJobType(String jobType) {
        this.jobType = jobType;
    }

    public Integer getErrCode() {
        return errCode;
    }

    public void setErrCode(Integer errCode) {
        this.errCode = errCode;
    }

    public String getErrMsg() {
        return errMsg;
    }

    public void setErrMsg(String errMsg) {
        this.errMsg = errMsg;
    }
}
