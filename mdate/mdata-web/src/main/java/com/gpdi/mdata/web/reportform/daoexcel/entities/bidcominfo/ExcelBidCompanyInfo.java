package com.gpdi.mdata.web.reportform.daoexcel.entities.bidcominfo;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

/**
 * @author: WangXiaoGang
 * @data: Created in 2018/8/7 15:11
 * @description:公开采购投标公司信息
 */
public class ExcelBidCompanyInfo implements Serializable {

    private static final long serialVersionUID = 2748043798032612389L;

    private Integer id;
    private String projectName;//采购项目名称
    private String projectCode;//采购项目编码
    private String purchaseWay;//采购方式
    private String openingTime;//开标时间
    private String tenderingCompany;//投标公司名称
    private String creditCode;//社会信用编码
    private String isItEligible;//是否符合投标条件:1.是;2:否

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getProjectCode() {
        return projectCode;
    }

    public void setProjectCode(String projectCode) {
        this.projectCode = projectCode;
    }

    public String getPurchaseWay() {
        return purchaseWay;
    }

    public void setPurchaseWay(String purchaseWay) {
        this.purchaseWay = purchaseWay;
    }

    public String getOpeningTime() {
        return openingTime;
    }

    public void setOpeningTime(String openingTime) {
        this.openingTime = openingTime;
    }

    public String getTenderingCompany() {
        return tenderingCompany;
    }

    public void setTenderingCompany(String tenderingCompany) {
        this.tenderingCompany = tenderingCompany;
    }

    public String getCreditCode() {
        return creditCode;
    }

    public void setCreditCode(String creditCode) {
        this.creditCode = creditCode;
    }

    public String getIsItEligible() {
        return isItEligible;
    }

    public void setIsItEligible(String isItEligible) {
        this.isItEligible = isItEligible;
    }

    @Override
    public String toString() {
        return "ExcelBidCompanyInfo{" +
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