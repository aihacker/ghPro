package com.gpdi.mdata.sys.entity.report;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * @author: WangXiaoGang
 * @data: Created in 2018/7/24 9:13
 * @description:
 */
@Entity //这是一个实体bean
@Table(name = "t_kinsfolk_business")//指定映射表的表名
public class KinsfolkBusiness implements Serializable {

    private static final long serialVersionUID = -1719087058529440637L;
    private Integer Id;             //id
    private String  EnterpriseName;   //企业名称
    private String  EnterpriseCode;   //企业代码
    private String  KinsfolkName;       //亲属姓名
    private String  Sex;   //性别
    private String IdentityCardNumber; // 身份证号码
    private String  Duty; //职务
    private Date  DataUpdateTime;   //数据更新时间
    private String  DataUpdateWay;  //数据更新方式
    private Integer PId;           //导入日志关联id

    @Id //定义一个主键
    @Column(name = "id")//映射数据表中对应的那一列字段名
    @GeneratedValue(strategy = GenerationType.IDENTITY)//为实体生成唯一标识的主键并自增
    public Integer getId() {
        return Id;
    }

    public void setId(Integer id) {
        Id = id;
    }
    @Column(name = "enterprise_name")
    public String getEnterpriseName() {
        return EnterpriseName;
    }

    public void setEnterpriseName(String enterpriseName) {
        EnterpriseName = enterpriseName;
    }
    @Column(name = "enterprise_code")
    public String getEnterpriseCode() {
        return EnterpriseCode;
    }

    public void setEnterpriseCode(String enterpriseCode) {
        EnterpriseCode = enterpriseCode;
    }
    @Column(name = "kinsfolk_name")
    public String getKinsfolkName() {
        return KinsfolkName;
    }

    public void setKinsfolkName(String kinsfolkName) {
        KinsfolkName = kinsfolkName;
    }
    @Column(name = "sex")
    public String getSex() {
        return Sex;
    }

    public void setSex(String sex) {
        Sex = sex;
    }
    @Column(name = "identity_card_number")
    public String getIdentityCardNumber() {
        return IdentityCardNumber;
    }

    public void setIdentityCardNumber(String identityCardNumber) {
        IdentityCardNumber = identityCardNumber;
    }
    @Column(name = "duty")
    public String getDuty() {
        return Duty;
    }

    public void setDuty(String duty) {
        Duty = duty;
    }
    @Column(name = "data_update_time")
    public Date getDataUpdateTime() {
        return DataUpdateTime;
    }

    public void setDataUpdateTime(Date dataUpdateTime) {
        DataUpdateTime = dataUpdateTime;
    }
    @Column(name = "data_update_way")
    public String getDataUpdateWay() {
        return DataUpdateWay;
    }

    public void setDataUpdateWay(String dataUpdateWay) {
        DataUpdateWay = dataUpdateWay;
    }

    @Column(name = "parent_id")
    public Integer getPId() {
        return PId;
    }

    public void setPId(Integer PId) {
        this.PId = PId;
    }

    @Override
    public String toString() {
        return "KinsfolkBusiness{" +
                "Id=" + Id +
                ", EnterpriseName='" + EnterpriseName + '\'' +
                ", EnterpriseCode='" + EnterpriseCode + '\'' +
                ", KinsfolkName='" + KinsfolkName + '\'' +
                ", Sex='" + Sex + '\'' +
                ", IdentityCardNumber='" + IdentityCardNumber + '\'' +
                ", Duty='" + Duty + '\'' +
                ", DataUpdateTime=" + DataUpdateTime +
                ", DataUpdateWay='" + DataUpdateWay + '\'' +
                ", PId=" + PId +
                '}';
    }
}
