package com.gpdi.mdata.sys.entity.system;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "tb_staff")
public class Staff implements Serializable{
    private Integer id;

    private String name;

    private Integer sex;

    private String phoneNbr;

    private String mobileNbr;

    private String emailAddr;

    private String idcard;

    private Integer state;

    private Date createDate;

    private Date updateDate;        //在职/离职时间

    private String staffNumber;     //员工号

    private String post;        //职位

    private Integer departmentId;       //部门ID

    private String departmentName;

    private BigDecimal quota;

    private List<String> numbers;

    private String level;

    private String remark;

    //非表字段
    private Integer companyId;



    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Integer getId() {
        return id;
    }
    @Column(name = "name")
    public String getName() {
        return name;
    }
    @Column(name = "sex")
    public Integer getSex() {
        return sex;
    }
    @Column(name = "phone_nbr")
    public String getPhoneNbr() {
        return phoneNbr;
    }
    @Column(name = "mobile_nbr")
    public String getMobileNbr() {
        return mobileNbr;
    }
    @Column(name = "email_addr")
    public String getEmailAddr() {
        return emailAddr;
    }
    @Column(name = "idcard")
    public String getIdcard() {
        return idcard;
    }
    @Column(name = "state")
    public Integer getState() {
        return state;
    }
    @Column(name = "create_date")
    public Date getCreateDate() {
        return createDate;
    }
    @Column(name = "update_date")
    public Date getUpdateDate() {
        return updateDate;
    }

    @Column(name = "staff_number")
    public String getStaffNumber() {
        return staffNumber;
    }
    @Column(name = "post")
    public String getPost() {
        return post;
    }
    @Column(name = "department_id")
    public Integer getDepartmentId() {
        return departmentId;
    }
    @Column(name = "quota")
    public BigDecimal getQuota() {
        return quota;
    }
    @Column(name="level")
    public String getLevel() {
        return level;
    }
    @Column(name="remark")
    public String getRemark() {
        return remark;
    }

    public Integer getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Integer companyId) {
        this.companyId = companyId;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    public void setLevel(String level) {
        this.level = level;
    }
    public List<String > getNumbers() {
        return numbers;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSex(Integer sex) {
            this.sex = sex;
    }

    public void setPhoneNbr(String phoneNbr) {
        this.phoneNbr = phoneNbr;
    }

    public void setMobileNbr(String mobileNbr) {
        this.mobileNbr = mobileNbr;
    }

    public void setEmailAddr(String emailAddr) {
        this.emailAddr = emailAddr;
    }

    public void setIdcard(String idcard) {
        this.idcard = idcard;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public void setStaffNumber(String staffNumber) {
        this.staffNumber = staffNumber;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    public void setPost(String post) {
        this.post = post;
    }

    public void setDepartmentId(Integer departmentId) {
        this.departmentId = departmentId;
    }

    public void setQuota(BigDecimal quota) {
        this.quota = quota;
    }

    public void setNumbers(List<String> numbers) {
        this.numbers = numbers;
    }
}