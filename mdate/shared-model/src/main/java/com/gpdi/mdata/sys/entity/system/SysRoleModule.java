package com.gpdi.mdata.sys.entity.system;

import javax.persistence.*;
import java.io.Serializable;

/**
 * 用户表
 */
@Entity
@Table(name = "sys_role_module")
public class SysRoleModule implements Serializable {
    private static final long serialVersionUID = 1L;

    private Integer id;
    private Integer roleId;
    private Integer moduleId;
    private Integer isEnable;


    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    public Integer getModuleId() {
        return moduleId;
    }

    public void setModuleId(Integer moduleId) {
        this.moduleId = moduleId;
    }

    public Integer getIsEnable() {
        return isEnable;
    }

    public void setIsEnable(Integer isEnable) {
        this.isEnable = isEnable;
    }
}
