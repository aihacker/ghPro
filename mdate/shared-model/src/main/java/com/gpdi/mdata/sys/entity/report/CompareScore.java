package com.gpdi.mdata.sys.entity.report;

import javax.persistence.Entity;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import java.io.Serializable;
import java.util.List;

/**
 * @description:对比得分临时表
 * @author: WangXiaoGang
 * @data: Created in 2018/8/23 20:14
 * @modifier:
 */
@Entity//声明这是一个实体类
public class CompareScore implements Serializable,Comparable<CompareScore>{
    private static final long serialVersionUID = -3203748582803176459L;

    private String contractName;//合同名称

    private double scroe;//得分

    private String purchaseTime;//定稿时间

    private String contractAmount;//合同金额

    private String purchaseWay;//采购方式

    private String undertakeDept;//经办部门

    private String supplierName;//供应商

    private String contractType;//合同类型

    private String contractCode;//合同编号

    private String purPlanCode;//合同方案号

    private String undertakeMan;//经办人

    private String  materialName;//物料名称

    public String getContractName() {
        return contractName;
    }

    public void setContractName(String contractName) {
        this.contractName = contractName;
    }

    public double getScroe() {
        return scroe;
    }

    public void setScroe(double scroe) {
        this.scroe = scroe;
    }

    public String getPurchaseTime() {
        return purchaseTime;
    }

    public void setPurchaseTime(String purchaseTime) {
        this.purchaseTime = purchaseTime;
    }

    public String getContractAmount() {
        return contractAmount;
    }

    public void setContractAmount(String contractAmount) {
        this.contractAmount = contractAmount;
    }

    public String getPurchaseWay() {
        return purchaseWay;
    }

    public void setPurchaseWay(String purchaseWay) {
        this.purchaseWay = purchaseWay;
    }

    public String getUndertakeDept() {
        return undertakeDept;
    }

    public void setUndertakeDept(String undertakeDept) {
        this.undertakeDept = undertakeDept;
    }

    public String getSupplierName() {
        return supplierName;
    }

    public void setSupplierName(String supplierName) {
        this.supplierName = supplierName;
    }

    public String getContractType() {
        return contractType;
    }

    public void setContractType(String contractType) {
        this.contractType = contractType;
    }

    public String getContractCode() {
        return contractCode;
    }

    public void setContractCode(String contractCode) {
        this.contractCode = contractCode;
    }

    public String getPurPlanCode() {
        return purPlanCode;
    }

    public void setPurPlanCode(String purPlanCode) {
        this.purPlanCode = purPlanCode;
    }

    public String getUndertakeMan() {
        return undertakeMan;
    }

    public void setUndertakeMan(String undertakeMan) {
        this.undertakeMan = undertakeMan;
    }

    public String getMaterialName() {
        return materialName;
    }

    public void setMaterialName(String materialName) {
        this.materialName = materialName;
    }

    @Override
    public String toString() {
        return "CompareScore{" +
                "contractName='" + contractName + '\'' +
                ", scroe=" + scroe +
                ", purchaseTime='" + purchaseTime + '\'' +
                ", contractAmount='" + contractAmount + '\'' +
                ", purchaseWay='" + purchaseWay + '\'' +
                ", undertakeDept='" + undertakeDept + '\'' +
                ", supplierName='" + supplierName + '\'' +
                ", contractType='" + contractType + '\'' +
                ", contractCode='" + contractCode + '\'' +
                ", purPlanCode='" + purPlanCode + '\'' +
                ", undertakeMan='" + undertakeMan + '\'' +
                ", materialName='" + materialName + '\'' +
                '}';
    }

    @Override
    public int compareTo(CompareScore o) {
        if(this.scroe>o.scroe)//score是private的，为什么能够直接调用,这是因为在Student类内部
            return -1;//由高到底排序
        else if(this.scroe<o.scroe)
            return 1;
        return 0;
    }



}
