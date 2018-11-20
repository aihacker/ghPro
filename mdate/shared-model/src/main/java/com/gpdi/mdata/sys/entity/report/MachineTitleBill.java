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
 * 保存IC卡台帐对帐单标题
 */
@Entity //这是一个实体bean
@Table(name = "t_iccard_title_bill")//指定映射表的表名*/
public class MachineTitleBill {
    private static final long serialVersionUID = 1L;
    /*public static Integer STATE_READY = 0;
    public static Integer STATE_SUCCESS = 1;
    public static Integer STATE_FAILED = 2;*/

    private Integer  Id;         //主键
    private String  CustomerName;         //客户名称
    private String  CustomerCode;      //客户编码
    private String  WebsiteName;        //网点
    private String  BillType;           //账单类型
    private String  StartstopTime;     //起止时间
    private String  ApplyType;          //应用类型
    private String  BillOperator;       //账单操作员
    private String  PrintDate;            //打印日期
    private Double TitleId;                 //标题id



    @Id //定一个主键
    @Column(name = "id")//映射数据表中对应的那一列字段名
    @GeneratedValue(strategy = GenerationType.IDENTITY)//为实体生成唯一标识的主键并自增
    public Integer getId() {
        return Id;
    }

    public void setId(Integer id) {
        Id = id;
    }
    @Column(name = "title_id")
    public Double getTitleId() {
        return TitleId;
    }

    public void setTitleId(Double titleId) {
        TitleId = titleId;
    }

    @Column(name = "customer_name")
    public String getCustomerName() {
        return CustomerName;
    }

    public void setCustomerName(String customerName) {
        CustomerName = customerName;
    }

    @Column(name = "customer_code")
    public String getCustomerCode() {
        return CustomerCode;
    }

    public void setCustomerCode(String customerCode) {
        CustomerCode = customerCode;
    }

    @Column(name = "website_name")
    public String getWebsiteName() {
        return WebsiteName;
    }

    public void setWebsiteName(String websiteName) {
        WebsiteName = websiteName;
    }

    @Column(name = "bill_type")
    public String getBillType() {
        return BillType;
    }

    public void setBillType(String billType) {
        BillType = billType;
    }

    @Column(name = "startstop_time")
    public String getStartstopTime() {
        return StartstopTime;
    }

    public void setStartstopTime(String startstopTime) {
        StartstopTime = startstopTime;
    }

    @Column(name = "apply_type")
    public String getApplyType() {
        return ApplyType;
    }

    public void setApplyType(String applyType) {
        ApplyType = applyType;
    }

    @Column(name = "bill_operator")
    public String getBillOperator() {
        return BillOperator;
    }

    public void setBillOperator(String billOperator) {
        BillOperator = billOperator;
    }

    @Column(name = "print_date")
    public String getPrintDate() {
        return PrintDate;
    }

    public void setPrintDate(String printDate) {
        PrintDate = printDate;
    }

    @Override
    public String toString() {
        return "MachineTitleBill{" +
                "Id=" + Id +
                ", CustomerName='" + CustomerName + '\'' +
                ", CustomerCode=" + CustomerCode +
                ", WebsiteName='" + WebsiteName + '\'' +
                ", BillType='" + BillType + '\'' +
                ", StartstopTime='" + StartstopTime + '\'' +
                ", ApplyType='" + ApplyType + '\'' +
                ", BillOperator='" + BillOperator + '\'' +
                ", PrintDate='" + PrintDate + '\'' +
                ", TitleId=" + TitleId +
                '}';
    }
}
