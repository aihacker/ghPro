package com.gpdi.mdata.sys.entity.report;

import org.springframework.transaction.annotation.Transactional;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

/**
 * @description:拆单临时存储表
 * @author: WangXiaoGang
 * @data: Created in 2018/8/23 11:04
 * @modifier:
 */

@Entity//声明这是一个实体类
public class DismantTemp implements Serializable ,Comparable<DismantTemp>{

    private static final long serialVersionUID = -737451245656768675L;

    private String id;
    private String undertakeDept;//经办部门
    private String undertakeMan;//拟稿人/经办人
    private String draft_department;//经办单位
    private String purchase_amount;//采购金额
    private String supplierName;//供应商
    private String contractTypeName;//合同类型
    private Integer count;//合同数量
    private String  contractName;//合同名称
    private String  contractTime;//合同时间
    private String  contractAmount;//合同金额
    private String  purchaseWay;//采购方式
    private String  materialName;//物料名称
    private double score;//得分
    private List<String> colect;//合同名称集合
    private List<String> idid;//合同id集合
    private List<String> purTime;//合同时间集合
    private List<String> purMoney;//合同金额集合
    private List<String> purWay;//合同采购方式集合
    private List<String> purCode;//合同编号集合
    private List<String> purPlanCode;//合同方案号集合
    private List<String> undMan;//经办人集合
    private List<String> material;//物料名称集合

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getContractTypeName() {
        return contractTypeName;
    }

    public void setContractTypeName(String contractTypeName) {
        this.contractTypeName = contractTypeName;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }
    public String getContractName() {
        return contractName;
    }

    public void setContractName(String contractName) {
        this.contractName = contractName;
    }
    public double getScore() {
        return score;
    }

    public void setScore(double score) {
        this.score = score;
    }

    public String getContractTime() {
        return contractTime;
    }

    public void setContractTime(String contractTime) {
        this.contractTime = contractTime;
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

    public List<String> getColect() {
        return colect;
    }

    public void setColect(List<String> colect) {
        this.colect = colect;
    }

    public List<String> getPurTime() {
        return purTime;
    }

    public void setPurTime(List<String> purTime) {
        this.purTime = purTime;
    }

    public List<String> getPurMoney() {
        return purMoney;
    }

    public void setPurMoney(List<String> purMoney) {
        this.purMoney = purMoney;
    }

    public List<String> getPurWay() {
        return purWay;
    }

    public void setPurWay(List<String> purWay) {
        this.purWay = purWay;
    }

    public List<String> getPurCode() {
        return purCode;
    }

    public void setPurCode(List<String> purCode) {
        this.purCode = purCode;
    }

    public List<String> getPurPlanCode() {
        return purPlanCode;
    }

    public void setPurPlanCode(List<String> purPlanCode) {
        this.purPlanCode = purPlanCode;
    }

    public String getDraft_department() {
        return draft_department;
    }

    public void setDraft_department(String draft_department) {
        this.draft_department = draft_department;
    }

    public String getPurchase_amount() {
        return purchase_amount;
    }

    public void setPurchase_amount(String purchase_amount) {
        this.purchase_amount = purchase_amount;
    }

    public List<String> getIdid() {
        return idid;
    }

    public void setIdid(List<String> idid) {
        this.idid = idid;
    }

    public String getUndertakeMan() {
        return undertakeMan;
    }

    public void setUndertakeMan(String undertakeMan) {
        this.undertakeMan = undertakeMan;
    }

    public List<String> getUndMan() {
        return undMan;
    }

    public void setUndMan(List<String> undMan) {
        this.undMan = undMan;
    }

    public String getMaterialName() {
        return materialName;
    }

    public void setMaterialName(String materialName) {
        this.materialName = materialName;
    }

    public List<String> getMaterial() {
        return material;
    }

    public void setMaterial(List<String> material) {
        this.material = material;
    }

    @Override
    public String toString() {
        return "DismantTemp{" +
                "id='" + id + '\'' +
                ", undertakeDept='" + undertakeDept + '\'' +
                ", undertakeMan='" + undertakeMan + '\'' +
                ", draft_department='" + draft_department + '\'' +
                ", purchase_amount='" + purchase_amount + '\'' +
                ", supplierName='" + supplierName + '\'' +
                ", contractTypeName='" + contractTypeName + '\'' +
                ", count=" + count +
                ", contractName='" + contractName + '\'' +
                ", contractTime='" + contractTime + '\'' +
                ", contractAmount='" + contractAmount + '\'' +
                ", purchaseWay='" + purchaseWay + '\'' +
                ", materialName='" + materialName + '\'' +
                ", score=" + score +
                ", colect=" + colect +
                ", idid=" + idid +
                ", purTime=" + purTime +
                ", purMoney=" + purMoney +
                ", purWay=" + purWay +
                ", purCode=" + purCode +
                ", purPlanCode=" + purPlanCode +
                ", undMan=" + undMan +
                ", material=" + material +
                '}';
    }

    @Override
    public int compareTo(DismantTemp o) {
        if(this.score>o.score)//score是private的，为什么能够直接调用,这是因为在Student类内部
            return -1;//由高到底排序
        else if(this.score<o.score)
            return 1;
        return 0;
    }
}
