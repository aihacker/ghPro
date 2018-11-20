/*
 * @(#) TBranchBizRel.java Jun 17, 2016
 * Copyright  2016 GPDI. All right reserved.
 */
package some_package.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.*;

/**
 * @author zoe
 * @version 1.0 Jun 17, 2016
 */
@Entity
@Table(name = "t_branch_biz_rel")
public class TBranchBizRel implements Serializable {
    /**
     * 
     */
    private static final long serialVersionUID = 1L;

    // ??ID
    private Integer id;

    // ??ID
    private Integer branchId;

    // ??ID
    private Integer bizTypeId;

    // ?????? 1 ?,0 ?
    private Integer isNetAppoint;


    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Integer getId() {
        return id;
    }

    @Column(name = "BRANCH_ID")
    public Integer getBranchId() {
        return branchId;
    }

    @Column(name = "BIZ_TYPE_ID")
    public Integer getBizTypeId() {
        return bizTypeId;
    }

    @Column(name = "IS_NET_APPOINT")
    public Integer getIsNetAppoint() {
        return isNetAppoint;
    }


    public void setId(Integer id) {
        this.id = id;
    }

    public void setBranchId(Integer branchId) {
        this.branchId = branchId;
    }

    public void setBizTypeId(Integer bizTypeId) {
        this.bizTypeId = bizTypeId;
    }

    public void setIsNetAppoint(Integer isNetAppoint) {
        this.isNetAppoint = isNetAppoint;
    }

}
