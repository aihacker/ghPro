package com.gpdi.mdata.sys.entity.report;

import org.springframework.context.annotation.EnableAspectJAutoProxy;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @description:必须公开招标的采购方式规则
 * @author: WangXiaoGang
 * @data: Created in 2018/10/13 19:17
 * @modifier:
 */
@Entity
@Table(name = "t_contract_type_way")
public class ContractTypeWay implements Serializable{

    private static final long serialVersionUID = 1508474839166623597L;

    private Integer id;
    private String codes;//合同类型编号
    private String typeName;//合同类型名称
    private String titleOne;//工程类别1
    private String titleTwo;//工程类别2
    private String titleThree;//工程类别3
    private Integer money;//金额
    private Integer rolesNumber;//规则编号

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
    @Column(name = "codes")
    public String getCodes() {
        return codes;
    }

    public void setCodes(String codes) {
        this.codes = codes;
    }
    @Column(name = "type_name")
    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }
    @Column(name = "title_one")
    public String getTitleOne() {
        return titleOne;
    }

    public void setTitleOne(String titleOne) {
        this.titleOne = titleOne;
    }
    @Column(name = "title_two")
    public String getTitleTwo() {
        return titleTwo;
    }

    public void setTitleTwo(String titleTwo) {
        this.titleTwo = titleTwo;
    }
    @Column(name = "title_three")
    public String getTitleThree() {
        return titleThree;
    }

    public void setTitleThree(String titleThree) {
        this.titleThree = titleThree;
    }

    @Column(name = "money")
    public Integer getMoney() {
        return money;
    }

    public void setMoney(Integer money) {
        this.money = money;
    }

    @Column(name = "roles_number")
    public Integer getRolesNumber() {
        return rolesNumber;
    }

    public void setRolesNumber(Integer rolesNumber) {
        this.rolesNumber = rolesNumber;
    }


    @Override
    public String toString() {
        return "ContractTypeWay{" +
                "id=" + id +
                ", codes='" + codes + '\'' +
                ", typeName='" + typeName + '\'' +
                ", titleOne='" + titleOne + '\'' +
                ", titleTwo='" + titleTwo + '\'' +
                ", titleThree='" + titleThree + '\'' +
                ", money=" + money +
                ", rolesNumber='" + rolesNumber + '\'' +
                '}';
    }
}
