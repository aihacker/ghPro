package com.gpdi.mdata.sys.entity.report;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * 获取即时清结候选供应商
 */
@Entity //这是一个实体bean
@Table(name = "t_clean_result")//指定映射表的表名
public class CleanResult implements Serializable {

    private static final long serialVersionUID = -5086856965921471571L;
    private Integer id;             //id
    private String  jsqj_num;   //即时清结编号
    private String  dtime;   //拟稿时间
    private String  providers;   //供应商
    private String  pro_sum;     //评审总价
    private String  opinion; //流程审核信息
    private String  branch; //甩单部门(广东省)
    private String  account;   //甩单工号(admin)
    private Date  date;  //甩单时间
    private String  service_tem;  //模板名字(单条即时清结)



    @Id //定义一个主键
    @Column(name = "id")//映射数据表中对应的那一列字段名
    @GeneratedValue(strategy = GenerationType.IDENTITY)//为实体生成唯一标识的主键并自增
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
    @Column(name = "jsqj_num")
    public String getJsqj_num() {
        return jsqj_num;
    }

    public void setJsqj_num(String jsqj_num) {
        this.jsqj_num = jsqj_num;
    }
    @Column(name = "dtime")
    public String getDtime() {
        return dtime;
    }

    public void setDtime(String dtime) {
        this.dtime = dtime;
    }
    @Column(name = "providers")
    public String getProviders() {
        return providers;
    }

    public void setProviders(String providers) {
        this.providers = providers;
    }
    @Column(name = "pro_sum")
    public String getPro_sum() {
        return pro_sum;
    }

    public void setPro_sum(String pro_sum) {
        this.pro_sum = pro_sum;
    }
    @Column(name = "opinion")
    public String getOpinion() {
        return opinion;
    }

    public void setOpinion(String opinion) {
        this.opinion = opinion;
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
    @Column(name = "date")
    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
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
        return "CleanResult{" +
                "id=" + id +
                ", jsqj_num='" + jsqj_num + '\'' +
                ", dtime='" + dtime + '\'' +
                ", providers='" + providers + '\'' +
                ", pro_sum='" + pro_sum + '\'' +
                ", opinion='" + opinion + '\'' +
                ", branch='" + branch + '\'' +
                ", account='" + account + '\'' +
                ", date='" + date + '\'' +
                ", service_tem='" + service_tem + '\'' +
                '}';
    }
}
