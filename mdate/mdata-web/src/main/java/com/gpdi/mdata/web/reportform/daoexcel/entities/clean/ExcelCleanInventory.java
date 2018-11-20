package com.gpdi.mdata.web.reportform.daoexcel.entities.clean;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

/**
 * @description:
 * @author:
 * @data: Created in 2018/6/28 9:16
 * @modifier:
 */
@Entity //这是一个实体bean
@Table(name = "t_clean_list_immediately")//指定映射表的表名
public class ExcelCleanInventory implements Serializable {
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
    private Double  PurchaseAmount;     //采购金额(万)
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

    public String getFlowPath() {
        return FlowPath;
    }

    public void setFlowPath(String flowPath) {
        FlowPath = flowPath;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getIscmNumber() {
        return IscmNumber;
    }

    public void setIscmNumber(String iscmNumber) {
        IscmNumber = iscmNumber;
    }

    public String getFlowPathNumber() {
        return FlowPathNumber;
    }

    public void setFlowPathNumber(String flowPathNumber) {
        FlowPathNumber = flowPathNumber;
    }

    public String getCleanAdvance() {
        return CleanAdvance;
    }

    public void setCleanAdvance(String cleanAdvance) {
        CleanAdvance = cleanAdvance;
    }

    public String getSupplierCode() {
        return SupplierCode;
    }

    public void setSupplierCode(String supplierCode) {
        SupplierCode = supplierCode;
    }

    public String getSupplierName() {
        return SupplierName;
    }

    public void setSupplierName(String supplierName) {
        SupplierName = supplierName;
    }

    public String getMaterialCode() {
        return MaterialCode;
    }

    public void setMaterialCode(String materialCode) {
        MaterialCode = materialCode;
    }

    public String getMaterialName() {
        return MaterialName;
    }

    public void setMaterialName(String materialName) {
        MaterialName = materialName;
    }

    public String getUnitMeasurement() {
        return UnitMeasurement;
    }

    public void setUnitMeasurement(String unitMeasurement) {
        UnitMeasurement = unitMeasurement;
    }

    public Double getTransactionPrice() {
        return TransactionPrice;
    }

    public void setTransactionPrice(Double transactionPrice) {
        TransactionPrice = transactionPrice;
    }

    public Double getTurnover() {
        return Turnover;
    }

    public void setTurnover(Double turnover) {
        Turnover = turnover;
    }

    public Double getTradingVolume() {
        return TradingVolume;
    }

    public void setTradingVolume(Double tradingVolume) {
        TradingVolume = tradingVolume;
    }

    public String getDraftUnit() {
        return DraftUnit;
    }

    public void setDraftUnit(String draftUnit) {
        DraftUnit = draftUnit;
    }

    public String getDraftPeople() {
        return DraftPeople;
    }

    public void setDraftPeople(String draftPeople) {
        DraftPeople = draftPeople;
    }

    public String getDraftDepartment() {
        return DraftDepartment;
    }

    public void setDraftDepartment(String draftDepartment) {
        DraftDepartment = draftDepartment;
    }

    public Double getPurchaseAmount() {
        return PurchaseAmount;
    }

    public void setPurchaseAmount(Double purchaseAmount) {
        PurchaseAmount = purchaseAmount;
    }

    public String getDraftTime() {
        return DraftTime;
    }

    public void setDraftTime(String draftTime) {
        DraftTime = draftTime;
    }

    public String getProcurementMethod() {
        return ProcurementMethod;
    }

    public void setProcurementMethod(String procurementMethod) {
        ProcurementMethod = procurementMethod;
    }

    public String getWhetherOpenPurchasing() {
        return WhetherOpenPurchasing;
    }

    public void setWhetherOpenPurchasing(String whetherOpenPurchasing) {
        WhetherOpenPurchasing = whetherOpenPurchasing;
    }

    public String getProcurementClassification() {
        return ProcurementClassification;
    }

    public void setProcurementClassification(String procurementClassification) {
        ProcurementClassification = procurementClassification;
    }

    public String getCapitalSource() {
        return CapitalSource;
    }

    public void setCapitalSource(String capitalSource) {
        CapitalSource = capitalSource;
    }

    public String getNewestTime() {
        return NewestTime;
    }

    public void setNewestTime(String newestTime) {
        NewestTime = newestTime;
    }

    public String getProcurementSubmitTime() {
        return ProcurementSubmitTime;
    }

    public void setProcurementSubmitTime(String procurementSubmitTime) {
        ProcurementSubmitTime = procurementSubmitTime;
    }

    public String getCurrentState() {
        return CurrentState;
    }

    public void setCurrentState(String currentState) {
        CurrentState = currentState;
    }

    public String getCurrentHandler() {
        return CurrentHandler;
    }

    public void setCurrentHandler(String currentHandler) {
        CurrentHandler = currentHandler;
    }

    public String getProductServiceType() {
        return ProductServiceType;
    }

    public void setProductServiceType(String productServiceType) {
        ProductServiceType = productServiceType;
    }
}