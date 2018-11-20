package com.gpdi.mdata.sys.entity.report;

import javax.persistence.*;
import java.io.Serializable;

/**
 * 及时清洁
 */
@Entity //这是一个实体bean
@Table(name = "t_clean_list_immediately_rel")//指定映射表的表名
public class CleanInventoryRel implements Serializable {
    /**
     * Java的序列化的机制通过判断serialVersionUID来验证版本的一致性。
     * 在反序列化的时候与本地的类的serialVersionUID进行比较，
     * 一致则可以进行反序列化，不一致则会抛出异常InvalidCastException
     */
    private static final long serialVersionUID = 1404287303904307420L;
    private Integer Id;             //id
    private String  ContractName;          //合同名称
    private String  ContractCode;//合同编号
    private String  SupplierCode; //供应商编码
    private String  SupplierName; //供应商名称
    private String  MaterialCode;   //物料编码
    private String  MaterialName;  //物料名称
    private String  UndertakeMan;   //拟稿人
    private String  UndertakeDept; //拟稿部门
    private String  ContractAmount;     //合同金额
    private String  PurchaseTime;     //拟稿时间

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
    @Column(name = "contract_name")
    public String getContractName() {
        return ContractName;
    }

    public void setContractName(String contractName) {
        ContractName = contractName;
    }
    @Column(name = "contract_code")
    public String getContractCode() {
        return ContractCode;
    }
    public void setContractCode(String contractCode) {
        ContractCode = contractCode;
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

    @Column(name = "undertake_man")
    public String getUndertakeMan() {
        return UndertakeMan;
    }

    public void setUndertakeMan(String undertakeMan) {
        UndertakeMan = undertakeMan;
    }

    @Column(name = "undertake_dept")
    public String getUndertakeDept() {
        return UndertakeDept;
    }

    public void setUndertakeDept(String undertakeDept) {
        UndertakeDept = undertakeDept;
    }

    @Column(name = "contract_amount")
    public String getContractAmount() {
        return ContractAmount;
    }

    public void setContractAmount(String contractAmount) {
        ContractAmount = contractAmount;
    }
    @Column(name = "purchase_time")
    public String getPurchaseTime() {
        return PurchaseTime;
    }

    public void setPurchaseTime(String purchaseTime) {
        PurchaseTime = purchaseTime;
    }


    @Override
    public String toString() {
        return "CleanInventoryRel{" +
                "Id=" + Id +
                ", ContractName='" + ContractName + '\'' +
                ", ContractCode='" + ContractCode + '\'' +
                ", SupplierCode='" + SupplierCode + '\'' +
                ", SupplierName='" + SupplierName + '\'' +
                ", MaterialCode='" + MaterialCode + '\'' +
                ", MaterialName='" + MaterialName + '\'' +
                ", UndertakeMan='" + UndertakeMan + '\'' +
                ", UndertakeDept='" + UndertakeDept + '\'' +
                ", ContractAmount='" + ContractAmount + '\'' +
                ", PurchaseTime='" + PurchaseTime + '\'' +
                '}';
    }
}
