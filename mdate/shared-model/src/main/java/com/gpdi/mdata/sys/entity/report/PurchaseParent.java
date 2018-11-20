package com.gpdi.mdata.sys.entity.report;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "t_purchase_parent")//导入表
public class PurchaseParent implements Serializable,Cloneable{


    private static final long serialVersionUID = -3407198655700266462L;
    public static Integer STATE_READY = 0;//导入处理中
    public static Integer STATE_SUCCESS = 1;//成功
    public static Integer STATE_FAILED = 2;//失败

    private Integer id;
    private String code;    //导入批次号
    private Date createDate;   //导入时间
    private Integer state;      //0-导入处理中 1-导入成功， 2-导入失败
    private Integer type;      //导入文件类型,,0:采购合同报表;1.合同台账报表;2:领导亲属经商表;3:公开采购投标公司信息表
    private String file_name;        //文件名
    private String user;        //导入人
    private String remark;      //备注
    private String md5;         //唯一标识文件

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Column(name = "md5")
    public String getMd5() {
        return md5;
    }


    public void setMd5(String md5) {
        this.md5 = md5;
    }

    @Column(name = "code")
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
    @Column(name = "create_date")
    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }
    @Column(name = "state")
    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }
    @Column(name = "type")
    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }
    @Column(name = "file_name")
    public String getFile_name() {
        return file_name;
    }

    public void setFile_name(String file_name) {
        this.file_name = file_name;
    }
    @Column(name = "user")
    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }
    @Column(name = "remark")
    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    @Override
    public PurchaseParent clone() throws CloneNotSupportedException{
        return (PurchaseParent)super.clone();
    }

}