package com.gpdi.mdata.sys.entity.system;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by ye on 2017
 */
@Entity
@Table(name = "sys_role_user")
public class SysRoleUser implements Serializable {
    private static final long serialVersionUID = 1L;

    private Integer id;
    private Integer roleId;
    private Integer userId;
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

    @Column(name = "role_id")
    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    @Column(name = "user_id")
    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    @Column(name = "is_enable")
    public Integer getIsEnable() {
        return isEnable;
    }

    public void setIsEnable(Integer isEnable) {
        this.isEnable = isEnable;
    }
}
