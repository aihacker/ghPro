package com.gpdi.mdata.web.reportform.daoexcel.entities.StationBill;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

/**
 * @description:
 * @author: zzy
 * @data: Created in 2018/6/28 9:16
 * @modifier:
 */
@Entity //这是一个实体bean
@Table(name = "t_iccard_machine_bill")//指定映射表的表名
public class ExcelPurchaseBill implements Serializable {
    private String CardNumber;    //卡号
    private String Date;            //时间
    private String BusinessType;   //业务类型
    private String Variety;         //品种
    private Double Number;          //数量
    private Double UnitPrice;       //单价
    private Double Sum;              //金额
    private Double RewardPoints;   //奖励分值
    private Double PreferentialPrice; //优惠价
    private Double Balance;             //余额
    private String Site;                //地点
    private String Operator;            //操作员
    private String Remark;              //备注
    private String TitleId;              //title



    public String getCardNumber() {
        return CardNumber;
    }

    public void setCardNumber(String cardNumber) {
        CardNumber = cardNumber;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }

    public String getBusinessType() {
        return BusinessType;
    }

    public void setBusinessType(String businessType) {
        BusinessType = businessType;
    }

    public String getVariety() {
        return Variety;
    }

    public void setVariety(String variety) {
        Variety = variety;
    }

    public Double getNumber() {
        return Number;
    }

    public void setNumber(Double number) {
        Number = number;
    }

    public Double getUnitPrice() {
        return UnitPrice;
    }

    public void setUnitPrice(Double unitPrice) {
        UnitPrice = unitPrice;
    }

    public Double getSum() {
        return Sum;
    }

    public void setSum(Double sum) {
        Sum = sum;
    }

    public Double getRewardPoints() {
        return RewardPoints;
    }

    public void setRewardPoints(Double rewardPoints) {
        RewardPoints = rewardPoints;
    }

    public Double getPreferentialPrice() {
        return PreferentialPrice;
    }

    public void setPreferentialPrice(Double preferentialPrice) {
        PreferentialPrice = preferentialPrice;
    }

    public Double getBalance() {
        return Balance;
    }

    public void setBalance(Double balance) {
        Balance = balance;
    }

    public String getSite() {
        return Site;
    }

    public void setSite(String site) {
        Site = site;
    }

    public String getOperator() {
        return Operator;
    }

    public void setOperator(String operator) {
        Operator = operator;
    }

    public String getRemark() {
        return Remark;
    }

    public void setRemark(String remark) {
        Remark = remark;
    }

    public String getTitleId() {
        return TitleId;
    }

    public void setTitleId(String titleId) {
        TitleId = titleId;
    }




}