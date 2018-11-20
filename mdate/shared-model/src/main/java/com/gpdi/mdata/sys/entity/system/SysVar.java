package com.gpdi.mdata.sys.entity.system;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by lihz on 2017/4/20.
 */
@Entity
@Table(name = "sys_var")
public class SysVar implements Serializable {
    private static final long serialVersionUID = 1L;

    private Integer id;
    private String varName;
    private String varValue;
    private String varCode ;
    private Integer isEnable;
    private String  description;

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
    @Column(name = "var_name")
    public String getVarName() {
        return varName;
    }

    public void setVarName(String varName) {
        this.varName = varName;
    }
    @Column(name = "var_value")
    public String getVarValue() {
        return varValue;
    }

    public void setVarValue(String varValue) {
        this.varValue = varValue;
    }
    @Column(name = "var_code")
    public String getVarCode() {
        return varCode;
    }

    public void setVarCode(String varCode) {
        this.varCode = varCode;
    }
    @Column(name = "is_enable")
    public Integer getIsEnable() {
        return isEnable;
    }

    public void setIsEnable(Integer isEnable) {
        this.isEnable = isEnable;
    }
    @Column(name = "description")
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
