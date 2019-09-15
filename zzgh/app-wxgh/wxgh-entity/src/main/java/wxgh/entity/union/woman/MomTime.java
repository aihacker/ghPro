package wxgh.entity.union.woman;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * Created by Administrator on 2017/9/14.
 */
@Entity
@Table(name = "t_woman_mom_time")
public class MomTime implements Serializable {

    private Integer id;
    private Integer momId;
    private Integer week;
    private String startTime;
    private String endTime;
    private Integer status;

    @Id
    @Column
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Column
    public Integer getMomId() {
        return momId;
    }

    public void setMomId(Integer momId) {
        this.momId = momId;
    }

    @Column
    public Integer getWeek() {
        return week;
    }

    public void setWeek(Integer week) {
        this.week = week;
    }

    @Column
    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    @Column
    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    @Column
    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
