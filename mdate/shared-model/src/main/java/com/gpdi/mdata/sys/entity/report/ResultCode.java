package com.gpdi.mdata.sys.entity.report;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * 采购合同表
 */
@Entity //这是一个实体bean
@Table(name = "t_result_code")//指定映射表的表名
public class ResultCode implements Serializable {
    private static final long serialVersionUID = -7217556249959194755L;

    private Integer id;             //id
    private String  cgbh;   //采购方案编号
    private String  gcmc;   //项目名称
    private String  xmlx;   //项目类型
    private String  gzrq;     //盖章日期
    private String  cgsqlx; //采购申请编码
    private String  yxgysmc; //候选供应商名称
    private String  gysbm;   //候选供应商编码
    private String  cgjgbm;  //采购结果编号
    private String  gcbm;  //项目编码
    private String  cggsze;  //采购估算总额
    private String  shxx;     //审批人
    private Date    date;  //甩单时间
    private String  branch;  //甩单部门
    private String  account;  //甩单工号
    private String  service_tem;  //模板的名称


    @Id //定义一个主键
    @Column(name = "id")//映射数据表中对应的那一列字段名
    @GeneratedValue(strategy = GenerationType.IDENTITY)//为实体生成唯一标识的主键并自增
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
    @Column(name = "cgbh")
    public String getCgbh() {
        return cgbh;
    }

    public void setCgbh(String cgbh) {
        this.cgbh = cgbh;
    }
    @Column(name = "gcmc")
    public String getGcmc() {
        return gcmc;
    }

    public void setGcmc(String gcmc) {
        this.gcmc = gcmc;
    }
    @Column(name = "xmlx")
    public String getXmlx() {
        return xmlx;
    }

    public void setXmlx(String xmlx) {
        this.xmlx = xmlx;
    }
    @Column(name = "gzrq")
    public String getGzrq() {
        return gzrq;
    }

    public void setGzrq(String gzrq) {
        this.gzrq = gzrq;
    }
    @Column(name = "cgsqlx")
    public String getCgsqlx() {
        return cgsqlx;
    }

    public void setCgsqlx(String cgsqlx) {
        this.cgsqlx = cgsqlx;
    }
    @Column(name = "yxgysmc")
    public String getYxgysmc() {
        return yxgysmc;
    }

    public void setYxgysmc(String yxgysmc) {
        this.yxgysmc = yxgysmc;
    }
    @Column(name = "gysbm")
    public String getGysbm() {
        return gysbm;
    }

    public void setGysbm(String gysbm) {
        this.gysbm = gysbm;
    }
    @Column(name = "cgjgbm")
    public String getCgjgbm() {
        return cgjgbm;
    }

    public void setCgjgbm(String cgjgbm) {
        this.cgjgbm = cgjgbm;
    }
    @Column(name = "gcbm")
    public String getGcbm() {
        return gcbm;
    }

    public void setGcbm(String gcbm) {
        this.gcbm = gcbm;
    }
    @Column(name = "cggsze")
    public String getCggsze() {
        return cggsze;
    }

    public void setCggsze(String cggsze) {
        this.cggsze = cggsze;
    }
    @Column(name = "shxx")
    public String getShxx() {
        return shxx;
    }

    public void setShxx(String shxx) {
        this.shxx = shxx;
    }
    @Column(name = "date")
    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
    @Column(name = "branch")
    public String getBranch() {
        return branch;
    }

    public void setBranch(String branch) {
        this.branch = branch;
    }
    @Column(name = "account")
    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }
    @Column(name = "service_tem")
    public String getService_tem() {
        return service_tem;
    }
    public void setService_tem(String service_tem) {
        this.service_tem = service_tem;
    }

    @Override
    public String toString() {
        return "ResultCode{" +
                "id=" + id +
                ", cgbh='" + cgbh + '\'' +
                ", gcmc='" + gcmc + '\'' +
                ", xmlx='" + xmlx + '\'' +
                ", gzrq='" + gzrq + '\'' +
                ", cgsqlx='" + cgsqlx + '\'' +
                ", yxgysmc='" + yxgysmc + '\'' +
                ", gysbm='" + gysbm + '\'' +
                ", cgjgbm='" + cgjgbm + '\'' +
                ", gcbm='" + gcbm + '\'' +
                ", cggsze='" + cggsze + '\'' +
                ", shxx='" + shxx + '\'' +
                ", date=" + date +
                ", branch='" + branch + '\'' +
                ", account='" + account + '\'' +
                ", service_tem='" + service_tem + '\'' +
                '}';
    }
}
