package com.gpdi.mdata.sys.entity.report;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @description:
 * @author: WangXiaoGang
 * @data: Created in 2018/11/8 9:20
 * @modifier:合同类型大类
 */
@Entity
@Table(name = "t_contract_type")
public class ContractType implements Serializable{

    private static final long serialVersionUID = -2267706646071415370L;

    private int id;

    private String typeCode;//合同类型编号

    private String typeName;    //合同类型名称

    private int parentId;//父级id

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Column(name = "type_code")
    public String getTypeCode() {
        return typeCode;
    }

    public void setTypeCode(String typeCode) {
        this.typeCode = typeCode;
    }
    @Column(name = "type_name")
    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }
    @Column(name = "parent_id")
    public int getParentId() {
        return parentId;
    }

    public void setParentId(int parentId) {
        this.parentId = parentId;
    }

    @Override
    public String toString() {
        return "ContractType{" +
                "id=" + id +
                ", typeCode='" + typeCode + '\'' +
                ", typeName='" + typeName + '\'' +
                ", parentId=" + parentId +
                '}';
    }
}
