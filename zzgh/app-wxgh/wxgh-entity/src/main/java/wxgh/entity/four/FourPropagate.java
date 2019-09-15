package wxgh.entity.four;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by WIN on 2016/8/24.
 */
@Entity
@Table(name = "t_four_propagate")
public class FourPropagate implements Serializable {

    // 新购
    public static final Integer Buy = 1;
    // 维修
    public static final Integer Repair = 2;

    private Integer id;
    private Integer deptId;
    private Integer fpId;
    private Integer fpcId;
    private String brand;
    private String modelName;
    private Integer numb;
    private String remark;
    private Float budget;
    private Integer status;
    private String fpName;
    private String fpcName;
    private String deptName;
    private String suggest;
    private Integer deviceStatus;
    private String userid;
    private Date applyTime;
    private Integer mid;
    private String unit;
    private Integer deId;
    private Integer addIs;

    private String marketing;
    private String username;

    public String getMarketing() {
        return marketing;
    }

    public void setMarketing(String marketing) {
        this.marketing = marketing;
    }

    @Column(name = "mid")
    public Integer getMid() {
        return mid;
    }

    public void setMid(Integer mid) {
        this.mid = mid;
    }

    @Column(name = "device_status")
    public Integer getDeviceStatus() {
        return deviceStatus;
    }

    public void setDeviceStatus(Integer deviceStatus) {
        this.deviceStatus = deviceStatus;
    }

    @Column(name = "suggest")
    public String getSuggest() {
        return suggest;
    }

    public void setSuggest(String suggest) {
        this.suggest = suggest;
    }

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    public String getFpName() {
        return fpName;
    }

    public void setFpName(String fpName) {
        this.fpName = fpName;
    }

    public String getFpcName() {
        return fpcName;
    }

    public void setFpcName(String fpcName) {
        this.fpcName = fpcName;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    public Integer getId() {
        return id;
    }

    @Column(name = "dept_id")
    public Integer getDeptId() {
        return deptId;
    }

    @Column(name = "fp_id")
    public Integer getFpId() {
        return fpId;
    }

    @Column(name = "fpc_id")
    public Integer getFpcId() {
        return fpcId;
    }

    @Column(name = "brand")
    public String getBrand() {
        return brand;
    }

    @Column(name = "model_name")
    public String getModelName() {
        return modelName;
    }

    @Column(name = "numb")
    public Integer getNumb() {
        return numb;
    }

    @Column(name = "remark")
    public String getRemark() {
        return remark;
    }

    @Column(name = "budget")
    public Float getBudget() {
        return budget;
    }

    @Column(name = "status")
    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setDeptId(Integer deptId) {
        this.deptId = deptId;
    }

    public void setFpId(Integer fpId) {
        this.fpId = fpId;
    }

    public void setFpcId(Integer fpcId) {
        this.fpcId = fpcId;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public void setModelName(String modelName) {
        this.modelName = modelName;
    }

    public void setNumb(Integer numb) {
        this.numb = numb;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public void setBudget(Float budget) {
        this.budget = budget;
    }

    @Column(name = "userid")
    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    @Column(name = "apply_time")
    public Date getApplyTime() {
        return applyTime;
    }

    public void setApplyTime(Date applyTime) {
        this.applyTime = applyTime;
    }

    @Column(name = "unit")
    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    @Column(name = "de_id")
    public Integer getDeId() {
        return deId;
    }

    public void setDeId(Integer deId) {
        this.deId = deId;
    }

    @Column(name = "add_is")
    public Integer getAddIs() {
        return addIs;
    }

    public void setAddIs(Integer addIs) {
        this.addIs = addIs;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
