package wxgh.data.four;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by WIN on 2016/8/29.
 */
public class FourDetailsData implements Serializable {

    private Integer deptId;
    private Integer fpId;
    private Integer fpcId;
    private String brand;
    private String modelName;
    private Integer numb;
    private String remark;
    private String buyTime;
    private Integer condition;
    private Integer fproId;
    private Date time;
    private String condit;

    public String getCondit() {
        return condit;
    }

    public void setCondit(String condit) {
        this.condit = condit;
    }

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

    public Integer getFproId() {
        return fproId;
    }

    public void setFproId(Integer fproId) {
        this.fproId = fproId;
    }

    public Integer getDeptId() {
        return deptId;
    }

    public void setDeptId(Integer deptId) {
        this.deptId = deptId;
    }


    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getModelName() {
        return modelName;
    }

    public void setModelName(String modelName) {
        this.modelName = modelName;
    }

    public Integer getNumb() {
        return numb;
    }

    public void setNumb(Integer numb) {
        this.numb = numb;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getBuyTime() {
        return buyTime;
    }

    public void setBuyTime(String buyTime) {
        this.buyTime = buyTime;
    }

    public Integer getCondition() {
        return condition;
    }

    public void setCondition(Integer condition) {
        this.condition = condition;
    }
}
