package wxgh.entity.common.female;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

/**
 *----------------------------------------------------------
 * @Description
 *----------------------------------------------------------
 * @Author  Ape
 *----------------------------------------------------------
 * @Email <16511660@qq.com>
 *----------------------------------------------------------
 * @Date 2017-08-01 09:21
 *----------------------------------------------------------
 */
@Entity
@Table(name = "t_female_mama_join")
public class FemaleMamaJoin implements Serializable {

    private Integer id;
    private String userid;
    private Date addTime;
    private Integer startTime;
    private Integer endTime;
    private Date date;
    private Integer mid;

    private String dateStr;

    /**
     *@Author:  ✔
     *@Description:  扩展属性
     *@Date:  15:20, 2017/5/11
     */
    private String cover;
    private String name;
    private Integer status;// 1有效  2无效

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    public Integer getId() {
        return id;
    }

    @Column(name = "userid")
    public String getUserid() {
        return userid;
    }

    @Column(name = "add_time")
    public Date getAddTime() {
        return addTime;
    }

    @Column(name = "start_time")
    public Integer getStartTime() {
        return startTime;
    }

    @Column(name = "end_time")
    public Integer getEndTime() {
        return endTime;
    }

    @Column(name = "date")
    public Date getDate() {
        return date;
    }

    @Column(name = "mid")
    public Integer getMid() {
        return mid;
    }

    public String getCover() {
        return cover;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setMid(Integer mid) {
        this.mid = mid;
    }

    public String getDateStr() {
        return dateStr;
    }

    public void setDateStr(String dateStr) {
        this.dateStr = dateStr;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public void setAddTime(Date addTime) {
        this.addTime = addTime;
    }

    public void setStartTime(Integer startTime) {
        this.startTime = startTime;
    }

    public void setEndTime(Integer endTime) {
        this.endTime = endTime;
    }
}
