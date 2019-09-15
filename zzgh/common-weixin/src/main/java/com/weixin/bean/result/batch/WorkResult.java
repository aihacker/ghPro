package com.weixin.bean.result.batch;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.weixin.bean.ErrResult;
import com.weixin.json.ResultDeserialize;

import java.util.List;

/**
 * Created by XLKAI on 2017/7/9.
 */
@JsonDeserialize(using = ResultDeserialize.class)
public class WorkResult extends ErrResult {

    private Integer status;
    private String type;
    private Integer total;
    private Integer percentage;
    private Integer remaintime;
    private List<? extends SimpleResult> result;

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public Integer getPercentage() {
        return percentage;
    }

    public void setPercentage(Integer percentage) {
        this.percentage = percentage;
    }

    public Integer getRemaintime() {
        return remaintime;
    }

    public void setRemaintime(Integer remaintime) {
        this.remaintime = remaintime;
    }

    public List<? extends SimpleResult> getResult() {
        return result;
    }

    public void setResult(List<? extends SimpleResult> result) {
        this.result = result;
    }
}
