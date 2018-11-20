package com.gpdi.mdata.sys.entity.report;

/**
 * @description:
 * @author: ZZY
 * @data: Created in 2018/7/10 16:31
 * @modifier:
 */

import javax.persistence.*;
import java.io.Serializable;

/**
 * 保存IC卡台帐对帐单
 */
@Entity //这是一个实体bean
@Table(name = "t_pdf_through_detail")//指定映射表的表名
public class PdfTotalBean implements Serializable {
    private static final long serialVersionUID = -8379080183465387723L;
    public static Integer STATE_READY = 0;
    public static Integer STATE_SUCCESS = 1;
    public static Integer STATE_FAILED = 2;

    private Integer detailid;          //明细id
    private String invoiceCode;          //发票代码
    private String invoiceNumber;       //发票号码
    private Double invoiceSum;            //发票金额Double
    private String throughCity;          //通行城市
    private Integer serialNumber;         //序号Integer
    private String dealTime;              //交易时间
    private String entrance;                 //入口
    private String exitS;                    //出口
    private Double splitSum;             //拆分金额Double
    private String dealSum;                //交易金额Double
    private String etcCardNumber;            //etc卡号
    private String licencePlateNumber;       //车牌号
    private String week;
    private String holidayTypes;



    @Id //定一个主键
    @Column(name = "detail_id")//映射数据表中对应的那一列字段名
    @GeneratedValue(strategy = GenerationType.IDENTITY)//为实体生成唯一标识的主键并自增
    public Integer getDetailid() {
        return detailid;
    }

    public void setDetailid(Integer detailid) {
        this.detailid = detailid;
    }
    @Column(name = "invoice_code")
    public String getInvoiceCode() {
        return invoiceCode;
    }

    public void setInvoiceCode(String invoiceCode) {
        this.invoiceCode = invoiceCode;
    }

    @Column(name = "invoice_number")
    public String getInvoiceNumber() {
        return invoiceNumber;
    }

    public void setInvoiceNumber(String invoiceNumber) {
        this.invoiceNumber = invoiceNumber;
    }

    @Column(name = "invoice_sum")
    public Double getInvoiceSum() {
        return invoiceSum;
    }

    public void setInvoiceSum(Double invoiceSum) {
        this.invoiceSum = invoiceSum;
    }



    @Column(name = "through_city")
    public String getThroughCity() {
        return throughCity;
    }

    public void setThroughCity(String throughCity) {
        this.throughCity = throughCity;
    }

    @Column(name = "serial_number")
    public Integer getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(Integer serialNumber) {
        this.serialNumber = serialNumber;
    }
    @Column(name = "deal_time")
    public String getDealTime() {
        return dealTime;
    }

    public void setDealTime(String dealTime) {
        this.dealTime = dealTime;
    }

    @Column(name = "entrance")
    public String getEntrance() {
        return entrance;
    }

    public void setEntrance(String entrance) {
        this.entrance = entrance;
    }
    @Column(name = "exit_s")
    public String getExit() {
        return exitS;
    }

    public void setExit(String exit) {
        this.exitS = exit;
    }

    @Column(name = "split_sum")
    public Double getSplitSum() {
        return splitSum;
    }

    public void setSplitSum(Double splitSum) {
        this.splitSum = splitSum;
    }

    @Column(name = "deal_sum")
    public String getDealSum() {
        return dealSum;
    }

    public void setDealSum(String dealSum) {
        this.dealSum = dealSum;
    }

    @Column(name = "etc_card_number")
    public String getEtcCardNumber() {
        return etcCardNumber;
    }

    public void setEtcCardNumber(String etcCardNumber) {
        this.etcCardNumber = etcCardNumber;
    }

    @Column(name = "licence_plate_number")
    public String getLicencePlateNumber() {
        return licencePlateNumber;
    }

    public void setLicencePlateNumber(String licencePlateNumber) {
        this.licencePlateNumber = licencePlateNumber;
    }

    @Column(name = "week")
    public String getWeek() {
        return week;
    }

    public void setWeek(String week) {
        this.week = week;
    }

    @Column(name = "holiday_types")
    public String getHolidayTypes() {
        return holidayTypes;
    }

    public void setHolidayTypes(String holidayTypes) {
        this.holidayTypes = holidayTypes;
    }
    @Override
    public String toString() {
        return "PdfTotalBean{" + "detailid=" + detailid + ", invoiceCode='" + invoiceCode + '\'' + ", invoiceNumber='" + invoiceNumber + '\'' + ", invoiceSum=" + invoiceSum + ", throughCity='" + throughCity + '\'' + ", serialNumber=" + serialNumber + ", dealTime='" + dealTime + '\'' + ", entrance='" + entrance + '\'' + ", exit='" + exitS + '\'' + ", splitSum=" + splitSum + ", dealSum='" + dealSum + '\'' + ", etcCardNumber='" + etcCardNumber + '\'' + ", licencePlateNumber='" + licencePlateNumber + '\'' + '}';
    }


}
