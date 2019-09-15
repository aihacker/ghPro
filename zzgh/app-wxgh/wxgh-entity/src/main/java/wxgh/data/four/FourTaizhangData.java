package wxgh.data.four;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by WIN on 2016/8/29.
 */
public class FourTaizhangData implements Serializable{
    private  Integer numb;
    private  Integer status0;
    private  Integer status1;
    private  Integer status2;
    private  String remark;
    private  Date time;
    private  Integer fpId;
    private  Integer fpcId;


    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public Integer getFpId() {
        return fpId;
    }

    public void setFpId(Integer fpId) {
        this.fpId = fpId;
    }

    public Integer getFpcId() {
        return fpcId;
    }

    public void setFpcId(Integer fpcId) {
        this.fpcId = fpcId;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Integer getNumb() {
        return numb;
    }

    public void setNumb(Integer numb) {
        this.numb = numb;
    }

    public Integer getStatus0() {
        return status0;
    }

    public void setStatus0(Integer status0) {
        this.status0 = status0;
    }

    public Integer getStatus1() {
        return status1;
    }

    public void setStatus1(Integer status1) {
        this.status1 = status1;
    }

    public Integer getStatus2() {
        return status2;
    }

    public void setStatus2(Integer status2) {
        this.status2 = status2;
    }
}
