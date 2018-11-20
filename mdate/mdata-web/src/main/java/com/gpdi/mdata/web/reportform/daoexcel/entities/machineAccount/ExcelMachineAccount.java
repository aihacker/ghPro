package com.gpdi.mdata.web.reportform.daoexcel.entities.machineAccount;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * @author: WangXiaoGang
 * @data: Created in 2018/8/8 10:06
 * @description:合同台账表
 */
@Entity
@Table(name = "t_machine_account")
public class ExcelMachineAccount implements Serializable {

    private static final long serialVersionUID = 1027392073214885061L;
    private Integer Id;               //台账表id
    private String  ContractCode;      //合同编号
    private String  ContractName;      //合同名称
    private String  CommanyName;       //我方名称
    private String  ContractType;      //合同类型
    private String  ReceiptPayType;    //收付类型
    private Double  ContractAmount;    //合同金额
    private String  ContractStatus;    //合同状态
    private String  UndertakeDept;     //承办部门
    private String  UndertakeMan;      //承办人
    private String  CurrentStep;       //当前办理步骤
    private String  SignBasis;         //签约依据
    private String  IsBusiOutsourcing; //是否业务外包合同
    private String  IsItcProject;      //是否itc项目
    private Date    draftDate;        //拟稿日期
    private Date    FinalizeDate;      //定稿日期
    private Date    StampDate;         //盖章日期
    private String  SignType;          //签约类型
    private String  IsCorrelationContract;//是否关联合同
    private String  CorrelationContractInfo;//关联合同信息
    private String  IsCooperateShared;      //是否合作分成
    private String  SupplierName;           //对方名称
    private String  IsCorrelationTrade;     //是否关联交易
    private String  IsCorrelationTradeType;     //关联交易类型
    private String  IsOriginalSuppliey;         //是否原合同方
    private String  SupplieyLegalRep;          //对方法人代表
    private Date    PerformanceBegin;         //履行期限起
    private Date    PerformanceEnd;            //履行期限止
    private String  PurchaseType;           //采购类型
    private String  SetServiceType;         //设备服务类型
    private String  PurchaseWay;            //采购方式
    private String  PurchaseKind;           //采购种类
    private String  SetTypeLv1;             //设备类型第一层
    private String  SetTypeLv2;             //设备类型第二层
    private String  ContractProp;           //合同属性
    private String  FeeProp;                //费用属性


    public Integer getId() {
        return Id;
    }

    public void setId(Integer id) {
        Id = id;
    }

    public String getContractCode() {
        return ContractCode;
    }

    public void setContractCode(String contractCode) {
        ContractCode = contractCode;
    }

    public String getContractName() {
        return ContractName;
    }

    public void setContractName(String contractName) {
        ContractName = contractName;
    }

    public String getCommanyName() {
        return CommanyName;
    }

    public void setCommanyName(String commanyName) {
        CommanyName = commanyName;
    }

    public String getContractType() {
        return ContractType;
    }

    public void setContractType(String contractType) {
        ContractType = contractType;
    }

    public String getReceiptPayType() {
        return ReceiptPayType;
    }

    public void setReceiptPayType(String receiptPayType) {
        ReceiptPayType = receiptPayType;
    }

    public Double getContractAmount() {
        return ContractAmount;
    }

    public void setContractAmount(Double contractAmount) {
        ContractAmount = contractAmount;
    }

    public String getContractStatus() {
        return ContractStatus;
    }

    public void setContractStatus(String contractStatus) {
        ContractStatus = contractStatus;
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

    public String getCurrentStep() {
        return CurrentStep;
    }

    public void setCurrentStep(String currentStep) {
        CurrentStep = currentStep;
    }

    public String getSignBasis() {
        return SignBasis;
    }

    public void setSignBasis(String signBasis) {
        SignBasis = signBasis;
    }

    public String getIsBusiOutsourcing() {
        return IsBusiOutsourcing;
    }

    public void setIsBusiOutsourcing(String isBusiOutsourcing) {
        IsBusiOutsourcing = isBusiOutsourcing;
    }

    public String getIsItcProject() {
        return IsItcProject;
    }

    public void setIsItcProject(String isItcProject) {
        IsItcProject = isItcProject;
    }

    public Date getDraftDate() {
        return draftDate;
    }

    public void setDraftDate(Date draftDate) {
        this.draftDate = draftDate;
    }

    public Date getFinalizeDate() {
        return FinalizeDate;
    }

    public void setFinalizeDate(Date finalizeDate) {
        FinalizeDate = finalizeDate;
    }

    public Date getStampDate() {
        return StampDate;
    }

    public void setStampDate(Date stampDate) {
        StampDate = stampDate;
    }

    public String getSignType() {
        return SignType;
    }

    public void setSignType(String signType) {
        SignType = signType;
    }

    public String getIsCorrelationContract() {
        return IsCorrelationContract;
    }

    public void setIsCorrelationContract(String isCorrelationContract) {
        IsCorrelationContract = isCorrelationContract;
    }

    public String getCorrelationContractInfo() {
        return CorrelationContractInfo;
    }

    public void setCorrelationContractInfo(String correlationContractInfo) {
        CorrelationContractInfo = correlationContractInfo;
    }

    public String getIsCooperateShared() {
        return IsCooperateShared;
    }

    public void setIsCooperateShared(String isCooperateShared) {
        IsCooperateShared = isCooperateShared;
    }

    public String getSupplierName() {
        return SupplierName;
    }

    public void setSupplierName(String supplierName) {
        SupplierName = supplierName;
    }

    public String getIsCorrelationTrade() {
        return IsCorrelationTrade;
    }

    public void setIsCorrelationTrade(String isCorrelationTrade) {
        IsCorrelationTrade = isCorrelationTrade;
    }

    public String getIsCorrelationTradeType() {
        return IsCorrelationTradeType;
    }

    public void setIsCorrelationTradeType(String isCorrelationTradeType) {
        IsCorrelationTradeType = isCorrelationTradeType;
    }

    public String getIsOriginalSuppliey() {
        return IsOriginalSuppliey;
    }

    public void setIsOriginalSuppliey(String isOriginalSuppliey) {
        IsOriginalSuppliey = isOriginalSuppliey;
    }

    public String getSupplieyLegalRep() {
        return SupplieyLegalRep;
    }

    public void setSupplieyLegalRep(String supplieyLegalRep) {
        SupplieyLegalRep = supplieyLegalRep;
    }

    public Date getPerformanceBegin() {
        return PerformanceBegin;
    }

    public void setPerformanceBegin(Date performanceBegin) {
        PerformanceBegin = performanceBegin;
    }

    public Date getPerformanceEnd() {
        return PerformanceEnd;
    }

    public void setPerformanceEnd(Date performanceEnd) {
        PerformanceEnd = performanceEnd;
    }

    public String getPurchaseType() {
        return PurchaseType;
    }

    public void setPurchaseType(String purchaseType) {
        PurchaseType = purchaseType;
    }

    public String getSetServiceType() {
        return SetServiceType;
    }

    public void setSetServiceType(String setServiceType) {
        SetServiceType = setServiceType;
    }

    public String getPurchaseWay() {
        return PurchaseWay;
    }

    public void setPurchaseWay(String purchaseWay) {
        PurchaseWay = purchaseWay;
    }

    public String getPurchaseKind() {
        return PurchaseKind;
    }

    public void setPurchaseKind(String purchaseKind) {
        PurchaseKind = purchaseKind;
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

    public String getContractProp() {
        return ContractProp;
    }

    public void setContractProp(String contractProp) {
        ContractProp = contractProp;
    }

    public String getFeeProp() {
        return FeeProp;
    }

    public void setFeeProp(String feeProp) {
        FeeProp = feeProp;
    }
}
