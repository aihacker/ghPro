package com.gpdi.mdata.sys.entity.report;

import org.springframework.validation.support.BindingAwareModelMap;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * 采购合同表
 */
@Entity //这是一个实体bean
@Table(name = "t_purchase_contract")//指定映射表的表名
public class PurchaseContract implements Serializable {
    /**
     * Java的序列化的机制通过判断serialVersionUID来验证版本的一致性。
     * 在反序列化的时候与本地的类的serialVersionUID进行比较，
     * 一致则可以进行反序列化，不一致则会抛出异常InvalidCastException
     */
    private static final long serialVersionUID = 1404287303904307420L;
    private Integer Id;             //id
    private Integer PId ;           //父id
    private String  ContractCode;   //合同编号
    private Date    PurchaseTime;   //定稿时间
    private String  PurchaseTimeStr;
    private String  ContractName;   //合同名称
    private Double  ContractAmount; //合同金额  数据库字段类型用decimal,精度要求高可用BigDecimal
    private String  Amount; //合同金额  数据库字段类型用decimal,精度要求高可用BigDecimal
    private String  UndertakeOrg;   //承办单位
    private String  UndertakeDept;  //承办部门
    private String  UndertakeMan;   //承办人
    private String  SupplierName;   //对方名称
    private String  PurchaseWay;    //采购方式
    private String  PurchaseType;   //采购类型
    private String  CompanyCode;    //公司代码
    private String  PurchaseKind;   //采购种类
    private String  SetServiceType; //设备/服务类型
    private String  SetTypeLv1;     //设备类型第一层
    private String  SetTypeLv2;     //设备类型第二层
    private String  SignType;       //签约类型
    private String  IsCorrelationTrade;//是否关联交易
    private String  CorrelationTradeType;//关联交易类型
    private String  IsItcProject;   //是否itc项目
    private String  IsPivotalContract;  //是否属于关键合同
    private String  kong;  //空列值
    private String  ReceiptPayType;     //收付类型
    private String  ContractType;   //合同类型
    private String  ContractTypeCode;   //合同类型前面编号
    private String  ContractTypeName;   //合同类型后面中文
    private String  PurchaseResultCode;//采购结果编号
    private String  ContractProp;   //合同属性
    private Date    ArchiveDate;    //归档日期
    private String  ArchiveDateStr;
    private String  ContractStatus; //合同状态
    private String  PlanCode; //采购方案编号
    private Double  Percent;



    @Id //定义一个主键
    @Column(name = "id")//映射数据表中对应的那一列字段名
    @GeneratedValue(strategy = GenerationType.IDENTITY)//为实体生成唯一标识的主键并自增
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
    @Column(name = "purchase_time")
    public Date getPurchaseTime() {
        return PurchaseTime;
    }

    public void setPurchaseTime(Date purchaseTime) {
        PurchaseTime = purchaseTime;
    }
    @Column(name = "contract_name")
    public String getContractName() {
        return ContractName;
    }

    public void setContractName(String contractName) {
        ContractName = contractName;
    }
    @Column(name = "contract_amount")
    public Double getContractAmount() {
        return ContractAmount;
    }

    public void setContractAmount(Double contractAmount) {
        ContractAmount = contractAmount;
    }
    @Column(name = "undertake_org")
    public String getUndertakeOrg() {
        return UndertakeOrg;
    }

    public void setUndertakeOrg(String undertakeOrg) {
        UndertakeOrg = undertakeOrg;
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
    @Column(name = "supplier_name")
    public String getSupplierName() {
        return SupplierName;
    }

    public void setSupplierName(String supplierName) {
        SupplierName = supplierName;
    }
    @Column(name = "purchase_way")
    public String getPurchaseWay() {
        return PurchaseWay;
    }

    public void setPurchaseWay(String purchaseWay) {
        PurchaseWay = purchaseWay;
    }
    @Column(name = "purchase_type")
    public String getPurchaseType() {
        return PurchaseType;
    }

    public void setPurchaseType(String purchaseType) {
        PurchaseType = purchaseType;
    }
    @Column(name = "company_code")
    public String getCompanyCode() {
        return CompanyCode;
    }

    public void setCompanyCode(String companyCode) {
        CompanyCode = companyCode;
    }
    @Column(name = "purchase_kind")
    public String getPurchaseKind() {
        return PurchaseKind;
    }

    public void setPurchaseKind(String purchaseKind) {
        PurchaseKind = purchaseKind;
    }
    @Column(name = "set_service_type")
    public String getSetServiceType() {
        return SetServiceType;
    }

    public void setSetServiceType(String setServiceType) {
        SetServiceType = setServiceType;
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
    @Column(name = "sign_type")
    public String getSignType() {
        return SignType;
    }

    public void setSignType(String signType) {
        SignType = signType;
    }
    @Column(name = "is_correlation_trade")
    public String getIsCorrelationTrade() {
        return IsCorrelationTrade;
    }

    public void setIsCorrelationTrade(String isCorrelationTrade) {
        IsCorrelationTrade = isCorrelationTrade;
    }
    @Column(name = "correlation_trade_type")
    public String getCorrelationTradeType() {
        return CorrelationTradeType;
    }

    public void setCorrelationTradeType(String correlationTradeType) {
        CorrelationTradeType = correlationTradeType;
    }
    @Column(name = "is_itc_project")
    public String getIsItcProject() {
        return IsItcProject;
    }

    public void setIsItcProject(String isItcProject) {
        IsItcProject = isItcProject;
    }
    @Column(name = "is_pivotal_contract")
    public String getIsPivotalContract() {
        return IsPivotalContract;
    }

    public void setIsPivotalContract(String isPivotalContract) {
        IsPivotalContract = isPivotalContract;
    }
    @Column(name = "kong")
    public String getKong() {
        return kong;
    }

    public void setKong(String kong) {
        this.kong = kong;
    }
    @Column(name = "receipt_pay_type")
    public String getReceiptPayType() {
        return ReceiptPayType;
    }

    public void setReceiptPayType(String receiptPayType) {
        ReceiptPayType = receiptPayType;
    }
    @Column(name = "contract_type")
    public String getContractType() {
        return ContractType;
    }

    public void setContractType(String contractType) {
        ContractType = contractType;
    }
    @Column(name = "contract_type_code")
    public String getContractTypeCode() {
        return ContractTypeCode;
    }

    public void setContractTypeCode(String contractTypeCode) {
        ContractTypeCode = contractTypeCode;
    }
    @Column(name = "contract_type_name")
    public String getContractTypeName() {
        return ContractTypeName;
    }

    public void setContractTypeName(String contractTypeName) {
        ContractTypeName = contractTypeName;
    }
    @Column(name = "purchase_result_code")
    public String getPurchaseResultCode() {
        return PurchaseResultCode;
    }

    public void setPurchaseResultCode(String purchaseResultCode) {
        PurchaseResultCode = purchaseResultCode;
    }
    @Column(name = "contract_prop")
    public String getContractProp() {
        return ContractProp;
    }

    public void setContractProp(String contractProp) {
        ContractProp = contractProp;
    }
    @Column(name = "archive_date")
    public Date getArchiveDate() {
        return ArchiveDate;
    }

    public void setArchiveDate(Date archiveDate) {
        ArchiveDate = archiveDate;
    }
    @Column(name = "contract_status")
    public String getContractStatus() {
        return ContractStatus;
    }

    public void setContractStatus(String contractStatus) {
        ContractStatus = contractStatus;
    }
    @Column(name = "parent_id")
    public Integer getPId() {
        return PId;
    }

    public void setPId(Integer PId) {
        this.PId = PId;
    }

    public String getAmount() {
        return Amount;
    }

    public void setAmount(String amount) {
        Amount = amount;
    }

    public String getPurchaseTimeStr() {
        return PurchaseTimeStr;
    }

    public void setPurchaseTimeStr(String purchaseTimeStr) {
        PurchaseTimeStr = purchaseTimeStr;
    }

    public String getArchiveDateStr() {
        return ArchiveDateStr;
    }

    public void setArchiveDateStr(String archiveDateStr) {
        ArchiveDateStr = archiveDateStr;
    }

    public String getPlanCode() {
        return PlanCode;
    }

    public void setPlanCode(String planCode) {
        PlanCode = planCode;
    }

    public Double getPercent() {
        return Percent;
    }

    public void setPercent(Double percent) {
        Percent = percent;
    }

    @Override
    public String toString() {
        return "PurchaseContract{" + "Id=" + Id + ", PId=" + PId + ", ContractCode='" + ContractCode + '\'' + ", PurchaseTime=" + PurchaseTime + ", PurchaseTimeStr='" + PurchaseTimeStr + '\'' + ", ContractName='" + ContractName + '\'' + ", ContractAmount=" + ContractAmount + ", Amount='" + Amount + '\'' + ", UndertakeOrg='" + UndertakeOrg + '\'' + ", UndertakeDept='" + UndertakeDept + '\'' + ", UndertakeMan='" + UndertakeMan + '\'' + ", SupplierName='" + SupplierName + '\'' + ", PurchaseWay='" + PurchaseWay + '\'' + ", PurchaseType='" + PurchaseType + '\'' + ", CompanyCode='" + CompanyCode + '\'' + ", PurchaseKind='" + PurchaseKind + '\'' + ", SetServiceType='" + SetServiceType + '\'' + ", SetTypeLv1='" + SetTypeLv1 + '\'' + ", SetTypeLv2='" + SetTypeLv2 + '\'' + ", SignType='" + SignType + '\'' + ", IsCorrelationTrade='" + IsCorrelationTrade + '\'' + ", CorrelationTradeType='" + CorrelationTradeType + '\'' + ", IsItcProject='" + IsItcProject + '\'' + ", IsPivotalContract='" + IsPivotalContract + '\'' + ", kong='" + kong + '\'' + ", ReceiptPayType='" + ReceiptPayType + '\'' + ", ContractType='" + ContractType + '\'' + ", ContractTypeCode='" + ContractTypeCode + '\'' + ", ContractTypeName='" + ContractTypeName + '\'' + ", PurchaseResultCode='" + PurchaseResultCode + '\'' + ", ContractProp='" + ContractProp + '\'' + ", ArchiveDate=" + ArchiveDate + ", ArchiveDateStr='" + ArchiveDateStr + '\'' + ", ContractStatus='" + ContractStatus + '\'' + ", PlanCode='" + PlanCode + '\'' + ", Percent=" + Percent + '}';
    }
}
