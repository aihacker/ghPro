package wxgh.param.union.innovation.work;

import java.io.Serializable;

/**
 * Created by XDLK on 2016/8/29.
 * <p>
 * Date： 2016/8/29
 */
public class WorkInnovateQuery implements Serializable {

    private String userid;

    private Integer status; //0未审核，1审核通过，2审核失败

    private Integer start;

    private Integer num;

    private Integer id;

    private Integer isFirst;

    private Integer jobOldestId;

    public Integer getJobOldestId() {
        return jobOldestId;
    }

    public void setJobOldestId(Integer jobOldestId) {
        this.jobOldestId = jobOldestId;
    }

    public Integer getIsFirst() {
        return isFirst;
    }

    public void setIsFirst(Integer isFirst) {
        this.isFirst = isFirst;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getStart() {
        return start;
    }

    public void setStart(Integer start) {
        this.start = start;
    }

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
