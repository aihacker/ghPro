package wxgh.param.common.fraternity;

import java.io.Serializable;

/**
 * Created by XDLK on 2016/8/29.
 * <p>
 * Date： 2016/8/29
 */
public class ApplyParam implements Serializable {

    private String userid; //申请人
    private Integer status; //申请状态
    private Integer step; //申请步骤，默认
    private Integer start;
    private Integer num;
    private Integer id;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    private Integer fraternityOldestId;
    private Integer isFirst;

    public Integer getFraternityOldestId() {
        return fraternityOldestId;
    }

    public void setFraternityOldestId(Integer fraternityOldestId) {
        this.fraternityOldestId = fraternityOldestId;
    }

    public Integer getIsFirst() {
        return isFirst;
    }

    public void setIsFirst(Integer isFirst) {
        this.isFirst = isFirst;
    }

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }

    public Integer getStart() {
        return start;
    }

    public void setStart(Integer start) {
        this.start = start;
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

    public Integer getStep() {
        return step;
    }

    public void setStep(Integer step) {
        this.step = step;
    }
}
