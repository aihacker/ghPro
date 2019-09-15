package wxgh.entity.common.disease;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by Administrator on 2016/10/20.
 */
@Entity
@Table(name = "t_disease_pk")
public class DiseasePk implements Serializable {

    private Integer id;
    private String pkCate;
    private Float ownerIncome;
    private Float familyIncome;
    private String info;
    private Date addTime;
    private String userid;
    private Integer applyId;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Column(name = "pk_cate")
    public String getPkCate() {
        return pkCate;
    }

    public void setPkCate(String pkCate) {
        this.pkCate = pkCate;
    }

    @Column(name = "owner_income")
    public Float getOwnerIncome() {
        return ownerIncome;
    }

    public void setOwnerIncome(Float ownerIncome) {
        this.ownerIncome = ownerIncome;
    }

    @Column(name = "family_income")
    public Float getFamilyIncome() {
        return familyIncome;
    }

    public void setFamilyIncome(Float familyIncome) {
        this.familyIncome = familyIncome;
    }

    @Column(name = "info")
    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    @Column(name = "add_time")
    public Date getAddTime() {
        return addTime;
    }

    public void setAddTime(Date addTime) {
        this.addTime = addTime;
    }

    @Column(name = "userid")
    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    @Column(name = "apply_id")
    public Integer getApplyId() {
        return applyId;
    }

    public void setApplyId(Integer applyId) {
        this.applyId = applyId;
    }
}
