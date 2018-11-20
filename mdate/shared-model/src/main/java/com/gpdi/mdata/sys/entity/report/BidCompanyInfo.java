package com.gpdi.mdata.sys.entity.report;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * @description:公开采购投标公司信息
 * @author: WangXiaoGang
 * @data: Created in 2018/8/7 14:21
 * @modifier:
 */
@Entity//这是一个实体bean
@Table(name = "t_public_company_infoes")//映射到数据表
public class BidCompanyInfo implements Serializable{
    private static final long serialVersionUID = -2073488573055575251L;

    private Integer id;
    private String projectName;//采购项目名称
    private String projectCode;//采购项目编码
    private String purchaseWay;//采购方式
    private Date   openingTime;//开标时间
    private String tenderingCompany;//投标公司名称
    private String creditCode;//社会信用编码
    private String isItEligible;//是否符合投标条件:1.是;2:否
    private Integer parentId;           //导入日志关联id
    private String openingTimeStr;

    @Id//定义一个主键
    @Column(name = "id")//映射数据表中对应的字段名称
    @GeneratedValue(strategy = GenerationType.IDENTITY)//为实体生成唯一标识的主键并自增
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
    @Column(name = "project_name")
    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }
    @Column(name = "project_code")
    public String getProjectCode() {
        return projectCode;
    }

    public void setProjectCode(String projectCode) {
        this.projectCode = projectCode;
    }
    @Column(name = "purchase_way")
    public String getPurchaseWay() {
        return purchaseWay;
    }

    public void setPurchaseWay(String purchaseWay) {
        this.purchaseWay = purchaseWay;
    }
    @Column(name = "opening_time")
    public Date getOpeningTime() {
        return openingTime;
    }

    public void setOpeningTime(Date openingTime) {
        this.openingTime = openingTime;
    }
    @Column(name = "tendering_company")
    public String getTenderingCompany() {
        return tenderingCompany;
    }

    public void setTenderingCompany(String tenderingCompany) {
        this.tenderingCompany = tenderingCompany;
    }
    @Column(name = "credit_code")
    public String getCreditCode() {
        return creditCode;
    }

    public void setCreditCode(String creditCode) {
        this.creditCode = creditCode;
    }
    @Column(name = "is_it_eligible")
    public String getIsItEligible() {
        return isItEligible;
    }

    public void setIsItEligible(String isItEligible) {
        this.isItEligible = isItEligible;
    }
    @Column(name = "parent_id")
    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    public String getOpeningTimeStr() {
        return openingTimeStr;
    }

    public void setOpeningTimeStr(String openingTimeStr) {
        this.openingTimeStr = openingTimeStr;
    }

    @Override
    public String toString() {
        return "BidCompanyInfo{" +
                "id=" + id +
                ", projectName='" + projectName + '\'' +
                ", projectCode='" + projectCode + '\'' +
                ", purchaseWay='" + purchaseWay + '\'' +
                ", openingTime=" + openingTime +
                ", tenderingCompany='" + tenderingCompany + '\'' +
                ", creditCode='" + creditCode + '\'' +
                ", isItEligible='" + isItEligible + '\'' +
                '}';
    }
}
