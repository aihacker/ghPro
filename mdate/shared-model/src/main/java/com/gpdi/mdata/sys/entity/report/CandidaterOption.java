package com.gpdi.mdata.sys.entity.report;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @description:采购合同审批人
 * @author: WangXiaoGang
 * @data: Created in 2018/10/25 10:22
 * @modifier:
 */
@Entity//指明这是一个实体
@Table(name = "t_candidater_option")//映射表名
public class CandidaterOption implements Serializable{

    private static final long serialVersionUID = -6704744682565786381L;

    private int id;

    private String canditaterResultCode;//采购结果编号

    private String approver;    //审批人

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)//为实体生成唯一标识主键自增
    @Column(name = "id")
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    @Column( name ="canditater_result_code")
    public String getCanditaterResultCode() {
        return canditaterResultCode;
    }

    public void setCanditaterResultCode(String canditaterResultCode) {
        this.canditaterResultCode = canditaterResultCode;
    }
    @Column(name = "approver")
    public String getApprover() {
        return approver;
    }

    public void setApprover(String approver) {
        this.approver = approver;
    }

    @Override
    public String toString() {
        return "CandidaterOption{" +
                "id=" + id +
                ", canditaterResultCode='" + canditaterResultCode + '\'' +
                ", approver='" + approver + '\'' +
                '}';
    }
}
