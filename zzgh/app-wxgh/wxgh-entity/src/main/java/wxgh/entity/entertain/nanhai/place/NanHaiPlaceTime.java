package wxgh.entity.entertain.nanhai.place;


import com.libs.common.data.DateUtils;

import javax.persistence.*;
import java.io.Serializable;

/**
 * @Author xlkai
 * @Version 2017/1/12
 */
@Entity
@Table(name = "t_nanhai_place_time")
public class NanHaiPlaceTime implements Serializable {

    private Integer id;
    private Integer siteId;
    private Integer cateId;
    private String startTime;
    private String endTime;
    private Integer week;
    private Integer status;
    private Integer timeType;

    //
    private Float price;
    private Float score;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Column(name = "site_id")
    public Integer getSiteId() {
        return siteId;
    }

    public void setSiteId(Integer siteId) {
        this.siteId = siteId;
    }

    @Column(name = "cate_id")
    public Integer getCateId() {
        return cateId;
    }

    public void setCateId(Integer cateId) {
        this.cateId = cateId;
    }

    @Column(name = "start_time")
    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    @Column(name = "end_time")
    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    @Column(name = "week")
    public Integer getWeek() {
        return week;
    }

    public void setWeek(Integer week) {
        this.week = week;
    }

    @Column(name = "status")
    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    public Float getScore() {
        return score;
    }

    public void setScore(Float score) {
        this.score = score;
    }

    @Column(name = "time_type")
    public Integer getTimeType() {
        return timeType;
    }

    public void setTimeType(Integer timeType) {
        this.timeType = timeType;
    }

    @Override
    public String toString() {
        return DateUtils.getWeekName(week) + " " + startTime + "~" + endTime;
    }
}
