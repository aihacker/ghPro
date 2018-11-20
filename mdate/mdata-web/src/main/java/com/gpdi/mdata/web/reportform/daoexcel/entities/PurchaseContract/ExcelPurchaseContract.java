package com.gpdi.mdata.web.reportform.daoexcel.entities.PurchaseContract;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

/**
 * @description:
 * @author: WangXiaoGang
 * @data: Created in 2018/6/28 9:16
 * @modifier:
 */
@Entity //这是一个实体bean
@Table(name = "t_purchase_contract")//指定映射表的表名
public class ExcelPurchaseContract implements Serializable {
    private String ContractCode;   //合同编号
    private Date PurchaseTime;   //定稿时间
    private String ContractName;   //合同名称
    private Double ContractAmount; //合同金额  数据库字段类型用decimal,精度要求高可用BigDecimal
    private String UndertakeOrg;   //承办单位
    private String UndertakeDept;  //承办部门
    private String UndertakeMan;   //承办人
    private String SupplierName;   //对方名称
    private String PurchaseWay;    //采购方式
    private String PurchaseType;   //采购类型
    private String CompanyCode;    //公司代码
    private String PurchaseKind;   //采购种类
    private String SetServiceType; //设备/服务类型
    private String SetTypeLv1;     //设备类型第一层
    private String SetTypeLv2;     //设备类型第二层
    private String SignType;       //签约类型
    private String IsCorrelationTrade;//是否关联交易
    private String CorrelationTradeType;//关联交易类型
    private String IsItcProject;   //是否itc项目
    private String IsPivotalContract;  //是否属于关键合同
    private String Kong;  //空列
    private String ReceiptPayType;     //收付类型
    private String ContractType;   //合同类型
    private String ContractTypeCode;   //合同类型前面的编号
    private String ContractTypeName;   //合同类型后面的中文
    private String PurchaseResultCode;//采购结果编号
    private String ContractProp;   //合同属性
    private Date ArchiveDate;    //归档日期
    private String ContractStatus; //合同状态

    public String getContractCode() {
        return ContractCode;
    }

    public void setContractCode(String contractCode) {
        ContractCode = contractCode;
    }

    public Date getPurchaseTime() {
        return PurchaseTime;
    }

    public void setPurchaseTime(Date purchaseTime) {
        PurchaseTime = purchaseTime;
    }

    public String getContractName() {
        return ContractName;
    }

    public void setContractName(String contractName) {
        ContractName = contractName;
    }

    public Double getContractAmount() {
        return ContractAmount;
    }

    public void setContractAmount(Double contractAmount) {
        ContractAmount = contractAmount;
    }

    public String getUndertakeOrg() {
        return UndertakeOrg;
    }

    public void setUndertakeOrg(String undertakeOrg) {
        UndertakeOrg = undertakeOrg;
    }

    public String getUndertakeDept() {
        return UndertakeDept;
    }

    public void setUndertakeDept(String undertakeDept) {
        UndertakeDept = undertakeDept;
    }

    public String getUndertakeMan() {
        return UndertakeMan;
    }

    public void setUndertakeMan(String undertakeMan) {
        UndertakeMan = undertakeMan;
    }

    public String getSupplierName() {
        return SupplierName;
    }

    public void setSupplierName(String supplierName) {
        SupplierName = supplierName;
    }

    public String getPurchaseWay() {
        return PurchaseWay;
    }

    public void setPurchaseWay(String purchaseWay) {
        PurchaseWay = purchaseWay;
    }

    public String getPurchaseType() {
        return PurchaseType;
    }

    public void setPurchaseType(String purchaseType) {
        PurchaseType = purchaseType;
    }

    public String getCompanyCode() {
        return CompanyCode;
    }

    public void setCompanyCode(String companyCode) {
        CompanyCode = companyCode;
    }

    public String getPurchaseKind() {
        return PurchaseKind;
    }

    public void setPurchaseKind(String purchaseKind) {
        PurchaseKind = purchaseKind;
    }

    public String getSetServiceType() {
        return SetServiceType;
    }

    public void setSetServiceType(String setServiceType) {
        SetServiceType = setServiceType;
    }

    public String getSetTypeLv1() {
        return SetTypeLv1;
    }

    public void setSetTypeLv1(String setTypeLv1) {
        SetTypeLv1 = setTypeLv1;
    }

    public String getSetTypeLv2() {
        return SetTypeLv2;
    }

    public void setSetTypeLv2(String setTypeLv2) {
        SetTypeLv2 = setTypeLv2;
    }

    public String getSignType() {
        return SignType;
    }

    public void setSignType(String signType) {
        SignType = signType;
    }

    public String getIsCorrelationTrade() {
        return IsCorrelationTrade;
    }

    public void setIsCorrelationTrade(String isCorrelationTrade) {
        IsCorrelationTrade = isCorrelationTrade;
    }

    public String getCorrelationTradeType() {
        return CorrelationTradeType;
    }

    public void setCorrelationTradeType(String correlationTradeType) {
        CorrelationTradeType = correlationTradeType;
    }

    public String getIsItcProject() {
        return IsItcProject;
    }

    public void setIsItcProject(String isItcProject) {
        IsItcProject = isItcProject;
    }

    public String getIsPivotalContract() {
        return IsPivotalContract;
    }

    public void setIsPivotalContract(String isPivotalContract) {
        IsPivotalContract = isPivotalContract;
    }

    public String getReceiptPayType() {
        return ReceiptPayType;
    }

    public void setReceiptPayType(String receiptPayType) {
        ReceiptPayType = receiptPayType;
    }

    public String getContractType() {
        return ContractType;
    }

    public void setContractType(String contractType) {
        ContractType = contractType;
    }

    public String getPurchaseResultCode() {
        return PurchaseResultCode;
    }

    public void setPurchaseResultCode(String purchaseResultCode) {
        PurchaseResultCode = purchaseResultCode;
    }

    public String getContractProp() {
        return ContractProp;
    }

    public void setContractProp(String contractProp) {
        ContractProp = contractProp;
    }

    public Date getArchiveDate() {
        return ArchiveDate;
    }

    public void setArchiveDate(Date archiveDate) {
        ArchiveDate = archiveDate;
    }

    public String getContractStatus() {
        return ContractStatus;
    }

    public void setContractStatus(String contractStatus) {
        ContractStatus = contractStatus;
    }

    public String getKong() {
        return Kong;
    }

    public void setKong(String kong) {
        Kong = kong;
    }

    public String getContractTypeCode() {
        return ContractTypeCode;
    }

    public void setContractTypeCode(String contractTypeCode) {
        ContractTypeCode = contractTypeCode;
    }

    public String getContractTypeName() {
        return ContractTypeName;
    }

    public void setContractTypeName(String contractTypeName) {
        ContractTypeName = contractTypeName;
    }
}