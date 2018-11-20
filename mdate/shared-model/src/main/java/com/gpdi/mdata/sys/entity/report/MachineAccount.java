package com.gpdi.mdata.sys.entity.report;

import jdk.internal.org.objectweb.asm.commons.SerialVersionUIDAdder;

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
public class MachineAccount implements Serializable {

    private static final long serialVersionUID = -8870858742092583439L;
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
    private Integer parentId;           //导入日志关联id

    private String ContractAmountStr;
    private String draftDateStr;
    private String FinalizeDateStr;
    private String StampDateStr;
    private String PerformanceBeginStr;
    private String PerformanceEndStr;

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Integer getId() {
        return Id;
    }

    public void setId(Integer id) {
        Id = id;
    }
    @Column(name = "contract_code")
    public String getContractCode() {
        return ContractCode;
    }

    public void setContractCode(String contractCode) {
        ContractCode = contractCode;
    }
    @Column(name = "contract_name")
    public String getContractName() {
        return ContractName;
    }

    public void setContractName(String contractName) {
        ContractName = contractName;
    }
    @Column(name = "commany_name")
    public String getCommanyName() {
        return CommanyName;
    }

    public void setCommanyName(String commanyName) {
        CommanyName = commanyName;
    }
    @Column(name = "contract_type")
    public String getContractType() {
        return ContractType;
    }

    public void setContractType(String contractType) {
        ContractType = contractType;
    }
    @Column(name = "receipt_pay_type")
    public String getReceiptPayType() {
        return ReceiptPayType;
    }

    public void setReceiptPayType(String receiptPayType) {
        ReceiptPayType = receiptPayType;
    }
    @Column(name = "contract_amount")
    public Double getContractAmount() {
        return ContractAmount;
    }

    public void setContractAmount(Double contractAmount) {
        ContractAmount = contractAmount;
    }
    @Column(name = "contract_status")
    public String getContractStatus() {
        return ContractStatus;
    }

    public void setContractStatus(String contractStatus) {
        ContractStatus = contractStatus;
    }
    @Column(name = "undertake_dept")
    public String getUndertakeDept() {
        return UndertakeDept;
    }

    public void setUndertakeDept(String undertakeDept) {
        UndertakeDept = undertakeDept;
    }
    @Column(name = "undertake_man")
    public String getUndertakeMan() {
        return UndertakeMan;
    }

    public void setUndertakeMan(String undertakeMan) {
        UndertakeMan = undertakeMan;
    }
    @Column(name = "current_step")
    public String getCurrentStep() {
        return CurrentStep;
    }

    public void setCurrentStep(String currentStep) {
        CurrentStep = currentStep;
    }
    @Column(name = "sign_basis")
    public String getSignBasis() {
        return SignBasis;
    }

    public void setSignBasis(String signBasis) {
        SignBasis = signBasis;
    }
    @Column(name = "is_busi_outsourcing")
    public String getIsBusiOutsourcing() {
        return IsBusiOutsourcing;
    }

    public void setIsBusiOutsourcing(String isBusiOutsourcing) {
        IsBusiOutsourcing = isBusiOutsourcing;
    }
    @Column(name = "is_itc_project")
    public String getIsItcProject() {
        return IsItcProject;
    }

    public void setIsItcProject(String isItcProject) {
        IsItcProject = isItcProject;
    }
    @Column(name = "draft_date")
    public Date getDraftDate() {
        return draftDate;
    }

    public void setDraftDate(Date draftDate) {
        this.draftDate = draftDate;
    }
    @Column(name = "finalize_date")
    public Date getFinalizeDate() {
        return FinalizeDate;
    }

    public void setFinalizeDate(Date finalizeDate) {
        FinalizeDate = finalizeDate;
    }
    @Column(name = "stamp_date")
    public Date getStampDate() {
        return StampDate;
    }

    public void setStampDate(Date stampDate) {
        StampDate = stampDate;
    }
    @Column(name = "sign_type")
    public String getSignType() {
        return SignType;
    }

    public void setSignType(String signType) {
        SignType = signType;
    }
    @Column(name = "is_correlation_contract")
    public String getIsCorrelationContract() {
        return IsCorrelationContract;
    }

    public void setIsCorrelationContract(String isCorrelationContract) {
        IsCorrelationContract = isCorrelationContract;
    }
    @Column(name = "correlation_contract_info")
    public String getCorrelationContractInfo() {
        return CorrelationContractInfo;
    }

    public void setCorrelationContractInfo(String correlationContractInfo) {
        CorrelationContractInfo = correlationContractInfo;
    }
    @Column(name = "is_cooperate_shared")
    public String getIsCooperateShared() {
        return IsCooperateShared;
    }

    public void setIsCooperateShared(String isCooperateShared) {
        IsCooperateShared = isCooperateShared;
    }
    @Column(name = "supplier_name")
    public String getSupplierName() {
        return SupplierName;
    }

    public void setSupplierName(String supplierName) {
        SupplierName = supplierName;
    }
    @Column(name = "is_correlation_trade")
    public String getIsCorrelationTrade() {
        return IsCorrelationTrade;
    }

    public void setIsCorrelationTrade(String isCorrelationTrade) {
        IsCorrelationTrade = isCorrelationTrade;
    }
    @Column(name = "is_correlation_trade_type")
    public String getIsCorrelationTradeType() {
        return IsCorrelationTradeType;
    }

    public void setIsCorrelationTradeType(String isCorrelationTradeType) {
        IsCorrelationTradeType = isCorrelationTradeType;
    }
    @Column(name = "is_original_suppliey")
    public String getIsOriginalSuppliey() {
        return IsOriginalSuppliey;
    }

    public void setIsOriginalSuppliey(String isOriginalSuppliey) {
        IsOriginalSuppliey = isOriginalSuppliey;
    }
    @Column(name = "suppliey_legal_rep")
    public String getSupplieyLegalRep() {
        return SupplieyLegalRep;
    }

    public void setSupplieyLegalRep(String supplieyLegalRep) {
        SupplieyLegalRep = supplieyLegalRep;
    }
    @Column(name = "performance_begin")
    public Date getPerformanceBegin() {
        return PerformanceBegin;
    }

    public void setPerformanceBegin(Date performanceBegin) {
        PerformanceBegin = performanceBegin;
    }
    @Column(name = "performance_end")
    public Date getPerformanceEnd() {
        return PerformanceEnd;
    }

    public void setPerformanceEnd(Date performanceEnd) {
        PerformanceEnd = performanceEnd;
    }
    @Column(name = "purchase_type")
    public String getPurchaseType() {
        return PurchaseType;
    }

    public void setPurchaseType(String purchaseType) {
        PurchaseType = purchaseType;
    }
    @Column(name = "set_service_type")
    public String getSetServiceType() {
        return SetServiceType;
    }

    public void setSetServiceType(String setServiceType) {
        SetServiceType = setServiceType;
    }
    @Column(name = "purchase_way")
    public String getPurchaseWay() {
        return PurchaseWay;
    }

    public void setPurchaseWay(String purchaseWay) {
        PurchaseWay = purchaseWay;
    }
    @Column(name = "purchase_kind")
    public String getPurchaseKind() {
        return PurchaseKind;
    }

    public void setPurchaseKind(String purchaseKind) {
        PurchaseKind = purchaseKind;
    }
    @Column(name = "set_type_lv1")
    public String getSetTypeLv1() {
        return SetTypeLv1;
    }

    public void setSetTypeLv1(String setTypeLv1) {
        SetTypeLv1 = setTypeLv1;
    }
    @Column(name = "set_type_lv2")
    public String getSetTypeLv2() {
        return SetTypeLv2;
    }

    public void setSetTypeLv2(String setTypeLv2) {
        SetTypeLv2 = setTypeLv2;
    }
    @Column(name = "contract_prop")
    public String getContractProp() {
        return ContractProp;
    }

    public void setContractProp(String contractProp) {
        ContractProp = contractProp;
    }
    @Column(name = "fee_prop")
    public String getFeeProp() {
        return FeeProp;
    }

    public void setFeeProp(String feeProp) {
        FeeProp = feeProp;
    }
    @Column(name = "parent_id")
    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    public String getContractAmountStr() {
        return ContractAmountStr;
    }

    public void setContractAmountStr(String contractAmountStr) {
        ContractAmountStr = contractAmountStr;
    }

    public String getDraftDateStr() {
        return draftDateStr;
    }

    public void setDraftDateStr(String draftDateStr) {
        this.draftDateStr = draftDateStr;
    }

    public String getFinalizeDateStr() {
        return FinalizeDateStr;
    }

    public void setFinalizeDateStr(String finalizeDateStr) {
        FinalizeDateStr = finalizeDateStr;
    }

    public String getStampDateStr() {
        return StampDateStr;
    }

    public void setStampDateStr(String stampDateStr) {
        StampDateStr = stampDateStr;
    }

    public String getPerformanceBeginStr() {
        return PerformanceBeginStr;
    }

    public void setPerformanceBeginStr(String performanceBeginStr) {
        PerformanceBeginStr = performanceBeginStr;
    }

    public String getPerformanceEndStr() {
        return PerformanceEndStr;
    }

    public void setPerformanceEndStr(String performanceEndStr) {
        PerformanceEndStr = performanceEndStr;
    }

    @Override
    public String toString() {
        return "MachineAccount{" +
                "Id=" + Id +
                ", ContractCode='" + ContractCode + '\'' +
                ", ContractName='" + ContractName + '\'' +
                ", CommanyName='" + CommanyName + '\'' +
                ", ContractType='" + ContractType + '\'' +
                ", ReceiptPayType='" + ReceiptPayType + '\'' +
                ", ContractAmount=" + ContractAmount +
                ", ContractStatus='" + ContractStatus + '\'' +
                ", UndertakeDept='" + UndertakeDept + '\'' +
                ", UndertakeMan='" + UndertakeMan + '\'' +
                ", CurrentStep='" + CurrentStep + '\'' +
                ", SignBasis='" + SignBasis + '\'' +
                ", IsBusiOutsourcing='" + IsBusiOutsourcing + '\'' +
                ", IsItcProject='" + IsItcProject + '\'' +
                ", draftDate=" + draftDate +
                ", FinalizeDate=" + FinalizeDate +
                ", StampDate=" + StampDate +
                ", SignType='" + SignType + '\'' +
                ", IsCorrelationContract='" + IsCorrelationContract + '\'' +
                ", CorrelationContractInfo='" + CorrelationContractInfo + '\'' +
                ", IsCooperateShared='" + IsCooperateShared + '\'' +
                ", SupplierName='" + SupplierName + '\'' +
                ", IsCorrelationTrade='" + IsCorrelationTrade + '\'' +
                ", IsCorrelationTradeType='" + IsCorrelationTradeType + '\'' +
                ", IsOriginalSuppliey='" + IsOriginalSuppliey + '\'' +
                ", SupplieyLegalRep='" + SupplieyLegalRep + '\'' +
                ", PerformanceBegin=" + PerformanceBegin +
                ", PerformanceEnd=" + PerformanceEnd +
                ", PurchaseType='" + PurchaseType + '\'' +
                ", SetServiceType='" + SetServiceType + '\'' +
                ", PurchaseWay='" + PurchaseWay + '\'' +
                ", PurchaseKind='" + PurchaseKind + '\'' +
                ", SetTypeLv1='" + SetTypeLv1 + '\'' +
                ", SetTypeLv2='" + SetTypeLv2 + '\'' +
                ", ContractProp='" + ContractProp + '\'' +
                ", FeeProp='" + FeeProp + '\'' +
                '}';
    }
}
