package wxgh.entity.union.race;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by Administrator on 2017/3/1.
 */
@Entity
@Table(name = "t_race")
public class Race implements Serializable {

    private Integer id;
    private String name;
    private String info;
    private Integer cateType;
    private String userid;
    private String phone;
    private Date startTime;
    private Date endTime;
    private Date entryTime;
    private Integer limitNum;
    private String img;
    private Integer raceType;
    private Integer deptid;
    private Integer status;
    private Integer bianpaiIs;
    private Integer groupIs;

    //
    private String username;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Column(name = "name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(name = "info")
    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    @Column(name = "cate_type")
    public Integer getCateType() {
        return cateType;
    }

    public void setCateType(Integer cateType) {
        this.cateType = cateType;
    }

    @Column(name = "userid")
    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    @Column(name = "phone")
    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Column(name = "start_time")
    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    @Column(name = "end_time")
    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    @Column(name = "entry_time")
    public Date getEntryTime() {
        return entryTime;
    }

    public void setEntryTime(Date entryTime) {
        this.entryTime = entryTime;
    }

    @Column(name = "limit_num")
    public Integer getLimitNum() {
        return limitNum;
    }

    public void setLimitNum(Integer limitNum) {
        this.limitNum = limitNum;
    }

    @Column(name = "img")
    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    @Column(name = "race_type")
    public Integer getRaceType() {
        return raceType;
    }

    public void setRaceType(Integer raceType) {
        this.raceType = raceType;
    }

    @Column(name = "deptid")
    public Integer getDeptid() {
        return deptid;
    }

    public void setDeptid(Integer deptid) {
        this.deptid = deptid;
    }

    @Column(name = "status")
    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    @Column(name = "bianpai_is")
    public Integer getBianpaiIs() {
        return bianpaiIs;
    }

    public void setBianpaiIs(Integer bianpaiIs) {
        this.bianpaiIs = bianpaiIs;
    }

    @Column(name = "group_is")
    public Integer getGroupIs() {
        return groupIs;
    }

    public void setGroupIs(Integer groupIs) {
        this.groupIs = groupIs;
    }
}
