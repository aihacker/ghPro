package com.gpdi.mdata.sys.entity.report;

import javax.persistence.*;
import java.io.Serializable;
import java.text.DecimalFormat;
import java.util.Date;

/**
 * 及时清洁
 */
@Entity //这是一个实体bean
@Table(name = "t_clean_list_immediately")//指定映射表的表名
public class CleanInventory implements Serializable {
    /**
     * Java的序列化的机制通过判断serialVersionUID来验证版本的一致性。
     * 在反序列化的时候与本地的类的serialVersionUID进行比较，
     * 一致则可以进行反序列化，不一致则会抛出异常InvalidCastException
     */
    private static final long serialVersionUID = 1404287303904307420L;
    private Integer Id;             //id
    private String FlowPath ;       //流程
    private String  Title;          //标题
    private String  IscmNumber;     //ISCM编号
    private String  FlowPathNumber;//流程编号
    private String  CleanAdvance;   //是否即时清洁电商企业预付款
    private String  SupplierCode; //供应商编码

    private String  SupplierName; //供应商名称
    private String  MaterialCode;   //物料编码
    private String  MaterialName;  //物料名称
    private String  UnitMeasurement;   //计量单位
    private Double  TransactionPrice;   //成交单价(元)

    private Double  Turnover;    //成交数量
    private Double  TradingVolume;   //成交金额(元)
    private String  DraftUnit;    //拟稿单位

    private String  DraftPeople;   //拟稿人
    private String  DraftDepartment; //拟稿部门
    private String  PurchaseAmount;     //采购金额(万)
    private String  DraftTime;     //拟稿时间

    private String  ProcurementMethod;   //采购方式
    private String  WhetherOpenPurchasing; //是否在经过公开评选年度供应商范围内组织的采购
    private String  ProcurementClassification;     //采购分类
    private String  CapitalSource;     //资金来源

    private String  NewestTime;   //最新到达采购实施部门时间
    private String  ProcurementSubmitTime; //采购实施部门提交时间
    private String  CurrentState;     //当前状态
    private String  CurrentHandler;     //当前处理人
    private String  ProductServiceType;     //产品或服务类型

    //==================================================================================================================

    @Id //定义一个主键
    @Column(name = "id")//映射数据表中对应的那一列字段名
    @GeneratedValue(strategy = GenerationType.IDENTITY)//为实体生成唯一标识的主键并自增
    public Integer getId() {
        return Id;
    }

    public void setId(Integer id) {
        Id = id;
    }
    @Column(name = "flow_path")
    public String getFlowPath() {
        return FlowPath;
    }

    public void setFlowPath(String flowPath) {
        FlowPath = flowPath;
    }
    @Column(name = "title")
    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }
    @Column(name = "iscm_number")
    public String getIscmNumber() {
        return IscmNumber;
    }

    public void setIscmNumber(String iscmNumber) {
        IscmNumber = iscmNumber;
    }
    @Column(name = "flow_path_number")
    public String getFlowPathNumber() {
        return FlowPathNumber;
    }

    public void setFlowPathNumber(String flowPathNumber) {
        FlowPathNumber = flowPathNumber;
    }
    @Column(name = "clean_advance")
    public String getCleanAdvance() {
        return CleanAdvance;
    }

    public void setCleanAdvance(String cleanAdvance) {
        CleanAdvance = cleanAdvance;
    }
    @Column(name = "supplier_code")
    public String getSupplierCode() {
        return SupplierCode;
    }

    public void setSupplierCode(String supplierCode) {
        SupplierCode = supplierCode;
    }
    @Column(name = "supplier_name")
    public String getSupplierName() {
        return SupplierName;
    }

    public void setSupplierName(String supplierName) {
        SupplierName = supplierName;
    }
    @Column(name = "material_code")
    public String getMaterialCode() {
        return MaterialCode;
    }

    public void setMaterialCode(String materialCode) {
        MaterialCode = materialCode;
    }
    @Column(name = "material_name")
    public String getMaterialName() {
        return MaterialName;
    }

    public void setMaterialName(String materialName) {
        MaterialName = materialName;
    }
    @Column(name = "unit_measurement")
    public String getUnitMeasurement() {
        return UnitMeasurement;
    }

    public void setUnitMeasurement(String unitMeasurement) {
        UnitMeasurement = unitMeasurement;
    }
    @Column(name = "transaction_price")
    public Double getTransactionPrice() {
        return TransactionPrice;
    }

    public void setTransactionPrice(Double transactionPrice) {
        TransactionPrice = transactionPrice;
    }
    @Column(name = "turnover")
    public Double getTurnover() {
        return Turnover;
    }

    public void setTurnover(Double turnover) {
        Turnover = turnover;
    }
    @Column(name = "trading_volume")
    public Double getTradingVolume() {
        return TradingVolume;
    }

    public void setTradingVolume(Double tradingVolume) {
        TradingVolume = tradingVolume;
    }
    @Column(name = "draft_unit")
    public String getDraftUnit() {
        return DraftUnit;
    }

    public void setDraftUnit(String draftUnit) {
        DraftUnit = draftUnit;
    }
    @Column(name = "draft_people")
    public String getDraftPeople() {
        return DraftPeople;
    }

    public void setDraftPeople(String draftPeople) {
        DraftPeople = draftPeople;
    }
    @Column(name = "draft_department")
    public String getDraftDepartment() {
        return DraftDepartment;
    }

    public void setDraftDepartment(String draftDepartment) {
        DraftDepartment = draftDepartment;
    }
    @Column(name = "purchase_amount")
    public String getPurchaseAmount() {
        return PurchaseAmount;
    }

    public void setPurchaseAmount(String purchaseAmount) {
        PurchaseAmount = purchaseAmount;
    }
    @Column(name = "draft_time")
    public String getDraftTime() {
        return DraftTime;
    }

    public void setDraftTime(String draftTime) {
        DraftTime = draftTime;
    }
    @Column(name = "procurement_method")
    public String getProcurementMethod() {
        return ProcurementMethod;
    }

    public void setProcurementMethod(String procurementMethod) {
        ProcurementMethod = procurementMethod;
    }
    @Column(name = "whether_open_purchasing")
    public String getWhetherOpenPurchasing() {
        return WhetherOpenPurchasing;
    }

    public void setWhetherOpenPurchasing(String whetherOpenPurchasing) {
        WhetherOpenPurchasing = whetherOpenPurchasing;
    }
    @Column(name = "procurement_classification")
    public String getProcurementClassification() {
        return ProcurementClassification;
    }

    public void setProcurementClassification(String procurementClassification) {
        ProcurementClassification = procurementClassification;
    }
    @Column(name = "capital_source")
    public String getCapitalSource() {
        return CapitalSource;
    }

    public void setCapitalSource(String capitalSource) {
        CapitalSource = capitalSource;
    }
    @Column(name = "newest_time")
    public String getNewestTime() {
        return NewestTime;
    }

    public void setNewestTime(String newestTime) {
        NewestTime = newestTime;
    }
    @Column(name = "procurement_submit_time")
    public String getProcurementSubmitTime() {
        return ProcurementSubmitTime;
    }

    public void setProcurementSubmitTime(String procurementSubmitTime) {
        ProcurementSubmitTime = procurementSubmitTime;
    }
    @Column(name = "current_state")
    public String getCurrentState() {
        return CurrentState;
    }

    public void setCurrentState(String currentState) {
        CurrentState = currentState;
    }
    @Column(name = "current_handler")
    public String getCurrentHandler() {
        return CurrentHandler;
    }

    public void setCurrentHandler(String currentHandler) {
        CurrentHandler = currentHandler;
    }
    @Column(name = "product_service_type")
    public String getProductServiceType() {
        return ProductServiceType;
    }

    public void setProductServiceType(String productServiceType) {
        ProductServiceType = productServiceType;
    }

    @Override
    public String toString() {
        return "CleanInventory{" + "Id=" + Id + ", FlowPath='" + FlowPath + '\'' + ", Title='" + Title + '\'' + ", IscmNumber='" + IscmNumber + '\'' + ", FlowPathNumber='" + FlowPathNumber + '\'' + ", CleanAdvance='" + CleanAdvance + '\'' + ", SupplierCode='" + SupplierCode + '\'' + ", SupplierName='" + SupplierName + '\'' + ", MaterialCode='" + MaterialCode + '\'' + ", MaterialName='" + MaterialName + '\'' + ", UnitMeasurement='" + UnitMeasurement + '\'' + ", TransactionPrice=" + TransactionPrice + ", Turnover=" + Turnover + ", TradingVolume=" + TradingVolume + ", DraftUnit='" + DraftUnit + '\'' + ", DraftPeople='" + DraftPeople + '\'' + ", DraftDepartment='" + DraftDepartment + '\'' + ", PurchaseAmount=" + PurchaseAmount + ", DraftTime='" + DraftTime + '\'' + ", ProcurementMethod='" + ProcurementMethod + '\'' + ", WhetherOpenPurchasing='" + WhetherOpenPurchasing + '\'' + ", ProcurementClassification='" + ProcurementClassification + '\'' + ", CapitalSource='" + CapitalSource + '\'' + ", NewestTime='" + NewestTime + '\'' + ", ProcurementSubmitTime='" + ProcurementSubmitTime + '\'' + ", CurrentState='" + CurrentState + '\'' + ", CurrentHandler='" + CurrentHandler + '\'' + ", ProductServiceType='" + ProductServiceType + '\'' + '}';
    }
}
