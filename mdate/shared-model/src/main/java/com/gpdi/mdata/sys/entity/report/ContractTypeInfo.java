package com.gpdi.mdata.sys.entity.report;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @description:合同类型小类
 * @author: WangXiaoGang
 * @data: Created in 2018/11/8 19:21
 * @modifier:
 */
@Entity
@Table(name = "t_contract_type_child")
public class ContractTypeInfo implements Serializable{

    private static final long serialVersionUID = -4015160390021748900L;

    private int id;
    private String typeCode;//小类编号
    private String typeName;//小类名称
    private String parentNum;//所属父类编号

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
    @Column(name = "parent_num")
    public String getParentNum() {
        return parentNum;
    }

    public void setParentNum(String parentNum) {
        this.parentNum = parentNum;
    }

    @Override
    public String toString() {
        return "ContractTypeInfo{" +
                "id=" + id +
                ", typeCode='" + typeCode + '\'' +
                ", typeName='" + typeName + '\'' +
                ", parentNum='" + parentNum + '\'' +
                '}';
    }
}
