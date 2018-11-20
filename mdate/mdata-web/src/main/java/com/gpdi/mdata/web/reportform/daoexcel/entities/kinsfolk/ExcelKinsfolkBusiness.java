package com.gpdi.mdata.web.reportform.daoexcel.entities.kinsfolk;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

/**
 * @description:做日志记录调用
 * @author: WangXiaoGang
 * @data: Created in 2018/6/28 9:16
 * @modifier:
 */
@Entity //这是一个实体bean
@Table(name = "t_kinsfolk_business")//指定映射表的表名
public class ExcelKinsfolkBusiness implements Serializable {
    private String  EnterpriseName;   //企业名称
    private String  EnterpriseCode;   //企业代码
    private String  KinsfolkName;       //亲属姓名
    private String  Sex;   //性别
    private String IdentityCardNumber; // 身份证号码
    private String  Duty; //职务
    private Date  DataUpdateTime;   //数据更新时间
    private String  DataUpdateWay;  //数据更新方式


    public String getEnterpriseName() {
        return EnterpriseName;
    }

    public void setEnterpriseName(String enterpriseName) {
        EnterpriseName = enterpriseName;
    }

    public String getEnterpriseCode() {
        return EnterpriseCode;
    }

    public void setEnterpriseCode(String enterpriseCode) {
        EnterpriseCode = enterpriseCode;
    }

    public String getKinsfolkName() {
        return KinsfolkName;
    }

    public void setKinsfolkName(String kinsfolkName) {
        KinsfolkName = kinsfolkName;
    }

    public String getSex() {
        return Sex;
    }

    public void setSex(String sex) {
        Sex = sex;
    }

    public String getIdentityCardNumber() {
        return IdentityCardNumber;
    }

    public void setIdentityCardNumber(String identityCardNumber) {
        IdentityCardNumber = identityCardNumber;
    }

    public String getDuty() {
        return Duty;
    }

    public void setDuty(String duty) {
        Duty = duty;
    }

    public Date getDataUpdateTime() {
        return DataUpdateTime;
    }

    public void setDataUpdateTime(Date dataUpdateTime) {
        DataUpdateTime = dataUpdateTime;
    }

    public String getDataUpdateWay() {
        return DataUpdateWay;
    }

    public void setDataUpdateWay(String dataUpdateWay) {
        DataUpdateWay = dataUpdateWay;
    }
}