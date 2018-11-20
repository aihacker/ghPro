package com.gpdi.mdata.sys.entity.report;

/**
 * @description:
 * @author: ZZY
 * @data: Created in 2018/7/10 16:31
 * @modifier:
 */

import javax.persistence.*;
import java.util.Date;

/**
 * 保存IC卡台帐对帐单
 */
@Entity //这是一个实体bean
@Table(name = "t_iccard_machine_bill")//指定映射表的表名
public class MachineBill {
    public static Integer STATE_READY = 0;
    public static Integer STATE_SUCCESS = 1;
    public static Integer STATE_FAILED = 2;

    private Integer CardId;                 //id
    private String CardNumber ;           //卡号
    private String Date;                    //时间
    private String BusinessType;           //业务类型
    private String  Variety;               //品种
    private Double  Number;                //数量
    private Double  UnitPrice;            //单价
    private Double  Sum;                   //金额
    private Double  RewardPoints;        //奖励分值
    private Double  PreferentialPrice;  //优惠价
    private Double  Balance;              //余额
    private String  Site;                 //地点
    private String  Operator;            //操作员
    private String  Remark;              //备注
    private String  TitleId;              //title_id



    @Id //定一个主键
    @Column(name = "Card_id")//映射数据表中对应的那一列字段名
    @GeneratedValue(strategy = GenerationType.IDENTITY)//为实体生成唯一标识的主键并自增
    public Integer getCardId() {
        return CardId;
    }

    public void setCardId(Integer cardId) {
        CardId = cardId;
    }
    @Column(name = "card_number")
    public String getCardNumber() {
        return CardNumber;
    }

    public void setCardNumber(String cardNumber) {
        CardNumber = cardNumber;
    }
    @Column(name = "date")
    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }
    @Column(name = "business_type")
    public String getBusinessType() {
        return BusinessType;
    }

    public void setBusinessType(String businessType) {
        BusinessType = businessType;
    }
    @Column(name = "variety")
    public String getVariety() {
        return Variety;
    }

    public void setVariety(String variety) {
        Variety = variety;
    }
    @Column(name = "number")
    public Double getNumber() {
        return Number;
    }

    public void setNumber(Double number) {
        Number = number;
    }
    @Column(name = "unit_price")
    public Double getUnitPrice() {
        return UnitPrice;
    }

    public void setUnitPrice(Double unitPrice) {
        UnitPrice = unitPrice;
    }
    @Column(name = "sum")
    public Double getSum() {
        return Sum;
    }

    public void setSum(Double sum) {
        Sum = sum;
    }
    @Column(name = "reward_points")
    public Double getRewardPoints() {
        return RewardPoints;
    }

    public void setRewardPoints(Double rewardPoints) {
        RewardPoints = rewardPoints;
    }
    @Column(name = "preferential_price")
    public Double getPreferentialPrice() {
        return PreferentialPrice;
    }

    public void setPreferentialPrice(Double preferentialPrice) {
        PreferentialPrice = preferentialPrice;
    }
    @Column(name = "balance")
    public Double getBalance() {
        return Balance;
    }

    public void setBalance(Double balance) {
        Balance = balance;
    }
    @Column(name = "site")
    public String getSite() {
        return Site;
    }

    public void setSite(String site) {
        Site = site;
    }
    @Column(name = "operator")
    public String getOperator() {
        return Operator;
    }

    public void setOperator(String operator) {
        Operator = operator;
    }
    @Column(name = "remark")
    public String getRemark() {
        return Remark;
    }

    public void setRemark(String remark) {
        Remark = remark;
    }
    @Column(name = "title_id")
    public String getTitleId() {
        return TitleId;
    }

    public void setTitleId(String titleId) {
        TitleId = titleId;
    }


    @Override
    public String toString() {
        return "MachineBill{" +
                "CardId=" + CardId +
                ", CardNumber='" + CardNumber + '\'' +
                ", Date='" + Date + '\'' +
                ", BusinessType='" + BusinessType + '\'' +
                ", Variety='" + Variety + '\'' +
                ", Number='" + Number + '\'' +
                ", UnitPrice='" + UnitPrice + '\'' +
                ", Sum='" + Sum + '\'' +
                ", RewardPoints='" + RewardPoints + '\'' +
                ", PreferentialPrice='" + PreferentialPrice + '\'' +
                ", Balance='" + Balance + '\'' +
                ", Site='" + Site + '\'' +
                ", Operator='" + Operator + '\'' +
                ", Remark='" + Remark + '\'' +
                ", TitleId=" + TitleId +
                '}';
    }
}
