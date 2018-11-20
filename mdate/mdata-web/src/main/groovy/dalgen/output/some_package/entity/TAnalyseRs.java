/*
 * @(#) TAnalyseRs.java Jun 17, 2016
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
@Table(name = "t_analyse_rs")
public class TAnalyseRs implements Serializable {
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

    // ??
    private String sj;

    // ????
    private Double waitTime;

    // ????
    private Double dealwithTime;

    // ???
    private Integer bizNum;


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

    @Column(name = "SJ")
    public String getSj() {
        return sj;
    }

    @Column(name = "WAIT_TIME")
    public Double getWaitTime() {
        return waitTime;
    }

    @Column(name = "DEALWITH_TIME")
    public Double getDealwithTime() {
        return dealwithTime;
    }

    @Column(name = "BIZ_NUM")
    public Integer getBizNum() {
        return bizNum;
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

    public void setSj(String sj) {
        this.sj = sj;
    }

    public void setWaitTime(Double waitTime) {
        this.waitTime = waitTime;
    }

    public void setDealwithTime(Double dealwithTime) {
        this.dealwithTime = dealwithTime;
    }

    public void setBizNum(Integer bizNum) {
        this.bizNum = bizNum;
    }

}
