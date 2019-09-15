package com.weixin.bean.result.batch;

import com.weixin.bean.ErrResult;

/**
 * Created by XLKAI on 2017/7/9.
 */
public class SyncResult extends ErrResult {

    private String jobid;

    public String getJobid() {
        return jobid;
    }

    public void setJobid(String jobid) {
        this.jobid = jobid;
    }
}
